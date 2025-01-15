package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class AristocratCard extends DomCard {
    public AristocratCard() {
      super( DomCardName.Aristocrat);
    }

    public void play() {
      if (owner.countInPlay(DomCardName.Aristocrat)==1 || owner.countInPlay(DomCardName.Aristocrat)==5)
          owner.addActions(3);
      if (owner.countInPlay(DomCardName.Aristocrat)==2 || owner.countInPlay(DomCardName.Aristocrat)==6)
          owner.drawCards(3);
      if (owner.countInPlay(DomCardName.Aristocrat)==3 || owner.countInPlay(DomCardName.Aristocrat)==7)
          owner.addAvailableCoins(3);
      if (owner.countInPlay(DomCardName.Aristocrat)==4 || owner.countInPlay(DomCardName.Aristocrat)==8)
          owner.addAvailableBuys(3);
    }

    @Override
    public int getPlayPriority() {
        if (owner.countInPlay(DomCardName.Aristocrat)==0)
            return 5;
        return super.getPlayPriority();
    }
}