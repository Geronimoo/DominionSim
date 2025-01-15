package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class AmassCard extends DomCard {
    public AmassCard() {
      super( DomCardName.Amass);
    }

  public void play() {
    if (!owner.getCardsFromPlay(DomCardType.Action).isEmpty())
        return;
    DomCardName theDesiredCard = owner.getDesiredCard(DomCardType.Action ,new DomCost(5,0),false,false,null);
    if (theDesiredCard!=null)
	  owner.gain(theDesiredCard);
  }
}