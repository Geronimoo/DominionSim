package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class CutpurseCard extends DomCard {
    public CutpurseCard () {
      super( DomCardName.Cutpurse);
    }

    public void play() {
      owner.addAvailableCoins(2);
      for (DomPlayer thePlayer : owner.getOpponents())
        if (!thePlayer.checkDefense())
          thePlayer.discardFromHand(DomCardName.Copper);
    }
}