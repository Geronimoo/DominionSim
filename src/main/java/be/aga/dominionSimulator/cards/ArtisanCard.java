package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ArtisanCard extends DomCard {
    public ArtisanCard() {
      super( DomCardName.Artisan);
    }

    public void play() {
        if (owner.isHumanOrPossessedByHuman()) {
            ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
            for (DomCardName theCard : owner.getCurrentGame().getBoard().keySet()) {
                if (theCard.getCost(owner.getCurrentGame()).compareTo(new DomCost(5, 0)) <= 0 && owner.getCurrentGame().countInSupply(theCard)>0)
                    theChooseFrom.add(theCard);
            }
            if (!theChooseFrom.isEmpty()) {
              owner.gainInHand(owner.getCurrentGame().takeFromSupply(owner.getEngine().getGameFrame().askToSelectOneCard("Gain a card for" + this.getName().toString(), theChooseFrom, "Mandatory!")));
            }
            Set<DomCardName> uniqueCards = owner.getUniqueCardsInHand();
            if (!uniqueCards.isEmpty()) {
                theChooseFrom.clear();
                theChooseFrom.addAll(uniqueCards);
                owner.setNeedsToUpdate();
                DomCard theChosenCard = owner.getCardsFromHand(owner.getEngine().getGameFrame().askToSelectOneCard("Put back a card for " + this.getName().toString(), theChooseFrom, "Mandatory!")).get(0);
                owner.putOnTopOfDeck(owner.removeCardFromHand(theChosenCard));
            }
        } else {
            DomCardName theDesiredCard = owner.getDesiredCard(new DomCost(5, 0), false);
            if (theDesiredCard == null) {
                //possible to get here if card was throne-roomed
                theDesiredCard = owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost(5, 0));
            }
            if (theDesiredCard != null)
                owner.gainInHand(theDesiredCard);
            owner.putCardFromHandOnTop();
        }
    }
    
    @Override
    public boolean wantsToBePlayed() {
       return owner.getDesiredCard(new DomCost( 5, 0), false) != null ;
    }

    @Override
    public int getPlayPriority() {
        if (owner.actionsLeft>1 && !owner.getCardsFromHand(DomCardName.Vassal).isEmpty()
                && owner.getDesiredCard(new DomCost( 5, 0), false) != null
                && owner.getDesiredCard(new DomCost( 5, 0), false).hasCardType(DomCardType.Action))
            return owner.getCardsFromHand(DomCardName.Vassal).get(0).getPlayPriority()-1;
        return super.getPlayPriority();
    }
}