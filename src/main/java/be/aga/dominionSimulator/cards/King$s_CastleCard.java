package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class King$s_CastleCard extends DomCard {
    public King$s_CastleCard() {
      super( DomCardName.King$s_Castle);
    }
    
    @Override
    public int getTrashPriority() {
      return 70;
    }
}