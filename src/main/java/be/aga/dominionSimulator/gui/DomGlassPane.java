package be.aga.dominionSimulator.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DomGlassPane extends JPanel {

	public DomGlassPane() {
	  addMouseListener( new MouseAdapter() {
	    public void mouseClicked( MouseEvent anE ) {
	      JOptionPane.showMessageDialog(null, "Close the editor first!", "", JOptionPane.INFORMATION_MESSAGE);
	    }
	   });
	  setVisible(false);
	  setOpaque(false);
	}
}
