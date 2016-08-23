package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class UniversityCard extends DomCard {
    public UniversityCard () {
      super( DomCardName.University);
    }

  public void play() {
    owner.addActions(2);
    DomCardName theDesiredCard = owner.getDesiredCard(DomCardType.Action
    		                                               ,new DomCost(5,0) 
     													   ,false
     													   ,false
     													   ,null);
    if (theDesiredCard!=null)
	  owner.gain(theDesiredCard);
  }
}