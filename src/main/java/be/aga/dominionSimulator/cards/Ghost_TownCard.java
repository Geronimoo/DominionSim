package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Ghost_TownCard extends DomCard {
    public Ghost_TownCard() {
      super( DomCardName.Ghost_Town);
    }

    public void resolveDuration() {
        owner.addActions(1);
        owner.drawCards(1);
    }

    @Override
    public void doWhenGained() {
        owner.addCardToHand(this);
    }
}