package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class ContinueCard extends DomCard {
    public ContinueCard() {
      super( DomCardName.Continue);
    }

    public void play() {
        DomCardName theDesiredCard = owner.getDesiredCard(DomCardType.Action,new DomCost(4, 0), false, false, DomCardType.Attack);
        if (theDesiredCard==null)
            return;
        owner.gainInHand(theDesiredCard);
        owner.play(owner.removeCardFromHand(owner.getCardsFromHand(theDesiredCard).get(0)));
        owner.addActions(1);
        owner.addAvailableBuys(1);
        owner.triggerContinue();
    }
}