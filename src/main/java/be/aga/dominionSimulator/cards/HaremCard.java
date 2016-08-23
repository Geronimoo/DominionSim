package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class HaremCard extends DomCard {
    public HaremCard () {
      super( DomCardName.Harem);
    }
    
    @Override
    public int getTrashPriority() {
      return 41;
    }
}