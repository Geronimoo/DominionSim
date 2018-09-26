package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class CobblerCard extends DomCard {
    public CobblerCard() {
      super( DomCardName.Cobbler);
    }

    public void resolveDuration() {
      if (owner.isHumanOrPossessedByHuman()) {
          handleHumanPlayer();
      }else {
          DomCardName theDesiredCard=null;
          if (owner.getProbableActionsLeft()<0
                  ||(owner.getProbableActionsLeft()==0 && !owner.getCardsFromHand(DomCardType.Card_Advantage).isEmpty())) {
              theDesiredCard = owner.getDesiredCard(DomCardType.Village, new DomCost(4, 0), false, false, null);
          }
          if (theDesiredCard == null) {
              theDesiredCard = owner.getDesiredCard(new DomCost(4, 0), false);
          }
          if (theDesiredCard == null) {
              //possible to get here if card was throne-roomed
              theDesiredCard = owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost(4, 0));
          }
          if (theDesiredCard != null)
              owner.gainInHand(theDesiredCard);
      }
    }

    private void handleHumanPlayer() {
        owner.setNeedsToUpdateGUI();
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (new DomCost(4,0).customCompare(theCard.getCost(owner.getCurrentGame()))>=0 && owner.getCurrentGame().countInSupply(theCard)>0 )
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        owner.gainInHand(owner.getCurrentGame().takeFromSupply(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for "+this.getName().toString(), theChooseFrom, "Mandatory!")));
    }

    @Override
    public boolean wantsToBePlayed() {
       return owner.getDesiredCard(new DomCost( 4, 0), false) != null ;
    }
}