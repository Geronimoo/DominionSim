package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class BargainCard extends DomCard {
    public BargainCard() {
      super( DomCardName.Bargain);
    }

  public void play() {
    if (owner.isHumanOrPossessedByHuman()) {
        handleHuman();
    } else {
        DomCardName theDesiredCard = owner.getDesiredCard(null
                , new DomCost(5, 0)
                , false
                , false
                , DomCardType.Victory);
        if (theDesiredCard == null)
            theDesiredCard = owner.getCurrentGame().getBoard().getBestCardInSupplyFor(owner, null, new DomCost(5, 0), false, DomCardType.Victory, null);
        owner.gain(theDesiredCard);
    }
    for (DomPlayer player : owner.getOpponents()) {
        player.gain(DomCardName.Horse);
    }
  }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (!theCard.hasCardType(DomCardType.Victory) && new DomCost(5,0).customCompare(theCard.getCost(owner.getCurrentGame()))>=0 && owner.getCurrentGame().countInSupply(theCard)>0)
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for " + this.getName().toString(), theChooseFrom, "Mandatory!");
        owner.gain(owner.getCurrentGame().takeFromSupply(theChosenCard));
    }
}