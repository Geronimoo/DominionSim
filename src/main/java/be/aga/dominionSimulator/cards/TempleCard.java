package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.Collections;
import java.util.HashSet;

public class TempleCard extends DomCard {
    public TempleCard() {
      super( DomCardName.Temple);
    }

    public void play() {
        owner.addVP(1);
        HashSet<DomCardName> theCardsTrashed = new HashSet<DomCardName>();
        Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
        for (int i=0;i<owner.getCardsInHand().size() && theCardsTrashed.size()<3;i++) {
            DomCard theCardToTrash = owner.getCardsInHand().get(i);
            if (theCardsTrashed.isEmpty()) {
                theCardsTrashed.add(theCardToTrash.getName());
                continue;
            }
            if (theCardsTrashed.contains(theCardToTrash.getName()))
                continue;

            if (theCardToTrash.getTrashPriority()>DomCardName.Copper.getTrashPriority(owner)
                    || owner.removingReducesBuyingPower( theCardToTrash ))
                continue;
            theCardsTrashed.add(theCardToTrash.getName());
        }
        for (DomCardName theCardname : theCardsTrashed) {
            owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theCardname).get(0)));
        }
        owner.getCurrentGame().getBoard().addVPon(DomCardName.Temple);
    }

    @Override
    public void doWhenGained() {
        owner.addVP(owner.getCurrentGame().getBoard().getAllVPFromPile(DomCardName.Temple));
    }

    @Override
    public boolean wantsToBePlayed() {
        Collections.sort( owner.getCardsInHand() , SORT_FOR_TRASHING);
        if (owner.getCardsInHand().get(0).getTrashPriority()<=DomCardName.Copper.getTrashPriority())
            return true;
        if (!owner.getCardsFromHand(DomCardName.Fortress).isEmpty())
            return true;
        return false;
    }

}