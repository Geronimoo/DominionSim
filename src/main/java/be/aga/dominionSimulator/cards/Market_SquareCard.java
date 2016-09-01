package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Market_SquareCard extends DomCard {
    public Market_SquareCard() {
      super( DomCardName.Market_Square);
    }

    public void play() {
        owner.addActions(1);
        owner.drawCards(1);
        owner.addAvailableBuys(1);
    }

    @Override
    public int getPlayPriority() {
        if (!owner.getCardsFromHand(DomCardName.Apprentice).isEmpty())
            return 40;
        if (!owner.getCardsFromHand(DomCardType.Trasher).isEmpty() && owner.countInDeck(DomCardName.Gold)<3)
            return 40;
        if (!owner.getCardsFromHand(DomCardName.Moneylender).isEmpty() && !owner.getCardsFromHand(DomCardName.Copper).isEmpty() && owner.countInDeck(DomCardName.Gold)<3)
            return 40;
        if (!owner.getCardsFromHand(DomCardType.Trasher).isEmpty() && !owner.getCardsFromHand(DomCardName.Madman).isEmpty())
            return 40;
        return super.getPlayPriority();
    }
}