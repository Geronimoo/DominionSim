package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class ExpeditionCard extends DomCard {

	public ExpeditionCard() {
      super( DomCardName.Expedition);
    }

    public void play() {
        owner.activateExpedition();
    }
}