package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Royal_BlacksmithCard extends DomCard {
    public Royal_BlacksmithCard() {
      super( DomCardName.Royal_Blacksmith);
    }

    @Override
    public void play() {
      owner.drawCards( 5 );
      while (!owner.getCardsFromHand(DomCardName.Copper).isEmpty())
          owner.discardFromHand(DomCardName.Copper);
      owner.revealHand();
    }
    
    @Override
    public int getPlayPriority() {
      return owner.getActionsAndVillagersLeft()>1 ? 6 : super.getPlayPriority();
    }

    @Override
    public boolean wantsToBePlayed() {
        if (owner.wants(DomCardName.Villa) && owner.getTotalAvailableCoins()>=4)
            return false;
        if (owner.getActionsAndVillagersLeft()==1 && !owner.getCardsFromHand(DomCardName.Counting_House).isEmpty())
            return false;
        return true;
    }
}