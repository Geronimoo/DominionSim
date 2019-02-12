package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class Old_WitchCard extends DomCard {
    public Old_WitchCard() {
      super( DomCardName.Old_Witch);
    }

    public void play() {
      owner.drawCards(3);
      for (DomPlayer thePlayer : owner.getOpponents()) {
          if (!thePlayer.checkDefense()) {
              thePlayer.gain(DomCardName.Curse);
              if (thePlayer.getCardsFromHand(DomCardName.Curse).isEmpty())
                  continue;
              if (!thePlayer.isHumanOrPossessedByHuman()
                      || owner.getEngine().getGameFrame().askPlayer("<html>Trash " + DomCardName.Curse.toHTML() + "?</html>", "Resolving " + this.getName().toString())) {
                  thePlayer.trash(thePlayer.removeCardFromHand(thePlayer.getCardsFromHand(DomCardName.Curse).get(0)));
              }
          }
      }
    }
    
    @Override
    public int getPlayPriority( ) {
      if (owner!=null) {
          if (owner.getCurrentGame().countInSupply( DomCardName.Curse )==0)
              return DomCardName.Smithy.getPlayPriority();
      }
      return super.getPlayPriority();
    }
}