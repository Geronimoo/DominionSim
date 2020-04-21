package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import java.util.ArrayList;

public class StorytellerCard extends DomCard {
   protected static final Logger LOGGER = Logger.getLogger( StorytellerCard.class );
   static {
       LOGGER.setLevel( DomEngine.LEVEL );
       LOGGER.removeAllAppenders();
       if (DomEngine.addAppender)
           LOGGER.addAppender(new ConsoleAppender(new SimpleLayout()) );
   }

    public StorytellerCard() {
      super( DomCardName.Storyteller);
    }

    public void play() {
        owner.addActions(1);
        owner.addAvailableCoins(1);
        DomCard theCardToPlay;
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
        if (owner.getAvailableCoins()<owner.getDeckAndDiscardSize()) {
            int thePlayCount = 0;
            do {
                theCardToPlay = null;
                for (DomCard theCard : owner.getCardsInHand()) {
                    if (theCard.hasCardType(DomCardType.Treasure)) {
                        if (theCardToPlay == null || theCard.getPlayPriority()<theCardToPlay.getPlayPriority()) {
                            if (theCard.wantsToBePlayed())
                                theCardToPlay = theCard;
                        }
                    }
                }
                if (theCardToPlay!=null) {
                    owner.play(owner.removeCardFromHand(theCardToPlay));
                    thePlayCount++;
                }
            } while (theCardToPlay!=null && owner.getAvailableCoins()<owner.getDeckAndDiscardSize() && thePlayCount<3);
        }

        if (DomEngine.haveToLog) {
            if (owner.getPreviousPlayedCardName()!=null) {
              DomEngine.addToLog( owner + " plays " + (owner.getSameCardCount()+1)+" "+ owner.getPreviousPlayedCardName().toHTML()
                                 + (owner.getSameCardCount()>0 ? "s" : ""));
              owner.setPreviousPlayedCardName(null);
              owner.setSameCardCount(0);
            }
        }
        owner.drawCards(owner.getAvailableCoinsWithoutTokens());
        owner.setAvailableCoins(0);
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom;
        int theCount=0;
        DomCardName theCardToPlay;
        do {
            owner.setNeedsToUpdateGUI();
            theChooseFrom = new ArrayList<DomCardName>();
            for (DomCard theCard : owner.getCardsFromHand(DomCardType.Treasure))
                theChooseFrom.add(theCard.getName());
            if (theChooseFrom.isEmpty())
                break;
            theCardToPlay = owner.getEngine().getGameFrame().askToSelectOneCard("Select card to " + this.getName().toString(), theChooseFrom, "Stop playing Treasures!");
            if (theCardToPlay != null) {
                owner.play(owner.removeCardFromHand(owner.getCardsFromHand(theCardToPlay).get(0)));
                if (owner.previousPlayedCardName != null) {
                    DomEngine.addToLog(owner + " plays " + (owner.sameCardCount + 1) + " " + owner.previousPlayedCardName.toHTML()
                            + (owner.sameCardCount > 0 ? "s" : ""));
                    owner.previousPlayedCardName = null;
                    owner.sameCardCount = 0;
                }
            }
            theCount++;
        } while (theCount < 3 && theCardToPlay != null && !owner.getCardsFromHand(DomCardType.Treasure).isEmpty()) ;
        owner.drawCards(owner.getAvailableCoinsWithoutTokens());
        owner.setAvailableCoins(0);
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}