package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class City_QuarterCard extends DomCard {
    public City_QuarterCard() {
      super( DomCardName.City_Quarter);
    }

    public void play() {
      owner.addActions(2);
      int theNumberOfActions = owner.getCardsFromHand(DomCardType.Action).size();
      if (theNumberOfActions>0)
        owner.drawCards(theNumberOfActions);
    }
}