package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class GroomCard extends DomCard {
    public GroomCard() {
      super( DomCardName.Groom);
    }

    public void play() {
      DomCardName theDesiredCard=null;
      if (owner.isHumanOrPossessedByHuman()) {
          theDesiredCard=handleHumanPlayer();
      } else {
          if (!owner.getCardsFromHand(DomCardType.Action).isEmpty() && owner.getActionsLeft() == 0) {
              //if we have more actions in hand we probably want to play them so find a victory card to gain
              theDesiredCard = owner.getDesiredCard(DomCardType.Victory, new DomCost(4, 0), false, false, null);
          }
          if (theDesiredCard == null) {
              //we didn't have more actions in hand or no actions to gain, so just gain the card we want the most
              theDesiredCard = owner.getDesiredCard(new DomCost(4, 0), false);
          }
          if (theDesiredCard == null) {
              //possible if card was throne-roomed
              theDesiredCard = owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost(4, 0));
          }
      }
      if (theDesiredCard==null)
    	return;
      owner.gain(theDesiredCard);
      if (theDesiredCard.hasCardType(DomCardType.Treasure))
      	owner.gain(DomCardName.Silver);
      if (theDesiredCard.hasCardType(DomCardType.Action))
      	owner.gain(DomCardName.Horse);
      if (theDesiredCard.hasCardType(DomCardType.Victory)) {
          owner.drawCards(1);
          owner.addActions(1);
      }
    }

    private DomCardName handleHumanPlayer() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (new DomCost(4,0).customCompare(theCard.getCost(owner.getCurrentGame()))>=0 && owner.getCurrentGame().countInSupply(theCard)>0 )
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return null;
        return owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for "+this.getName().toString(), theChooseFrom, "Mandatory!");
    }

    @Override
    public boolean wantsToBePlayed() {
       return owner.getDesiredCard(new DomCost( 4, 0), false) != null ;
    }
    @Override
    public int getPlayPriority() {
        if (wantsToBePlayed() && owner.getDesiredCard(new DomCost(4, 0), false).hasCardType(DomCardType.Victory))
            return 14;
        return super.getPlayPriority();
    }

}