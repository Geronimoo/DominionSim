package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class MonkeyCard extends DomCard {
    public MonkeyCard() {
      super( DomCardName.Monkey);
    }

    @Override
    public void play() {
        if (owner.getOpponents().isEmpty())
            return;
        DomPlayer theRightOpponent = owner.getOpponents().get(owner.getOpponents().size()-1);
        theRightOpponent.setAnnoyedByMonkey(true);
    }

    public void resolveDuration() {
      owner.drawCards(1);
    }
}