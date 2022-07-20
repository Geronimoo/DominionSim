package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class MagnateCard extends DomCard {
    public MagnateCard() {
      super( DomCardName.Magnate);
    }

    @Override
    public void play() {
      owner.drawCards( owner.getCardsFromHand(DomCardType.Treasure).size() );
    }
}