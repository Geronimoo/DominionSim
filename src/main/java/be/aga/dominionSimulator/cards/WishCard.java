package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class WishCard extends DomCard {
    public WishCard() {
      super( DomCardName.Wish);
    }

    public void play() {
      owner.addActions(1);
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
      } else {
          DomCardName theDesiredCard = owner.getDesiredCard(new DomCost(6, 0), false);
          if (owner.getNextActionToPlay() != null && owner.getNextActionToPlay().hasCardType(DomCardType.Terminal) && owner.getNextActionToPlay().hasCardType(DomCardType.Card_Advantage) && owner.actionsLeft == 1) {
              DomCardName theDesiredVillage = owner.getDesiredCard(DomCardType.Village, new DomCost(6, 0), false, false, null);
              if (theDesiredVillage != null)
                  theDesiredCard = theDesiredVillage;
          }
          if (theDesiredCard == null) {
              theDesiredCard = owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost(6, 0));
          }
          if (theDesiredCard != null)
              owner.gainInHand(theDesiredCard);
      }
        if (owner.getCardsInPlay().contains(this)) {
            owner.getCardsInPlay().remove(this);
            owner.returnToSupply(this);
        }
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
        theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (new DomCost(6,0).customCompare(theCard.getCost(owner.getCurrentGame()))>=0 )
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        owner.gainInHand(owner.getCurrentGame().takeFromSupply(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for "+this.getName().toString(), theChooseFrom, "Mandatory!")));
    }

    @Override
    public boolean wantsToBePlayed() {
        DomCardName theDesiredCard = owner.getDesiredCard(new DomCost( 6, 0), false);
        if (theDesiredCard==null)
            return false;
        return true;
    }

    @Override
    public int getPlayPriority() {
        if (!owner.getCardsFromHand(DomCardType.Terminal).isEmpty())
            return owner.getCardsFromHand(DomCardType.Terminal).get(0).getPlayPriority() - 1;
        return super.getPlayPriority();
    }
}