package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class MissionCard extends DomCard {
    public MissionCard() {
      super( DomCardName.Mission);
    }
    @Override
    public void play() {
		owner.setExtraMissionTurn(true);
    }
}