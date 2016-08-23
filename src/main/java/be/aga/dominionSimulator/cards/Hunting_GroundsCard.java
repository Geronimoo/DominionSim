package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class Hunting_GroundsCard extends DomCard {
    public Hunting_GroundsCard() {
      super( DomCardName.Hunting_Grounds);
    }

    public void play() {
        owner.drawCards(4);
    }

    @Override
    public void doWhenTrashed() {
        if (owner.wants(DomCardName.Gardens) && owner.wants(DomCardName.Estate)) {
            gainEstates();
            return;
        }
        if (owner.getCurrentGame().countInSupply(DomCardName.Duchy)>0)
            owner.gain(DomCardName.Duchy);
        else
            gainEstates();
    }

    private void gainEstates() {
        owner.gain(DomCardName.Estate);
        owner.gain(DomCardName.Estate);
        owner.gain(DomCardName.Estate);
    }

    @Override
    public int getPlayPriority() {
        return owner.getActionsLeft()>1 ? 6 : super.getPlayPriority();
    }
}