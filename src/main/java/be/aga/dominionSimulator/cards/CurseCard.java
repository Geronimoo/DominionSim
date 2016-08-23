package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class CurseCard extends DomCard {
    public CurseCard () {
      super( DomCardName.Curse);
    }
    
    @Override
    public int getTrashPriority() {
      return 0;
    }
}