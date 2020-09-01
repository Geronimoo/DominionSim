package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class MonumentCard extends DomCard {
    public MonumentCard () {
      super( DomCardName.Monument);
    }

    public void play() {
      owner.addAvailableCoins( 2 );
      owner.addVP( 1);
    }

    @Override
    public int getPlayPriority() {
        if (owner.getActionsAndVillagersLeft()>1 && !owner.getCardsFromHand(DomCardName.Hunting_Party).isEmpty())
            return owner.getCardsFromHand(DomCardName.Hunting_Party).get(0).getPlayPriority()-1;
        return super.getPlayPriority();
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}