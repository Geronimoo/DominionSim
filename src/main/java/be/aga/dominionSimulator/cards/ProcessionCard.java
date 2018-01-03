package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class ProcessionCard extends MultiplicationCard {
    public ProcessionCard() {
      super( DomCardName.Procession);
    }

    @Override
    public void play() {
        super.play();
        DomCard theCardToMultiply = getMyCardToMultiply();
        if (theCardToMultiply==null)
            return;
        if (owner.isHumanOrPossessedByHuman()) {
            ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
            for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
                if (theCard.getCost(owner.getCurrentGame()).customCompare(theCardToMultiply.getCost(owner.getCurrentGame()).add(new DomCost(1,0)))==0
                  && owner.getCurrentGame().countInSupply(theCard)>0
                  && theCard.hasCardType(DomCardType.Action))
                    theChooseFrom.add(theCard);
            }
            if (owner.getCardsInPlay().indexOf(theCardToMultiply) != -1)
              owner.trash(owner.removeCardFromPlay(theCardToMultiply));
            if (theChooseFrom.isEmpty())
                return;
            if (theChooseFrom.size()==1) {
                owner.gain(owner.getCurrentGame().takeFromSupply(theChooseFrom.get(0)));
            } else {
                owner.gain(owner.getCurrentGame().takeFromSupply(owner.getEngine().getGameFrame().askToSelectOneCard("Gain a card for " + this.getName().toString(), theChooseFrom, "Mandatory!")));
            }
        } else {
            DomCardName theDesiredCard = owner.getDesiredCard(DomCardType.Action, theCardToMultiply.getName().getCost(owner.getCurrentGame()).add(new DomCost(1, 0)), true, false, null);
            if (owner.getCardsInPlay().indexOf(theCardToMultiply) != -1)
                owner.trash(owner.removeCardFromPlay(theCardToMultiply));
            if (theDesiredCard == null) {
                DomCost theCost = new DomCost(theCardToMultiply.getCoinCost(owner.getCurrentGame()) + 1, theCardToMultiply.getPotionCost());
                DomCardName theCardToGain = owner.getCurrentGame().getBestCardInSupplyFor(owner, DomCardType.Action, theCost, true);
                if (theCardToGain != null)
                    owner.gain(theCardToGain);
            } else {
                owner.gain(theDesiredCard);
            }
        }
    }
}