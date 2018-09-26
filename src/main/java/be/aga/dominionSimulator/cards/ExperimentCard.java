package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class ExperimentCard extends DomCard {
    public ExperimentCard() {
      super( DomCardName.Experiment);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(2);
      owner.returnToSupply(owner.removeCardFromPlay(this));
    }

    @Override
    public void doWhenGained() {
        if (owner.hasGainedExtraExperiment())
            return;
        owner.setGainedExtraExperiment(true);
        owner.gain(DomCardName.Experiment);
        owner.setGainedExtraExperiment(false);
    }
}