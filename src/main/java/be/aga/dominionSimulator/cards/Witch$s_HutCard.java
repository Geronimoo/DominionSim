package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class Witch$s_HutCard extends DomCard {
    public Witch$s_HutCard() {
      super( DomCardName.Witch$s_Hut);
    }

    public void play() {
      owner.drawCards( 4 );
      if (owner.getCardsFromHand(DomCardType.Action).size()<2) {
          Collections.sort(owner.getCardsInHand(), SORT_FOR_DISCARD_FROM_HAND);
          int i = 0;
          while (!owner.getCardsInHand().isEmpty() && i < 2) {
              owner.discardFromHand(owner.getCardsInHand().get(0));
              i++;
          }
      } else {
          ArrayList<DomCard> actionsInHand = owner.getCardsFromHand(DomCardType.Action);
          Collections.sort( actionsInHand, SORT_FOR_DISCARD_FROM_HAND);
          int i=0;
          while (i < 2) {
              owner.discardFromHand(actionsInHand.remove(0));
              i++;
          }
          for (DomPlayer thePlayer : owner.getOpponents()) {
              if (!thePlayer.checkDefense()) {
                  thePlayer.gain(DomCardName.Curse);
              }
          }
      }
	}
}