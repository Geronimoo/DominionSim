package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomArtifact;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class SwashbucklerCard extends DomCard {
    public SwashbucklerCard() {
      super( DomCardName.Swashbuckler);
    }

    public void play() {
        owner.drawCards(3);
        if (owner.getCardsFromDiscard().isEmpty())
            return;
        owner.addCoffers(1);
        if (owner.getCoffers()>=4 && owner.getCurrentGame().getArtifactOwner(DomArtifact.Treasure_Chest)!=owner)
            owner.getCurrentGame().giveArtifactTo(DomArtifact.Treasure_Chest, owner);
    }
}