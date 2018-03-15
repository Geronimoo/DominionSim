package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Cursed_VillageCard extends DrawUntilXCardsCard {
    public Cursed_VillageCard() {
      super( DomCardName.Cursed_Village);
    }

    public void play() {
        owner.addActions(2);
        if (owner.getCardsInHand().size()<6)
          owner.drawCards(6 - owner.getCardsInHand().size());
    }

    @Override
    public void doWhenGained() {
        owner.receiveHex(null);
    }
}