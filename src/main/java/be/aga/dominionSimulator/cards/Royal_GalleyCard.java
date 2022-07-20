package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class Royal_GalleyCard extends DomCard {
    private ArrayList<DomCard> myHavenedCards = new ArrayList<DomCard>();

    public Royal_GalleyCard() {
      super( DomCardName.Royal_Galley);
    }

    public void play() {
      owner.drawCards(1);
      if (owner.getCardsInHand().isEmpty())
    	  return;
      DomCard theChosenCard = null;
      if (owner.isHumanOrPossessedByHuman()) {
          theChosenCard=handleHuman();
          return;
      } else {
          ArrayList<DomCard> theActionsToConsider = owner.getCardsFromHand(DomCardType.Action);
          if (theActionsToConsider.isEmpty())
              return;
          Collections.sort(theActionsToConsider, DomCard.SORT_FOR_PLAYING);
          for (DomCard card : theActionsToConsider) {
              if (!card.hasCardType(DomCardType.Duration) && card.wantsToBePlayed()) {
                  theChosenCard = card;
                  break;
              }
          }
      }
      if (theChosenCard==null) {
          if (DomEngine.haveToLog)
              DomEngine.addToLog(owner + " has nothing to play by " + this.getName().toHTML() + "!");
      } else {
          owner.play(owner.removeCardFromHand(theChosenCard));
          for (DomCard domCard : owner.getCardsFromPlay(theChosenCard.getName())) {
              if (domCard==theChosenCard) {
                  myHavenedCards.add(owner.removeCardFromPlay(theChosenCard));
                  if (DomEngine.haveToLog ) DomEngine.addToLog( owner + " sets " + myHavenedCards + " aside");
                  break;
              }
          }
      }
    }

    private DomCard handleHuman() {
        owner.setNeedsToUpdateGUI();
        ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand()) {
            if (!theCard.hasCardType(DomCardType.Duration))
              theChooseFrom.add(theCard.getName());
        }
        if (theChooseFrom.isEmpty())
            return null;
        return owner.getCardsFromHand(owner.getEngine().getGameFrame().askToSelectOneCard("Play:" + this.getName().toString(), theChooseFrom, "Mandatory!")).get(0);
    }

    public void resolveDuration() {
      for (DomCard theCard : myHavenedCards) {
          owner.play(theCard);
      }
      myHavenedCards.clear();
    }

    public void cleanVariablesFromPreviousGames() {
        myHavenedCards.clear();
    }

    @Override
    public boolean durationFailed() {
        return myHavenedCards.isEmpty();
    }

    @Override
    public int getPlayPriority() {
        return 0;
    }

}