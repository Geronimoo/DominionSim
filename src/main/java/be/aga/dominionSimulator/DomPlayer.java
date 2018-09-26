package be.aga.dominionSimulator;

import java.awt.*;
import java.util.*;

import be.aga.dominionSimulator.cards.*;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import be.aga.dominionSimulator.enums.DomBotComparator;
import be.aga.dominionSimulator.enums.DomBotFunction;
import be.aga.dominionSimulator.enums.DomBotOperator;
import be.aga.dominionSimulator.enums.DomBotType;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPhase;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

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
    private int hoardCount = 0;
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
    private boolean saveActivated;
    private int expeditionsActivated;
    private DomCardName minus$2TokenOn;
    private DomCard estateTokenOn;
    private int coffersToAdd;
    private boolean extraMissionTurn;
    private boolean noBuyThisTurn;
    private DomCardName plusOneCoinTokenOn;
    private DomCardName trashingTokenOn;
    private int bridgesPlayedCount;
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

    public static ArrayList<DomCard> getMultiplesInHand(MenagerieCard card) {
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

    public DomCard findCardToRemodel(DomCard domCard, int theAmount) {
    	ArrayList<DomCard> theCardsToConsiderTrashing=new ArrayList<DomCard>();
    	ArrayList<DomCardName> theCardsToGain=new ArrayList<DomCardName>();
    	DomCardName theDesiredCardIfRemodelNotUsed = getDesiredCard(getTotalPotentialCurrency(), false);
        for (int i=0;i< getCardsInHand().size();i++) {
            if (getCardsInHand().get(i)== domCard)
                continue;
        	//temporarily remove the card from hand AND deck
        	DomCard theCard = getCardsInHand().remove(i);
            DomCost theMaxCostOfCardToGain = new DomCost( theCard.getCoinCost(getCurrentGame()) + theAmount, theCard.getPotionCost());
        	getDeck().get(theCard.getName()).remove(theCard );
      	    DomCardName theRemodelGainCard = getDesiredCard(theMaxCostOfCardToGain, false);
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

    public void makeBuyDecision() {
        for (DomBuyRule theBuyRule : getBuyRules()) {
            if (theBuyRule.getCardToBuy().hasCardType(DomCardType.Prize))
                continue;
            if (getCurrentGame().getBoard().isFromSeparatePile(theBuyRule.getCardToBuy()))
                continue;
            DomCost theCost = determineCostAndCheckSplitPiles(theBuyRule);
            if (theCost == null)
                continue;
            if (getTotalAvailableCurrency().compareButIgnoreDebtTo(theCost) < 0)
                continue;

            if (checkBuyConditions(theBuyRule)) {
                if (theBuyRule.getCardToBuy().hasCardType(DomCardType.Event)) {
                    if (!wantsEvent(theBuyRule.getCardToBuy()))
                        continue;
                    resolveEvent(theBuyRule.getCardToBuy());
                    if (debt > 0) {
                        payOffDebt();
                    }
                    return;
                }
                if (!getNoBuyThisTurn() && tryToBuy(theBuyRule.getCardToBuy(), true)) {
                    coffersToAdd += getCardsFromPlay(DomCardName.Merchant_Guild).size();
                    return;
                }
            }
        }
        if (DomEngine.haveToLog) DomEngine.addToLog(name + " buys NOTHING!");

        //a bit dirty setting buysLeft to 0 to make him stop trying to buy stuff and say 'buys nothing'
        //TODO maybe clean this up
        buysLeft = 0;
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
        if (cardToBuy == DomCardName.Borrow && borrowActivated)
            return false;
        if (cardToBuy == DomCardName.Quest && !checkForQuest())
            return false;
        if (cardToBuy == DomCardName.Mission && hasExtraMissionTurn())
            return false;
        if (cardToBuy == DomCardName.Windfall && getDeckSize() > 0)
            return false;
        if (cardToBuy == DomCardName.Advance && getCardsFromHand(DomCardType.Action).isEmpty())
            return false;
        if (cardToBuy == DomCardName.Ritual && getCardsInHand().isEmpty())
            return false;
        if (cardToBuy==DomCardName.Save && saveActivated)
            return false;

        return true;
    }

    private boolean checkForQuest() {
        return getCardsInHand().size() >= 6 || getCardsFromHand(DomCardName.Curse).size() >= 2 || !getCardsFromHand(DomCardType.Attack).isEmpty();
    }

    private DomCost getAvailableCurrencyWithoutTokens() {
        return getTotalAvailableCurrency().add(new DomCost(-coffers, 0));
    }

    private boolean checkIfWantsToHoardCoffers() {
        if (!stillInEarlyGame() && !isGoingToBuyTopCardInBuyRules(getTotalAvailableCurrency()) && countInDeck(DomCardName.Duchy) == 0)
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

    public int getTotalMoneyExcludingNativeVillage() {
        return deck.getTotalMoney() - getMoneyFromVillageMat();
    }

    /**
     * @return
     */
    private int getMoneyFromVillageMat() {
        int theTotal = 0;
        for (DomCard theCard : nativeVillageMat) {
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

    /**
     * @return
     */
    public int countInDeck(DomCardName aCardName) {
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
        initializeTurn();
        handleTeachers();
        handleGuides();
        resolveHorseTraders();
        resolveDurationEffects();
        resolveCardsToSummon();
        resolvePrincedCards();
        resolveRatcatchers();
        handleTransmogrify();
        handleDelayedBoons();
        handleLostInTheWoods();
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
    }

    private void handleLostInTheWoods() {
        if (!lostInTheWoods)
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
        while (theTeacher != null) {
            getCardsInPlay().add(removeFromTavernMat(theTeacher));
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " calls " + theTeacher + " from the tavern mat");
            theTeacher.doWhenCalled();
            theTeacher = getFromTavernMat(DomCardName.Teacher);
        }
    }

    private void doCleanUpPhase() {
        setPhase(DomPhase.CleanUp);
        while (!boons.isEmpty()) {
            getCurrentGame().getBoard().returnBoon(boons.remove(0));
        }
        for (DomCard theEncampment : mySetAsideEncampments) {
            returnToSupply(theEncampment);
        }
        mySetAsideEncampments.clear();
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
        for (DomPlayer thePlayer : getCurrentGame().getPlayers()) {
            thePlayer.addFaithFulHoundsToHand();
        }
        setAsideFaithfulHounds.clear();
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
        hoardCount = 0;
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
        if (river$sGiftActive)
            drawCards(1);
        //edge case with Sacred Grove
        for (DomPlayer theOpp : getOpponents()) {
            if (theOpp.isRiver$sGiftActive())
                drawCards(1);
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
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " resolves duration effect from " + aCard);
            aCard.resolveDuration();
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

    public void initializeTurn() {
        if (possessor == null && !extraOutpostTurn && !extraMissionTurn) {
            turns++;
            sumTurns++;
        }
        if (DomEngine.haveToLog) showBeginningOfTurnLog();
        actionsLeft = 1;
        buysLeft = 1;
        availableCoins = 0;
        availablePotions = 0;
        hoardCount = 0;
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
        coppersmithsPlayedCount = 0;
        hasDoubledMoney = false;
        charmReminder = 0;
        donateTriggered = false;
        cantBuyActions = false;
        enviousActive = false;
        gainedExtraExperiment = false;
        trashingBonus=0;
        //TODO moved from cleanup to here.. maybe problems
        resetVariables();
    }

    private void doBuyPhase() {
        long theTime = System.currentTimeMillis();
        setPhase(DomPhase.Buy);
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
        coffersToAdd = 0;
        updateMoneyCurve();

        while (buysLeft > 0) {
            if (debt > 0)
                payOffDebt();
            if (debt > 0) {
                if (DomEngine.haveToLog)
                    DomEngine.addToLog(name + " has $" + debt + " in debt left so can't buy cards or events");
                break;
            }
            makeBuyDecision();
            buysLeft--;
            if (isVillaTriggered()) {
                setVillaTriggered(false);
                if (DomEngine.haveToLog)
                    DomEngine.addToLog(name + " triggers " + DomCardName.Villa.toHTML() + " and moves back to the action phase");
                doActionPhase();
                doBuyPhase();
            }
        }
        if (coffersToAdd > 0) {
            addCoffers(coffersToAdd);
            coffersToAdd = 0;
        }
        handleWineMerchants();
        buyTime += System.currentTimeMillis() - theTime;
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
        if (checkSuicide && suicideIfBuys(aCardName)) {
            if (DomEngine.haveToLog) DomEngine.addToLog(
                    "<FONT style=\"BACKGROUND-COLOR: red\">SUICIDE!</FONT> Can not buy " + aCardName.toHTML());
            return false;
        }
        if (forbiddenCardsToBuy.contains(aCardName))
            return false;

        if (aCardName == DomCardName.Grand_Market && !getCardsFromPlay(DomCardName.Copper).isEmpty())
            return false;

        if (!isHumanOrPossessedByHuman() && coffers > 0 && getDesiredCard(getAvailableCurrencyWithoutTokens(), false) != aCardName && checkIfWantsToHoardCoffers() && !wants(DomCardName.Gardens)) {
            return false;
        }
        DomCard theCardToBuy = game.takeFromSupply(aCardName);
        buy(theCardToBuy);
        return true;
    }

    private void resolveEvent(DomCardName aCardName) {
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " buys event: " + aCardName.toHTML());
        DomCard theCard = aCardName.createNewCardInstance();
        theCard.owner = getPossessor() == null ? this : getPossessor();
        theCard.play();
        availableCoins -= aCardName.getCost().getCoins();
        if (availableCoins < 0) {
            spendCoffers(-availableCoins);
            availableCoins = 0;
        }
        addDebt(aCardName.getCost(getCurrentGame()).getDebt());
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
        for (int i = 0; i < hoardCount; i++) {
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
    }

    public void buy(DomCard aCard) {
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
        long theTime = System.currentTimeMillis();
        DomCard theCardToPlay = null;
        do {
            theCardToPlay = getNextActionToPlay();
            if (theCardToPlay != null) {
                if (actionsLeft==0)
                useVillager();
                actionsLeft--;
                handleUrchins(theCardToPlay);
                play(removeCardFromHand(theCardToPlay));
                maybeHandleRoyalCarriage(theCardToPlay);
                if (actionsLeft == 0 && getFromTavernMat(DomCardName.Coin_of_the_Realm) != null && getNextActionToPlay() != null) {
                    handleCoinOfTheRealm();
                }
            }
        } while (actionsLeft + villagers> 0 && theCardToPlay != null);
        actionTime += System.currentTimeMillis() - theTime;
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
                        if (countInDeck(DomCardName.Royal_Carriage) < 5) {
                            int theRCs = countOnTavernMat(DomCardName.Royal_Carriage);
                            if (getTotalPotentialCurrency().getCoins() >= 8 - 3 * theRCs)
                                handleRoyalCarriages();
                        }
                    }
                } else {
                    handleRoyalCarriages();
                }
            }
        }
    }

    private void handleMidGameRoyalCarriageForBridge() {
        DomCard theRoyalCarriage = removeFromTavernMat(getFromTavernMat(DomCardName.Royal_Carriage));
        while (getTotalPotentialCurrency().getCoins()<DomCardName.Royal_Carriage.getCoinCost(getCurrentGame())*2) {
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
                setPhase(DomPhase.Buy);
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
        if (aPhase == DomPhase.Buy) {
            maybeHandleArena();
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
        if ( isHumanOrPossessedByHuman() && getCurrentGame().getBoard().isLandmarkActive(DomCardName.Arena)
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

    public DomCard getNextActionToPlay() {
        ArrayList<DomCard> theActionsToConsider = getCardsFromHand(DomCardType.Action);
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
        if (actionsplayed == 1 && aCard.hasCardType(DomCardType.Action) && handleEnchantress(aCard)) {
            addActions(1);
            drawCards(1);
        } else {
            if (DomEngine.haveToLog) {
                playAndLog(aCard);
            } else {
                playThis(aCard);
            }
        }
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
        aCard.play();
        if (aCard.hasCardType(DomCardType.Duration) && !aCard.durationFailed())
            aCard.setDiscardAtCleanup(false);
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
        journeyTokenIsFaceUp = true;
        if (getCurrentGame().countInSupply(DomCardName.Baker) > 0)
            addCoffers(1);
        minusOneCardToken = false;
        minusOneCoinToken = false;
        plusOneBuyTokenOn = null;
        plusOneCardTokenOn = null;
        plusOneActionTokenOn = null;
        plusOneCoinTokenOn = null;
        trashingTokenOn = null;
        minus$2TokenOn = null;
        cardsToSummon.clear();
        debt = 0;
        deluded=false;
        envious=false;
        setExtraMissionTurn(false);
        lostInTheWoods=false;
        miserable=false;
        twiceMiserable=false;
        setAsideFaithfulHounds.clear();
        villagers=0;
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
        if (discardsLeft==0)
            return;
        if (isHumanOrPossessedByHuman()) {
            setNeedsToUpdateGUI();
            ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
            myEngine.getGameFrame().askToSelectCards("Choose "+discardsLeft+" cards to discard" +(discardToTopOfDeck?" to top of deck":""), cardsInHand, theChosenCards, discardsLeft);
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
        if (getActionsLeft() > 0)
            checkForPossibleTrashingBeforeDiscarding(discardsLeft);
        //then discard the rest
        while (!cardsInHand.isEmpty() && discardsLeft > 0) {
            discard(cardsInHand.get(0), discardToTopOfDeck);
            discardsLeft--;
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
                if (getDesiredCard(theTotalMoney, false) == theRule.getCardToBuy())
                  return true;
                else
                  return false;
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
        if (aCardToTrash == null)
            return;
        if (aCardToTrash.owner == null || possessor == null) {
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " trashes a " + aCardToTrash);
            game.addToTrash(aCardToTrash);
        }
        if (!getCardsFromHand(DomCardName.Market_Square).isEmpty() && getCardsFromPlay(DomCardName.Forge).isEmpty()&&aCardToTrash.owner!=null) {
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
        if (getTrashingBonus()>0)
            addAvailableCoins(2*getTrashingBonus());
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
                && checkPenultimateProvinceRule(aCardName))
            return true;

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
        if (theNextCardToHandle instanceof MultiplicationCard) {
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " resolves duration effect from " + theNextCardToHandle.getName().toHTML());
            for (DomCard theDuration: ((MultiplicationCard)theNextCardToHandle).getDurationCards()){
                theDuration.resolveDuration();
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
        beginningOfTurnTriggers.addAll(princedCards);
        beginningOfTurnTriggers.addAll(delayedBoons);
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
        if (minusOneCoinToken) {
            if (DomEngine.haveToLog && aI > 0) DomEngine.addToLog(this + " loses -$1 token");
            aI--;
            if (aI < 0)
                aI = 0;
            minusOneCoinToken = false;
        }

        availableCoins += aI;
    }

    public void addActions(int aI) {
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " gets +" + aI + " actions");
        actionsLeft += aI;
    }

    public ArrayList<DomCard> getNativeVillageMat() {
        return nativeVillageMat;
    }

    public int getTotalMoneyInDeck() {
        return deck.getTotalMoney();
    }

    public int getDeckSize() {
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
        if (countInDeck(DomCardName.Baron) > 0 && countInDeck(DomCardName.Estate) < 3 && theCardToTrash.getName() == DomCardName.Estate) {
            if (!getCardsFromHand(DomCardName.Copper).isEmpty())
                theCardToTrash = getCardsFromHand(DomCardName.Copper).get(0);
        }
        return removeCardFromHand(theCardToTrash);
    }

    public int getActionsLeft() {
        return actionsLeft;
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
     *
     */
    public void increaseHoardCount() {
        hoardCount++;
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
                    || theRule.getCardToBuy().hasCardType(DomCardType.Event)) {
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
                                    && getCurrentGame().countInSupply(cardToBuy) > 0))
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
        if (getCurrentGame().countInSupply(aCardName) == 0
                || suicideIfBuys(aCardName))
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
        int probableActionsLeft = getActionsLeft();
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
            if (cardName.hasCardType(DomCardType.Victory) && cardName.getDiscardPriority(1) < 10 && countInDeck(cardName) > 0)
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
        if (possessor != null) {
            possessor.addCoffers(i);
            return;
        }
        coffers += i;
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " adds " + i + " coffers.");
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

    private void resolveCardsToSummon() {
        for (DomCard theCard : cardsToSummon) {
            if (DomEngine.haveToLog) DomEngine.addToLog(name + " plays Summoned cards");
            play(theCard);
        }
        cardsToSummon.clear();
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
        }
        availableCoins -= theAmount;
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
                        setPhase(DomPhase.Buy);
                } else {
                    if (villagers==0)
                      setPhase(DomPhase.Buy);
                }
            }
        } else {
            if (selectedCard.hasCardType(DomCardType.Treasure) && getPhase() == DomPhase.Buy && (getBoughtCards().isEmpty()||getBoughtCards().get(getBoughtCards().size()-1).getName()==DomCardName.Villa)) {
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
        if (getPhase()!=DomPhase.Buy)
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
        if (getPhase()==DomPhase.Buy && buysLeft>0) {
            if (debt>0) {
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
            if (availableCoins>=card.getCoinCost(myEngine.getCurrentGame())
                    && availablePotions>=card.getPotionCost()) {
                if (card.hasCardType(DomCardType.Event)) {
                    resolveEvent(card);
                    buysLeft--;
                    setChanged();
                    notifyObservers();
                } else {
                    if (!getNoBuyThisTurn() && tryToBuy(card, false)) {
                        buysLeft--;
                        if (getCardsFromPlay(DomCardName.Merchant_Guild).size() > 0)
                            addCoffers(getCardsFromPlay(DomCardName.Merchant_Guild).size());
                        setChanged();
                        notifyObservers();
                    }
                }
                if (getPhase()==DomPhase.Buy && buysLeft==0 && (getDebt()==0 || getAvailableCoins()==0) ) {
                    if (!getCardsFromHand(DomCardType.Night).isEmpty()){
                        setPhase(DomPhase.Night);
                    } else {
                        endHumanTurn();
                    }
                }
            } else {
                if (getTotalPotentialCurrency().customCompare( card.getCost(getCurrentGame())) >= 0 && baseTreasuresInHand()) {
                    attemptToPlayAllTreasures();
                    attemptToBuyFromSupplyAsHuman(card);
                }
            }
        }
    }

    public void endActions() {
        if (!getCurrentGame().isGameFinished() || currentPhase!=null) {
            setPhase(DomPhase.Buy);
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
        return getTotalAvailableCoins()>=theCard.getCoinCost(getCurrentGame()) && getTotalPotentialCurrency().potions>=theCard.getPotionCost();
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

    public void gainVillagers(int i) {
        villagers+=i;
        if (DomEngine.haveToLog)
            DomEngine.addToLog(this + " has " + villagers + " villager" + (villagers==1? "":"s") );
    }

    public int countVillagers() {
        return villagers;
    }
}