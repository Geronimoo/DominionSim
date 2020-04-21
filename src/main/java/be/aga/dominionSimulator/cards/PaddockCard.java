package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class PaddockCard extends DomCard {
    public PaddockCard() {
      super( DomCardName.Paddock);
    }

    @Override
    public void play() {
    	owner.addAvailableCoins(2);
        owner.gain(DomCardName.Horse);
        owner.gain(DomCardName.Horse);
        if (owner.getCurrentGame().countEmptyPiles()>0)
            owner.addActions(owner.getCurrentGame().countEmptyPiles());
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}