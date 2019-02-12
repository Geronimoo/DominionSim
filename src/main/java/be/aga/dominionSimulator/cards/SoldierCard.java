package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class SoldierCard extends TravellerCard{
    public SoldierCard() {
      super( DomCardName.Soldier);
      myUpgrade=DomCardName.Fugitive;
    }

    public void play() {
      owner.addAvailableCoins(2);
      if (owner.getCardsFromPlay(DomCardType.Attack).size()-1>0)
          owner.addAvailableCoins(owner.getCardsFromPlay(DomCardType.Attack).size()-1);
      for (DomPlayer thePlayer : owner.getOpponents()) {
          if (thePlayer.checkDefense() || thePlayer.getCardsInHand().isEmpty() || thePlayer.getCardsInHand().size()<4)
              continue;
          thePlayer.doForcedDiscard(1,false);
      }
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}