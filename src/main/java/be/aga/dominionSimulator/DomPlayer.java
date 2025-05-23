package be.aga.dominionSimulator;

import java.awt.*;
import java.util.*;

import be.aga.dominionSimulator.cards.*;
import be.aga.dominionSimulator.enums.*;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import javax.swing.*;

/**
 * Represents a single player in a simulated game.
 */
public class DomPlayer extends Observable implements Comparable<DomPlayer> {
    protected static final Logger LOGGER = Logger.getLogger(DomPlayer.class);

    static {
        LOGGER.setLevel(DomEngine.LEVEL);
        LOGGER.removeAllAppenders();
        if (DomEngine.addAppender)
            LOGGER.addAppender(new ConsoleAppender(new SimpleLayout()));
    }

    final private ArrayList<DomBuyRule> buyRules = new ArrayList<DomBuyRule>();
    final private ArrayList<DomBuyRule> prizeBuyRules = new ArrayList<DomBuyRule>();
    private EnumMap<DomCardName, DomPlayStrategy> playStrategies = new EnumMap<DomCardName, DomPlayStrategy>(DomCardName.class);
    private String[] keywords = null;

    private DomDeck deck = new DomDeck(this);
    private ArrayList<DomCard> cardsInPlay = new ArrayList<DomCard>();
    private ArrayList<DomCard> cardsInHand = new ArrayList<DomCard>();
    private ArrayList<DomCard> nativeVillageMat = new ArrayList<DomCard>();
    public ArrayList<DomCard> horseTradersPile = new ArrayList<DomCard>();
    private ArrayList<DomCard> cardsToSummon = new ArrayList<DomCard>();
    private ArrayList<DomCard> cardsToReap = new ArrayList<DomCard>();
    private ArrayList<DomCard> foresightedCards = new ArrayList<DomCard>();

    protected String name;
    public int actionsLeft=1;
    protected int buysLeft;
    private int availableCoins = 0;
    public int availablePotions = 0;
    protected DomGame game;
    private int wins = 0;
    private double ties = 0;
    private ArrayList<Integer> moneyCurve = new ArrayList<Integer>();
    private ArrayList<Integer> VPcurve = new ArrayList<Integer>();
    private int turns = 0;
    private int victoryTokens = 0;
    private int pointsBeforeBuys = 0;
    private int sumTurns = 0;
    protected int pirateShipLevel = 0;
    public long actionTime = 0;
    public long countVPTime = 0;
    public long buyTime = 0;
    private int forcedStart = 0;
    private ArrayList<DomCard> boughtCards = new ArrayList<DomCard>();
    private int actionsplayed;
    private ArrayList<DomCardName> forbiddenCardsToBuy = new ArrayList<DomCardName>();
    private boolean extraOutpostTurn;
    private ArrayList<DomPlayer> possessionTurns = new ArrayList<DomPlayer>();
    DomPlayer possessor;
    private ArrayList<DomCardName> cardsGainedLastTurn = new ArrayList<DomCardName>();
    public int sameCardCount = 0;
    public DomCardName previousPlayedCardName = null;
    public boolean pprUsed = false;
    private HashSet<DomBotType> types = new HashSet<DomBotType>();
    private String description = "No description available";
    private String author = "Anonymous";
    private DomPhase currentPhase = null;
    private ArrayList<DomCard> cardsToStayInPlay = new ArrayList<DomCard>();
    private int knownTopCards = 0;
    private StartState myStartState = null;
    private ArrayList<DomCardName> mySuggestedBoardCards = new ArrayList<DomCardName>();
    private DomCardName myBaneCard;
    private int coffers;
    private boolean journeyTokenIsFaceUp;
    private ArrayList<DomCard> tavernMat = new ArrayList<DomCard>();
    private boolean minusOneCardToken;
    private DomCardName plusOneBuyTokenOn;
    private ArrayList<DomCard> princedCards = new ArrayList<DomCard>();
    private boolean minusOneCoinToken;
    private DomCardName plusOneCardTokenOn;
    private DomCardName plusOneActionTokenOn;
    private boolean travellingFairIsActive;
    private boolean pilgrimageActivatedThisTurn;
    private boolean almsActivated;
    private boolean continueTriggered;
    private boolean saveActivated;
    private int expeditionsActivated;
    private DomCardName minus$2TokenOn;
    private DomCard estateTokenOn;
    private boolean extraMissionTurn;
    private boolean noBuyThisTurn;
    private DomCardName plusOneCoinTokenOn;
    private DomCardName trashingTokenOn;
    private int bridgesPlayedCount;
    private int bridgeTrollPlayedCount;
    private int coppersmithsPlayedCount;
    private int debt;
    private boolean hasDoubledMoney;
    private int charmReminder = 0;
    private ArrayList<DomCard> mySetAsideEncampments = new ArrayList<DomCard>();
    private boolean donateTriggered;
    private int mountainPassBid = 0;
    private DomCardName obeliskChoice = null;
    private boolean villaTriggered = false;
    private int merchantsPlayed;
    private DomCard savedCard;
    private boolean isHuman;
    private DomEngine myEngine;
    private ArrayList<DomCard> beginningOfTurnTriggers=new ArrayList<DomCard>();
    private boolean shelters=false;
    private ArrayList<DomCard> boons = new ArrayList<DomCard>();
    private boolean river$sGiftActive=false;
    private ArrayList<DomCard> delayedBoons = new ArrayList<DomCard>();
    private boolean deluded;
    private boolean envious;
    private boolean cantBuyActions;
    private boolean lostInTheWoods;
    private boolean enviousActive;
    private boolean miserable;
    private boolean twiceMiserable;
    private ArrayList<DomCard> setAsideFaithfulHounds=new ArrayList<>();
    private boolean borrowActivated;
    private boolean gainedExtraExperiment;
    private int trashingBonus;
    private int villagers;
    private HashSet<DomCardName> myProjects = new HashSet<>();
    private boolean resolvingSewers;
    private boolean hornActivated;
    private int improvePlayedCounter;
    private int inventorPlayedCounter;
    private ArrayList<DomCard> ghostProcessionedCards = new ArrayList();
    private int sinisterPlotTokens;
    private boolean hasFleetTurnLeft;
    private boolean hasTriggeredInnovation;
    private int liveryTriggers=0;
    private int merchant_GuildTrigger=0;
    private boolean snowedIn=false;
    private ArrayList<DomCard> investments = new ArrayList<>();
    private boolean cavalryTriggered;
    private int numberOfTrashedCards = 0;
    private boolean kilnTriggerd = false;
    private int cargoShipTriggers = 0;
    private ArrayList<DomCard> cargoCards = new ArrayList<>();
    private ArrayList<DomCard> deliverCards = new ArrayList<>();
    private int favors=0;
    private int gainsSinceBeginningOfBuyPhase =0;
    private boolean annoyedByMonkey;
    private boolean attackedByCorsair=false;
    private boolean trashedForCorsair=false;
    private int collectionTriggers;
    private ArrayList<DomCard> myPreparedCards=new ArrayList<>();
    private int miningRoadTriggers=0;
    private int cardsPlayedThisTurnCounter=0;
    private int carnivalsPlayed = 0;
    private int carnivalDraws = 0;
    private int smallPotatoesPlayed=0;
    private int endTurnExtraDraws=0;
    private int magicLampOpened=0;
    private boolean uberDominationWinTrigger=false;
    private boolean continuePlayedOnce;
    private int weddingCounter=0;
    private int avoidTriggers =0;
    private boolean rushTriggered=false;
    private int sailorTriggers =0;
    private boolean deliverTriggered=false;

    public DomPlayer(String aString) {
        name = aString;
    }

    public DomPlayer(String aName, String anAuthor, String aDescription) {
        this(aName);
        if (anAuthor != null)
            author = anAuthor;
        if (aDescription != null)
            description = aDescription;
    }

    public static ArrayList<DomCard> getMultiplesInHand(DomCard card) {
        ArrayList<DomCardName> theSingleCards = new ArrayList<DomCardName>();
        ArrayList<DomCard> theMultipleCards = new ArrayList<DomCard>();
        for (DomCard theCard : card.owner.getCardsInHand()) {
            if (theCard == card)
                continue;
            if (!theSingleCards.contains(theCard.getName())) {
                theSingleCards.add(theCard.getName());
            } else {
                theMultipleCards.add(theCard);
            }
        }
        return theMultipleCards;
    }

    public static void doLocustsOrBarbarianEffect(DomPlayer domPlayer, DomCardName cardName) {
        ArrayList<DomCard> theCards = domPlayer.revealTopCards(1);
        if (theCards.isEmpty())
            return;
        domPlayer.trash(theCards.get(0));
        if ((cardName==DomCardName.Locusts && (theCards.get(0).getName() == DomCardName.Copper || theCards.get(0).getName() == DomCardName.Estate))
        ||(cardName == DomCardName.Barbarian && (theCards.get(0).getCoinCost(domPlayer.getCurrentGame()) <= 3 && theCards.get(0).getDebtCost() == 0 && theCards.get(0).getPotionCost() == 0))) {
            domPlayer.gain(DomCardName.Curse);
        }else {
            if (domPlayer.isHumanOrPossessedByHuman()) {
                DomCard theTrashedCard = theCards.get(0);
                ArrayList<DomCardName> theChooseFrom = new ArrayList<>();
                for (DomCardName theCard : domPlayer.getCurrentGame().getBoard().getTopCardsOfPiles()) {
                    if (theTrashedCard.getCost(domPlayer.getCurrentGame()).customCompare(theCard.getCost(domPlayer.getCurrentGame())) > 0
                            && domPlayer.getCurrentGame().countInSupply(theCard) > 0
                            && theCard.sharesTypeWith(theTrashedCard))
                        theChooseFrom.add(theCard);
                }
                if (theChooseFrom.isEmpty())
                    return;
                if (theChooseFrom.size() == 1) {
                    domPlayer.gain(domPlayer.getCurrentGame().takeFromSupply(theChooseFrom.get(0)));
                } else {
                    domPlayer.gain(domPlayer.getCurrentGame().takeFromSupply(domPlayer.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for " + domPlayer.getName().toString(), theChooseFrom, "Mandatory!")));
                }
            } else {
                DomCard theTrashedCard = theCards.get(0);
                ArrayList<DomCardName> theChooseFrom = new ArrayList<>();
                for (DomCardName theCard : domPlayer.getCurrentGame().getBoard().getTopCardsOfPiles()) {
                    if (theTrashedCard.getCost(domPlayer.getCurrentGame()).customCompare(theCard.getCost(domPlayer.getCurrentGame())) > 0
                            && domPlayer.getCurrentGame().countInSupply(theCard) > 0
                            && theCard.sharesTypeWith(theTrashedCard))
                        theChooseFrom.add(theCard);
                }
                if (theChooseFrom.isEmpty())
                    return;
                for (DomBuyRule theRule : domPlayer.getBuyRules()) {
                    DomCardName cardToBuy = theRule.getCardToBuy();
                    for (DomCardName theChoice : theChooseFrom) {
                        if (cardToBuy == theChoice) {
                            boolean wantsToGain = true;
                            for (DomBuyCondition theCondition : theRule.getBuyConditions()) {
                                if (!theCondition.isTrue(domPlayer.getPossessor() != null ? domPlayer.getPossessor() : domPlayer)) {
                                    wantsToGain = false;
                                    break;
                                }
                            }
                            if (wantsToGain && !domPlayer.suicideIfBuys(cardToBuy)) {
                                domPlayer.gain(theChoice);
                                return;
                            }
                        }
                    }
                }
                Collections.sort(theChooseFrom, DomCardName.FOR_TRASHING);
                domPlayer.gain(theChooseFrom.get(theChooseFrom.size() - 1));
            }
        }
    }

    public DomCard findCardToRemodel(DomCard cardInHandThatCanNotBeRemodeled, int theAmount, boolean sameCardAllowed) {
    	ArrayList<DomCard> theCardsToConsiderTrashing=new ArrayList<DomCard>();
    	ArrayList<DomCardName> theCardsToGain=new ArrayList<DomCardName>();
    	DomCardName theDesiredCardIfRemodelNotUsed = getDesiredCard(getTotalPotentialCurrency(), false);
        for (int i=0;i< getCardsInHand().size();i++) {
            if (getCardsInHand().get(i)== cardInHandThatCanNotBeRemodeled)
                continue;
        	//temporarily remove the card from hand AND deck
        	DomCard theCard = getCardsInHand().remove(i);
            DomCost theMaxCostOfCardToGain = new DomCost( theCard.getCoinCost(getCurrentGame()) + theAmount, theCard.getPotionCost());
        	getDeck().get(theCard.getName()).remove(theCard );
      	    DomCardName theRemodelGainCard = getDesiredCardWithRestriction(null,theMaxCostOfCardToGain, false, sameCardAllowed?null:cardInHandThatCanNotBeRemodeled.getName());
        	DomCardName theDesiredCard = getDesiredCard(getTotalPotentialCurrency(), false);
        	//first we will make a list of cards we consider good candidates for trashing
        	//only add to the list if:
        	//  -what we will gain is better than the card we trash (so of course it's not null)
        	//  -(and the card we will gain is better than what we were able to buy without using Remodel
        	//    or -trashing the card will not hinder our buying potential)
            if (   (theRemodelGainCard!=null
            	  && theRemodelGainCard.getTrashPriority(this)>theCard.getName().getTrashPriority(this)
            	  && (theDesiredCardIfRemodelNotUsed == null
            	  || theRemodelGainCard.getTrashPriority(this)>=theDesiredCardIfRemodelNotUsed.getTrashPriority(this)
            	  || theDesiredCard==theDesiredCardIfRemodelNotUsed))){
				theCardsToConsiderTrashing.add(theCard);
				theCardsToGain.add(theRemodelGainCard);
            }
        	getDeck().get(theCard.getName()).add(theCard );
        	getCardsInHand().add(i, theCard);
        }
        //nothing good found
        if (theCardsToConsiderTrashing.isEmpty())
        	return null;
        //now we scan the lists to find the best possible trashing candidate
        DomCardName theBestCardToGain=null;
        DomCard theBestCardToTrash=null;
        for (int i=0;i<theCardsToGain.size();i++) {
          DomCardName theCardToGain = theCardsToGain.get(i);
          if (stillInEarlyGame()){
	    	if (theBestCardToGain==null
	        || theCardsToConsiderTrashing.get(i).getTrashPriority()<theBestCardToTrash.getTrashPriority()) {
	    	    theBestCardToGain=theCardToGain;
	    	    theBestCardToTrash=theCardsToConsiderTrashing.get(i);
	    	}
	      } else {
	    	  if (theBestCardToGain==null
	      	   //trashing this card will give us a better card
	    	   || theCardToGain.getTrashPriority(this)>theBestCardToGain.getTrashPriority(this)
	           //trashing this card is more desirable while still allowing us to gain the best card
	    	   || ((theCardToGain.getTrashPriority(this)==theBestCardToGain.getTrashPriority(this)
	               && theCardsToConsiderTrashing.get(i).getTrashPriority()<theBestCardToTrash.getTrashPriority()))) {
	    	    theBestCardToGain=theCardToGain;
	    	    theBestCardToTrash=theCardsToConsiderTrashing.get(i);
	    	  }
	      }
        }
        return theBestCardToTrash;
    }

    public DomCard findCardToDisplace(DomCard domCard, int theAmount, boolean sameCardAllowed) {
        ArrayList<DomCard> theCardsToConsiderExiling=new ArrayList<DomCard>();
        ArrayList<DomCardName> theCardsToGain=new ArrayList<DomCardName>();
        DomCardName theDesiredCardIfDisplaceNotUsed = getDesiredCard(getTotalPotentialCurrency(), false);
        for (int i=0;i< getCardsInHand().size();i++) {
            if (getCardsInHand().get(i)== domCard)
                continue;
            //temporarily remove the card from hand AND deck
            DomCard theCard = getCardsInHand().remove(i);
            DomCost theMaxCostOfCardToGain = new DomCost( theCard.getCoinCost(getCurrentGame()) + theAmount, theCard.getPotionCost());
            getDeck().get(theCard.getName()).remove(theCard );
            DomCardName theRemodelGainCard = getDesiredCardWithRestriction(null,theMaxCostOfCardToGain, false, sameCardAllowed?null:domCard.getName());
            DomCardName theDesiredCard = getDesiredCard(getTotalPotentialCurrency(), false);
            //first we will make a list of cards we consider good candidates for exiling
            //only add to the list if:
            //  -(and the card we will gain is better than what we were able to buy without using Remodel
            //    or -trashing the card will not hinder our buying potential)
            if (   (theRemodelGainCard!=null
                    && (theDesiredCardIfDisplaceNotUsed == null
                    || theRemodelGainCard.getTrashPriority(this)>=theDesiredCardIfDisplaceNotUsed.getTrashPriority(this)
                    || theDesiredCard==theDesiredCardIfDisplaceNotUsed))){
                theCardsToConsiderExiling.add(theCard);
                theCardsToGain.add(theRemodelGainCard);
            }
            getDeck().get(theCard.getName()).add(theCard );
            getCardsInHand().add(i, theCard);
        }
        //nothing good found
        if (theCardsToConsiderExiling.isEmpty())
            return null;
        //now we scan the lists to find the best possible trashing candidate
        DomCardName theBestCardToGain=null;
        DomCard theBestCardToExile=null;
        for (int i=0;i<theCardsToGain.size();i++) {
            DomCardName theCardToGain = theCardsToGain.get(i);
            if (stillInEarlyGame()){
                if (theBestCardToGain==null
                        || theCardsToConsiderExiling.get(i).getDiscardPriority(1)<theBestCardToExile.getDiscardPriority(1)) {
                    theBestCardToGain=theCardToGain;
                    theBestCardToExile=theCardsToConsiderExiling.get(i);
                }
            } else {
                if (theBestCardToGain==null
                        //trashing this card will give us a better card
                        || theCardToGain.getTrashPriority(this)>theBestCardToGain.getTrashPriority(this)
                        //trashing this card is more desirable while still allowing us to gain the best card
                        || ((theCardToGain.getTrashPriority(this)==theBestCardToGain.getTrashPriority(this)
                        && theCardsToConsiderExiling.get(i).getDiscardPriority(1)<theBestCardToExile.getDiscardPriority(1)))) {
                    theBestCardToGain=theCardToGain;
                    theBestCardToExile=theCardsToConsiderExiling.get(i);
                }
            }
        }
        return theBestCardToExile;
    }

    public void makeBuyDecision() {
        for (DomBuyRule theBuyRule : getBuyRules()) {
            if (theBuyRule.getCardToBuy().hasCardType(DomCardType.Prize))
                continue;
            if (getCurrentGame().getBoard().isFromSeparatePile(theBuyRule.getCardToBuy()))
                continue;
            DomCost theCost = determineCostAndCheckSplitPiles(theBuyRule);
            if (theCost == null)
                continue;
            if (theBuyRule.getCardToBuy()==DomCardName.Animal_Fair) {
                if (getCardsFromHand(DomCardType.Ruins).isEmpty()
                        && getCardsFromHand(DomCardName.Necropolis).isEmpty()
                        && getTotalAvailableCurrency().compareButIgnoreDebtTo(theCost) < 0)
                    continue;
            } else {
                if (getCoffers()>0 && doesNotWantToSpendCoffers(theBuyRule.getBuyConditions())) {
                    if (getTotalAvailableCurrencySansCoffers().compareButIgnoreDebtTo(theCost)<0)
                        continue;
                } else{
                    if (getTotalAvailableCurrency().compareButIgnoreDebtTo(theCost) < 0)
                        continue;
                }
            }

            if (checkBuyConditions(theBuyRule)) {
                if (theBuyRule.getCardToBuy().hasCardType(DomCardType.Event)) {
                    if (!wantsEvent(theBuyRule.getCardToBuy()))
                        continue;
                    payForAndResolveEvent(theBuyRule.getCardToBuy());
                    if (debt > 0) {
                        payOffDebt();
                    }
                    return;
                }
                if (theBuyRule.getCardToBuy().hasCardType(DomCardType.Project)) {
                    payForAndBuildProject(theBuyRule.getCardToBuy());
                    myProjects.add(theBuyRule.getCardToBuy());
                    return;
                }
                if (!getNoBuyThisTurn() && tryToBuy(theBuyRule.getCardToBuy(), !hasFleetTurnLeft())) {
                    return;
                }
            }
        }
        if (DomEngine.haveToLog) DomEngine.addToLog(name + " buys NOTHING!");

        //a bit dirty setting buysLeft to 0 to make him stop trying to buy stuff and say 'buys nothing'
        //TODO maybe clean this up
        buysLeft = 0;
    }

    private boolean doesNotWantToSpendCoffers(ArrayList<DomBuyCondition> buyConditions) {
        for (DomBuyCondition buyCondition : buyConditions) {
            if (buyCondition.getLeftFunction()==DomBotFunction.doesNotSpendCoffers)
                return true;
        }
        return false;
    }

    private DomCost determineCostAndCheckSplitPiles(DomBuyRule theBuyRule) {
      return determineCostAndCheckSplitPiles(theBuyRule.getCardToBuy());
    }

    public DomCost determineCostAndCheckSplitPiles(DomCardName theCard) {
        DomCost theCost = theCard.getCost(getCurrentGame());
        if (theCard == DomCardName.Castles) {
            if (getCurrentGame().countInSupply(DomCardName.Castles) > 0)
                theCost = getCurrentGame().getBoard().get(DomCardName.Castles).get(0).getCost(getCurrentGame());
            else
                theCost = null;
        }
        if (theCard == DomCardName.Knights) {
            if (getCurrentGame().countInSupply(DomCardName.Knights) > 0)
                theCost = getCurrentGame().getBoard().get(DomCardName.Knights).get(0).getCost(getCurrentGame());
            else
                theCost = null;
        }
        if (theCard == DomCardName.Gladiator) {
            if (getCurrentGame().countInSupply(DomCardName.Gladiator) ==0 || getCurrentGame().getBoard().get(DomCardName.Gladiator).get(0).getName()!=DomCardName.Gladiator)
                theCost = null;
        }
        if (theCard == DomCardName.Fortune) {
            if (getCurrentGame().countInSupply(DomCardName.Gladiator) ==0 || getCurrentGame().getBoard().get(DomCardName.Gladiator).get(0).getName()!=DomCardName.Fortune)
                theCost = null;
        }
        if (theCard == DomCardName.Settlers) {
            if (getCurrentGame().countInSupply(DomCardName.Settlers) ==0 || getCurrentGame().getBoard().get(DomCardName.Settlers).get(0).getName()!=DomCardName.Settlers)
                theCost = null;
        }
        if (theCard == DomCardName.Bustling_Village) {
            if (getCurrentGame().countInSupply(DomCardName.Settlers) ==0 || getCurrentGame().getBoard().get(DomCardName.Settlers).get(0).getName()!=DomCardName.Bustling_Village)
                theCost = null;
        }
        if (theCard == DomCardName.Catapult) {
            if (getCurrentGame().countInSupply(DomCardName.Catapult) ==0 || getCurrentGame().getBoard().get(DomCardName.Catapult).get(0).getName()!=DomCardName.Catapult)
                theCost = null;
        }
        if (theCard == DomCardName.Rocks) {
            if (getCurrentGame().countInSupply(DomCardName.Catapult) ==0 || getCurrentGame().getBoard().get(DomCardName.Catapult).get(0).getName()!=DomCardName.Rocks)
                theCost = null;
        }
        if (theCard == DomCardName.Patrician) {
            if (getCurrentGame().countInSupply(DomCardName.Patrician) ==0 || getCurrentGame().getBoard().get(DomCardName.Patrician).get(0).getName()!=DomCardName.Patrician)
                theCost = null;
        }
        if (theCard == DomCardName.Emporium) {
            if (getCurrentGame().countInSupply(DomCardName.Patrician) ==0 || getCurrentGame().getBoard().get(DomCardName.Patrician).get(0).getName()!=DomCardName.Emporium)
                theCost = null;
        }
        if (theCard == DomCardName.Encampment) {
            if (getCurrentGame().countInSupply(DomCardName.Encampment) ==0 || getCurrentGame().getBoard().get(DomCardName.Encampment).get(0).getName()!=DomCardName.Encampment)
                theCost = null;
        }
        if (theCard == DomCardName.Plunder) {
            if (getCurrentGame().countInSupply(DomCardName.Encampment) ==0 || getCurrentGame().getBoard().get(DomCardName.Encampment).get(0).getName()!=DomCardName.Plunder)
                theCost = null;
        }
        if (theCard == DomCardName.Sauna) {
            if (getCurrentGame().countInSupply(DomCardName.Sauna) ==0 || getCurrentGame().getBoard().get(DomCardName.Sauna).get(0).getName()!=DomCardName.Sauna)
                theCost = null;
        }
        if (theCard == DomCardName.Avanto) {
            if (getCurrentGame().countInSupply(DomCardName.Sauna) ==0 || getCurrentGame().getBoard().get(DomCardName.Sauna).get(0).getName()!=DomCardName.Avanto)
                theCost = null;
        }
        return theCost;
    }

    private boolean wantsEvent(DomCardName cardToBuy) {
        if (cardToBuy == DomCardName.Pilgrimage && pilgrimageActivatedThisTurn)
            return false;
        if (cardToBuy == DomCardName.Alms && (almsActivated || countInPlay(DomCardType.Treasure) > 0))
            return false;
        if (cardToBuy == DomCardName.Continue && (continueTriggered || continuePlayedOnce))
            return false;
        if (cardToBuy == DomCardName.Amass && !getCardsFromPlay(DomCardType.Action).isEmpty())
            return false;
        if (cardToBuy == DomCardName.Borrow && borrowActivated)
            return false;
        if (cardToBuy == DomCardName.Quest && !checkForQuest())
            return false;
        if (cardToBuy == DomCardName.Mission && hasExtraMissionTurn())
            return false;
        if (cardToBuy == DomCardName.Windfall && getDeckAndDiscardSize() > 0)
            return false;
        if (cardToBuy == DomCardName.Advance && getCardsFromHand(DomCardType.Action).isEmpty())
            return false;
        if (cardToBuy == DomCardName.Ritual && getCardsInHand().isEmpty())
            return false;
        if (cardToBuy == DomCardName.Donate && donateTriggered)
            return false;
        if (cardToBuy==DomCardName.Save && saveActivated)
            return false;
        if (cardToBuy == DomCardName.Bury && discardedCardsAreCrapy())
            return false;
        if (cardToBuy == DomCardName.UberDomination && getUberDominationWinTrigger())
            return false;

        return true;
    }

    private boolean discardedCardsAreCrapy() {
        if (getCardsFromDiscard().isEmpty())
            return true;
        getCardsFromDiscard().sort(DomCard.SORT_FOR_DISCARDING_REVERSE);
        if (getCardsFromDiscard().get(0).getDiscardPriority(1)<DomCardName.Silver.getDiscardPriority(1))
            return true;
        return false;
    }

    private boolean checkForQuest() {
        return getCardsInHand().size() >= 6 || getCardsFromHand(DomCardName.Curse).size() >= 2 || !getCardsFromHand(DomCardType.Attack).isEmpty();
    }

    private DomCost getAvailableCurrencyWithoutTokens() {
        return getTotalAvailableCurrency().add(new DomCost(-coffers, 0));
    }

    private boolean checkIfWantsToHoardCoffers() {
        if (!stillInEarlyGame() && !isGoingToBuyTopCardInBuyRules(getTotalAvailableCurrency()) && count(DomCardName.Duchy) == 0)
            return true;
        return false;
    }

    public boolean checkBuyConditions(DomBuyRule theBuyRule) {
        for (DomBuyCondition theCondition : theBuyRule.getBuyConditions()) {
            if (!theCondition.isTrue(possessor != null ? possessor : this))
                return false;
        }
        return true;
    }

    public int getTotalMoneyExcludingMats() {
        return deck.getTotalMoney() - getMoneyFromMats();
    }

    /**
     * @return
     */
    private int getMoneyFromMats() {
        int theTotal = 0;
        for (DomCard theCard : nativeVillageMat) {
            theTotal += theCard.getPotentialCoinValue();
        }
        for (DomCard theCard : getDeck().getExileMat()) {
            theTotal += theCard.getPotentialCoinValue();
        }
        return theTotal;
    }

    /**
     * @return
     */
    public int getMoneyInHand() {
        int theTotal = 0;
        for (int i = 0; i < cardsInHand.size(); i++) {
            theTotal += cardsInHand.get(i).getPotentialCoinValue();
        }
        return theTotal;
    }

    /**
     *
     */
    public void shuffleDeck() {
        deck.shuffle();
    }

    /**
     * @param aI
     */
    public void drawCards(int aI) {
        if (minusOneCardToken) {
            aI--;
            minusOneCardToken = false;
        }
        ArrayList<DomCard> theDrawnCards = deck.getTopCards(aI);
        cardsInHand.addAll(theDrawnCards);
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " draws " + theDrawnCards.size() + " cards");
        showHand();
    }

    /**
     *
     */
    public void showHand() {
        if (DomEngine.haveToLog) DomEngine.addToLog(name + "'s cards in Hand: " + cardsInHand);
    }

    public void revealHand() {
        showHand();
        for (DomCard theCard:cardsInHand){
            if (theCard.getName()==DomCardName.Patron)
                theCard.react();
        }
    }


    /**
     * @return
     */
    public int count(DomCardName aCardName) {
        return deck.count(aCardName);
    }

    /**
     * @return
     */
    public int count(DomCardType aCardType) {
        return deck.count(aCardType);
    }

    /**
     * @return
     */
    public ArrayList<DomCard> getCardsFromHand(DomCardName theCardName) {
        ArrayList<DomCard> theCards = new ArrayList<DomCard>();
        for (DomCard theCard : cardsInHand) {
            if (theCard.getName().equals(theCardName))
                theCards.add(theCard);
            if (theCardName!=DomCardName.Estate && theCard.getName()==DomCardName.Estate && getEstateTokenOn()!=null && getEstateTokenOn().getName()==theCardName)
                theCards.add(theCard);
        }
        return theCards;
    }

    /**
     * @return
     */
    public ArrayList<DomCard> getCardsFromPlay(DomCardName theCardName) {
        ArrayList<DomCard> theCards = new ArrayList<DomCard>();
        for (DomCard theCard : cardsInPlay) {
            if (theCard.getName().equals(theCardName))
                theCards.add(theCard);
        }
        return theCards;
    }

    public void takeTurn() {
//        for(DomCard theCard : getDeck().getAllCards()) {
//            if (theCard.owner==null) {
//                System.out.println("Error, cards in deck with null owner "+ theCard);

//                System.out.println(getDeck());
//            }
//        }
        if (getCurrentGame().isGameFinished()&&possessor==null)
          setFleetTurnLeft(false);
        initializeTurn();
        handleTeachers();
        handleGang_of_Pickpockets();
        handleGuides();
        resolveHorseTraders();
        resolvePreparedCards();
        resolveDurationEffects();
        handleShaman();
        resolveProcessionGhosts();
        handleCropRotation();
        handleCityGate();
        handlePiazza();
        resolveCardsToReap();
        resolveCardsToSummon();
        resolvePrincedCards();
        resolveRatcatchers();
        handleTransmogrify();
        handleCathedral();
        handleDelayedBoons();
        handleLostInTheWoods();
        handleSilos();
        handleSinister_Plot();
        handleClerk();
        doActionPhase();
        doBuyPhase();
        doNightPhase();
        doCleanUpPhase();

        //actually this is not part of the turn so we set Possessor to null
        possessor = null;
        if (donateTriggered)
            DonateCard.trashStuff(this);
        if (getCurrentGame().isAuctionTriggered()) {
            Mountain_PassCard.doTheAuction(this);
            getCurrentGame().setAuctionTriggered(false);
        }
        getCurrentGame().setPreviousTurnTakenBy(this);
        if (hasExtraMissionTurn()) {
            setNoBuyThisTurn(true);
        } else {
            setNoBuyThisTurn(false);
        }
        //TODO moved from buy phase to here... ok?
        updateVPCurve(false);
        //TODO needed fixing
        actionsLeft=1;
        miningRoadTriggers=0;
    }

    private void handleClerk() {
        if (getCardsFromHand(DomCardName.Clerk).isEmpty())
            return;
        while (!getCardsFromHand(DomCardName.Clerk).isEmpty()) {
          play(removeCardFromHand(getCardsFromHand(DomCardName.Clerk).get(0)));
        }
    }

    private void handleShaman() {
        if (getCurrentGame().getBoard().get(DomCardName.Shaman)!=null && !getCurrentGame().getTrashedCards().isEmpty()) {
            getCurrentGame().getTrashedCards().sort(DomCard.SORT_FOR_TRASHING_DESC);
            if (DomEngine.haveToLog) DomEngine.addToLog(DomCardName.Shaman.toHTML() + " triggers to gain something from the trash:" );
            for (DomCard theCard: getCurrentGame().getTrashedCards()) {
                if (theCard.getCost(getCurrentGame()).getPotions()==0 && theCard.getCost(getCurrentGame()).getDebt()==0 && theCard.getCost(getCurrentGame()).getCoins()<=6) {
                    gain(getCurrentGame().removeFromTrash(theCard));
                    return;
                }
            }
        }
    }

    private void resolvePreparedCards() {
        if (myPreparedCards.isEmpty())
            return;
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " plays following set aside cards: " + myPreparedCards);
        myPreparedCards.sort(DomCard.SORT_FOR_PLAYING);
        ArrayList<DomCard> cardsToRemove = new ArrayList<>();
        for (int i=0;i<myPreparedCards.size();i++) {
            if (myPreparedCards.get(i).hasCardType(DomCardType.Action) || myPreparedCards.get(i).hasCardType(DomCardType.Treasure)) {
                play(myPreparedCards.get(i));
                cardsToRemove.add(myPreparedCards.get(i));
            }
        }
        myPreparedCards.removeAll(cardsToRemove);
        for (DomCard theCard : myPreparedCards) {
            discard(theCard);
        }
        myPreparedCards.clear();
    }

    private void handleGang_of_Pickpockets() {
        if (game.getActiveAlly()!=DomCardName.Gang_of_Pickpockets || getCardsInHand().isEmpty())
            return;
        Collections.sort(getCardsInHand(),DomCard.SORT_FOR_DISCARD_FROM_HAND);
        DomCard theCardToDiscard=getCardsInHand().get( 0 );
        if (!removingReducesBuyingPower( theCardToDiscard)||getFavors()==0||!getCardsFromHand(DomCardType.Victory).isEmpty()) {
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " pays off " + DomCardName.Gang_of_Pickpockets.toHTML());
            discardFromHand(theCardToDiscard);
        } else {
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " asks " + DomCardName.Gang_of_Pickpockets.toHTML() +" for a favor and does not discard!");
            spendFavors(1);
        }

    }

    private void spendFavors(int i) {
            favors -= i;
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " spends " + (i==1 ? "1 favor." : i+" favors.")+ " (now has "+favors+")");
    }

    private void handleCropRotation() {
        if (hasBuiltProject(DomCardName.Crop_Rotation)&&!getCardsFromHand(DomCardType.Victory).isEmpty()){
            Collections.sort(cardsInHand, DomCard.SORT_FOR_DISCARDING);
            ArrayList<DomCard> theVictoryCards = getCardsFromHand(DomCardType.Victory);
            discardFromHand(theVictoryCards.get(0));
            drawCards(2);
        }
    }

    private void handleCityGate() {
        if (hasBuiltProject(DomCardName.City_Gate)){
            drawCards(1);
            if (getCardsFromHand(DomCardName.Treasure_Map).size()==1)
                putOnTopOfDeck(removeCardFromHand(getCardsFromHand(DomCardName.Treasure_Map).get(0)));
            else
                doForcedDiscard(1,true);
        }
    }

    private void handlePiazza() {
        if (hasBuiltProject(DomCardName.Piazza)) {
            ArrayList<DomCard> theTopCards = revealTopCards(1);
            if (!theTopCards.isEmpty()) {
                if (theTopCards.get(0).hasCardType(DomCardType.Action))
                    play(theTopCards.get(0));
                else
                    putOnTopOfDeck(theTopCards.get(0));
            }
        }
    }

    private void resolveProcessionGhosts() {
        for (DomCard theNextCardToHandle:ghostProcessionedCards) {
            theNextCardToHandle.owner=this;
//            theNextCardToHandle.resolveDuration();
//            theNextCardToHandle.resolveDuration();
            theNextCardToHandle.owner=null;
        }
        ghostProcessionedCards.clear();
    }

    private void handleSilos() {
        if (hasBuiltProject(DomCardName.Silos)) {
            int i = 0;
            while (!getCardsFromHand(DomCardName.Copper).isEmpty()) {
                discardFromHand(DomCardName.Copper);
                i++;
            }
            if (i>0)
                drawCards(i);
        }
    }

    private void handleSinister_Plot() {
        if (hasBuiltProject(DomCardName.Sinister_Plot)) {
            if (sinisterPlotTokens==0) {
                setSinisterPlotTokens(1);
                return;
            }
            if (stillInEarlyGame()) {
                if (sinisterPlotTokens > 2 && !isGoingToBuyTopCardInBuyRules(getTotalPotentialCurrency())) {
                    drawCards(getSinisterPlotTokens());
                    setSinisterPlotTokens(0);
                } else {
                    setSinisterPlotTokens(getSinisterPlotTokens() + 1);
                }
            } else {
                if (isGoingToBuyTopCardInBuyRules(getTotalPotentialCurrency())) {
                    setSinisterPlotTokens(getSinisterPlotTokens() + 1);
                } else {
                    int theExpectedMoney = sinisterPlotTokens * getTotalMoneyInDeck() / countAllCards()+2;
                    DomCost theTotalExpectedMoney = getTotalPotentialCurrency().add(new DomCost(theExpectedMoney, 0));
                    if (isGoingToBuyTopCardInBuyRules(theTotalExpectedMoney)) {
                        drawCards(getSinisterPlotTokens());
                        setSinisterPlotTokens(0);
                    } else {
                        setSinisterPlotTokens(getSinisterPlotTokens() + 1);
                    }
                }
            }
        }
    }

    private void handleCathedral() {
        if (myProjects.contains(DomCardName.Cathedral)){
            DomCard theCathedral = DomCardName.Cathedral.createNewCardInstance();
            theCathedral.owner=this;
            theCathedral.trigger();
        }
    }

    private void handleLostInTheWoods() {
        if (!lostInTheWoods || getCardsInHand().isEmpty())
            return;
        Collections.sort(getCardsInHand(),DomCard.SORT_FOR_DISCARD_FROM_HAND);
        DomCard theCardToDiscard=getCardsInHand().get( 0 );
        if (!removingReducesBuyingPower( theCardToDiscard )) {
            discardFromHand(theCardToDiscard);
            receiveBoon(null);
        }
    }

    private void handleDelayedBoons() {
        while (!delayedBoons.isEmpty()) {
            receiveBoon(delayedBoons.remove(0));
        }
    }

    private void doNightPhase() {
        setPhase(DomPhase.Night);
        DomCard theCardToPlay = null;
        do {
            theCardToPlay = getNextNightActionToPlay();
            if (theCardToPlay != null) {
                play(removeCardFromHand(theCardToPlay));
            }
        } while (theCardToPlay != null);
    }

    private void handleTransmogrify() {
        DomCard theTransmogrify = getFromTavernMat(DomCardName.Transmogrify);
        while (theTransmogrify != null && ((TransmogrifyCard) theTransmogrify).wantsToBeCalled()) {
            getCardsInPlay().add(removeFromTavernMat(theTransmogrify));
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " calls " + theTransmogrify + " from the tavern mat");
            theTransmogrify.doWhenCalled();
            theTransmogrify = getFromTavernMat(DomCardName.Transmogrify);
        }
    }

    private void resolveRatcatchers() {
        DomCard theRatcatcher = getFromTavernMat(DomCardName.Ratcatcher);
        while (theRatcatcher != null && ((RatcatcherCard) theRatcatcher).wantsToBeCalled()) {
            getCardsInPlay().add(removeFromTavernMat(theRatcatcher));
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " calls " + theRatcatcher + " from the tavern mat");
            theRatcatcher.doWhenCalled();
            theRatcatcher = getFromTavernMat(DomCardName.Ratcatcher);
        }
    }

    private void handleGuides() {
        DomCard theGuide = getFromTavernMat(DomCardName.Guide);
        while (theGuide != null && ((GuideCard) theGuide).wantsToBeCalled()) {
            getCardsInPlay().add(removeFromTavernMat(theGuide));
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " calls " + theGuide + " from the tavern mat");
            theGuide.doWhenCalled();
            theGuide = getFromTavernMat(DomCardName.Guide);
        }
    }

    private void handleTeachers() {
        DomCard theTeacher = getFromTavernMat(DomCardName.Teacher);
        while (theTeacher != null && theTeacher.wantsToBeCalled()) {
            putInPlay(removeFromTavernMat(theTeacher));
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " calls " + theTeacher + " from the tavern mat");
            theTeacher.doWhenCalled();
            theTeacher = getFromTavernMat(DomCardName.Teacher);
        }
    }

    private void putInPlay(DomCard domCard) {
        cardsInPlay.add(domCard);
    }

    private void doCleanUpPhase() {
        setPhase(DomPhase.CleanUp);
        while (!boons.isEmpty()) {
            getCurrentGame().getBoard().returnBoon(boons.remove(0));
        }
        handleEncampments();
        handleImprove();
        cardsToStayInPlay.clear();
        handleHerbalists();
        handleSchemes();
        discardAll();
        discard(deck.getPutAsideCards());
        drawHandForNextTurn();
        if (savedCard!=null) {
            cardsInHand.add(savedCard);
            setNeedsToUpdateGUI();
        }
        for (DomCard card: deliverCards) {
            cardsInHand.add(card);
            setNeedsToUpdateGUI();
            showHand();
        }
        deliverCards.clear();
        for (DomPlayer thePlayer : getCurrentGame().getPlayers()) {
            thePlayer.addFaithFulHoundsToHand();
        }
        setAsideFaithfulHounds.clear();
        addCardsToHand(foresightedCards);
        foresightedCards.clear();
        savedCard=null;
        setPhase(null);
        //reset variables needed for total money checking in other player's turns
        availableCoins=0;
        availablePotions=0;
        if (getCardsGainedLastTurn().isEmpty() && getCurrentGame().getBoard().isLandmarkActive(DomCardName.Baths)) {
            int theVP = getCurrentGame().getBoard().removeVPFrom(DomCardName.Baths, 2);
            if (theVP > 0) {
                if (DomEngine.haveToLog) DomEngine.addToLog(this + " takes VP from " + DomCardName.Baths.toHTML());
                addVP(theVP);
            }
        }
    }

    private void handleImprove() {
        for (int i=0;i<improvePlayedCounter;i++) {
            ImproveCard.improveSomething(this);
        }
    }

    private void handleEncampments() {
        for (DomCard theEncampment : mySetAsideEncampments) {
            returnToSupply(theEncampment);
        }
        mySetAsideEncampments.clear();
    }

    private void addFaithFulHoundsToHand() {
        if (setAsideFaithfulHounds.isEmpty())
            return;
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " adds all " + DomCardName.Faithful_Hound.toHTML() +"s to hand");
        cardsInHand.addAll(setAsideFaithfulHounds);
        setAsideFaithfulHounds.clear();
        setNeedsToUpdateGUI();
    }

    public void showBeginningOfTurnLog() {
        DomEngine.addToLog(possessor != null ? "</i><i>" : "</i>");
        DomEngine.addToLog("<FONT style=\"COLOR: blue\"> *** " + this + "'s turn "
                + getTurns()
                + (possessor != null ? " (Possessed by " + possessor + ")" : "")
                + (extraOutpostTurn ? " (from Outpost)" : "")
                + (extraMissionTurn ? " (from Mission - can not buy cards!)" : "") + " ***"
                + "</FONT>");
        showHand();
    }

    private void resetVariables() {
        actionsLeft = 1;
        buysLeft = 1;
        availableCoins = 0;
        availablePotions = 0;
        actionsplayed = 0;
        merchantsPlayed=0;
        pointsBeforeBuys = countVictoryPoints();
        getCurrentGame().resetFaceDownCards();
    }

    private void resolveHorseTraders() {
        if (horseTradersPile.isEmpty())
            return;
        if (DomEngine.haveToLog)
            DomEngine.addToLog(this + " adds " + horseTradersPile.size() + " " + DomCardName.Horse_Traders.toHTML() + " to his hand");
        cardsInHand.addAll(horseTradersPile);
        drawCards(horseTradersPile.size());
        horseTradersPile.clear();
    }

    private void drawHandForNextTurn() {
        for (DomCard theCard : cardsInPlay) {
            if (theCard.getName() == DomCardName.Outpost) {
                drawCards(3);
                return;
            }
        }
        drawCards(5);
        for (int i = 0; i < expeditionsActivated; i++)
            drawCards(2);
        if (river$sGiftActive) {
            drawCards(1);
            river$sGiftActive=false;
        }
        //edge case with Sacred Grove
        for (DomPlayer theOpp : getOpponents()) {
            if (theOpp.isRiver$sGiftActive()) {
                theOpp.drawCards(1);
                theOpp.river$sGiftActive = false;
            }
        }
        if (getCurrentGame().getArtifactOwner(DomArtifact.Flag)==this) {
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " resolves Artifact:" + DomArtifact.Flag);
            drawCards(1);
        }
        if (endTurnExtraDraws>0) {
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " resolves "+DomCardName.Farrier.toHTML()+" Overbuy:");
            drawCards(endTurnExtraDraws);
            endTurnExtraDraws=0;
        }
    }

    /**
     *
     */
    private void resolveDurationEffects() {
        ArrayList<DomCard> theDurations = new ArrayList<DomCard>();
        for (DomCard aCard : getCardsInPlay()) {
            if (aCard.hasCardType(DomCardType.Duration) && aCard.hasCardType(DomCardType.Card_Advantage)) {
                theDurations.add(aCard);
                continue;
            }
            if (aCard instanceof MultiplicationCard && ((MultiplicationCard) aCard).hasCardAdvantageDuration()) {
                theDurations.add(aCard);
                continue;
            }
        }
        for (DomCard aCard : getCardsInPlay()) {
            if (aCard.hasCardType(DomCardType.Duration) && !aCard.hasCardType(DomCardType.Card_Advantage)) {
                theDurations.add(aCard);
                continue;
            }
            if (aCard instanceof MultiplicationCard && !((MultiplicationCard) aCard).hasCardAdvantageDuration()) {
                theDurations.add(aCard);
                continue;
            }
        }
        for (DomCard aCard : theDurations) {
            if (aCard.getName()==DomCardName.Crew) {
                if (DomEngine.haveToLog) DomEngine.addToLog(this + " resolves duration effect from " + aCard);
                aCard.resolveDuration();
                theDurations.remove(aCard);
                break;
            }
        }

        for (DomCard aCard : theDurations) {
            if (!aCard.hasCardType(DomCardType.NextTime)) {
                if (DomEngine.haveToLog) DomEngine.addToLog(this + " resolves duration effect from " + aCard);
                aCard.resolveDuration();
            }
            if (!aCard.mustStayInPlay())
                aCard.setDiscardAtCleanup(true);
        }
    }

    public void discardHand() {
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " discards all cards in hand");
        deck.addHandToDiscardPile();
        cardsInHand.clear();
    }

    protected void discardAll() {
        deck.getDiscardPile().addAll(cardsInHand);
        cardsInHand.clear();
        for (DomCard theCard : cardsInPlay) {
            theCard.handleCleanUpPhase();
        }
        cardsInPlay.clear();
        cardsInPlay.addAll(cardsToStayInPlay);
    }

    private void handleSchemes() {
        //TODO maybe rethink this
        Collections.sort(cardsInPlay, DomCard.SORT_FOR_DISCARDING);
        for (DomCard theCard : cardsInPlay) {
            if (theCard.getName() == DomCardName.Scheme) {
                ((SchemeCard) theCard).maybeAddTagFor(cardsInPlay);
            }
        }
    }

    private void handleHerbalists() {
        //kind of looks dirty to handle Herbalist but
        //concurrent array modifications are annoying
        Collections.sort(cardsInPlay, DomCard.SORT_FOR_DISCARDING);
        for (DomCard theCard : cardsInPlay) {
            if (theCard.getName() == DomCardName.Herbalist) {
                ((HerbalistCard) theCard).maybeAddTagFor(cardsInPlay);
            }
        }
    }

    public int getNumberOfTrashedCards() {
        return numberOfTrashedCards;
    }

    public void initializeTurn() {
        if (possessor == null && !extraOutpostTurn && !extraMissionTurn) {
            turns++;
            sumTurns++;
        }
        if (DomEngine.haveToLog) showBeginningOfTurnLog();

        if (!getOpponents().isEmpty()) {
            DomPlayer theRightOpponent = getOpponents().get(getOpponents().size() - 1);
            theRightOpponent.setAnnoyedByMonkey(false);
        }

        actionsLeft = 1;
        buysLeft = 1;
        availableCoins = 0;
        availablePotions = 0;
        actionsplayed = 0;
        forbiddenCardsToBuy.clear();
        cardsGainedLastTurn.clear();
        boughtCards.clear();
        sameCardCount = 0;
        previousPlayedCardName = null;
        travellingFairIsActive = false;
        pilgrimageActivatedThisTurn = false;
        almsActivated = false;
        borrowActivated = false;
        saveActivated = false;
        expeditionsActivated = 0;
        river$sGiftActive=false;
        bridgesPlayedCount = 0;
        bridgeTrollPlayedCount = 0;
        improvePlayedCounter = 0;
        inventorPlayedCounter = 0;
        coppersmithsPlayedCount = 0;
        hasDoubledMoney = false;
        charmReminder = 0;
        donateTriggered = false;
        cantBuyActions = false;
        enviousActive = false;
        gainedExtraExperiment = false;
        trashingBonus=0;
        hornActivated=false;
        hasTriggeredInnovation = false;
        //TODO moved from cleanup to here.. maybe problems
        resetVariables();
        if (getCurrentGame().getArtifactOwner(DomArtifact.Key)==this) {
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " resolves Artifact: " + DomArtifact.Key);
            addAvailableCoins(1);
        }
        if (hasBuiltProject(DomCardName.Barracks)) {
            addActions(1);
        }
        if (hasBuiltProject(DomCardName.Fair)) {
            addAvailableBuys(1);
        }
        liveryTriggers=0;
        merchant_GuildTrigger=0;
        snowedIn=false;
        numberOfTrashedCards=0;
        kilnTriggerd=false;
        cargoShipTriggers=0;
        gainsSinceBeginningOfBuyPhase=0;
        trashedForCorsair=false;
        collectionTriggers=0;
        cardsPlayedThisTurnCounter=0;
        smallPotatoesPlayed=0;
        endTurnExtraDraws=0;
        continueTriggered=false;
        continuePlayedOnce=false;
        villaTriggered=false;
        cavalryTriggered=false;
        rushTriggered=false;
        deliverTriggered=false;
        sailorTriggers =0;
    }

    private void doBuyPhase() {
        long theTime = System.currentTimeMillis();
        if (getCurrentGame().getActiveAlly()==DomCardName.League_of_Bankers && getFavors()>=4) {
          if (DomEngine.haveToLog) DomEngine.addToLog("The "+DomCardName.League_of_Bankers.toHTML()+" are your Ally and give you $"+getFavors()/4);
          addAvailableCoinsSilent(getFavors()/4);
        }
        setPhase(DomPhase.Buy_PlayTreasures);
        if (getCurrentGame().getBoard().isLandmarkActive(DomCardName.Arena) && !getCardsFromHand(DomCardType.Action).isEmpty()) {
            if (!getCardsFromHand(DomCardType.Action).get(0).hasCardType(DomCardType.Treasure)) {
                discard(removeCardFromHand(getCardsFromHand(DomCardType.Action).get(0)));
                int theVP = getCurrentGame().getBoard().removeVPFrom(DomCardName.Arena, 2);
                if (theVP > 0) {
                    if (DomEngine.haveToLog) DomEngine.addToLog(this + " takes VP from " + DomCardName.Arena.toHTML());
                    addVP(theVP);
                }
            }
        }
        if (!isInBuyRules(DomCardName.Alms) || getTotalPotentialCurrency().customCompare(new DomCost(4, 0)) > 0)
            playTreasures();

        if (DomEngine.haveToLog) {
            if (previousPlayedCardName != null) {
                DomEngine.addToLog(name + " plays " + (sameCardCount + 1) + " " + previousPlayedCardName.toHTML()
                        + (sameCardCount > 0 ? "s" : ""));
                previousPlayedCardName = null;
                sameCardCount = 0;
            }
            showBuyStatus();
        }
        updateMoneyCurve();

        setPhase(DomPhase.Buy_BuyStuff);
        boolean canUseActionPlaysAsBuys=false;
        if (getCurrentGame().getBoard().getActiveProphecy() == DomCardName.Flourishing_Trade
            && getCurrentGame().getBoard().getProphecyCount()==0){
            canUseActionPlaysAsBuys=true;
        }

        while (buysLeft > 0 || (canUseActionPlaysAsBuys && actionsLeft>0)) {
            if (debt > 0)
                payOffDebt();
            if (debt > 0) {
                if (DomEngine.haveToLog)
                    DomEngine.addToLog(name + " has $" + debt + " in debt left so can't buy cards or events");
                break;
            }
            makeBuyDecision();
            if (buysLeft==0) {
                //we must be in the case of Flourishing Trade so actions are used up as buys
                actionsLeft--;
            } else {
                buysLeft--;
            }
            if (isVillaTriggered()||isCavalryTriggered()||isContinueTriggered()) {
                setVillaTriggered(false);
                setCavalryTriggered(false);
                setContinueTriggered(false);
                if (DomEngine.haveToLog)
                    DomEngine.addToLog(name + " moves back to the action phase");
                doActionPhase();
                doBuyPhase();
            }
        }
        if (boughtCards.isEmpty() && hasBuiltProject(DomCardName.Exploration)) {
            addCoffers(1);
            addVillagers(1);
        }
        handleMerchantGuilds();
        handleWineMerchants();
        handlePageant();
        buyTime += System.currentTimeMillis() - theTime;
    }

    public int getMerchant_GuildTrigger() {
        return merchant_GuildTrigger;
    }

    public void addMerchantGuildTrigger() {
        this.merchant_GuildTrigger++;
    }

    private void handleMerchantGuilds() {
        if (getMerchant_GuildTrigger()>0 && gainsSinceBeginningOfBuyPhase>0) {
            if (DomEngine.haveToLog)
                DomEngine.addToLog(name + " triggers all " + DomCardName.Merchant_Guild.toHTML() + "s");
            addCoffers(getMerchant_GuildTrigger() * gainsSinceBeginningOfBuyPhase);
        }
    }

    public int getFavors() {
        return favors;
    }

    private void handlePageant() {
        if (availableCoins>0 && hasBuiltProject(DomCardName.Pageant)) {
            if (isHumanOrPossessedByHuman()) {
                if (getEngine().getGameFrame().askPlayer("<html>Use " + DomCardName.Pageant.toHTML() +"?</html>", "Resolving " + DomCardName.Pageant.toString())) {
                    availableCoins--;
                    if (DomEngine.haveToLog)
                        DomEngine.addToLog(name + " triggers " + DomCardName.Pageant.toHTML());
                    addCoffers(1);
                }
            } else {
                availableCoins--;
                if (DomEngine.haveToLog)
                    DomEngine.addToLog(name + " triggers " + DomCardName.Pageant.toHTML());
                addCoffers(1);
            }
        }
    }

    private boolean isInBuyRules(DomCardName aCard) {
        for (DomBuyRule theBuyRule : getBuyRules()) {
            if (theBuyRule.getCardToBuy() == aCard)
                return true;
        }
        return false;
    }

    private void playTreasures() {
        DomCard theCardToPlay;
        do {
            theCardToPlay = null;
            for (DomCard theCard : cardsInHand) {
                if (theCard.hasCardType(DomCardType.Treasure)) {
                    if (theCardToPlay == null || theCard.getPlayPriority() < theCardToPlay.getPlayPriority()) {
                        if (theCard.wantsToBePlayed())
                            theCardToPlay = theCard;
                    }
                }
            }
            if (theCardToPlay != null) {
                play(removeCardFromHand(theCardToPlay));
            }
        } while (theCardToPlay != null);
    }

    private void handleWineMerchants() {
        if (availableCoins >= 2) {
            while (getFromTavernMat(DomCardName.Wine_Merchant) != null) {
                DomCard theCard = getFromTavernMat(DomCardName.Wine_Merchant);
                tavernMat.remove(theCard);
                discard(theCard);
            }
        }
    }

    private void handleWineMerchantsForHuman() {
        if (availableCoins >= 2) {
            while (getFromTavernMat(DomCardName.Wine_Merchant) != null &&
                    getEngine().getGameFrame().askPlayer("<html>Discard " + DomCardName.Wine_Merchant.toHTML() +" ?</html>", "Resolving " + this.getName().toString())) {
                DomCard theCard = getFromTavernMat(DomCardName.Wine_Merchant);
                tavernMat.remove(theCard);
                discard(theCard);
            }
        }
    }

    public DomCard getFromTavernMat(DomCardName cardName) {
        for (DomCard theCard : tavernMat) {
            if (theCard.getName() == cardName)
                return theCard;
        }
        return null;
    }

    public void showBuyStatus() {
        StringBuilder theMessage = new StringBuilder();
        theMessage.append(this + " has $" + availableCoins);
        if (availablePotions > 0) {
            theMessage.append(" and ");
            for (int i = 0; i < availablePotions; i++)
                theMessage.append("P");
        }
        if (coffers > 0) {
            theMessage.append(" (+").append(coffers).append(" coffers)");
        }
        theMessage.append(" to spend and " + buysLeft + " buy" + (buysLeft > 1 ? "s" : ""));
        DomEngine.addToLog(theMessage.toString());
    }

    /**
     * @param isCumulative
     */
    public void updateVPCurve(boolean isCumulative) {
        if (VPcurve.size() < getTurns()) {
            //initialize curves
            if (isCumulative)
                VPcurve.add(countVictoryPoints());
            else
                VPcurve.add(countVictoryPoints() - pointsBeforeBuys);
        } else {
            if (isCumulative)
                VPcurve.set(getTurns() - 1, VPcurve.get(getTurns() - 1) + countVictoryPoints());
            else
                VPcurve.set(getTurns() - 1, VPcurve.get(getTurns() - 1) + countVictoryPoints() - pointsBeforeBuys);
        }
    }

    /**
     *
     */
    public void updateMoneyCurve() {
        if (moneyCurve.size() < getTurns()) {
            //initialize curves
            moneyCurve.add(availableCoins);
        } else {
            moneyCurve.set(getTurns() - 1, moneyCurve.get(getTurns() - 1) + availableCoins);
        }
    }

    /**
     * @return
     */
    protected double getTotalTreasure() {
        return deck.getTotalTreasure();
    }

    /**
     * @return
     */
    public int countAllCards() {
        return deck.countAllCards();
    }

    /**
     * @param aCardName
     * @return
     */
    protected boolean tryToBuy(DomCardName aCardName, boolean checkSuicide) {
        if (game.countInSupply(aCardName) == 0) {
//          if (DomEngine.haveToLog) DomEngine.addToLog( aCardName + " is no more available to buy");
            return false;
        }
        if (cantBuyActions && aCardName.hasCardType(DomCardType.Action))
            return false;

        if (forbiddenCardsToBuy.contains(aCardName))
            return false;

        if (aCardName == DomCardName.Grand_Market && !getCardsFromPlay(DomCardName.Copper).isEmpty())
            return false;

        if (checkSuicide && suicideIfBuys(aCardName)) {
            if (DomEngine.haveToLog) DomEngine.addToLog(
                    "<FONT style=\"BACKGROUND-COLOR: red\">SUICIDE!</FONT> Can not buy " + aCardName.toHTML());
            return false;
        }
//        if (!isHumanOrPossessedByHuman() && coffers > 0 && getDesiredCard(getAvailableCurrencyWithoutTokens(), false) != aCardName && checkIfWantsToHoardCoffers() && !wants(DomCardName.Gardens)) {
//            return false;
//        }
        DomCard theCardToBuy = game.takeFromSupply(aCardName);
        buy(theCardToBuy);
        return true;
    }

    private void payForAndResolveEvent(DomCardName aCardName) {
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " buys event: " + aCardName.toHTML());
        DomCard theCard = aCardName.createNewCardInstance();
        theCard.owner = this;
        theCard.play();
        availableCoins -= aCardName.getCost().getCoins();
        if (availableCoins < 0) {
            spendCoffers(-availableCoins);
            availableCoins = 0;
        }
        addDebt(aCardName.getCost(getCurrentGame()).getDebt());
    }

    private void payForAndBuildProject(DomCardName aCardName) {
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " buys Project: " + aCardName.toHTML());
        availableCoins -= aCardName.getCost().getCoins();
        if (availableCoins < 0) {
            spendCoffers(-availableCoins);
            availableCoins = 0;
        }
        if (aCardName == DomCardName.Fleet && !hasBuiltProject(DomCardName.Fleet))
            setFleetTurnLeft(true);
        myProjects.add(aCardName);
    }

    /**
     * @param aCard
     */
    public void handleSpecialBuyEffects(DomCard aCard) {
        while (charmReminder > 0) {
            if (isHumanOrPossessedByHuman()) {
                ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
                for (DomCardName theCard : getCurrentGame().getBoard().keySet()) {
                    if (theCard.getCost(getCurrentGame()).customCompare(aCard.getCost(getCurrentGame()))==0 && getCurrentGame().countInSupply(theCard)>0 && theCard!=aCard.getName())
                        theChooseFrom.add(theCard);
                }
                if (theChooseFrom.isEmpty())
                    break;
                DomCardName theChosenCard = getEngine().getGameFrame().askToSelectOneCard("Gain a card for " + this.getName().toString(), theChooseFrom, "Don't gain anything!");
                if (theChosenCard!=null) {
                    gain(theChosenCard);
                    charmReminder--;
                } else {
                    break;
                }
            } else {
                DomCardName theDifferentCard = getDesiredCardWithRestriction(null, aCard.getCost(getCurrentGame()), true, aCard.getName());
                if (theDifferentCard == null)
                    break;
                gain(theDifferentCard);
                charmReminder--;
            }
        }
        for (DomCard theHaggler : getCardsFromPlay(DomCardName.Haggler)) {
            ((HagglerCard) theHaggler).haggleFor(aCard);
        }
        int theGoonsCount = getCardsFromPlay(DomCardName.Goons).size();
        if (theGoonsCount > 0) {
            addVP(theGoonsCount);
        }
        for (int i = 0; i < getCardsFromPlay(DomCardName.Talisman).size(); i++) {
            if (new DomCost(4, 0).customCompare(aCard.getCost(getCurrentGame())) >= 0
                    && !aCard.hasCardType(DomCardType.Victory)) {
                DomCard theDouble = getCurrentGame().takeFromSupply(aCard.getName());
                if (theDouble != null) {
                    gain(theDouble);
                }
            }
        }
        if (aCard.getName() == DomCardName.Messenger && boughtCards.size() == 0) {
            ((MessengerCard)aCard).handleFirstBuy();
        }
        for (int i = 0; i < getCardsFromPlay(DomCardName.Hoard).size(); i++) {
            if (aCard.hasCardType(DomCardType.Victory)) {
                DomCard theGold = getCurrentGame().takeFromSupply(DomCardName.Gold);
                if (theGold != null) {
                    gain(theGold);
                }
            }
        }
        for (int i = 0; i < game.getEmbargoTokensOn(aCard.name); i++) {
            DomCard theCurse = getCurrentGame().takeFromSupply(DomCardName.Curse);
            if (theCurse != null) {
                gain(theCurse);
            }
        }

        if (game.getTaxOn(aCard.name) > 0) {
            addDebt(game.getBoard().removeTaxFrom(aCard.name));
        }

        if (aCard.getName() == DomCardName.Mint) {
            for (DomCard theCard : getCardsFromPlay(DomCardType.Treasure)) {
                trash(removeCardFromPlay(theCard));
            }
        }
        if (aCard.getName() == DomCardName.Farmland) {
            ((FarmlandCard) aCard).remodelSomething();
        }
        if (aCard.getName() == DomCardName.Doctor) {
            ((DoctorCard) aCard).doWhenBought();
        }
        if (aCard.getName() == DomCardName.Masterpiece) {
            //fix for interaction with Trader
            ((MasterpieceCard) aCard).doWhenBought(this);
        }
        if (aCard.getName() == DomCardName.Herald) {
            ((HeraldCard) aCard).doWhenBought();
        }
        if (aCard.getName() == DomCardName.Noble_Brigand) {
            ((Noble_BrigandCard) aCard).attack(this);
        }
        if (aCard.getName() == DomCardName.Stonemason) {
            ((StonemasonCard)aCard).doWhenBought();
        }
        if (aCard.getName() == DomCardName.Port) {
            ((PortCard) aCard).doWhenBought();
        }
        if (aCard.getName() == DomCardName.Forum) {
            ((ForumCard)aCard).doWhenBought();
        }
        if (aCard.getName() == DomCardName.Farrier) {
            ((FarrierCard) aCard).doWhenBought();
        }

    }

    public void buy(DomCard aCard) {
        boolean alternativeCostPaid = false;
        if (aCard.getName()==DomCardName.Animal_Fair) {
            alternativeCostPaid = handleAnimalFair(aCard);
        }
        if (!alternativeCostPaid) {
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " buys a " + aCard);
            availableCoins -= aCard.getCoinCost(getCurrentGame());
            if (availableCoins < 0) {
                spendCoffers(-availableCoins);
                if (coffers < 0) {
                    LOGGER.error("coffers: " + coffers);
                }
                availableCoins = 0;
            }
            availablePotions -= aCard.getPotionCost();
        }
        if (availableCoins >= 2 && getCurrentGame().getBoard().isLandmarkActive(DomCardName.Basilica)) {
            int theVP = getCurrentGame().getBoard().removeVPFrom(DomCardName.Basilica, 2);
            if (theVP > 0) {
                if (DomEngine.haveToLog) DomEngine.addToLog(this + " takes VP from " + DomCardName.Basilica.toHTML());
                addVP(theVP);
            }
        }
        addDebt(aCard.getCost(getCurrentGame()).getDebt());
        //a bit dirty to set the owner here, but needed for the buy effects
        aCard.owner=this;
        handleSpecialBuyEffects(aCard);
        deck.gain(aCard);
        boughtCards.add(aCard);

        if (getTrashingTokenOn() == aCard.isFromPile())
            maybeTrashACardForPlan();
        if (debt > 0 && !isHumanOrPossessedByHuman()) {
            payOffDebt();
        }
        if (aCard.hasCardType(DomCardType.Action) && getCurrentGame().getBoard().isLandmarkActive(DomCardName.Colonnade) && !getCardsFromPlay(aCard.getName()).isEmpty()) {
            int theVP = getCurrentGame().getBoard().removeVPFrom(DomCardName.Colonnade, 2);
            if (theVP > 0) {
                if (DomEngine.haveToLog) DomEngine.addToLog(this + " takes VP from " + DomCardName.Colonnade.toHTML());
                addVP(theVP);
            }
        }
        if (aCard.getName() == DomCardName.Curse && getCurrentGame().getBoard().isLandmarkActive(DomCardName.Defiled_Shrine)) {
            int theVP = getCurrentGame().getBoard().removeVPFrom(DomCardName.Defiled_Shrine, getCurrentGame().getBoard().countVPon(DomCardName.Defiled_Shrine));
            if (theVP > 0) {
                if (DomEngine.haveToLog)
                    DomEngine.addToLog(this + " takes VP from " + DomCardName.Defiled_Shrine.toHTML());
                addVP(theVP);
            }
        }
        handleSwampHags();
        handleHauntedWoods();
        if (aCard.hasCardType(DomCardType.Victory) && !getCardsFromHand(DomCardName.Hovel).isEmpty()) {
          if (isHumanOrPossessedByHuman()
            && getEngine().getGameFrame().askPlayer("<html>Trash " + DomCardName.Hovel.toHTML() +" ?</html>", "Resolving Hovel" ))
                trash(removeCardFromHand(getCardsFromHand(DomCardName.Hovel).get(0)));
          else
            trash(removeCardFromHand(getCardsFromHand(DomCardName.Hovel).get(0)));
        }
    }

    private boolean handleAnimalFair(DomCard aCard) {
        boolean alternativeCostPaid = true;
        if (isHumanOrPossessedByHuman()) {
            ArrayList<DomCard> theChooseFrom;
            if (aCard.getCost(getCurrentGame()).customCompare(getTotalAvailableCurrency())>0) {
                if (getCardsFromHand(DomCardType.Action).isEmpty())
                    return false;
                theChooseFrom = new ArrayList<DomCard>();
                theChooseFrom.addAll(getCardsFromHand(DomCardType.Action));
                DomCard theChosenCard = getEngine().getGameFrame().askToSelectOneCardWithDomCard("Trash a card", theChooseFrom, "Mandatory!");
                trash(removeCardFromHand(theChosenCard));
                alternativeCostPaid=true;
            } else {
                if (getCardsFromHand(DomCardType.Action).isEmpty())
                    return false;
                theChooseFrom = new ArrayList<DomCard>();
                theChooseFrom.addAll(getCardsFromHand(DomCardType.Action));
                DomCard theChosenCard = getEngine().getGameFrame().askToSelectOneCardWithDomCard("Trash a card?", theChooseFrom, "Pay with coins instead");
                if (theChosenCard==null) {
                    return false;
                } else {
                    trash(removeCardFromHand(theChosenCard));
                    alternativeCostPaid = true;
                }
            }
        } else {
            ArrayList<DomCard> ruinsInHand = getCardsFromHand(DomCardType.Ruins);
            if (!ruinsInHand.isEmpty()) {
                trash(removeCardFromHand(ruinsInHand.get(0)));
            } else {
                ArrayList<DomCard> necroInHand = getCardsFromHand(DomCardName.Necropolis);
                if (!necroInHand.isEmpty())
                    trash(removeCardFromHand(necroInHand.get(0)));
                else
                    alternativeCostPaid = false;
            }
        }
        if (alternativeCostPaid)
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " buys a " + aCard + " by paying the alternative cost");
        return alternativeCostPaid;
    }

    private void handleSwampHags() {
        if (!getCurrentGame().isInKingDom(DomCardName.Swamp_Hag))
            return;
        for (DomPlayer theOpp : getOpponents()) {
            for (DomCard theHag : theOpp.getCardsFromPlay(DomCardName.Swamp_Hag)) {
                if (((Swamp_HagCard) theHag).hasProtectedOpponent(this)) {
                    if (DomEngine.haveToLog) DomEngine.addToLog(this + " is protected from " + theHag);
                } else {
                    if (DomEngine.haveToLog) DomEngine.addToLog(theHag + " from player " + theOpp + " attacks!");
                    gain(DomCardName.Curse);
                }
            }
        }
    }

    private boolean handleEnchantress(DomCard aCard) {
        if (!getCurrentGame().isInKingDom(DomCardName.Enchantress))
            return false;
        for (DomPlayer theOpp : getOpponents()) {
            for (DomCard theEnchantress : theOpp.getCardsFromPlay(DomCardName.Enchantress)) {
                if (((EnchantressCard) theEnchantress).hasProtectedOpponent(this)) {
                    if (DomEngine.haveToLog) DomEngine.addToLog(this + " is protected from " + theEnchantress);
                    return false;
                } else {
                    if (DomEngine.haveToLog)
                        DomEngine.addToLog(this + " plays " + aCard + ", but " + theEnchantress + " magically turns it into a cantrip!");
                    return true;
                }
            }
        }
        return false;
    }

    private void maybeTrashACardForPlan() {
        if (getCardsInHand().isEmpty())
            return;
        if (isHumanOrPossessedByHuman()) {
            ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
            for (DomCard theCard : getCardsInHand()) {
                theChooseFrom.add(theCard.getName());
            }
            DomCardName theChosenCard = getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Don't trash");
            if (theChosenCard!=null) {
                trash(removeCardFromHand(getCardsFromHand(theChosenCard).get(0)));
            }
        } else {
            Collections.sort(getCardsInHand(), DomCard.SORT_FOR_TRASHING);
            if (getCardsInHand().get(0).getTrashPriority() <= DomCardName.Copper.getTrashPriority())
                trash(removeCardFromHand(getCardsInHand().get(0)));
        }
    }

    private void handleHauntedWoods() {
        if (!getCurrentGame().isInKingDom(DomCardName.Haunted_Woods))
            return;
        for (DomPlayer theOpp : getOpponents()) {
            if (!theOpp.getCardsFromPlay(DomCardName.Haunted_Woods).isEmpty()) {
                DomCard theHauntedWoods = theOpp.getCardsFromPlay(DomCardName.Haunted_Woods).get(0);
                if (((Haunted_WoodsCard) theHauntedWoods).hasProtectedOpponent(this)) {
                    if (DomEngine.haveToLog) DomEngine.addToLog(this + " is protected from " + theHauntedWoods);
                } else {
                    if (isHumanOrPossessedByHuman()) {
                        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
                        getEngine().getGameFrame().askToSelectCards("<html>Choose <u>order</u> (first card = top card)</html>" , getCardsInHand(), theChosenCards, getCardsInHand().size());
                        for (int i=theChosenCards.size()-1;i>=0;i--) {
                            for (DomCard theCard : getCardsInHand()) {
                                if (theChosenCards.get(i).getName()==theCard.getName()) {
                                    putOnTopOfDeck(theCard);
                                    cardsInHand.remove(theCard);
                                    break;
                                }
                            }
                        }
                    } else {
                        for (DomCard theCard : cardsInHand) {
                            putOnTopOfDeck(theCard);
                        }
                    }
                    cardsInHand.clear();
                    return;
                }
            }
        }
    }

    /**
     * @param theCard
     * @return
     */
    public DomCard removeCardFromHand(DomCard theCard) {
        return cardsInHand.remove(cardsInHand.indexOf(theCard));
    }

    public void doActionPhase() {
        setPhase(DomPhase.Action);
        //we might get here with no actions left due to Cavalry
        if (actionsLeft==0)
            return;
        long theTime = System.currentTimeMillis();
        DomCard theCardToPlay = null;
        do {
            theCardToPlay = getNextActionToPlay();
            if (theCardToPlay != null) {
                if (actionsLeft==0)
                  useVillager();
                actionsLeft--;
                handleUrchins(theCardToPlay);
                if (getCurrentGame().getBoard().containsShadowCards() && !getCardsInHand().contains(theCardToPlay)) {
                    play(removeCardFromDeck(theCardToPlay));
                } else {
                    play(removeCardFromHand(theCardToPlay));
                }
                handleFrigates();
                maybeHandleRoyalCarriage(theCardToPlay);
                if (actionsLeft == 0 && getFromTavernMat(DomCardName.Coin_of_the_Realm) != null && getNextActionToPlay() != null) {
                    handleCoinOfTheRealm();
                }
            }
        } while (actionsLeft + villagers> 0 && theCardToPlay != null);
        actionTime += System.currentTimeMillis() - theTime;
    }

    private DomCard removeCardFromDeck(DomCard theCardToPlay) {
        getDeck().getDrawDeck().remove(theCardToPlay);
        return theCardToPlay;
    }

    private void handleFrigates() {
        if (!getCurrentGame().isInKingDom(DomCardName.Frigate))
            return;
        for (DomPlayer theOpp : getOpponents()) {
            for (DomCard theFrigate : theOpp.getCardsFromPlay(DomCardName.Frigate)) {
                if (((FrigateCard) theFrigate).hasProtectedOpponent(this)) {
                    if (DomEngine.haveToLog) DomEngine.addToLog(this + " is protected from " + theFrigate);
                } else {
                    if (DomEngine.haveToLog) DomEngine.addToLog(theFrigate + " from player " + theOpp + " attacks!");
                    if (cardsInHand.size()>4)
                      doForcedDiscard(cardsInHand.size()-4,false);
                }
            }
        }

    }

    public void useVillager() {
        if (villagers==0) {
            LOGGER.error("Tried to use Villager, but none were left");
            System.exit(1);
        }
        villagers--;
        if (DomEngine.haveToLog)
            DomEngine.addToLog(this + " uses a Villager and has " + villagers + " villager" + (villagers==1? "":"s") +" left");
        addActions(1);
    }

    private void maybeHandleRoyalCarriage(DomCard theCardToPlay) {
        if (getFromTavernMat(DomCardName.Royal_Carriage)==null)
            return;
        if (isHumanOrPossessedByHuman()) {
            do {
                setNeedsToUpdateGUI();
                if (getEngine().getGameFrame().askPlayer("<html>Call " + DomCardName.Royal_Carriage.toHTML() + " ?</html>", "Call Royal Carriage")) {
                    DomCard theRoyalCarriage = removeFromTavernMat(getFromTavernMat(DomCardName.Royal_Carriage));
                    cardsInPlay.add(theRoyalCarriage);
                    if (DomEngine.haveToLog)
                        DomEngine.addToLog(this + " calls " + DomCardName.Royal_Carriage.toHTML() + " from the tavern mat");
                    ((Royal_CarriageCard)theRoyalCarriage).doWhenCalled();
                } else {
                    break;
                }
            } while (getFromTavernMat(DomCardName.Royal_Carriage) != null);
        } else {
            if (cardsInPlay.contains(theCardToPlay) && theCardToPlay.getName() != DomCardName.Tactician && theCardToPlay.getName()!=DomCardName.Bridge_Troll ) {
                if (theCardToPlay.getName() == DomCardName.Bridge && getPlayStrategyFor(getFromTavernMat(DomCardName.Royal_Carriage)) == DomPlayStrategy.bigTurnBridge) {
                    if (countOnTavernMat(DomCardName.Royal_Carriage) >= 6) {
                        handleRoyalCarriages();
                    } else {
                        if (count(DomCardName.Royal_Carriage) < 5) {
                            int theRCs = countOnTavernMat(DomCardName.Royal_Carriage);
                            if (getTotalPotentialCurrency().getCoins() >= 8 - 3 * theRCs)
                                handleRoyalCarriages();
                        }
                    }
                } else {
                    if (getPlayStrategyFor(getFromTavernMat(DomCardName.Royal_Carriage)) != DomPlayStrategy.bigTurnBridge)
                      handleRoyalCarriages();
                }
            }
        }
    }

    private void handleMidGameRoyalCarriageForBridge() {
        DomCard theRoyalCarriage = removeFromTavernMat(getFromTavernMat(DomCardName.Royal_Carriage));
        while (getTotalPotentialCurrency().getCoins()<DomCardName.Royal_Carriage.getCoinCost(this)*2) {
            getCardsInPlay().add(theRoyalCarriage);
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " calls " + theRoyalCarriage + " from the tavern mat");
            theRoyalCarriage.doWhenCalled();
            if (getFromTavernMat(DomCardName.Royal_Carriage) == null)
                theRoyalCarriage = null;
            else
                theRoyalCarriage = removeFromTavernMat(getFromTavernMat(DomCardName.Royal_Carriage));
        }
    }

    private void handleRoyalCarriages() {
        DomCard theRoyalCarriage = removeFromTavernMat(getFromTavernMat(DomCardName.Royal_Carriage));
        while (theRoyalCarriage != null) {
            getCardsInPlay().add(theRoyalCarriage);
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " calls " + theRoyalCarriage + " from the tavern mat");
            theRoyalCarriage.doWhenCalled();
            if (getFromTavernMat(DomCardName.Royal_Carriage) == null)
                theRoyalCarriage = null;
            else
                theRoyalCarriage = removeFromTavernMat(getFromTavernMat(DomCardName.Royal_Carriage));
        }
    }

    public void handleUrchins(DomCard theCardToPlay) {
        if (!theCardToPlay.hasCardType(DomCardType.Attack))
            return;
        if (getCardsFromPlay(DomCardName.Urchin).isEmpty())
            return;
        if (isHumanOrPossessedByHuman()) {
            while (!getCardsFromPlay(DomCardName.Urchin).isEmpty() && getEngine().getGameFrame().askPlayer("<html>Trash " + DomCardName.Urchin.toHTML() +" to gain "+DomCardName.Mercenary.toHTML()+" ?</html>", "Resolving " + this.getName().toString())){
                trash(removeCardFromPlay(getCardsFromPlay(DomCardName.Urchin).get(0)));
                gain(DomCardName.Mercenary);
            }
        } else {
            while (!getCardsFromPlay(DomCardName.Urchin).isEmpty() && wants(DomCardName.Mercenary)) {
                trash(removeCardFromPlay(getCardsFromPlay(DomCardName.Urchin).get(0)));
                gain(DomCardName.Mercenary);
            }
        }
    }

    private void handleCoinOfTheRealm() {
        if (isHumanOrPossessedByHuman()) {
            do {
                setNeedsToUpdateGUI();
                if (getEngine().getGameFrame().askPlayer("<html>Call " + DomCardName.Coin_of_the_Realm.toHTML() + " ?</html>", "Call CotR")) {
                    cardsInPlay.add(removeFromTavernMat(getFromTavernMat(DomCardName.Coin_of_the_Realm)));
                    if (DomEngine.haveToLog)
                        DomEngine.addToLog(this + " calls " + DomCardName.Coin_of_the_Realm.toHTML() + " from the tavern mat");
                    addActions(2);
                } else {
                    break;
                }
            } while (getFromTavernMat(DomCardName.Coin_of_the_Realm)!=null);
            if ((actionsLeft==0 && villagers==0)|| getCardsFromHand(DomCardType.Action).isEmpty())
                setPhase(DomPhase.Buy_PlayTreasures);
        } else {
            getCardsInPlay().add(removeFromTavernMat(getFromTavernMat(DomCardName.Coin_of_the_Realm)));
            if (DomEngine.haveToLog)
                DomEngine.addToLog(this + " calls " + DomCardName.Coin_of_the_Realm.toHTML() + " from the tavern mat");
            addActions(2);
        }
    }

    private void resolvePrincedCards() {
        Collections.sort(princedCards, DomCard.SORT_FOR_PLAYING);
        for (DomCard theCard : princedCards) {
            if (DomEngine.haveToLog)
                DomEngine.addToLog(" <html><i>( " + theCard + " set aside with Prince)</i></html>");
            theCard.markForPrince();
            play(theCard);
        }
        princedCards.clear();
    }

    public void setPhase(DomPhase aPhase) {
        if (aPhase == DomPhase.Buy_PlayTreasures) {
            maybeHandleArena();
            if (getCurrentGame().getArtifactOwner(DomArtifact.Treasure_Chest)==this) {
                if (DomEngine.haveToLog) DomEngine.addToLog(this + " resolves Artifact:" + DomArtifact.Treasure_Chest);
                gain(DomCardName.Gold);
            }
            if (isDeluded()) {
                setCantBuyActions(true);
                setDeluded(false);
            }
            if (isEnvious()) {
                setEnviousActive(true);
                setEnvious(false);
            }
        }
        currentPhase = aPhase;
        myEngine.setStatus("Now in " + currentPhase + " Phase");
    }

    private void maybeHandleArena() {
        if (isHumanOrPossessedByHuman()) {
            setNeedsToUpdateGUI();
            if ( getCurrentGame().getBoard().isLandmarkActive(DomCardName.Arena)
                    && !getCardsFromHand(DomCardType.Action).isEmpty() && getEngine().getGameFrame().askPlayer("<html>Discard an action?</html>", "Resolving " + this.getName().toString())){
                ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
                for (DomCard theCard : getCardsFromHand(DomCardType.Action)) {
                    theChooseFrom.add(theCard.getName());
                }
                DomCardName theChosenCard = getEngine().getGameFrame().askToSelectOneCard("Discard", theChooseFrom, "Mandatory!");
                discardFromHand(theChosenCard);
                int theVP = getCurrentGame().getBoard().removeVPFrom(DomCardName.Arena, 2);
                if (theVP > 0) {
                    if (DomEngine.haveToLog) DomEngine.addToLog(this + " takes VP from " + DomCardName.Arena.toHTML());
                    addVP(theVP);
                }
            }
        }
    }

    public DomCard getNextActionToPlay() {
        ArrayList<DomCard> theActionsToConsider = getCardsFromHand(DomCardType.Action);
        if (getCurrentGame().getBoard().containsShadowCards()) {
            for (DomCard card : getDeck().getDrawDeck()) {
               if (card.hasCardType(DomCardType.Shadow))
                   theActionsToConsider.add(card);
            }
        }
        if (theActionsToConsider.isEmpty())
            return null;
        Collections.sort(theActionsToConsider, DomCard.SORT_FOR_PLAYING);
        for (DomCard card : theActionsToConsider) {
            if (card.wantsToBePlayed())
                return card;
        }
        return null;
    }

    public DomCard getNextNightActionToPlay() {
        ArrayList<DomCard> theActionsToConsider = getCardsFromHand(DomCardType.Night);
        if (theActionsToConsider.isEmpty())
            return null;
        Collections.sort(theActionsToConsider, DomCard.SORT_FOR_PLAYING);
        for (DomCard card : theActionsToConsider) {
            if (card.wantsToBePlayed())
                return card;
        }
        return null;
    }

    /**
     * @param aCard
     */
    public void play(DomCard aCard) {
        //TODO remove this; used for bug fixing
        DomEngine.currentPlayer = this;
        if (aCard.hasCardType(DomCardType.Action)) {
            increaseActionsPlayed();
        }
        cardsInPlay.add(aCard);
        if (isHumanOrPossessedByHuman()) {
            setChanged();
            notifyObservers();
        }
        boolean mustHandleCitadel = false;
        if (getCurrentGame().getActivePlayer()==this && actionsplayed==1 && aCard.hasCardType(DomCardType.Action) && hasBuiltProject(DomCardName.Citadel)) {
            mustHandleCitadel = true;
        }
        if (DomEngine.haveToLog) {
            playAndLog(aCard);
        } else {
            playThis(aCard);
        }
        if (mustHandleCitadel) {
            handleCitadel(aCard);
        }
    }

    private void handleCitadel(DomCard aCard) {
        if (DomEngine.haveToLog) {
            DomEngine.addToLog(this + " plays " + aCard + " with Citadel");
            DomEngine.logIndentation++;
        }
        //some cards have been trashed so playing second time needs an owner assigned
        DomPlayer theOwner = aCard.owner;
        aCard.owner = this;
        playThis(aCard);
        if (DomEngine.haveToLog) {
            DomEngine.logIndentation--;
        }
        aCard.owner = theOwner;
    }

    private void playAndLog(DomCard aCard) {
        if (previousPlayedCardName != aCard.getName()) {
            if (previousPlayedCardName != null) {
                DomEngine.addToLog(name + " plays " + (sameCardCount + 1) + " " + previousPlayedCardName.toHTML()
                        + (sameCardCount > 0 ? "s" : ""));
            }
            if (!aCard.hasCardType(DomCardType.Kingdom)
                    && !aCard.hasCardType(DomCardType.Prize)
                    && aCard.getName() != DomCardName.Mercenary
                    && aCard.getName() != DomCardName.Wish
                    && aCard.getName() != DomCardName.Horse
                    && !aCard.hasCardType(DomCardType.Traveller)
                    && aCard.getName() != DomCardName.Champion
                    && aCard.getName() != DomCardName.Madman
                    && aCard.getName() != DomCardName.Necropolis
                    && aCard.getName() != DomCardName.Estate
                    && !aCard.hasCardType(DomCardType.Spirit)
                    && aCard.getName() != DomCardName.Bat  ) {
              previousPlayedCardName = aCard.getName();
            } else {
              previousPlayedCardName = null;
            }
            sameCardCount = 0;
        } else {
            sameCardCount++;
        }
        if (aCard.hasCardType(DomCardType.Kingdom)
                || aCard.hasCardType(DomCardType.Prize)
                || aCard.getName() == DomCardName.Mercenary
                || aCard.getName() == DomCardName.Wish
                || aCard.hasCardType(DomCardType.Traveller)
                || aCard.getName() == DomCardName.Champion
                || aCard.getName() == DomCardName.Necropolis
                || aCard.getName() == DomCardName.Horse
                || aCard.getName() == DomCardName.Madman
                || aCard.hasCardType(DomCardType.Spirit)
                || aCard.getName() == DomCardName.Bat) {
            DomEngine.addToLog(name + " plays " + aCard);
        }
        DomEngine.logIndentation++;
        playThis(aCard);
        DomEngine.logIndentation--;
    }

    public void playThis(DomCard aCard) {
        if (kilnTriggerd) {
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " triggers Kiln");
            if (getCurrentGame().countInSupply(aCard.getName())>0 && wants(aCard.getName())) {
              gain(getCurrentGame().takeFromSupply(aCard.getName()));
          }
          kilnTriggerd=false;
        }

        if (aCard.hasCardType(DomCardType.Action) && !getCardsFromPlay(DomCardName.Champion).isEmpty()) {
            addActions(getCardsFromPlay(DomCardName.Champion).size());
        }
        if (plusOneBuyTokenOn != null && plusOneBuyTokenOn == aCard.isFromPile()) {
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " activates plus buy token on " + aCard.isFromPile());
            addAvailableBuys(1);
        }
        if (plusOneCardTokenOn != null && plusOneCardTokenOn == aCard.isFromPile()) {
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " activates +1 Card Token on " + aCard.isFromPile());
            drawCards(1);
        }
        if (plusOneActionTokenOn != null && plusOneActionTokenOn == aCard.isFromPile()) {
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " activates +1 Action Token on " + aCard.isFromPile());
            addActions(1);
        }
        if (plusOneCoinTokenOn != null && plusOneCoinTokenOn == aCard.isFromPile()) {
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " activates +$1 Token on " + aCard.isFromPile());
            addAvailableCoins(1);
        }
        if (isHumanOrPossessedByHuman())
            setNeedsToUpdateGUI();
        if (!playedByWay(aCard)) {
            if (actionsplayed == 1 && aCard.hasCardType(DomCardType.Action) && handleEnchantress(aCard)) {
                addActions(1);
                drawCards(1);
            } else {
                aCard.play();
                if (aCard.hasCardType(DomCardType.Duration) && !aCard.durationFailed())
                    aCard.setDiscardAtCleanup(false);
            }
        }
        if (getCurrentGame().getBoard().getActiveProphecy()==DomCardName.Panic && getCurrentGame().getBoard().getProphecyCount()==0 && aCard.hasCardType(DomCardType.Treasure)) {
          addAvailableBuys(2);
        }
        cardsPlayedThisTurnCounter++;
        if (cardsPlayedThisTurnCounter==1 && aCard.hasCardType(DomCardType.Treasure) && !getCardsFromPlay(DomCardName.Landing_Party).isEmpty()) {
            for (DomCard theCard : getCardsFromPlay(DomCardName.Landing_Party)) {
                putOnTopOfDeck(removeCardFromPlay(theCard));
            }
        }
    }

    private boolean playedByWay(DomCard aCard) {
        if (!aCard.hasCardType(DomCardType.Action))
            return false;
        if (getCurrentGame().getBoard().isWayActive(DomCardName.Way_of_the_Ox)) {
            if ( actionsLeft==0 && !aCard.hasCardType(DomCardType.Village) && getNextActionToPlay()!=null && getNextActionToPlay().hasCardType(DomCardType.Terminal)) {
                if (DomEngine.haveToLog) DomEngine.addToLog(this + " plays it in the " + DomCardName.Way_of_the_Ox.toHTML());
                addActions(2);
                return true;
            }
        }
        if (getCurrentGame().getBoard().isWayActive(DomCardName.Way_of_the_Pig)) {
            if ( actionsLeft==0 && !aCard.hasCardType(DomCardType.Village) && getNextActionToPlay()!=null && getNextActionToPlay().hasCardType(DomCardType.Terminal)) {
                if (DomEngine.haveToLog) DomEngine.addToLog(this + " plays it in the " + DomCardName.Way_of_the_Pig.toHTML());
                addActions(1);
                drawCards(1);
                return true;
            }
        }
        return false;
    }

    public void discard(ArrayList<DomCard> aCards) {
        for (DomCard theCard : aCards)
            discard(theCard);
    }

    /**
     * @return
     */
    public int countTreasureFromNativeVillageMat() {
        int theTotal = 0;
        for (DomCard theCard : nativeVillageMat) {
            if (theCard.hasCardType(DomCardType.Treasure)) {
                theTotal += theCard.getPotentialCoinValue();
            }
        }
        return theTotal;
    }


    public DomPlayer getPossessor() {
        return possessor;
    }

    /**
     * @return
     */
    public ArrayList<DomCard> revealUntilType(DomCardType aType) {
        return deck.revealUntilType(aType);
    }

    /**
     * @return
     */
    public ArrayList<DomCard> revealUntilCost(int aCost) {
        return deck.revealUntilCost(aCost);
    }

    /**
     * @param aDomGame
     */
    public void setCurrentGame(DomGame aDomGame) {
        game = aDomGame;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * @param aDomGame
     */
    public void initializeForGame(DomGame aDomGame) {
        deliverCards.clear();
        deliverTriggered=false;
        avoidTriggers =0;
        tavernMat.clear();
        pprUsed = false;
        setCurrentGame(aDomGame);
        setMyEngine(aDomGame.getEngine());
        turns = 0;
        pirateShipLevel = 0;
        victoryTokens = 0;
        pointsBeforeBuys = 3;
        cardsInPlay = new ArrayList<DomCard>();
        cardsInHand = new ArrayList<DomCard>();
        horseTradersPile.clear();
        princedCards.clear();
        mySetAsideEncampments.clear();
        nativeVillageMat = new ArrayList<DomCard>();
        deck = new DomDeck(this);
        possessor = null;
        extraOutpostTurn = false;
        possessionTurns.clear();
        setKnownTopCards(0);
        if (myStartState != null) {
            putPlayerInStartState();
            if (myStartState.getHand().isEmpty())
                drawCards(5);
        } else {
            dealStartCards();
            doTrueRandomShuffle();
            while (!checkForcedStart())
                doTrueRandomShuffle();
            drawCards(5);
        }
        coffers = 0;
        favors=0;
        journeyTokenIsFaceUp = true;
        coffers = getCurrentGame().countInSupply(DomCardName.Baker) > 0 ? 1 : 0;
        favors =  getCurrentGame().isAllyPresent() ? 1 : 0;
        minusOneCardToken = false;
        minusOneCoinToken = false;
        plusOneBuyTokenOn = null;
        plusOneCardTokenOn = null;
        plusOneActionTokenOn = null;
        plusOneCoinTokenOn = null;
        trashingTokenOn = null;
        minus$2TokenOn = null;
        cardsToSummon.clear();
        cardsToReap.clear();
        foresightedCards.clear();
        debt = 0;
        deluded=false;
        envious=false;
        setExtraMissionTurn(false);
        lostInTheWoods=false;
        miserable=false;
        twiceMiserable=false;
        setAsideFaithfulHounds.clear();
        villagers=0;
        myProjects=new HashSet<>();
        ghostProcessionedCards.clear();
        setSinisterPlotTokens(0);
        hasFleetTurnLeft=false;
        investments.clear();
        cargoCards.clear();
        myPreparedCards.clear();
        miningRoadTriggers=0;
        uberDominationWinTrigger=false;
    }

    private void putPlayerInStartState() {
        for (DomCardName cardName : myStartState.getDrawDeck()) {
            if (getCurrentGame().countInSupply(cardName) == 0)
                getCurrentGame().getBoard().addCardPile(cardName);
            gainOnTopOfDeck(getCurrentGame().takeFromSupply(cardName));
        }
        for (DomCardName cardName : myStartState.getDiscard()) {
            if (getCurrentGame().countInSupply(cardName) == 0)
                getCurrentGame().getBoard().addCardPile(cardName);
            gain(getCurrentGame().takeFromSupply(cardName));
        }
        if (myStartState.isShuffleDrawDeck())
            shuffleDeck();
        for (DomCardName cardName : myStartState.getHand()) {
            if (getCurrentGame().countInSupply(cardName) == 0)
                getCurrentGame().getBoard().addCardPile(cardName);
            DomCard theCard = getCurrentGame().takeFromSupply(cardName);
            cardsInHand.add(theCard);
            getDeck().addPhysicalCard(theCard);
        }
    }

    /**
     *
     */
    public void addWin() {
        wins++;
    }

    /**
     * @return
     */
    public int getWins() {
        return wins;
    }

    /**
     * @return
     */
    public int countVictoryPoints() {
        long theTime = System.currentTimeMillis();
        int theTotalVP = victoryTokens + deck.countVictoryPoints();
        if (getCurrentGame().getBoard().isLandmarkActive(DomCardName.Fountain))
            theTotalVP += FountainCard.countVP(this);
        if (getCurrentGame().getBoard().isLandmarkActive(DomCardName.Wolf_Den))
            theTotalVP += Wolf_DenCard.countVP(this);
        if (getCurrentGame().getBoard().isLandmarkActive(DomCardName.Keep))
            theTotalVP += KeepCard.countVP(this);
        if (getCurrentGame().getBoard().isLandmarkActive(DomCardName.Museum))
            theTotalVP += MuseumCard.countVP(this);
        if (getCurrentGame().getBoard().isLandmarkActive(DomCardName.Obelisk))
            theTotalVP += ObeliskCard.countVP(this);
        if (getCurrentGame().getBoard().isLandmarkActive(DomCardName.Orchard))
            theTotalVP += OrchardCard.countVP(this);
        if (getCurrentGame().getBoard().isLandmarkActive(DomCardName.Palace))
            theTotalVP += PalaceCard.countVP(this);
        if (getCurrentGame().getBoard().isLandmarkActive(DomCardName.Tower))
            theTotalVP += TowerCard.countVP(this);
        if (getCurrentGame().getBoard().isLandmarkActive(DomCardName.Triumphal_Arch))
            theTotalVP += Triumphal_ArchCard.countVP(this);
        if (getCurrentGame().getBoard().isLandmarkActive(DomCardName.Wall))
            theTotalVP += WallCard.countVP(this);
        if (getCurrentGame().getBoard().isLandmarkActive(DomCardName.Bandit_Fort))
            theTotalVP += Bandit_FortCard.countVP(this);
        if (getCurrentGame().getBoard().getActiveAlly()==DomCardName.Plateau_Shepherds)
            theTotalVP += Plateau_ShepherdsCard.countVP(this);

        countVPTime += System.currentTimeMillis() - theTime;
        if (isMiserable())
            theTotalVP -= 2;
        if (isTwiceMiserable())
            theTotalVP -= 4;
        return theTotalVP;
    }

    /**
     * @param aI
     * @return
     */
    public Integer getMoneyCurve(int aI) {
        if (aI < moneyCurve.size())
            return moneyCurve.get(aI);
        else
            return moneyCurve.get(moneyCurve.size() - 1);
    }

    /**
     * @return
     */
    public int getTurns() {
        return turns;
    }

    /**
     * @param v
     */
    public void addTie(double v) {
        ties += v;
    }

    /**
     * @return
     */
    public double getTies() {
        return ties;
    }

    /**
     * @param discardsLeft
     * @param discardToTopOfDeck
     */
    public void doForcedDiscard(int discardsLeft, boolean discardToTopOfDeck) {
        if (discardsLeft==0||getCardsInHand().isEmpty())
            return;
        if (isHumanOrPossessedByHuman()) {
            setNeedsToUpdateGUI();
            ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
            myEngine.getGameFrame().askToSelectCards("Choose "+discardsLeft+" cards to discard" +(discardToTopOfDeck?" to top of deck":""), cardsInHand, theChosenCards, getCardsInHand().size()<discardsLeft?getCardsInHand().size():discardsLeft);
            for (DomCard theCardName: theChosenCards) {
                discard(getCardsFromHand(theCardName.getName()).get(0), discardToTopOfDeck);
            }
            return;
        }
        if (!discardToTopOfDeck) {
            //Tunnel needs absolute priority to get discarded
            while (discardsLeft > 0 && !getCardsFromHand(DomCardName.Tunnel).isEmpty()) {
                discardFromHand(getCardsFromHand(DomCardName.Tunnel).get(0));
                discardsLeft--;
            }
        }
        //first discard excess terminals
        ArrayList<DomCard> theTerminalsInHand = getCardsFromHand(DomCardType.Terminal);
        if (getCardsFromHand(DomCardName.Treasure_Map).size()>1) {
            for (DomCard theTM : getCardsFromHand(DomCardName.Treasure_Map)) {
                theTerminalsInHand.remove(theTM);
            }
        }
        if (getCurrentGame().getActivePlayer() == this)
            Collections.sort(theTerminalsInHand, DomCard.SORT_FOR_DISCARD_FROM_HAND);
        else
            Collections.sort(theTerminalsInHand, DomCard.SORT_FOR_DISCARDING);
        int theCultistCount = 0;
        for (DomCard theCard : theTerminalsInHand) {
            if (theCard.getName() == DomCardName.Cultist)
                theCultistCount++;
        }
        int i = theTerminalsInHand.size() - actionsLeft - (theCultistCount > 0 ? theCultistCount - 1 : 0);
        if (!getCardsFromPlay(DomCardName.Champion).isEmpty())
            i = 0;
        while (i > 0 && discardsLeft > 0) {
            discard(theTerminalsInHand.remove(0), discardToTopOfDeck);
            discardsLeft--;
            i--;
        }
        if (discardsLeft < 1)
            return;
        if (getCurrentGame().getActivePlayer() == this)
            Collections.sort(getCardsInHand(), DomCard.SORT_FOR_DISCARD_FROM_HAND);
        else
            Collections.sort(getCardsInHand(), DomCard.SORT_FOR_DISCARDING);
        if (!getCardsFromHand(DomCardName.Menagerie).isEmpty())
            discardsLeft = discardForMenagerie(discardsLeft, discardToTopOfDeck);
        if (getActionsAndVillagersLeft() > 0)
            checkForPossibleTrashingBeforeDiscarding(discardsLeft);
        //then discard the rest
        if (discardToTopOfDeck) {
            while (!cardsInHand.isEmpty() && discardsLeft > 0) {
                if (discardsLeft>1) {
                    discard(cardsInHand.get(0), discardToTopOfDeck);
                    discardsLeft--;
                } else {
                    int j=cardsInHand.size()-1;
                    while (j>0 && (removingReducesBuyingPower(cardsInHand.get(j)) || cardsInHand.get(j).getName()==DomCardName.Treasure_Map))
                      j--;
                    discard(cardsInHand.get(j), discardToTopOfDeck);
                    discardsLeft--;
                }
            }
        } else {
            while (!cardsInHand.isEmpty() && discardsLeft > 0) {
                discard(cardsInHand.get(0), discardToTopOfDeck);
                discardsLeft--;
            }
        }
    }

    private int discardForMenagerie(int discardsLeft, boolean discardToTopOfDeck) {
        MenagerieCard theMenagerie = (MenagerieCard) getCardsFromHand(DomCardName.Menagerie).get(0);
        ArrayList<DomCard> theCardsToDiscard = getMultiplesInHand(theMenagerie);
        if (theCardsToDiscard.isEmpty())
            return discardsLeft;
        while (discardsLeft > 0 && !theCardsToDiscard.isEmpty()) {
            discard(theCardsToDiscard.remove(0), discardToTopOfDeck);
            discardsLeft--;
        }
        return discardsLeft;
    }

    public void discard(DomCard aCard, boolean discardToTopOfDeck) {
        if (discardToTopOfDeck)
            discardFromHandToTopOfDeck(aCard);
        else
            discardFromHand(aCard);
    }

    private void checkForPossibleTrashingBeforeDiscarding(int discardsLeft) {
        if (cardsInHand.size() <= discardsLeft + 1
                || getCardsFromHand(DomCardType.Trasher).isEmpty())
            return;

        int theNumberOfCardsNeededForTrashing = 2;

        ArrayList<DomCard> theCardsNeededForTrashing = new ArrayList<DomCard>();
        for (DomCard card : cardsInHand) {
            if (card.getTrashPriority() < 16)
                theCardsNeededForTrashing.add(card);
        }

        if (theCardsNeededForTrashing.isEmpty())
            return;

//    	if (!getCardsFromHand(DomCardName.Bishop).isEmpty()
//    	 || !getCardsFromHand(DomCardName.Ambassador).isEmpty()	
//    	 || !getCardsFromHand(DomCardName.Develop).isEmpty()	
//    	 || !getCardsFromHand(DomCardName.Expand).isEmpty()	
//    	 || !getCardsFromHand(DomCardName.Masquerade).isEmpty()	
//    	 || !getCardsFromHand(DomCardName.Remodel).isEmpty()	
//    	 || !getCardsFromHand(DomCardName.Salvager).isEmpty()	
//    	 || !getCardsFromHand(DomCardName.Trader).isEmpty()	
//    	 || !getCardsFromHand(DomCardName.Trade_Route).isEmpty()	
//    	 || !getCardsFromHand(DomCardName.Transmute).isEmpty()){
//    	}

        if (!getCardsFromHand(DomCardName.Steward).isEmpty()
                || !getCardsFromHand(DomCardName.Trading_Post).isEmpty()
                || !getCardsFromHand(DomCardName.Remake).isEmpty()) {
            if (theCardsNeededForTrashing.size() < 2)
                return;
            theNumberOfCardsNeededForTrashing = 3;
        }
        if (!getCardsFromHand(DomCardName.Chapel).isEmpty()
                || !getCardsFromHand(DomCardName.Forge).isEmpty()) {
            if (theCardsNeededForTrashing.size() > 4)
                theNumberOfCardsNeededForTrashing = 5;
            else
                theNumberOfCardsNeededForTrashing = theCardsNeededForTrashing.size() + 1;
            if (theNumberOfCardsNeededForTrashing > cardsInHand.size() - discardsLeft)
                theNumberOfCardsNeededForTrashing = cardsInHand.size() - discardsLeft;
        }

        if (cardsInHand.size() - discardsLeft < theNumberOfCardsNeededForTrashing)
            return;

        if (theNumberOfCardsNeededForTrashing <= 1)
            return;

        DomCost theTotalMoney = getTotalAvailableCurrency();
        for (int i = discardsLeft; i < cardsInHand.size(); i++) {
            theTotalMoney.add(cardsInHand.get(i).getPotentialCurrencyValue());
        }
        if (isGoingToBuyTopCardInBuyRules(theTotalMoney))
            return;

        Collections.sort(theCardsNeededForTrashing, DomCard.SORT_FOR_TRASHING);
        //now we move the cards to trash and trasher to the back of the hand
        for (int j = 0; j < theNumberOfCardsNeededForTrashing - 1; j++) {
            cardsInHand.add(removeCardFromHand(theCardsNeededForTrashing.get(j)));
        }
        cardsInHand.add(removeCardFromHand(getCardsFromHand(DomCardType.Trasher).get(0)));
    }

    public boolean isGoingToBuyTopCardInBuyRules(DomCost theTotalMoney) {
        for (DomBuyRule theRule : getBuyRules()) {
            //we don't want to mess with a hand if we're going to buy the top card this turn (although we could)
            if (wantsToGainOrKeep(theRule.getCardToBuy())) {
                return getDesiredCard(theTotalMoney, false) == theRule.getCardToBuy();
            }
        }
        return false;
    }

    public ArrayList<DomCard> getCardsFromHand(DomCardType aCardType) {
        ArrayList<DomCard> theCards = new ArrayList<DomCard>();
        for (DomCard theCard : cardsInHand) {
            if (theCard.hasCardType(aCardType)) {
                theCards.add(theCard);
            }
        }
        return theCards;
    }

    public ArrayList<DomCard> getCardsFromPlay(DomCardType aCardType) {
        ArrayList<DomCard> theCards = new ArrayList<DomCard>();
        for (DomCard theCard : cardsInPlay) {
            if (theCard.hasCardType(aCardType)) {
                theCards.add(theCard);
            }
        }
        return theCards;
    }

    /**
     * @param aCardToDiscard
     */
    public void discardFromHand(DomCard aCardToDiscard) {
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " discards a " + aCardToDiscard);
        deck.discard(removeCardFromHand(aCardToDiscard));
    }

    public void discardFromHandToTopOfDeck(DomCard aCardToDiscard) {
        putOnTopOfDeck(removeCardFromHand(aCardToDiscard));
    }

    /**
     * @return
     */
    public boolean discardFromHand(DomCardName aCardName) {
        for (DomCard theCard : cardsInHand) {
            if (theCard.getName().equals(aCardName)) {
                discardFromHand(theCard);
                return true;
            }
        }
        return false;
    }

    /**
     * @param aCardName
     * @return
     */
    public void gain(DomCardName aCardName) {
        gain(getCurrentGame().takeFromSupply(aCardName));
    }


    /**
     */
    public void gain(DomCard aCard) {
        if (aCard != null) {
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " gains a " + aCard);
            deck.gain(aCard);
        }
    }

    /**
     */
    public void gainInHand(DomCardName aCardName) {
        if (getCurrentGame().countInSupply(aCardName) > 0)
            gainInHand(game.takeFromSupply(aCardName));
    }

    public void gainInHand(DomCard aCard) {
        deck.gain(aCard, DomDeck.HAND);
    }

    /**
     * @param aCardToTrash
     */
    public void trash(DomCard aCardToTrash) {
        if (aCardToTrash.getShapeshifterCard()!=null){
            aCardToTrash=aCardToTrash.getShapeshifterCard();
        }
        if (aCardToTrash == null)
            return;
        if (resolvingSewers)
            if (DomEngine.haveToLog) DomEngine.addToLog("Trashing with " + DomCardName.Sewers.toHTML() + " is active");
        if (aCardToTrash.owner == null || possessor == null) {
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " trashes a " + aCardToTrash);
            game.addToTrash(aCardToTrash);
        }
        if (!getCardsFromHand(DomCardName.Market_Square).isEmpty() && getCardsFromPlay(DomCardName.Forge).isEmpty()) {
            for (DomCard theMS : getCardsFromHand(DomCardName.Market_Square)) {
                if (isHumanOrPossessedByHuman()) {
                    if (getEngine().getGameFrame().askPlayer("<html>Discard " + DomCardName.Market_Square.toHTML() +" ?</html>", "Resolving " + this.getName().toString())) {
                        discardFromHand(theMS);
                        gain(DomCardName.Gold);
                    } else
                        break;
                } else {
                    discardFromHand(theMS);
                    gain(DomCardName.Gold);
                }
            }
        }
        if (aCardToTrash.owner != null) {
            aCardToTrash.doWhenTrashed();
            if (aCardToTrash.getName()!=DomCardName.Trail)
              deck.trash(aCardToTrash);
        } else {
            //fix for Lurker and Salt the Earth
            aCardToTrash.owner=this;
            aCardToTrash.doWhenTrashed();
            aCardToTrash.owner=null;
        }
        if (aCardToTrash.getName() == DomCardName.Fortress) {
            game.getTrashedCards().remove(aCardToTrash);
            deck.addPhysicalCardWhenNotGained(aCardToTrash);
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " adds " +DomCardName.Fortress.toHTML() +" to hand");
            cardsInHand.add(aCardToTrash);
        }
        if (getCurrentGame().getBoard().isLandmarkActive(DomCardName.Tomb)) {
            if (DomEngine.haveToLog) DomEngine.addToLog("Landmark " + DomCardName.Tomb.toHTML() + " is active");
            addVP(1);
        }
        if (myProjects.contains(DomCardName.Sewers) && !resolvingSewers) {
            DomCard theSewers = DomCardName.Sewers.createNewCardInstance();
            theSewers.setOwner(this);
            theSewers.trigger();
        }
        if (getTrashingBonus()>0)
            addAvailableCoins(2*getTrashingBonus());
        numberOfTrashedCards++;
    }

    /**
     *
     */
    public void discardTopCardFromDeck() {
        deck.discardTopCardFromDeck();
    }

    /**
     * @param aCard
     */
    public void gainOnTopOfDeck(DomCard aCard) {
        deck.gain(aCard, DomDeck.TOP_OF_DECK);
    }

    /**
     * @return
     */
    public int getHandSize() {
        return cardsInHand.size();
    }

    /**
     * @param aI
     * @return
     */
    public double getVPCurve(int aI) {
        if (aI < VPcurve.size())
            return VPcurve.get(aI);
        else
            return VPcurve.get(VPcurve.size() - 1);
    }

    /**
     * @param aCardName
     * @return
     */
    public boolean suicideIfBuys(DomCardName aCardName) {
        if (possessor != null) {
            return possessor.suicideIfBuys(aCardName);
        }

        if (getTypes().contains(DomBotType.AppliesPPR)
                && (aCardName == DomCardName.Province || aCardName == DomCardName.Colony)
                && game.countInSupply(aCardName) == 2
                && checkPenultimateProvinceRule(aCardName)) {
//            System.out.println("PPR! " + getCurrentGame());
            return true;
        }


        if (game.countInSupply(aCardName) != 1)
            return false;

        if (game.countEmptyPiles() < 2) {
            if (aCardName != DomCardName.Province && aCardName != DomCardName.Colony) {
                return false;
            }
        }
        //temporarily add the card to the deck
        DomCard theCard = getCurrentGame().takeFromSupply(aCardName);
        getDeck().forcedAdd(theCard);
        for (DomPlayer thePlayer : getOpponents()) {
            if (countVictoryPoints() < thePlayer.countVictoryPoints()
              || (countVictoryPoints() == thePlayer.countVictoryPoints() && getTurns() > thePlayer.getTurns())) {
                //remove it again
                getCurrentGame().getBoard().add(getDeck().forcedRemove(theCard));
                return true;
            }
        }
        //remove it again
        getCurrentGame().getBoard().add(getDeck().forcedRemove(theCard));

//        for (DomPlayer thePlayer : getOpponents()) {
//            if (countVictoryPoints() < thePlayer.countVictoryPoints() - aCardName.getVictoryValue(this)) {
//                return true;
//            }
//            if (countVictoryPoints() == thePlayer.countVictoryPoints() - aCardName.getVictoryValue(this)) {
//                if (getTurns() > thePlayer.getTurns()) {
//                    return true;
//                }
//            }
//        }
        return false;
    }

    /**
     * @param aCardName
     * @return
     */
    private boolean checkPenultimateProvinceRule(DomCardName aCardName) {
        for (DomPlayer thePlayer : getOpponents()) {
            for (DomBuyRule rule : getBuyRules()) {
                DomCardName theCard = rule.getCardToBuy();
                if (theCard == aCardName)
                    continue;
                if (getCurrentGame().countInSupply(theCard) > 0
                        && countVictoryPoints() < thePlayer.countVictoryPoints()
                        && countVictoryPoints() + theCard.getVictoryValue(this) > thePlayer.countVictoryPoints()) {
                    if (DomEngine.haveToLog) DomEngine.addToLog("Penultimate Province Rule!!!");
                    return true;
                }
            }
        }
        return false;
    }

    public double getSumTurns() {
        return sumTurns;
    }

    public void addVP(int aI) {
        if (possessor != null) {
            possessor.addVP(aI);
        } else {
            victoryTokens += aI;
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " gains +" + aI + "&#x25BC;");
        }
    }

    public void handleGameEnd() {
        if (!deck.getIslandMat().isEmpty())
          deck.returnCardsFromIslandMat();
        if (!deck.getExileMat().isEmpty())
          deck.returnCardsFromExileMat();
        deliverCards.clear();
    }

    public boolean checkDefense() {
        if (isHuman) {
            return checkHumanDefense();
        }
        while (!getCardsFromHand(DomCardName.Caravan_Guard).isEmpty()) {
            play(removeCardFromHand(getCardsFromHand(DomCardName.Caravan_Guard).get(0)));
        }
        while (!getCardsFromHand(DomCardName.Horse_Traders).isEmpty()) {
            if (DomEngine.haveToLog)
                DomEngine.addToLog(this + " sets a " + DomCardName.Horse_Traders.toHTML() + " aside");
            horseTradersPile.add(removeCardFromHand(getCardsFromHand(DomCardName.Horse_Traders).get(0)));
        }
        if (!getCardsFromHand(DomCardName.Beggar).isEmpty()) {
            ArrayList<DomCard> theBeggars = getCardsFromHand(DomCardName.Beggar);
            for (int i = 0; i < (theBeggars.get(0).wantsToBePlayed() ? theBeggars.size() - 1 : theBeggars.size()); i++) {
                discardFromHand(DomCardName.Beggar);
                if (getCurrentGame().countInSupply(DomCardName.Silver) > 0)
                    gainOnTopOfDeck(getCurrentGame().takeFromSupply(DomCardName.Silver));
                gain(DomCardName.Silver);
            }
        }
        if (!getCardsFromHand(DomCardName.Diplomat).isEmpty()) {
            ((DiplomatCard) getCardsFromHand(DomCardName.Diplomat).get(0)).react();
        }
        if (!getCardsFromHand(DomCardName.Secret_Chamber).isEmpty()) {
            ((Secret_ChamberCard) getCardsFromHand(DomCardName.Secret_Chamber).get(0)).react();
        }
        for (DomCard theCard : cardsInPlay) {
            if (theCard.getName() == DomCardName.Lighthouse) {
                if (DomEngine.haveToLog) DomEngine.addToLog(theCard + " prevents the attack!");
                return true;
            }
            if (theCard.getName() == DomCardName.Guardian) {
                if (DomEngine.haveToLog) DomEngine.addToLog(theCard + " prevents the attack!");
                return true;
            }
        }
        if (!getCardsFromHand(DomCardName.Moat).isEmpty()) {
            if (DomEngine.haveToLog)
                DomEngine.addToLog(this + " reveals a " + DomCardName.Moat.toHTML() + " from hand and prevents the attack");
            return true;
        }
        if (!getCardsFromPlay(DomCardName.Champion).isEmpty()) {
            if (DomEngine.haveToLog)
                DomEngine.addToLog(this + " has a " + DomCardName.Champion.toHTML() + " in play and prevents the attack");
            return true;
        }
        return false;
    }

    private boolean checkHumanDefense() {
        boolean defense = false;
        if (!getCardsFromPlay(DomCardName.Champion).isEmpty() || !getCardsFromPlay(DomCardName.Lighthouse).isEmpty())
            defense = true;

        DomCardName theChosenReaction;
        ArrayList<DomCard> theCardsToReset = new ArrayList<DomCard>();
        while ((theChosenReaction=revealHandToChooseNextReaction())!=null) {
            for (DomCard theCard : getCardsFromHand(theChosenReaction)) {
                if (!theCard.hasReacted()) {
                    theCardsToReset.add(theCard);
                    if (theCard.reactForHuman()) {
                        defense = true;
                    }
                    break;
                }
            }
            setNeedsToUpdateGUI();
        }
        for (DomCard theCard : theCardsToReset) {
            theCard.setReacted(false);
        }
        return defense;
    }

    private DomCardName revealHandToChooseNextReaction() {
        ArrayList<DomCardName> theCards = new ArrayList<DomCardName>();
        for (DomCard theCard : getCardsFromHand(DomCardType.Reaction)) {
            if (theCard.getName()==DomCardName.Watchtower)
                continue;
            if (theCard.getName()==DomCardName.Trader)
                continue;
            if (theCard.getName()==DomCardName.Hovel)
                continue;
            if (theCard.getName()==DomCardName.Faithful_Hound)
                continue;
            if (!theCard.hasReacted() && theCard.canReact())
              theCards.add(theCard.getName());
        }
        if (theCards.isEmpty())
            return null;
        else
            return getEngine().getGameFrame().askToSelectOneCard("React?", theCards, "Don't React");
    }

    public void resolveBeginningOfTurnForHuman() {
        fillTriggerStack();
        resolvePreparedCards();
        if (beginningOfTurnTriggers.isEmpty())
            return;
        ArrayList<DomCard> theChosenCards;
        do {
            do {
                setNeedsToUpdateGUI();
                theChosenCards = new ArrayList<DomCard>();
                myEngine.getGameFrame().askToSelectCards("Choose next beginning of turn trigger", beginningOfTurnTriggers, theChosenCards, 0);
            } while (theChosenCards.size() > 1);
            DomCard theNextCardToHandle;
            if (theChosenCards.isEmpty()) {
                //automatic handling of beginning of turn triggers
                while (!beginningOfTurnTriggers.isEmpty()) {
                    if (beginningOfTurnTriggers.get(0).hasCardType(DomCardType.Reserve)) {
                        beginningOfTurnTriggers.remove(0);
                    } else {
                        handleTriggerForHuman(beginningOfTurnTriggers.get(0));
                    }
                }
            } else {
                //human chooses order of beginning of turn triggers
                theNextCardToHandle = null;
                DomCard theChosenCard = theChosenCards.get(0);
                for (DomCard theCard : beginningOfTurnTriggers) {
                    if (theCard.getName()==theChosenCard.getName()) {
                        theNextCardToHandle = theCard;
                        break;
                    }
                }
                handleTriggerForHuman(theNextCardToHandle);
            }
        } while (!theChosenCards.isEmpty() && !beginningOfTurnTriggers.isEmpty());
    }

    private void handleTriggerForHuman(DomCard theNextCardToHandle) {
        beginningOfTurnTriggers.remove(theNextCardToHandle);
        if (theNextCardToHandle.getName()==DomCardName.Lost_In_The_Woods) {
            ArrayList<DomCard> theChosenCards = new ArrayList<>();
            getEngine().getGameFrame().askToSelectCards("Discard" , getCardsInHand(), theChosenCards, 1);
            for (DomCard theCardName: theChosenCards) {
                discard(getCardsFromHand(theCardName.getName()).get(0), false);
            }
            receiveBoon(null);
        }
        if (theNextCardToHandle.hasCardType(DomCardType.Duration)) {
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " resolves duration effect from " + theNextCardToHandle.getName().toHTML());
            theNextCardToHandle.resolveDuration();
            if (!theNextCardToHandle.mustStayInPlay())
                theNextCardToHandle.setDiscardAtCleanup(true);
        }
        if (theNextCardToHandle instanceof MultiplicationCard && !ghostProcessionedCards.contains(theNextCardToHandle)) {
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " resolves duration effect from " + theNextCardToHandle.getName().toHTML());
            for (DomCard theDuration: ((MultiplicationCard)theNextCardToHandle).getDurationCards()){
                if (theDuration.owner!=null) {
                    theDuration.resolveDuration();
                }
                if (!theDuration.mustStayInPlay())
                    theDuration.setDiscardAtCleanup(true);
            }
            theNextCardToHandle.resolveDuration();
            if (!theNextCardToHandle.mustStayInPlay())
                theNextCardToHandle.setDiscardAtCleanup(true);
        }
        if (horseTradersPile.contains(theNextCardToHandle)) {
            if (DomEngine.haveToLog)
                DomEngine.addToLog(this + " adds " + DomCardName.Horse_Traders.toHTML() + " to his hand");
            cardsInHand.add(theNextCardToHandle);
            horseTradersPile.remove(theNextCardToHandle);
            drawCards(1);
        }
        if (cardsToSummon.contains(theNextCardToHandle)) {
            play(theNextCardToHandle);
            cardsToSummon.remove(theNextCardToHandle);
        }
        if (cardsToReap.contains(theNextCardToHandle)) {
            play(theNextCardToHandle);
            cardsToReap.remove(theNextCardToHandle);
        }
        if (princedCards.contains(theNextCardToHandle)) {
            if (DomEngine.haveToLog)
                DomEngine.addToLog(" <html><i>( " + theNextCardToHandle.getName().toHTML()+ " set aside with Prince)</i></html>");
            theNextCardToHandle.play();
            theNextCardToHandle.markForPrince();
        }
        if (theNextCardToHandle.hasCardType(DomCardType.Reserve)) {
            getCardsInPlay().add(removeFromTavernMat(theNextCardToHandle));
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " calls " + theNextCardToHandle.getName().toHTML() + " from the tavern mat");
            theNextCardToHandle.doWhenCalled();
        }
        if (theNextCardToHandle.hasCardType(DomCardType.Boon)) {
            receiveBoon(delayedBoons.remove(delayedBoons.indexOf(theNextCardToHandle)));
        }
        if (theNextCardToHandle.getName()==DomCardName.Cathedral || theNextCardToHandle.getName()==DomCardName.Sinister_Plot){
            theNextCardToHandle.trigger();
        }
        if (theNextCardToHandle.getName()== DomCardName.Silos && !getCardsFromHand(DomCardName.Copper).isEmpty()) {
            theNextCardToHandle.trigger();
        }
        if (ghostProcessionedCards.contains(theNextCardToHandle)) {
            theNextCardToHandle.owner=this;
            theNextCardToHandle.resolveDuration();
            theNextCardToHandle.owner=null;
            if (theNextCardToHandle.discardAtCleanUp())
              ghostProcessionedCards.remove(theNextCardToHandle);
        }
        if (theNextCardToHandle.getName()== DomCardName.City_Gate ) {
            drawCards(1);
            doForcedDiscard(1,true);
        }
        if (theNextCardToHandle.getName()== DomCardName.Crop_Rotation && !getCardsFromHand(DomCardType.Victory).isEmpty()) {
            setNeedsToUpdateGUI();
            ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
            do {
                getEngine().getGameFrame().askToSelectCards("Discard?", getCardsFromHand(DomCardType.Victory), theChosenCards, 0);
            }while (theChosenCards.size()>1);
            if (theChosenCards.isEmpty())
                return;
            discardFromHand(theChosenCards.get(0));
            drawCards(2);
        }
        if (theNextCardToHandle.getName()== DomCardName.Piazza ) {
            ArrayList<DomCard> theTopCards = revealTopCards(1);
            if (!theTopCards.isEmpty()) {
                if (theTopCards.get(0).hasCardType(DomCardType.Action))
                    play(theTopCards.get(0));
                else
                    putOnTopOfDeck(theTopCards.get(0));
            }
        }
    }

    private void fillTriggerStack() {
        beginningOfTurnTriggers.clear();
        beginningOfTurnTriggers.addAll(getAllFromTavernMat(DomCardName.Teacher));
        beginningOfTurnTriggers.addAll(getAllFromTavernMat(DomCardName.Guide));
        beginningOfTurnTriggers.addAll(getAllFromTavernMat(DomCardName.Ratcatcher));
        beginningOfTurnTriggers.addAll(getAllFromTavernMat(DomCardName.Transmogrify));
        beginningOfTurnTriggers.addAll(horseTradersPile);
        if (isLostInTheWoods())
            beginningOfTurnTriggers.add(DomCardName.Lost_In_The_Woods.createNewCardInstance());
        for (DomCard aCard : getCardsInPlay()) {
            if (aCard instanceof MultiplicationCard)
                beginningOfTurnTriggers.add(aCard);
        }
        for (DomCard aCard : getCardsInPlay()) {
            if (aCard.hasCardType(DomCardType.Duration)) {
                boolean dontAdd = false;
                for (DomCard theCard : getCardsInPlay()) {
                    if ( (theCard instanceof MultiplicationCard) && ((MultiplicationCard)theCard).getDurationCards().contains(aCard)){
                        dontAdd=true;
                        break;
                    }

                }
                if (!dontAdd)
                    beginningOfTurnTriggers.add(aCard);
            }
        }
        beginningOfTurnTriggers.addAll(cardsToSummon);
        beginningOfTurnTriggers.addAll(cardsToReap);
        beginningOfTurnTriggers.addAll(princedCards);
        beginningOfTurnTriggers.addAll(delayedBoons);
        if (myProjects.contains(DomCardName.Cathedral)) {
            DomCard theCathedral = DomCardName.Cathedral.createNewCardInstance();
            theCathedral.setOwner(this);
            beginningOfTurnTriggers.add(theCathedral);
        }
        if (hasBuiltProject(DomCardName.Silos)) {
            DomCard theSilos = DomCardName.Silos.createNewCardInstance();
            theSilos.setOwner(this);
            beginningOfTurnTriggers.add(theSilos);
        }
        if (hasBuiltProject(DomCardName.Sinister_Plot)) {
            DomCard theSinisterPlot = DomCardName.Sinister_Plot.createNewCardInstance();
            theSinisterPlot.setOwner(this);
            beginningOfTurnTriggers.add(theSinisterPlot);
        }
        if (hasBuiltProject(DomCardName.City_Gate)) {
            DomCard theCityGate = DomCardName.City_Gate.createNewCardInstance();
            theCityGate.setOwner(this);
            beginningOfTurnTriggers.add(theCityGate);
        }
        if (hasBuiltProject(DomCardName.Crop_Rotation)) {
            DomCard cropRotationNewCardInstance = DomCardName.Crop_Rotation.createNewCardInstance();
            cropRotationNewCardInstance.setOwner(this);
            beginningOfTurnTriggers.add(cropRotationNewCardInstance);
        }
        if (hasBuiltProject(DomCardName.Piazza)) {
            DomCard thePiazza = DomCardName.Piazza.createNewCardInstance();
            thePiazza.setOwner(this);
            beginningOfTurnTriggers.add(thePiazza);
        }
        beginningOfTurnTriggers.addAll(ghostProcessionedCards);
    }

    /**
     * @param aI
     * @return
     */
    public ArrayList<DomCard> revealTopCards(int aI) {
        return deck.revealTopCards(aI);
    }

    public void showDeck() {
        deck.showContents();
    }

    public void discard(DomCard aCard) {
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " discards " + aCard);
        deck.discard(aCard);
    }

    public DomGame getCurrentGame() {
        return game;
    }

    public ArrayList<DomCard> getCardsInHand() {
        return cardsInHand;
    }

    public ArrayList<DomCard> getCardsInPlay() {
        return cardsInPlay;
    }

    public void removePhysicalCard(DomCard aCard) {
        deck.removePhysicalCard(aCard);
        aCard.owner = null;
    }

    public void returnToSupply(DomCard aCard) {
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " returns " + aCard + " to the supply");
        if (aCard.getShapeshifterCard() != null)
            aCard = aCard.getShapeshifterCard();
        getCurrentGame().returnToSupply(aCard);
        getDeck().removePhysicalCard(aCard);
        aCard.owner = null;
    }

    public ArrayList<DomPlayer> getOpponents() {
        ArrayList<DomPlayer> theLaterOpponents = new ArrayList<DomPlayer>();
        ArrayList<DomPlayer> theFirstOpponents = null;
        for (DomPlayer thePlayer : getCurrentGame().getPlayers()) {
            if (thePlayer == this) {
                theFirstOpponents = new ArrayList<DomPlayer>();
            } else {
                if (theFirstOpponents != null) {
                    theFirstOpponents.add(thePlayer);
                } else {
                    theLaterOpponents.add(thePlayer);
                }
            }
        }
        if (theFirstOpponents == null) {
            theFirstOpponents = new ArrayList<DomPlayer>();
        }
        theFirstOpponents.addAll(theLaterOpponents);
        return theFirstOpponents;
    }


    public boolean usesTrader(DomCard aCard) {
        ArrayList<DomCard> theTradersInHand = getCardsFromHand(DomCardName.Trader);
        if (theTradersInHand.size() > 0) {
            if (((TraderCard) theTradersInHand.get(0)).wantsToReact(aCard)) {
                return ((TraderCard) theTradersInHand.get(0)).react(aCard);
            }
        }
        return false;
    }

    public boolean usesWatchtower(DomCard aCard) {
        ArrayList<DomCard> theWatchTowersInHand = getCardsFromHand(DomCardName.Watchtower);
        if (theWatchTowersInHand.size() > 0) {
            if (((WatchtowerCard) theWatchTowersInHand.get(0)).wantsToReact(aCard)) {
                return ((WatchtowerCard) theWatchTowersInHand.get(0)).react(aCard);
            }
        }
        return false;
    }

    public void addAvailableBuys(int aI) {
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " gets +" + aI + " buys");
        buysLeft += aI;
    }

    public void addAvailableCoins(int aI) {
        if (DomEngine.haveToLog && aI > 0) DomEngine.addToLog(this + " gets +$" + aI);
        addAvailableCoinsSilent(aI);
    }

    public void addActions(int aI) {
        if (snowedIn){
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " is snowed in and does not get +Actions");
            return;
        }
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " gets +" + aI + " actions");
        actionsLeft += aI;
    }

    public ArrayList<DomCard> getNativeVillageMat() {
        return nativeVillageMat;
    }

    public int getTotalMoneyInDeck() {
        return deck.getTotalMoney();
    }

    public int getDeckAndDiscardSize() {
        return deck.getDeckAndDiscardSize();
    }

    public boolean isBigTurnReady() {
        return false;
    }

    public int getPirateShipLevel() {
        return pirateShipLevel;
    }

    /**
     * @param aPirateShipLevel The pirateShipLevel to set.
     */
    public void setPirateShipLevel(int aPirateShipLevel) {
        pirateShipLevel = aPirateShipLevel;
    }

    /**
     * @param aCardToPass
     * @param theMasqueradePlayer
     */
    public void passCardToTheLeftForMasquerade(DomCard aCardToPass, DomPlayer theMasqueradePlayer) {
        ArrayList<DomPlayer> theOpponents = getOpponents();
        int i = 0;
        while (i++!=theOpponents.size() && theOpponents.get(i-1).getCardsInHand().isEmpty() );
        if (i>theOpponents.size())
            return;
        if (i<=theOpponents.size() && theOpponents.get(i-1)!=theMasqueradePlayer){
            theOpponents.get(i-1).passCardToTheLeftForMasquerade(theOpponents.get(i-1).chooseCardToPass(), theMasqueradePlayer);
        }
        if (aCardToPass != null) {
            if (DomEngine.haveToLog)
                DomEngine.addToLog(this + " passes a " + aCardToPass + " to " + theOpponents.get(i-1));
            removePhysicalCard(aCardToPass);
            theOpponents.get(i-1).receiveCard(aCardToPass);
        }
    }

    private void receiveCard(DomCard aCardToPass) {
        deck.addPhysicalCardWhenNotGained(aCardToPass);
        cardsInHand.add(aCardToPass);
    }

    /**
     * @return
     */
    public DomCard chooseCardToPass() {
        if (isHumanOrPossessedByHuman()) {
            setNeedsToUpdateGUI();
            ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
            for (DomCard theCard : cardsInHand) {
                theChooseFrom.add(theCard.getName());
            }
            return removeCardFromHand(getCardsFromHand(getEngine().getGameFrame().askToSelectOneCard("Pass a card" , theChooseFrom, "Mandatory!")).get(0));
        }
        Collections.sort(getCardsInHand(), DomCard.SORT_FOR_TRASHING);
        DomCard theCardToTrash = getCardsInHand().get(0);
        if (count(DomCardName.Baron) > 0 && count(DomCardName.Estate) < 3 && theCardToTrash.getName() == DomCardName.Estate) {
            if (!getCardsFromHand(DomCardName.Copper).isEmpty())
                theCardToTrash = getCardsFromHand(DomCardName.Copper).get(0);
        }
        return removeCardFromHand(theCardToTrash);
    }

    public int getActionsAndVillagersLeft() {
        return actionsLeft+villagers;
    }

    /**
     * @return
     */
    public int getTotalAvailableCoins() {
        return getMoneyInHand() + availableCoins;
    }

    /**
     */
    public void moveToIslandMat(DomCard aCard) {
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " adds " + aCard + " to the Island Mat");
        deck.moveToIslandMat(aCard);
    }

    /**
     * @return
     */
    public DomCard removeCardFromPlay(DomCard aCard) {
        return cardsInPlay.remove(cardsInPlay.indexOf(aCard));
    }

    /**
     * @param aDomCard
     */
    public void putOnTopOfDeck(DomCard aDomCard) {
        deck.putOnTopOfDeck(aDomCard);
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " puts " + aDomCard + " on top of the deck");
    }

    public ArrayList<DomCard> collectAllCards() {
        return deck.collectAllCards();
    }

    /**
     * @return
     */
    public int getAvailableCoins() {
        return availableCoins + coffers;
    }

    /**
     * @return
     */
    public int getforcedStart() {
        return forcedStart;
    }

    public DomDeck getDeck() {
        return deck;
    }

    /**
     * @return Returns the boughtCards.
     */
    public ArrayList<DomCard> getBoughtCards() {
        return boughtCards;
    }

    public void addBuyRule(DomBuyRule buyRule) {
        if (buyRule.getCardToBuy().hasCardType(DomCardType.Prize)) {
            prizeBuyRules.add(buyRule);
        } else {
            buyRules.add(buyRule);
        }
        clearKeywords();
    }

    public DomPlayer getCopy(String aName) {
        DomPlayer theCopy = new DomPlayer(aName);
        theCopy.buyRules.addAll(buyRules);
        theCopy.prizeBuyRules.addAll(prizeBuyRules);
        theCopy.playStrategies = playStrategies.clone();
        for (DomBotType botType : getTypes()) {
            theCopy.addType(botType);
        }
        theCopy.setStartState(myStartState);
        theCopy.setSuggestedBoard(mySuggestedBoardCards);
        theCopy.setBane(myBaneCard);
        theCopy.setMountainPassBid(mountainPassBid);
        theCopy.setObeliskCard(obeliskChoice == null ? null : obeliskChoice.toString());
        theCopy.setShelters(shelters);
        return theCopy;
    }

    private void setBane(DomCardName aBaneCard) {
        myBaneCard = aBaneCard;
    }

    private void setSuggestedBoard(ArrayList<DomCardName> aSuggestedBoardCards) {
        mySuggestedBoardCards = aSuggestedBoardCards;
    }

    public DomPlayer getColonyCopy(String string) {
        DomPlayer theCopy = getCopy(string);
        theCopy.removeType(DomBotType.Province);
        theCopy.buyRules.add(0, new DomBuyRule("Colony", null, null));
        int theLastGreenIndex = 1;
        for (int i = 0; i < theCopy.buyRules.size(); i++) {
            DomBuyRule rule = theCopy.buyRules.get(i);
            if (rule.getCardToBuy().hasCardType(DomCardType.Victory)) {
                theLastGreenIndex = i;
            }
            if (rule.getCardToBuy() == DomCardName.Province) {
                theCopy.buyRules.remove(i);
                rule = new DomBuyRule("Province", null, null);
                rule.addCondition(
                        new DomBuyCondition(DomBotFunction.countCardsInSupply
                                , DomCardName.Colony
                                , DomCardType.Action
                                , "0"
                                , DomBotComparator.smallerOrEqualThan
                                , DomBotFunction.constant
                                , DomCardName.Copper
                                , DomCardType.Action
                                , "6"
                                , DomBotOperator.plus
                                , "0"));
                theCopy.buyRules.add(i, rule);
            }
            if (rule.getCardToBuy() == DomCardName.Duchy) {
                theCopy.buyRules.remove(i);
                rule = new DomBuyRule("Duchy", null, null);
                rule.addCondition(
                        new DomBuyCondition(DomBotFunction.countCardsInSupply
                                , DomCardName.Colony
                                , DomCardType.Action
                                , "0"
                                , DomBotComparator.smallerOrEqualThan
                                , DomBotFunction.constant
                                , DomCardName.Copper
                                , DomCardType.Action
                                , "5"
                                , DomBotOperator.plus
                                , "0"));
                theCopy.buyRules.add(i, rule);
            }
            if (rule.getCardToBuy() == DomCardName.Estate) {
                theCopy.buyRules.remove(i);
                rule = new DomBuyRule("Estate", null, null);
                rule.addCondition(
                        new DomBuyCondition(DomBotFunction.countCardsInSupply
                                , DomCardName.Colony
                                , DomCardType.Action
                                , "0"
                                , DomBotComparator.smallerOrEqualThan
                                , DomBotFunction.constant
                                , DomCardName.Copper
                                , DomCardType.Action
                                , "2"
                                , DomBotOperator.plus
                                , "0"));
                theCopy.buyRules.add(i, rule);
            }
        }
        theCopy.buyRules.add(theLastGreenIndex + 1, new DomBuyRule("Platinum", null, null));
        theCopy.addType(DomBotType.Colony);

        return theCopy;
    }

    private void removeType(DomBotType aType) {
        types.remove(aType);
    }

    public ArrayList<DomCardName> getCardsNeededInSupply() {
        ArrayList<DomCardName> theCards = new ArrayList<DomCardName>();
        for (DomBuyRule theRule : getBuyRules()) {
            if (theRule.getCardToBuy().hasCardType(DomCardType.Kingdom)
                    || theRule.getCardToBuy() == DomCardName.Potion
                    || theRule.getCardToBuy() == DomCardName.Colony
                    || theRule.getCardToBuy().hasCardType(DomCardType.Event)
                    || theRule.getCardToBuy().hasCardType(DomCardType.Project)) {
                theCards.add(theRule.getCardToBuy());
            }
            for (DomBuyCondition buyCondition : theRule.getBuyConditions()) {
                if (buyCondition.getLeftCardName() != null && buyCondition.getLeftCardName().hasCardType(DomCardType.Kingdom))
                    theCards.add(buyCondition.getLeftCardName());
                if (buyCondition.getRightCardName() != null && buyCondition.getRightCardName().hasCardType(DomCardType.Kingdom))
                    theCards.add(buyCondition.getRightCardName());
            }
        }
        return theCards;
    }

    /**
     */
    public void addPlayStrategy(String aCard, String aStrategy) {
        if (aStrategy != null) {
            playStrategies.put(DomCardName.valueOf(aCard), DomPlayStrategy.valueOf(aStrategy));
        }
    }

    /* (non-Javadoc)
     * @see DominionSimulator.DomPlayer#getStrategyFor(DominionSimulator.DomCard)
     */
    public DomPlayStrategy getPlayStrategyFor(DomCard aCard) {
        DomPlayStrategy theStrategy = playStrategies.get(aCard.name);
        if (aCard.getName() == DomCardName.Madman)
            theStrategy = playStrategies.get(DomCardName.Hermit);
        return theStrategy == null ? DomPlayStrategy.standard : theStrategy;
    }

    /**
     * @return
     */
    public ArrayList<DomBuyRule> getBuyRules() {
        if (possessor != null)
            return possessor.getBuyRules();
        return buyRules;
    }

    /**
     * @param aI
     */
    public void forceStart(int aI) {
        forcedStart = aI;
    }

    public DomCardName getDesiredCard(DomCost anAvailableCurrency, boolean costExact) {
        return getDesiredCard(null, anAvailableCurrency, costExact, false, null);
    }

    public DomCardName getDesiredCard(DomCardType aDesiredType
            , DomCost anAvailableCurrency
            , boolean costExact
            , boolean noConstraints
            , DomCardType aForbiddenType) {
        for (DomBuyRule theRule : getBuyRules()) {
            DomCardName cardToBuy = theRule.getCardToBuy();
            //TODO this seemed wrong, but there might be a reason it's here
            if (!cardToBuy.hasCardType(DomCardType.Kingdom) && !cardToBuy.hasCardType(DomCardType.Base))
                continue;
            if (aDesiredType != null && !cardToBuy.hasCardType(aDesiredType))
                continue;
            if (aForbiddenType != null && cardToBuy.hasCardType(aForbiddenType))
                continue;
            boolean wantsToGain = true;
            for (DomBuyCondition theCondition : theRule.getBuyConditions()) {
//            	  if (noConstraints && theCondition.getLeftFunction()==DomBotFunction.isActionPhase){
//            		  //TODO probably remove this...
//            		  //be very careful: this fix was added so Estates can be gained with Remodel, but their trash priority stays low
//            		  //so eg Coppers aren't remodeled into Estates before Estates into Caravans
//            		  wantsToGain=false;
//            		  break;
//            	  }
                if (!theCondition.isTrue(possessor != null ? possessor : this)) {
                    wantsToGain = false;
                    break;
                }
            }
            DomCost theCost = determineCostAndCheckSplitPiles(cardToBuy);
            if (wantsToGain && theCost!=null) {
                if ((!costExact && anAvailableCurrency.customCompare(theCost) >= 0)
                        || (costExact && anAvailableCurrency.customCompare(theCost) == 0)) {
                    if (noConstraints ||
                            (!suicideIfBuys(cardToBuy)
                                    && (getCurrentGame().countInSupply(cardToBuy) > 0 || cardToBuy.hasCardType(DomCardType.Event))))
                        return cardToBuy;
                }
            }
        }
        return null;
    }

    public DomCardName getDesiredCardWithRestriction(DomCardType aDesiredType
            , DomCost anAvailableCurrency
            , boolean costExact
            , DomCardName aForbiddenCard) {
        for (DomBuyRule theRule : getBuyRules()) {
            if (aDesiredType != null && !theRule.getCardToBuy().hasCardType(aDesiredType))
                continue;
            if (theRule.getCardToBuy() == aForbiddenCard)
                continue;
            if (!(theRule.getCardToBuy().hasCardType(DomCardType.Kingdom) || theRule.getCardToBuy().hasCardType(DomCardType.Base)))
                continue;
            boolean wantsToGain = true;
            for (DomBuyCondition theCondition : theRule.getBuyConditions()) {
//            	  if (noConstraints && theCondition.getLeftFunction()==DomBotFunction.isActionPhase){
//            		  //TODO probably remove this...
//            		  //be very careful: this fix was added so Estates can be gained with Remodel, but their trash priority stays low
//            		  //so eg Coppers aren't remodeled into Estates before Estates into Caravans
//            		  wantsToGain=false;
//            		  break;
//            	  }
                if (!theCondition.isTrue(possessor != null ? possessor : this)) {
                    wantsToGain = false;
                    break;
                }
            }
            DomCost theCost = determineCostAndCheckSplitPiles(theRule);
            if (wantsToGain && theCost!=null) {
                if ((!costExact && anAvailableCurrency.customCompare(theCost) >= 0)
                        || (costExact && anAvailableCurrency.customCompare(theCost) == 0)) {
                    if (!suicideIfBuys(theRule.getCardToBuy()) && getCurrentGame().countInSupply(theRule.getCardToBuy()) > 0)
                        return theRule.getCardToBuy();
                }
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#customCompare(java.lang.Object)
     */
    public int compareTo(DomPlayer aO) {
        return toString().compareTo(aO.toString());
    }

    /**
     * @param aCard
     */
    public void addBuyRuleFor(DomCardName aCard) {
        for (DomBuyRule theRule : getBuyRules()) {
            if (theRule.getCardToBuy().hasCardType(DomCardType.Victory))
                continue;
            if (aCard.getCost().getCoins() >= theRule.getCardToBuy().getCost().getCoins()
                    || theRule.getCardToBuy() == DomCardName.Silver) {
                insertBuyRule(aCard, theRule);
                return;
            }
        }
    }

    private void insertBuyRule(DomCardName aCard, DomBuyRule theRule) {
        String theBane = null;
        if (aCard == DomCardName.Young_Witch)
            theBane = ((DomCardName) DomCardName.getPossibleBaneCards()[(int) Math.random() * DomCardName.getPossibleBaneCards().length]).name();
        DomBuyRule theNewRule = new DomBuyRule(aCard.name(), DomPlayStrategy.standard.name(), theBane);
        DomBuyCondition theCondition = new DomBuyCondition();
        theCondition.addLeftHand("countCardsInDeck", aCard.name());
        theCondition.addComparator("smallerThan");
        theCondition.addRightHand("constant", "1");
        theNewRule.addCondition(theCondition);
        getBuyRules().add(getBuyRules().indexOf(theRule), theNewRule);
        if (aCard.getPotionCost() > 0) {
            theNewRule = new DomBuyRule(DomCardName.Potion.name(), DomPlayStrategy.standard.name(), null);
            theCondition = new DomBuyCondition();
            theCondition.addLeftHand("countCardsInDeck", DomCardName.Potion.name());
            theCondition.addComparator("smallerThan");
            theCondition.addRightHand("constant", "1");
            theNewRule.addCondition(theCondition);
            getBuyRules().add(getBuyRules().indexOf(theRule), theNewRule);
        }
        clearKeywords();
        return;
    }

    /**
     * @param aPotionValue
     */
    public void addAvailablePotion(int aPotionValue) {
        if (DomEngine.haveToLog && aPotionValue > 0) DomEngine.addToLog(this + " gets +" + aPotionValue + "P");
        availablePotions += aPotionValue;
    }

    /**
     * @return
     */
    public DomCost getTotalPotentialCurrency() {
        int theTotalCoins = availableCoins;
        int theTotalPotions = availablePotions;
        for (int i = 0; i < cardsInHand.size(); i++) {
            DomCard theCardInHand = cardsInHand.get(i);
            theTotalCoins += theCardInHand.getPotentialCoinValue();
            theTotalPotions += theCardInHand.getPotionValue();
        }
        theTotalCoins += coffers;
        return new DomCost(theTotalCoins, theTotalPotions);
    }

    public DomCost getPotentialCurrencyFromTreasures() {
        int theTotalCoins = availableCoins;
        int theTotalPotions = availablePotions;
        for (int i = 0; i < cardsInHand.size(); i++) {
            DomCard theCardInHand = cardsInHand.get(i);
            if (theCardInHand.hasCardType(DomCardType.Treasure)) {
                theTotalCoins += theCardInHand.getPotentialCoinValue();
                theTotalPotions += theCardInHand.getPotionValue();
            }
        }
        theTotalCoins += coffers;
        return new DomCost(theTotalCoins, theTotalPotions);
    }

    /**
     * @return
     */
    public DomCost getPotentialCurrencyFromNativeVillageMat() {
        int theTotalCoins = 0;
        int theTotalPotions = 0;
        for (DomCard theCard : nativeVillageMat) {
            theTotalCoins += theCard.getPotentialCoinValue();
            theTotalPotions += theCard.getPotionValue();
        }
        return new DomCost(theTotalCoins, theTotalPotions);
    }


    /**
     * @param aCardToTrash
     * @return
     */
    public boolean removingReducesBuyingPower(DomCard aCardToTrash) {
        DomCost theValue = aCardToTrash.getPotentialCurrencyValue();
        if (theValue.customCompare(DomCost.ZERO) > 0) {
            DomCost theTotalCurrency = getTotalPotentialCurrency();
            int theIndex = cardsInHand.indexOf(aCardToTrash);
            cardsInHand.remove(aCardToTrash);
            DomCost theReducedCurrency = getTotalPotentialCurrency();
            cardsInHand.add(theIndex, aCardToTrash);
            if (getDesiredCard(theTotalCurrency, false) != getDesiredCard(theReducedCurrency, false))
                return true;
        }
        return false;
    }

    /**
     * @return
     */
    public boolean addingThisIncreasesBuyingPower(DomCost aCost) {
        if (aCost.customCompare(DomCost.ZERO) > 0) {
            DomCost theTotalCurrency = getTotalPotentialCurrency();
            DomCost theAddedCurrency = theTotalCurrency.add(aCost);
            if (getDesiredCard(theTotalCurrency, false) != getDesiredCard(theAddedCurrency, false))
                return true;
        }
        return false;
    }

    /**
     *
     */
    public void increasePirateShipLevel() {
        pirateShipLevel++;
        if (DomEngine.haveToLog) DomEngine.addToLog(this + "'s Pirate Ships now worth $" + pirateShipLevel);
    }

    public void putInHand(DomCard aCard) {
        getCardsInHand().add(aCard);
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " adds the " + aCard + " to his hand");
    }

    public void putDeckInDiscard() {
        deck.putDeckInDiscard();
    }

    public boolean isDeckEmpty() {
        return deck.getDeckAndDiscardSize() == 0;
    }

    public boolean isUserCreated() {
        return types.contains(DomBotType.UserCreated);
    }

    public String getXML() {
        StringBuilder theXML = new StringBuilder();
        String newline = System.getProperty("line.separator");
        theXML.append("<player name=\"").append(name).append("\"").append(newline);
        theXML.append(" author=\"").append(author).append("\"").append(newline);
        theXML.append(" description=\"").append(description.replace(newline, "XXXX")).append("\"");
        theXML.append(">").append(newline);
        for (DomBotType botType : getTypes()) {
            theXML.append(" <type name=\"").append(botType.name()).append("\"/>").append(newline);
        }
        if (myStartState != null)
            theXML.append(myStartState.getXML());
        if (!mySuggestedBoardCards.isEmpty() || mountainPassBid > 0 || getObeliskChoice().length() > 0) {
            theXML.append("  <board contents=\"").append(mySuggestedBoardCards.toString().replaceAll("\\[|\\]", "")).append("\"");
            theXML.append(" bane=\"").append(myBaneCard == null ? "" : myBaneCard).append("\"");
            theXML.append(" Mountain_Pass_Bid=\"").append(mountainPassBid).append("\"");
            theXML.append(" Obelisk_Choice=\"").append(getObeliskChoice() == null ? "" : getObeliskChoice()).append("\"");
            theXML.append(" Shelters=\""+Boolean.toString(getShelters())+"\"");
            theXML.append("/>").append(newline);
        }
        for (DomBuyRule theRule : getPrizeBuyRules()) {
            theXML.append(theRule.getXML());
        }
        for (DomBuyRule theRule : getBuyRules()) {
            theXML.append(theRule.getXML());
        }
        theXML.append("</player>").append(newline);
        return theXML.toString();
    }

    public double countMaxOpponentsVictoryPoints() {
        int theMaxVP = 0;
        for (DomPlayer player : getOpponents()) {
            int theVP = player.countVictoryPoints();
            theMaxVP = theVP > theMaxVP ? theVP : theMaxVP;
        }
        return theMaxVP;
    }

    public DomCost getTotalAvailableCurrency() {
        return new DomCost(availableCoins + coffers, availablePotions);
    }

    public DomCost getTotalAvailableCurrencySansCoffers() {
        return new DomCost(availableCoins , availablePotions);
    }

    public void increaseActionsPlayed() {
        actionsplayed++;
    }

    public int getActionsPlayed() {
        return actionsplayed;
    }

    public void addForbiddenCardToBuy(DomCardName aCard) {
        forbiddenCardsToBuy.add(aCard);
    }

    public ArrayList<DomCardName> getForbiddenCardsToBuy() {
        return forbiddenCardsToBuy;
    }

    public ArrayList<DomCard> removeCardsFromDiscard(DomCardName aCardName) {
        return deck.removeCardsFromDiscard(aCardName);
    }

    public int countDifferentCardsInDeck() {
        return deck.countDifferentCards();
    }


    public int getVictoryTokens() {
        return victoryTokens;
    }

    public DomCard getBottomCardFromDeck() {
        return deck.getBottomCard();
    }

    public void putOnBottomOfDeck(DomCard theBottomCard) {
        deck.putCardOnBottomOfDeck(theBottomCard);
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " puts " + theBottomCard + " on the bottom");
    }

    public void putSecondFromTop(DomCard theCard) {
        deck.putSecondFromTop(theCard);
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " puts " + theCard + " second from the top");
    }

    public void addPossessionTurn(DomPlayer owner) {
        possessionTurns.add(owner);
    }

    public ArrayList<DomCardName> getCardsGainedLastTurn() {
        return cardsGainedLastTurn;
    }

    public void setComputerGenerated() {
        types.add(DomBotType.Generated);
    }

    public boolean isComputerGenerated() {
        return types.contains(DomBotType.Generated);
    }

    public ArrayList<DomCard> revealUntilVictoryOrCurse() {
        return deck.revealUntilVictoryOrCurse();
    }

    public ArrayList<DomCard> revealUntilActionOrTreasure() {
        return deck.revealUntilActionOrTreasure();
    }

    public ArrayList<DomBuyRule> getPrizeBuyRules() {
        return prizeBuyRules;
    }

    public ArrayList<DomPlayer> getPossessionTurns() {
        return possessionTurns;
    }

    public void setPossessor(DomPlayer aPossessor) {
        possessor = aPossessor;
    }

    public boolean hasExtraOutpostTurn() {
        return extraOutpostTurn;
    }

    public void addPhysicalCard(DomCard aCard) {
        deck.get(aCard.getName()).add(aCard);
    }

    public boolean wants(DomCardName aCardName) {
        //first check if pile not empty and not suicide
        if ((getCurrentGame().countInSupply(aCardName) == 0
                || suicideIfBuys(aCardName)) && (aCardName.hasCardType(DomCardType.Event) && getCurrentGame().getBoard().get(aCardName)==null))
            return false;

        //then check if buy rules indicate player wants the card
        for (DomBuyRule buyRule : getBuyRules()) {
            if (buyRule.getCardToBuy() == aCardName) {
                if (buyRule.wantsToBuyOrGainNow(this))
                    return true;
            }
        }
        return false;
    }

    public void doTrueRandomShuffle() {
        deck.shuffle();
    }

    public int getBuysLeft() {
        return buysLeft;
    }

    public int getProbableActionsLeft() {
        int probableActionsLeft = getActionsAndVillagersLeft();
        for (DomCard theCard : getCardsInHand()) {
            if (theCard.hasCardType(DomCardType.Terminal))
                probableActionsLeft--;
            if (theCard.hasCardType(DomCardType.Village))
                probableActionsLeft++;
        }
        return probableActionsLeft;
    }

    public HashSet<DomBotType> getTypes() {
        return types;
    }

    public boolean hasType(Object object) {
        if (types.contains(object))
            return true;
        return false;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public void addType(DomBotType aType) {
        types.add(aType);
    }

    public void setAuthor(String text) {
        author = text;
    }

    public void setDescription(String text) {
        description = text;
    }

    public void setTypes(HashSet<DomBotType> myTypes) {
        types = myTypes;
    }

    public boolean isInBuyPhase() {
        return getCurrentGame().isBuyPhase();
    }

    public boolean wantsToGainOrKeep(DomCardName aCardName) {
        //check if buy rules indicate player wants the card
        for (DomBuyRule buyRule : getBuyRules()) {
            if (buyRule.getCardToBuy() == aCardName) {
                if (buyRule.wantsToBuyOrGainNow(this))
                    return true;
            }
        }
        return false;
    }

    public boolean stillInEarlyGame() {
        for (DomCardName cardName : DomCardName.values()) {
            if (cardName == DomCardName.Estate)
                continue;
            if (cardName.hasCardType(DomCardType.Victory) && cardName.getDiscardPriority(1) < 10 && count(cardName) > 0)
                return false;
        }
        return true;
    }

    public DomPhase getPhase() {
        return currentPhase;
    }

    public void addToCardsToStayInPlay(DomCard domCard) {
        cardsToStayInPlay.add(domCard);
    }

    public void setExtraOutpostTurn(boolean b) {
        extraOutpostTurn = b;
    }

    public int getKnownTopCards() {
        return knownTopCards;
    }

    public void setKnownTopCards(int size) {
        if (size < 0)
            size = 0;
        knownTopCards = size;
    }

    public boolean checkForcedStart() {
        return getDeck().checkForcedStart();
    }

    public void setStartState(StartState tmp) {
        myStartState = tmp;
    }

    public StartState getStartState() {
        return myStartState;
    }

    void dealStartCards() {
        int i=7;
        for (DomCardName theCard : getCurrentGame().getBoard().keySet()) {
            if (theCard.hasCardType(DomCardType.Heirloom)) {
                DomCard theHeirloom = getCurrentGame().takeFromSupply(theCard);
                if (theHeirloom!=null) {
                    i--;
                    gainOnTopOfDeck(theHeirloom);
                }
            }
        }
        for (int c = 0; c < i; c++) {
            gainOnTopOfDeck(getCurrentGame().takeFromSupply(DomCardName.Copper));
        }
        if (shelters) {
            getCurrentGame().getBoard().addCardPile(DomCardName.Necropolis);
            gainOnTopOfDeck(getCurrentGame().takeFromSupply(DomCardName.Necropolis));
            getCurrentGame().getBoard().addCardPile(DomCardName.Hovel);
            gainOnTopOfDeck(getCurrentGame().takeFromSupply(DomCardName.Hovel));
            getCurrentGame().getBoard().addCardPile(DomCardName.Overgrown_Estate);
            gainOnTopOfDeck(getCurrentGame().takeFromSupply(DomCardName.Overgrown_Estate));
        } else {
            for (int c = 0; c < 3; c++) {
                gainOnTopOfDeck(getCurrentGame().takeFromSupply(DomCardName.Estate));
            }
        }
    }

    public boolean addBoard(String contents, String bane, String aMountainPassBid, String anObeliskChoice, String aShelters) {
        if (!StartState.dissectAndAdd(contents, mySuggestedBoardCards))
            return false;
        if (bane != null) {
            try {
                bane = bane.replaceAll("\\s|-", "_").replaceAll("'", "\\$");
                myBaneCard = DomCardName.valueOf(bane);
            } catch (Exception e) {
                if (!bane.trim().isEmpty()) {
                    return false;
                }
                myBaneCard = null;
            }
        } else {
            myBaneCard = null;
        }
        setMountainPassBid(Integer.valueOf(aMountainPassBid));
        setObeliskCard(anObeliskChoice);
        setShelters(Boolean.valueOf(aShelters));
        return true;
    }

    public String getBoardString() {
        return mySuggestedBoardCards.toString().replaceAll("\\[|\\]", "");
    }

    public DomCardName getBaneCard() {
        return myBaneCard;
    }

    public String getBaneCardAsString() {
        if (myBaneCard == null)
            return "";
        return myBaneCard.toString();
    }

    public void addCoffers(int i) {
        coffers += i;
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " adds " + (i==1 ? "1 coffer." : i+" coffers.") + " (now has "+coffers+")");
    }

    public void addFavors(int i) {
        favors += i;
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " adds " + (i==1 ? "1 favor." : i+" favors.")+ " (now has "+favors+")");
    }

    public int getCoffers() {
        return coffers;
    }

    public void spendCoffers(int theCoffers) {
        coffers -= theCoffers;
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " spends " + theCoffers + " coffers.");
    }

    public void setAvailableCoins(int availableCoins) {
        this.availableCoins = availableCoins;
    }

    public void flipJourneyToken() {
        journeyTokenIsFaceUp = !journeyTokenIsFaceUp;
        if (DomEngine.haveToLog)
            DomEngine.addToLog(this + " flips journey token (now " + (journeyTokenIsFaceUp ? "face up)" : "face down)"));
    }

    public boolean isJourneyTokenFaceUp() {
        return journeyTokenIsFaceUp;
    }

    public void putOnTavernMat(DomCard aCard) {
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " adds " + aCard + " to the Tavern Mat");
        //possible if throne roomed card
        if (tavernMat.contains(aCard))
            return;
        if (aCard.getShapeshifterCard() != null)
            tavernMat.add(aCard.getShapeshifterCard());
        else
            tavernMat.add(aCard);
    }

    public ArrayList<DomCard> getAllFromTavernMat(DomCardName cardName) {
        ArrayList<DomCard> theList = new ArrayList<DomCard>();
        for (DomCard theCard : tavernMat) {
            if (theCard.getName() == cardName)
                theList.add(theCard);
        }
        return theList;
    }

    public boolean isMinusOneCardToken() {
        return minusOneCardToken;
    }

    public void setMinusOneCardToken() {
        minusOneCardToken = true;
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " gets -1 Card Token");
    }

    public ArrayList<DomCard> revealUntilVictoryCardNotNamed(DomCardName theNamedCard) {
        return deck.revealUntilVictoryCardNotNamed(theNamedCard);
    }

    public void setPlusBuyAction(DomCardName theCard) {
        plusOneBuyTokenOn = theCard;
    }

    public boolean isPlusOneBuyTokenSet() {
        return plusOneBuyTokenOn != null;
    }

    public ArrayList<DomCard> getCardsFromDiscard() {
        return deck.getDiscardPile();
    }

    public DomCard removeCardFromDiscard(DomCard theCard) {
        getDeck().getDiscardPile().remove(theCard);
        return theCard;
    }

    public void setAsideForPrince(DomCard aPrincedCard) {
        princedCards.add(aPrincedCard);
    }

    public void setAsideToSummon(DomCard domCard) {
        cardsToSummon.add(domCard);
    }

    public void setAsideToReap(DomCard domCard) {
        cardsToReap.add(domCard);
    }

    private void resolveCardsToSummon() {
        for (DomCard theCard : cardsToSummon) {
            if (DomEngine.haveToLog) DomEngine.addToLog(name + " plays Summoned cards");
            play(theCard);
        }
        cardsToSummon.clear();
    }

    private void resolveCardsToReap() {
        for (DomCard theCard : cardsToReap) {
            if (DomEngine.haveToLog) DomEngine.addToLog(name + " plays Reaped Gold");
            play(theCard);
        }
        cardsToReap.clear();
    }

    public int countOnTavernMat(DomCardName aCardName) {
        int theCount = 0;
        for (DomCard theCard : tavernMat) {
            if (theCard.getName() == aCardName)
                theCount++;
        }
        return theCount;
    }

    public DomCard removeFromTavernMat(DomCard aCard) {
        return tavernMat.remove(tavernMat.indexOf(aCard));
    }

    public void removeAvailableCoins(int size) {
        availableCoins -= size;
        if (availableCoins < 0)
            availableCoins = 0;
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " loses $" + size + " and has $" + availableCoins + " left");
    }

    public void activateMinusOneCoin() {
        minusOneCoinToken = true;
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " gets -$1 Token");
    }

    public void addAvailableCoinsSilent(int aI) {
        if (aI<=0)
            return;
        if (minusOneCoinToken) {
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " loses -$1 token");
            aI--;
            minusOneCoinToken = false;
        }
        availableCoins += aI;
    }

    public void placeMinus$2Token() {
        minus$2TokenOn = getChosenCardForFunction(DomBotFunction.isMinus$2TokenSet);
        if (minus$2TokenOn != null)
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " puts -$2 token on  " + getMinus$2TokenOn());
    }

    public void placePlusOneCardToken() {
        plusOneCardTokenOn = getChosenCardForFunction(DomBotFunction.isPlusOneCardTokenSet);
        if (plusOneCardTokenOn != null)
            if (DomEngine.haveToLog)
                DomEngine.addToLog(this + " puts +1 Card token on " + plusOneCardTokenOn.toHTML());
    }

    public boolean isPlusOneCardTokenSet() {
        return plusOneCardTokenOn != null;
    }

    public boolean isPlusOneActionTokenSet() {
        return plusOneActionTokenOn != null;
    }

    public DomCardName plusOneActionTokenOn() {
        return plusOneActionTokenOn;
    }

    public void placePlusOneActionToken() {
        plusOneActionTokenOn = getChosenCardForFunction(DomBotFunction.isPlusOneActionTokenSet);
        if (plusOneActionTokenOn != null) {
            if (DomEngine.haveToLog)
                DomEngine.addToLog(this + " puts +1 Action token on " + plusOneActionTokenOn.toHTML());
        }
    }

    public void placePlusOneActionToken(DomCardName aCard) {
        plusOneActionTokenOn = aCard;
        if (DomEngine.haveToLog)
           DomEngine.addToLog(this + " puts +1 Action token on " + aCard.toHTML());
    }

    public void placePlusOneCardToken(DomCardName aCard) {
        plusOneCardTokenOn = aCard;
        if (DomEngine.haveToLog)
            DomEngine.addToLog(this + " puts +1 Action token on " + aCard.toHTML());
    }

    private DomCardName getChosenCardForFunction(DomBotFunction function) {
        DomCardName theChosenCard = null;
        for (DomBuyRule theRule : getBuyRules()) {
            boolean validRule = true;
            for (DomBuyCondition theCondition : theRule.getBuyConditions()) {
                if (theCondition.getLeftFunction() == function) {
                    theChosenCard = theCondition.getLeftCardName();
                    break;
                }
                if (theCondition.getRightFunction() == function) {
                    theChosenCard = theCondition.getRightCardName();
                    break;
                }
            }
            if (theChosenCard == null)
                continue;
            if (theRule.getCardToBuy() == DomCardName.Teacher)
                break;
            for (DomBuyCondition theCondition : theRule.getBuyConditions()) {
                if (theCondition.getLeftFunction() == function
                        || theCondition.getRightFunction() == function) {
                    continue;
                }
                if (!theCondition.isTrue(this)) {
                    validRule = false;
                    break;
                }
            }
            if (!validRule) {
                theChosenCard = null;
                continue;
            }
            break;
        }
        return theChosenCard;
    }

    public DomCardName getPreviousPlayedCardName() {
        return previousPlayedCardName;
    }

    public int getSameCardCount() {
        return sameCardCount;
    }

    public void setPreviousPlayedCardName(DomCardName previousPlayedCardName) {
        this.previousPlayedCardName = previousPlayedCardName;
    }


    public void setSameCardCount(int sameCardCount) {
        this.sameCardCount = sameCardCount;
    }

    public void activateTravellingFair() {
        travellingFairIsActive = true;
    }

    public boolean isTravellingFairActive() {
        return travellingFairIsActive;
    }

    public void activatePilgrimage() {
        pilgrimageActivatedThisTurn = true;
    }

    public ArrayList<DomCard> revealUntilThreeOfCardNotNamed(DomCardName card) {
        return deck.revealUntilThreeOfCardNotNamed(card);
    }

    public int countInPlay(DomCardType cardType) {
        int theCount = 0;
        for (DomCard theCard : cardsInPlay) {
            if (theCard.hasCardType(cardType))
                theCount++;
        }
        return theCount;
    }

    public void setAlmsActivated() {
        almsActivated = true;
    }

    public void setBorrowActivated() {
        borrowActivated = true;
    }

    public void activateExpedition() {
        expeditionsActivated++;
    }

    public DomCardName getMinus$2TokenOn() {
        return minus$2TokenOn;
    }

    public void spend(int theAmount) {
        int theRest = availableCoins - theAmount;
        if (theRest < 0) {
            availableCoins = 0;
            spendCoffers(-theRest);
        } else {
            availableCoins -= theAmount;
        }
    }

    public void placeEstateToken() {
        if (estateTokenOn != null) {
            if (isHumanOrPossessedByHuman()) {
                JOptionPane.showMessageDialog(null, "You can only do this once per game!");
            }
            return;
        }
        if (isHumanOrPossessedByHuman()) {
            ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
            for (DomCardName theCard : getCurrentGame().getBoard().keySet()) {
                if (new DomCost(4,0).customCompare(theCard.getCost(getCurrentGame()))>=0 && getCurrentGame().countInSupply(theCard)>0 && !theCard.hasCardType(DomCardType.Victory) && theCard.hasCardType(DomCardType.Action))
                    theChooseFrom.add(theCard);
            }
            if (theChooseFrom.isEmpty())
                return;
            DomCardName theChosenCard = getEngine().getGameFrame().askToSelectOneCard("Select card to Inherit", theChooseFrom, "Mandatory!");
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " places Estate token on " + theChosenCard.toHTML());
            estateTokenOn = getCurrentGame().takeFromSupply(theChosenCard);
            estateTokenOn.owner=this;
        } else {
            DomCardName theCard = getChosenCardForFunction(DomBotFunction.isEstateTokenPlaced);
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " places Estate token on " + theCard.toHTML());
            estateTokenOn = getCurrentGame().takeFromSupply(theCard);
            if (estateTokenOn!=null)
              estateTokenOn.owner=this;
        }
    }

    public boolean isEstateTokenPlaced() {
        return estateTokenOn != null;
    }

    public DomCard removeEstateToken() {
        DomCard theTokenOn = estateTokenOn;
        estateTokenOn.owner = null;
        estateTokenOn = null;
        return theTokenOn;
    }

    public DomCard getEstateTokenOn() {
        return estateTokenOn;
    }

    public void setExtraMissionTurn(boolean b) {
        extraMissionTurn = b;
    }

    public boolean hasExtraMissionTurn() {
        return extraMissionTurn;
    }

    private void setNoBuyThisTurn(boolean noBuyThisTurn) {
        this.noBuyThisTurn = noBuyThisTurn;
    }

    private boolean getNoBuyThisTurn() {
        return noBuyThisTurn;
    }

    public boolean isPlusOneCoinTokenSet() {
        return plusOneCoinTokenOn != null;
    }

    public void placePlusOneCoinToken() {
        plusOneCoinTokenOn = getChosenCardForFunction(DomBotFunction.isPlusOneCoinTokenSet);
        if (plusOneCoinTokenOn != null)
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " puts +$1 token on " + plusOneCoinTokenOn.toHTML());
    }

    public void placePlusOneCoinToken(DomCardName aCard) {
        plusOneCoinTokenOn = aCard;
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " puts +$1 token on " + plusOneCoinTokenOn.toHTML());
    }

    public void placePlusOneBuyToken(DomCardName aCard) {
        plusOneBuyTokenOn = aCard;
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " puts +1 Buy token on " + plusOneBuyTokenOn.toHTML());
    }

    public DomCardName placePlusOneBuyToken() {
        plusOneBuyTokenOn = getChosenCardForFunction(DomBotFunction.isPlusOneBuyTokenSet);
        if (plusOneBuyTokenOn != null)
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " puts +1 Buy token on " + plusOneBuyTokenOn.toHTML());
        return plusOneBuyTokenOn;
    }

    public void placeTrashingToken() {
        trashingTokenOn = getChosenCardForFunction(DomBotFunction.isTrashingTokenPlaced);
        if (trashingTokenOn != null)
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " puts Trashing token on " + trashingTokenOn.toHTML());
    }

    public boolean isTrashingTokenSet() {
        return trashingTokenOn != null;
    }

    public DomCardName getTrashingTokenOn() {
        return trashingTokenOn;
    }

    public void increaseBridgePlayedCounter() {
        bridgesPlayedCount++;
    }

    public int getBridgesPlayedCount() {
        return bridgesPlayedCount;
    }

    public int getBridgeTrollPlayedCount() {
        return bridgeTrollPlayedCount;
    }

    public void increaseCoppersmithPlayedCounter() {
        coppersmithsPlayedCount++;
    }

    public int getCoppersmithPlayedCount() {
        return coppersmithsPlayedCount;
    }

    public void addCardToHand(DomCard card) {
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " adds " + card + " to hand");
        cardsInHand.add(card);
    }

    public int countInPlay(DomCardName cardName) {
        int theCount = 0;
        for (DomCard theCard : cardsInPlay) {
            if (theCard.getName() == cardName)
                theCount++;
        }
        return theCount;

    }

    public ArrayList<DomCardName> getSuggestedBoard() {
        return mySuggestedBoardCards;
    }

    public boolean isInReserve(DomCardName aCard) {
        return getFromTavernMat(aCard) != null;
    }

    public void addDebt(int i) {
        if (possessor != null) {
            possessor.addDebt(i);
            return;
        }
        if (i == 0)
            return;
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " adds $" + i + " debt");
        debt += i;
    }

    public void payOffDebt() {
        //removed because players will always want to pay off debt
        //        if ((availableCoins > 0 || coffers > 0) && isHumanOrPossessedByHuman() && !getEngine().getGameFrame().askPlayer("<html>Pay off debt?</html>", "Resolving " + this.getName().toString()))
        //            return;
        if (DomEngine.haveToLog)
            DomEngine.addToLog(name + " has $" + debt + " in debt and $" + getAvailableCoins() + " to pay off the debt");
        while (debt > 0 && (availableCoins > 0 || coffers > 0)) {
            if (availableCoins > 0)
                availableCoins--;
            else
                coffers--;
            debt--;
        }
    }

    public boolean hasDoubledMoney() {
        return hasDoubledMoney;
    }

    public void setDoubledMoney(boolean b) {
        hasDoubledMoney = b;
    }

    public DomCard removeFromDiscard(DomCardName cardName) {
        return getDeck().removeFromDiscard(cardName);
    }

    public void addCharmReminder() {
        charmReminder++;
    }

    public void setAside(DomCard aCard) {
        mySetAsideEncampments.add(aCard);
    }

    public void triggerDonateAfterTurn() {
        donateTriggered = true;
    }

    public ArrayList<DomCard> removeAllCardsFromDiscardAndDeck() {
        return getDeck().removeAllCardsFromDiscardAndDeck();
    }

    public void addCardsToHand(ArrayList<DomCard> domCards) {
        cardsInHand.addAll(domCards);
    }

    public void addAllToDeck(ArrayList<DomCard> cards) {
        getDeck().addAllToDeck(cards);
    }

    public String getName() {
        return name;
    }

    public DomCardName getCardForObelisk() {
        return obeliskChoice;
    }

    public boolean isCardInPlay(DomCardName cardName) {
        for (DomCard theCard : getCardsInPlay()) {
            if (theCard.getName() == cardName)
                return true;
        }
        return false;
    }

    public int getMountainPassBid(int highestBid) {
        if (isHumanOrPossessedByHuman()) {
            try {
                Integer theBid = Integer.valueOf(JOptionPane.showInputDialog("Bid (current bid = " + highestBid + ")"));
                return theBid;
            } catch (NumberFormatException e) {
                return 0;
            } catch (HeadlessException e) {
                return 0;
            }
        }
        return mountainPassBid;
    }

    public String getObeliskChoice() {
        if (obeliskChoice == null)
            return "";
        return obeliskChoice.toString();
    }

    public void setMountainPassBid(Integer value) {
        mountainPassBid = value;
    }

    public boolean setObeliskCard(String text) {
        String aCard = "";
        try {
            aCard = text.replaceAll("\\s|-", "_").replaceAll("'", "\\$");
            obeliskChoice = DomCardName.valueOf(aCard);
        } catch (Exception e) {
            if (!aCard.trim().isEmpty()) {
                return false;
            }
            obeliskChoice = null;
        }
        return true;
    }

    public void clearCardsInHand() {
        cardsInHand.clear();
    }

    public void triggerVilla() {
        setVillaTriggered(true);
    }

    public void setVillaTriggered(boolean villaTriggered) {
        this.villaTriggered = villaTriggered;
    }

    public boolean isVillaTriggered() {
        return villaTriggered;
    }

    public void triggerContinue() {
        continueTriggered =true;
        continuePlayedOnce=true;
    }

    public void setContinueTriggered(boolean continueTriggered) {
        this.continueTriggered=continueTriggered;
    }

    public boolean isContinueTriggered() {
        return continueTriggered;
    }

    public DomCard getTopOfDiscard() {
        return getDeck().getTopOfDiscard();
    }

    public int getAvailableCoinsWithoutTokens() {
        return availableCoins;
    }

    public int getMerchantsPlayed() {
        return merchantsPlayed;
    }

    public void resetMerchantsPlayed() {
        merchantsPlayed=0;
    }

    public void addMerchantPlayed() {
        merchantsPlayed++;
    }

    public void putCardFromHandOnTop() {
        Collections.sort(getCardsInHand(), DomCard.SORT_FOR_DISCARD_FROM_HAND);
        ArrayList<DomCard> theCardsInHand = getCardsInHand();
        DomCard theCardToReturn = theCardsInHand.get(0);
        for (int i=theCardsInHand.size()-1;i>=0;i--){
            if (theCardsInHand.get(i).hasCardType(DomCardType.Action))
                if (actionsLeft==0 || theCardsInHand.get(i).getName()==DomCardName.Vassal || getCardsFromHand(DomCardName.Vassal).isEmpty())
                    continue;
            theCardToReturn = theCardsInHand.get(i);
            if (!removingReducesBuyingPower(theCardToReturn)) {
                break;
            }
        }
        if (theCardsInHand.get(0).hasCardType(DomCardType.Action)) {
            ArrayList<DomCard> theActions = getCardsFromHand(DomCardType.Action);
            Collections.sort(theActions,DomCard.SORT_FOR_DISCARDING);
            theCardToReturn=theActions.get(theActions.size()-1);
        }
        putOnTopOfDeck(removeCardFromHand(theCardToReturn));
    }

    public int getDrawDeckSize() {
        return getDeck().getDrawDeckSize();
    }

    private void clearKeywords() {
        keywords = null;
    }

    public String[] getKeywords() {
        if (keywords == null) {
            HashSet<String> kws = new HashSet<String>();
            for (DomBuyRule rule : buyRules) {
                for (String word : rule.getCardToBuy().toString().trim().split("[\\W/]+")) {
                    kws.add(word.toLowerCase(Locale.ENGLISH));
                }
            }
            for (String word : name.trim().split("[\\W/]+")) {
                kws.add(word.toLowerCase(Locale.ENGLISH));
            }
            keywords = kws.toArray(new String[kws.size()]);
        }
        return keywords;
    }

    public void setAsideToSave(DomCard domCard) {
        savedCard = domCard;
    }

    public void setSaveActivated() {
        saveActivated=true;
    }

    public void addCardGainedLastTurn(DomCardName name) {
        if (turns==0)
            return;
        if (isInBuyPhase() ) {
            gainsSinceBeginningOfBuyPhase = gainsSinceBeginningOfBuyPhase == 0 ? 1 : (gainsSinceBeginningOfBuyPhase+1);
        }
        cardsGainedLastTurn.add(name);
    }

    public void setHuman() {
        isHuman=true;
    }

    public boolean isHuman() {
        return isHuman;
    }

    public boolean hasJunkInHand() {
        for (DomCard theCard : getCardsInHand())
            if (theCard.getTrashPriority()<=DomCardName.Copper.getTrashPriority())
                return true;
        return false;
    }

    public void setMyEngine(DomEngine myEngine) {
        this.myEngine = myEngine;
    }

    public Set<DomCardName> getUniqueCardNamesInHand() {
        HashSet<DomCardName> theSet = new HashSet<DomCardName>();
        for (DomCard theCard : cardsInHand) {
            theSet.add(theCard.getName());
        }
        Set<DomCardName> treeSet = new TreeSet<DomCardName>();
        treeSet.addAll(theSet);
        return treeSet;
    }

    public void attemptToPlay(DomCard selectedCard) {
        if (selectedCard==null)
            return;
        if (selectedCard.hasCardType(DomCardType.Action) && getPhase()==DomPhase.Action) {
            if (actionsLeft==0 && villagers==0)
                return;
            if (actionsLeft==0 && villagers>0)
                useVillager();
            actionsLeft--;
            handleUrchins(selectedCard);
            play(removeCardFromHand(selectedCard));
            if (selectedCard.getName()!=DomCardName.Royal_Carriage)
              maybeHandleRoyalCarriage(selectedCard);
            if (actionsLeft == 0 || getCardsFromHand(DomCardType.Action).isEmpty()) {
                if (getFromTavernMat(DomCardName.Coin_of_the_Realm) != null) {
                    handleCoinOfTheRealm();
                    if (actionsLeft==0 && villagers==0)
                        setPhase(DomPhase.Buy_PlayTreasures);
                } else {
                    if (villagers==0)
                      setPhase(DomPhase.Buy_PlayTreasures);
                }
            }
        } else {
            if (selectedCard.hasCardType(DomCardType.Treasure) && getPhase() == DomPhase.Buy_PlayTreasures) {
                play(removeCardFromHand(selectedCard));
                if (previousPlayedCardName != null) {
                    DomEngine.addToLog(name + " plays " + (sameCardCount + 1) + " " + previousPlayedCardName.toHTML()
                            + (sameCardCount > 0 ? "s" : ""));
                    previousPlayedCardName = null;
                    sameCardCount = 0;
                }
            } else {
                if (selectedCard.hasCardType(DomCardType.Night) && getPhase()==DomPhase.Night) {
                    play(removeCardFromHand(selectedCard));
                    if (getCardsFromHand(DomCardType.Night).isEmpty())
                        endHumanTurn();
                }
            }
        }
        setChanged();
        notifyObservers();
    }

    public void attemptToPlayAllTreasures() {
        if (getPhase()!=DomPhase.Buy_PlayTreasures)
            return;
        ArrayList<DomCard> theCards = new ArrayList<DomCard>();
        theCards.addAll(cardsInHand);
        Collections.sort(theCards,DomCard.SORT_FOR_PLAYING);
        cardsInHand.clear();
        cardsInHand.addAll(theCards);
        while (baseTreasuresInHand()) {
            for (DomCard theCard : getCardsFromHand(DomCardType.Base)) {
                if (theCard.hasCardType(DomCardType.Treasure)){
                    play(removeCardFromHand(theCard));
                    break;
                }
            }
        }
        if (previousPlayedCardName != null) {
            DomEngine.addToLog(name + " plays " + (sameCardCount + 1) + " " + previousPlayedCardName.toHTML()
                    + (sameCardCount > 0 ? "s" : ""));
            previousPlayedCardName = null;
            sameCardCount = 0;
        }
        setChanged();
        notifyObservers();
    }

    private boolean baseTreasuresInHand() {
        for (DomCard theCard : getCardsFromHand(DomCardType.Base))
            if (theCard.hasCardType(DomCardType.Treasure))
                return true;
        return false;
    }

    public void attemptToBuyFromSupplyAsHuman(DomCardName card) {
        if (card.hasCardType(DomCardType.Landmark))
            return;
        if ((getPhase()==DomPhase.Buy_PlayTreasures || getPhase()==DomPhase.Buy_BuyStuff)&& buysLeft>0) {
            if (debt>0) {
                if (getPhase()==DomPhase.Buy_PlayTreasures)
                  attemptToPlayAllTreasures();
                payOffDebt();
                setChanged();
                notifyObservers();
                if (debt>0) {
                    JOptionPane.showMessageDialog(getEngine().getGameFrame(), "You have debt left!");
                    return;
                }
            }
            if (getEngine().getCurrentGame().getBoard().isFromSeparatePile(card))
                return;
            if ((availableCoins>=card.getCoinCost(this)
                    && availablePotions>=card.getPotionCost()) || (card==DomCardName.Animal_Fair && !getCardsFromHand(DomCardType.Action).isEmpty())) {
                setPhase(DomPhase.Buy_BuyStuff);
                if (card.hasCardType(DomCardType.Event)) {
                    payForAndResolveEvent(card);
                    buysLeft--;
                    setChanged();
                    notifyObservers();
                } else {
                    if (card.hasCardType(DomCardType.Project)) {
                        payForAndBuildProject(card);
                        buysLeft--;
                        setChanged();
                        notifyObservers();
                    } else {
                        if (!getNoBuyThisTurn() && tryToBuy(card, false)) {
                            buysLeft--;
                            setChanged();
                            notifyObservers();
                        }
                    }
                }
                if (getPhase()==DomPhase.Buy_BuyStuff && buysLeft==0 && (getDebt()==0 || getAvailableCoins()==0) ) {
                    endBuyPhaseForHuman();
                }
            } else {
                if (getTotalPotentialCurrency().customCompare( card.getCost(getCurrentGame())) >= 0 && baseTreasuresInHand()) {
                    if (getPhase()==DomPhase.Buy_PlayTreasures) {
                        attemptToPlayAllTreasures();
                        attemptToBuyFromSupplyAsHuman(card);
                    }
                }
            }
        }
    }

    public void endActions() {
        if (!getCurrentGame().isGameFinished() || currentPhase!=null) {
            setPhase(DomPhase.Buy_PlayTreasures);
            setChanged();
            notifyObservers();
        }
    }

    public void humanEndsTurn() {
        if (!getCurrentGame().isGameFinished() || currentPhase!=null) {
            endHumanTurn();
        }
    }

    private void endHumanTurn() {
        handleWineMerchantsForHuman();
        handlePageant();
        doCleanUpPhase();
        possessor = null;
        if (donateTriggered)
            DonateCard.trashStuff(getCurrentGame().getActivePlayer());
        if (getCurrentGame().isAuctionTriggered()) {
            Mountain_PassCard.doTheAuction(this);
            getCurrentGame().setAuctionTriggered(false);
        }
        getCurrentGame().setPreviousTurnTakenBy(this);
        getCurrentGame().continueHumanGame();
    }

    public boolean canBuy(DomCardName theCard) {
        return getTotalAvailableCoins()>=theCard.getCoinCost(this) && getTotalPotentialCurrency().potions>=theCard.getPotionCost();
    }

    public DomEngine getEngine() {
        return myEngine;
    }

    public boolean isHumanOrPossessedByHuman() {
        return isHuman || (possessor!=null && possessor.isHuman);
    }

    public void setNeedsToUpdateGUI() {
        setChanged();
        notifyObservers();
    }

    public void setBuyRules(ArrayList<DomBuyRule> buyRules) {
        this.buyRules.clear();
        this.buyRules.addAll(buyRules);
    }

    public void putInDeckAt(DomCard domCard, int theChoice) {
        if (DomEngine.haveToLog) DomEngine.addToLog(name + " puts "+domCard.getName().toHTML()+" at position "+(theChoice+1));
        deck.putInDeckAt(domCard,theChoice);
    }

    public String getGainedCardsText() {
        StringBuilder theSr = new StringBuilder();
        for (DomCardName theCard : getCardsGainedLastTurn())
            theSr.append("<br>").append(theCard.toHTML());
        return theSr.toString();
    }

    public String getNativeVillageMatToString() {
        return getCardNameString(getNativeVillageMat());
    }

    public String getCardNameString(ArrayList<DomCard> aCards) {
        Map<DomCardName,Integer> theMap = new HashMap<DomCardName, Integer>();
        for (DomCard theCard:aCards) {
            if (theMap.get(theCard.getName())!=null) {
                theMap.put(theCard.getName(), theMap.get(theCard.getName())+1);
            } else {
                theMap.put(theCard.getName(), 1);
            }
        }
        StringBuilder theStr = new StringBuilder();
        String thePrefix = "";
        for (DomCardName theName : theMap.keySet()) {
            theStr.append(thePrefix).append(theMap.get(theName)).append(" ").append(theName.toHTML());
            thePrefix=", ";
        }
        return theStr.toString();
    }

    public String getIslandMatString() {
        return getCardNameString(deck.getIslandMat());
    }

    public String getExileMatString() {
        return getCardNameString(deck.getExileMat());
    }

    public DomPlayer removePossessorTurn() {
        if (possessionTurns.isEmpty())
            return null;
        return possessionTurns.remove(0);
    }

    public DomCard removeCardFromDiscard(DomCardName aCard) {
        for (DomCard theCard : getDeck().getDiscardPile())
            if (theCard.getName() == aCard) {
                return removeCardFromDiscard(theCard);
            }
        return null;
    }

    public ArrayList<DomCard> getTavernMat() {
        return tavernMat;
    }

    public String getTavernMatAsString() {
        Map<DomCardName,Integer> theMap = new HashMap<DomCardName, Integer>();
        for (DomCard theCard:getTavernMat()) {
            if (theMap.get(theCard.getName())!=null) {
                theMap.put(theCard.getName(), theMap.get(theCard.getName())+1);
            } else {
                theMap.put(theCard.getName(), 1);
            }
        }
        StringBuilder theStr = new StringBuilder();
        String thePrefix = "";
        for (DomCardName theName : theMap.keySet()) {
            theStr.append(thePrefix).append(theMap.get(theName)).append(" ").append(theName.toHTML());
            thePrefix=", ";
        }
        return theStr.length()==0? "<i>empty</i>" : theStr.toString();
    }

    public boolean cardHasToken(DomCardName theCard) {
        if (plusOneActionTokenOn==theCard)
            return true;
        if (plusOneCardTokenOn==theCard)
            return true;
        if (plusOneCoinTokenOn==theCard)
            return true;
        if (plusOneBuyTokenOn==theCard)
            return true;
        if (trashingTokenOn==theCard)
            return true;
        if (minus$2TokenOn==theCard)
            return true;
        return false;
    }

    public String getTokensStringOn(DomCardName theCard) {
        String theStr = "";
        if (plusOneCardTokenOn==theCard)
            theStr+=" +C";
        if (plusOneActionTokenOn==theCard)
            theStr+=" +A";
        if (plusOneCoinTokenOn==theCard)
            theStr+=" +$";
        if (plusOneBuyTokenOn==theCard)
            theStr+=" +B";
        if (minus$2TokenOn==theCard)
            theStr+=" -$2";
        if (trashingTokenOn==theCard)
            theStr+=" X";
        return theStr;
    }

    public boolean isAlmsActivated() {
        return almsActivated;
    }

    public boolean isSaveActivated() {
        return saveActivated;
    }

    public boolean getShelters() {
        return shelters;
    }

    public void setShelters(boolean shelters) {
        this.shelters = shelters;
    }

    public void placeMinus$2Token(DomCardName aChosenCard) {
        minus$2TokenOn = aChosenCard;
        if (DomEngine.haveToLog)
            DomEngine.addToLog(this + " puts -$2 Action token on " + aChosenCard.toHTML());
    }

    public void placeTrashingToken(DomCardName aChosenCard) {
        trashingTokenOn = aChosenCard;
        if (DomEngine.haveToLog)
            DomEngine.addToLog(this + " puts the trashing token on " + aChosenCard.toHTML());
    }

    public boolean isPilgrimageActivatedThisTurn() {
        return pilgrimageActivatedThisTurn;
    }

    public ArrayList<DomCard> getUniqueCardsInPlay() {
        ArrayList<DomCard> theUniqueCardsList = new ArrayList<DomCard>();
        HashSet<DomCardName> theUniqueCardsSet = new HashSet<DomCardName>();
        for (DomCard theCard:cardsInPlay) {
            theUniqueCardsSet.add(theCard.getName());
        }
        for (DomCardName theName : theUniqueCardsSet) {
            theUniqueCardsList.add(getCardsFromPlay(theName).get(0));
        }
        return theUniqueCardsList;
    }

    public int getDebt() {
        return debt;
    }

    public void payOffDebt(int i) {
        debt-=i;
    }

    public void receiveBoon(DomCard aBoon) {
        getCurrentGame().getBoard().receiveBoon(this, aBoon);
    }

    public void receiveHex(DomCard aHex) {
        getCurrentGame().getBoard().receiveHex(this, aHex);
    }

    public void keepBoon(DomCard aBoon) {
        if (!boons.contains(aBoon))
          boons.add(aBoon);
    }

    public void activateRiver$sGift() {
        river$sGiftActive = true;
    }

    public DomCard takeBoon() {
        return getCurrentGame().getBoard().takeBoon();
    }

    public void addDelayedBoon(DomCard aBoon) {
        delayedBoons.add(aBoon);
    }

    public void returnDelayedBoons() {
        for (DomCard theBoon : delayedBoons)
          getCurrentGame().getBoard().returnBoon(theBoon);
        delayedBoons.clear();
    }

    public boolean isDeluded() {
        return deluded;
    }

    public boolean isEnvious() {
        return envious;
    }

    public void setDeluded(boolean deluded) {
        this.deluded = deluded;
    }

    public void setCantBuyActions(boolean cantBuyActions) {
        this.cantBuyActions = cantBuyActions;
    }

    public void setLostInTheWoods(boolean lostInTheWoods) {
        this.lostInTheWoods = lostInTheWoods;
    }

    public boolean isLostInTheWoods() {
        return lostInTheWoods;
    }

    public void setEnvious(boolean envious) {
        this.envious = envious;
    }

    public void setEnviousActive(boolean enviousActive) {
        this.enviousActive = enviousActive;
    }

    public boolean isEnviousActive() {
        return enviousActive;
    }

    public boolean isMiserable() {
        return miserable;
    }

    public void setTwiceMiserable() {
        miserable=false;
        this.twiceMiserable = true;
    }

    public void setMiserable(boolean miserable) {
        this.miserable = miserable;
    }

    public boolean isTwiceMiserable() {
        return twiceMiserable;
    }

    public void addFaithfulHoundToSetAside(DomCard aFaithful_houndCard) {
       setAsideFaithfulHounds.add(aFaithful_houndCard);
        if (DomEngine.haveToLog)
            DomEngine.addToLog(this + " sets aside " + DomCardName.Faithful_Hound.toHTML());
    }

    public boolean isRiver$sGiftActive() {
        return river$sGiftActive;
    }

    public boolean isBorrowActivated() {
        return borrowActivated;
    }

    public boolean hasGainedExtraExperiment() {
        return gainedExtraExperiment;
    }

    public void setGainedExtraExperiment(boolean gainedExtraExperiment) {
        this.gainedExtraExperiment = gainedExtraExperiment;
    }

    public int getTrashingBonus() {
        return trashingBonus;
    }

    public void addTrashingBonus() {
        trashingBonus++;
    }

    public void addVillagers(int i) {
        villagers+=i;
        if (DomEngine.haveToLog)
            DomEngine.addToLog(this + " gains " + i + " villagers and now has " + villagers + " villager" + (villagers==1? "":"s") );
    }

    public int countVillagers() {
        return villagers;
    }

    public boolean hasBuiltProject(DomCardName aProject) {
        return myProjects.contains(aProject);
    }

    public void setResolvingSewers(boolean resolvingSewers) {
        this.resolvingSewers = resolvingSewers;
    }

    public boolean isHornActivated() {
        return hornActivated;
    }

    public void setHornActivated(boolean hornActivated) {
        this.hornActivated = hornActivated;
    }

    public void increaseImprovePlayedCounter() {
        improvePlayedCounter++;
    }

    public void increaseInventorPlayedCounter() {
        inventorPlayedCounter++;
    }

    public int getInventorsPlayed() {
        return inventorPlayedCounter;
    }

    public ArrayList<DomCard> getTopCards(int i) {
        return deck.getTopCards(i);
    }

    public void addGhostProcessionedMultiplicationCardWithDurations(DomCard aCard) {
        ghostProcessionedCards.add(aCard);
    }

    public int getSinisterPlotTokens() {
        return sinisterPlotTokens;
    }

    public void setSinisterPlotTokens(int sinisterPlotTokens) {
        this.sinisterPlotTokens = sinisterPlotTokens;
        if (DomEngine.haveToLog)
            DomEngine.addToLog(DomCardName.Sinister_Plot.toHTML() + " has " +getSinisterPlotTokens()+" tokens." );
    }

    public void endBuyPhaseForHuman() {
        if (hasBuiltProject(DomCardName.Exploration) && boughtCards.isEmpty()) {
            addCoffers(1);
            addVillagers(1);
        }
        if (!getCardsFromHand(DomCardType.Night).isEmpty()){
            setPhase(DomPhase.Night);
            setNeedsToUpdateGUI();
        } else {
            endHumanTurn();
        }
    }

    public boolean hasFleetTurnLeft() {
        return hasFleetTurnLeft;
    }

    public void setFleetTurnLeft(boolean fleetTurnLeft) {
        this.hasFleetTurnLeft = fleetTurnLeft;
    }

    public boolean hasTriggeredInnovation() {
        return hasTriggeredInnovation;
    }

    public void setInnovationTriggered(boolean innovationActivated) {
        hasTriggeredInnovation = innovationActivated;
    }

    public int getVillagers() {
        return villagers;
    }

    public int getLiveryTriggers() {
        return liveryTriggers;
    }

    public void addLiveryTrigger() {
        liveryTriggers++;
    }

    public void exile(DomCard domCard) {
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " adds " + domCard + " to the Exile Mat");
        deck.moveToExileMat(domCard);
    }

    public void discardAllFromExileMat(DomCardName cardName) {
        deck.discardAllFromExileMat(cardName);
    }

    public void setSnowedIn() {
        snowedIn = true;
    }

    public void addInvestment(DomCard theAction) {
        investments.add(theAction);
    }

    public boolean hasInvestedIn(DomCardName theAction) {
        for (DomCard theCard : investments)
            if (theCard.getName()==theAction)
                return true;
        return false;
    }

    public boolean hasInvestments() {
        return !investments.isEmpty();
    }

    public int countInvestmentsIn(DomCardName name) {
        int theCount = 0;
        for (DomCard theCard : investments)
            theCount+=theCard.getName()==name ? 1 : 0;
        return theCount;
    }

    public void removeFromInvestments(DomCard theCard) {
        investments.remove(theCard);
    }

    public int countOnMats(DomCardName cardName) {
        int theTotal = 0;
        for (DomCard theCard : nativeVillageMat) {
            theTotal += theCard.getName()==cardName?1:0;
        }
        for (DomCard theCard : getDeck().getExileMat()) {
            theTotal += theCard.getName()==cardName?1:0;
        }
        return theTotal;
    }

    public boolean hasInExile(DomCardName name) {
        for (DomCard domCard : getDeck().getExileMat()) {
            if (domCard.getName()==name)
                return true;
        }
        return false;
    }

    public void triggerCavalry() {
        setCavalryTriggered(true);
    }

    public void setCavalryTriggered(boolean cavalryTriggered) {
        this.cavalryTriggered = cavalryTriggered;
    }

    public boolean isCavalryTriggered() {
        return cavalryTriggered;
    }

    public void triggerKiln() {
        kilnTriggerd = true;
    }

    public void addCargoShipTrigger() {
        cargoShipTriggers++;
    }

    public int getCargoShipTriggers() {
        return cargoShipTriggers;
    }

    public void removeCargoShipTrigger() {
        cargoShipTriggers--;
    }

    public void addCargoCard(DomCard aCard) {
        cargoCards.add(aCard);
    }

    public int getActionsLeftNoVillagers() {
        return actionsLeft;
    }

    public int countDifferentCardsOfType(DomCardType domCardType) {
        return deck.countDifferentCardsOfType(domCardType);
    }

    public void setAnnoyedByMonkey(boolean b) {
        annoyedByMonkey = b;
    }

    public boolean isAnnoyedByMonkey() {
        return annoyedByMonkey;
    }

    public void setAttackedByCorsair(boolean b) {
        attackedByCorsair = b;
    }

    public boolean isAttackedByCorsair() {
        return attackedByCorsair;
    }

    public boolean hasTrashedForCorsair() {
        return trashedForCorsair;
    }

    public void setTrashedForCorsair(boolean b) {
        trashedForCorsair=b;
    }

    public int getCollectionTriggers() {
        return collectionTriggers;
    }

    public void addCollectionTrigger() {
        collectionTriggers++;
    }

    public DomCardName getLastGainedCardNotWayfarer() {
        int i=getCardsGainedLastTurn().size()-1;
        while (i>=0 && getCardsGainedLastTurn().get(i) == DomCardName.Wayfarer){
            i--;
        };
        if (i>-1)
            return (getCardsGainedLastTurn().get(i));

        return null;
    }

    public void addPreparedCards(ArrayList<DomCard> cardsToPrepare) {
        myPreparedCards.addAll(cardsToPrepare);
    }

    public void addPreparedCard(DomCard cardToPrepare) {
        myPreparedCards.add(cardToPrepare);
    }

    public ArrayList<DomCard> getPreparedCards() {
        return myPreparedCards;
    }

    public void addMiningRoadTrigger() {
        miningRoadTriggers++;
    }

    public int getMiningRoadTriggers() {
        return miningRoadTriggers;
    }

    public void removeMiningRoadTrigger() {
        miningRoadTriggers--;
    }

    public void gainLoot() {

    }

    public boolean hasGained$5ThisTurn() {
        for (DomCardName card : getCardsGainedLastTurn()) {
            if (card.getCoinCost(this)==5 && card.getPotionCost()==0 && card.getDebtCost()==0) {
                return true;
            }
        }
        return false;
    }

    public int getCarnivalsPlayed() {
        return carnivalsPlayed;
    }

    public void addCarnivalsPlayed() {
        this.carnivalsPlayed++;
    }

    public int getCarnivalDraws() {
        return carnivalDraws;
    }

    public void addCarnivalDraws(int carnivalDraws) {
        this.carnivalDraws += carnivalDraws;
    }

    public void increaseSmallPotatoesPlayedCounter() {
        smallPotatoesPlayed++;
    }

    public int getSmallPotatoesPlayed() {
        return smallPotatoesPlayed;
    }

    public void increaseBridgeTrollPlayedCounter() {
        bridgeTrollPlayedCount++;
    }

    public void addEndTurnExtraDraws(int theTotalCards) {
        endTurnExtraDraws+=theTotalCards;
    }

    public void recordMagicLampOpened() {
        magicLampOpened+=getTurns();
    }

    public int getMagicLampOpened() {
        return magicLampOpened;
    }

    public void setUberDominationWinTrigger() {
        uberDominationWinTrigger=true;
    }

    public boolean getUberDominationWinTrigger() {
        return uberDominationWinTrigger;
    }

    public void addSunForProphecy(int i) {
        getCurrentGame().decreaseProphecyCounter(i);
    }

    public void setAsideForForesight(DomCard domCard) {
        foresightedCards.add(domCard);
    }

    public void addWeddingCounter() {
        weddingCounter++;
    }

    public double getWeddingCounter() {
        return weddingCounter;
    }

    public void addAvoidTrigger() {
        avoidTriggers++;
    }

    public int getAvoidTriggers() {
        return avoidTriggers;
    }

    public void resetAvoidTriggers() {
        avoidTriggers=0;
    }

    public void triggerRush() {
        rushTriggered=true;
    }

    public boolean hasTriggeredRush() {
        return rushTriggered;
    }

    public void triggerSailor() {
        sailorTriggers++;
    }

    public boolean hasSailorTriggersLeft() {
        return sailorTriggers>0;
    }

    public void removeTriggerSailor() {
        sailorTriggers--;
    }

    public int getDifferentTreasuresInHand() {
        Set<DomCardName> differentTreasureCards = new HashSet();
        for (DomCard card : getCardsFromHand(DomCardType.Treasure)) {
          differentTreasureCards.add(card.getName());
        }
        return differentTreasureCards.size();
    }

    public void triggerDeliver() {
        deliverTriggered=true;
    }

    public boolean getDeliverTriggered() {
        return deliverTriggered;
    }

    public void addDeliverCard(DomCard aCard) {
        deliverCards.add(aCard);
    }

    public DomCard getNextActionNotInPlayToPlay() {
        ArrayList<DomCard> theActionsToConsider = getCardsFromHand(DomCardType.Action);
        if (getCurrentGame().getBoard().containsShadowCards()) {
            for (DomCard card : getDeck().getDrawDeck()) {
                if (card.hasCardType(DomCardType.Shadow))
                    theActionsToConsider.add(card);
            }
        }
        if (theActionsToConsider.isEmpty())
            return null;
        Collections.sort(theActionsToConsider, DomCard.SORT_FOR_PLAYING);
        for (DomCard card : theActionsToConsider) {
            if (card.wantsToBePlayed() && getCardsFromPlay(card.getName()).isEmpty())
                return card;
        }
        return null;
    }
}