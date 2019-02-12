package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class Opulent_CastleCard extends DomCard {
    public Opulent_CastleCard() {
      super( DomCardName.Opulent_Castle);
    }

    public void play() {
        int amount = 0;
        if (owner.getCardsFromHand(DomCardType.Victory).isEmpty())
            return;
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
        while (!owner.getCardsFromHand(DomCardType.Victory).isEmpty()) {
            owner.discardFromHand(owner.getCardsFromHand(DomCardType.Victory).get(0));
            amount+=2;
        }
        if (amount>0)
            owner.addAvailableCoins( amount );
    }

    private void handleHuman() {
        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
        owner.getEngine().getGameFrame().askToSelectCards("<html>Discard for +$2</html>" , owner.getCardsFromHand(DomCardType.Victory), theChosenCards, 0);
        for (DomCard theCard : theChosenCards) {
            owner.discardFromHand(theCard.getName());
            owner.addAvailableCoins(2);
        }
    }

    @Override
    public int getDiscardPriority(int aActionsLeft) {
    	if (aActionsLeft>0 && owner.getCardsInHand().contains(this) 
    	&& owner.getCardsFromHand(DomCardName.Opulent_Castle).size()==1 && owner.getCardsFromHand(DomCardType.Victory).size()>1)
    		return 29;
    	return super.getDiscardPriority(aActionsLeft);
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}