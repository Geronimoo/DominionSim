package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class GardensCard extends DomCard {
    public GardensCard () {
      super( DomCardName.Gardens);
    }
    
    @Override
    public int getTrashPriority() {
      if (owner!=null && owner.wantsToGainOrKeep(DomCardName.Gardens))
          return 65;

      return super.getTrashPriority();
    }
}