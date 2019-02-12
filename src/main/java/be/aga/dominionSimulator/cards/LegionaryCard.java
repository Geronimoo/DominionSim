package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class LegionaryCard extends DomCard {
    public LegionaryCard() {
      super( DomCardName.Legionary);
    }

    public void play() {
      owner.addAvailableCoins(3);
      if (owner.getCardsFromHand(DomCardName.Gold).isEmpty())
        return;
      if (owner.isHumanOrPossessedByHuman()
         && !owner.getEngine().getGameFrame().askPlayer("<html>Reveal " + DomCardName.Gold.toHTML() +"?</html>", "Resolving " + this.getName().toString()))
         return;
      if (DomEngine.haveToLog) DomEngine.addToLog( owner + " reveals " + owner.getCardsFromHand(DomCardName.Gold).get(0));
      for (DomPlayer thePlayer : owner.getOpponents()) {
          if (!thePlayer.checkDefense()) {
              thePlayer.doForcedDiscard(thePlayer.getCardsInHand().size() - 2, false);
              thePlayer.drawCards(1);
          }
      }
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}