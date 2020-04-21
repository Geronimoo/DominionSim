package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class SuppliesCard extends DomCard {
    public SuppliesCard() {
      super( DomCardName.Supplies);
    }

    public void play() {
        owner.addAvailableCoins(1);
        DomCard horse = owner.getCurrentGame().takeFromSupply(DomCardName.Horse);
        if (horse==null)
            return;
        owner.gainOnTopOfDeck(horse);
    }
}