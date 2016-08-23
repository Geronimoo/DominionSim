package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class DukeCard extends DomCard {
    public DukeCard () {
      super( DomCardName.Duke);
    }
    
    @Override
    public int getTrashPriority() {
      if (owner!=null && owner.wantsToGainOrKeep(DomCardName.Duke))
    	return 42;
      
      return super.getTrashPriority();
    }
}