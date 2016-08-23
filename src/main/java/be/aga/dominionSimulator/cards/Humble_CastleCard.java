package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Humble_CastleCard extends DomCard {
    public Humble_CastleCard() {
      super( DomCardName.Humble_Castle);
    }
    
    @Override
    public int getTrashPriority() {
      return 43;
    }
}