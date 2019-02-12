package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Cargo_ShipCard extends DomCard {
    private DomCard cargoCard;

    public Cargo_ShipCard() {
      super( DomCardName.Cargo_Ship);
    }

    public void play() {
      owner.addAvailableCoins(2);
    }

    public void resolveDuration() {
      owner.addCardToHand(cargoCard);
      cargoCard =null;
      owner.showHand();
    }

    @Override
    public void handleCleanUpPhase() {
        if (cargoCard ==null)
            setDiscardAtCleanup(true);
        super.handleCleanUpPhase();
    }

    public void cleanVariablesFromPreviousGames() {
        cargoCard =null;
    }

    public DomCard getCargoCard() {
        return cargoCard;
    }

    public void setCargoCard(DomCard cargoCard) {
        if (DomEngine.haveToLog ) DomEngine.addToLog( owner + " puts " + cargoCard.getName().toHTML()+" on " + DomCardName.Cargo_Ship.toHTML());
        this.cargoCard = cargoCard;
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}