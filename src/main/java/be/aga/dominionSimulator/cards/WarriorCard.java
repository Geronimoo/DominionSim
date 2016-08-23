package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class WarriorCard extends DomCard {
    public WarriorCard() {
      super( DomCardName.Warrior);
    }

    public void play() {
      owner.drawCards(2);
        for (int i=0;i<owner.getCardsFromPlay(DomCardType.Traveller).size();i++) {
            for (DomPlayer thePlayer : owner.getOpponents()) {
              if (thePlayer.checkDefense() )
                  continue;
              ArrayList< DomCard > theCards = thePlayer.revealTopCards(1);
              if (theCards.isEmpty())
                  continue;
              DomCard card = theCards.get(0);
              if (card.getCoinCost(owner.getCurrentGame())>=3 && card.getCoinCost(owner.getCurrentGame())<=4 && card.getPotionCost()==0 && card.getDebtCost()==0) {
                  thePlayer.trash(card);
              } else{
                  thePlayer.discard(card);
              }
            }
        }
    }
    @Override
    public void handleCleanUpPhase() {
        if (owner==null)
            return;
        if (owner.wants(DomCardName.Hero)) {
            DomPlayer theOwner = owner;
            owner.returnToSupply(this);
            theOwner.gain(DomCardName.Hero);
            return;
        }
        super.handleCleanUpPhase();
    }
}