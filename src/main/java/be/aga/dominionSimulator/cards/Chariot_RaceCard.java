package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Chariot_RaceCard extends DomCard {
    public Chariot_RaceCard() {
        super(DomCardName.Chariot_Race);
    }

    public void play() {
        owner.addActions(1);
        if (owner.getDeckAndDiscardSize()==0 || owner.getOpponents().isEmpty())
            return;
        DomPlayer theLeftOpp = owner.getOpponents().get(0);
        if (theLeftOpp.getDeckAndDiscardSize()==0)
            return;
        DomCard theRevealedCard = owner.revealTopCards(1).get(0);
        DomCard theOppRevealedCard = theLeftOpp.revealTopCards(1).get(0);

        if (theRevealedCard.getDebtCost()==0 && theOppRevealedCard.getDebtCost()==0 &&
        theRevealedCard.getCost(owner.getCurrentGame()).customCompare(theOppRevealedCard.getCost(owner.getCurrentGame()))>0) {
            owner.addAvailableCoins(1);
            owner.addVP(1);
        }
        if (theRevealedCard.getDebtCost()>0 && theOppRevealedCard.getDebtCost()>0 &&
                theRevealedCard.getCost(owner.getCurrentGame()).customCompare(theOppRevealedCard.getCost(owner.getCurrentGame()))>0) {
            owner.addAvailableCoins(1);
            owner.addVP(1);
        }
        owner.putInHand(theRevealedCard);
        theLeftOpp.putOnTopOfDeck(theOppRevealedCard);
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}