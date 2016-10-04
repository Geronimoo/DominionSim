package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class DrawUntilXCardsCard extends DomCard{
    public DrawUntilXCardsCard(DomCardName aCardName) {
		super(aCardName);
	}

	@Override
    public boolean wantsToBePlayed() {
		if (owner.getActionsLeft() > 2
                && !owner.getCardsFromHand(DomCardType.Trasher).isEmpty()
                && owner.getCardsFromHand(DomCardType.Trasher).get(0).wantsToBePlayed())
			return false;
    	return super.wantsToBePlayed();
    }

    @Override
    public boolean wantsToBeMultiplied() {
        return false;
    }
}
