package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class EstateCard extends DomCard {
    public EstateCard () {
      super( DomCardName.Estate);
    }
    
    @Override
    public int getTrashPriority() {
      if (owner!=null) {
    	if (owner.wantsToGainOrKeep(DomCardName.Estate)) {
//        	if (!owner.isInBuyPhase()) {
//        		//fix for Remodel
//        		owner.getCurrentGame().setBuyphase(true);
//        		if (!owner.wantsToGainOrKeep(DomCardName.Estate)){
//            	  owner.getCurrentGame().setBuyphase(false);
//        		  return 19;
//        		}
//        	}
        	return 35;
    	}
      }
      return super.getTrashPriority();
    }

    @Override
    public void play() {
        DomCard theAction = owner.getEstateTokenOn().getName().createNewCardInstance();
        theAction.setOwner(owner);
        theAction.setEstateCard(this);
        if (DomEngine.haveToLog) DomEngine.addToLog( owner + " plays "+ this + " as "+theAction);
        owner.removeCardFromPlay(this);
        owner.play(theAction);
    }

    @Override
    public int getDiscardPriority(int aActionsLeft) {
        if (owner!=null && owner.isEstateTokenPlaced())
            return owner.getEstateTokenOn().getDiscardPriority(aActionsLeft);
        if (aActionsLeft>0 && owner.getCardsInHand().contains(this)
    	&& !owner.getCardsFromHand(DomCardName.Baron).isEmpty() && owner.getCardsFromHand(DomCardName.Estate).size()==1)
    		return 29;
    	return super.getDiscardPriority(aActionsLeft);
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType!=DomCardType.Action)
          return super.hasCardType(aType);
        if (owner==null)
            return false;
        return owner.isEstateTokenPlaced();
    }

    @Override
    public int getPlayPriority() {
        if (owner.isEstateTokenPlaced())
            return owner.getEstateTokenOn().getName().getPlayPriority();
        return 0;
    }
}