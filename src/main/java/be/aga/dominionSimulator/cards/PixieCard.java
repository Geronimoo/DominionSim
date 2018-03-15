package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.Collections;

public class PixieCard extends DomCard {
    public PixieCard() {
      super( DomCardName.Pixie);
    }

    public void play() {
        owner.addActions(1);
        owner.drawCards(1);
        DomCard theBoon = owner.takeBoon();
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman(theBoon);
            return;
        }
        if (theBoon.getName() == DomCardName.The_Flame$s_Gift) {
            if (owner.getCardsInHand().size() < 1)
                return;
            if (owner.getCardsInHand().size() == 1) {
                if (owner.getCardsInHand().get(0).getTrashPriority() <= DomCardName.Copper.getTrashPriority())
                    owner.receiveBoon(theBoon);
                owner.trash(owner.removeCardFromPlay(this));
                return;
            }
            Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
            if (owner.getCardsInHand().get(0).getTrashPriority() <= DomCardName.Copper.getTrashPriority()
                    && owner.getCardsInHand().get(1).getTrashPriority() <= DomCardName.Copper.getTrashPriority()) {
                owner.receiveBoon(theBoon);
                owner.receiveBoon(theBoon);
                owner.trash(owner.removeCardFromPlay(this));
            }
        } else {
            owner.receiveBoon(theBoon);
            owner.receiveBoon(theBoon);
            owner.trash(owner.removeCardFromPlay(this));
        }
    }

    private void handleHuman(DomCard aBoon) {
        if (owner.getEngine().getGameFrame().askPlayer("<html>Receive " + aBoon +" twice ?</html>", "Resolving " + this.getName().toString())) {
            owner.receiveBoon(aBoon);
            owner.receiveBoon(aBoon);
            owner.trash(owner.removeCardFromPlay(this));
        }
    }
}