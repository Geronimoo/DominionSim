package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class Tragic_HeroCard extends DomCard{
    public Tragic_HeroCard() {
      super( DomCardName.Tragic_Hero);
    }

    public void play() {
      owner.drawCards(3);
      owner.addAvailableBuys(1);
      if (owner.getCardsInHand().size()>=8) {
          if (owner.isHumanOrPossessedByHuman()) {
              ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
              for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
                  if (theCard.hasCardType(DomCardType.Treasure) && owner.getCurrentGame().countInSupply(theCard) > 0)
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
          if (owner.getCardsInPlay().contains(this))
            owner.trash(owner.removeCardFromPlay(this));
      }
    }
}