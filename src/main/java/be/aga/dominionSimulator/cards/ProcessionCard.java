package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class ProcessionCard extends MultiplicationCard {
    public ProcessionCard() {
      super( DomCardName.Procession);
    }

    @Override
    public void play() {
        DomCard theCardToMultiply = getCardToMultiply();
        super.play();
        if (theCardToMultiply==null)
            return;

        DomCardName theDesiredCard = owner.getDesiredCard(DomCardType.Action, theCardToMultiply.getName().getCost(owner.getCurrentGame()).add(new DomCost(1,0 ) ), true,false,null);
        if (owner.getCardsInPlay().indexOf(theCardToMultiply)!=-1)
          owner.trash(owner.removeCardFromPlay(theCardToMultiply));
        if (theDesiredCard==null) {
            DomCost theCost = new DomCost( theCardToMultiply.getCoinCost(owner.getCurrentGame()) + 1, theCardToMultiply.getPotionCost());
            DomCardName theCardToGain = owner.getCurrentGame().getBestCardInSupplyFor(owner, DomCardType.Action, theCost,true);
            if (theCardToGain!=null)
                owner.gain(theCardToGain);
        }else {
            owner.gain(theDesiredCard);
        }
    }
}