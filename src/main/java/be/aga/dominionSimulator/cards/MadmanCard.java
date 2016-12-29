package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class MadmanCard extends DomCard {
    public MadmanCard() {
      super( DomCardName.Madman);
    }

    public void play() {
      if (owner==null)
          return;
      owner.addActions(2);
      owner.drawCards(owner.getCardsInHand().size());
      if (owner.getCardsFromPlay(DomCardName.Madman).isEmpty())
          return;
      owner.returnToSupply(owner.removeCardFromPlay(this));
    }

    @Override
    public boolean wantsToBePlayed() {
        if (owner.getPlayStrategyFor(this)== DomPlayStrategy.MarketSquareCombo) {
          if ((owner.countInDeck(DomCardName.Madman)>4 || owner.getCardsInHand().size()>5)
                  && owner.countInDeck(DomCardName.Market_Square)-owner.countInPlay(DomCardName.Market_Square)>5 && owner.getDeck().getDeckAndDiscardSize() > 3 )
              return true;
          else
              if (owner.getCardsInHand().size()>10 && owner.getDeck().getDeckAndDiscardSize() > 3 )
                  return true;
              else
                  return false;
        }
        if (owner.getPlayStrategyFor(this)==DomPlayStrategy.bigTurnBridge) {
            if ((owner.countInDeck(DomCardName.Madman)>4 || owner.getCardsInHand().size()>5)
                    && owner.countInDeck(DomCardName.Bridge)>4 )
                return true;
            else
                return false;
        }
        return owner.getDeck().getDeckAndDiscardSize() > 3 && owner.getCardsInHand().size() > 3;
    }

    @Override
    public int getDiscardPriority(int aActionsLeft) {
        if (!wantsToBePlayed())
            return 5;
        return super.getDiscardPriority(aActionsLeft);
    }
}