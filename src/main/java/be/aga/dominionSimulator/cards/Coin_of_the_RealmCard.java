package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class Coin_of_the_RealmCard extends DomCard {
    public Coin_of_the_RealmCard() {
      super( DomCardName.Coin_of_the_Realm);
    }

    public void play() {
      owner.addAvailableCoins(1);
      if (owner.getCardsFromPlay(getName()).contains(this))
         owner.putOnTavernMat(owner.removeCardFromPlay(this));
    }

    @Override
    public void doWhenCalled() {
        owner.addActions(2);
    }
}