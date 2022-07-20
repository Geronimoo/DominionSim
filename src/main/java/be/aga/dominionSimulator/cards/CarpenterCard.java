package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class CarpenterCard extends DomCard {
    public CarpenterCard() {
      super( DomCardName.Carpenter);
    }

    public void play() {
      if (hasCardType(DomCardType.Terminal)) {
          RemodelCard.doRemodel(owner, this);
      } else {
          owner.addActions(1);
          WorkshopCard.doWorkshop(owner);
      }
    }

    @Override
    public boolean wantsToBePlayed() {

//       if (hasCardType(DomCardType.Terminal))
//         return owner.findCardToRemodel(this, 2, true)!=null;
       return true;
   }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Terminal && owner.getCurrentGame().countEmptyPiles()>0)
            return true;
        return super.hasCardType(aType);
    }
}