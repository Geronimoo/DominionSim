package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class SeawayCard extends DomCard {

	public SeawayCard() {
      super( DomCardName.Seaway);
    }

    public void play() {
        owner.gain(owner.placePlusOneBuyToken());
   }
}