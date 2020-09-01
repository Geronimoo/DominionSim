package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class HostelryCard extends DomCard {
    public HostelryCard() {
      super( DomCardName.Hostelry);
    }

    public void play() {
      owner.addActions(2);
      owner.drawCards(1);
    }

    @Override
    public void doWhenGained() {
        if (owner.getCardsFromHand(DomCardType.Treasure).isEmpty())
            return;
        if (owner.isHumanOrPossessedByHuman()) {
            handleHumanPlayer();
        }else {
            Collections.sort(owner.getCardsInHand(),SORT_FOR_DISCARDING);
            while (!owner.getCardsFromHand(DomCardType.Treasure).isEmpty()) {
                DomCard theTreasure = owner.getCardsFromHand(DomCardType.Treasure).get(0);
                if (!owner.removingReducesBuyingPower(theTreasure)) {
                    owner.discardFromHand(theTreasure);
                    owner.gain(DomCardName.Horse);
                } else {
                    break;
                }
            }
        }
    }

    private void handleHumanPlayer() {
        if (!owner.getEngine().getGameFrame().askPlayer("<html>Discard Treasures?</html>", "Resolving " + this.getName().toString()))
            return;
        owner.setNeedsToUpdateGUI();
        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
        owner.getEngine().getGameFrame().askToSelectCards("<html>Discard Treasures?</html>", owner.getCardsFromHand(DomCardType.Treasure), theChosenCards,0);
        for (DomCard theCardName: theChosenCards) {
            owner.discard(owner.getCardsFromHand(theCardName.getName()).get(0), false);
            owner.gain(DomCardName.Horse);
        }

    }
}