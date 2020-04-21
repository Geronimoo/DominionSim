package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

import java.util.ArrayList;
import java.util.Collections;

public class RatsCard extends DomCard {
    public RatsCard() {
      super( DomCardName.Rats);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
      owner.gain(DomCardName.Rats);
      if (owner.getCardsInHand().isEmpty())
          return;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      if (!owner.getCardsFromHand(DomCardName.Menagerie).isEmpty() && !DomPlayer.getMultiplesInHand((MenagerieCard) owner.getCardsFromHand(DomCardName.Menagerie).get(0)).isEmpty()) {
          ArrayList<DomCard> theMultiples = DomPlayer.getMultiplesInHand((MenagerieCard) owner.getCardsFromHand(DomCardName.Menagerie).get(0));
          Collections.sort(theMultiples, SORT_FOR_TRASHING);
          if (theMultiples.get(0).getTrashPriority() <= DomCardName.Silver.getTrashPriority(owner)) {
              owner.trash(owner.removeCardFromHand(theMultiples.get(0)));
              return;
          }
      }
      Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
      int i=0;
      while (i<owner.getCardsInHand().size() && owner.getCardsInHand().get(i).getName()==DomCardName.Rats)
          i++;
      if (i<owner.getCardsInHand().size()) {
          if (owner.getCardsInHand().get(i).getTrashPriority()<= DomCardName.Estate.getTrashPriority() || owner.getCardsFromHand(DomCardName.Fortress).isEmpty()) {
              owner.trash(owner.removeCardFromHand(owner.getCardsInHand().get(i)));
          } else {
              if (!owner.getCardsFromHand(DomCardName.Fortress).isEmpty())
                  owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(DomCardName.Fortress).get(0)));
          }
      }
    }

    private void handleHuman() {
        owner.setNeedsToUpdateGUI();
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand()) {
            if (theCard.getName()!=DomCardName.Rats)
              theChooseFrom.add(theCard.getName());
        }
        if (theChooseFrom.isEmpty())
            return;
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Mandatory!");
        owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
    }

    @Override
    public boolean wantsToBePlayed() {
        if (owner.getPlayStrategyFor(this)== DomPlayStrategy.DominateTraining){
          return true;
        }

        if (owner.getCardsInHand().isEmpty())
            return false;
//        if (owner.count(DomCardName.Rats)>2)
//            return false;
        Collections.sort( owner.getCardsInHand() , SORT_FOR_TRASHING);
        int i=0;
        while (i<owner.getCardsInHand().size() && owner.getCardsInHand().get(i).getName()==DomCardName.Rats )
            i++;
        if (i==owner.getCardsInHand().size())
            return false;
        if (owner.getCardsInHand().get(i).getTrashPriority()<=DomCardName.Copper.getTrashPriority())
            return true;
        if (!owner.getCardsFromHand(DomCardName.Fortress).isEmpty())
            return true;
        return false;
    }

    @Override
    public void doWhenTrashed() {
        owner.drawCards(1);
    }

    @Override
    public int getPlayPriority() {
        if (!owner.getCardsFromHand(DomCardName.Fortress).isEmpty())
            return owner.getCardsFromHand(DomCardName.Fortress).get(0).getPlayPriority()-1;
        if (wantsToBePlayed() && owner.getCardsFromHand(DomCardName.Rats).size()>1)
            return super.getPlayPriority();
        if (owner.getCardsFromHand(DomCardType.TrashForBenefit).isEmpty() && owner.getCardsFromHand(DomCardName.Death_Cart).isEmpty() && (owner.getCardsFromHand(DomCardName.Chapel).isEmpty() || owner.count(DomCardName.Rats)==1))
            return super.getPlayPriority();
        return 100;
    }

    @Override
    public int getTrashPriority() {
        if (owner==null)
            return super.getTrashPriority();
        if (owner.count(DomCardName.Rats)>1)
            return 13;
        return super.getTrashPriority();
    }

    @Override
    public boolean wantsToBeMultiplied() {
        return false;
    }
}