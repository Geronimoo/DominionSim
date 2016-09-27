package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

public class ArtisanCard extends DomCard {
    public ArtisanCard() {
      super( DomCardName.Artisan);
    }

    public void play() {
      DomCardName theDesiredCard = owner.getDesiredCard(new DomCost( 5, 0), false);
      if (theDesiredCard==null) {
        //possible to get here if card was throne-roomed
    	theDesiredCard=owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost(5, 0));
      }
      if (theDesiredCard!=null)
        owner.gainInHand(theDesiredCard);
      owner.putCardFromHandOnTop();
    }
    
    @Override
    public boolean wantsToBePlayed() {
       return owner.getDesiredCard(new DomCost( 5, 0), false) != null ;
    }
}