package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class FortressCard extends DomCard {
    public FortressCard() {
      super( DomCardName.Fortress);
    }

    public void play() {
      owner.addActions(2);
      owner.drawCards(1);
    }

    @Override
    public int getPlayPriority() {
        if (owner.getActionsLeft()>1 && !owner.getCardsFromHand(DomCardType.TrashForBenefit).isEmpty())
            return 35;
        if (!owner.getCardsFromHand(DomCardType.TrashForBenefit).isEmpty()&&!owner.getCardsFromHand(DomCardType.TrashForBenefit).get(0).hasCardType(DomCardType.Terminal))
            return 35;
        return super.getPlayPriority();
    }
}