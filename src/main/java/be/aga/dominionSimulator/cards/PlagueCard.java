package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class PlagueCard extends DomCard {
    public PlagueCard() {
      super( DomCardName.Haunting);
    }

    public void play() {
        DomCard theCurse = owner.getCurrentGame().takeFromSupply(DomCardName.Curse);
        if (theCurse==null)
            return;
        owner.gainInHand(theCurse);
    }
}