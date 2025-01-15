package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class SirenCard extends DomCard {
    public SirenCard() {
      super( DomCardName.Siren);
    }

    public void play() {
        for (DomPlayer thePlayer : owner.getOpponents()) {
            if (!thePlayer.checkDefense()) {
                thePlayer.gain(DomCardName.Curse);
            }
        }
    }

    @Override
    public void resolveDuration() {
        owner.drawCards( 8 - owner.getCardsInHand().size() );
    }

    @Override
    public void doWhenGained() {
        ArrayList<DomCard> theActions = owner.getCardsFromHand(DomCardType.Action);
        theActions.sort(DomCard.SORT_FOR_TRASHING);
        if (theActions.isEmpty()) {
            if (owner.getCardsFromDiscard().contains(this)) {
                owner.trash(owner.removeCardFromDiscard(this));
            }
        } else {
            owner.trash(owner.removeCardFromHand(theActions.get(0)));
        }
        if (owner!=null)
          super.doWhenGained();
    }
}