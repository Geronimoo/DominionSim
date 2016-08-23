package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class TransmuteCard extends DomCard {
    public TransmuteCard () {
      super( DomCardName.Transmute);
    }

    public void play() {
    	DomCard theCardToTrash = findCardToTrash();
        if (theCardToTrash==null) {
	    	//possible if played with Throne Room/KC
	        if (owner.getCardsInHand().isEmpty())
	          return;
	        theCardToTrash=owner.getCardsInHand().get(0);
        }
        owner.trash(owner.removeCardFromHand(theCardToTrash));
        if (theCardToTrash.hasCardType(DomCardType.Action)
         && owner.getCurrentGame().countInSupply(DomCardName.Duchy)>0) {
           owner.gain(DomCardName.Duchy);
        }
        if (theCardToTrash.hasCardType(DomCardType.Treasure)
         && owner.getCurrentGame().countInSupply(DomCardName.Transmute)>0) {
           owner.gain(DomCardName.Transmute);
        }
        if (theCardToTrash.hasCardType(DomCardType.Victory)
          && owner.getCurrentGame().countInSupply(DomCardName.Gold)>0) {
            owner.gain(DomCardName.Gold);
        }
    }
    
    private DomCard findCardToTrash() {
        ArrayList<DomCard> cardsInHand = owner.getCardsInHand();
        if (cardsInHand.isEmpty())
        	return null;
        DomCard theCardToTrash = null;
        Collections.sort(cardsInHand,SORT_FOR_TRASHING);
        //if it's time to get the green cards, find an action first
        if (owner.wants(DomCardName.Duchy)
         && !owner.getCardsFromHand(DomCardType.Action).isEmpty()){
        	theCardToTrash = owner.getCardsFromHand(DomCardType.Action).get(0);
        }
        if (theCardToTrash==null){
          theCardToTrash=cardsInHand.get(0);
        }
        if (theCardToTrash.getTrashPriority()>=20
          || owner.removingReducesBuyingPower(theCardToTrash))
        	return null;
        return theCardToTrash;
	}

	@Override
    public boolean wantsToBePlayed() {
		owner.getCardsInHand().remove(this);
		boolean foundCardToTrash= findCardToTrash()!=null;
	    owner.getCardsInHand().add(this);
    	return foundCardToTrash;
    }
}