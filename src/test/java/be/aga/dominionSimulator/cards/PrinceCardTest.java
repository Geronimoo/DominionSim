package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomBoard;
import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomGame;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import org.easymock.EasyMock;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PrinceCardTest {

    @Test
    public void testGetPlayPriority() {
        ArrayList<DomCard> cardsInHand = new ArrayList<>();
        DomCard goons = new GoonsCard();
        cardsInHand.add(goons);
        cardsInHand.add(new SilverCard());
        DomCard prince = new PrinceCard();
        cardsInHand.add(prince);
        cardsInHand.add(new CopperCard());
        cardsInHand.add(new SilverCard());
        DomPlayer owner = new DomPlayer("Test player");
        goons.owner = owner;
        prince.owner = owner;
        owner.addCardsToHand(cardsInHand);
        DomGame currentGame = EasyMock.createMock(DomGame.class);
        owner.setCurrentGame(currentGame);
        owner.placeMinus$2Token(DomCardName.Goons);
        mockBasicGameItems(owner, currentGame);
        EasyMock.replay(currentGame);
        assertEquals(4, goons.getCost(currentGame).getCoins());
        assertEquals(true, prince.wantsToBePlayed());
        assertTrue(goons.getPlayPriority() > prince.getPlayPriority());
        assertEquals(prince, owner.getNextActionToPlay());
        owner.playThis(prince);
        owner.removeCardFromHand(prince);
        assertEquals(3, owner.getCardsInHand().size());
    }

    private void mockBasicGameItems(DomPlayer owner, DomGame currentGame) {
        EasyMock.expect(currentGame.getInventorsPlayed()).andReturn(0).anyTimes();
        EasyMock.expect(currentGame.getBridgesPlayed()).andReturn(0).anyTimes();
        EasyMock.expect(currentGame.getPrincessesInPlay()).andReturn(0).anyTimes();
        EasyMock.expect(currentGame.getQuarriesInPlay()).andReturn(0).anyTimes();
        EasyMock.expect(currentGame.getHighwaysInPlay()).andReturn(0).anyTimes();
        EasyMock.expect(currentGame.getBridgetrollPlayed()).andReturn(0).anyTimes();
        EasyMock.expect(currentGame.getActivePlayer()).andReturn(owner).anyTimes();
        EasyMock.expect(currentGame.getBoard()).andReturn(new DomBoard(DomCardName.class, new ArrayList<DomPlayer>())).anyTimes();
    }
}