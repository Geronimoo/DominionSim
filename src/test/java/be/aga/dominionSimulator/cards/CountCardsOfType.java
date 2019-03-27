package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import org.junit.Test;

public class CountCardsOfType {

    @Test
    public void countCards() {
      for(DomCardName theCard :DomCardName.values())
        if (theCard.hasCardType(DomCardType.Terminal)&&theCard.hasCardType(DomCardType.Card_Advantage))
            System.out.println(theCard);
    }

}
