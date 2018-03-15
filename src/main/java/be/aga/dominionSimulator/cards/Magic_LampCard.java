package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class Magic_LampCard extends DomCard {
    public Magic_LampCard() {
      super( DomCardName.Magic_Lamp);
    }

    public void play() {
      owner.addAvailableCoins(1);
      if (getSingleCardsInPlay()>=6) {
          owner.gain(DomCardName.Wish);
          owner.gain(DomCardName.Wish);
          owner.gain(DomCardName.Wish);
          owner.trash(owner.removeCardFromPlay(this));
      }
    }

    public  int getSingleCardsInPlay(){
        ArrayList<DomCardName> theSingleCards = new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInPlay()) {
            if (owner.getCardsFromPlay(theCard.getName()).size()==1) {
                theSingleCards.add(theCard.getName());
            }
        }
        return theSingleCards.size();
    }

    @Override
    public int getPlayPriority( ) {
      if (getSingleCardsInPlay()>=5)
          return 0;
      return super.getPlayPriority( );
    }
}