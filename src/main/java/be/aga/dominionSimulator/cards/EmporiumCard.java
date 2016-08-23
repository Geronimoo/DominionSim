package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class EmporiumCard extends DomCard {
    public EmporiumCard() {
      super( DomCardName.Emporium);
    }

    public void play() {
      owner.addActions(1);
      owner.addAvailableCoins(1);
      owner.drawCards(1);
    }

    @Override
    public void doWhenGained() {
        if (owner.getCardsFromPlay(DomCardType.Action).isEmpty())
            return;
        if (owner.getCardsFromPlay(DomCardType.Action).size()>=5)
            owner.addVP(2);
    }
}