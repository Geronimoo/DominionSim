package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class BerserkerCard extends DomCard {
    public BerserkerCard() {
      super( DomCardName.Berserker);
    }

    public void play() {
        DomCardName theDesiredCard = owner.getDesiredCard(new DomCost(getCoinCost(owner.getCurrentGame())-1, 0), false);
        if (theDesiredCard == null) {
            //possible to get here if card was throne-roomed
            theDesiredCard = owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost(getCoinCost(owner.getCurrentGame())-1, 0));
        }
        if (theDesiredCard != null)
            owner.gain(theDesiredCard);
        for (DomPlayer thePlayer : owner.getOpponents()) {
        if (!thePlayer.checkDefense()) {
          thePlayer.doForcedDiscard(thePlayer.getCardsInHand().size()-3, false);
        }
      }
    }

    @Override
    public void doWhenGained() {
        if (owner.getCardsFromPlay(DomCardType.Action).isEmpty())
            return;
        if (owner.getDeck().getDiscardPile().contains(this)) {
            owner.play(owner.removeCardFromDiscard(this));
        }
    }
}