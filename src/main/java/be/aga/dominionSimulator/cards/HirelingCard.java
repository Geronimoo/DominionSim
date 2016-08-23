package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class HirelingCard extends DomCard {
    public HirelingCard() {
      super( DomCardName.Hireling);
    }

    public void resolveDuration() {
      owner.drawCards(1);
    }

    @Override
    public boolean mustStayInPlay() {
        return true;
    }
}