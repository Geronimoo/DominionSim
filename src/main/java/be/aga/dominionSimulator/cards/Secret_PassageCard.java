package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class Secret_PassageCard extends DomCard {
    public Secret_PassageCard() {
      super( DomCardName.Secret_Passage);
    }

    public void play() {
        owner.addActions(1);
        owner.drawCards(2);
        if (owner.getCardsInHand().isEmpty())
            return;
        Collections.sort(owner.getCardsInHand(), SORT_FOR_DISCARD_FROM_HAND);
        DomCard theCardToReturn = null;
        ArrayList<DomCard> theCardsInHand = owner.getCardsInHand();
        for (int i=theCardsInHand.size()-1;i>=0;i--){
            if (theCardsInHand.get(i).hasCardType(DomCardType.Action))
                if (owner.actionsLeft==0 || theCardsInHand.get(i).getName()==DomCardName.Vassal || owner.getCardsFromHand(DomCardName.Vassal).isEmpty())
                    continue;
            theCardToReturn = theCardsInHand.get(i);
            if (!owner.removingReducesBuyingPower(theCardToReturn)) {
                break;
            }
        }
        if (theCardsInHand.get(0).hasCardType(DomCardType.Action))
            theCardToReturn=theCardsInHand.get(0);
        if (owner.getDrawDeckSize()!=0 && !owner.getCardsFromHand(DomCardName.Sentry).isEmpty() && owner.hasJunkInHand()) {
            Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
            theCardToReturn=theCardsInHand.get(0);
            owner.putSecondFromTop(owner.removeCardFromHand(theCardToReturn));
            return;
        }

        if (!owner.getCardsFromHand(DomCardName.Wishing_Well).isEmpty() && owner.getDeck().getDrawDeckSize()>0)
            owner.putSecondFromTop(owner.removeCardFromHand(theCardToReturn));
          else
            if (theCardToReturn.getDiscardPriority(1)<=DomCardName.Copper.getDiscardPriority(1))
              owner.putOnBottomOfDeck(owner.removeCardFromHand(theCardToReturn));
            else
              owner.putOnTopOfDeck(owner.removeCardFromHand(theCardToReturn));
    }

    @Override
    public int getPlayPriority() {
        if (owner.getKnownTopCards()==0 && !owner.getCardsFromHand(DomCardName.Wishing_Well).isEmpty())
            return owner.getCardsFromHand(DomCardName.Wishing_Well).get(0).getPlayPriority()-1;
        return super.getPlayPriority();
    }
}