package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.*;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class BlockadeCard extends DomCard {
    private ArrayList<DomCard> myBlockadedCards=new ArrayList<>();

    public BlockadeCard() {
      super( DomCardName.Blockade);
    }

    public void play() {
        DomCardName theDesiredCard = owner.getDesiredCard(new DomCost(4, 0), false);
        if (theDesiredCard == null) {
            //possible to get here if card was throne-roomed
            theDesiredCard = owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost(4, 0));
        }
        if (theDesiredCard != null) {
            owner.gain(theDesiredCard);
            for (DomCard card : owner.getCardsFromDiscard()) {
                if (card.getName() == theDesiredCard) {
                    havenAway(card);
                    owner.removeCardFromDiscard(card);
                    owner.getCurrentGame().putBlockadeTokenOn(theDesiredCard);
                    break;
                }
            }
        }
    }

    @Override
    public void resolveDuration() {
        for (DomCard theCard : myBlockadedCards) {
            owner.putInHand(theCard);
            owner.getCurrentGame().removeEmbargoTokenFrom(theCard.getName());
        }
        owner.showHand();
        myBlockadedCards.clear();
    }

    private void havenAway(DomCard aCard) {
        myBlockadedCards.add(aCard);
        if (DomEngine.haveToLog ) DomEngine.addToLog( owner + " puts " + myBlockadedCards + " aside");
    }

    @Override
    public boolean wantsToBePlayed() {
       return owner.getDesiredCard(new DomCost( 4, 0), false) != null ;
    }

    @Override
    public int getPlayPriority() {
        if (owner.getDrawDeckSize()==0 && owner.getActionsAndVillagersLeft()>1 && !owner.getCardsFromHand(DomCardType.Cycler).isEmpty())
            return 1;
        return super.getPlayPriority();
    }

    public void cleanVariablesFromPreviousGames() {
        myBlockadedCards.clear();
    }

    @Override
    public boolean durationFailed() {
        return myBlockadedCards.isEmpty();
    }

}