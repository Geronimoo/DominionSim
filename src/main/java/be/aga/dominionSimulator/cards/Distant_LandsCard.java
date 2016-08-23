package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Distant_LandsCard extends DomCard {
    public Distant_LandsCard() {
      super( DomCardName.Distant_Lands);
    }

    @Override
    public void play() {
        if (!owner.getAllFromTavernMat(DomCardName.Distant_Lands).contains(this))
            owner.putOnTavernMat(owner.removeCardFromPlay(this));
    }

    @Override
    public int getTrashPriority() {
      if (owner!=null && owner.wantsToGainOrKeep(DomCardName.Distant_Lands))
    	return 42;
      return super.getTrashPriority();
    }

    @Override
    public int getVictoryValue() {
        if (owner.getAllFromTavernMat(DomCardName.Distant_Lands).isEmpty())
            return 0;
        return owner.getAllFromTavernMat(DomCardName.Distant_Lands).contains(this) ? 4 : 0;
    }
}