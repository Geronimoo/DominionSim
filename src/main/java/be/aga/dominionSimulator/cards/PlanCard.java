package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class PlanCard extends DomCard {

	public PlanCard() {
      super( DomCardName.Plan);
    }

    public void play() {
        owner.placeTrashingToken();
    }
}