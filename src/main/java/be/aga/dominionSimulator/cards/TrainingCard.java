package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class TrainingCard extends DomCard {
    public TrainingCard() {
      super( DomCardName.Training);
    }

    public void play() {
        owner.placePlusOneCoinToken();
    }
}