package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class VassalCard extends DomCard {
    public VassalCard() {
      super( DomCardName.Vassal);
    }

    public void play() {
      owner.addAvailableCoins(2);
      if (owner.getDeckSize()==0)
    	return;
      DomCard theRevealedCard = owner.revealTopCards(1).get(0);
      if (theRevealedCard.hasCardType(DomCardType.Action) && theRevealedCard.wantsToBePlayed())
        owner.play(theRevealedCard);
      else
        owner.discard(theRevealedCard);
    }
}