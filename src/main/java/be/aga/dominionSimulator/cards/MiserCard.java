package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;


public class MiserCard extends DomCard {
    public MiserCard() {
      super( DomCardName.Miser);
    }

    public void play() {
      if (owner.isHumanOrPossessedByHuman()) {
          if (owner.getEngine().getGameFrame().askPlayer("<html>Copper to Mat?" , "Resolving " + this.getName().toString()))
              playForInvesting();
          else
              playForMoney();
      } else {
          if (owner.getCardsFromHand(DomCardName.Copper).isEmpty()
                  || (owner.getPlayStrategyFor(this) != DomPlayStrategy.removeAllCoppers && owner.getAllFromTavernMat(DomCardName.Copper).size() > 4)) {
              playForMoney();
          } else {
              playForInvesting();
          }
      }
    }

    private void playForInvesting() {
        if (owner.getCardsFromHand(DomCardName.Copper).isEmpty())
            return;
        owner.putOnTavernMat(owner.removeCardFromHand(owner.getCardsFromHand(DomCardName.Copper).get(0)));
    }

    private void playForMoney() {
        owner.addAvailableCoins(owner.getAllFromTavernMat(DomCardName.Copper).size());
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}