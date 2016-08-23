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
      for (int i=theCardsToHandle.size()-1;i>=0;i--) {
    	DomCard theCard = theCardsToHandle.get(i);
    	if (!theCard.isTaggedByScheme()
//    	 && theCard.getDiscardPriority(1)>=20 
    	 && theCard.hasCardType(DomCardType.Action) 
    	 && theCard.discardAtCleanUp() 
    	 && (theCard.getName()!=DomCardName.Alchemist || owner.getCardsFromPlay(DomCardName.Potion).isEmpty())) {
    		theCard.addSchemeTag();
    		break;
    	}
      }
	}
}