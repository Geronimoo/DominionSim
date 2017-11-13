package be.aga.dominionSimulator.gui.util;

import be.aga.dominionSimulator.DomCard;
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
        DomCard theTopCard = null;
        if (myEngine.getCurrentGame().getBoard().get(theCard)!=null && !myEngine.getCurrentGame().getBoard().get(theCard).isEmpty())
            theTopCard = myEngine.getCurrentGame().getBoard().get(theCard).get(0);
        DomCardName theCardName = theTopCard == null ? theCard : theTopCard.getName();
        if (theCardName.getDebtCost()>0)
            theText.append(theCardName.getDebtCost()+"D");
        if (theCard.getCoinCost(myEngine.getCurrentGame())!=0 || (theCard.getPotionCost()==0 && theCard.getDebtCost()==0 && !theCard.hasCardType(DomCardType.Landmark))) {
            if (myEngine.getCurrentGame().getBoard().get(theCard)!=null && !myEngine.getCurrentGame().getBoard().get(theCard).isEmpty())
              theTopCard = myEngine.getCurrentGame().getBoard().get(theCard).get(0);
            theCardName = theTopCard == null ? theCard : theTopCard.getName();
            theText.append("$").append(theCardName.getCoinCost(myEngine.getCurrentGame()));
        }
        if (myEngine.getCurrentGame().getBoard().isFromSeparatePile(theCard))
            theText.append("*");
        if (theCard.getPotionCost()>0)
            theText.append("P");
        theText.append("&nbsp;&nbsp;&nbsp;");
        if (myEngine.getCurrentGame().countInSupply(theCard)==0 && !theCard.hasCardType(DomCardType.Event) && !theCard.hasCardType(DomCardType.Landmark) )
                theText.append("<strike>");
        if (!theCard.hasCardType(DomCardType.Landmark) && !myEngine.getCurrentGame().getBoard().isFromSeparatePile(theCard) && (myEngine.getCurrentGame().countInSupply(theCard)>0 || theCard.hasCardType(DomCardType.Event))&& myEngine.getCurrentGame().getActivePlayer().getPhase()== DomPhase.Buy ) {
            if (myEngine.getCurrentGame().getBoard().get(theCard).isEmpty())
                theTopCard = theCard.createNewCardInstance();
            else
                theTopCard = myEngine.getCurrentGame().getBoard().get(theCard).get(0);
            if (myEngine.getCurrentGame().getActivePlayer().canBuy(theTopCard.getName()))
              theText.append("<b>");
        }
        if (myEngine.getCurrentGame().getBoard().get(theCard)!=null && !myEngine.getCurrentGame().getBoard().get(theCard).isEmpty()) {
            theTopCard = myEngine.getCurrentGame().getBoard().get(theCard).get(0);
            theText.append(theTopCard.getName().toHTML());
        } else {
            theText.append(theCard.toHTML());
        }
        if (!theCard.hasCardType(DomCardType.Landmark) && !myEngine.getCurrentGame().getBoard().isFromSeparatePile(theCard) && (myEngine.getCurrentGame().countInSupply(theCard)>0 || theCard.hasCardType(DomCardType.Event)) && myEngine.getCurrentGame().getActivePlayer().getPhase()== DomPhase.Buy ) {
            if (myEngine.getCurrentGame().getBoard().get(theCard).isEmpty())
                theTopCard = theCard.createNewCardInstance();
            else
                theTopCard = myEngine.getCurrentGame().getBoard().get(theCard).get(0);
            if (myEngine.getCurrentGame().getActivePlayer().canBuy(theTopCard.getName()))
                theText.append("</b>");
        }
        if (myEngine.getCurrentGame().countInSupply(theCard)==0 && !theCard.hasCardType(DomCardType.Event) && !theCard.hasCardType(DomCardType.Landmark))
            theText.append("</strike>");
        if (myEngine.getCurrentGame().getBoard().countVPon(theCard)>0) {
            theText.append(" " + myEngine.getCurrentGame().getBoard().countVPon(theCard)+"&#x25BC;");
        }
        for (int i=0;i<myEngine.getCurrentGame().getEmbargoTokensOn(theCard);i++)
            theText.append(" <FONT style=\"BACKGROUND-COLOR: #CC8BC7\">E</font>");
        if (myEngine.getCurrentGame().getBoard().keySet().contains(DomCardName.Trade_Route)
            && theCard.hasCardType(DomCardType.Victory)
                &&!myEngine.getCurrentGame().getBoard().getTradeRouteMat().contains(theCard))
                  theText.append(" <FONT style=\"BACKGROUND-COLOR: #F3F584\">$</font>");
        theText.append(myEngine.getCurrentGame().getActivePlayer().getTokensStringOn(theCard));
        if (!theCard.hasCardType(DomCardType.Event) && !theCard.hasCardType(DomCardType.Landmark))
            theText.append(" (").append(myEngine.getCurrentGame().countInSupply(theCard)).append(")");
        if (myEngine.getCurrentGame().getObeliskChoice()==theCard)
            theText.append(" (Ob.)");
        if (myEngine.getCurrentGame().getTaxOn(theCard)>0)
            theText.append(" "+myEngine.getCurrentGame().getTaxOn(theCard)+"D");
        theText.append("</html>");
        theOriginal.setText(theText.toString());
        return theOriginal;
    }
}
