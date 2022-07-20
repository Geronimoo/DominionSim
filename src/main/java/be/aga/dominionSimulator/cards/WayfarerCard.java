package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class WayfarerCard extends DomCard {
    public WayfarerCard() {
      super( DomCardName.Wayfarer);
    }

    @Override
    public void play() {
      owner.drawCards( 3 );
      owner.gain(DomCardName.Silver);
    }
    
    @Override
    public int getPlayPriority() {
      return owner.getActionsAndVillagersLeft()>1 ? 6 : super.getPlayPriority();
    }
}