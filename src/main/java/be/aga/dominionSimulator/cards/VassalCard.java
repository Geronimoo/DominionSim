package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class VassalCard extends DomCard {
    public VassalCard() {
      super( DomCardName.Vassal);
    }

    public void play() {
      owner.addAvailableCoins(2);
      if (owner.getDeckAndDiscardSize()==0)
    	return;
      DomCard theRevealedCard = owner.revealTopCards(1).get(0);
      if (owner.isHumanOrPossessedByHuman()) {
          handleHumanPlayer(theRevealedCard);
      } else {
          if (theRevealedCard.hasCardType(DomCardType.Action) && theRevealedCard.wantsToBePlayed())
              owner.play(theRevealedCard);
          else
              owner.discard(theRevealedCard);
      }
    }

    private void handleHumanPlayer(DomCard theRevealedCard) {
        if (theRevealedCard.hasCardType(DomCardType.Action) && owner.getEngine().getGameFrame().askPlayer("<html>Play " + theRevealedCard.getName().toHTML() +"?</html>", "Resolving " + this.getName().toString())) {
            owner.play(theRevealedCard);
        }else{
            owner.discard(theRevealedCard);
        }
    }

    @Override
    public int getPlayPriority() {
        if (owner.getKnownTopCards()>0 && owner.getDeck().peekAtTopCard().hasCardType(DomCardType.Action) && !owner.getDeck().peekAtTopCard().hasCardType(DomCardType.Terminal))
            return 1;
        if (owner.getKnownTopCards()>0 && owner.getDeck().peekAtTopCard().hasCardType(DomCardType.Action) && owner.getActionsLeft()>1)
            return 1;
        return super.getPlayPriority();
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}