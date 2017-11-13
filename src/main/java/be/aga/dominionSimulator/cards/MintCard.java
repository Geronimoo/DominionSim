package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class MintCard extends DomCard {
    public MintCard () {
      super( DomCardName.Mint);
    }

    public void play() {
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      DomCardName theCardToCopy=findCardToCopy();
      if (theCardToCopy!=null) {
        if (DomEngine.haveToLog) 
          DomEngine.addToLog( owner+ " reveals " +theCardToCopy + " from hand");
    	owner.gain(theCardToCopy);
      } else {
        if (DomEngine.haveToLog) 
          DomEngine.addToLog( " but does not find a suitable card to Mint");
      }
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand()) {
            if (theCard.hasCardType(DomCardType.Treasure))
                theChooseFrom.add(theCard.getName());
        }
        if (theChooseFrom.isEmpty())
            return;
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Mint", theChooseFrom, "Mandatory!");
        DomEngine.addToLog( owner+ " reveals " +theChosenCard.toHTML()+ " from hand");
        owner.gain(owner.getCardsFromHand(theChosenCard).get(0).getName());
    }

    private DomCardName findCardToCopy() {
    	for (DomBuyRule buyRule : owner.getBuyRules()){
    	  DomCardName theCard = buyRule.getCardToBuy();
    	  if (theCard.hasCardType(DomCardType.Treasure)
    	   && owner.wants(theCard) 
    	   && !owner.getCardsFromHand(theCard).isEmpty()) 
            return theCard;
    	}
		return null;
    }
    
	@Override
    public boolean wantsToBePlayed() {
      return findCardToCopy()!=null;
    }
}