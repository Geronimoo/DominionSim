package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class EngineerCard extends DomCard {
    public EngineerCard() {
      super( DomCardName.Engineer);
    }

    public void play() {
      if (owner.isHumanOrPossessedByHuman()){
          handleHuman();
          return;
      }
      DomCardName theDesiredCard = owner.getDesiredCard(new DomCost( 4, 0), false);
      if (theDesiredCard==null) {
        //possible to get here if card was throne-roomed
    	theDesiredCard=owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost(4, 0));
      }
      if (theDesiredCard!=null)
        owner.gain(theDesiredCard);
      theDesiredCard = owner.getDesiredCard(new DomCost( 4, 0), false);
      if (theDesiredCard==null)
          return;
      if (owner.getCurrentGame().countInSupply(theDesiredCard)<3 || !owner.stillInEarlyGame()) {
          DomCardName theNewDesiredCard = owner.getDesiredCard(new DomCost(4, 0), false);
          if (theDesiredCard==theNewDesiredCard) {
              DomPlayer theOwner = owner;
              owner.trash(owner.removeCardFromPlay(this));
              theOwner.gain(theDesiredCard);
          }
      }
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().keySet()) {
            if (new DomCost(4,0).compareTo(theCard.getCost(owner.getCurrentGame()))>=0 && owner.getCurrentGame().countInSupply(theCard)>0 )
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        owner.gain(owner.getCurrentGame().takeFromSupply(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for "+this.getName().toString(), theChooseFrom, "Mandatory!")));
        if (owner.getCardsInPlay().contains(this)
          && owner.getEngine().getGameFrame().askPlayer("<html>Trash " + DomCardName.Engineer.toHTML() +"?</html>", "Resolving " + this.getName().toString())){
            DomPlayer theOwner = owner;
            theOwner.trash(owner.removeCardFromPlay(this));
            theChooseFrom = new ArrayList<DomCardName>();
            for (DomCardName theCard : theOwner.getCurrentGame().getBoard().keySet()) {
                if (new DomCost(4,0).compareTo(theCard.getCost(theOwner.getCurrentGame()))>=0 && theOwner.getCurrentGame().countInSupply(theCard)>0 )
                    theChooseFrom.add(theCard);
            }
            if (theChooseFrom.isEmpty())
                return;
            theOwner.gain(theOwner.getCurrentGame().takeFromSupply(theOwner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for "+this.getName().toString(), theChooseFrom, "Mandatory!")));
        }


    }

    @Override
    public boolean wantsToBePlayed() {
       return owner.getDesiredCard(new DomCost( 4, 0), false) != null ;
    }
}