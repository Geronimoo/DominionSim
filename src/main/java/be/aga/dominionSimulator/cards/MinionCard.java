package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class MinionCard extends DomCard {
    public MinionCard () {
      super( DomCardName.Minion );
    }

    public void play() {
      if (!owner.getCardsFromHand( DomCardName.Minion ).isEmpty()) {
        //if there are more Minions in hand, always play for +$2
        playForMoney();
        return;
      }
      DomCardName theDesiredCard = owner.getDesiredCard( owner.getTotalPotentialCurrency().add( new DomCost( 2, 0 ) ), false);
      //go through owner's buy rules (from top) and if there's a card we want to buy, but can't now, get 4 new cards with Minion
      //this can only work if every card in owner's buy rules has a treshold setting for number of Minions in the deck
      for (DomBuyRule buyRule : owner.getBuyRules()){
        if (owner.wants(buyRule.getCardToBuy()) && theDesiredCard != buyRule.getCardToBuy()) {
             playForCards();
             return;
        }
        if (theDesiredCard == buyRule.getCardToBuy()) {
          if (theDesiredCard==DomCardName.Minion && owner.getAvailableCoins()>=2 && owner.getCardsFromPlay(DomCardName.Minion).size()==2) {
              playForCards();
              return;
          }
          break;
        }
      }
      //if we can get in a free attack without it decreasing our buying potential this turn, do it
      if (theDesiredCard == owner.getDesiredCard( new DomCost( owner.getAvailableCoins(), owner.availablePotions ), false)){
        playForCards();
        return;
      }
      //just play it for +$2
      playForMoney();
   }

    private final void playForMoney() {
      owner.addActions( 1 );
      owner.addAvailableCoins( 2 );
      //although we don't attack, opponents are still allowed to use reaction cards
      for (DomPlayer thePlayer:owner.getOpponents()) {
        thePlayer.checkDefense();
      }
    }

    private final void playForCards() {
      owner.addActions( 1 );
      owner.discardHand();
      owner.drawCards( 4 );
      for (DomPlayer thePlayer : owner.getOpponents()) {
          if (!thePlayer.checkDefense() && thePlayer.getHandSize()>4) {
            thePlayer.discardHand();
            thePlayer.drawCards( 4 );
           }
        }
    }
    @Override
    public int getPlayPriority() {
    	if (owner.getActionsLeft()>1)
    		return 35;
    	return super.getPlayPriority();
    }
}