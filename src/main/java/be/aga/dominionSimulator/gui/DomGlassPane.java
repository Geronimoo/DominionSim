package be.aga.dominionSimulator.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DomGlassPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6027091777531751872L;

	public DomGlassPane() {
	  addMouseListener( new MouseAdapter() {
	    public void mouseClicked( MouseEvent anE ) {
	      JOptionPane.showMessageDialog(null, "Close the other window first!", "", JOptionPane.INFORMATION_MESSAGE);
	    }
	   });
	  setVisible(false);
	  setOpaque(false);
	}
}
