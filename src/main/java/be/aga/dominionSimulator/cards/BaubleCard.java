package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class BaubleCard extends DomCard {
    public BaubleCard() {
      super( DomCardName.Bauble);
    }

    public void play() {
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }

        switch (owner.getPlayStrategyFor(this)) {
            case PlateauShepherds:
                playPlateauShepherds();
                break;
            default:
                playDefault();
                break;
        }

    }

    private void playPlateauShepherds() {
        int theChoicesCount=0;
        boolean buysChosen = false;

        if (owner.getTotalPotentialCurrency().getCoins()+1>=owner.getBuysLeft()*2+2) {
            owner.addAvailableBuys(1);
            theChoicesCount++;
            buysChosen=true;
        }
        if (owner.addingThisIncreasesBuyingPower( new DomCost( 1,0 ))) {
            owner.addAvailableCoins(1);
            theChoicesCount++;
        }
        if (theChoicesCount<2) {
            owner.addFavors(1);
            theChoicesCount++;
        }
        if (theChoicesCount<2){
            owner.addAvailableCoins(1);
        }
    }

    private void playDefault() {
        int theChoicesCount=0;
        boolean buysChosen = false;

        if (owner.getTotalPotentialCurrency().customCompare(new DomCost(7,0))>0) {
            owner.addAvailableBuys(1);
            theChoicesCount++;
            buysChosen=true;
        }
        if (owner.addingThisIncreasesBuyingPower( new DomCost( 1,0 ))) {
            owner.addAvailableCoins(1);
            theChoicesCount++;
        }
        if (theChoicesCount<2) {
            owner.addFavors(1);
            theChoicesCount++;
        }
        if (theChoicesCount<2 && !buysChosen){
            owner.addAvailableBuys(1);
        }
    }

    private void handleHuman() {
        ArrayList<String> theOptions = new ArrayList<String>();
        theOptions.add("+Favor/+Coin");
        theOptions.add("+Favor/+Buy");
        theOptions.add("+Coin/+Buy");
        int theChoice = owner.getEngine().getGameFrame().askToSelectOption("Select for Pawn", theOptions, "Mandatory!");
        if (theChoice==0) {
            owner.addFavors(1);
            owner.addAvailableCoins(1);
        }
        if (theChoice==1) {
            owner.addFavors(1);
            owner.addAvailableBuys(1);
        }
        if (theChoice==2) {
            owner.addAvailableCoins(1);
            owner.addAvailableBuys(1);
        }
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}