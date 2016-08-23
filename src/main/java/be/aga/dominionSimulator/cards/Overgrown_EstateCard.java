package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Overgrown_EstateCard extends DomCard {
    public Overgrown_EstateCard() {
      super( DomCardName.Overgrown_Estate);
    }

    @Override
    public void doWhenTrashed() {
        owner.drawCards(1);
    }

    @Override
    public int getTrashPriority() {
      return 1;
    }
}