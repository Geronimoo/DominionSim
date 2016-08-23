package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class BanquetCard extends DomCard {
    public BanquetCard() {
      super( DomCardName.Banquet);
    }

  public void play() {
    owner.gain(DomCardName.Copper);
    owner.gain(DomCardName.Copper);
    DomCardName theDesiredCard = owner.getDesiredCard(null,new DomCost(5,0),false,false,DomCardType.Victory);
    if (theDesiredCard==null)
        theDesiredCard=owner.getCurrentGame().getBoard().getBestCardInSupplyFor(owner,null,new DomCost(5,0),false, DomCardType.Victory);
    if (theDesiredCard!=null)
	  owner.gain(theDesiredCard);
  }
}