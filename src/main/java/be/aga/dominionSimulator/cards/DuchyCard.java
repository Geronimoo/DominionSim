package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class DuchyCard extends DomCard {
    public DuchyCard () {
      super( DomCardName.Duchy);
    }
    
    @Override
    public int getTrashPriority() {
      if (owner!=null && owner.wantsToGainOrKeep(DomCardName.Duchy))
    	return 40;
      
      return super.getTrashPriority();
    }
    
    @Override
    public void doWhenGained() {
      if (owner.wants(DomCardName.Duchess))
    	owner.gain(DomCardName.Duchess);
    }
}