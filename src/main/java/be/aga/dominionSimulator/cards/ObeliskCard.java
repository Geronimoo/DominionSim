package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.SimpleLayout;


public class ObeliskCard extends DomCard {
    protected static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ObeliskCard.class);
    static {
        LOGGER.setLevel( DomEngine.LEVEL );
        LOGGER.removeAllAppenders();
        if (DomEngine.addAppender)
            LOGGER.addAppender(new ConsoleAppender(new SimpleLayout()) );
    }

    public ObeliskCard() {
      super( DomCardName.Obelisk);
    }

    public static int countVP(DomPlayer aPlayer) {
      DomCardName cardForObelisk = aPlayer.getCurrentGame().getObeliskChoice();
      if (cardForObelisk == null)
          return 0;
      return aPlayer.countInDeck(cardForObelisk)*2;
    }
}