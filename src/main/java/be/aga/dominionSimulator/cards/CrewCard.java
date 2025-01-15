package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class CrewCard extends DomCard {
    public CrewCard() {
      super( DomCardName.Crew);
    }

    public void play() {
      owner.drawCards(3);
    }

    public void resolveDuration() {
        owner.removeCardFromPlay(this);
        owner.putOnTopOfDeck(this);
    }
    
    @Override
    public int getPlayPriority() {
      return owner.getActionsAndVillagersLeft()>1 ? 6 : super.getPlayPriority();
    }
}