package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Shanty_TownCard extends DomCard {
    public Shanty_TownCard () {
      super( DomCardName.Shanty_Town);
    }

    public void play() {
      owner.addActions(2);
      owner.showHand();
      if (owner.getCardsFromHand(DomCardType.Action).isEmpty())
    	owner.drawCards(2);
    }
    
    @Override
    public int getPlayPriority() {
    	if (wantsToBePlayed())
    		return 0;
    	return 1000;
    }
    
    @Override
	public boolean wantsToBePlayed() {
    	int theIndex = owner.getCardsInHand().indexOf(this);
    	if (theIndex<0)
    		//possible if played by Golem
    		return true;
    	//we remove this Shanty Town temporarily to make checks easier
    	owner.getCardsInHand().remove(this);
    	
    	boolean wantsToBePlayed = false;
    	
    	if (owner.getCardsFromHand(DomCardType.Action).size()==0)
    		wantsToBePlayed=true;

    	if (owner.getCardsFromHand(DomCardName.Shanty_Town).size()>0)
    		wantsToBePlayed=true;

    	if (owner.getActionsLeft()==1 
    	 && !owner.getCardsFromHand(DomCardName.Minion).isEmpty())
    		wantsToBePlayed=true;

    	if (owner.getActionsLeft()==1 
    	 && owner.getCardsFromHand(DomCardType.Terminal).size()>=2 
    	 && owner.getCardsFromHand(DomCardType.Action).size()==owner.getCardsFromHand(DomCardType.Terminal).size())
    		wantsToBePlayed=true;
    	
    	if (owner.getActionsLeft()==1 
    	 && owner.getCardsFromHand(DomCardType.Card_Advantage).size()>=1
    	 && owner.getCardsFromHand(DomCardType.Action).size()==owner.getCardsFromHand(DomCardType.Card_Advantage).size())
    		wantsToBePlayed=true;

    	if (owner.getActionsLeft()==1 
    	 && owner.getCardsFromHand(DomCardType.Terminal).size()==1
    	 && owner.getCardsFromHand(DomCardType.Action).size()==1    	 
    	 && owner.getCardsFromHand(DomCardName.Horn_of_Plenty).size()>0)
    		wantsToBePlayed=true;

        if (owner.getActionsLeft()==1
                && owner.getCardsFromHand(DomCardName.Young_Witch).size()==owner.getCardsFromHand(DomCardType.Action).size())
            wantsToBePlayed=true;

    	owner.getCardsInHand().add(theIndex,this);
    	
		return wantsToBePlayed;
	}
}