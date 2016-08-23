package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class Pirate_ShipCard extends DomCard {
    public Pirate_ShipCard () {
      super( DomCardName.Pirate_Ship);
    }

    @Override
    public void play() {
        if (owner.addingThisIncreasesBuyingPower( new DomCost( owner.getPirateShipLevel(), 0 ) )) {
          if (owner.getPlayStrategyFor(this)!=DomPlayStrategy.attackUntil5Coins || owner.getPirateShipLevel()>=5) {
            owner.addAvailableCoins( owner.getPirateShipLevel());
            return;
          }
        }
        if (attackOpponents()){
          owner.increasePirateShipLevel();
        }
    }

    private boolean attackOpponents() {
        boolean attackSuccess = false;
        for (DomPlayer thePlayer : owner.getOpponents()) {
            if (thePlayer.checkDefense())
              continue;
            ArrayList< DomCard > theTopTwo = thePlayer.revealTopCards(2);
            DomCard theCardToTrash = null;
            for (DomCard theCard : theTopTwo) {
                if (theCard.hasCardType( DomCardType.Treasure )){
                    if (theCardToTrash==null 
                     || theCard.getTrashPriority()>theCardToTrash.getTrashPriority()){ 
                      theCardToTrash = theCard;
                    }
                }
            }
            for (DomCard theCard:theTopTwo) {
              if (theCard==theCardToTrash) {
                thePlayer.trash( theCardToTrash );
                attackSuccess=true;
              } else {
                thePlayer.discard( theCard );
              }
            }
        }
        return attackSuccess;
    }
    
    public double getPotentialCoinValue() {
      if (owner.getActionsLeft()==0 && owner.getCardsInHand().contains( this ) ) 
        return 0;
      return owner.getPirateShipLevel();
    }    
}