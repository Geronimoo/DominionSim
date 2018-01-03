package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class Small_CastleCard extends DomCard {
    public Small_CastleCard() {
      super( DomCardName.Small_Castle);
    }

    public void play() {
      DomPlayer theOwner = owner;
      if (owner==null)
          return;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      if (owner.getCardsFromHand(DomCardName.Crumbling_Castle).isEmpty()) {
          if (owner.getCardsFromPlay(getName()).contains(this))
              owner.trash(owner.removeCardFromPlay(this));
      } else  {
          owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(DomCardName.Crumbling_Castle).get(0)));
      }
      theOwner.gain(DomCardName.Castles);
    }

    private void handleHuman() {
        DomPlayer theOwner = owner;
        if (owner.getCardsFromHand(DomCardType.Castle).isEmpty() ) {
            if (owner.getCardsInPlay().contains(this)) {
                owner.trash(owner.removeCardFromPlay(this));
                theOwner.gain(DomCardName.Castles);
            }
        } else {
            ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
            for (DomCard theCard : owner.getCardsFromHand(DomCardType.Castle)) {
                theChooseFrom.add(theCard.getName());
            }
            DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Trash itself");
            if (theChosenCard!=null) {
                owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
                theOwner.gain(DomCardName.Castles);
            } else {
                if (owner.getCardsInPlay().contains(this)) {
                    owner.trash(owner.removeCardFromPlay(this));
                    theOwner.gain(DomCardName.Castles);
                }
            }
        }
    }

    @Override
    public boolean wantsToBePlayed() {
        return owner.getCurrentGame().countInSupply(DomCardName.Castles)>0;
    }

    @Override
    public int getTrashPriority() {
        return 38;
    }

    @Override
    public int getDiscardPriority(int aActionsLeft) {
        if (wantsToBePlayed() && aActionsLeft>0)
            return 22;
        else
            return super.getDiscardPriority(aActionsLeft);
    }

    @Override
    public int getPlayPriority() {
        if (owner.getCurrentGame().countInSupply(DomCardName.Castles)<=2)
            return 22;
        return super.getPlayPriority();
    }
}