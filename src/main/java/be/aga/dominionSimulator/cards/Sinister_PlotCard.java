package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Sinister_PlotCard extends DomCard {
    public Sinister_PlotCard() {
      super( DomCardName.Sinister_Plot);
    }

    public void trigger() {

        owner.setNeedsToUpdateGUI();
        if (owner.getEngine().getGameFrame().askPlayer("<html>Draw " +
                owner.getSinisterPlotTokens() +" cards?", "Resolving " + this.getName().toString())) {
            owner.drawCards(owner.getSinisterPlotTokens());
            owner.setSinisterPlotTokens(0);
        } else {
            owner.setSinisterPlotTokens(owner.getSinisterPlotTokens()+1);
        }
    }
}