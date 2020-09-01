package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class Bounty_HunterCard extends DomCard {
    public Bounty_HunterCard() {
      super( DomCardName.Bounty_Hunter);
    }

    public void play() {
      owner.addActions(1);
      if (owner.getCardsInHand().isEmpty())
          return;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      if (!owner.getCardsFromHand(DomCardName.Menagerie).isEmpty() && !DomPlayer.getMultiplesInHand((MenagerieCard) owner.getCardsFromHand(DomCardName.Menagerie).get(0)).isEmpty()) {
          ArrayList<DomCard> theMultiples = DomPlayer.getMultiplesInHand((MenagerieCard) owner.getCardsFromHand(DomCardName.Menagerie).get(0));
          Collections.sort(theMultiples, SORT_FOR_DISCARDING);
          exile(owner.removeCardFromHand(theMultiples.get(0)));
      }else {
          Collections.sort(owner.getCardsInHand(), SORT_FOR_DISCARDING);
          exile(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
      }
    }

    private void exile(DomCard domCard) {
        boolean bounty=true;
        for (DomCard aCard : owner.getDeck().getExileMat()){
            if (aCard.getName()==domCard.getName())
                bounty = false;
        }
        owner.exile(domCard);
        if (bounty)
            owner.addAvailableCoins(3);
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand()) {
            theChooseFrom.add(theCard.getName());
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Exile a card", theChooseFrom, "Mandatory!");
        exile(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
    }

    @Override
    public boolean wantsToBePlayed() {
        //empty hand possible with Vassal
        if (owner.getCardsInHand().size()<=1)
            return true;
        Collections.sort( owner.getCardsInHand() , SORT_FOR_TRASHING);
        if (owner.getCardsInHand().get(0).getTrashPriority()<= DomCardName.Copper.getTrashPriority())
            return true;
        if (!owner.getCardsFromHand(DomCardType.Treasure).isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }
}