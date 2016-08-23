package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomGame;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class Mining_VillageCard extends DomCard {
    public Mining_VillageCard () {
      super( DomCardName.Mining_Village);
    }

    public void play() {
      owner.addActions( 2 );
      owner.drawCards( 1 );
      if (!owner.getCurrentGame().getBoard().getTrashedCards().contains(this))
        posibblyTrashThis();
    }

    private final void posibblyTrashThis() {
        if (owner.getPlayStrategyFor(this)==DomPlayStrategy.forEngines && owner.getCurrentGame().getGainsNeededToEndGame()>3)
            return;
        if (owner.addingThisIncreasesBuyingPower( new DomCost( 2,0 )) || owner.getCurrentGame().getGainsNeededToEndGame()<=3) {
            DomPlayer theOwner = owner;
            owner.trash(owner.removeCardFromPlay( this ));
            //owner has now become null... so we use theOwner
            theOwner.addAvailableCoins( 2 );
        }
    }
}