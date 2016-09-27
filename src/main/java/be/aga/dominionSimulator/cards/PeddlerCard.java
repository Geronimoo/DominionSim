package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class PeddlerCard extends DomCard {
    public PeddlerCard () {
      super( DomCardName.Peddler);
    }

    public void play() {
      owner.addActions(1);
      owner.addAvailableCoins(1);
      owner.drawCards(1);
    }
    
    @Override
    public int getPlayPriority() {
        if (owner.getCardsFromHand(DomCardName.Peddler).size()==1 && owner.getPlayStrategyFor(this)== DomPlayStrategy.combo && !owner.getCardsFromHand(DomCardName.Stonemason).isEmpty())
            return owner.getCardsFromHand(DomCardName.Stonemason).get(0).getPlayPriority()+1;

        if (owner.getCardsFromHand(DomCardName.Peddler).size()==1 && owner.getPlayStrategyFor(this)== DomPlayStrategy.combo && !owner.getCardsFromHand(DomCardName.Expand).isEmpty())
            return owner.getCardsFromHand(DomCardName.Expand).get(0).getPlayPriority()+1;

        if (owner.getCardsFromHand(DomCardName.Peddler).size()==1 && owner.getPlayStrategyFor(this)== DomPlayStrategy.combo && !owner.getCardsFromHand(DomCardName.Apprentice).isEmpty())
            return owner.getCardsFromHand(DomCardName.Apprentice).get(0).getPlayPriority()+1;

        //TODO it's one of the best combos with Salvager, but can't handle multiple buys (yet)
//    	if (!owner.getCardsFromHand(DomCardName.Salvager).isEmpty()
//    	&& owner.getCardsFromHand(DomCardName.Peddler).size()==1
//    	&& !owner.stillInEarlyGame()) {
//    		//little fix for Salvager so we have a shot at $8 in the end game
//    		return owner.getCardsFromHand(DomCardName.Salvager).get(0).getPlayPriority()+1;
//    	}
    	return super.getPlayPriority();
    }

}