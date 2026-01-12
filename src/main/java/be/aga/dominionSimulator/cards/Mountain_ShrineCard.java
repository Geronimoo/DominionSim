package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class Mountain_ShrineCard extends DomCard {
    public Mountain_ShrineCard() {
      super( DomCardName.Mountain_Shrine);
    }

    public void play() {
        owner.addSunForProphecy(1);
        owner.addAvailableCoins(2);
        Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
        owner.trash(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
        for (DomCard theCard : owner.getCurrentGame().getTrashedCards()){
            if (theCard.hasCardType(DomCardType.Action)) {
                owner.drawCards(2);
                break;
            }
        }
    }
    @Override
    public boolean wantsToBePlayed() {
        //empty hand possible with Vassal
        if (owner.getCardsInHand().size()<=1)
            return true;
        Collections.sort( owner.getCardsInHand() , SORT_FOR_TRASHING);
        if (owner.getCardsInHand().get(0).getTrashPriority()<= DomCardName.Copper.getTrashPriority())
            return true;
        return false;
    }
}