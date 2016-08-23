package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.SimpleLayout;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
      DomCardName cardForObelisk = null;
      for (DomPlayer thePlayer : aPlayer.getCurrentGame().getPlayers()) {
          if (thePlayer.getObeliskChoice()!=null) {
              cardForObelisk=thePlayer.getCardForObelisk();
              break;
          }
      }
      if (cardForObelisk == null)
          return 0;
      return aPlayer.countInDeck(cardForObelisk)*2;
    }
}