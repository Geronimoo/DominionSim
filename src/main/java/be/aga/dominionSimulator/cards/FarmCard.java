package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class FarmCard extends DomCard {
    public FarmCard() {
      super( DomCardName.Farm);
    }
    
    @Override
    public int getTrashPriority() {
      return 41;
    }
}