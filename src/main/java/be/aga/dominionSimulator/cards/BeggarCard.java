package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class BeggarCard extends DomCard {
    public BeggarCard() {
      super( DomCardName.Beggar);
    }

    public void play() {
        owner.gainInHand(DomCardName.Copper);
        owner.gainInHand(DomCardName.Copper);
        owner.gainInHand(DomCardName.Copper);
    }

    @Override
    public boolean wantsToBePlayed() {
        return owner.wants(DomCardName.Copper);
    }
}