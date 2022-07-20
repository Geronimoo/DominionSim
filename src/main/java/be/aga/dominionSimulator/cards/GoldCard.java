package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class GoldCard extends DomCard {

    public GoldCard() {
        super(DomCardName.Gold);
    }

    @Override
    public void play() {
        if (owner.isEnviousActive()) {
            owner.addAvailableCoinsSilent(1);
        } else {
            owner.addAvailableCoinsSilent(3);
        }
        if (owner.isAttackedByCorsair() && !owner.hasTrashedForCorsair() ) {
            owner.setTrashedForCorsair(true);
            owner.removeCardFromPlay(this);
            owner.trash(this);
        }
    }

    @Override
    public int getPlayPriority() {
        if (owner.count(DomCardName.Magic_Lamp)>0
                && !owner.getCardsFromHand(DomCardName.Magic_Lamp).isEmpty()
                && owner.getCardsFromPlay(DomCardName.Gold).isEmpty())
            return 5;
        return super.getPlayPriority();
    }

    @Override
    public void doWhenGained() {
        if (owner.getCurrentGame().getBoard().isLandmarkActive(DomCardName.Aqueduct))
            owner.getCurrentGame().getBoard().moveVPFromTo(this.getName(),DomCardName.Aqueduct);
    }
}