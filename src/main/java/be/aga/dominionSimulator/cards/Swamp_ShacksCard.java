package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Swamp_ShacksCard extends DomCard {
    public Swamp_ShacksCard() {
      super( DomCardName.Swamp_Shacks);
    }

    public void play() {
      owner.addActions(2);
      if (owner.getCardsInPlay().size()>=3) {
          owner.drawCards(owner.getCardsInPlay().size()/3);
      }
    }

    @Override
    public int getPlayPriority() {
        if (owner.getActionsAndVillagersLeft()>1)
            return 500;
        if (owner.getActionsAndVillagersLeft()>0)
            return DomCardName.Wharf.getPlayPriority()-1;
        return super.getPlayPriority();
    }
}