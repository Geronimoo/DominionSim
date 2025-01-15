package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class Secret_CaveCard extends DomCard {
    private boolean durationFailed = false;

    public Secret_CaveCard() {
      super( DomCardName.Secret_Cave);
    }

    public void play() {
      durationFailed = true;
      owner.addActions(1);
      owner.drawCards(1);
      if (owner.getCardsInHand().size()<3)
          return;
      if (owner.isHumanOrPossessedByHuman()) {
         handleHuman();
      } else {
          if (!owner.isGoingToBuyTopCardInBuyRules(owner.getTotalPotentialCurrency()) && owner.getCardsFromHand(DomCardName.Magic_Lamp).isEmpty()) {
              owner.doForcedDiscard(3, false);
              durationFailed = false;
          }
      }
    }

    private void handleHuman() {
        durationFailed = true;
        owner.setNeedsToUpdateGUI();
        if (!owner.getEngine().getGameFrame().askPlayer("<html>Discard 3 cards ?</html>", "Resolving " + this.getName().toString()))
           return;
        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
        owner.getEngine().getGameFrame().askToSelectCards("Discard 3 cards" , owner.getCardsInHand(), theChosenCards, 3);
        for (DomCard theCardName: theChosenCards) {
            owner.discardFromHand(owner.getCardsFromHand(theCardName.getName()).get(0));
        }
        durationFailed =false;
    }

    public boolean durationFailed() {
        return durationFailed;
    }

    @Override
    public void resolveDuration() {
        owner.addAvailableCoins(3);
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}