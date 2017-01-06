package be.aga.dominionSimulator;

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

public class DomPlayer implements Comparable<DomPlayer> {
    protected static final Logger LOGGER = Logger.getLogger(DomPlayer.class);

    static {
        LOGGER.setLevel(DomEngine.LEVEL);
        LOGGER.removeAllAppenders();
        if (DomEngine.addAppender)
            LOGGER.addAppender(new ConsoleAppender(new SimpleLayout()));
    }

    private ArrayList<DomBuyRule> buyRules = new ArrayList<DomBuyRule>();
    private ArrayList<DomBuyRule> prizeBuyRules = new ArrayList<DomBuyRule>();
    private EnumMap<DomCardName, DomPlayStrategy> playStrategies = new EnumMap<DomCardName, DomPlayStrategy>(DomCardName.class);
    private String[] keywords = null;

    private DomDeck deck = new DomDeck(this);
    private ArrayList<DomCard> cardsInPlay = new ArrayList<DomCard>();
    private ArrayList<DomCard> cardsInHand = new ArrayList<DomCard>();
    private ArrayList<DomCard> nativeVillageMat = new ArrayList<DomCard>();
    private ArrayList<DomCard> horseTradersPile = new ArrayList<DomCard>();
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
    private int sameCardCount = 0;
    private DomCardName previousPlayedCardName = null;
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
    private int coinTokens;
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
    private boolean expeditionActivated;
    private DomCardName minus$2TokenOn;
    private DomCard estateTokenOn;
    private int coinTokensToAdd;
    private boolean extraMissionTurn;
    private DomCardName plusOneCoinTokenOn;
    private DomCardName trashingTokenOn;
    private int bridgesPlayedCount;
    private int debt;
    private boolean hasDoubledMoney;
    private int charmReminder = 0;
    private ArrayList<DomCard> mySetAsideEncampments = new ArrayList<DomCard>();
    private boolean donateTriggered;
    private int mountainPassBid = 0;
    private DomCardName obeliskChoice = null;
    private boolean villaTriggered = false;
    private int merchantsPlayed;
    private int drawDeckSize;

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
                    return;
                }
                if (!hasExtraMissionTurn() && tryToBuy(theBuyRule.getCardToBuy())) {
                    coinTokensToAdd += getCardsFromPlay(DomCardName.Merchant_Guild).size();
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
        DomCost theCost = theBuyRule.getCardToBuy().getCost(getCurrentGame());
        if (theBuyRule.getCardToBuy() == DomCardName.Castles) {
            if (getCurrentGame().countInSupply(DomCardName.Castles) > 0)
                theCost = getCurrentGame().getBoard().get(DomCardName.Castles).get(0).getCost(getCurrentGame());
            else
                theCost = null;
        }
        if (theBuyRule.getCardToBuy() == DomCardName.Knights) {
            if (getCurrentGame().countInSupply(DomCardName.Knights) > 0)
                theCost = getCurrentGame().getBoard().get(DomCardName.Knights).get(0).getCost(getCurrentGame());
            else
                theCost = null;
        }
        if (theBuyRule.getCardToBuy() == DomCardName.Gladiator) {
            if (getCurrentGame().countInSupply(DomCardName.Gladiator) < 6)
                theCost = null;
        }
        if (theBuyRule.getCardToBuy() == DomCardName.Fortune) {
            if (getCurrentGame().countInSupply(DomCardName.Gladiator) > 5)
                theCost = null;
        }
        if (theBuyRule.getCardToBuy() == DomCardName.Settlers) {
            if (getCurrentGame().countInSupply(DomCardName.Settlers) < 6)
                theCost = null;
        }
        if (theBuyRule.getCardToBuy() == DomCardName.Bustling_Village) {
            if (getCurrentGame().countInSupply(DomCardName.Settlers) > 5)
                theCost = null;
        }
        if (theBuyRule.getCardToBuy() == DomCardName.Catapult) {
            if (getCurrentGame().countInSupply(DomCardName.Catapult) < 6)
                theCost = null;
        }
        if (theBuyRule.getCardToBuy() == DomCardName.Rocks) {
            if (getCurrentGame().countInSupply(DomCardName.Catapult) > 5)
                theCost = null;
        }
        if (theBuyRule.getCardToBuy() == DomCardName.Patrician) {
            if (getCurrentGame().countInSupply(DomCardName.Patrician) < 6)
                theCost = null;
        }
        if (theBuyRule.getCardToBuy() == DomCardName.Emporium) {
            if (getCurrentGame().countInSupply(DomCardName.Patrician) > 5)
                theCost = null;
        }
        if (theBuyRule.getCardToBuy() == DomCardName.Encampment) {
            if (getCurrentGame().countInSupply(DomCardName.Encampment) < 6)
                theCost = null;
        }
        if (theBuyRule.getCardToBuy() == DomCardName.Plunder) {
            if (getCurrentGame().countInSupply(DomCardName.Encampment) > 5)
                theCost = null;
        }
        if (theBuyRule.getCardToBuy() == DomCardName.Sauna) {
            if (getCurrentGame().countInSupply(DomCardName.Sauna) < 6)
                theCost = null;
        }
        if (theBuyRule.getCardToBuy() == DomCardName.Avanto) {
            if (getCurrentGame().countInSupply(DomCardName.Sauna) > 5)
                theCost = null;
        }
        return theCost;
    }

    private boolean wantsEvent(DomCardName cardToBuy) {
        if (cardToBuy == DomCardName.Pilgrimage && pilgrimageActivatedThisTurn)
            return false;
        if (cardToBuy == DomCardName.Alms && (almsActivated || countInPlay(DomCardType.Treasure) > 0))
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

        return true;
    }

    private boolean checkForQuest() {
        return getCardsInHand().size() >= 6 || getCardsFromHand(DomCardName.Curse).size() >= 2 || !getCardsFromHand(DomCardType.Attack).isEmpty();
    }

    private DomCost getAvailableCurrencyWithoutTokens() {
        return getTotalAvailableCurrency().add(new DomCost(-coinTokens, 0));
    }

    private boolean checkIfWantsToHoardCoinTokens() {
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
        doActionPhase();
        doBuyPhase();
        doCleanUpPhase();
        if (getCardsGainedLastTurn().isEmpty() && getCurrentGame().getBoard().isLandmarkActive(DomCardName.Baths)) {
            int theVP = getCurrentGame().getBoard().removeVPFrom(DomCardName.Baths, 2);
            if (theVP > 0) {
                if (DomEngine.haveToLog) DomEngine.addToLog(this + " takes VP from " + DomCardName.Baths.toHTML());
                addVP(theVP);
            }
        }

        //actually this is not part of the turn so we set Possessor to null
        possessor = null;
        if (donateTriggered)
            DonateCard.trashStuff(this);
        if (getCurrentGame().isAuctionTriggered()) {
            Mountain_PassCard.doTheAuction(this);
            getCurrentGame().setAuctionTriggered(false);
        }
        //TODO moved from buy phase to here... ok?
        updateVPCurve(false);
        //TODO needed fixing
        actionsLeft=1;
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
        setPhase(null);
        //reset variables needed for total money checking in other player's turns
        availableCoins=0;
        availablePotions=0;
    }

    private void showBeginningOfTurnLog() {
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
        if (expeditionActivated)
            drawCards(2);
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
        deck.addToDiscardPile(cardsInHand);
        cardsInHand.clear();
    }

    protected void discardAll() {
        deck.addToDiscardPile(cardsInHand);
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

    private void initializeTurn() {
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
        expeditionActivated = false;
        bridgesPlayedCount = 0;
        hasDoubledMoney = false;
        charmReminder = 0;
        donateTriggered = false;
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
        if (!isInBuyRules(DomCardName.Alms) || getTotalPotentialCurrency().compareTo(new DomCost(4, 0)) > 0)
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
        coinTokensToAdd = 0;
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
        if (boughtCards.isEmpty()) {
            while (!getCardsFromPlay(DomCardName.Hermit).isEmpty()) {
                trash(removeCardFromPlay(getCardsFromPlay(DomCardName.Hermit).get(0)));
                gain(DomCardName.Madman);
            }
        }
        if (coinTokensToAdd > 0) {
            addCoinTokens(coinTokensToAdd);
            coinTokensToAdd = 0;
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
        if (coinTokens > 0) {
            theMessage.append(" (+").append(coinTokens).append(" coin tokens)");
        }
        theMessage.append(" to spend and " + buysLeft + " buy" + (buysLeft > 1 ? "s" : ""));
        DomEngine.addToLog(theMessage.toString());
    }

    /**
     * @param isCumulative
     */
    private void updateVPCurve(boolean isCumulative) {
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
    private void updateMoneyCurve() {
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
    protected boolean tryToBuy(DomCardName aCardName) {
        if (game.countInSupply(aCardName) == 0) {
//          if (DomEngine.haveToLog) DomEngine.addToLog( aCardName + " is no more available to buy");
            return false;
        }
        if (suicideIfBuys(aCardName)) {
            if (DomEngine.haveToLog) DomEngine.addToLog(
                    "<FONT style=\"BACKGROUND-COLOR: red\">SUICIDE!</FONT> Can not buy " + aCardName.toHTML());
            return false;
        }
        if (forbiddenCardsToBuy.contains(aCardName))
            return false;

        if (aCardName == DomCardName.Grand_Market && !getCardsFromPlay(DomCardName.Copper).isEmpty())
            return false;

        if (coinTokens > 0 && getDesiredCard(getAvailableCurrencyWithoutTokens(), false) != aCardName && checkIfWantsToHoardCoinTokens() && !wants(DomCardName.Gardens)) {
            return false;
        }

        buy(game.takeFromSupply(aCardName));
        return true;
    }

    private void resolveEvent(DomCardName aCardName) {
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " buys event: " + aCardName.toHTML());
        DomCard theCard = aCardName.createNewCardInstance();
        theCard.owner = getPossessor() == null ? this : getPossessor();
        theCard.play();
        availableCoins -= aCardName.getCost().getCoins();
        if (availableCoins < 0) {
            spendCoinTokens(-availableCoins);
            availableCoins = 0;
        }
        addDebt(aCardName.getCost(getCurrentGame()).getDebt());
        if (debt > 0) {
            payOffDebt();
        }
    }

    /**
     * @param aCard
     */
    public void handleSpecialBuyEffects(DomCard aCard) {
        while (charmReminder > 0) {
            DomCardName theDifferentCard = getDesiredCardWithRestriction(null, aCard.getCost(getCurrentGame()), true, aCard.getName());
            if (theDifferentCard == null)
                break;
            gain(theDifferentCard);
            charmReminder--;
        }
        for (DomCard theHaggler : getCardsFromPlay(DomCardName.Haggler)) {
            ((HagglerCard) theHaggler).haggleFor(aCard);
        }
        int theGoonsCount = getCardsFromPlay(DomCardName.Goons).size();
        if (theGoonsCount > 0) {
            addVP(theGoonsCount);
        }
        for (int i = 0; i < getCardsFromPlay(DomCardName.Talisman).size(); i++) {
            if (new DomCost(4, 0).compareTo(aCard.getCost(getCurrentGame())) >= 0
                    && !aCard.hasCardType(DomCardType.Victory)) {
                DomCard theDouble = getCurrentGame().takeFromSupply(aCard.getName());
                if (theDouble != null) {
                    gain(theDouble);
                }
            }
        }
        if (aCard.getName() == DomCardName.Messenger && boughtCards.size() == 1) {
            DomCardName theDesiredCard = getDesiredCard(new DomCost(4, 0), false);
            if (theDesiredCard == null)
                theDesiredCard = getCurrentGame().getBestCardInSupplyFor(this, null, new DomCost(4, 0));
            if (theDesiredCard != null) {
                gain(theDesiredCard);
                for (DomPlayer thePlayer : getOpponents())
                    thePlayer.gain(theDesiredCard);
            }
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
            DomCardName theDesiredCard = getDesiredCardWithRestriction(DomCardType.Action, getTotalAvailableCurrency(), false, DomCardName.Stonemason);
            if (theDesiredCard != null) {
                availableCoins -= theDesiredCard.getCoinCost(getCurrentGame());
                availablePotions -= theDesiredCard.getPotionCost();
                if (DomEngine.haveToLog)
                    DomEngine.addToLog(this + " overpays " + theDesiredCard.getCost(getCurrentGame()));
                gain(theDesiredCard);
                theDesiredCard = getDesiredCardWithRestriction(DomCardType.Action, theDesiredCard.getCost(getCurrentGame()), true, DomCardName.Stonemason);
                if (theDesiredCard != null)
                    gain(theDesiredCard);
            }
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
            spendCoinTokens(-availableCoins);
            if (coinTokens < 0) {
               LOGGER.error("Coin tokens: " + coinTokens);
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
        deck.gain(aCard);
        boughtCards.add(aCard);

        if (getTrashingTokenOn() == aCard.isFromPile())
            maybeTrashACardFromHand();
        handleSpecialBuyEffects(aCard);
        if (debt > 0) {
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
        if (aCard.hasCardType(DomCardType.Victory) && !getCardsFromHand(DomCardName.Hovel).isEmpty())
            trash(removeCardFromHand(getCardsFromHand(DomCardName.Hovel).get(0)));
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

    private void maybeTrashACardFromHand() {
        if (getCardsInHand().isEmpty())
            return;
        Collections.sort(getCardsInHand(), DomCard.SORT_FOR_TRASHING);
        if (getCardsInHand().get(0).getTrashPriority() <= DomCardName.Copper.getTrashPriority())
            trash(removeCardFromHand(getCardsInHand().get(0)));
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
                    for (DomCard theCard : cardsInHand) {
                        putOnTopOfDeck(theCard);
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
                actionsLeft--;
                play(removeCardFromHand(theCardToPlay));
                handleUrchins(theCardToPlay);
                if (cardsInPlay.contains(theCardToPlay) && theCardToPlay.getName() != DomCardName.Tactician && theCardToPlay.getName()!=DomCardName.Bridge_Troll && getFromTavernMat(DomCardName.Royal_Carriage) != null) {
                    if (getPlayStrategyFor(getFromTavernMat(DomCardName.Royal_Carriage)) == DomPlayStrategy.bigTurnBridge) {
                        if (theCardToPlay.getName() == DomCardName.Bridge && countOnTavernMat(DomCardName.Royal_Carriage) >= 5)
                            handleRoyalCarriages();
                    } else {
                        handleRoyalCarriages();
                    }
                }
                if (actionsLeft == 0 && getFromTavernMat(DomCardName.Coin_of_the_Realm) != null && getNextActionToPlay() != null) {
                    handleCoinOfTheRealm();
                }
            }
        } while (actionsLeft > 0 && theCardToPlay != null);
        actionTime += System.currentTimeMillis() - theTime;
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

    private void handleUrchins(DomCard theCardToPlay) {
        if (!theCardToPlay.hasCardType(DomCardType.Attack))
            return;
        while (!getCardsFromPlay(DomCardName.Urchin).isEmpty() && wants(DomCardName.Mercenary)) {
            if (getCardsFromPlay(DomCardName.Urchin).get(0)==theCardToPlay)
                break;
            trash(removeCardFromPlay(getCardsFromPlay(DomCardName.Urchin).get(0)));
            gain(DomCardName.Mercenary);
        }
    }

    private void handleCoinOfTheRealm() {
        getCardsInPlay().add(removeFromTavernMat(getFromTavernMat(DomCardName.Coin_of_the_Realm)));
        if (DomEngine.haveToLog)
            DomEngine.addToLog(this + " calls " + DomCardName.Coin_of_the_Realm.toHTML() + " from the tavern mat");
        addActions(2);
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

    private void setPhase(DomPhase aPhase) {
        currentPhase = aPhase;
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
            if (!aCard.hasCardType(DomCardType.Kingdom) && !aCard.hasCardType(DomCardType.Prize) && aCard.getName() != DomCardName.Mercenary && !aCard.hasCardType(DomCardType.Traveller) && aCard.getName() != DomCardName.Champion && aCard.getName() != DomCardName.Madman && aCard.getName() != DomCardName.Necropolis) {
                previousPlayedCardName = aCard.getName();
            } else {
                previousPlayedCardName = null;
            }
            sameCardCount = 0;
        } else {
            sameCardCount++;
        }
        if (aCard.hasCardType(DomCardType.Kingdom) || aCard.hasCardType(DomCardType.Prize) || aCard.getName() == DomCardName.Mercenary || aCard.hasCardType(DomCardType.Traveller) || aCard.getName() == DomCardName.Champion || aCard.getName() == DomCardName.Necropolis || aCard.getName() == DomCardName.Madman) {
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

        if (aCard.hasCardType(DomCardType.Duration))
            aCard.setDiscardAtCleanup(false);
        aCard.play();
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
        coinTokens = 0;
        journeyTokenIsFaceUp = true;
        if (getCurrentGame().countInSupply(DomCardName.Baker) > 0)
            addCoinTokens(1);
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
        setExtraMissionTurn(false);
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

    private void discard(DomCard aCard, boolean discardToTopOfDeck) {
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
        if (getBuyRules().isEmpty())
            //something is wrong, so we don't bother
            return false;
        DomCardName theTopCardInBuyRules = getBuyRules().get(0).getCardToBuy();
        if (wantsToGainOrKeep(theTopCardInBuyRules) && getDesiredCard(theTotalMoney, false) == theTopCardInBuyRules)
            //we don't want to mess with a hand if we're going to buy the top card this turn (although we could)
            return true;
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
        if (aCardToTrash.owner != null) {
            aCardToTrash.doWhenTrashed();
            deck.trash(aCardToTrash);
        } else {
            //fix for Lurker
            aCardToTrash.owner=this;
            aCardToTrash.doWhenTrashed();
            aCardToTrash.owner=null;
        }
        if (aCardToTrash.getName() == DomCardName.Fortress) {
            game.getTrashedCards().remove(aCardToTrash);
            gainInHand(aCardToTrash);
        }
        if (!getCardsFromHand(DomCardName.Market_Square).isEmpty() && getCardsFromPlay(DomCardName.Forge).isEmpty()) {
            for (DomCard theMS : getCardsFromHand(DomCardName.Market_Square)) {
                discardFromHand(theMS);
                gain(DomCardName.Gold);
            }
        }
        if (getCurrentGame().getBoard().isLandmarkActive(DomCardName.Tomb)) {
            if (DomEngine.haveToLog) DomEngine.addToLog("Landmark " + DomCardName.Tomb.toHTML() + " is active");
            addVP(1);
        }
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
        for (DomPlayer thePlayer : getOpponents()) {
            if (countVictoryPoints() < thePlayer.countVictoryPoints() - aCardName.getVictoryValue(this)) {
                return true;
            }
            if (countVictoryPoints() == thePlayer.countVictoryPoints() - aCardName.getVictoryValue(this)) {
                if (getTurns() > thePlayer.getTurns()) {
                    return true;
                }
            }
        }
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
        DomPlayer theOpponentToTheLeft = theOpponents.get(0);
        if (theOpponentToTheLeft != theMasqueradePlayer) {
            theOpponentToTheLeft.passCardToTheLeftForMasquerade(theOpponentToTheLeft.chooseCardToPass(), theMasqueradePlayer);
        }
        if (aCardToPass != null) {
            if (DomEngine.haveToLog)
                DomEngine.addToLog(this + " passes a " + aCardToPass + " to " + theOpponentToTheLeft);
            removePhysicalCard(aCardToPass);
            theOpponentToTheLeft.receiveCard(aCardToPass);
        }
    }

    private void receiveCard(DomCard aCardToPass) {
        deck.addPhysicalCardFromMasquerade(aCardToPass);
        cardsInHand.add(aCardToPass);
    }

    /**
     * @return
     */
    public DomCard chooseCardToPass() {
        if (getCardsInHand().isEmpty())
            return null;
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
        return availableCoins + coinTokens;
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
        theCopy.buyRules = (ArrayList<DomBuyRule>) buyRules.clone();
        theCopy.prizeBuyRules = (ArrayList<DomBuyRule>) prizeBuyRules.clone();
        theCopy.playStrategies = playStrategies.clone();
        for (DomBotType botType : getTypes()) {
            theCopy.addType(botType);
        }
        theCopy.setStartState(myStartState);
        theCopy.setSuggestedBoard(mySuggestedBoardCards);
        theCopy.setBane(myBaneCard);
        theCopy.setMountainPassBid(mountainPassBid);
        theCopy.setObeliskCard(obeliskChoice == null ? null : obeliskChoice.toString());
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
            if (wantsToGain) {
                if ((!costExact && anAvailableCurrency.compareTo(cardToBuy.getCost(getCurrentGame())) >= 0)
                        || (costExact && anAvailableCurrency.compareTo(cardToBuy.getCost(getCurrentGame())) == 0)) {
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
            if (wantsToGain) {
                if ((!costExact && anAvailableCurrency.compareTo(theRule.getCardToBuy().getCost(getCurrentGame())) >= 0)
                        || (costExact && anAvailableCurrency.compareTo(theRule.getCardToBuy().getCost(getCurrentGame())) == 0)) {
                    if (!suicideIfBuys(theRule.getCardToBuy()) && getCurrentGame().countInSupply(theRule.getCardToBuy()) > 0)
                        return theRule.getCardToBuy();
                }
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
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
        theTotalCoins += coinTokens;
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
        if (theValue.compareTo(DomCost.ZERO) > 0) {
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
        if (aCost.compareTo(DomCost.ZERO) > 0) {
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
        return new DomCost(availableCoins + coinTokens, availablePotions);
    }

    public void addToBoughtCards(DomCard theCard) {
        boughtCards.add(theCard);
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
            if (cardName.hasCardType(DomCardType.Victory) && cardName.getDiscardPriority(1) < 10)
                if (countInDeck(cardName) > 0)
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
        for (int c = 0; c < 7; c++) {
            gainOnTopOfDeck(getCurrentGame().takeFromSupply(DomCardName.Copper));
        }
        for (int c = 0; c < 3; c++) {
            gainOnTopOfDeck(getCurrentGame().takeFromSupply(DomCardName.Estate));
        }
    }

    public boolean addBoard(String contents, String bane, String aMountainPassBid, String anObeliskChoice) {
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

    public void addCoinTokens(int i) {
        if (possessor != null) {
            possessor.addCoinTokens(i);
            return;
        }
        coinTokens += i;
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " adds " + i + " coin tokens.");
    }

    public int getCoinTokens() {
        return coinTokens;
    }

    public void spendCoinTokens(int theCoinTokens) {
        coinTokens -= theCoinTokens;
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " spends " + theCoinTokens + " coin tokens.");
    }

    public void setAvailableCoins(int availableCoins) {
        this.availableCoins = availableCoins;
    }

    public void setCoinTokens(int coinTokens) {
        this.coinTokens = coinTokens;
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
        ArrayList<DomCard> theCards = new ArrayList<DomCard>();
        for (DomCard theCard : cardsInPlay) {
            if (theCard.hasCardType(cardType))
                theCount++;
        }
        return theCount;
    }

    public void setAlmsActivated() {
        almsActivated = true;
    }

    public void setExpeditionActivated() {
        expeditionActivated = true;
    }

    public DomCardName getMinus$2TokenOn() {
        return minus$2TokenOn;
    }

    public void spend(int theAmount) {
        int theRest = availableCoins - theAmount;
        if (theRest < 0) {
            availableCoins = 0;
            spendCoinTokens(-theRest);
        }
        availableCoins -= theAmount;
    }

    public void placeEstateToken() {
        if (estateTokenOn != null)
            return;
        DomCardName theCard = getChosenCardForFunction(DomBotFunction.isEstateTokenPlaced);
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " places Estate token on " + theCard.toHTML());
        estateTokenOn = getCurrentGame().takeFromSupply(theCard);
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

    public boolean isPlusOneCoinTokenSet() {
        return plusOneCoinTokenOn != null;
    }

    public void placePlusOneCoinToken() {
        plusOneCoinTokenOn = getChosenCardForFunction(DomBotFunction.isPlusOneCoinTokenSet);
        if (plusOneCoinTokenOn != null)
            if (DomEngine.haveToLog) DomEngine.addToLog(this + " puts +$1 token on " + plusOneCoinTokenOn.toHTML());
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

    public void addCardToHand(DomCard card) {
        if (DomEngine.haveToLog) DomEngine.addToLog(this + " adds " + card + " to hand");
        cardsInHand.add(card);
    }

    public int countInPlay(DomCardName cardName) {
        int theCount = 0;
        ArrayList<DomCard> theCards = new ArrayList<DomCard>();
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
        if (DomEngine.haveToLog)
            DomEngine.addToLog(name + " has $" + debt + " in debt and $" + getAvailableCoins() + " to pay off the debt");
        while (debt > 0 && (availableCoins > 0 || coinTokens > 0)) {
            if (availableCoins > 0)
                availableCoins--;
            else
                coinTokens--;
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

    public int getMountainPassBid() {
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
        DomCard theCardToReturn = null;
        ArrayList<DomCard> theCardsInHand = getCardsInHand();
        for (int i=theCardsInHand.size()-1;i>=0;i--){
            if (theCardsInHand.get(i).hasCardType(DomCardType.Action))
                continue;
            theCardToReturn = theCardsInHand.get(i);
            if (!removingReducesBuyingPower(theCardToReturn)) {
                break;
            }
        }
        if (theCardsInHand.get(0).hasCardType(DomCardType.Action))
            theCardToReturn=theCardsInHand.get(0);
        putOnTopOfDeck(removeCardFromHand(theCardToReturn));
    }

    public double getDrawDeckSize() {
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
}