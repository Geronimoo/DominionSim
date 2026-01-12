package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class RiverboatCard extends DomCard {
    public RiverboatCard() {
      super( DomCardName.Riverboat);
    }

    public void play() {}

    public void resolveDuration() {
        DomCard riverBoatCard = owner.getCurrentGame().getRiverboatChoice().createNewCardInstance();
        riverBoatCard.setOwner(owner);
        riverBoatCard.play();
    }
}