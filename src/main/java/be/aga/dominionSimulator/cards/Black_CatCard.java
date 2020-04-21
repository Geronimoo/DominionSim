package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class Black_CatCard extends DomCard {
    public Black_CatCard() {
      super( DomCardName.Black_Cat);
    }

    public void play() {
        owner.drawCards(2);
        if (owner.getCurrentGame().getActivePlayer()!=owner) {
            for (DomPlayer player : owner.getOpponents()) {
                if (!player.checkDefense())
                    player.gain(DomCardName.Curse);
            }
        }
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