package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Den_of_SinCard extends DomCard {
    public Den_of_SinCard() {
      super( DomCardName.Den_of_Sin);
    }

    public void resolveDuration() {
        owner.drawCards(2);
    }

    @Override
    public void doWhenGained() {
        owner.addCardToHand(this);
    }
}