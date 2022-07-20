package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPhase;

public class TrailCard extends DomCard {
    public TrailCard() {
      super( DomCardName.Trail);
    }

    public void play() {
        owner.addActions(1);
        owner.drawCards(1);
    }

    @Override
    public void doWhenDiscarded() {
    	if (owner.getCurrentGame().getActivePlayer().getPhase()!=DomPhase.CleanUp) {
    	    owner.play(owner.removeCardFromDiscard(this));
        }
    }

    @Override
    public void doWhenGained() {
        owner.play(owner.removeCardFromDiscard(this));
    }

    @Override
    public void doWhenTrashed() {
        owner.getCurrentGame().getTrashedCards().remove(this);
        owner.play(this);
    }

    @Override
    public int getPlayPriority() {
        if (owner.getCurrentGame().countEmptyPiles()>0 && !owner.getCardsFromHand(DomCardName.Carpenter).isEmpty())
            return 100;
        return super.getPlayPriority();
    }
}