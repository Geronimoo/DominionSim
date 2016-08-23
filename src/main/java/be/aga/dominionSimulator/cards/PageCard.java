package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class PageCard extends DomCard {
    public PageCard() {
      super( DomCardName.Page);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
    }

    @Override
    public void handleCleanUpPhase() {
        if (owner==null)
            return;
        if (owner.wants(DomCardName.Treasure_Hunter)) {
            DomPlayer theOwner = owner;
            owner.returnToSupply(this);
            theOwner.gain(DomCardName.Treasure_Hunter);
            return;
        }
        super.handleCleanUpPhase();
    }
}