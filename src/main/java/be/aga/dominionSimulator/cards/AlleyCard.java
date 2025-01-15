package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class AlleyCard extends DomCard {
    public AlleyCard() {
      super( DomCardName.Alley);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
      owner.doForcedDiscard(1, false);
    }

    @Override
    public boolean wantsToBePlayed() {
        if (owner.getCardsFromHand(this.getName()).isEmpty() && !owner.getCardsFromHand(DomCardName.Crew).isEmpty() && owner.getDrawDeckSize()>5)
            return false;
        return super.wantsToBePlayed();
    }
}