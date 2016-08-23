package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class RaidCard extends DomCard {

	public RaidCard() {
      super( DomCardName.Raid);
    }

    public void play() {
      for (DomPlayer thePlayer : owner.getOpponents()) {
          thePlayer.setMinusOneCardToken();
      }
      for (int i=0;i<owner.getCardsFromPlay(DomCardName.Silver).size();i++) {
          owner.gain(DomCardName.Silver);
      }
    }
}