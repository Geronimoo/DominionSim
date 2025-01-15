package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class Fortune_HunterCard extends DomCard {
    public Fortune_HunterCard() {
      super( DomCardName.Fortune_Hunter);
    }

    public void play() {
      owner.addAvailableCoins(2);
      ArrayList< DomCard > theRevealedCards = owner.revealTopCards( 3 );
      if (theRevealedCards.isEmpty())
        return;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman(theRevealedCards);
          return;
      }
      DomCardName theDesiredCard = null;
      Collections.sort(theRevealedCards,SORT_FOR_DISCARD_FROM_HAND);
      DomCard theCardToTake = null;
      for (DomCard domCard : theRevealedCards) {
          if (!domCard.hasCardType(DomCardType.Treasure))
              continue;
          DomCost potentialMoney = owner.getTotalPotentialCurrency();
          potentialMoney=potentialMoney.add(new DomCost(domCard.getCoinValue(),domCard.getPotionValue()));
          if (theCardToTake==null || theDesiredCard==null) {
              theDesiredCard = owner.getDesiredCard(potentialMoney,false);
              theCardToTake = domCard;
          } else {
              DomCardName theNewDesiredCard = owner.getDesiredCard(potentialMoney, false);
              if (theNewDesiredCard.getTrashPriority(owner)>theDesiredCard.getTrashPriority(owner)) {
                  theDesiredCard=theNewDesiredCard;
                  theCardToTake=domCard;
              }
          }
      }
      if (theCardToTake!=null){
          owner.play(theCardToTake);
          theRevealedCards.remove(theCardToTake);
      }
      while (!theRevealedCards.isEmpty())
        owner.putOnTopOfDeck(theRevealedCards.remove(0));
    }

    private void handleHuman(ArrayList<DomCard> theRevealedCards) {
        //TODO
//        ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
//        for (DomCard theCard : theRevealedCards) {
//            theChooseFrom.add(theCard.getName());
//        }
//        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Mandatory!");
//        for (DomCard theCard : theRevealedCards) {
//            if (theCard.getName()==theChosenCard) {
//                theRevealedCards.remove(theCard);
//                owner.trash(theCard);
//                break;
//            }
//        }
//        if (theRevealedCards.isEmpty())
//            return;
//        theChooseFrom=new ArrayList<DomCardName>();
//        for (DomCard theCard : theRevealedCards) {
//            theChooseFrom.add(theCard.getName());
//        }
//        theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Discard a card", theChooseFrom, "Mandatory!");
//        for (DomCard theCard : theRevealedCards) {
//            if (theCard.getName()==theChosenCard) {
//                theRevealedCards.remove(theCard);
//                owner.discard(theCard);
//                break;
//            }
//        }
//        if (theRevealedCards.isEmpty())
//            return;
//        owner.putOnTopOfDeck(theRevealedCards.get(0));
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}