package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class CultistCard extends DomCard {
    public CultistCard () {
      super( DomCardName.Cultist);
    }

    public void play() {
      owner.drawCards(2);
      for (DomPlayer thePlayer : owner.getOpponents()) {
        if (!thePlayer.checkDefense()) {
          thePlayer.gain(DomCardName.Ruins);
        }
      }
      if (!owner.getCardsFromHand(DomCardName.Cultist).isEmpty()) {
          if (owner.isHumanOrPossessedByHuman()) {
              owner.setNeedsToUpdateGUI();
              if (owner.getEngine().getGameFrame().askPlayer("<html>Chain " + DomCardName.Cultist.toHTML() + " ?</html>", "Resolving " + this.getName().toString()))
                  owner.play(owner.removeCardFromHand(owner.getCardsFromHand(DomCardName.Cultist).get(0)));
          } else {
              owner.play(owner.removeCardFromHand(owner.getCardsFromHand(DomCardName.Cultist).get(0)));
          }
      }
    }

    @Override
    public void doWhenTrashed() {
        owner.drawCards(3);
    }
}