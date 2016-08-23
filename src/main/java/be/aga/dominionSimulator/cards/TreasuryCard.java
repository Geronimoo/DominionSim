package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class TreasuryCard extends DomCard {
    public TreasuryCard () {
      super( DomCardName.Treasury);
    }

    public void play() {
      owner.addActions(1);
      owner.addAvailableCoins(1);
      owner.drawCards(1);
    }
    
    @Override
    public void handleCleanUpPhase() {
      for (DomCard theCard : owner.getBoughtCards()) {
        if (theCard.hasCardType( DomCardType.Victory )) {
          super.handleCleanUpPhase();
          return;
        }
      }
      owner.putOnTopOfDeck( this );
    }
}