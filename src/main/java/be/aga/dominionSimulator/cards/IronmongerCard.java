package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class IronmongerCard extends DomCard {
    public IronmongerCard() {
      super( DomCardName.Ironmonger);
    }

    public void play() {
        boolean willdraw = false;
        owner.addActions(1);
        owner.drawCards(1);
        ArrayList<DomCard> theTopCards = owner.revealTopCards(1);
        if (theTopCards.isEmpty())
            return;
        DomCard theTopCard = theTopCards.get(0);
        if (theTopCard.hasCardType(DomCardType.Treasure))
            owner.addAvailableCoins(1);
        if (theTopCard.hasCardType(DomCardType.Victory))
            willdraw=true;
        if (theTopCard.hasCardType(DomCardType.Action))
            owner.addActions(1);
        if (owner.isHumanOrPossessedByHuman()) {
            owner.setNeedsToUpdateGUI();
            if (owner.getEngine().getGameFrame().askPlayer("<html>Discard " + theTopCard.getName().toHTML() +" ?</html>", "Resolving " + this.getName().toString()))
                owner.discard(theTopCard);
            else
                owner.putOnTopOfDeck(theTopCard);
        } else {
            if (theTopCard.getDiscardPriority(owner.getActionsLeft()) >= 16)
                owner.putOnTopOfDeck(theTopCard);
            else
                owner.discard(theTopCard);
        }
        if (willdraw)
            owner.drawCards(1);
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

    @Override
    public int getPlayPriority() {
        if (owner.getDeckAndDiscardSize()==0)
            return 500;
        return super.getPlayPriority();
    }

    @Override
    public int getDiscardPriority(int aActionsLeft) {
        if (aActionsLeft>1 && owner.getDeckAndDiscardSize()==0)
            return 10;
        return super.getDiscardPriority(aActionsLeft);
    }
}