package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class HarbingerCard extends DomCard {
    public HarbingerCard() {
      super( DomCardName.Harbinger);
    }

    public void play() {
        owner.addActions(1);
        owner.drawCards(1);
        ArrayList<DomCard> theDiscard = owner.getCardsFromDiscard();
        if (theDiscard.isEmpty()) {
            if (DomEngine.haveToLog) DomEngine.addToLog("Discard is empty");
            return;
        }
        if (owner.isHumanOrPossessedByHuman()) {
            handleHumanPlayer(theDiscard);
        } else {
            if (owner.getNextActionToPlay()==null) {
                Collections.sort(theDiscard, SORT_FOR_DISCARDING);
                if (theDiscard.get(theDiscard.size() - 1).getDiscardPriority(1) > DomCardName.Copper.getDiscardPriority(1))
                    owner.putOnTopOfDeck(owner.removeCardFromDiscard(theDiscard.get(theDiscard.size() - 1)));
                else if (DomEngine.haveToLog)
                    DomEngine.addToLog(owner + " has no good card in discard to put on top of deck");
            } else {
                if (owner.getNextActionToPlay().hasCardType(DomCardType.Terminal) && owner.getNextActionToPlay().hasCardType(DomCardType.Card_Advantage)){
                    for (DomCard card:theDiscard) {
                        if (card.getName()==DomCardName.Gold){
                            owner.putOnTopOfDeck(owner.removeCardFromDiscard(DomCardName.Gold));
                            break;
                        }
                        if (card.getName()==DomCardName.Silver){
                            owner.putOnTopOfDeck(owner.removeCardFromDiscard(DomCardName.Silver));
                            break;
                        }
                        if (card.getName()==DomCardName.Copper){
                            owner.putOnTopOfDeck(owner.removeCardFromDiscard(DomCardName.Copper));
                            break;
                        }
                    }
                }
            }
        }
    }

    private void handleHumanPlayer(ArrayList<DomCard> theDiscard) {
        owner.setNeedsToUpdateGUI();
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : theDiscard)
           theChooseFrom.add(theCard.getName());
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Select card for " + this.getName().toString(), theChooseFrom, "Don't use");
        for (DomCard theCard:theDiscard) {
            if (theCard.getName()==theChosenCard) {
                owner.putOnTopOfDeck(owner.removeCardFromDiscard(theCard));
                break;
            }
        }
    }
}