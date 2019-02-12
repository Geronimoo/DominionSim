package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class MoneylenderCard extends DomCard {
    public MoneylenderCard () {
      super( DomCardName.Moneylender);
    }

    public void play() {
      ArrayList<DomCard> theCoppers = owner.getCardsFromHand(DomCardName.Copper); 
      if (!theCoppers.isEmpty()) {
        if (owner.isHumanOrPossessedByHuman())
            if (!owner.getEngine().getGameFrame().askPlayer("<html>Trash " + DomCardName.Copper.toHTML() +"</html>", "Resolving " + this.getName().toString()))
                return;
    	owner.trash(owner.removeCardFromHand(theCoppers.get(0)));
    	owner.addAvailableCoins(3);
      }
    }
    
    @Override
    public int getPlayPriority() {
        if (owner.getCardsFromHand(DomCardName.Copper).isEmpty()) 
        	return 100;
    	return super.getPlayPriority();
    }
    
    public int getDiscardPriority(int i) {
    	if (owner!=null)
          if (owner.getCardsFromHand(DomCardName.Copper).isEmpty()) 
        	return 0;
    	return super.getDiscardPriority(i);
    }

    @Override
    public int getTrashPriority() {
        if (owner==null)
            return super.getTrashPriority();
        if (owner.countInDeck(DomCardName.Copper)==0)
            return DomCardName.Curse.getTrashPriority()+1;
        return super.getTrashPriority();
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}