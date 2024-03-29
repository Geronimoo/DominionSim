package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class CorsairCard extends DomCard {
    public CorsairCard() {
      super( DomCardName.Corsair);
    }

    public void play() {
        owner.addAvailableCoins(2);
        for (DomPlayer thePlayer : owner.getOpponents()) {
            if (!thePlayer.checkDefense()) {
               thePlayer.setAttackedByCorsair(true);
            }
        }
    }

    public void resolveDuration() {
        owner.drawCards(1);
        for (DomPlayer thePlayer : owner.getOpponents()) {
           thePlayer.setAttackedByCorsair(false);
        }
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }
}