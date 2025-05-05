package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.HashSet;

public class RiceCard extends DomCard {
    public RiceCard() {
      super( DomCardName.Rice);
    }
    
    @Override
    public void play() {
      owner.addAvailableBuys(1);
      HashSet<DomCardType> theTypes = new HashSet<>();
      for (DomCard theCard : owner.getCardsInPlay()) {
          for (DomCardType type : theCard.getName().types()) {
              if (type.isLegal())
                  theTypes.add(type);
          }
      }
      owner.addAvailableCoins(theTypes.size());
    }
}