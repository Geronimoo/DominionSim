package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class PriestCard extends DomCard {
    public PriestCard() {
      super( DomCardName.Priest);
    }

    public void play() {
        owner.addAvailableCoins(2);
        if (owner.getCardsInHand().isEmpty()) {
            owner.addTrashingBonus();
            return;
        }
        Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
        if (owner.isHumanOrPossessedByHuman()) {
            ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
            for (DomCard theCard : owner.getCardsInHand()) {
                theChooseFrom.add(theCard.getName());
            }
            DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Mandatory!");
            if (theChosenCard != null) {
                owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
            }
        } else {
            owner.trash(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
        }
        owner.addTrashingBonus();
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}