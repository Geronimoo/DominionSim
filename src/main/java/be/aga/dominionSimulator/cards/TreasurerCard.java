package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomArtifact;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class TreasurerCard extends DomCard {
    public TreasurerCard() {
      super( DomCardName.Treasurer);
    }

    public void play() {
        owner.addAvailableCoins(3);
        ArrayList<DomCard> theTreasures = owner.getCardsFromHand(DomCardType.Treasure);
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman(theTreasures);
        } else {
            if (owner.getCurrentGame().getArtifactOwner(DomArtifact.Key)!=owner) {
                owner.getCurrentGame().giveArtifactTo(DomArtifact.Key, owner);
                return;
            }
            Collections.sort(theTreasures, SORT_FOR_TRASHING);
            if (!theTreasures.isEmpty() && theTreasures.get(0).getTrashPriority() <= DomCardName.Copper.getTrashPriority() && !owner.removingReducesBuyingPower(theTreasures.get(0))) {
                owner.trash(owner.removeCardFromHand(theTreasures.get(0)));
                return;
            } else {
                ArrayList<DomCard> theTrash = owner.getCurrentGame().getTrashedCards();
                Collections.sort(theTrash, SORT_FOR_TRASHING);
                for (int i = theTrash.size() - 1; i > 0; i--) {
                    if (theTrash.get(i).hasCardType(DomCardType.Treasure) &&
                      (theTrash.get(i).getTrashPriority() >= DomCardName.Gold.getTrashPriority() || owner.addingThisIncreasesBuyingPower(theTrash.get(i).getPotentialCurrencyValue()))) {
                        owner.gainInHand(owner.getCurrentGame().removeFromTrash(theTrash.get(i)));
                        return;
                    }
                }
            }
        }
    }

    private void handleHuman(ArrayList<DomCard> theTreasures) {
        //take the Key ?
        owner.setNeedsToUpdateGUI();
        if (owner.getCurrentGame().getArtifactOwner(DomArtifact.Key) != owner
         && owner.getEngine().getGameFrame().askPlayer("Gain the Key?", "Resolving " + this.getName().toString())) {
              owner.getCurrentGame().giveArtifactTo(DomArtifact.Key, owner);
              return;
        }

        //Trash a Treasure?
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : theTreasures) {
            theChooseFrom.add(theCard.getName());
        }
        if (!theChooseFrom.isEmpty()) {
          DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Don't trash");
          if (theChosenCard != null) {
              owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
              return;
          }
        }
        //Gain a Treasure from trash?
        ArrayList<DomCard> theTrash = owner.getCurrentGame().getTrashedCards();
        ArrayList<DomCard> theChooseFrom2 = new ArrayList<>();
        for (DomCard theCard : theTrash) {
            if (theCard.hasCardType(DomCardType.Treasure))
              theChooseFrom2.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        DomCard theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCardWithDomCard("Gain from trash?", theChooseFrom2, "Gain nothing");
        if (theChosenCard != null) {
            owner.gainInHand(owner.getCurrentGame().getBoard().removeFromTrash(theChosenCard));
        }
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}