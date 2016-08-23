package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class VagrantCard extends DomCard {
    public VagrantCard() {
      super( DomCardName.Vagrant);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
      if (owner.getDeckSize()==0)
    	return;
      DomCard theRevealedCard = owner.revealTopCards(1).get(0);
	  if (theRevealedCard.hasCardType(DomCardType.Ruins) || theRevealedCard.hasCardType(DomCardType.Curse) || theRevealedCard.hasCardType(DomCardType.Victory) || theRevealedCard.hasCardType(DomCardType.Shelter)) {
        owner.putInHand(theRevealedCard);
	  }else{
        owner.putOnTopOfDeck(theRevealedCard);
      }
    }
}