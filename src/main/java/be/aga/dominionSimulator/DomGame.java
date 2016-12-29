package be.aga.dominionSimulator;

import java.util.ArrayList;

import be.aga.dominionSimulator.cards.*;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPhase;

public class DomGame {
    private static final Logger LOGGER = Logger.getLogger( DomGame.class );
    static {
        LOGGER.setLevel( DomEngine.LEVEL );
        LOGGER.removeAllAppenders();
        if (DomEngine.addAppender)
            LOGGER.addAppender(new ConsoleAppender(new SimpleLayout()) );
    }

  ArrayList< DomPlayer > players = new ArrayList< DomPlayer >();
  DomBoard board;
  public long checkGameFinishTime=0;
  public long playerTurnTime=0;
  private DomPlayer activePlayer;
  public boolean emptyPilesEnding=false;
  private boolean extraTurnsTakenByActivePlayer;
  private boolean isNoProvinceGainedYet = true;
  private boolean auctionTriggered;


    /**
   * @param aPlayers
 */
  public DomGame ( DomBoard aBoard, ArrayList< DomPlayer > aPlayers, ArrayList< DomCardName > aCardNames) {
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
public void run() {
    long theTime = System.currentTimeMillis();
    int turn = 0;
    do {
        turn++;
        DomEngine.logPlayerIndentation = 0;
        for (int i=0;i<players.size()&&!isGameFinished();i++) {
          extraTurnsTakenByActivePlayer=false;
          activePlayer = players.get(i);
          theTime = System.currentTimeMillis();
          //first take all possessed turns
      	  while (!activePlayer.getPossessionTurns().isEmpty() && !isGameFinished()) {
     	    activePlayer.setPossessor(activePlayer.getPossessionTurns().remove(0));
            activePlayer.takeTurn();
            extraTurnsTakenByActivePlayer=true;
      	  }
      	  activePlayer.setPossessor(null);
      	  //take normal turn
      	  if (!isGameFinished()) {
            activePlayer.takeTurn();
          }
      	  //take Outpost turn
          if (activePlayer.hasExtraOutpostTurn() && !isGameFinished()){
        	activePlayer.takeTurn();
            extraTurnsTakenByActivePlayer=true;
          }
          if (activePlayer.hasExtraMissionTurn() && !isGameFinished() && !extraTurnsTakenByActivePlayer) {
              activePlayer.takeTurn();
              activePlayer.setExtraMissionTurn(false);
              extraTurnsTakenByActivePlayer=true;
          }
          playerTurnTime += System.currentTimeMillis()-theTime;
          DomEngine.logPlayerIndentation++;
        }
    } while (!isGameFinished() );
//    if (turn<=10)
//        LOGGER.info("Weinig beurten: " + turn);
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
    for (DomPlayer thePlayer : players) {
      if (thePlayer.countVictoryPoints()>=theMaxPoints && thePlayer.getTurns()<=theMinTurns) {
        if (winners > 1) {
          if (DomEngine.haveToLog) DomEngine.addToStartOfLog( thePlayer + " ties for the win !!" );
//          if (players.get(0).pprUsed)
          thePlayer.addTie(1.0/winners);
        } else {
          if (DomEngine.haveToLog) DomEngine.addToStartOfLog( thePlayer + " wins this game!!");
//          if (players.get(0).pprUsed)
          thePlayer.addWin();
        }
      } 
    }
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
boolean isGameFinished() {
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
      if (DomEngine.haveToLog) {
        DomEngine.addToLog("");
        DomEngine.addToLog("Three piles depleted!");
      }
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
	return activePlayer.getBridgesPlayedCount();
}

public int getPrincessesInPlay() {
    return activePlayer.getCardsFromPlay(DomCardName.Princess).size();
}

public int getQuarriesPlayed() {
    return activePlayer.getCardsFromPlay(DomCardName.Quarry).size();
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
	return activePlayer.getCardsFromPlay(DomCardName.Highway).size();
}

public DomPlayer getActivePlayer() {
	return activePlayer;
}

public boolean isInKingDom(DomCardName aCard) {
        return getBoard().containsKey(aCard);
    }

public int getBridge_TrollsInPlay() { return activePlayer.getCardsFromPlay(DomCardName.Bridge_Troll).size();    }

    public ArrayList<DomCard> getRogueableCardsInTrash() {
        ArrayList<DomCard> theRogueableCards = new ArrayList<DomCard>();
        for (DomCard theCard : getTrashedCards()) {
            if (theCard.getCoinCost(this)>=3 && theCard.getCoinCost(this)<=6 && theCard.getPotionCost()==0)
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
}