package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class TormentorCard extends DomCard {
    public TormentorCard() {
      super( DomCardName.Tormentor);
    }

    public void play() {
        owner.addAvailableCoins(2);
        if (owner.getCardsInPlay().size()>1) {
            for (DomPlayer thePlayer : owner.getOpponents()) {
                if (!thePlayer.checkDefense()) {
                    thePlayer.receiveHex(null);
                }
            }
        } else {
            owner.gain(DomCardName.Imp);
        }
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}