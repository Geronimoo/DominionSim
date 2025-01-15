package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class Landing_PartyCard extends DomCard {
    public Landing_PartyCard() {
      super(DomCardName.Landing_Party);
    }

    public void play() {
      owner.addActions( 2 );
      owner.drawCards( 2 );
    }

    @Override
    public boolean mustStayInPlay() {
        return true;
    }
}