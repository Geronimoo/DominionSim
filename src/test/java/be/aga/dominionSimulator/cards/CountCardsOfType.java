package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import org.junit.Test;

public class CountCardsOfType {

    @Test
    public void countCards() {
      for(DomCardName theCard :DomCardName.values())
        if (theCard.hasCardType(DomCardType.Action)&&theCard.hasCardType(DomCardType.Treasure))
            System.out.println(theCard);
    }


}
