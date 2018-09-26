package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class DucatCard extends DomCard {
    public DucatCard() {
      super( DomCardName.Ducat);
    }

    public void play() {
      owner.addCoffers(1);
      owner.addAvailableBuys(1);
    }

    @Override
    public void doWhenGained() {
        if (owner.getCardsFromHand(DomCardName.Copper).isEmpty())
            return;
        if (owner.isHumanOrPossessedByHuman()) {
            if (owner.getEngine().getGameFrame().askPlayer("<html>Trash " + DomCardName.Copper.toHTML() + "</html>", "Resolving " + this.getName().toString()))
                owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(DomCardName.Copper).get(0)));
        } else {
            owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(DomCardName.Copper).get(0)));
        }
    }
}