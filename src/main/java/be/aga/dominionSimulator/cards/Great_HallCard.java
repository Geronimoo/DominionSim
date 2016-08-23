package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Great_HallCard extends DomCard {
    public Great_HallCard () {
      super( DomCardName.Great_Hall);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
    }
    
    @Override
    public int getTrashPriority() {
        //TODO this should be lower if we're in the greening stage! 
    	return super.getTrashPriority();
    }
}