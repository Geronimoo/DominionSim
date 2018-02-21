package be.aga.dominionSimulator;

import java.util.ArrayList;
import java.util.Observable;

import be.aga.dominionSimulator.cards.*;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPhase;

public class DomGame extends Observable{
    private static final Logger LOGGER = Logger.getLogger( DomGame.class );
    static {
        LOGGER.setLevel( DomEngine.LEVEL );
        LOGGER.removeAllAppenders();
        if (DomEngine.addAppender)
            LOGGER.addAppender(new ConsoleAppender(new SimpleLayout()) );
    }

    private final DomEngine myEngine;

  ArrayList< DomPlayer > players = new ArrayList< DomPlayer >();
  DomBoard board;
  public long checkGameFinishTime=0;
  public long playerTurnTime=0;
  private DomPlayer activePlayer;
  public boolean emptyPilesEnding=false;
  private boolean isNoProvinceGainedYet = true;
  private boolean auctionTriggered;
  private DomPlayer previousTurnTakenBy;
  private DomCardName obeliskChoice;
    private ArrayList<DomCard> faceDownCardsInTrash = new ArrayList<DomCard>();


    /**
     * @param aPlayers
     * @param anEngine
     */
  public DomGame(DomBoard aBoard, ArrayList<DomPlayer> aPlayers, DomEngine anEngine) {
     myEngine = anEngine;
     players.addAll( aPlayers );
     if (aBoard==null){
       board = new DomBoard(DomCardName.class, players);
       board.initialize();
     }else{
       board=aBoard;
     }
     initialize();
  }

/**
 * @return 
 * 
 */
private void initialize() {
    for (DomPlayer thePlayer : players) {
        thePlayer.initializeForGame(this);
    }
    //adding the starting estates adds tokens to the trade route mat which we don't want
    getBoard().clearTradeRouteMat();
    isNoProvinceGainedYet=true;
    auctionTriggered=false;
    setObeliskChoice();
}

    private void setObeliskChoice() {
        for (DomPlayer thePlayer : players) {
            if (thePlayer.getObeliskChoice()!=null && thePlayer.getObeliskChoice().length()>0) {
                obeliskChoice=thePlayer.getCardForObelisk();
                break;
            }
        }
    }

public DomPlayer getPreviousTurnTakenBy() {
    return previousTurnTakenBy;
}

    /**
 * @return
 */
public ArrayList< DomPlayer > getPlayers() {
  return players;
}

/**
 * @return
 */
public DomCard takeFromSupply( DomCardName aCardName ) {
  return board.take(aCardName);
}

/**
 * @return 
 * 
 */
public void runSimulation() {
    setPreviousTurnTakenBy(null);
    long theTime = System.currentTimeMillis();
    do {
        DomEngine.logPlayerIndentation = 0;
        for (int i=0;i<players.size()&&!isGameFinished();i++) {
          activePlayer = players.get(i);
          theTime = System.currentTimeMillis();
          //first take all possessed turns
      	  while (!activePlayer.getPossessionTurns().isEmpty() && !isGameFinished()) {
     	    activePlayer.setPossessor(activePlayer.removePossessorTurn());
            activePlayer.takeTurn();
      	  }
      	  activePlayer.setPossessor(null);
      	  //take normal turn
      	  if (!isGameFinished()) {
            activePlayer.takeTurn();
          }
      	  //take Outpost turn
          if (activePlayer.hasExtraOutpostTurn() && !isGameFinished()){
        	activePlayer.takeTurn();
          }
          if (activePlayer.hasExtraMissionTurn() && !isGameFinished() ) {
            activePlayer.takeTurn();
          }
          playerTurnTime += System.currentTimeMillis()-theTime;
          DomEngine.logPlayerIndentation++;
          previousTurnTakenBy = activePlayer;
        }
    } while (!isGameFinished() );
    DomEngine.logPlayerIndentation = 0;
}

public void determineWinners() {
    int theMaxPoints = -1000;
    int theMinTurns = 10000;
    int winners = 0;

    for (DomPlayer thePlayer : players) {
      thePlayer.handleGameEnd();
    }
    for (DomPlayer thePlayer : players) {
      if (DomEngine.haveToLog) DomEngine.addToStartOfLog( "");
      thePlayer.showDeck();
      if (DomEngine.haveToLog)
    	  DomEngine.addToStartOfLog( "<B>"+thePlayer + "</B> has " +  thePlayer.countVictoryPoints() + " points "
    			  +(thePlayer.getVictoryTokens()>0 ? (" ("+thePlayer.getVictoryTokens()+"&#x25BC;) ") : "")
                  + getLandMarkText(thePlayer)
                  + getMiserableText(thePlayer)
                  +"and took " + thePlayer.getTurns() + " turns");
      theMaxPoints = thePlayer.countVictoryPoints()>theMaxPoints ? thePlayer.countVictoryPoints() : theMaxPoints;
    }
    for (DomPlayer thePlayer : players) {
      if (thePlayer.countVictoryPoints()>=theMaxPoints) {
        theMinTurns = thePlayer.getTurns()<theMinTurns ? thePlayer.getTurns() : theMinTurns;
      }
    }
    for (DomPlayer thePlayer : players) {
      if (thePlayer.countVictoryPoints()>=theMaxPoints && thePlayer.getTurns()<=theMinTurns) {
        winners++;
      }
    }
    if (DomEngine.haveToLog) DomEngine.addToStartOfLog( "");
    if (!isGameFinished()) {
        if (DomEngine.haveToLog) DomEngine.addToStartOfLog(getHumanPlayer() + " resigned!!");
    } else {
        for (DomPlayer thePlayer : players) {
            if (thePlayer.countVictoryPoints() >= theMaxPoints && thePlayer.getTurns() <= theMinTurns) {
                if (winners > 1) {
                    if (DomEngine.haveToLog) DomEngine.addToStartOfLog(thePlayer + " ties for the win !!");
//          if (players.get(0).pprUsed)
                    thePlayer.addTie(1.0 / winners);
                } else {
                    if (DomEngine.haveToLog) DomEngine.addToStartOfLog(thePlayer + " wins this game!!");
//          if (players.get(0).pprUsed)
                    thePlayer.addWin();
                }
            }
        }
    }
}

    private String getMiserableText(DomPlayer thePlayer) {
        StringBuilder theBuilder = new StringBuilder();
        if (thePlayer.isMiserable()) {
            theBuilder.append("(Miserable:-2").append("&#x25BC;) ");
        }
        if (thePlayer.isTwiceMiserable()) {
            theBuilder.append("(Twice Miserable:-4").append("&#x25BC;) ");
        }
        return theBuilder.toString();
    }

    private String getLandMarkText(DomPlayer thePlayer) {
        StringBuilder theBuilder = new StringBuilder();
        if (getBoard().isLandmarkActive(DomCardName.Fountain)) {
            theBuilder.append("(").append(DomCardName.Fountain.toHTML()).append(":").append(FountainCard.countVP(thePlayer)).append("&#x25BC;) ");
        }
        if (getBoard().isLandmarkActive(DomCardName.Wolf_Den)) {
            theBuilder.append("(").append(DomCardName.Wolf_Den.toHTML()).append(":").append(Wolf_DenCard.countVP(thePlayer)).append("&#x25BC;) ");
        }
        if (getBoard().isLandmarkActive(DomCardName.Keep)) {
            theBuilder.append("(").append(DomCardName.Keep.toHTML()).append(":").append(KeepCard.countVP(thePlayer)).append("&#x25BC;) ");
        }
        if (getBoard().isLandmarkActive(DomCardName.Museum)) {
            theBuilder.append("(").append(DomCardName.Museum.toHTML()).append(":").append(MuseumCard.countVP(thePlayer)).append("&#x25BC;) ");
        }
        if (getBoard().isLandmarkActive(DomCardName.Obelisk)) {
            theBuilder.append("(").append(DomCardName.Obelisk.toHTML()).append(":").append(ObeliskCard.countVP(thePlayer)).append("&#x25BC;) ");
        }
        if (getBoard().isLandmarkActive(DomCardName.Orchard)) {
            theBuilder.append("(").append(DomCardName.Orchard.toHTML()).append(":").append(OrchardCard.countVP(thePlayer)).append("&#x25BC;) ");
        }
        if (getBoard().isLandmarkActive(DomCardName.Palace)) {
            theBuilder.append("(").append(DomCardName.Palace.toHTML()).append(":").append(PalaceCard.countVP(thePlayer)).append("&#x25BC;) ");
        }
        if (getBoard().isLandmarkActive(DomCardName.Tower)) {
            theBuilder.append("(").append(DomCardName.Tower.toHTML()).append(":").append(TowerCard.countVP(thePlayer)).append("&#x25BC;) ");
        }
        if (getBoard().isLandmarkActive(DomCardName.Triumphal_Arch)) {
            theBuilder.append("(").append(DomCardName.Triumphal_Arch.toHTML()).append(":").append(Triumphal_ArchCard.countVP(thePlayer)).append("&#x25BC;) ");
        }
        if (getBoard().isLandmarkActive(DomCardName.Wall)) {
            theBuilder.append("(").append(DomCardName.Wall.toHTML()).append(":").append(WallCard.countVP(thePlayer)).append("&#x25BC;) ");
        }
        if (getBoard().isLandmarkActive(DomCardName.Bandit_Fort)) {
            theBuilder.append("(").append(DomCardName.Bandit_Fort.toHTML()).append(":").append(Bandit_FortCard.countVP(thePlayer)).append("&#x25BC;) ");
        }

        return theBuilder.toString();
    }

    /**
 * @return
 */
    public boolean isGameFinished() {
    long theTime = System.currentTimeMillis();
    if (players.get( 0 ).getTurns()>60){
      LOGGER.debug( "Too many turns!!! Game ended!" );
      checkGameFinishTime+=System.currentTimeMillis()-theTime;
      return true;
    }
    
    boolean isGameFinished;
	if (players.size()==1) {
    	isGameFinished = board.count( DomCardName.Province)<=4 
            || (board.get(DomCardName.Colony)!=null && board.count( DomCardName.Colony)<=4 );
        checkGameFinishTime+=System.currentTimeMillis()-theTime;
    } else {
    	isGameFinished = board.count( DomCardName.Province)==0 
          || (board.get(DomCardName.Colony)!=null && board.count( DomCardName.Colony)==0 );
        checkGameFinishTime+=System.currentTimeMillis()-theTime;
    }

    if (isGameFinished) {
      return true;
    }

    if (board.countEmptyPiles() >= 3) {
      checkGameFinishTime+=System.currentTimeMillis()-theTime;
      emptyPilesEnding=true;
      return true;
    }

    return false;
}

/**
 * @return
 */
public int countInSupply( DomCardName aCardName ) {
    return board.count( aCardName );
}

/**
 * @param aRemove
 */
public void addToTrash( DomCard aRemove ) {
  board.addToTrash(aRemove);
}

/**
 * @param aCard
 */
public void returnToSupply( DomCard aCard ) {
   board.add( aCard);
}

/**
 * @return
 */
public int countEmptyPiles() {
  return board.countEmptyPiles();
}

/**
 * @return
 */
public ArrayList<String> getEmptyPiles() {
    return board.getEmptyPiles();
}

/**
 * @return
 */
public ArrayList< DomCard > getTrashedCards() {
    return board.getTrashedCards();
}

public DomBoard getBoard() {
  return board;
}

/**
 * @param
 * @return
 */
public DomCard removeFromTrash( DomCard aCard) {
  return board.removeFromTrash(aCard);
}

public ArrayList<DomCard> revealFromBlackMarketDeck() {
	return board.revealFromBlackMarketDeck();
}

public void returnToBlackMarketDeck(DomCard theCard) {
	board.returnToBlackMarketDeck(theCard);
}

public int getEmbargoTokensOn(DomCardName aCard) {
	return board.getEmbargoTokensOn(aCard);
}

public void putEmbargoTokenOn(DomCardName aCard) {
  board.putEmbargoTokenOn(aCard);
}

public DomCardName getBestCardInSupplyFor(DomPlayer aPlayer, DomCardType aType, DomCost domCost, boolean anExactCost) {
	return board.getBestCardInSupplyFor(aPlayer, aType, domCost, anExactCost, null);
}

public DomCardName getBestCardInSupplyFor(DomPlayer aPlayer, DomCardType aType, DomCost domCost) {
	return board.getBestCardInSupplyFor(aPlayer, aType, domCost, false, null);
}

public DomCardName getCardForSwindler(DomPlayer aPlayer, DomCost domCost) {
	return board.getCardForSwindler( aPlayer, domCost);
}

public double countCardsInSmallestPile() {
	return board.countCardsInSmallestPile();
}

public boolean isBuyPhase() {
  return activePlayer.getPhase()==DomPhase.Buy;
}

public int getBridgesPlayed() {
	return activePlayer!=null?activePlayer.getBridgesPlayedCount():0;
}

public int getPrincessesInPlay() {
    return activePlayer!=null?activePlayer.getCardsFromPlay(DomCardName.Princess).size():0;
}

public int getQuarriesPlayed() {
    return activePlayer!=null?activePlayer.getCardsFromPlay(DomCardName.Quarry).size():0;
}

  public int countActionsInPlay() {
    int theCount = 0;
	for (DomCard theCard : activePlayer.getCardsInPlay()) {
	  theCount+=theCard.hasCardType( DomCardType.Action ) ? 1 : 0;
	}
    return theCount;
  }

public int getGainsNeededToEndGame() {
	return getBoard().getGainsNeededToEndGame();
}

public DomCardName getBestCardInSupplyNotOfType(DomPlayer aPlayer,
		DomCardType aType, DomCost domCost) {
	return board.getBestCardInSupplyFor(aPlayer, null, domCost, false, aType);
}

public int getHighwaysInPlay() {
	return activePlayer!=null?activePlayer.getCardsFromPlay(DomCardName.Highway).size():0;
}

public DomPlayer getActivePlayer() {
	return activePlayer;
}

public boolean isInKingDom(DomCardName aCard) {
        return getBoard().containsKey(aCard);
    }

public int getBridge_TrollsInPlay() { return activePlayer!=null?activePlayer.getCardsFromPlay(DomCardName.Bridge_Troll).size():0;    }

    public ArrayList<DomCard> getRogueableCardsInTrash() {
        ArrayList<DomCard> theRogueableCards = new ArrayList<DomCard>();
        for (DomCard theCard : getTrashedCards()) {
            if (theCard.getCoinCost(this)>=3 && theCard.getCoinCost(this)<=6 && theCard.getPotionCost()==0 && theCard.getCost(this).getDebt()==0)
                theRogueableCards.add(theCard);
        }
        return theRogueableCards;
    }

    public boolean isSwampHagActive() {
        if (!isInKingDom(DomCardName.Swamp_Hag))
            return false;
        for (DomPlayer theOpp : activePlayer.getOpponents()) {
            if (!theOpp.getCardsFromPlay(DomCardName.Swamp_Hag).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public int getTaxOn(DomCardName name) {
        return board.getTaxOn(name);
    }

    public boolean checkForMountainPass() {
        if (isNoProvinceGainedYet && getBoard().isLandmarkActive(DomCardName.Mountain_Pass)) {
            isNoProvinceGainedYet=false;
            return true;
        }
        return false;
    }

    public void triggerAuction() {
        auctionTriggered = true;
    }

    public boolean isAuctionTriggered() {
        return auctionTriggered;
    }

    public void setAuctionTriggered(boolean auctionTriggered) {
        this.auctionTriggered = auctionTriggered;
    }

    public void startUpHumanGame() {
        addObserver(myEngine.getGameFrame());
        for (DomPlayer thePlayer:players) {
            thePlayer.addObserver(myEngine.getGameFrame());
        }
        DomEngine.addToLog("<BR><HR><B>Game Log</B><BR>");

        DomEngine.logPlayerIndentation = 0;
//        activePlayer = players.get(0).isHuman() ? players.get(1):players.get(0);
        activePlayer=players.get(0);
        while (!activePlayer.isHuman()) {
            activePlayer.takeTurn();
            activePlayer=activePlayer.getOpponents().get(0);
            DomEngine.logPlayerIndentation++;
        }
//        activePlayer.gainInHand(takeFromSupply(DomCardName.Bazaar));
//        activePlayer.gainInHand(takeFromSupply(DomCardName.Bazaar));
//        activePlayer.gainInHand(takeFromSupply(DomCardName.Possession));
//        activePlayer.gainInHand(takeFromSupply(DomCardName.Possession));
//        activePlayer.gainInHand(takeFromSupply(DomCardName.Possession));

        initHumanOrPossessedPlayer();
        setChanged();
        notifyObservers();
    }

    public DomEngine getEngine() {
        return myEngine;
    }

    public DomPlayer getHumanPlayer() {
        for (DomPlayer thePlayer : players) {
            if (thePlayer.isHuman())
                return thePlayer;
        }
        return null;
    }

    public void continueHumanGame() {
        if (!isGameFinished()) {
            setChanged();
            notifyObservers();
            if (activePlayer.isHuman() && !activePlayer.hasExtraOutpostTurn() && !activePlayer.hasExtraMissionTurn() ) {
                activePlayer = activePlayer.getOpponents().get(0);
                DomEngine.logPlayerIndentation++;
            }
            activePlayer.setPossessor(activePlayer.removePossessorTurn());
            while (!isGameFinished() && (!activePlayer.isHumanOrPossessedByHuman() || (activePlayer.isHuman() && activePlayer.getPossessor()!=null))) {
                if (activePlayer.equals(players.get(0))) {
                    DomEngine.logPlayerIndentation=0;
                }
                while (activePlayer.getPossessor()!=null && !isGameFinished()) {
                    activePlayer.takeTurn();
                    activePlayer.setPossessor(activePlayer.removePossessorTurn());
                }
                if (!activePlayer.isHuman()) {
                    activePlayer.takeTurn();
                    if (activePlayer.hasExtraOutpostTurn() && !isGameFinished() ){
                        activePlayer.takeTurn();
                    }
                    if (activePlayer.hasExtraMissionTurn() && !isGameFinished() ) {
                        activePlayer.takeTurn();
                    }
                    getEngine().getGameFrame().hover("<html>Opponent gained: " + activePlayer.getGainedCardsText() + "</html>");
                    activePlayer = activePlayer.getOpponents().get(0);
                    if (!activePlayer.getPossessionTurns().isEmpty()) {
                        activePlayer.setPossessor(activePlayer.removePossessorTurn());
                    }
                    DomEngine.logPlayerIndentation++;
                }
            }
            if (!isGameFinished()) {
                if (activePlayer.equals(players.get(0))) {
                    DomEngine.logPlayerIndentation = 0;
                }
                initHumanOrPossessedPlayer();
            } else {
                myEngine.doEndOfHumanGameStuff();
            }
        } else {
            myEngine.doEndOfHumanGameStuff();
        }
        setChanged();
        notifyObservers();
    }

    private void initHumanOrPossessedPlayer() {
        activePlayer.initializeTurn();
        activePlayer.resolveBeginningOfTurnForHuman();
        activePlayer.setPhase(DomPhase.Action);
        if (activePlayer.getCardsFromHand(DomCardType.Action).isEmpty())
            activePlayer.setPhase(DomPhase.Buy);
    }

    public DomCard removeFromTrash(DomCardName theChosenCard) {
        for (DomCard theCard:getTrashedCards()) {
            if (theCard.getName()==theChosenCard) {
                return removeFromTrash(theCard);
            }
        }
        return null;
    }

    public void setPreviousTurnTakenBy(DomPlayer aPlayer) {
        if (aPlayer==null)
            return;
        if (previousTurnTakenBy==aPlayer) {
            aPlayer.setExtraMissionTurn(false);
            aPlayer.setExtraOutpostTurn(false);
        }
        this.previousTurnTakenBy = aPlayer;
    }

    public DomCardName getObeliskChoice() {
        return obeliskChoice;
    }

    public ArrayList<DomCard> getFaceDownCardsInTrash() {
        return faceDownCardsInTrash;
    }

    public void resetFaceDownCards() {
        faceDownCardsInTrash.clear();
    }

    public void addFaceDownCard(DomCard theChosenCard) {
        faceDownCardsInTrash.add(theChosenCard);
    }
}