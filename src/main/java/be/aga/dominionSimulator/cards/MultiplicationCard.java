package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class MultiplicationCard extends DomCard {

	private final ArrayList<DomCard> myDurationCards = new ArrayList<DomCard>();

	public MultiplicationCard(DomCardName aCardName) {
	  super(aCardName);
	}
	
	public void play(){
      DomCard theCardToMultiply = getCardToMultiply();
      if (theCardToMultiply == null)
    	return;
      //little fix for Tactician
      if (theCardToMultiply.hasCardType(DomCardType.Duration) && theCardToMultiply.getName()!=DomCardName.Tactician){
        myDurationCards.add(theCardToMultiply);
        setDiscardAtCleanup(false);
      }
      owner.removeCardFromHand(theCardToMultiply);
      owner.getCardsInPlay().add(theCardToMultiply);
      play(theCardToMultiply, 1);
      //little fix for Tactician
      if (theCardToMultiply.getName()!=DomCardName.Tactician){
          play(theCardToMultiply, 2);
	      if (getName()==DomCardName.King$s_Court) {
	        play(theCardToMultiply, 3);
	      }
      }
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
        cardOwnerWasNull = false;
    	if (DomEngine.haveToLog) DomEngine.addToLog( owner + " played " +card + " with "+ this);
        if (card.owner==null) {
          cardOwnerWasNull = true;
          card.owner=owner;
        }
	    card.resolveDuration();
	    if (getName()==DomCardName.King$s_Court){
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
        for (int i = 0;i<owner.getCardsInHand().size();i++) {
          DomCard theCard = owner.getCardsInHand().get( i );
          if (!theCard.wantsToBeMultiplied())
              continue;
          if (getName()==DomCardName.Procession && theCard.getName()==DomCardName.Fortress)
              return theCard;
          if (theCard.hasCardType(DomCardType.Multiplier))
        	return theCard;
          if (theCard.hasCardType(DomCardType.Action) && theCard.wantsToBePlayed()){
        	if (theCard.hasCardType(DomCardType.Terminal)
                && (owner.getActionsLeft()>0 || owner.getCardsFromHand(DomCardType.Terminal).size()==owner.getCardsFromHand(DomCardType.Action).size())
        	    && (theCardToPlay == null ||theCard.getDiscardPriority(1)> theCardToPlay.getDiscardPriority(1))) {
              if (theCard.getName()!=DomCardName.Trading_Post)
                theCardToPlay = theCard;
        	} 
    		if (!theCard.hasCardType(DomCardType.Terminal) 
      	     && (theCardToPlay == null ||theCard.getDiscardPriority(1)> theCardToPlay.getDiscardPriority(1))){
               theCardToPlay = theCard;
       		}
          }
        }
        if (theCardToPlay==null && !owner.getCardsFromHand(DomCardType.Action).isEmpty())
            theCardToPlay=owner.getCardsFromHand(DomCardType.Action).get(0);

        DomCard theNewCardToPlay = fixForKingsCourtRabble(theCardToPlay);
        if (theNewCardToPlay!=null)
            theCardToPlay=theNewCardToPlay;
        return theCardToPlay;
    }

    private DomCard fixForKingsCourtRabble(DomCard theCardToPlay) {
        DomCard theNewCardToPlay = null;
        if (owner.getActionsLeft()==0 && owner.getCardsFromPlay(DomCardName.King$s_Court).size()==1 && theCardToPlay!=null && theCardToPlay.hasCardType(DomCardType.Card_Advantage) && theCardToPlay.hasCardType(DomCardType.Terminal)) {
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
    	for (DomCard theCard : owner.getCardsInHand()) {
    		if (theCard==this)
    			continue;
    		if (theCard.getName()==DomCardName.King$s_Court
    		|| theCard.getName()==DomCardName.Throne_Room
            || theCard.getName()==DomCardName.Crown)
    			return 0;
            if (theCard.hasCardType(DomCardType.Action) && !(theCard instanceof DrawUntilXCardsCard))
            	theActionCount++;
    	}
    	if (theActionCount==1)
    		return 0;
        if (getName()==DomCardName.Procession && !owner.getCardsFromHand(DomCardName.Fortress).isEmpty())
            return owner.getCardsFromHand(DomCardName.Fortress).get(0).getPlayPriority()-1;
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
            if (theCard.hasCardType(DomCardType.Action)) 
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
        if (myDurationCards.isEmpty())
            return false;
        for (DomCard theDuration : myDurationCards) {
            if (theDuration.mustStayInPlay())
                return true;
        }
        return false;
    }

    public boolean areDurationsEmpty() {
        return myDurationCards.isEmpty();
    }
}