package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class HeroCard extends DomCard {
    public HeroCard() {
      super( DomCardName.Hero);
    }

    public void play() {
      owner.addAvailableCoins(2);
      DomCardName theDesiredCard = owner.getDesiredCard(DomCardType.Treasure, new DomCost(1000, 0), false, false, null);
      if (theDesiredCard==null)
          theDesiredCard=owner.getCurrentGame().getBestCardInSupplyFor(owner, DomCardType.Treasure, new DomCost(1000,0));
      owner.gain(theDesiredCard);
    }

    @Override
    public void handleCleanUpPhase() {
        if (owner==null)
            return;
        if (owner.wants(DomCardName.Champion)) {
            DomPlayer theOwner = owner;
            owner.returnToSupply(this);
            theOwner.gain(DomCardName.Champion);
            return;
        }
        super.handleCleanUpPhase();
    }
}