package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class MultiplicationCard extends DomCard {

    protected final ArrayList<DomCard> myDurationCards = new ArrayList<DomCard>();

    protected DomCard myCardToMultiply;

    public MultiplicationCard(DomCardName aCardName) {
	  super(aCardName);
	}

    public DomCard getMyCardToMultiply() {
        return myCardToMultiply;
    }

    public void play(){
      myCardToMultiply =null;
      if (owner.isHumanOrPossessedByHuman()) {
          myCardToMultiply = handleHumanPlayer();
      } else {
          myCardToMultiply = getCardToMultiply();
      }
      if (myCardToMultiply == null)
        return;
      //little fix for Tactician
      if (myCardToMultiply.hasCardType(DomCardType.Duration) && myCardToMultiply.getName()!= DomCardName.Tactician){
        myDurationCards.add(myCardToMultiply);
        setDiscardAtCleanup(false);
      }
      owner.removeCardFromHand(myCardToMultiply);
      owner.handleUrchins(myCardToMultiply);
      owner.getCardsInPlay().add(myCardToMultiply);
      play(myCardToMultiply, 1);
      //little fix for Tactician
      if (myCardToMultiply.getName()!=DomCardName.Tactician){
          play(myCardToMultiply, 2);
          if (getName()==DomCardName.King$s_Court || getName()==DomCardName.Mastermind) {
            play(myCardToMultiply, 3);
          }
      }
    }

    protected DomCard handleHumanPlayer() {
	    owner.setNeedsToUpdateGUI();
        DomCard theCardToMultiply = null;
        if (!owner.getCardsFromHand(DomCardType.Action).isEmpty()) {
            ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
            for (DomCard theCard : owner.getCardsFromHand(DomCardType.Action)) {
                if (getName()==DomCardName.Procession && theCard.hasCardType(DomCardType.Duration))
                    continue;
                theChooseFrom.add(theCard.getName());
            }
            theCardToMultiply = owner.getCardsFromHand(owner.getEngine().getGameFrame().askToSelectOneCard("Select card for " + this.getName().toString(), theChooseFrom, "Don't use")).get(0);
        }
        return theCardToMultiply;
    }

    public ArrayList<DomCard> getDurationCards() {
        return myDurationCards;
    }

    @Override
    public void cleanVariablesFromPreviousGames() {
      myDurationCards.clear();
      //TODO this is a test
	  if (myDurationCards.isEmpty())
		return;
      if (owner==null) {
          myDurationCards.clear();
          return;
      }
	  //this is needed to check for lingering Duration cards linked to this card from a previous game
	  for (DomCard card : myDurationCards){
		  if (!owner.getCardsInPlay().contains(card) ){
			  myDurationCards.clear();
			  return;
		  }
	  }
	}

	private void play(DomCard theCardToMultiply, int i ) {
        DomPlayer thePlayer = owner;
        //fix for Crowning a Black Market buying a Mint from the Black Market
        if (thePlayer==null)
            thePlayer=theCardToMultiply.owner;
		String aLogAppend = " with the " + this;
		if (i==2)
		  aLogAppend = " again";
		if (i==3)
	      aLogAppend = " a third time";
		thePlayer.increaseActionsPlayed();
		if (DomEngine.haveToLog ) {
		  DomEngine.addToLog( thePlayer + " plays " + theCardToMultiply + aLogAppend);
		  DomEngine.logIndentation++;
		}
        //some cards have been trashed so playing second time needs an owner assigned
        theCardToMultiply.owner=thePlayer;
        thePlayer.playThis(theCardToMultiply);
		if (DomEngine.haveToLog ) {
  		  DomEngine.logIndentation--;
		}
	}
	    
	public void resolveDuration() {
      boolean cardOwnerWasNull = false;
      for (DomCard card : myDurationCards) {
          //Mastermind is tricky
          if (card.getName()==DomCardName.Mastermind && !((MastermindCard)card).getDurationCards().isEmpty())
              continue;
        cardOwnerWasNull = false;
    	if (DomEngine.haveToLog) DomEngine.addToLog( owner + " played " +card + " with "+ this);
        if (card.owner==null) {
          cardOwnerWasNull = true;
          card.owner=owner;
          //Procession trashed the Duration so it's no longer in play but it still needs to resolve its effect twice so we do it here
          card.resolveDuration();
        }
	    card.resolveDuration();
	    if (getName()==DomCardName.King$s_Court || getName()==DomCardName.Mastermind){
	      if (DomEngine.haveToLog) DomEngine.addToLog( owner + " played " +card + " with "+ this);
		  card.resolveDuration();
	    }
        if (cardOwnerWasNull)
            card.owner=null;
      }
      ArrayList<DomCard> theCardsToStayInPlay = new ArrayList<DomCard>();
      for (DomCard theCard : myDurationCards)
        if (theCard.mustStayInPlay())
            theCardsToStayInPlay.add(theCard);
      myDurationCards.clear();
      myDurationCards.addAll(theCardsToStayInPlay);
      if (myDurationCards.isEmpty())
          setDiscardAtCleanup(true);
      else
          setDiscardAtCleanup(false);
    }

    public DomCard getCardToMultiply( ) {
        DomCard theCardToPlay = null;
        Collections.sort(owner.getCardsInHand(), SORT_FOR_DISCARDING);
        DomCard thePerhapsCard = null;
        for (int i = 0;i<owner.getCardsInHand().size();i++) {
          DomCard theCard = owner.getCardsInHand().get( i );
          if (!theCard.wantsToBeMultiplied())
              continue;
          if (getName()==DomCardName.Procession && theCard.getName()==DomCardName.Fortress)
              return theCard;
          if (getName()==DomCardName.Procession && theCard.hasCardType(DomCardType.Duration))
              continue;
          if (theCard.hasCardType(DomCardType.Multiplier))
        	return theCard;
          if (theCard.getName()==DomCardName.Chariot_Race) //Chariot Race is useless when deck is empty so throne it first
              return theCard;
          if (theCard.getName()==DomCardName.Conspirator) //Conspirator instantly becomes a Grand Market with Throne Room
              return theCard;
          if (theCard.hasCardType(DomCardType.Action) && theCard.wantsToBePlayed()){
            thePerhapsCard = theCard;
        	if (theCard.hasCardType(DomCardType.Terminal)
                && (owner.getActionsAndVillagersLeft()>0 || owner.getCardsFromHand(DomCardType.Terminal).size()==owner.getCardsFromHand(DomCardType.Action).size())
        	    && (theCardToPlay == null ||theCard.getDiscardPriority(1)> theCardToPlay.getDiscardPriority(1))) {
              if (theCard.getName()!=DomCardName.Trading_Post && theCard.getName()!=DomCardName.Tactician)
                theCardToPlay = theCard;
        	} 
    		if (!theCard.hasCardType(DomCardType.Terminal) 
      	     && (theCardToPlay == null ||theCard.getDiscardPriority(1)> theCardToPlay.getDiscardPriority(1))){
               theCardToPlay = theCard;
       		}
          }
        }

        DomCard theNewCardToPlay = fixForKingsCourtRabble(theCardToPlay);
        if (theNewCardToPlay==null)
            theNewCardToPlay=theCardToPlay;
        if (theNewCardToPlay==null)
            theNewCardToPlay=thePerhapsCard;
        if (theNewCardToPlay!=null)
            theCardToPlay=theNewCardToPlay;
        return theCardToPlay;
    }

    private DomCard fixForKingsCourtRabble(DomCard theCardToPlay) {
        DomCard theNewCardToPlay = null;
        if (owner.getActionsAndVillagersLeft()==0 && owner.getCardsFromPlay(DomCardName.King$s_Court).size()==1 && theCardToPlay!=null && theCardToPlay.hasCardType(DomCardType.Card_Advantage) && theCardToPlay.hasCardType(DomCardType.Terminal)) {
            for (int i = 0;i<owner.getCardsInHand().size();i++) {
                DomCard theCard = owner.getCardsInHand().get( i );
                if (theCard.hasCardType(DomCardType.Action) && theCard.wantsToBePlayed()){
                    if (!theCard.hasCardType(DomCardType.Card_Advantage)
                            && (theNewCardToPlay == null ||theCard.getDiscardPriority(1)> theNewCardToPlay.getDiscardPriority(1))) {
                        theNewCardToPlay = theCard;
                    }
                }
            }
        }
        if (theCardToPlay!=null && owner.getDeckAndDiscardSize()==0 && theCardToPlay.hasCardType(DomCardType.Card_Advantage)) {
            for (int i = 0;i<owner.getCardsInHand().size();i++) {
                DomCard theCard = owner.getCardsInHand().get( i );
                if (theCard.hasCardType(DomCardType.Action) && theCard.wantsToBePlayed()){
                    if (!theCard.hasCardType(DomCardType.Card_Advantage)
                            && (theNewCardToPlay == null ||theCard.getDiscardPriority(1)> theNewCardToPlay.getDiscardPriority(1))) {
                        theNewCardToPlay = theCard;
                    }
                }
            }
        }
        return theNewCardToPlay;
    }

    @Override
    public int getPlayPriority() {
    	int theActionCount=0;
    	int theNonTerminalCount=0;
    	for (DomCard theCard : owner.getCardsInHand()) {
    		if (theCard==this)
    			continue;
    		if (theCard.getName()==DomCardName.King$s_Court
    		|| theCard.getName()==DomCardName.Throne_Room
            || theCard.getName()==DomCardName.Crown)
    			return 0;
            if (theCard.hasCardType(DomCardType.Action) && !(theCard instanceof DrawUntilXCardsCard))
            	theActionCount++;
            if (theCard.hasCardType(DomCardType.Action) && !theCard.hasCardType(DomCardType.Terminal))
                theNonTerminalCount++;
    	}
    	if (theActionCount==1 || theNonTerminalCount==1)
    		return 0;
        if (getName()==DomCardName.Procession && !owner.getCardsFromHand(DomCardName.Fortress).isEmpty())
            return owner.getCardsFromHand(DomCardName.Fortress).get(0).getPlayPriority()-1;
//        if (theActionCount>1 && !owner.getCardsFromHand(DomCardType.Card_Advantage).isEmpty() && owner.getDeckAndDiscardSize()>0)
//            return owner.getCardsFromHand(DomCardType.Card_Advantage).get(0).getPlayPriority()-1;
    	return super.getPlayPriority();
    }
    
    @Override
    public boolean wantsToBePlayed() {
    	int theActionCount=0;
    	for (DomCard theCard : owner.getCardsInHand()) {
    		if (theCard==this 
    		 || theCard.getName()==DomCardName.King$s_Court
             || theCard.getName()==DomCardName.Throne_Room
             || theCard.getName()==DomCardName.Crown
             || theCard.getName()==DomCardName.Procession)
    			continue;
            if (theCard.hasCardType(DomCardType.Action) && theCard.wantsToBeMultiplied())
            		//&& theCard.wantsToBePlayed())
            	theActionCount++;
    	}
    	return theActionCount>0;
    }

    public boolean hasDurationCard(DomCardName aName) {
        for (DomCard theCard : myDurationCards) {
            if (theCard.getName()==aName)
                return true;
        }
        return false;
    }

    public boolean hasCardAdvantageDuration() {
        for (DomCard theCard : myDurationCards) {
            if (theCard.hasCardType(DomCardType.Card_Advantage))
                return true;
        }
        return false;
    }

    @Override
    public boolean mustStayInPlay() {
        for (DomCard theDuration : myDurationCards) {
            if (theDuration.mustStayInPlay())
                return true;
        }
        return false;
    }

    public boolean areDurationsEmpty() {
        return myDurationCards.isEmpty();
    }

    public String getDurationsString() {
        if (myDurationCards.isEmpty())
            return null;
        String theTxt = "";
        String thePrefix="";
        for (DomCard theCard: myDurationCards) {
            theTxt+=thePrefix+theCard.getName().toHTML();
            thePrefix=", ";
        }
        return theTxt;
    }
}