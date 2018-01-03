package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class DelusionCard extends DomCard {
    public DelusionCard() {
      super( DomCardName.Delusion);
    }

    public void play() {
        if (!owner.isDeluded() && !owner.isEnvious())
          owner.setDeluded(true);
    }
}