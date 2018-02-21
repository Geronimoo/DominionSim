package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class EnvyCard extends DomCard {
    public EnvyCard() {
      super( DomCardName.Envy);
    }

    public void play() {
        if (!owner.isDeluded() && !owner.isEnvious())
          owner.setEnvious(true);
    }
}