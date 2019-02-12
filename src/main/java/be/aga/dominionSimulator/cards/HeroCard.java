package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class HeroCard extends TravellerCard{
    public HeroCard() {
      super( DomCardName.Hero);
      myUpgrade=DomCardName.Champion;
    }

    public void play() {
      owner.addAvailableCoins(2);
      if (owner.isHumanOrPossessedByHuman()) {
          ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
          for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
              if (theCard.hasCardType(DomCardType.Treasure) && owner.getCurrentGame().countInSupply(theCard)>0)
                  theChooseFrom.add(theCard);
          }
          if (theChooseFrom.isEmpty())
              return;
          owner.gain(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain from " + this.getName().toString(), theChooseFrom, "Mandatory!"));
      } else {
          DomCardName theDesiredCard = owner.getDesiredCard(DomCardType.Treasure, new DomCost(1000, 0), false, false, null);
          if (theDesiredCard == null)
              theDesiredCard = owner.getCurrentGame().getBestCardInSupplyFor(owner, DomCardType.Treasure, new DomCost(1000, 0));
          owner.gain(theDesiredCard);
      }
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}