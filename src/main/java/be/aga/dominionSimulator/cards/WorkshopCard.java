package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class WorkshopCard extends DomCard {
    public WorkshopCard () {
      super( DomCardName.Workshop);
    }

    public void play() {
      if (owner.isHumanOrPossessedByHuman()) {
          ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
          for (DomCardName theCard : owner.getCurrentGame().getBoard().keySet()) {
              if (theCard.getCost(owner.getCurrentGame()).compareTo(new DomCost(4,0))<=0 && owner.getCurrentGame().countInSupply(theCard)>0)
                  theChooseFrom.add(theCard);
          }
          if (theChooseFrom.isEmpty())
              return;
          owner.gain(owner.getCurrentGame().takeFromSupply(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for "+this.getName().toString(), theChooseFrom, "Mandatory!")));
      }else {
          DomCardName theDesiredCard = owner.getDesiredCard(new DomCost(4, 0), false);
          if (theDesiredCard == null) {
              //possible to get here if card was throne-roomed
              theDesiredCard = owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost(4, 0));
          }
          if (theDesiredCard != null)
              owner.gain(theDesiredCard);
      }
    }
    
    @Override
    public boolean wantsToBePlayed() {
       return owner.getDesiredCard(new DomCost( 4, 0), false) != null ;
    }

    @Override
    public int getPlayPriority() {
        if (owner.getDrawDeckSize()==0 && owner.getActionsLeft()>1 && !owner.getCardsFromHand(DomCardType.Cycler).isEmpty())
            return 1;
        return super.getPlayPriority();
    }
}