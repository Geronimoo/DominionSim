package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class PathfindingCard extends DomCard {

	public PathfindingCard() {
      super( DomCardName.Pathfinding);
    }

    public void play() {
        owner.placePlusOneCardToken();
    }
}