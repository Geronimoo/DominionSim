package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class FairgroundsCard extends DomCard {
    public FairgroundsCard () {
      super( DomCardName.Fairgrounds);
    }
    
    @Override
    public int getTrashPriority() {
      if (owner!=null && owner.wantsToGainOrKeep(DomCardName.Fairgrounds))
          return 55;

      return super.getTrashPriority();
    }
}