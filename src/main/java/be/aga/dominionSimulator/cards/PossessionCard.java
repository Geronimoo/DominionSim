package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class PossessionCard extends DomCard {
    public PossessionCard () {
      super( DomCardName.Possession);
    }

    public void play() {
    	if (!owner.getOpponents().isEmpty())
    	  owner.getOpponents().get(0).addPossessionTurn(owner);
    }
    
    //handling is done mostly in DomPlayer and DomDeck
}