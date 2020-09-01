package be.aga.dominionSimulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;

import be.aga.dominionSimulator.cards.*;
import be.aga.dominionSimulator.enums.DomPhase;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import be.aga.dominionSimulator.enums.DomBotType;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class DomDeck extends EnumMap< DomCardName, ArrayList<DomCard> > {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2812359334315822796L;
    protected static final Logger LOGGER = Logger.getLogger( DomDeck.class );
    static {
        LOGGER.setLevel( DomEngine.LEVEL );
        LOGGER.removeAllAppenders();
        if (DomEngine.addAppender)
            LOGGER.addAppender(new ConsoleAppender(new SimpleLayout()) );
    }

    private ArrayList< DomCard > drawDeck = new ArrayList< DomCard >();
    private final ArrayList< DomCard > discardPile = new ArrayList< DomCard >();
    private ArrayList< DomCard > islandMat = new ArrayList< DomCard >();
    private ArrayList< DomCard > exileMat = new ArrayList< DomCard >();

    private DomPlayer owner;
	private ArrayList<DomCard> putAsideCards=new ArrayList<DomCard>();
    public static final int DISCARD = 0;
    public static final int TOP_OF_DECK = 1;
    public static final int HAND = 2;

    public DomDeck (DomPlayer aDomPlayer) {
      super( DomCardName.class );
      owner = aDomPlayer; 
      islandMat = new ArrayList<>();
      exileMat = new ArrayList<>();
    }

    public void shuffle() {
      owner.setKnownTopCards(0);
	  if (owner.getTypes().contains(DomBotType.RiffleShuffle) )
		doRiffleShuffle();
  	  else  
  		if (owner.getTypes().contains(DomBotType.ShuffleOverhand))
	  	  doOverhandShuffle();
  	    else
          Collections.shuffle( drawDeck );
      if (DomEngine.haveToLog) DomEngine.addToLog( owner + " shuffles deck" );
	  if (count(DomCardName.Stash)>0)
        handleStash();
      if (owner.hasBuiltProject(DomCardName.Star_Chart)) {
          handleStarChart();
      }

    }

    private void handleStarChart() {
        if (owner.isHumanOrPossessedByHuman()) {
            ArrayList<DomCardName> theCards = new ArrayList<>();
            for (DomCard theCard : drawDeck) {
                if (!theCards.contains(theCard.getName()))
                    theCards.add(theCard.getName());
            }
            DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Put on top?", theCards, "Don't put on top");
            if (theChosenCard != null) {
                for (DomCard theCard : drawDeck) {
                    if (theCard.getName() == theChosenCard) {
                        if (DomEngine.haveToLog) DomEngine.addToLog(owner + " puts " + theCard + " on top");
                        drawDeck.add(0, drawDeck.remove(drawDeck.indexOf(theCard)));
                        break;
                    }
                }
            }
        } else {
            DomCard theBestCard = drawDeck.get(0);
            for (DomCard theCard:drawDeck) {
                if (owner.getPhase()==DomPhase.Action) {
                    if (theCard.getDiscardPriority(owner.actionsLeft)> theBestCard.getDiscardPriority(owner.actionsLeft))
                        theBestCard = theCard;
                } else {
                    if (theCard.getDiscardPriority(10) > theBestCard.getDiscardPriority(10))
                        theBestCard = theCard;
                }
            }
            if (DomEngine.haveToLog) DomEngine.addToLog(owner + " puts " + theBestCard+ " on top");
            drawDeck.add(0,drawDeck.remove(drawDeck.indexOf(theBestCard)));
        }
    }

    private void handleStash() {
	  ArrayList<DomCard> theStashes= new ArrayList<DomCard>();
	  for (DomCard card : drawDeck){
		if (card.getName()==DomCardName.Stash){
		  theStashes.add(card);
		}
	  }
	  for (DomCard card : theStashes){
		drawDeck.remove(drawDeck.indexOf(card));
	  }
      if (owner.isHumanOrPossessedByHuman()) {
          if (owner.getDrawDeckSize() == 0) {
              for (DomCard card : theStashes) {
                  owner.putOnTopOfDeck(owner.removeCardFromHand(card));
              }
          } else {
              for (DomCard card : theStashes) {
                  ArrayList<String> theOptions = new ArrayList<String>();
                  for (int i = 0; i < owner.getDrawDeckSize() + 1; i++) {
                      if (i<owner.getDrawDeckSize() && drawDeck.get(i).getName()==DomCardName.Stash)
                        theOptions.add("Stash");
                      else
                        theOptions.add(i == 0 ? "Top" : i == owner.getDrawDeckSize() ? "Bottom" : "Here");
                  }
                  int theChoice = owner.getEngine().getGameFrame().askToSelectOption("Position?", theOptions, "Mandatory!");
                  owner.putInDeckAt(card, theChoice);
              }
          }
      } else {
          for (DomCard card : theStashes) {
              owner.putOnTopOfDeck(card);
          }
      }

    }

    private void doOverhandShuffle() {
      for (int j=0;j<2+Math.random()*4;j++){
        ArrayList<DomCard> newDeck = new ArrayList<DomCard>();
    	while (!drawDeck.isEmpty()) {
		    int theNumber = (int) (drawDeck.size()*Math.random()/3.0);
		    for (int i=theNumber;i>=0;i--)
			  newDeck.add(0,drawDeck.remove(i));
    	}
    	drawDeck=newDeck;
     	LOGGER.debug("Iteratie " +j);
        LOGGER.debug("-------------");
    	for (DomCard card : drawDeck){
    		LOGGER.debug(card.getName());
    	}
      }
	}

    private void doRiffleShuffle() {
        for (int j=0;j<2+Math.random()*4;j++){
          ArrayList<DomCard> newDeck = new ArrayList<DomCard>();
          ArrayList<DomCard> leftDeck = new ArrayList<DomCard>();
          ArrayList<DomCard> rightDeck = new ArrayList<DomCard>();
		  int theSplit = (int) (drawDeck.size()/2.0 + drawDeck.size()*Math.random()/10.0);
		  for (int i=0;i<theSplit;i++){
			leftDeck.add(drawDeck.remove(0));
		  }
		  while (!drawDeck.isEmpty())
			rightDeck.add(drawDeck.remove(0));
		  
      	  while (!leftDeck.isEmpty() || !rightDeck.isEmpty()) {
            int i=0;
      		double theRiffledCards = Math.random()*4;
  		    for (i=0;i<theRiffledCards && leftDeck.size()>0;i++){
  		      newDeck.add(leftDeck.remove(leftDeck.size()-1));
  		    }
      		theRiffledCards = Math.random()*4;
  		    for (i=0;i<theRiffledCards && rightDeck.size()>0;i++){
  		      newDeck.add(rightDeck.remove(rightDeck.size()-1));
  		    }
      	  }
      	  drawDeck=newDeck;
         	LOGGER.debug("Iteratie " );
            LOGGER.debug("-------------");
        	for (DomCard card : drawDeck){
        		LOGGER.debug(card.getName());
        	}
        }
  	}

    public ArrayList< DomCard > getTopCards( int aI ) {
    	owner.setKnownTopCards(owner.getKnownTopCards()-aI);
        ArrayList< DomCard > theTopCards = new ArrayList< DomCard >();
        for (int i=0;i<aI ;i++) {
          if (drawDeck.isEmpty()) {
            if (discardPile.isEmpty())
              return theTopCards;
            drawDeck.addAll( discardPile);
            discardPile.clear();
            shuffle();
          }
          theTopCards.add( drawDeck.remove(0) );
        }
        return theTopCards;
    }

    public int count( DomCardName aCardName ) {
      ArrayList< DomCard > theList = get(aCardName); 
      return theList==null ? 0 : theList.size();
    }

    public int count( DomCardType aCardType ) {
        int theCount = 0;
        for (DomCardName theCardName : keySet()) {
          if (theCardName==DomCardName.Estate && owner.isEstateTokenPlaced() && aCardType==DomCardType.Action)
              theCount+= get( theCardName ).size();
          if (theCardName.hasCardType( aCardType ))
            theCount+= get( theCardName ).size();
        }
        return theCount;
    }

    public double getTotalTreasure() {
        double theTotalMoney =0;
        for (DomCardName theCardName : keySet()) {
           theTotalMoney+= theCardName.hasCardType( DomCardType.Treasure ) ? theCardName.getCoinValue() * get(theCardName).size() : 0;
        }
        return theTotalMoney;
    }

    public void discard( DomCard aCard ) {
      if (owner.getPhase()== DomPhase.CleanUp && aCard.getName()==DomCardName.Hermit && owner.getBoughtCards().isEmpty()) {
          if (aCard.getShapeshifterCard() != null)
              owner.trash(aCard.getShapeshifterCard());
          else if (aCard.getEstateCard() != null) {
              owner.trash(aCard.getEstateCard());
          } else
              owner.trash(aCard);
          owner.gain(DomCardName.Madman);
      } else {
          if (aCard.getShapeshifterCard() != null)
              discardPile.add(aCard.getShapeshifterCard());
          else if (aCard.getEstateCard() != null) {
              discardPile.add(aCard.getEstateCard());
          } else
              discardPile.add(aCard);
          aCard.doWhenDiscarded();
      }
    }

    public void gain( DomCard aCard , int aLocation) {
		if (gainIfPossessed(aCard) || !addPhysicalCard(aCard))
		  return;
        if (owner.isHumanOrPossessedByHuman()
                && aCard.getName()!=DomCardName.Changeling
                && owner.getCurrentGame().countInSupply(DomCardName.Changeling)>0
                && aCard.getCost(owner.getCurrentGame()).customCompare(new DomCost(3,0))>=0
                && owner.getEngine().getGameFrame()!=null
                && owner.getEngine().getGameFrame().askPlayer("<html>Gain " + DomCardName.Changeling.toHTML() +" instead of " + aCard.getName().toHTML() + "?</html>", "Replace with Changeling?".toString())) {
            owner.returnToSupply(aCard);
            owner.gain(DomCardName.Changeling);
            return;
        }
        if (aCard.hasCardType(DomCardType.Action)
                && owner.hasBuiltProject(DomCardName.Innovation)
                && !owner.hasTriggeredInnovation()
                && (!owner.isHumanOrPossessedByHuman() || owner.getEngine().getGameFrame().askPlayer("<html>Set " + aCard.getName().toHTML() +" aside for Innovation?</html>", "Resolving " + DomCardName.Innovation.toString()))){
            if (DomEngine.haveToLog) DomEngine.addToLog(owner + " sets " + aCard + " aside to play with Innovation");
            owner.setInnovationTriggered(true);
            owner.play(aCard);
        } else {
            if (!fillsCargoShip(aCard, aLocation) && !handleGatekeeper(aCard)) {
                if (owner.count(DomCardName.Sleigh)>0 && !owner.getCardsFromHand(DomCardName.Sleigh).isEmpty()) {
                    if (((SleighCard)owner.getCardsFromHand(DomCardName.Sleigh).get(0)).wantsToReact(aCard)) {
                        owner.discardFromHand(owner.getCardsFromHand(DomCardName.Sleigh).get(0));
                        if (owner.getActionsAndVillagersLeft() > 0 && owner.getPhase() == DomPhase.Action && aCard.hasCardType(DomCardType.Action)) {
                            aLocation = HAND;
                        } else {
                            if (owner.getPhase() != DomPhase.Buy_BuyStuff && aCard.hasCardType(DomCardType.Treasure)) {
                                aLocation = HAND;
                            } else {
                                aLocation = TOP_OF_DECK;
                            }
                        }
                    }
                }
                if (aLocation == HAND && aCard.getName() != DomCardName.Villa && aCard.getName() != DomCardName.Ghost_Town && aCard.getName() != DomCardName.Guardian && aCard.getName() != DomCardName.Night_Watchman && aCard.getName() != DomCardName.Den_of_Sin) {
                    owner.getCardsInHand().add(aCard);
                    if (DomEngine.haveToLog) DomEngine.addToLog(owner + " gains a " + aCard + " in hand");
                }
                if (aLocation == TOP_OF_DECK && aCard.getName() != DomCardName.Villa && aCard.getName() != DomCardName.Ghost_Town && aCard.getName() != DomCardName.Guardian && aCard.getName() != DomCardName.Night_Watchman && aCard.getName() != DomCardName.Den_of_Sin) {
                    owner.putOnTopOfDeck(aCard);
                }
                if (aLocation == DISCARD && aCard.getName() != DomCardName.Villa && aCard.getName() != DomCardName.Ghost_Town && aCard.getName() != DomCardName.Guardian && aCard.getName() != DomCardName.Night_Watchman && aCard.getName() != DomCardName.Den_of_Sin) {
                    if (aCard.getName() == DomCardName.Nomad_Camp) {
                        owner.putOnTopOfDeck(aCard);
                    } else {
                        if (!owner.getCardsFromPlay(DomCardName.Royal_Seal).isEmpty() || !owner.getCardsFromPlay(DomCardName.Tracker).isEmpty()) {
                            if (owner.isHumanOrPossessedByHuman()) {
                                if (owner.getEngine().getGameFrame().askPlayer("<html>On Top of Deck: " + aCard.getName().toHTML() + "</html>", "Resolving Royal Seal"))
                                    owner.putOnTopOfDeck(aCard);
                                else
                                    discardPile.add(0, aCard);
                            } else {
                                if (aCard.getDiscardPriority(1) >= 16)
                                    owner.putOnTopOfDeck(aCard);
                                else
                                    discardPile.add(0, aCard);
                            }
                        } else {
                            if (owner.isTravellingFairActive()) {
                                if (owner.isHumanOrPossessedByHuman()) {
                                    if (owner.getEngine().getGameFrame().askPlayer("<html>On Top of Deck: " + aCard.getName().toHTML() + "</html>", "Resolving Travelling Fair"))
                                        owner.putOnTopOfDeck(aCard);
                                    else
                                        discardPile.add(0, aCard);
                                } else {
                                    if (aCard.getDiscardPriority(1) > DomCardName.Copper.getDiscardPriority(1) || (aCard.getName() == DomCardName.Copper && owner.getDrawDeckSize() < 8))
                                        owner.putOnTopOfDeck(aCard);
                                    else
                                        discardPile.add(0, aCard);
                                }
                            } else {
                                discardPile.add(0, aCard);
                            }
                        }
                    }
                }
            }
        }
        if (aCard.hasCardType(DomCardType.Victory)) {
            if (owner.isCardInPlay(DomCardName.Groundskeeper)) {
                for (DomCard theKeeper : owner.getCardsFromPlay(DomCardName.Groundskeeper)) {
                    if (DomEngine.haveToLog) DomEngine.addToLog(owner + " resolves " + theKeeper);
                    owner.addVP(1);
                }
            }
            if (owner.getCurrentGame().getBoard().isLandmarkActive(DomCardName.Aqueduct)) {
                int theVP = owner.getCurrentGame().getBoard().removeVPFrom(DomCardName.Aqueduct, owner.getCurrentGame().getBoard().countVPon(DomCardName.Aqueduct));
                if (theVP>0) {
                    if (DomEngine.haveToLog) DomEngine.addToLog(owner + " takes VP from " + DomCardName.Aqueduct.toHTML());
                    owner.addVP(theVP);
                }
            }
            if (owner.getCurrentGame().getBoard().isLandmarkActive(DomCardName.Battlefield) && owner.getTurns()>0 ) {
                int theVP = owner.getCurrentGame().getBoard().removeVPFrom(DomCardName.Battlefield, 2);
                if (theVP>0) {
                    if (DomEngine.haveToLog) DomEngine.addToLog(owner + " takes VP from " + DomCardName.Battlefield.toHTML());
                    owner.addVP(theVP);
                }
            }
            for (DomPlayer theOpp : owner.getOpponents()) {
                if (theOpp.hasBuiltProject(DomCardName.Road_Network)) {
                    if (DomEngine.haveToLog) DomEngine.addToLog(theOpp + " has built " + DomCardName.Road_Network.toHTML());
                    theOpp.drawCards(1);
                }
                if (theOpp.count(DomCardName.Black_Cat)>0) {
                    while (!theOpp.getCardsFromHand(DomCardName.Black_Cat).isEmpty()) {
                        if (!((Black_CatCard)theOpp.getCardsFromHand(DomCardName.Black_Cat).get(0)).wantsToReact())
                            break;
                        theOpp.play(theOpp.removeCardFromHand(theOpp.getCardsFromHand(DomCardName.Black_Cat).get(0)));
                    }
                }
            }
        }
        if (aCard.hasCardType(DomCardType.Action)) {
          if (owner.getCurrentGame().getBoard().isLandmarkActive(DomCardName.Defiled_Shrine)) {
              owner.getCurrentGame().getBoard().moveVPFromTo(aCard.getName(), DomCardName.Defiled_Shrine);
          }
          if (owner.hasBuiltProject(DomCardName.Academy)) {
              owner.addVillagers(1);
          }
        }
        if (aCard.hasCardType(DomCardType.Treasure) && owner.hasBuiltProject(DomCardName.Guildhall))
            owner.addCoffers(1);
        if (aCard.getName()==DomCardName.Province && owner.getCurrentGame().checkForMountainPass()) {
           owner.getCurrentGame().triggerAuction();
        }
        DomCard theDuplicate = owner.getFromTavernMat(DomCardName.Duplicate);
        if (theDuplicate!=null) {
            while (theDuplicate!=null && ((DuplicateCard)theDuplicate).wantsToGain(aCard)) {
                owner.getCardsInPlay().add(owner.removeFromTavernMat(theDuplicate));
                if (DomEngine.haveToLog) DomEngine.addToLog( owner + " calls " + theDuplicate +" from the tavern mat");
                owner.gain(owner.getCurrentGame().takeFromSupply(aCard.getName()));
                theDuplicate=owner.getFromTavernMat(DomCardName.Duplicate);
            }
        }
        if (aCard.getCoinCost(owner.getCurrentGame())>=4) {
            for (int i=0;i<owner.getLiveryTriggers();i++)
              owner.gain(DomCardName.Horse);
        }
        ArrayList<DomCard> cardsToDiscard = new ArrayList<>();
        for (DomCard theCard : exileMat) {
            if (theCard==aCard) {
                //fix for Gatekeeper which doesn't want to trigger discard from exile
                cardsToDiscard.clear();
                break;
            }
            if (aCard.getName() == theCard.getName() && theCard.getDiscardPriority(1)>=DomCardName.Silver.getDiscardPriority(1))
                cardsToDiscard.add(theCard);
        }
        for (DomCard theCard : cardsToDiscard) {
            discardPile.add(exileMat.remove(exileMat.indexOf(theCard)));
            if (DomEngine.haveToLog) DomEngine.addToLog(owner + " discards " + theCard + " from Exile");
            if (owner.hasInvestedIn(theCard.getName())) {
                owner.removeFromInvestments(theCard);
            }
        }
        aCard.doWhenGained();
    }

    private boolean handleGatekeeper(DomCard aCard) {
        if (!owner.getCurrentGame().isInKingDom(DomCardName.Gatekeeper))
            return false;
        if (!aCard.hasCardType(DomCardType.Action) && !aCard.hasCardType(DomCardType.Treasure))
            return false;
        for (DomPlayer theOpp : owner.getOpponents()) {
            for (DomCard theGatekeepr : theOpp.getCardsFromPlay(DomCardName.Gatekeeper)) {
                if (((GatekeeperCard) theGatekeepr).hasProtectedOpponent(owner)) {
                    if (DomEngine.haveToLog) DomEngine.addToLog(owner + " is protected from " + theGatekeepr);
                    return false;
                } else {
                    if (!owner.hasInExile(aCard.getName())) {
                        if (DomEngine.haveToLog) DomEngine.addToLog(theGatekeepr + " from player " + theOpp + " attacks!");
                        owner.exile(aCard);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean fillsCargoShip(DomCard aCard, int aLocation) {
        if (owner.getCurrentGame().getActivePlayer()!=owner)
            return false;
        if (owner.getCargoShipTriggers()>0) {
                if (owner.isHumanOrPossessedByHuman()) {
                    if (owner.getEngine().getGameFrame().askPlayer("<html>Put " + aCard.getName().toHTML() +" on " + DomCardName.Cargo_Ship.toHTML() + "?</html>", "Resolving " + DomCardName.Cargo_Ship.toHTML())){
                        owner.addCargoCard(aCard);
                        owner.removeCargoShipTrigger();
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (aLocation != HAND && aCard.getName() != DomCardName.Villa && aCard.getName() != DomCardName.Ghost_Town && aCard.getName() != DomCardName.Guardian && aCard.getName() != DomCardName.Night_Watchman && aCard.getName() != DomCardName.Den_of_Sin
                            && aCard.getDiscardPriority(1)>DomCardName.Copper.getDiscardPriority(1)) {
                        owner.addCargoCard(aCard);
                        owner.removeCargoShipTrigger();
                        return true;
                    } else {
                        return false;
                    }
                }
        }
        return false;
    }

    public boolean addPhysicalCard( DomCard aCard ) {
        //TODO Trader might conflict with Watchtower ????
        if (aCard.getName() != DomCardName.Silver && owner.count(DomCardName.Trader) > 0 && owner.usesTrader(aCard))
            return false;

        if (aCard.hasCardType(DomCardType.Action)) {
            for (DomPlayer thePlayer : owner.getOpponents()) {
                if (thePlayer.hasInvestments() && thePlayer.hasInvestedIn(aCard.getName())) {
                    if (DomEngine.haveToLog)
                        DomEngine.addToLog(thePlayer + " has Invested in " + aCard.getName().toHTML());
                    thePlayer.drawCards(thePlayer.countInvestmentsIn(aCard.getName()) * 2);
                }
            }
        }
        if (owner.getCurrentGame().getBoard().get(DomCardName.Falconer) != null && aCard.countTypes() > 1) {
            for (DomPlayer player : owner.getCurrentGame().getPlayers()) {
                if (!player.getCardsFromHand(DomCardName.Falconer).isEmpty()) {
                    if (player.isHumanOrPossessedByHuman()) {
                        owner.getCurrentGame().setChangedForced();
                        while (!player.getCardsFromHand(DomCardName.Falconer).isEmpty() && owner.getEngine().getGameFrame().askPlayer("<html>Play " + DomCardName.Falconer.toHTML() + "?</html>", "Resolving " + DomCardName.Falconer.toString()))
                            player.play(player.removeCardFromHand(player.getCardsFromHand(DomCardName.Falconer).get(0)));
                    } else {
                        while (!player.getCardsFromHand(DomCardName.Falconer).isEmpty() && player.getCardsFromHand(DomCardName.Falconer).get(0).wantsToBePlayed()) {
                            player.play(player.removeCardFromHand(player.getCardsFromHand(DomCardName.Falconer).get(0)));
                        }
                    }
                }
            }
        }
        if (owner.count(DomCardName.Watchtower) > 0 && owner.usesWatchtower(aCard))
            return false;

        if (!containsKey(aCard.getName())) {
            put(aCard.getName(), new ArrayList<DomCard>());
        }
        get(aCard.getName()).add(aCard);
        aCard.setOwner(owner);
        owner.addCardGainedLastTurn(aCard.getName());
        if (owner.getCurrentGame().getBoard().isLandmarkActive(DomCardName.Labyrinth) && owner == owner.getCurrentGame().getActivePlayer()) {
            if (owner.getCardsGainedLastTurn().size() == 2) {
                int theVP = owner.getCurrentGame().getBoard().removeVPFrom(DomCardName.Labyrinth, 2);
                if (theVP > 0) {
                    if (DomEngine.haveToLog)
                        DomEngine.addToLog(owner + " gains 2nd card this turn and triggers " + DomCardName.Labyrinth.toHTML());
                    owner.addVP(theVP);
                }
            }
        }
        if (owner.count(DomCardName.Sheepdog) > 0){
            while (!owner.getCardsFromHand(DomCardName.Sheepdog).isEmpty()) {
                if (!((SheepdogCard) owner.getCardsFromHand(DomCardName.Sheepdog).get(0)).wantsToReact())
                    break;
                owner.play(owner.removeCardFromHand(owner.getCardsFromHand(DomCardName.Sheepdog).get(0)));
            }
        }
        return true;
    }

    /**
     * @param aType
     * @return
     */
    public ArrayList< DomCard > revealUntilType( DomCardType aType ) {
        ArrayList< DomCard > theTopCards = new ArrayList< DomCard >();
        int theTotalDeckSize = getDeckAndDiscardSize();
        for (int i = 0; i< theTotalDeckSize;i++){
          DomCard theCard = getTopCard();
          theTopCards.add(theCard);
          if (DomEngine.haveToLog) DomEngine.addToLog( owner + " reveals " + theCard );
          if (theCard.hasCardType( aType ))
            break;
        }
        for (DomCard theCard:theTopCards){
            if (theCard.getName()==DomCardName.Patron)
                theCard.react();
        }
        return theTopCards;
    }

    /**
     * 
     */
    public void discardTopCardFromDeck() {
      DomCard theCard = null;
      if (drawDeck.isEmpty()) {
        if (DomEngine.haveToLog) DomEngine.addToLog( owner + " has no cards in the deck to discard");
        return;
      }
      theCard = getTopCard();
      if (DomEngine.haveToLog) DomEngine.addToLog( owner + " discards " + theCard + " from the top of his deck");
      discard( theCard);
    }

    private boolean gainIfPossessed(DomCard aCard) {
        if (owner.possessor!=null) {
          if (DomEngine.haveToLog) 
            DomEngine.addToLog( owner + " is Possessed by " + owner.possessor + " who will gain " + aCard);
          owner.possessor.gain(aCard);
          return true;
        }
        return false;
	}

    public ArrayList< DomCard > revealTopCards( int aI ) {
        ArrayList< DomCard > theTopX = new ArrayList< DomCard >();
        int theTotalDeckSize = drawDeck.size()+discardPile.size();
        for (int i = 0; i<aI && i< theTotalDeckSize;i++){
          theTopX.add(getTopCard());
        }
        if (DomEngine.haveToLog) {
            if (theTopX.isEmpty()) {
              DomEngine.addToLog( owner + " reveals nothing" );
            } else {
              DomEngine.addToLog( owner + " reveals " + theTopX );
            }
        }
        for (DomCard theCard:theTopX){
            if (theCard.getName()==DomCardName.Patron)
                theCard.react();
        }
        return theTopX;
    }

    /**
     * @return
     */
    public DomCard getTopCard() {
      return getTopCards( 1 ).get( 0 );
    }

    /**
     * 
     */
    public int countVictoryPoints() {
      int count = 0; 
      for (DomCardName theCardName : keySet()) {
        if (theCardName.hasCardType( DomCardType.Victory) || theCardName.hasCardType( DomCardType.Curse )) {
          count += get( theCardName ).size()* theCardName.getVictoryValue(owner);    
        }
      }
      count+= owner.getAllFromTavernMat(DomCardName.Distant_Lands).size()*4;

      return count;
    }

    /**
     * @param aRemove
     */
    public void trash( DomCard aRemove ) {
      if (owner.possessor!=null && aRemove.getName()!= DomCardName.Fortress) {
    	putCardAside(aRemove);
      } else {
	      try {
	        get(aRemove.getName()).remove( aRemove );
	        aRemove.owner=null;
	      } catch ( Exception e ) {
	        LOGGER.error("Problem when trashing " + aRemove);
	      }
      }
    }

    private void putCardAside(DomCard aRemove) {
      if (DomEngine.haveToLog) DomEngine.addToLog( owner + " puts aside "+aRemove );
	  putAsideCards.add(aRemove);
	}

	/**
     * 
     */
    public void showContents() {
        if (!DomEngine.haveToLog)
          return;
            
        StringBuilder theMessage=null;
        for (DomCardName theCardName : keySet()) {
          if (get( theCardName ).size() >0) {
            if (theCardName==DomCardName.Distant_Lands)
                continue;
            if (theMessage == null) {
              theMessage=new StringBuilder();
//            	.append( owner + " shows this deck (");
              theMessage.append("&nbsp;&nbsp;&nbsp;").append(countAllCards()).append(" cards : ["); 
            } else {
              theMessage.append( ", " );
            }
            theMessage.append( get( theCardName ).size() ).append( " " ).append( theCardName.toHTML() );
            int theVP = theCardName.getVictoryValue(owner);
            if (theVP!=0)
                theMessage.append(" (" + theVP*get( theCardName ).size() +"&#x25BC;)");
          }
        }
        //special handling for Distant Lands
        if (get(DomCardName.Distant_Lands)!=null) {
            int theCount = 0;
            for (DomCard theCard : get(DomCardName.Distant_Lands)) {
                theCount += theCard.getVictoryValue();
            }
            if (theCount > 0) {
                theMessage.append( ", " );
                theMessage.append(get(DomCardName.Distant_Lands).size()).append(" ").append(DomCardName.Distant_Lands.toHTML());
                theMessage.append(" (" + theCount + "&#x25BC;)");
            }
        }
        theMessage.append( "]" );
        
        DomEngine.addToStartOfLog(theMessage.toString());
    }

    /**
     * @return
     */
    public int countAllCards() {
        int theCount=0;
        for (DomCardName theName : keySet()){
            theCount+=get(theName).size();
        }
        return theCount;
    }

    /**
     * @return
     */
    public int getTotalMoney() {
        int theTotal = 0;
        for (DomCardName theCardName : keySet()) {
          theTotal += get(theCardName).size() * theCardName.getCoinValue();
        }
        return theTotal;
    }

    /**
     */
    public void putOnTopOfDeck( DomCard aCard ) {
    	owner.setKnownTopCards(owner.getKnownTopCards()+1);
       drawDeck.add(0, aCard );
    }

    /**
     * @param aCard
     */
    public void removePhysicalCard( DomCard aCard ) {
      get(aCard.getName()).remove( aCard );
    }

    /**
     * @return
     */
    public int getDeckAndDiscardSize() {
      return drawDeck.size()+discardPile.size();
    }

    public void returnCardsFromIslandMat() {
        if (DomEngine.haveToLog) DomEngine.addToLog( owner + " returns all cards from the Island Mat: " + getIslandMat() );
        for (DomCard theCard : islandMat) {
            discardPile.add(theCard);
        }
        islandMat.clear();
    }

    public void returnCardsFromExileMat() {
        if (DomEngine.haveToLog) DomEngine.addToLog( owner + " returns all cards from the Exile Mat: " + getExileMat() );
        for (DomCard theCard : exileMat) {
            discardPile.add(theCard);
        }
        exileMat.clear();
    }

    /**
     * @param aCard
     */
    public void moveToIslandMat( DomCard aCard ) {
      islandMat.add( aCard );
    }

    public ArrayList< DomCard > getIslandMat() {
      return islandMat;
    }

    public ArrayList< DomCard > getExileMat() {
        return exileMat;
    }

    /**
     * @return
     */
    public ArrayList< DomCard > collectAllCards() {
        ArrayList< DomCard > theCards = new ArrayList< DomCard >();
        for (DomCardName theCardName : keySet()) {
          theCards.addAll( get( theCardName ));    
        }
        return theCards;
    } 

	public void putDeckInDiscard() {
        if (DomEngine.haveToLog) DomEngine.addToLog( owner + " discards entire deck" );
    	discardPile.addAll(drawDeck);
		drawDeck.clear();
		owner.setKnownTopCards(0);
	}

	public int countDifferentCards() {
    	ArrayList<DomCardName> theSingleCards = new ArrayList<DomCardName>();
		int theCount=theSingleCards.size();
		for (DomCardName theCardName : keySet()) {
	      theCount+=get(theCardName).isEmpty()? 0 : 1;
		}
		return theCount;
	}
	
	public DomCard getBottomCard(){
	   if (drawDeck.isEmpty())
		   return null;
	   return drawDeck.remove(drawDeck.size()-1);	
	}

	public void putCardOnBottomOfDeck(DomCard theBottomCard) {
		drawDeck.add(theBottomCard);
	}

    public void putSecondFromTop(DomCard card) {
        owner.setKnownTopCards(2);
        drawDeck.add(1,card);
    }

	public ArrayList<DomCard> getPutAsideCards() {
		ArrayList<DomCard> thePutAsideCards = putAsideCards;
		putAsideCards=new ArrayList<DomCard>();
		return thePutAsideCards;
	}

	public ArrayList<DomCard> revealUntilCost(int aCost) {
        ArrayList< DomCard > theTopCards = new ArrayList< DomCard >();
        int theTotalDeckSize = getDeckAndDiscardSize();
        for (int i = 0; i< theTotalDeckSize;i++){
          DomCard theCard = getTopCard();
          theTopCards.add(theCard);
          if (DomEngine.haveToLog) DomEngine.addToLog( owner + " reveals " + theCard );
          if (theCard.getCoinCost(owner.getCurrentGame())>=aCost)
            break;
        }
        for (DomCard theCard:theTopCards){
            if (theCard.getName()==DomCardName.Patron)
                theCard.react();
        }
        return theTopCards;
	}

	public DomCardName getMostLikelyCardOnTop() {
		if (getDeckAndDiscardSize()==0)
			return null;
		if (drawDeck.isEmpty()){
		  drawDeck.addAll(discardPile);
		  discardPile.clear();
		}
		if (owner.getKnownTopCards()>0)
			return drawDeck.get(0).getName();
		shuffle();
		DomCardName theMostLikelyCard=null;
		EnumMap<DomCardName, Integer> theCounts = new EnumMap<DomCardName, Integer>(DomCardName.class);
		for (DomCard card : drawDeck){
		  if (theCounts.get(card.getName())==null){
			  theCounts.put(card.getName(),1);
		  } else {
			  theCounts.put(card.getName(),theCounts.get(card.getName())+1);
		  }
		}
		for (DomCardName cardName : theCounts.keySet()){
			if (theMostLikelyCard==null
	           || theCounts.get(cardName)>theCounts.get(theMostLikelyCard))
			  theMostLikelyCard=cardName;
		}
		return theMostLikelyCard;
	}

    public DomCardName getMostWantedCardOnTop() {
        if (getDeckAndDiscardSize()==0)
            return null;
        if (drawDeck.isEmpty()){
            drawDeck.addAll(discardPile);
            discardPile.clear();
        }
        if (owner.getKnownTopCards()>0)
            return drawDeck.get(0).getName();
        shuffle();
        DomCard theMostWantedCard=null;
        for (DomCard card : drawDeck){
            if (theMostWantedCard==null || card.getDiscardPriority(1)>theMostWantedCard.getDiscardPriority(1))
                theMostWantedCard=card;
        }
        return theMostWantedCard.getName();
    }

    public ArrayList<DomCard> revealUntilVictoryOrCurse() {
        ArrayList< DomCard > theTopCards = new ArrayList< DomCard >();
        int theTotalDeckSize = getDeckAndDiscardSize();
        for (int i = 0; i< theTotalDeckSize;i++){
          DomCard theCard = getTopCard();
          theTopCards.add(theCard);
          if (DomEngine.haveToLog) DomEngine.addToLog( owner + " reveals " + theCard );
          if (theCard.hasCardType( DomCardType.Victory) || theCard.hasCardType(DomCardType.Curse))
            break;
        }
        for (DomCard theCard:theTopCards){
            if (theCard.getName()==DomCardName.Patron)
                theCard.react();
        }
        return theTopCards;
	}

	public ArrayList<DomCard> revealUntilActionOrTreasure() {
        ArrayList< DomCard > theTopCards = new ArrayList< DomCard >();
        int theTotalDeckSize = getDeckAndDiscardSize();
        for (int i = 0; i< theTotalDeckSize;i++){
          DomCard theCard = getTopCard();
          theTopCards.add(theCard);
          if (DomEngine.haveToLog) DomEngine.addToLog( owner + " reveals " + theCard );
          if (theCard.hasCardType( DomCardType.Action) || theCard.hasCardType(DomCardType.Treasure))
            break;
        }
        for (DomCard theCard:theTopCards){
            if (theCard.getName()==DomCardName.Patron)
                theCard.react();
        }
        return theTopCards;
	}

	public boolean drawPileHasCard(DomCardName aCardName) {
		for (DomCard card : drawDeck)
			if (card.getName()== aCardName)
				return true;
		return false;
	}

	public int countInDrawAndDiscard(DomCardName aCardName) {
		int theCount=0;
		for (DomCard card : drawDeck)
			theCount+=card.getName()== aCardName ? 1 : 0;
		for (DomCard card : discardPile)
			theCount+=card.getName()== aCardName ? 1 : 0;
		return theCount;
	}

	public void gain(DomCard aCard) {
		gain(aCard,DISCARD);
	}

	public void addPhysicalCardWhenNotGained(DomCard aCard) {
		//cards that were passed are not considered 'gained' so can not be watchtowered...
        if (!containsKey( aCard.getName() )) 
          put(aCard.getName(), new ArrayList< DomCard >());
        get(aCard.getName()).add( aCard );
        aCard.setOwner(owner);
	}

	public ArrayList<DomCard> removeCardsFromDiscard(DomCardName aCardName) {
		ArrayList<DomCard> theCards = new ArrayList<DomCard>();
		for (DomCard theCard : discardPile) {
			if (theCard.getName()==aCardName) 
				theCards.add(theCard);
		}
		for (DomCard theCard : theCards) {
		  discardPile.remove(theCard);
		}
		return theCards;
	}

	public int getDrawDeckSize() {
		return drawDeck.size();
	}

	public ArrayList<DomCard> getDiscardPile() {
		return discardPile;
	}
	@Override
	public String toString() {
		StringBuilder theString= new StringBuilder();
		for (DomCardName cardName : keySet()){
			theString.append(cardName + "[" +get(cardName).size()+"]");
		}
		return theString.toString();
	}

	public DomCard lookAtTopCard() {
		return drawDeck.get(0);
	}

	public boolean checkForcedStart() {
		if (owner.getforcedStart()==0)
		  return true;
		int theCopperCount = 0;
		for (int i=0;i<5;i++){
		  theCopperCount+=drawDeck.get(i).getName()==DomCardName.Copper ? 1 : 0;
		}
		if ((theCopperCount==2 ||theCopperCount==5)
		  && owner.getforcedStart()==52)
		  return true;
		if ((theCopperCount==3 ||theCopperCount==4)
	 	  && owner.getforcedStart()==43)
		  return true;
		return false;
	}

    public DomCardName getMostLikelyCrappyCardForDoctor() {
        if (getDeckAndDiscardSize()==0)
            return null;
        if (drawDeck.isEmpty()){
            drawDeck.addAll(discardPile);
            discardPile.clear();
            shuffle();
        }
        DomCardName theMostLikelyCard=null;
        EnumMap<DomCardName, Integer> theCounts = new EnumMap<DomCardName, Integer>(DomCardName.class);
        int theMultiplier = 1;
        for (DomCard card : drawDeck){
            if (card.getTrashPriority()<=DomCardName.Copper.getTrashPriority(owner)) {
                if (card.getName() == DomCardName.Curse) {
                    theMultiplier = 5;
                }
                if (card.hasCardType(DomCardType.Victory)) {
                    theMultiplier = 3;
                }
                if (theCounts.get(card.getName()) == null) {
                    theCounts.put(card.getName(), theMultiplier);
                } else {
                    theCounts.put(card.getName(), theCounts.get(card.getName()) + theMultiplier);
                }
            }
        }
        for (DomCardName cardName : theCounts.keySet()){
            if (theMostLikelyCard==null
                    || theCounts.get(cardName)>theCounts.get(theMostLikelyCard))
                theMostLikelyCard=cardName;
        }
        if (theMostLikelyCard==null)
            return DomCardName.Copper;
        return theMostLikelyCard;
    }

    public ArrayList<DomCard> revealUntilVictoryCardNotNamed(DomCardName theNamedCard) {
        ArrayList< DomCard > theTopCards = new ArrayList< DomCard >();
        int theTotalDeckSize = getDeckAndDiscardSize();
        for (int i = 0; i< theTotalDeckSize;i++){
            DomCard theCard = getTopCard();
            theTopCards.add(theCard);
            if (DomEngine.haveToLog) DomEngine.addToLog( owner + " reveals " + theCard );
            if (theCard.hasCardType( DomCardType.Victory) && theCard.getName()!=theNamedCard)
                break;
        }
        for (DomCard theCard:theTopCards){
            if (theCard.getName()==DomCardName.Patron)
                theCard.react();
        }
        return theTopCards;
    }

    public ArrayList<DomCard> getDrawOrAndDiscardDeck() {
        ArrayList<DomCard> theCards = new ArrayList<DomCard>();
        if (drawDeck.size()<3) {
            theCards.addAll(discardPile);
        }
        theCards.addAll(drawDeck);
        return theCards;
    }

    public ArrayList<DomCard> revealUntilThreeOfCardNotNamed(DomCardName card) {
        int theCount=0;
        ArrayList<DomCard> theCards = new ArrayList<DomCard>();
        ArrayList<DomCard> theCardsToDiscard = new ArrayList<DomCard>();
        if (getDeckAndDiscardSize()==0)
            return theCards;
        DomCard theTopCard = getTopCard();
        while (theCount<3 && theTopCard!=null) {
            if (DomEngine.haveToLog) DomEngine.addToLog( owner + " reveals " +  theTopCard);
            if (theTopCard.getName()==DomCardName.Patron)
                theTopCard.react();
            if (theTopCard.getName()==card) {
                theCardsToDiscard.add(theTopCard);
            } else {
                theCards.add(theTopCard);
                theCount++;
            }
            if (theCount==3)
                break;
            ArrayList<DomCard> theTopCards = getTopCards(1);
            if (theTopCards.isEmpty())
                theTopCard=null;
            else
                theTopCard=theTopCards.get(0);
        }
        owner.discard(theCardsToDiscard);
        return theCards;
    }

    public ArrayList<DomCard> getAllCards() {
        ArrayList<DomCard> theCards = new ArrayList<DomCard>();
        theCards.addAll(drawDeck);
        theCards.addAll(discardPile);
        return theCards;
    }

    public DomCard removeFromDiscard(DomCardName cardName) {
        new ArrayList<DomCard>();
        for (DomCard theCard : discardPile) {
            if (theCard.getName()==cardName) {
                discardPile.remove(theCard);
                return theCard;
            }
        }
        return null;
    }

    public int countSingletonCards() {
        int theCount=0;
        for (DomCardName theCard : keySet()) {
            if (get(theCard).size()==1)
                theCount++;
        }
        return theCount;
    }

    public ArrayList<DomCard> removeAllCardsFromDiscardAndDeck() {
        ArrayList<DomCard> theCards = new ArrayList<DomCard>();
        theCards.addAll(discardPile);
        discardPile.clear();
        theCards.addAll(drawDeck);
        drawDeck.clear();
        return theCards;
    }

    public void addAllToDeck(ArrayList<DomCard> cards) {
        drawDeck.addAll(cards);
    }

    public DomCard getTopOfDiscard() {
        if (discardPile.isEmpty())
            return null;
        return discardPile.get(0);
    }

    public void forcedAdd(DomCard aCard) {
        if (!containsKey( aCard.getName() )) {
            put(aCard.getName(), new ArrayList< DomCard >());
        }
        get(aCard.getName()).add( aCard );
        aCard.setOwner(owner);
        discardPile.add(aCard);
    }

    public DomCard forcedRemove(DomCard aCard) {
        get(aCard.getName()).remove( aCard );
        aCard.setOwner(null);
        discardPile.remove(aCard);
        return aCard;
    }

    public DomCard peekAtTopCard() {
        if (drawDeck.isEmpty())
            return null;
        return drawDeck.get(0);
    }

    public void putInDeckAt(DomCard domCard, int theChoice) {
        drawDeck.add(theChoice,domCard);
    }

    public void removeFromDiscard(DomCard card) {
        discardPile.remove(card);
    }

    public void justAddToDiscard(DomCard card) {
	    if (DomEngine.haveToLog)
	        DomEngine.addToLog(owner +" adds " + card.getName().toHTML()+" to discard");
        discardPile.add(card);
    }

    public void addHandToDiscardPile() {
        while (!owner.getCardsInHand().isEmpty())
            discard(owner.getCardsInHand().remove(0));
    }

    public boolean drawDeckHasJunkLeft() {
	    for (DomCard theCard : drawDeck) {
           if (theCard.getTrashPriority()<=DomCardName.Copper.getTrashPriority())
               return true;
        }
        return false;
    }

    public void moveToExileMat(DomCard domCard) {
        exileMat.add(domCard);
    }

    public void discardAllFromExileMat(DomCardName cardName) {
        ArrayList<DomCard> cardsToDiscard = new ArrayList<>();
        for (DomCard theCard : exileMat) {
            if (cardName == theCard.getName())
                cardsToDiscard.add(theCard);
        }
        for (DomCard theCard : cardsToDiscard) {
            owner.discard(exileMat.remove(exileMat.indexOf(theCard)));
            if (DomEngine.haveToLog) DomEngine.addToLog(owner + " discards " + theCard + " from Exile");
        }
    }
}