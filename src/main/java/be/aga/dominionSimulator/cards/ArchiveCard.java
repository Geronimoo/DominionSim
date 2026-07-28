package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import java.util.ArrayList;
import java.util.Collections;

public class ArchiveCard extends DomCard {
    private ArrayList<DomCard> myArchivedCards = new ArrayList<DomCard>();

    public ArchiveCard() {
      super( DomCardName.Archive);
    }

    public void play() {
      owner.addActions(1);
      myArchivedCards.addAll(owner.revealTopCards(3));
      if (myArchivedCards.isEmpty())
          return;
      Collections.sort(myArchivedCards,SORT_FOR_DISCARDING);
      addCardToHandFromArchive();
    }

    private void addCardToHandFromArchive() {
        if (owner.isHumanOrPossessedByHuman()) {
            ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
            for (DomCard theCard : myArchivedCards) {
                theChooseFrom.add(theCard.getName());
            }
            DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Take in hand", theChooseFrom, "Mandatory!");
            for (DomCard theCard : myArchivedCards) {
                if (theCard.getName()==theChosenCard) {
                    owner.addCardToHand(myArchivedCards.remove(myArchivedCards.indexOf(theCard)));
                    break;
                }
            }
        } else {
            owner.addCardToHand(myArchivedCards.remove(myArchivedCards.size() - 1));
        }
    }

    public void resolveDuration() {
      if (!myArchivedCards.isEmpty())
        addCardToHandFromArchive();
    }

    @Override
    public boolean mustStayInPlay() {
        return !myArchivedCards.isEmpty();
    }

    @Override
    public void cleanVariablesFromPreviousGames() {
        myArchivedCards.clear();
    }
}