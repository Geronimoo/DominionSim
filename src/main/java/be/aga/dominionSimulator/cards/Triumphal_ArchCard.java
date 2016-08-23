package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Triumphal_ArchCard extends DomCard {
    public Triumphal_ArchCard() {
        super(DomCardName.Triumphal_Arch);
    }

    public static int countVP(DomPlayer aPlayer ){
        int theCount = 0;
        int theMax = 0;
        DomCardName theMaxAction = null;
        int theSecondMax = 0;
        DomCardName theSecondMaxAction = null;
        for (DomCardName theCard : aPlayer.getDeck().keySet()) {
            if (!theCard.hasCardType(DomCardType.Action))
                continue;
            if (aPlayer.countInDeck(theCard)>theMax) {
                theMax=aPlayer.countInDeck(theCard);
                theMaxAction=theCard;
            }
        }
        for (DomCardName theCard : aPlayer.getDeck().keySet()) {
            if (theCard.hasCardType(DomCardType.Action) && theCard!=theMaxAction) {
                if (aPlayer.countInDeck(theCard) < theMax && aPlayer.countInDeck(theCard)>theSecondMax) {
                    theSecondMax = aPlayer.countInDeck(theCard);
                    theSecondMaxAction = theCard;
                }
            }
        }
        if (theSecondMaxAction==null)
            return 0;
        return aPlayer.countInDeck(theSecondMaxAction)*3;
    }
}