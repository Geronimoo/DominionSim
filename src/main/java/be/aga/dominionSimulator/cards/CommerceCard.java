package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class CommerceCard extends DomCard {
    public CommerceCard() {
      super( DomCardName.Commerce);
    }

    public void play() {
      ArrayList<DomCardName> theList = new ArrayList<DomCardName>();
      for (DomCardName theCard : owner.getCardsGainedLastTurn()){
          if (!theList.contains(theCard))
              theList.add(theCard);
      }
      for (DomCardName elem : theList) {
    	  owner.gain(DomCardName.Gold);
      }
    }
}