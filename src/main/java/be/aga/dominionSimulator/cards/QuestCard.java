package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class QuestCard extends DomCard {

    public QuestCard() {
        super(DomCardName.Quest);
    }

    public void play() {
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }

        if (owner.getCardsInHand().size() >= 6) {
            while (!owner.getCardsInHand().isEmpty()) {
                owner.discardFromHand(owner.getCardsInHand().get(0));
            }
            owner.gain(DomCardName.Gold);
            return;
        }
        if (owner.getCardsFromHand(DomCardName.Curse).size() >= 2) {
            owner.discardFromHand(owner.getCardsFromHand(DomCardName.Curse).get(0));
            owner.discardFromHand(owner.getCardsFromHand(DomCardName.Curse).get(0));
            owner.gain(DomCardName.Gold);
            return;
        }
        if (!owner.getCardsFromHand(DomCardType.Attack).isEmpty()) {
            owner.discardFromHand(owner.getCardsFromHand(DomCardType.Attack).get(0));
            owner.gain(DomCardName.Gold);
            return;
        }
    }

    private void handleHuman() {
        ArrayList<String> theOptions = new ArrayList<String>();
        theOptions.add("Discard an attack card");
        theOptions.add("Discard 2 Curses");
        theOptions.add("Discard 6 cards");
        int theChoice = owner.getEngine().getGameFrame().askToSelectOption("Select for Quest", theOptions, "Mandatory!");
        if (theChoice == 0 && !owner.getCardsFromHand(DomCardType.Attack).isEmpty()) {
            ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
            for (DomCard theCard : owner.getCardsInHand()) {
                if (theCard.hasCardType(DomCardType.Attack))
                    theChooseFrom.add(theCard.getName());
            }
            DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Discard", theChooseFrom, "Mandatory!");
            if (theChosenCard != null) {
                owner.discardFromHand(owner.getCardsFromHand(theChosenCard).get(0));
                owner.gain(DomCardName.Gold);
            }
        }
        if (theChoice == 1 && owner.getCardsFromHand(DomCardType.Curse).size() >= 2) {
            owner.discardFromHand(owner.getCardsFromHand(DomCardName.Curse).get(0));
            owner.discardFromHand(owner.getCardsFromHand(DomCardName.Curse).get(0));
            owner.gain(DomCardName.Gold);
        }
        if (theChoice == 2 && owner.getCardsInHand().size() >= 6) {
            ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
            owner.getEngine().getGameFrame().askToSelectCards("<html>Choose to discard</html>", owner.getCardsInHand(), theChosenCards, 6);
            for (int i = theChosenCards.size() - 1; i >= 0; i--) {
                for (DomCard theCard : owner.getCardsInHand()) {
                    if (theChosenCards.get(i).getName() == theCard.getName()) {
                        owner.discardFromHand(theCard);
                        break;
                    }
                }
            }
            owner.gain(DomCardName.Gold);
        }
    }
}