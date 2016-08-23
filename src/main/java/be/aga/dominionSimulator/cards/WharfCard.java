package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class WharfCard extends DomCard {
    public WharfCard () {
      super( DomCardName.Wharf);
    }

    public void play() {
      owner.addAvailableBuys(1);
      owner.drawCards(2);
    }

    public void resolveDuration() {
        owner.addAvailableBuys(1);
        owner.drawCards(2);
    }
    
    @Override
    public int getPlayPriority() {
      return owner.getActionsLeft()>1 ? 6 : super.getPlayPriority();
    }
}