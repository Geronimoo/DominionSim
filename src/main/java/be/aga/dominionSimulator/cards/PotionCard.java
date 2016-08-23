package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class PotionCard extends DomCard {
    public PotionCard () {
      super( DomCardName.Potion);
    }
    
    @Override
    public int getPotionValue() {
      return 1;
    }
    
//    @Override
//    public int getTrashPriority() {
//    	if (owner==null || owner.getDeck()==null || owner.getDeck().get(getName())==null || owner.getDeck().get(getName()).isEmpty())
//    	  return super.getTrashPriority();
//    	boolean wantsThePotion=owner.wantsToGainOrKeep(getName());
//        return wantsThePotion ? super.getTrashPriority() : DomCardName.Copper.getTrashPriority(owner)-1;
//    }
}