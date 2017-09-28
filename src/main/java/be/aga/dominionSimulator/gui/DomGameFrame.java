package be.aga.dominionSimulator.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPhase;
import be.aga.dominionSimulator.gui.util.CardRenderer;
import be.aga.dominionSimulator.gui.util.HandCardRenderer;
import be.aga.dominionSimulator.gui.util.TableCardRenderer;

public class DomGameFrame extends JFrame implements ActionListener, ListSelectionListener, Observer {
	private DomEngine myEngine;
	private JLabel myActionsValue;
	private JLabel myBuysValue;
	private HashMap<JLabel, DomCardName> myBoardCards = new HashMap<JLabel, DomCardName>();
	private JTextPane myLogPane;
	private JList myInPlayList;
	private JButton myEndTurnBTN;
	private JList myHandList;
	private JTable myBoardTable;
	private HTMLEditorKit editorKit;
	private HTMLDocument gameLog;
	private JButton myEndActions;
    private StatusBar myStatusBar;
    private JScrollPane myLogScroll;
    private JButton myPlayAllTreasurersBTN;
    private JLabel myVPLabel;
    private JLabel myOppsVPLabel;
    private JLabel myDrawDeckLabel;
    private JLabel myDiscardLabel;

    public DomGameFrame(DomEngine anEngine) {
	 myEngine=anEngine;
	 buildGUI();
	 setTitle("Play Dominion");
//     setPreferredSize(RefineryUtilities.getMaximumWindowBounds().getSize());
     setPreferredSize(new Dimension(800,950));
	 pack();
	 setVisible(true);
     addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            if (myEngine.getCurrentGame()!=null)
                myEngine.doEndOfHumanGameStuff();
        }
     });
    }

private void buildGUI() {
	setLayout(new BorderLayout());
    JSplitPane theSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, getTopSplit(), getBottomPanel());
    theSplit.setResizeWeight(0.5);
    theSplit.setDividerLocation(450);
    theSplit.setDividerSize(1);
//    theSplit.resetToPreferredSizes();
	getContentPane().add(theSplit, BorderLayout.CENTER);
	getContentPane().add(getStatusBar(), BorderLayout.SOUTH);
}

    public Component getStatusBar() {
        myStatusBar = new StatusBar();
        myStatusBar.setText("Alles goed");
        return myStatusBar;
    }

    private JSplitPane getTopSplit() {
	JSplitPane theSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, getLogPanel(), getInfoPanel());
	theSplit.setResizeWeight(1);
	theSplit.setDividerSize(1);
//	theSplit.resetToPreferredSizes();
	return theSplit;
}

private JPanel getLogPanel() {
	JPanel theLogPanel = new JPanel();
	theLogPanel.setLayout(new BorderLayout());
	myLogPane = new JTextPane();
	myLogPane.setPreferredSize(new Dimension(400,300));
	editorKit = new HTMLEditorKit();
	gameLog = (HTMLDocument) editorKit.createDefaultDocument();;
	myLogPane.setEditorKit(editorKit);
	myLogPane.setDocument(gameLog);
	myLogScroll = new JScrollPane(myLogPane);
    myLogScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//	theScrollPane.setPreferredSize(new Dimension(400,400));
	theLogPanel.add(myLogScroll,BorderLayout.CENTER);
    Font font = new Font("Times New Roman", Font.PLAIN, 14);
    String bodyRule = "body { font-family: " + font.getFamily() + "; " +
            "font-size: " + font.getSize() + "pt; }";
    ((HTMLDocument)myLogPane.getDocument()).getStyleSheet().addRule(bodyRule);//	myLogPane.revalidate();
	return theLogPanel;
}

private JPanel getBottomPanel() {
	JPanel thePanel = new JPanel();
	thePanel.setLayout(new GridBagLayout());
	GridBagConstraints theCons = DomGui.getGridBagConstraints(0);
	theCons.fill=GridBagConstraints.BOTH;

	//hand list
	myHandList = new JList();
	myHandList.setLayoutOrientation(JList.VERTICAL);
	myHandList.setPreferredSize(new Dimension(150,350));
	myHandList.setVisibleRowCount(25);
	myHandList.setModel(new DefaultListModel());
//	myHandList.setMinimumSize(new Dimension(60,400));
	myHandList.setCellRenderer(new HandCardRenderer());
    myHandList.setFont(new Font("Arial",Font.PLAIN,12));
	myHandList.addListSelectionListener(this);
    JScrollPane theScrollPane = new JScrollPane(myHandList);
    theScrollPane.setBorder(new TitledBorder("Hand"));
    theScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	thePanel.add(theScrollPane,theCons);
	//in-play list
	myInPlayList = new JList();
	myInPlayList.setModel(new DefaultListModel());
//	myInPlayList.setBorder(new TitledBorder("In play"));
//	myInPlayList.setMinimumSize(new Dimension(60,400));
	myInPlayList.setPreferredSize(new Dimension(150,350));
	myInPlayList.setVisibleRowCount(25);
	myInPlayList.setCellRenderer(new CardRenderer());
    ((DefaultListModel)myInPlayList.getModel()).addElement("");
    theScrollPane = new JScrollPane(myInPlayList);
    theScrollPane.setBorder(new TitledBorder("In play"));
    theScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    theScrollPane.setAutoscrolls(true);
	theCons.gridx++;
	thePanel.add(theScrollPane,theCons);
	//the Board
	myBoardTable=new JTable(new KingdomTableModel(myEngine));
	myBoardTable.setDefaultRenderer(DomCardName.class, new TableCardRenderer(myEngine));
	myBoardTable.setTableHeader(null);
    myBoardTable.setShowHorizontalLines(false);
    myBoardTable.setShowVerticalLines(false);
	myBoardTable.setColumnSelectionAllowed(false);
	myBoardTable.setRowSelectionAllowed(false);
	myBoardTable.setRowHeight(20);
	myBoardTable.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton()==MouseEvent.BUTTON1) {
                int row = myBoardTable.rowAtPoint(e.getPoint());
                int col = myBoardTable.columnAtPoint(e.getPoint());
                tryToBuyOrGainFromSupply(myBoardTable.getModel().getValueAt(row,col));
            }
            super.mouseClicked(e);
        }
    });
    JScrollPane theBoardPanel = new JScrollPane(myBoardTable);
    theBoardPanel.setPreferredSize(new Dimension(250,350));
    theBoardPanel.setBorder(new TitledBorder("Kingdom"));
	theCons.gridx++;
	thePanel.add(theBoardPanel,theCons);
	return thePanel;
}

    private void tryToBuyOrGainFromSupply(Object aCardName) {
        DomCardName theCard = (DomCardName) aCardName;
        DomPlayer activePlayer = myEngine.getCurrentGame().getActivePlayer();
        activePlayer.tryToBuyFromSupplyAsHuman(theCard);
    }

    private JPanel getInfoPanel() {
	JPanel thePanel = new JPanel();
	thePanel.setLayout(new GridBagLayout());
	GridBagConstraints theCons = DomGui.getGridBagConstraints(2);
	theCons.fill=GridBagConstraints.NONE;
	//turn indicator
//	myTurnLabel = new JLabel("Your turn");
//	thePanel.add(myTurnLabel, theCons);
	//$ indicator
//	myDollarLabel = new JLabel("Money: $5 + P");
////	theCons.gridy++;
//	thePanel.add(myDollarLabel, theCons);
	//draw and discardpile
    myDrawDeckLabel = new JLabel();
    theCons.gridx++;
    thePanel.add(myDrawDeckLabel, theCons);
    myDiscardLabel = new JLabel();
    theCons.gridx++;
    thePanel.add(myDiscardLabel, theCons);
    //Actions indicator
    JLabel theActionsLabel = new JLabel("Actions:");
    theCons.gridx++;
    thePanel.add(theActionsLabel, theCons);
    myActionsValue=new JLabel();
    theCons.gridx++;
    thePanel.add(myActionsValue, theCons);
    //Buys indicator
	JLabel theBuysLabel = new JLabel("Buys:");
	theCons.gridx++;
	thePanel.add(theBuysLabel, theCons);
	myBuysValue = new JLabel();
	theCons.gridx++;
	thePanel.add(myBuysValue, theCons);
    myVPLabel = new JLabel();
    theCons.gridx++;
    thePanel.add(myVPLabel, theCons);
    myOppsVPLabel = new JLabel();
    theCons.gridx++;
    thePanel.add(myOppsVPLabel, theCons);
    theCons.gridx++;
    JButton theShowTrashBTN = new JButton("Show Trash");
    theShowTrashBTN.setActionCommand("Show Trash");
    theShowTrashBTN.addActionListener(this);
    thePanel.add(theShowTrashBTN, theCons);
	theCons.weightx=100;
	theCons.gridx++;
	thePanel.add(new JLabel(), theCons);
	theCons.weightx=1;
    theCons.gridx++;
    myPlayAllTreasurersBTN = new JButton("Play all treasures");
    myPlayAllTreasurersBTN.setActionCommand("Play all treasures");
    myPlayAllTreasurersBTN.addActionListener(this);
    myPlayAllTreasurersBTN.setVisible(false);
    thePanel.add(myPlayAllTreasurersBTN, theCons);
	theCons.gridx++;
	myEndActions = new JButton("End Actions");
	myEndActions.setActionCommand("End Actions");
	myEndActions.addActionListener(this);
	thePanel.add(myEndActions, theCons);
    theCons.gridx++;
    myEndTurnBTN = new JButton("End turn");
    myEndTurnBTN.setActionCommand("End turn");
    myEndTurnBTN.addActionListener(this);
    thePanel.add(myEndTurnBTN, theCons);

	return thePanel;
}

@Override
public void actionPerformed(ActionEvent e) {
	if (e.getActionCommand().equals("Cancel")){
		dispose();
	}
	if (e.getActionCommand().equals("Show Trash")) {
	    StringBuilder theTrash = new StringBuilder();
	    theTrash.append(myEngine.getCurrentGame().getBoard().getTrashedCards());
	    JOptionPane.showMessageDialog(this, theTrash.toString());
    }
    if (e.getActionCommand().equals("Play all treasures")) {
        if (!myEngine.getCurrentGame().isGameFinished())
            myEngine.getCurrentGame().getActivePlayer().attemptToPlayAllTreasures();
    }
    if (e.getActionCommand().equals("End Actions")) {
        myEngine.getCurrentGame().getHumanPlayer().endActions();
    }
    if (e.getActionCommand().equals("End turn")) {
        myEngine.getCurrentGame().getHumanPlayer().endTurn();
    }
}

	@Override
	public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && ((JList) e.getSource()).getSelectedValue()!=null) {
            myEngine.getCurrentGame().getActivePlayer().attemptToPlay((DomCard) ((JList) e.getSource()).getSelectedValue());
        }
	}

	public void addToLog(final String s) {
		try {
		    if (!s.contains("cards in Hand:")) {
                editorKit.insertHTML(gameLog, gameLog.getLength(), s, 0, 0, null);
            }
		} catch (BadLocationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        updateHandList();
        updateInPlayList();
        myBoardTable.setModel(new KingdomTableModel(myEngine));
        myBoardTable.setModel(new KingdomTableModel(myEngine));
        myStatusBar.setText(myEngine.getStatus());
        DomPlayer theHuman = myEngine.getCurrentGame().getHumanPlayer();
        myActionsValue.setText("<html><FONT style=\"BACKGROUND-COLOR: #D9D9D9\">"+theHuman.actionsLeft+"</font></html>");
        myActionsValue.setToolTipText(theHuman.actionsLeft + " actions left");
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("images/Randomizer.jpg"));
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(12, 18,  Image.SCALE_DEFAULT); // scale it the smooth way
        myDrawDeckLabel.setIcon(new ImageIcon(newimg));  // transform it back
        myDrawDeckLabel.setText(""+myEngine.getCurrentGame().getHumanPlayer().getDrawDeckSize());
        imageIcon = new ImageIcon(getClass().getResource("images/openDominion.jpg"));
        image = imageIcon.getImage(); // transform it
        newimg = image.getScaledInstance(12, 18,  Image.SCALE_DEFAULT); // scale it the smooth way
        myDiscardLabel.setIcon(new ImageIcon(newimg));  // transform it back
        myDiscardLabel.setText(""+myEngine.getCurrentGame().getHumanPlayer().getCardsFromDiscard().size());
        myBuysValue.setText("<html>"+theHuman.getBuysLeft() + " $" + "<FONT style=\"BACKGROUND-COLOR: #F3F584\">"+ theHuman.getAvailableCoins() +"</font> ($<FONT style=\"BACKGROUND-COLOR: #F3F584\">"+ theHuman.getTotalPotentialCurrency().getCoins()+"</font>?)");
        myVPLabel.setText("<html>"+myEngine.getCurrentGame().getHumanPlayer().countVictoryPoints()+"&#x25BC;</font></html>");
        String theOppVPString = "(Opp.: ";
        for (DomPlayer thePlayer : myEngine.getPlayers()) {
            if (!thePlayer.isHuman())
                theOppVPString+=thePlayer.countVictoryPoints()+"&#x25BC; ";
        }
        myOppsVPLabel.setText("<html>"+theOppVPString+")</html>");
        myOppsVPLabel.setFont(new Font("", Font.PLAIN, 10));
        if (theHuman.getPhase()==DomPhase.Action)
            myEndActions.setVisible(true);
        else
            myEndActions.setVisible(false);
        if (theHuman.getPhase()==DomPhase.Buy && theHuman.getBoughtCards().isEmpty() && !theHuman.getCardsFromHand(DomCardType.Treasure).isEmpty())
            myPlayAllTreasurersBTN.setVisible(true);
        else
            myPlayAllTreasurersBTN.setVisible(false);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                myLogScroll.getVerticalScrollBar().setValue(myLogScroll.getVerticalScrollBar().getMaximum());
            }
        });
    }

    private void updateInPlayList() {
        ((DefaultListModel)myInPlayList.getModel()).removeAllElements();
        for (DomCard theCard : myEngine.getCurrentGame().getHumanPlayer().getCardsInPlay()) {
            ((DefaultListModel)myInPlayList.getModel()).addElement(theCard.getName());
        }
        if (myEngine.getCurrentGame().getHumanPlayer().getCardsInPlay().isEmpty())
            ((DefaultListModel)myInPlayList.getModel()).addElement("");
        Runnable doRun = new Runnable() {
            @Override
            public void run() {
                myInPlayList.ensureIndexIsVisible(((DefaultListModel)myInPlayList.getModel()).size()-1);
            }
        };
        SwingUtilities.invokeLater(doRun);
}

    private void updateHandList() {
        ((DefaultListModel)myHandList.getModel()).removeAllElements();
        for (DomCardName theCard : myEngine.getCurrentGame().getHumanPlayer().getUniqueCardsInHand()) {
            ((DefaultListModel)myHandList.getModel()).addElement(myEngine.getCurrentGame().getHumanPlayer().getCardsFromHand(theCard).get(0));
        }
        myHandList.setSelectedIndex(-1);
    }

    public boolean askPlayer(String question, String title) {
        return JOptionPane.showConfirmDialog(this, question, title, JOptionPane.YES_NO_OPTION)==0;
    }

    public void askToSelectCards(String s, ArrayList<DomCard> chooseFrom, ArrayList<DomCardName> theChosenCards, int aNumber) {
        new CardSelector(myHandList,s, theChosenCards, chooseFrom, aNumber);
    }

    public DomCardName askToSelectOneCard(String title, ArrayList<DomCardName> cards, String buttonMessage) {
        OneCardSelector theSelector = new OneCardSelector(myHandList, title, cards, buttonMessage);
        return theSelector.getChosenCard();
    }
}