package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class CurseCard extends DomCard {
    public CurseCard () {
      super( DomCardName.Curse);
    }
    
    @Override
    public int getTrashPriority() {
      return 0;
    }

    @Override
    public void play() {
        if (owner!=null && owner.getCurrentGame().getBoard().containsKey(DomCardName.Charlatan))
            owner.addAvailableCoins(1);
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (owner!=null && aType==DomCardType.Treasure && owner.getCurrentGame().getBoard().containsKey(DomCardName.Charlatan))
            return true;
        return super.hasCardType(aType);
    }
}