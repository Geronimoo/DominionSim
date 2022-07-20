package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class RemodelCard extends DomCard {
    public RemodelCard () {
      super( DomCardName.Remodel);
    }

    public void play() {
        doRemodel(owner, this);
    }

    public static void doRemodel(DomPlayer domplayer, DomCard remodelCard) {
        if (domplayer.getCardsInHand().isEmpty())
            return;
        if (domplayer.isHumanOrPossessedByHuman()) {
            ArrayList<DomCardName> theChooseFrom = new ArrayList<>();
            for (DomCard theCard : domplayer.getCardsInHand())
                theChooseFrom.add(theCard.getName());
            DomCard theCardToRemodel = domplayer.getCardsFromHand(domplayer.getEngine().getGameFrame().askToSelectOneCard("Select card to " + remodelCard.getName().toString(), theChooseFrom, "Mandatory!")).get(0);
            domplayer.trash(domplayer.removeCardFromHand(theCardToRemodel));
            theChooseFrom = new ArrayList<>();
            for (DomCardName theCard : domplayer.getCurrentGame().getBoard().getTopCardsOfPiles()) {
                if (theCardToRemodel.getCost(domplayer.getCurrentGame()).add(new DomCost(2,0)).customCompare(theCard.getCost(domplayer.getCurrentGame()))>=0
                        && domplayer.getCurrentGame().countInSupply(theCard)>0)
                    theChooseFrom.add(theCard);
            }
            if (theChooseFrom.isEmpty())
                return;
            domplayer.gain(domplayer.getEngine().getGameFrame().askToSelectOneCard("Select card to gain from "+ DomCardName.Remodel, theChooseFrom, "Mandatory!"));
        } else {
            DomCard theCardToTrash = domplayer.findCardToRemodel(remodelCard, 2, true);
            if (theCardToTrash == null) {
                //this is needed when card is played with Throne Room effect or Golem
                Collections.sort(domplayer.getCardsInHand(), SORT_FOR_TRASHING);
                theCardToTrash = domplayer.getCardsInHand().get(0);
            }
            domplayer.trash(domplayer.removeCardFromHand(theCardToTrash));
            DomCost theMaxCostOfCardToGain = new DomCost(theCardToTrash.getCoinCost(domplayer.getCurrentGame()) + 2, theCardToTrash.getPotionCost());
            DomCardName theDesiredCard = domplayer.getDesiredCard(theMaxCostOfCardToGain, false);
            if (theDesiredCard == null)
                theDesiredCard = domplayer.getCurrentGame().getBestCardInSupplyFor(domplayer, null, theMaxCostOfCardToGain);
            if (theDesiredCard != null)
                domplayer.gain(theDesiredCard);
        }
    }


    @Override
    public boolean wantsToBePlayed() {
      return owner.findCardToRemodel(this, 2, true)!=null;
   }
}