package be.aga.dominionSimulator.cards;
//package be.aga.dominionSimulator.cards;
//
//import java.util.Collections;
//
//import be.aga.dominionSimulator.DomCard;
//import be.aga.dominionSimulator.DomPlayer;
//import be.aga.dominionSimulator.enums.DomCardName;
//
//public class GoopCard extends DomCard {
//    public GoopCard () {
//      super( DomCardName.Goop);
//    }
//
//    public void play() {
//      owner.addAvailableCoins( 2 );
//      Collections.sort( owner.getCardsInHand(), SORT_FOR_TRASHING);
//      if (!owner.getCardsInHand().isEmpty()) 
//        owner.trash(owner.removeCardFromHand( owner.getCardsInHand().get( 0 )));
//        
//  	  for (DomPlayer thePlayer : owner.getOpponents()) {
//  		  if (thePlayer.checkDefense())
//  			  continue;
//  		  DomCard theGoop = owner.getCurrentGame().takeFromSupply(DomCardName.Goop);
//  		  if (theGoop!=null)
//  			  thePlayer.gain(theGoop);
//  	  }
//    }
//}