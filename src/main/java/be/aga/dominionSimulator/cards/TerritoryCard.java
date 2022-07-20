package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class TerritoryCard extends DomCard {
    public TerritoryCard() {
      super( DomCardName.Territory);
    }

    @Override
    public void doWhenGained() {
        for (int i=0; i<owner.getCurrentGame().countEmptyPiles();i++) {
            owner.gain(DomCardName.Gold);
        }
    }
}