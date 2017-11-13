package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class PookaCard extends DomCard {
    public PookaCard() {
      super( DomCardName.Pooka);
    }

    public void play() {
      if (!trashTreasureFromHand())
    	  return;
      owner.drawCards(4);
    }
    
    private boolean trashTreasureFromHand() {
    	if (owner.getCardsFromHand(DomCardType.Treasure).isEmpty())
    		return false;
    	if (owner.isHumanOrPossessedByHuman()){
			ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
			for (DomCard theCard : owner.getCardsInHand()) {
				if (theCard.hasCardType(DomCardType.Treasure) && theCard.getName()!=DomCardName.Cursed_Gold)
					theChooseFrom.add(theCard.getName());
			}
			if (theChooseFrom.isEmpty())
				return false;
			DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash ?", theChooseFrom, "Don't trash");
			if (theChosenCard==null)
				return false;
			owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
			return true;
		}
    	ArrayList<DomCard> theCards = owner.getCardsFromHand(DomCardType.Treasure);
		Collections.sort(theCards, SORT_FOR_TRASHING);
		int i=0;
		while (i<theCards.size() && theCards.get(i++).getName()==DomCardName.Cursed_Gold);
		if (i==theCards.size())
			return false;
	    owner.trash(owner.removeCardFromHand(theCards.get(--i)));
		return true;
	}

	@Override
    public int getPlayPriority() {
    	if (owner.getCardsFromHand(DomCardType.Treasure).isEmpty())
    		return 10000;
    	return super.getPlayPriority();
    }
}