package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class WatchtowerCard extends DrawUntilXCardsCard {
    private DomCard lastWatchtoweredCard = null;

    public WatchtowerCard () {
      super( DomCardName.Watchtower);
    }

    public void play() {
      owner.drawCards( 6 - owner.getCardsInHand().size() );
    }

    public boolean react(DomCard aCard) {
       if (DomEngine.haveToLog) DomEngine.addToLog( owner + " reveals " + this );
       lastWatchtoweredCard = aCard;
       if (aCard.getName().getTrashPriority(owner)<16) {
         owner.trash( aCard );
       } else {
         owner.gainOnTopOfDeck( aCard );
       }
       return true;
    }

    public boolean wantsToBePlayed() {
      if (owner.getCardsInHand().size()<=6 && owner.getDeckSize()>0)
    	  return super.wantsToBePlayed();
      return false;
    }

    public boolean wantsToReact(DomCard aCard) {
      //TODO this way of handling Watchtower looks a bit dirty
      if (lastWatchtoweredCard == aCard)
        return false;
      if (aCard.getName().getTrashPriority(owner)<35) {
     	return true;
      } else {
    	return false;
      }
    }
    @Override
    public int getPlayPriority() {
        if (owner.getActionsLeft()>1)
            return 20;
        if (owner.getActionsLeft()>2)
            return 35;

        return super.getPlayPriority();
    }
}