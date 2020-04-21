package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class KilnCard extends DomCard {
    public KilnCard() {
      super( DomCardName.Kiln);
    }

    public void play() {
      owner.addAvailableCoins(2);
      owner.triggerKiln();
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

    @Override
    public int getPlayPriority() {
        if (owner.getActionsLeft()>1)
            return 5;
        return super.getPlayPriority();
    }
}