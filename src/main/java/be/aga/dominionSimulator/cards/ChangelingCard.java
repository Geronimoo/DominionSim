package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChangelingCard extends DomCard {
    public ChangelingCard() {
      super( DomCardName.Changeling);
    }

    public void play() {
        DomPlayer theOwner = owner;
        owner.trash(owner.removeCardFromPlay(this));
        List<DomCard> theCardsInPlay = new ArrayList<>();
        theCardsInPlay.addAll(theOwner.getCardsInPlay());
        Collections.sort(theCardsInPlay,SORT_FOR_TRASHING);
        if (theOwner.isHumanOrPossessedByHuman() && !theOwner.getCardsInPlay().isEmpty()) {
            DomCard theChosenCard = theOwner.getEngine().getGameFrame().askToSelectOneCardWithDomCard("Gain a copy of...", theOwner.getCardsInPlay(), "Mandatory!");
            theOwner.gain(theChosenCard.getName());
        } else {
            int i = theCardsInPlay.size() - 1;
            while (i > 0 && theOwner.getCurrentGame().countInSupply(theCardsInPlay.get(i).getName()) == 0 || !theOwner.wantsToGainOrKeep(theCardsInPlay.get(i).getName()))
                i--;
            if (i == 0)
                theOwner.gain(theCardsInPlay.get(theCardsInPlay.size() - 1));
            else
                theOwner.gain(theCardsInPlay.get(i));
        }
    }
    
    @Override
    public boolean wantsToBePlayed() {
       for (DomCard theCard : owner.getCardsInPlay()){
           if (theCard.getTrashPriority()> DomCardName.Silver.getTrashPriority())
               return true;
       }
       return false;
    }
}