package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.enums.DomCardName;

public class MastermindCard extends MultiplicationCard {

    private boolean mastermindedThisTurn=false;

    public MastermindCard() {
      super( DomCardName.Mastermind);
    }

    @Override
    public void play() {
        mastermindedThisTurn=false;
    }

    @Override
    public void cleanVariablesFromPreviousGames() {
        mastermindedThisTurn=false;
        super.cleanVariablesFromPreviousGames();
    }

    @Override
    public void resolveDuration() {
        //weird stuff happens when we Masterminded a Mastermind
        if (myDurationCards.isEmpty() || mastermindedThisTurn) {
            super.play();
            mastermindedThisTurn=true;
        } else {
            super.resolveDuration();
        }
    }

    @Override
    public boolean mustStayInPlay() {
        if (mastermindedThisTurn && !myDurationCards.isEmpty()) {
            mastermindedThisTurn=false;
            return true;
        }
        mastermindedThisTurn=false;
        return super.mustStayInPlay();
    }

    @Override
    public boolean wantsToBePlayed() {
        return true;
    }

    @Override
    public int getPlayPriority() {
        return DomCardName.Mastermind.getPlayPriority();
    }


}