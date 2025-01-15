package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class SycophantCard extends DomCard {
    public SycophantCard() {
      super( DomCardName.Sycophant);
    }

    public void play() {
      owner.addActions(1);
      int cardsInHand = owner.getCardsInHand().size();
      owner.doForcedDiscard(3, false);
      if (owner.getCardsInHand().size()<cardsInHand)
          owner.addAvailableCoins(3);
    }

    @Override
    public void doWhenGained() {
        owner.addFavors(2);
    }

    @Override
    public void doWhenTrashed() {
        owner.addFavors(2);
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}