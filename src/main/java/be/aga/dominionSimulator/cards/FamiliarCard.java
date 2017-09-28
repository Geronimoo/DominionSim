package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class FamiliarCard extends DomCard {
    public FamiliarCard () {
      super( DomCardName.Familiar);
    }

    public void play() {
    	owner.addActions(1);
    	owner.drawCards(1);
        for (DomPlayer thePlayer : owner.getOpponents()) {
          if (!thePlayer.checkDefense()) {
            thePlayer.gain(DomCardName.Curse);
          }
        }
    }

    @Override
    public int getTrashPriority() {
        if (owner.getCurrentGame().countInSupply(DomCardName.Curse)==0)
          return 18;
        return super.getTrashPriority();
    }

    @Override
    public int getPlayPriority() {
        if (owner.getCurrentGame().countInSupply(DomCardName.Curse)==0)
            return DomCardName.Apprentice.getPlayPriority()+1;
        return super.getPlayPriority();
    }
}