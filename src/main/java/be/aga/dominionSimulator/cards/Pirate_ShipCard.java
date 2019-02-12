package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class Pirate_ShipCard extends DomCard {
    private ArrayList<DomPlayer> defenders;

    public Pirate_ShipCard () {
      super( DomCardName.Pirate_Ship);
    }

    @Override
    public void play() {
        defenders = new ArrayList<DomPlayer>();
        for (DomPlayer thePlayer : owner.getOpponents()) {
            if (thePlayer.checkDefense())
                defenders.add(thePlayer);
        }
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
        if (owner.addingThisIncreasesBuyingPower( new DomCost( owner.getPirateShipLevel(), 0 ) )) {
          if (defenders.size()==owner.getOpponents().size() || owner.getPlayStrategyFor(this)!=DomPlayStrategy.attackUntil5Coins || owner.getPirateShipLevel()>=5) {
            owner.addAvailableCoins( owner.getPirateShipLevel());
            return;
          }
        }
        if (attackOpponents()){
          owner.increasePirateShipLevel();
        }
    }

    private void handleHuman() {
        ArrayList<String> theOptions = new ArrayList<String>();
        theOptions.add("+$"+owner.getPirateShipLevel());
        theOptions.add("Attack!");
        int theChoice = owner.getEngine().getGameFrame().askToSelectOption("Pirate Ship", theOptions, "Mandatory!");
        if (theChoice==0) {
            owner.addAvailableCoins( owner.getPirateShipLevel());
        } else {
            if (attackOpponents())
                owner.increasePirateShipLevel();
        }
    }

    private boolean attackOpponents() {
        boolean attackSuccess = false;
        for (DomPlayer thePlayer : owner.getOpponents()) {
            if (defenders.contains(thePlayer))
              continue;
            ArrayList< DomCard > theTopTwo = thePlayer.revealTopCards(2);
            DomCard theCardToTrash = null;
            if (owner.isHumanOrPossessedByHuman()) {
                theCardToTrash = attackForHuman(theTopTwo);
            } else {
                for (DomCard theCard : theTopTwo) {
                    if (theCard.hasCardType(DomCardType.Treasure)) {
                        if (theCardToTrash == null
                                || theCard.getTrashPriority() > theCardToTrash.getTrashPriority()) {
                            theCardToTrash = theCard;
                        }
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

    private DomCard attackForHuman(ArrayList<DomCard> theTopTwo) {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : theTopTwo) {
            if (theCard.hasCardType(DomCardType.Treasure)) {
                theChooseFrom.add(theCard.getName());
            }
        }
        if (theChooseFrom.size()==0)
            return null;
        if (theChooseFrom.size()==1) {
            for (DomCard theCard : theTopTwo) {
                if (theCard.getName()==theChooseFrom.get(0))
                    return theCard;
            }
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash: ", theChooseFrom, "Mandatory!");
        for (DomCard theCard : theTopTwo) {
            if (theCard.getName()==theChosenCard)
                return theCard;
        }
        return null;
    }

    public double getPotentialCoinValue() {
      if (owner.getActionsLeft()==0 && owner.getCardsInHand().contains( this ) ) 
        return 0;
      return owner.getPirateShipLevel();
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}