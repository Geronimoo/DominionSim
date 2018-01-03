package be.aga.dominionSimulator.gui.util;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.cards.MultiplicationCard;
import be.aga.dominionSimulator.enums.DomCardName;

import javax.swing.*;
import java.awt.*;

public class CardRenderer<T> extends JLabel implements ListCellRenderer<T> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8431906023861037433L;

    @Override
    public Component getListCellRendererComponent(JList<? extends T> list, T value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value.equals(DomCard.NONEXISTANT_CARD)) {
            setText("<html><i>nothing</i></html>");
        }else {
            if (value instanceof DomCardName) {
                setText("<html>" + ((DomCardName) value).toHTML() + "</html>");
            } else {
            	DomCard card = (DomCard) value;
                String theShapeShifterTxt = "";
                if (card.getShapeshifterCard() != null) {
                    theShapeShifterTxt = " (" + card.getShapeshifterCard().getName().toHTML() + ")";
                }
                String theEstateTxt = "";
                if (card.getEstateCard() != null) {
                    theEstateTxt = " (" + card.getEstateCard().getName().toHTML() + ")";
                }
                String theDurationTxt = "";
                if (card instanceof MultiplicationCard && ((MultiplicationCard)card).getDurationsString()!=null) {
                    theDurationTxt = " (" + ((MultiplicationCard)card).getDurationsString()+")";
                }
                setText("<html>" + card.getName().toHTML() + theShapeShifterTxt + theEstateTxt + theDurationTxt + "</html>");
            }
        }
        return this;
    }
}
