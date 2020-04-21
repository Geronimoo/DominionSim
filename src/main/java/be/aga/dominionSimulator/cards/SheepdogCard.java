package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class SheepdogCard extends DomCard {
    public SheepdogCard() {
      super( DomCardName.Sheepdog);
    }

    public void play() {
        owner.drawCards(2);
    }
    
     public boolean wantsToReact() {
         if (owner.isHumanOrPossessedByHuman()) {
             owner.setNeedsToUpdateGUI();
             return owner.getEngine().getGameFrame().askPlayer("<html>React with " + this.getName().toHTML() +" ?</html>", "Resolving " + this.getName().toString());
         } else {
             return true;
         }
     }
}