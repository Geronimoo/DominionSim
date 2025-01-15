package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Small_PotatoesCard extends DomCard {

	public Small_PotatoesCard() {
      super( DomCardName.Small_Potatoes);
    }

    public void play() {        
      owner.increaseSmallPotatoesPlayedCounter();
   }
}