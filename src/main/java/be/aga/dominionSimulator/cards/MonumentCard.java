package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

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
        if (owner.getActionsLeft()>1 && !owner.getCardsFromHand(DomCardName.Hunting_Party).isEmpty())
            return owner.getCardsFromHand(DomCardName.Hunting_Party).get(0).getPlayPriority()-1;
        return super.getPlayPriority();
    }
}