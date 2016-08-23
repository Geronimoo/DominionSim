package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Silk_RoadCard extends DomCard {
    public Silk_RoadCard () {
      super( DomCardName.Silk_Road);
    }
    
    @Override
    public int getTrashPriority() {
      if (owner!=null && owner.wantsToGainOrKeep(DomCardName.Silk_Road))
          return 40;

      return super.getTrashPriority();
    }
}