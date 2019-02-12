package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Dame_SylviaCard extends KnightCard {

    public Dame_SylviaCard() {
        super(DomCardName.Dame_Sylvia);
    }

    public void play() {
        owner.addAvailableCoins(2);
        super.play();
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}