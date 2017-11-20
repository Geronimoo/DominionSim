package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.Collections;

public class Blessed_VillageCard extends DomCard {
    public Blessed_VillageCard() {
      super( DomCardName.Blessed_Village);
    }

    public void play() {
      owner.addActions(2);
      owner.drawCards(1);
    }

    @Override
    public void doWhenGained() {
        DomCard theBoon = owner.takeBoon();
        if (owner.isHumanOrPossessedByHuman()) {
            if (owner.getEngine().getGameFrame().askPlayer("<html>Receive " + theBoon + " now?</html>", "Resolving " + this.getName().toString())) {
                owner.receiveBoon(theBoon);
                return;
            } else {
                owner.addDelayedBoon(theBoon);
                return;
            }
        }
        if (theBoon.getName() == DomCardName.The_Flame$s_Gift) {
            Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
            if (!owner.getCardsInHand().isEmpty() && owner.getCardsInHand().get(0).getTrashPriority() <= DomCardName.Copper.getTrashPriority()) {
                owner.receiveBoon(theBoon);
                return;
            }
        }
        if (theBoon.getName() == DomCardName.The_Forest$s_Gift) {
            if (owner.getBuysLeft() == 1 && owner.getDesiredCard(owner.getTotalAvailableCurrency().add(new DomCost(1, 0)), false) != null) {
                owner.receiveBoon(theBoon);
                return;
            }
        }
        if (theBoon.getName() == DomCardName.The_Mountain$s_Gift
                || theBoon.getName() == DomCardName.The_River$s_Gift
                || theBoon.getName() == DomCardName.The_Sun$s_Gift
                || theBoon.getName() == DomCardName.The_Swamp$s_Gift) {
            owner.receiveBoon(theBoon);
            return;
        }
        if (theBoon.getName() == DomCardName.The_Sky$s_Gift && owner.getCardsInHand().size() >= 3) {
            owner.receiveBoon(theBoon);
            return;
        }
        owner.addDelayedBoon(theBoon);
    }
}