package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class ChangeCard extends DomCard {
    public ChangeCard() {
      super( DomCardName.Change);
    }

    public void play() {
        if (owner.getDebt()>0) {
            owner.addAvailableCoins(3);
            return;
        }
        if (owner.getCardsInHand().isEmpty())
            return;
        DomCard theCardToTrash = owner.findCardToRemodel(this, 100, false);
        if (theCardToTrash == null) {
            //this is needed when card is played with Throne Room effect or Golem
            Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
            theCardToTrash = owner.getCardsInHand().get(0);
        }
        int coinsOfCardToTrash = theCardToTrash.getCoinCost(owner.getCurrentGame());
        owner.trash(owner.removeCardFromHand(theCardToTrash));
        DomCost theMaxCostOfCardToGain = new DomCost(100, 1);
        DomCardName theDesiredCard = owner.getDesiredCard(theMaxCostOfCardToGain, false);
        if (theDesiredCard == null)
            theDesiredCard = owner.getCurrentGame().getBestCardInSupplyFor(owner, null, theMaxCostOfCardToGain);
        if (theDesiredCard != null) {
            owner.gain(theDesiredCard);
            owner.addDebt(theDesiredCard.getCoinCost(owner)-coinsOfCardToTrash);
        }
    }

    @Override
    public boolean wantsToBePlayed() {
      return owner.findCardToRemodel(this, 100, false)!=null;
   }
}