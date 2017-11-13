package be.aga.dominionSimulator.gui.util;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.cards.MultiplicationCard;
import be.aga.dominionSimulator.enums.DomCardName;

import javax.swing.*;
import java.awt.*;

public class CardRenderer extends JLabel implements ListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value instanceof String) {
            setText("<html><i>nothing</i></html>");
        }else {
            if (value instanceof DomCardName) {
                setText("<html>" + ((DomCardName) value).toHTML() + "</html>");
            } else {
                String theShapeShifterTxt = "";
                if (((DomCard) value).getShapeshifterCard() != null) {
                    theShapeShifterTxt = " (" + ((DomCard) value).getShapeshifterCard().getName().toHTML() + ")";
                }
                String theEstateTxt = "";
                if (((DomCard) value).getEstateCard() != null) {
                    theEstateTxt = " (" + ((DomCard) value).getEstateCard().getName().toHTML() + ")";
                }
                String theDurationTxt = "";
                if (((DomCard)value) instanceof MultiplicationCard && ((MultiplicationCard)value).getDurationsString()!=null) {
                    theDurationTxt = " (" + ((MultiplicationCard)value).getDurationsString()+")";
                }
                setText("<html>" + ((DomCard) value).getName().toHTML() + theShapeShifterTxt + theEstateTxt + theDurationTxt + "</html>");
            }
        }
        return this;
    }
}
