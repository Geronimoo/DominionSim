package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class SchemeCard extends DomCard {
    public SchemeCard () {
      super( DomCardName.Scheme);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
    }
    
	public void maybeAddTagFor(ArrayList<DomCard> theCardsToHandle) {
      if (owner.isHumanOrPossessedByHuman()) {
      	handleHuman(theCardsToHandle);
      	return;
	  }
      for (int i=theCardsToHandle.size()-1;i>=0;i--) {
    	DomCard theCard = theCardsToHandle.get(i);
    	if (!theCard.isTaggedByScheme()
//    	 && theCard.getDiscardPriority(1)>=20 
    	 && theCard.hasCardType(DomCardType.Action) 
    	 && theCard.discardAtCleanUp() 
    	 && (theCard.getName()!=DomCardName.Alchemist || owner.getCardsFromPlay(DomCardName.Potion).isEmpty())) {
    		int theTerminalCount = 0;
    		int theVillageCount = 0;
    		for (DomCard card:owner.getCardsInPlay()) {
    			theTerminalCount+=card.hasCardType(DomCardType.Terminal) && card.isTaggedByScheme()? 1 : 0;
    			theVillageCount+=card.hasCardType(DomCardType.Village) && card.isTaggedByScheme() ? 1 : 0;
			}
			if (!theCard.hasCardType(DomCardType.Terminal)&&theTerminalCount<=theVillageCount) {
				theCard.addSchemeTag();
				break;
			} else {
    			if( theTerminalCount<=theVillageCount) {
					theCard.addSchemeTag();
					break;
				}else  {
					if (theCard.hasCardType(DomCardType.Village)) {
						theCard.addSchemeTag();
						break;
					}
				}
			}
    	}
      }
	}

	private void handleHuman(ArrayList<DomCard> theCardsToHandle) {
		ArrayList<DomCard> chooseFrom = new ArrayList<DomCard>();
		for (DomCard theCard : theCardsToHandle) {
			if (theCard.hasCardType(DomCardType.Action) && !theCard.isTaggedByScheme())
				chooseFrom.add(theCard);
		}
		if (chooseFrom.isEmpty())
			return;
		DomCard theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCardWithDomCard("Put on top?" + this.getName().toString(), chooseFrom, "Don't put anything on top");
		if (theChosenCard!=null) {
			theChosenCard.addSchemeTag();
		}
	}
}