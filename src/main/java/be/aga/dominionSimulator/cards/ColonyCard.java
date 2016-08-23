package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class ColonyCard extends DomCard {
    public ColonyCard () {
      super( DomCardName.Colony);
    }
    
    @Override
    public int getTrashPriority() {
      return 100;
    }
}