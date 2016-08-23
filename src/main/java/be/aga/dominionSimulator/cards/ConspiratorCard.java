package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class ConspiratorCard extends DomCard {
    public ConspiratorCard () {
      super( DomCardName.Conspirator);
    }

    public void play() {
      owner.addAvailableCoins(2);
      if (owner.getActionsPlayed()>=3) {
    	  owner.addActions(1);
    	  owner.drawCards(1);
      }
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Terminal) {
            return owner.getActionsPlayed()<2;
        }
        return super.hasCardType(aType);
    }

    @Override
    public int getPlayPriority() {
    	if (owner.getActionsPlayed()>=2) {
    		return 7;
    	}
    	return super.getPlayPriority();
    }
}