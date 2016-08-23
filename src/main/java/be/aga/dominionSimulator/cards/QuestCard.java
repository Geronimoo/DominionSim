package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class QuestCard extends DomCard {

	public QuestCard() {
      super( DomCardName.Quest);
    }

    public void play() {
        if (owner.getCardsInHand().size()>=6) {
            while (!owner.getCardsInHand().isEmpty()) {
                owner.discardFromHand(owner.getCardsInHand().get(0));
            }
            owner.gain(DomCardName.Gold);
            return;
        }
        if (owner.getCardsFromHand(DomCardName.Curse).size()>=2) {
            owner.discardFromHand(owner.getCardsFromHand(DomCardName.Curse).get(0));
            owner.discardFromHand(owner.getCardsFromHand(DomCardName.Curse).get(0));
            owner.gain(DomCardName.Gold);
            return;
        }
        if (!owner.getCardsFromHand(DomCardType.Attack).isEmpty()) {
            owner.discardFromHand(owner.getCardsFromHand(DomCardType.Attack).get(0));
            owner.gain(DomCardName.Gold);
            return;
        }
   }
}