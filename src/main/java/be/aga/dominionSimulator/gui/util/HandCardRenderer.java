package be.aga.dominionSimulator.gui.util;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPhase;

import javax.swing.*;
import java.awt.*;

public class HandCardRenderer extends JLabel implements ListCellRenderer<DomCard> {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8940309454492428884L;

    @Override
    public Component getListCellRendererComponent(JList<? extends DomCard> list, DomCard value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value.equals(DomCard.NONEXISTANT_CARD)) {
            setText("<html><i>nothing</i></html>");
        } else {
            DomCard theCard = (DomCard) value;
            int theNumber = theCard.owner.getCardsFromHand(theCard.getName()).size();
            if (theCard.owner.getCurrentGame().getActivePlayer()==theCard.owner) {
                if ((theCard.owner.getPhase()== DomPhase.Action && theCard.hasCardType(DomCardType.Action))
                        ||(theCard.owner.getPhase()== DomPhase.Buy_PlayTreasures && theCard.hasCardType(DomCardType.Treasure))
                        ||(theCard.owner.getPhase()==DomPhase.Night && theCard.hasCardType(DomCardType.Night))){
                    setText("<html><u>" + ((DomCard) value).getName().toHTML() + "</u>"+(theNumber>1?" ("+theNumber+")" : "")+"</html>");
                } else {
                    setText("<html>" + ((DomCard) value).getName().toHTML() + (theNumber>1?" ("+theNumber+")" : "") +"</html>");
                }
            } else {
                setText("<html>" + ((DomCard) value).getName().toHTML() + (theNumber > 1 ?" ("+theNumber+")" : "") +"</html>");
            }
        }
        return this;
    }
}
