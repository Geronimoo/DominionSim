package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomSet;
import org.junit.Test;

import static org.junit.Assert.*;

public class SetsContainsAllCardsTest {

    @Test
    public void testSetsContainAllCards() {
        int i = 0;
        for (DomCardName theCard: DomCardName.values()) {
            if (theCard.hasCardType(DomCardType.Ruins))
                continue;
            if (theCard.hasCardType(DomCardType.Split_Pile_Bottom))
                continue;
            if (theCard.hasCardType(DomCardType.Hex) || theCard.hasCardType(DomCardType.Boon))
                continue;
            if (theCard==DomCardName.Bat
                    || theCard==DomCardName.Madman
                    || theCard==DomCardName.Spoils
                    || theCard==DomCardName.Wish
                    || theCard==DomCardName.Zombie_Apprentice
                    || theCard==DomCardName.Zombie_Mason
                    || theCard==DomCardName.Zombie_Spy
                    || theCard == DomCardName.Mercenary)
                continue;
            if (theCard.hasCardType(DomCardType.Traveller) && !theCard.hasCardType(DomCardType.Kingdom))
                continue;
            if (theCard.hasCardType(DomCardType.Castle))
                continue;
            if (theCard.hasCardType(DomCardType.Knight))
                continue;
            if (theCard.hasCardType(DomCardType.Heirloom))
                continue;
            if (theCard.hasCardType(DomCardType.Spirit))
                continue;
            if (theCard.hasCardType(DomCardType.Shelter))
                continue;
            if (theCard.hasCardType(DomCardType.State))
                continue;

            boolean found = false;
            for (DomSet theSet : DomSet.values()){
                if (theSet.getCards().contains(theCard))
                    found=true;
            }
            assertTrue("Card " +theCard + " not found",found);
            i++;
        }
        System.out.println(i);
    }

}