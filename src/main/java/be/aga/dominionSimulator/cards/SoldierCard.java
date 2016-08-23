package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class SoldierCard extends DomCard {
    public SoldierCard() {
      super( DomCardName.Soldier);
    }

    public void play() {
      owner.addAvailableCoins(2);
      if (owner.getCardsFromPlay(DomCardType.Attack).size()-1>0)
          owner.addAvailableCoins(owner.getCardsFromPlay(DomCardType.Attack).size()-1);
      for (DomPlayer thePlayer : owner.getOpponents()) {
          if (thePlayer.checkDefense())
              continue;
          if (thePlayer.getCardsInHand().isEmpty() || thePlayer.getCardsInHand().size()<4)
              return;
          thePlayer.doForcedDiscard(1,false);
      }
    }

    @Override
    public void handleCleanUpPhase() {
        if (owner==null)
            return;
        if (owner.wants(DomCardName.Fugitive)) {
            DomPlayer theOwner = owner;
            owner.returnToSupply(this);
            theOwner.gain(DomCardName.Fugitive);
            return;
        }
        super.handleCleanUpPhase();
    }
}