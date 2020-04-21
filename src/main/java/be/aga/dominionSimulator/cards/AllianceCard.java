package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class AllianceCard extends DomCard {
    public AllianceCard() {
      super( DomCardName.Alliance);
    }

    public void play() {
		owner.gain(DomCardName.Province);
		owner.gain(DomCardName.Duchy);
		owner.gain(DomCardName.Estate);
		owner.gain(DomCardName.Gold);
		owner.gain(DomCardName.Silver);
		owner.gain(DomCardName.Copper);
    }
}