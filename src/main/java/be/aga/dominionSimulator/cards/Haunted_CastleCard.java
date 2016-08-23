package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class Haunted_CastleCard extends DomCard {
    public Haunted_CastleCard() {
      super( DomCardName.Haunted_Castle);
    }

    @Override
    public void doWhenGained() {
        if (owner.getCurrentGame().getActivePlayer()!=owner)
            return;
        owner.gain(DomCardName.Gold);
        for (DomPlayer thePlayer : owner.getOpponents()) {
            if (thePlayer.getCardsInHand().size()>=5)
              thePlayer.doForcedDiscard(2, true);
        }
    }

    @Override
    public int getTrashPriority() {
        return 45;
    }
}