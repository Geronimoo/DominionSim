package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class IronworksCard extends DomCard {
    public IronworksCard () {
      super( DomCardName.Ironworks);
    }

    public void play() {
      DomCardName theDesiredCard=null;
      if (!owner.getCardsFromHand(DomCardType.Action).isEmpty() && owner.getActionsLeft() == 0){
    	//if we have more actions in hand we probably want to play them so find an action card to gain
    	theDesiredCard = owner.getDesiredCard(DomCardType.Action, new DomCost( 4, 0) , false, false, null);
      }
      if (theDesiredCard==null) {
    	//we didn't have more actions in hand or no actions to gain, so just gain the card we want the most
        theDesiredCard = owner.getDesiredCard(new DomCost( 4, 0), false);
      }
      if (theDesiredCard==null) {
        //possible if card was throne-roomed
        theDesiredCard=owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost(4, 0));
      }
      if (theDesiredCard==null)
    	return;
      owner.gain(theDesiredCard);
      if (theDesiredCard.hasCardType(DomCardType.Treasure))
      	owner.addAvailableCoins(1);
      if (theDesiredCard.hasCardType(DomCardType.Action))
      	owner.addActions(1);
      if (theDesiredCard.hasCardType(DomCardType.Victory))
    	owner.drawCards(1);
    }
    
    @Override
    public boolean wantsToBePlayed() {
       return owner.getDesiredCard(new DomCost( 4, 0), false) != null ;
    }
    @Override
    public int getPlayPriority() {
    	if (wantsToBePlayed() && owner.getDesiredCard(new DomCost( 4, 0), false).hasCardType(DomCardType.Action))
    		return 14;
    	return super.getPlayPriority();
    }
}