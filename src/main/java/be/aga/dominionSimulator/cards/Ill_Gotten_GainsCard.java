package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class Ill_Gotten_GainsCard extends DomCard {
    public Ill_Gotten_GainsCard () {
      super( DomCardName.Ill_Gotten_Gains);
    }

    public void play() {
        owner.addAvailableCoins( 1 );
        if (owner.isHumanOrPossessedByHuman()) {
            if (owner.getEngine().getGameFrame().askPlayer("<html>Gain " + DomCardName.Copper.toHTML() +"</html>", "Resolving " + this.getName().toString())) {
                DomCard theCopper = owner.getCurrentGame().takeFromSupply(DomCardName.Copper);
                if (theCopper != null)
                    owner.gainInHand(theCopper);
            }
            return;
        }
        DomCard theCopper = DomCardName.Copper.createNewCardInstance();
        theCopper.setOwner(owner);
        DomCost theCopperCoins = new DomCost(theCopper.getCoinValue(), 0);
        if (owner.wants(DomCardName.Copper) || 
//         	  (!owner.stillInEarlyGame() && owner.addingThisIncreasesBuyingPower(theCopperCoins))){
         	  owner.addingThisIncreasesBuyingPower(theCopperCoins)){
           theCopper=owner.getCurrentGame().takeFromSupply(DomCardName.Copper);
           if (theCopper!=null)
        	 owner.gainInHand(theCopper);
        }
    }
    
    @Override
	public void doWhenGained() {
      for (DomPlayer thePlayer : owner.getOpponents()) {
        if (owner.getCurrentGame().countInSupply(DomCardName.Curse )>0)
          thePlayer.gain(DomCardName.Curse);
      }
	}
}