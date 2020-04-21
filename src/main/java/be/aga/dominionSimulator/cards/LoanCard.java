package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class LoanCard extends DomCard {
    public LoanCard () {
      super( DomCardName.Loan);
    }

    public void play() {
        owner.addAvailableCoins(1);
        ArrayList< DomCard > theCards = owner.revealUntilType(DomCardType.Treasure);
        for (DomCard theCard : theCards) {
            if (owner.isHumanOrPossessedByHuman()) {
                if (theCard.hasCardType(DomCardType.Treasure)
                     && owner.getEngine().getGameFrame().askPlayer("<html>Trash " + theCard.getName().toHTML() +" ?</html>", "Resolving " + this.getName().toString())) {
                    owner.trash(theCard);
                }else {
                    owner.discard(theCard);
                }
            } else {
                if (theCard.getName() == DomCardName.Copper
                        || (theCard.getName() == DomCardName.Silver && owner.count(DomCardName.Venture) > 3)
                        || (theCard.getName() == DomCardName.Silver && owner.count(DomCardName.Platinum) > 0)
                        || (theCard.getName() == DomCardName.Loan && owner.count(DomCardName.Copper) < 2)) {
                    owner.trash(theCard);
                } else {
                    owner.discard(theCard);
                }
            }
        }
    }
}