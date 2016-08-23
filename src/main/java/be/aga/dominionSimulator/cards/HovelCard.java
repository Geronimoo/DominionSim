package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class HovelCard extends DomCard {
    public HovelCard() {
      super( DomCardName.Hovel);
    }

    @Override
    public int getTrashPriority() {
        return 10;
    }
}