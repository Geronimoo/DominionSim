package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class ShopCard extends DomCard {
    public ShopCard() {
      super( DomCardName.Shop);
    }

    public void play() {
      owner.addAvailableCoins(1);
      owner.drawCards(1);
      DomCard nextActionToPlay = owner.getNextActionNotInPlayToPlay();
      if (nextActionToPlay !=null )  {
         owner.play(owner.removeCardFromHand(nextActionToPlay));
      }
    }

    @Override
    public int getPlayPriority() {
        return owner.getCardsFromPlay(DomCardType.Action).isEmpty() ? 1 : super.getPlayPriority();
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}