package be.aga.dominionSimulator.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomSet;
import org.jfree.ui.RefineryUtilities;

import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomBotType;

public class DomBotSelector extends EscapeDialog
                            implements ListSelectionListener, ActionListener, WindowListener {
   private DomEngine myEngine;
   private JList myBotTypeList;
   private JList myBotList;
   private HintTextField mSearchField;

   private String[] mSearchTerms = null;

   private static int[] sSelectedIndices = new int[]{8,10};

   Action chooseAction = new AbstractAction() {
       public void actionPerformed(ActionEvent e) {
   		 myEngine.setSelectedBot(myBotList.getSelectedValue());
   		 dispose();
       }
   };

public DomBotSelector(DomEngine anEngine, final DomPlayer aSelectedBot) {
	 myEngine=anEngine;
	 buildGUI();
   addWindowListener(this);
	 setTitle("Select a strategy (click to select multiple types)");
	 pack();
	 RefineryUtilities.centerFrameOnScreen(this);
	 setVisible(true);
	 mSearchField.requestFocus();
     Runnable doScroll = new Runnable() {
       public void run() {
      	 myBotList.setSelectedValue(aSelectedBot, true);
       }
     };
     SwingUtilities.invokeLater( doScroll );
}

private void buildGUI() {
	setLayout( new BorderLayout() );
	add(getSelectionPanel(), BorderLayout.CENTER);
	add(getButtonPanel(), BorderLayout.PAGE_END);
	setPreferredSize(new Dimension(600, 600));
}

private JPanel getButtonPanel() {
    final JPanel thePanel = new JPanel();
    thePanel.setLayout( new GridBagLayout() );
    final GridBagConstraints theCons = DomGui.getGridBagConstraints( 2 );
    theCons.fill=GridBagConstraints.NONE;
    theCons.anchor=GridBagConstraints.CENTER;
    //Clear button
    JButton theBTN = new JButton("Clear selection");
    theBTN.addActionListener(this);
    theBTN.setMnemonic('l');
    theBTN.setActionCommand("Clear");
    theCons.gridx++;
    thePanel.add(theBTN,theCons);
    theCons.gridx++;
    DomGui.addHeavyLabel(thePanel, theCons);
   //OK button
    theBTN = new JButton("OK");
    theBTN.addActionListener(this);
    theBTN.setMnemonic('O');
    theBTN.setActionCommand("OK");
    theCons.gridx++;
    thePanel.add(theBTN,theCons);
    //Cancel button
    theBTN = new JButton("Cancel");
    theBTN.addActionListener(this);
    theBTN.setMnemonic('C');
    theBTN.setActionCommand("Cancel");
    theCons.gridx++;
    thePanel.add(theBTN,theCons);
    theCons.gridx++;
    DomGui.addHeavyLabel(thePanel, theCons);
    //Delete button
    theBTN = new JButton("Delete selected");
    theBTN.addActionListener(this);
    theBTN.setMnemonic('D');
    theBTN.setActionCommand("Delete");
    theCons.gridx++;
    thePanel.add(theBTN,theCons);
	return thePanel;

}

private JPanel getSelectionPanel() {
    final JPanel thePanel = new JPanel();
    thePanel.setLayout( new GridBagLayout() );
    final GridBagConstraints theCons = DomGui.getGridBagConstraints( 2 );
    JScrollPane theTypePane = new JScrollPane(getBotTypeList(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    theTypePane.setBorder(new TitledBorder( "Type" ));
    theCons.weightx = 0.0;
    theCons.weighty = 1.0;
    thePanel.add(theTypePane, theCons);

    theCons.weightx = 1.0;
    theCons.gridx++;
    thePanel.add(getBotPanel(), theCons);

    return thePanel;
}

private JPanel getBotPanel() {
    JPanel thePanel = new JPanel();
    thePanel.setLayout(new BorderLayout());
    thePanel.setBorder(new TitledBorder("Select a strategy"));

    thePanel.add(getSearchField(), BorderLayout.PAGE_START);

    JScrollPane theBotPane = new JScrollPane(getBotList(),
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    thePanel.add(theBotPane, BorderLayout.CENTER);

    return thePanel;
}

private HintTextField getSearchField() {
    mSearchField = new HintTextField("Find by name or cards used");
    mSearchField.addKeyListener(new KeyListener() {
        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
        }

        public void keyReleased(KeyEvent e) {
            mSearchTerms = mSearchField.getText().split("[\\W/]+");
            for (int i=mSearchTerms.length-1; i >= 0; i--) {
                mSearchTerms[i] = mSearchTerms[i].toLowerCase();
            }
            refreshBotList();
        }
    });
    return mSearchField;
}

private JList getBotTypeList() {
	myBotTypeList = new JList(DomBotType.values());
  myBotTypeList.setSelectionModel(new ToggleListSelectionModel());
	myBotTypeList.setSelectedIndices(sSelectedIndices);
	myBotTypeList.addListSelectionListener(this);
	return myBotTypeList;
}

@SuppressWarnings("serial")
private JList getBotList() {
	myBotList = new JList(myEngine.getBots(myBotTypeList.getSelectedValues(), mSearchTerms)) {
        // This method is called as the cursor moves within the list.
        public String getToolTipText(MouseEvent evt) {
            int index = locationToIndex(evt.getPoint());
            Object item = getModel().getElementAt(index);
            DomPlayer bot = (DomPlayer) item;

            StringBuilder tip = new StringBuilder();
            tip.append("<html>");
            tip.append(bot.getDescription().replaceAll("XXXX", "<br>"));

            // Show the kingdom cards used by this bot.
            tip.append("<p>Cards used:<ul>");
            Set<DomCardName> cardSet = new HashSet<DomCardName>();
            for (DomBuyRule rule : bot.getBuyRules()) {
                DomCardName card = rule.getCardToBuy();
                if (!cardSet.contains(card) && !DomSet.Common.contains(card)) {
                    cardSet.add(card);
                    tip.append("<li>");
                    tip.append(rule.getCardToBuy().toHTML());
                    tip.append("</li>\n");
                }
            }
            tip.append("</p></ul>");
            tip.append("</html>");

            return tip.toString();
        }
	};
	new ListAction(myBotList, chooseAction);
	return myBotList;
}

@Override
public void valueChanged(ListSelectionEvent e) {
	if ( e.getSource().equals(myBotTypeList)){
        refreshBotList();
	}
}

private void refreshBotList() {
    myBotList.setListData(myEngine.getBots(myBotTypeList.getSelectedValues(), mSearchTerms));
}

@Override
public void actionPerformed(ActionEvent e) {
	if (e.getActionCommand().equals("Cancel")){
		dispose();
	}
	if (e.getActionCommand().equals("OK")){
		myEngine.setSelectedBot(myBotList.getSelectedValue());
		dispose();
	}
	if (e.getActionCommand().equals("Clear")){
		myEngine.setSelectedBot(null);
		dispose();
	}
	if (e.getActionCommand().equals("Delete")){
		myEngine.deleteBot((DomPlayer) myBotList.getSelectedValue());
		refreshBotList();
		myBotList.requestFocus();
	}
}

  public void windowClosed(WindowEvent arg0) {
    // Store the current type filter for the next time.
    sSelectedIndices = myBotTypeList.getSelectedIndices();
  }

  public void windowActivated(WindowEvent arg0) {
  }

  public void windowClosing(WindowEvent arg0) {
  }

  public void windowDeactivated(WindowEvent arg0) {
  }

  public void windowDeiconified(WindowEvent arg0) {
  }

  public void windowIconified(WindowEvent arg0) {
  }

  public void windowOpened(WindowEvent arg0) {
  }

}

class HintTextField extends JTextField {
    public HintTextField(String hint) {
        _hint = hint;
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (getText().length() == 0) {
            int h = getHeight();
            ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Insets ins = getInsets();
            FontMetrics fm = g.getFontMetrics();
            int c0 = getBackground().getRGB();
            int c1 = getForeground().getRGB();
            int m = 0xfefefefe;
            int c2 = ((c0 & m) >>> 1) + ((c1 & m) >>> 1);
            g.setColor(new Color(c2, true));
            g.drawString(_hint, ins.left, h / 2 + fm.getAscent() / 2 - 2);
        }
    }
    private final String _hint;
}