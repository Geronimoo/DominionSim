package be.aga.dominionSimulator.gui.util;

import be.aga.dominionSimulator.enums.DomCardName;

import javax.swing.*;
import java.awt.*;

public class CardRenderer extends JLabel implements ListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value instanceof String)
          setText("<html><i>nothing</i></html>");
        else
          setText("<html>"+((DomCardName)value).toHTML()+"</html>");
        return this;
    }
}
