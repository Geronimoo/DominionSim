package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.HashSet;

public class KeepCard extends DomCard {
    public KeepCard() {
        super(DomCardName.Keep);
    }

    public static int countVP(DomPlayer aPlayer ){
        int theVP = 0;
        for (DomCardName theCard : aPlayer.getDeck().keySet()) {
            if (theCard.hasCardType(DomCardType.Treasure)) {
                HashSet<DomPlayer> theWinners = new HashSet<DomPlayer>();
                int theMaxCount = 0;
                for (DomPlayer thePlayer : aPlayer.getCurrentGame().getPlayers()) {
                    if (thePlayer.getDeck().get(theCard)!=null) {
                        if (thePlayer.getDeck().get(theCard).size() > theMaxCount) {
                            theWinners.clear();
                            theWinners.add(thePlayer);
                            theMaxCount=thePlayer.getDeck().get(theCard).size();
                        } else {
                            if (thePlayer.getDeck().get(theCard).size() == theMaxCount) {
                                theWinners.add(thePlayer);
                            }
                        }
                    }
                }
                if (theMaxCount > 0 && theWinners.contains(aPlayer))
                    theVP += 5;
            }
        }
        return theVP;
    }
}