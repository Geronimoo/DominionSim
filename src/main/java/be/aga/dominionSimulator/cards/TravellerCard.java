package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class TravellerCard extends DomCard {
    DomCardName myUpgrade;

    public TravellerCard(DomCardName domCardName) {
      super( domCardName);
    }

    @Override
    public void handleCleanUpPhase() {
        if (owner==null)
            return;
        if ((!owner.isHumanOrPossessedByHuman() && owner.wants(myUpgrade))
           || (owner.isHumanOrPossessedByHuman()
                && owner.getEngine().getGameFrame().askPlayer("<html>Exchange "+ this.getName()+" for " + myUpgrade.toHTML() +"</html>", "Resolving " + this.getName().toString())
                && owner.getCurrentGame().countInSupply(myUpgrade)>0)){
            DomPlayer theOwner = owner;
            owner.returnToSupply(this);
            DomCard theTraveller = theOwner.getCurrentGame().takeFromSupply(myUpgrade);
            theOwner.getDeck().addPhysicalCardWhenNotGained(theTraveller);
            theOwner.getDeck().justAddToDiscard(theTraveller);
            return;
        }
        super.handleCleanUpPhase();
    }
}