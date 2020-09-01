package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class CovenCard extends DomCard {
    public CovenCard() {
      super( DomCardName.Coven);
    }

    public void play() {
        owner.addActions(1);
        owner.addAvailableCoins( 2 );
        for (DomPlayer thePlayer : owner.getOpponents()) {
          if (thePlayer.checkDefense())
        	continue;
          if (owner.getCurrentGame().countInSupply(DomCardName.Curse )>0) {
              DomCard theCurse = owner.getCurrentGame().takeFromSupply(DomCardName.Curse);
              thePlayer.getDeck().addPhysicalCardWhenNotGained(theCurse);
              thePlayer.exile(theCurse);
          }else {
              thePlayer.discardAllFromExileMat(DomCardName.Curse);
          }
        }
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}