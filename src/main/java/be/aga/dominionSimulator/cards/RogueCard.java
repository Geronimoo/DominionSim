package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class RogueCard extends KnightCard {
    public RogueCard() {
      super( DomCardName.Rogue );
    }

    public void play() {
        owner.addAvailableCoins(2);
        ArrayList<DomCard> theRogueableCards = owner.getCurrentGame().getRogueableCardsInTrash();
        if (!theRogueableCards.isEmpty()) {
            if (owner.isHumanOrPossessedByHuman() && theRogueableCards.size()>1) {
                ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
                for (DomCard theCard : theRogueableCards)
                    theChooseFrom.add(theCard.getName());
                DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for " + this.getName().toString(), theChooseFrom, "Mandatory!");
                owner.gain(owner.getCurrentGame().removeFromTrash(theChosenCard));
            } else {
                Collections.sort(theRogueableCards, SORT_FOR_TRASHING);
                owner.gain(owner.getCurrentGame().removeFromTrash(theRogueableCards.get(theRogueableCards.size() - 1)));
            }
        } else {
            super.play();
        }
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}