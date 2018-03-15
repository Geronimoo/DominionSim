package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class ExorcistCard extends DomCard {
    public ExorcistCard() {
      super( DomCardName.Exorcist);
    }

    public void play() {
      if (owner.getCardsInHand().isEmpty())
          return;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
      DomCard theCardToTrash = owner.removeCardFromHand(owner.getCardsInHand().get(0));
      owner.trash(theCardToTrash);
      if (theCardToTrash.getCost(owner.getCurrentGame()).customCompare(DomCardName.Ghost.getCost(owner.getCurrentGame()))>0 && owner.getCurrentGame().countInSupply(DomCardName.Ghost)>0) {
          owner.gain(DomCardName.Ghost);
      }
      if (theCardToTrash.getCost(owner.getCurrentGame()).customCompare(DomCardName.Imp.getCost(owner.getCurrentGame()))>0 && owner.getCurrentGame().countInSupply(DomCardName.Imp)>0) {
         owner.gain(DomCardName.Imp);
      }
      if (theCardToTrash.getCost(owner.getCurrentGame()).customCompare(DomCardName.Will_o$_Wisp.getCost(owner.getCurrentGame()))>0 && owner.getCurrentGame().countInSupply(DomCardName.Will_o$_Wisp)>0) {
         owner.gain(DomCardName.Will_o$_Wisp);
      }
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand()) {
            theChooseFrom.add(theCard.getName());
        }
        DomCardName theCardToTrash = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Mandatory!");
        owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theCardToTrash).get(0)));
        theChooseFrom=new ArrayList<>();
        if (theCardToTrash.getCost(owner.getCurrentGame()).customCompare(DomCardName.Ghost.getCost(owner.getCurrentGame()))>0 && owner.getCurrentGame().countInSupply(DomCardName.Ghost)>0) {
            theChooseFrom.add(DomCardName.Ghost);
        }
        if (theCardToTrash.getCost(owner.getCurrentGame()).customCompare(DomCardName.Imp.getCost(owner.getCurrentGame()))>0 && owner.getCurrentGame().countInSupply(DomCardName.Imp)>0) {
            theChooseFrom.add(DomCardName.Imp);
        }
        if (theCardToTrash.getCost(owner.getCurrentGame()).customCompare(DomCardName.Will_o$_Wisp.getCost(owner.getCurrentGame()))>0 && owner.getCurrentGame().countInSupply(DomCardName.Will_o$_Wisp)>0) {
            theChooseFrom.add(DomCardName.Will_o$_Wisp);
        }
        if (theChooseFrom.isEmpty())
            return;
        owner.gain(owner.getEngine().getGameFrame().askToSelectOneCard("Receive a Spirit", theChooseFrom, "Mandatory!"));
    }

    @Override
    public boolean wantsToBePlayed() {
        Collections.sort( owner.getCardsInHand() , SORT_FOR_TRASHING);
        if (owner.getCardsInHand().get(0).getTrashPriority()<= DomCardName.Copper.getTrashPriority())
            return true;
        return false;
    }
}