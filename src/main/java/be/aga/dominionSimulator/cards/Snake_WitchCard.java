package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class Snake_WitchCard extends DomCard {
    public Snake_WitchCard() {
      super( DomCardName.Snake_Witch);
    }

    public void play() {
      owner.addActions(1);
	  owner.drawCards(1);
      owner.revealHand();
      if (DomPlayer.getMultiplesInHand(this).isEmpty() && owner.getCurrentGame().countInSupply(DomCardName.Curse)>0) {
		  for (DomPlayer thePlayer : owner.getOpponents()) {
			  if (!thePlayer.checkDefense() && !owner.getCardsFromPlay(this.getName()).isEmpty()) {
				  thePlayer.gain(DomCardName.Curse);
				  owner.removeCardFromPlay(this);
				  owner.returnToSupply(this);
				  return;
			  }
		  }
	  }

    }

	@Override
	public int getPlayPriority() {
		//play it as soon as possible if we have no multiples
		if (DomPlayer.getMultiplesInHand(this).isEmpty())
		  return 0;
		//if we have multiples, but more than 1 action left wait to play it as long as possible
		if (owner.getActionsAndVillagersLeft()>1)
		  return 1000;
		//if we have multiples, but only 1 action left, we're going to wait until the other card gets played
		ArrayList<DomCard> theCards = new ArrayList<DomCard>();
		theCards.addAll(owner.getCardsInHand());
	    for (DomCard theCard : theCards) {
		   if (theCard==this || theCard.getName()==DomCardName.Menagerie)
			  continue;
		   if (theCard.hasCardType(DomCardType.Action)&&!theCard.hasCardType(DomCardType.Terminal)&&theCard.getName()!=DomCardName.Shanty_Town&&theCard.wantsToBePlayed())
//				   && theCard.wantsToBePlayed())
			  return 1000;
	    }
	    //we still haven't found a way to let us draw 3 so we just play the Menagerie as a Great Hall...
	    return super.getPlayPriority();
	}

}