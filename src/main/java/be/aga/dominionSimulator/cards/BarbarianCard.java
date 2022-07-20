package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class BarbarianCard extends DomCard {
    public BarbarianCard() {
      super( DomCardName.Barbarian);
    }

    public void play() {
      owner.addAvailableCoins(2);
      for (DomPlayer thePlayer : owner.getOpponents()) {
          if (thePlayer.checkDefense()) 
        	continue;
          DomPlayer.doLocustsOrBarbarianEffect(thePlayer,DomCardName.Barbarian);
      }
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}