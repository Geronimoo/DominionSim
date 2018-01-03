package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class Lost_CityCard extends DomCard {
    public Lost_CityCard() {
      super(DomCardName.Lost_City);
    }

    public void play() {
      owner.addActions( 2 );
      owner.drawCards( 2 );
    }

    @Override
    public void doWhenGained() {
        for (DomPlayer thePlayer : owner.getOpponents())
            thePlayer.drawCards(1);
    }
}