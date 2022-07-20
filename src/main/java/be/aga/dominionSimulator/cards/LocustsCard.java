package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.*;
import be.aga.dominionSimulator.enums.DomCardName;

public class LocustsCard extends DomCard {
    public LocustsCard() {
      super( DomCardName.Locusts);
    }

    public void play() {
        DomPlayer.doLocustsOrBarbarianEffect(owner,DomCardName.Locusts);
    }

}