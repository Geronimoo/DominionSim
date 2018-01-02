package be.aga.dominionSimulator.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

public class EscapeDialog extends JDialog {
  /**
	 * 
	 */
	private static final long serialVersionUID = 8797615002822678508L;

  protected JRootPane createRootPane() {
    ActionListener actionListener = new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        dispose();
      }
    };
    JRootPane rootPane = new JRootPane();
    KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
    rootPane.registerKeyboardAction(actionListener, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
    return rootPane;
  }
}