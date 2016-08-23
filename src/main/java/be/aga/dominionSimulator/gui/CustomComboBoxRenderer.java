package be.aga.dominionSimulator.gui;

import java.awt.Component;
import java.net.URL;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;

import be.aga.dominionSimulator.enums.DomCardName;

  
  public class CustomComboBoxRenderer extends DefaultListCellRenderer {
    public Component getListCellRendererComponent(
                                       JList list,
                                       Object value,
                                       int index,
                                       boolean isSelected,
                                       boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (isSelected) {
            URL theImageLocation = getClass().getResource(((DomCardName)value).getImageLocation());
            if (DomBotEditor.cardImageLabel!=null && theImageLocation!=null) {
              ImageIcon theCardPicture = new ImageIcon(theImageLocation) ;
              DomBotEditor.cardImageLabel.setIcon(theCardPicture);
            }
        } 
        return c;
    }
}
