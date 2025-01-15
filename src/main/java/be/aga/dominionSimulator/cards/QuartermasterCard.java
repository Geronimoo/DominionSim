package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class QuartermasterCard extends DomCard {
    private ArrayList<DomCard> cardsOnThis=new ArrayList<>();

    public QuartermasterCard() {
      super( DomCardName.Quartermaster);
    }

    public void resolveDuration() {
        if (cardsOnThis.isEmpty()
                || containsOnlyGardens() || containsOnlyCrossroadsAndNoVictoryInHand()) {
            DomCardName theDesiredCard = owner.getDesiredCard(new DomCost(4, 0), false);
            if (theDesiredCard == null) {
                //possible to get here if card was throne-roomed
                theDesiredCard = owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost(4, 0));
            }
            if (theDesiredCard != null) {
                DomCard theCardToGain = owner.getCurrentGame().takeFromSupply(theDesiredCard);
                owner.gain(theCardToGain);
                if (owner.getCardsFromDiscard().contains(theCardToGain)) {
                    cardsOnThis.add(owner.removeCardFromDiscard(theCardToGain));
                }
            }
        } else {
            for (DomCard theCard : cardsOnThis) {
                if (theCard.getName()!=DomCardName.Gardens) {
                    owner.putInHand(cardsOnThis.remove(cardsOnThis.indexOf(theCard)));
                    return;
                }
            }
        }
    }

    private boolean containsOnlyCrossroadsAndNoVictoryInHand() {
        if (!owner.getCardsFromHand(DomCardType.Victory).isEmpty())
            return false;
        for (DomCard theCard : cardsOnThis)
            if (theCard.getName()!=DomCardName.Crossroads)
                return false;
        return true;
    }

    private boolean containsOnlyGardens() {
        for (DomCard theCard : cardsOnThis)
            if (theCard.getName()!=DomCardName.Gardens)
                return false;
        return true;
    }

    @Override
    public boolean mustStayInPlay() {
        return true;
    }

    @Override
    public void cleanVariablesFromPreviousGames() {
        cardsOnThis.clear();
    }
}