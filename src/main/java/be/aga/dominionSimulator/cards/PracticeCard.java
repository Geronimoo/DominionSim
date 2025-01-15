package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class PracticeCard extends DomCard {
    public PracticeCard() {
      super( DomCardName.Alley);
    }

    public void play() {
        ArrayList<DomCard> actionsInHand = owner.getCardsFromHand(DomCardType.Action);
        if (actionsInHand.isEmpty())
            return;
        Collections.sort(actionsInHand, SORT_FOR_PLAYING);
        for (int i = 0;i<actionsInHand.size();i++) {
            DomCard theCard = actionsInHand.get(i);
            if (theCard.wantsToBePlayed()) {
                owner.play(owner.removeCardFromHand(theCard));
                if (owner.getCardsInPlay().contains((theCard))) {
                    owner.removeCardFromPlay(theCard);
                    owner.play(theCard);
                }
                return;
            }
        }
    }
}