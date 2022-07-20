package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPhase;

public class VillaCard extends DomCard {
    public VillaCard() {
      super( DomCardName.Villa);
    }

    public void play() {
      owner.addActions(2);
      owner.addAvailableCoins(1);
      owner.addAvailableBuys(1);
    }

    @Override
    public void doWhenGained() {
        owner.addCardToHand(this);
        if (owner.getCurrentGame().getActivePlayer()==owner) {
            owner.addActions(1);
            if (owner.getPhase() == DomPhase.Buy_BuyStuff || owner.getPhase()==DomPhase.Buy_PlayTreasures) {
                //Guildhall and Merchant Guild add Coppers we can't use in the buy phase
                if (owner.isHumanOrPossessedByHuman()) {
                    owner.setPhase(DomPhase.Action);
                } else {
                    owner.triggerVilla();
                }
            }
        }
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}