package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class TanukiCard extends DomCard {
    public TanukiCard() {
      super( DomCardName.Tanuki);
    }

    public void play() {
        RemodelCard.doRemodel(owner, this);
    }

    @Override
    public boolean wantsToBePlayed() {
      return owner.findCardToRemodel(this, 2, true)!=null;
   }
}