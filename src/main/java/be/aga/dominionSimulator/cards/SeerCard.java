package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class SeerCard extends DomCard {
    public SeerCard() {
      super( DomCardName.Seer);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
      ArrayList< DomCard > theCards = owner.revealTopCards(3);
      while (!theCards.isEmpty()) {
          boolean cardFound = false;
          for (DomCard theCard:theCards) {
              if (theCard.getPotionCost()>0)
                  continue;
              if (theCard.getCost(owner.getCurrentGame()).getCoins()==2
                      || theCard.getCost(owner.getCurrentGame()).getCoins()==3
                      || theCard.getCost(owner.getCurrentGame()).getCoins()==4) {
                  owner.addCardToHand(theCards.remove(theCards.indexOf(theCard)));
                  cardFound = true;
                  break;
              }
          }
          if (!cardFound)
              break;
      }
      if (theCards.isEmpty())
          return;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman(theCards);
      } else {
          Collections.sort(theCards, SORT_FOR_DISCARDING);
          for (DomCard theCard : theCards) {
            owner.putOnTopOfDeck(theCard);
          }
      }
    }

    private void handleHuman(ArrayList<DomCard> theRevealedCards) {
        owner.setNeedsToUpdateGUI();
        ArrayList<DomCard> chooseFrom = new ArrayList<DomCard>();
        for (DomCard theCard:theRevealedCards) {
          chooseFrom.add(theCard);
        }
        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
        do {
            owner.getEngine().getGameFrame().askToSelectCards("<html>Choose <u>order</u> (first card = top card)</html>", chooseFrom, theChosenCards, 0);
        } while (theChosenCards.size()!=0 && theChosenCards.size()!=chooseFrom.size());
        if (theChosenCards.size()==0) {
           while (!theRevealedCards.isEmpty()) {
               owner.putOnTopOfDeck(theRevealedCards.remove(0));
           }
        } else {
            for (int i = theChosenCards.size() - 1; i >= 0; i--) {
                for (DomCard theCard : chooseFrom) {
                    if (theChosenCards.get(i).getName() == theCard.getName()) {
                        owner.putOnTopOfDeck(theCard);
                        chooseFrom.remove(theCard);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public int getPlayPriority() {
        if (owner.getDeckSize()==0)
            return 1000;
        return super.getPlayPriority();
    }
}