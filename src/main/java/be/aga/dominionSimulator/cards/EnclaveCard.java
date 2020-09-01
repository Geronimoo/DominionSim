package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

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
            owner.exile(theDuchy);
        }
    }
}