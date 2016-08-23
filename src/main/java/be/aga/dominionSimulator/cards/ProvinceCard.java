package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class ProvinceCard extends DomCard {
    public ProvinceCard () {
      super( DomCardName.Province);
    }
    
    @Override
    public int getTrashPriority() {
      return 60;
    }
    @Override
    public int getDiscardPriority(int aActionsLeft) {
    	if (aActionsLeft>0
    	 && owner.getCardsFromHand(DomCardName.Tournament).size()>0 
    	 && owner.getCardsFromHand(DomCardName.Province).size()==1)
    		return 40;
    	return super.getDiscardPriority(aActionsLeft);
    }
    
    @Override
    public void doWhenGained() {
    	handleFoolsGold();
    }
    
	private void handleFoolsGold() {
	  for (DomPlayer thePlayer : owner.getOpponents()) {
		//first check if player has a Fool's Gold in hand
		if (thePlayer.getCardsFromHand(DomCardName.Fool$s_Gold).isEmpty())
		  continue;
		//if player has some Gold in deck already it probably means he prefers 'real' Gold
		if (thePlayer.countInDeck(DomCardName.Gold)==0)
			continue;
		//if player has 2 or more Fool's Golds in hand, he'll probably be happy to keep them
		if (thePlayer.getCardsFromHand(DomCardName.Fool$s_Gold).size()>1)
			continue;
        while (!thePlayer.getCardsFromHand(DomCardName.Fool$s_Gold).isEmpty()){
          DomCard theGold = owner.getCurrentGame().takeFromSupply( DomCardName.Gold);
          if (theGold==null) 
        	  break;
          DomCard theFool$s_GoldCard = thePlayer.getCardsFromHand(DomCardName.Fool$s_Gold).get(0);
          thePlayer.trash(thePlayer.removeCardFromHand(theFool$s_GoldCard));
          thePlayer.gainOnTopOfDeck(theGold);
        }
      }
	}
}