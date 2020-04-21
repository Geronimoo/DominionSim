package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class EnclaveCard extends DomCard {
    public EnclaveCard() {
      super( DomCardName.Enclave);
    }

    public void play() {
        if (owner.getCurrentGame().countInSupply(DomCardName.Gold)>0)
          owner.gain(DomCardName.Gold);
        if (owner.getCurrentGame().countInSupply(DomCardName.Duchy)>0) {
            DomCard theDuchy = owner.getCurrentGame().takeFromSupply(DomCardName.Duchy);
            owner.getDeck().addPhysicalCardWhenNotGained(theDuchy);
            owner.moveToExileMat(theDuchy);
        }
    }
}