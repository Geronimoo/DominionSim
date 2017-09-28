package be.aga.dominionSimulator.gui.util;

import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPhase;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class TableCardRenderer extends DefaultTableCellRenderer implements TableCellRenderer {

    private final DomEngine myEngine;

    public TableCardRenderer(DomEngine anEngine) {
        myEngine = anEngine;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel theOriginal = (JLabel) super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
        if (value==null)
            return theOriginal;
        StringBuilder theText = new StringBuilder("<html>");
        DomCardName theCard = (DomCardName) value;
        theText.append("$").append(theCard.getCoinCost(null));
        if (theCard.getPotionCost()>0)
            theText.append("P");
        theText.append("&nbsp;&nbsp;&nbsp;");
        if (myEngine.getCurrentGame().countInSupply(theCard)>0 && myEngine.getCurrentGame().getActivePlayer().getPhase()== DomPhase.Buy && myEngine.getCurrentGame().getActivePlayer().canBuy(theCard)) {
            theText.append("<b>");
        }
        theText.append(theCard.toHTML());
        if (myEngine.getCurrentGame().countInSupply(theCard)>0 && myEngine.getCurrentGame().getActivePlayer().getPhase()== DomPhase.Buy && myEngine.getCurrentGame().getActivePlayer().canBuy(theCard)) {
            theText.append("</b>");
        }
        theText.append(" (").append(myEngine.getCurrentGame().countInSupply(theCard)).append(")").append("</html>");
        theOriginal.setText(theText.toString());
        return theOriginal;
    }
}
