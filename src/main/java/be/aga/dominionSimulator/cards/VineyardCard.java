package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class VineyardCard extends DomCard {
    public VineyardCard () {
      super( DomCardName.Vineyard);
    }
    
    @Override
    public int getTrashPriority() {
      if (owner!=null && owner.wantsToGainOrKeep(DomCardName.Vineyard))
          return 55;

      return super.getTrashPriority();
    }
}