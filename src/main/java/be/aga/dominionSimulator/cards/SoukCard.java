package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPhase;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

import java.util.ArrayList;
import java.util.Collections;

public class SoukCard extends DomCard {
    public SoukCard() {
      super( DomCardName.Souk);
    }

    public void play() {
      int money = 7 - owner.getCardsInHand().size();
      if (money<0)
          money=0;
      owner.addAvailableCoins(money);
      owner.addAvailableBuys(1);
    }

    @Override
    public void doWhenGained() {
        ArrayList<DomCard> cardsInHand = owner.getCardsInHand();
        if (cardsInHand.isEmpty())
            return;
        if (cardsInHand.size()==1){
            if (cardsInHand.get(0).getTrashPriority()<16 && owner.getTotalMoneyInDeck()>5){
                owner.trash(owner.removeCardFromHand( cardsInHand.get(0)));
            }
            return;
        }
        Collections.sort(cardsInHand,SORT_FOR_TRASHING);
        if (cardsInHand.get(0).getTrashPriority() < 20 && owner.getTotalMoneyInDeck()>5) {
            owner.trash(owner.removeCardFromHand(cardsInHand.get(0)));
        }
        if (cardsInHand.get(0).getTrashPriority() < 20 && owner.getTotalMoneyInDeck()>5) {
            owner.trash(owner.removeCardFromHand(cardsInHand.get(0)));
        }
    }

    @Override
    public boolean wantsToBePlayed() {
        if (owner.wants(DomCardName.Practice) && owner.getPhase()==DomPhase.Action && owner.getTotalPotentialCurrency().getCoins()>=3)
            return false;
        return true;
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }
}