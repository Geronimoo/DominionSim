package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class Hunting_GroundsCard extends DomCard {
    public Hunting_GroundsCard() {
        super(DomCardName.Hunting_Grounds);
    }

    public void play() {
        owner.drawCards(4);
    }

    @Override
    public void doWhenTrashed() {
        if (owner.isHumanOrPossessedByHuman()) {
            ArrayList<String> theOptions = new ArrayList<String>();
            theOptions.add("Gain a Duchy");
            theOptions.add("Gain 3 Estates");
            int theChoice = owner.getEngine().getGameFrame().askToSelectOption("Select for Hunting Grounds", theOptions, "Mandatory!");
            if (theChoice == 0) {
                owner.gain(DomCardName.Duchy);
            } else {
                gainEstates();
            }
        } else {
            if (owner.wants(DomCardName.Gardens) && owner.wants(DomCardName.Estate)) {
                gainEstates();
            } else {
                if (owner.getCurrentGame().countInSupply(DomCardName.Duchy) > 0)
                    owner.gain(DomCardName.Duchy);
                else
                    gainEstates();
            }
        }
    }

    private void gainEstates() {
        owner.gain(DomCardName.Estate);
        owner.gain(DomCardName.Estate);
        owner.gain(DomCardName.Estate);
    }

    @Override
    public int getPlayPriority() {
        return owner.getActionsAndVillagersLeft() > 1 ? 6 : super.getPlayPriority();
    }
}