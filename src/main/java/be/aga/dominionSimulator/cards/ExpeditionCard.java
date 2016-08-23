package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class ExpeditionCard extends DomCard {

	public ExpeditionCard() {
      super( DomCardName.Expedition);
    }

    public void play() {
        owner.setExpeditionActivated();
    }
}