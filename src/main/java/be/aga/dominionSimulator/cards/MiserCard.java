package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;


public class MiserCard extends DomCard {
    public MiserCard() {
      super( DomCardName.Miser);
    }

    public void play() {
      if (owner.getCardsFromHand(DomCardName.Copper).isEmpty()) {
          playForMoney();
          return;
      }
      if (owner.getPlayStrategyFor(this)!= DomPlayStrategy.removeAllCoppers && owner.getAllFromTavernMat(DomCardName.Copper).size()>4) {
          playForMoney();
          return;
      }
      playForInvesting();
    }

    private void playForInvesting() {
        owner.putOnTavernMat(owner.removeCardFromHand(owner.getCardsFromHand(DomCardName.Copper).get(0)));
    }

    private void playForMoney() {
        owner.addAvailableCoins(owner.getAllFromTavernMat(DomCardName.Copper).size());
    }
}