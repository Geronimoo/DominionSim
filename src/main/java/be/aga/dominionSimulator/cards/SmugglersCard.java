package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class SmugglersCard extends DomCard {
    public SmugglersCard () {
      super( DomCardName.Smugglers);
    }

    public void play() {
      if (owner.getOpponents().isEmpty())
    	return;
      DomPlayer theRightOpponent = owner.getOpponents().get(owner.getOpponents().size()-1);
      ArrayList<DomCardName> cardsGainedLastTurn = theRightOpponent.getCardsGainedLastTurn();
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman(cardsGainedLastTurn);
      } else {
          Collections.sort(cardsGainedLastTurn, DomCardName.SORT_FOR_TRASHING);
          for (int i = cardsGainedLastTurn.size() - 1; i >= 0; i--) {
              if (new DomCost(6, 0).compareTo(cardsGainedLastTurn.get(i).getCost(owner.getCurrentGame())) >= 0
                      && owner.wants(cardsGainedLastTurn.get(i))) {
                  owner.gain(cardsGainedLastTurn.get(i));
                  return;
              }
          }
      }
    }

    private void handleHuman(ArrayList<DomCardName> cardsGainedLastTurn) {
        if (cardsGainedLastTurn.isEmpty())
            return;
        if (cardsGainedLastTurn.size()==1 && cardsGainedLastTurn.get(0).getCost(owner.getCurrentGame()).compareTo(new DomCost(6,0))<=0) {
            owner.gain(cardsGainedLastTurn.get(0));
        } else {
            ArrayList<DomCardName> theChoosfrom = new ArrayList<DomCardName>();
            for (DomCardName theCard : cardsGainedLastTurn) {
                if (new DomCost(6,0).compareTo(theCard.getCost(owner.getCurrentGame()))>=0){
                   theChoosfrom.add(theCard);
                }
            }
            owner.gain(owner.getEngine().getGameFrame().askToSelectOneCard("Gain", theChoosfrom, "Mandatory!"));
        }
    }
}