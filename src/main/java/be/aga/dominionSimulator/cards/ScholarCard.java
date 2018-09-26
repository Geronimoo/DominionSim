package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class ScholarCard extends DomCard {
    public ScholarCard() {
      super( DomCardName.Scholar);
    }

    @Override
    public void play() {
      if (!owner.getCardsInHand().isEmpty())
        owner.discardHand();
      owner.drawCards( 7 );
    }

    @Override
    public boolean wantsToBePlayed() {
      return !owner.isGoingToBuyTopCardInBuyRules(owner.getTotalPotentialCurrency());
    }
}