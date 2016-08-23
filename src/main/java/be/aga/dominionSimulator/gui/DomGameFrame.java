package be.aga.dominionSimulator.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomSet;

public class DomGameFrame extends JFrame implements ActionListener {
	private DomEngine myEngine;
	private JLabel myDollarLabel;
	private JLabel myActionsLabel;
	private JLabel myTurnLabel;
	private JLabel myBuysLabel;
	private HashMap<JLabel, DomCardName> myBoardCards = new HashMap<JLabel, DomCardName>();
	private JTextArea myLogArea;
	private JPanel myInPlayPanel;
	private DomCardPanel myHandPanel;
	private JPanel myKingdomPanel;
	private JPanel myCommonPanel;
	private DomCardLabel myBigImage;
	
	private final int SPLIT_DIVIDER_SIZE = 5;
	private JButton myAddTreasureBTN;
	private JButton myBuyPhaseBTN;
	private JButton myResignBTN;
	private JButton myLogBTN;
	private JButton myEndTurnBTN;

public DomGameFrame(DomEngine anEngine) {
	 myEngine=anEngine;
	 myEngine.setGameFrame(this);
	 buildGUI();
	 setTitle("Play Dominion");
//     setPreferredSize(RefineryUtilities.getMaximumWindowBounds().getSize());
     setPreferredSize(new Dimension(800,600));
	 pack();
	 setVisible(true);
}

private void buildGUI() {
	setLayout(new BorderLayout());
    JSplitPane theSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, getBoardSplit(), getBottomSplit());
    theSplit.setResizeWeight(0.3);
//    theSplit.setDividerLocation(300);
    theSplit.setDividerSize(SPLIT_DIVIDER_SIZE*2);
    theSplit.resetToPreferredSizes();
	getContentPane().add(theSplit, BorderLayout.CENTER);
	getContentPane().add(myEngine.getStatusBar(), BorderLayout.SOUTH);
}

private JSplitPane getBottomSplit() {
    JSplitPane theSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, getInfoSplit(), getInPlayAndHandSplit());
    theSplit.setResizeWeight(0.1);
    theSplit.setDividerSize(SPLIT_DIVIDER_SIZE*2);
    theSplit.resetToPreferredSizes();
	return theSplit;
}

private JSplitPane getInfoSplit() {
    JSplitPane theSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, getInfoPanel(), getBigCardImagePanel());
    theSplit.setResizeWeight(0);
    theSplit.setDividerSize(SPLIT_DIVIDER_SIZE);
    theSplit.resetToPreferredSizes();
	return theSplit;
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
	//Actions indicator
	myActionsLabel = new JLabel("Actions: 3");
//	theCons.gridx++;
	thePanel.add(myActionsLabel, theCons);
	//Info button
	JButton theBTN = new JButton("More info");
	theBTN.setActionCommand("Info");
	theBTN.addActionListener(this);
	theCons.gridx++;
	thePanel.add(theBTN, theCons);
	//Buys indicator
	myBuysLabel = new JLabel("Buys: 1 ($5 +P)");
	theCons.gridx=0;
	theCons.gridy++;
	thePanel.add(myBuysLabel, theCons);
	
	return thePanel;
}

private JPanel getBigCardImagePanel() {
	JPanel thePanel = new JPanel();
	thePanel.setLayout(new GridBagLayout());
//	thePanel.setPreferredSize(new Dimension(250,250));
	GridBagConstraints theCons = DomGui.getGridBagConstraints(2);
	thePanel.add(getBigImage(), theCons);
	return thePanel;
}

private DomCardLabel getBigImage() {
	myBigImage = new DomCardLabel(); 
	return myBigImage;
}

//private JSplitPane getGameSplit() {
//    JSplitPane theGameSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, getBoardAndGameLogSplit(), getInPlayAndHandSplit());
//    theGameSplit.setResizeWeight(0.5);
//    theGameSplit.setDividerSize(5);
//	return theGameSplit;
//}

//private Component getBoardAndGameLogSplit() {
//    JSplitPane theSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, getBoardSplit(), getLogPane());
//    theSplit.setResizeWeight(0.6);
//    theSplit.setDividerSize(SPLIT_DIVIDER_SIZE);
//	return theSplit;
//}

private JSplitPane getBoardSplit() {
    JSplitPane theSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, getCommonPanel(), getKingdomPanel());
	theSplit.setBorder(new TitledBorder("Board"));
    theSplit.setResizeWeight(0.3);
    theSplit.setDividerSize(SPLIT_DIVIDER_SIZE);
	return theSplit;
}

private JPanel getKingdomPanel() {
	myKingdomPanel = new JPanel();
	myKingdomPanel.setLayout(new GridBagLayout());
	GridBagConstraints theCons = DomGui.getGridBagConstraints(2);
	myKingdomPanel.setMinimumSize(new Dimension(100,100));
	for (DomCardName cardName : myEngine.getBoardCards()) {
		if (cardName.getSet()!=DomSet.Common){
		    myKingdomPanel.add(getCardLabel(cardName), theCons);
			theCons.gridx++;
		}
	}
	return myKingdomPanel;
}

private JPanel getCommonPanel() {
	myCommonPanel = new JPanel();
	myCommonPanel.setLayout(new GridBagLayout());
	GridBagConstraints theCons = DomGui.getGridBagConstraints(2);
	myCommonPanel.setMinimumSize(new Dimension(100,100));
	for (DomCardName cardName : myEngine.getBoardCards()) {
		if (cardName.getSet()==DomSet.Common){
		    myCommonPanel.add(getCardLabel(cardName), theCons);
			theCons.gridx++;
		}
	}
	return myCommonPanel;
}

private JLabel getCardLabel(DomCardName cardName) {
    DomCardLabel theLBL = new DomCardLabel(cardName, this);
    myBoardCards.put(theLBL, cardName);
    return theLBL;
}

//private JTextArea getLogPane() {
//	myLogArea = new JTextArea("sdglksdljsdlhflsdlsdf<br>sdvsdldvlvsd<br>");
////	JScrollPane theScr = new JScrollPane(myLogArea);
////	return theScr;
//	return myLogArea;
//}

private Component getInPlayAndHandSplit() {
    JSplitPane theSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, getInPlayPanel(), getHandAndButtonsSplit());
    theSplit.setResizeWeight(0.3);
    theSplit.setDividerSize(SPLIT_DIVIDER_SIZE);
	return theSplit;
}

private Component getInPlayPanel() {
	myInPlayPanel = new JPanel();
	myInPlayPanel.setMinimumSize(new Dimension(100,100));
	myInPlayPanel.setLayout(new GridBagLayout());
	myInPlayPanel.setBorder(new TitledBorder("In Play"));
	GridBagConstraints theCons = DomGui.getGridBagConstraints(2);
	for (DomCard card : myEngine.getCardsInPlay()) {
	    myInPlayPanel.add(getCardLabel(card.getName()), theCons);
		theCons.gridx++;
	}
	return myInPlayPanel;
}

private Component getHandAndButtonsSplit() {
    JSplitPane theSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, getButtonPanel(), getHandPanel());
    theSplit.setResizeWeight(0);
    theSplit.setDividerSize(SPLIT_DIVIDER_SIZE);
    theSplit.resetToPreferredSizes();
	return theSplit;
}

private JComponent getHandPanel() {
	myHandPanel = new DomCardPanel("In Hand", this);
	myHandPanel.setCards(myEngine.getHumanPlayerHand());
	return myHandPanel;
}

private void addCardsToHand(ArrayList<DomCard> aCards) {
	int theNumberOfSplits = 1;
	//2X^2 + 5X - "number of cards to display" > 0
	while (theNumberOfSplits*theNumberOfSplits*2 + 5*theNumberOfSplits - aCards.size()<0 || theNumberOfSplits>=4)
	  theNumberOfSplits*=2;

    if (theNumberOfSplits==1) {	
		GridBagConstraints theCons = DomGui.getGridBagConstraints(2);
		for (DomCard card : aCards) {
		    myHandPanel.add(getCardLabel(card.getName()), theCons);
			theCons.gridx++;
		}
    }
    if (theNumberOfSplits==2) {
    	
		GridBagConstraints theCons = DomGui.getGridBagConstraints(2);
		for (DomCard card : aCards) {
		    myHandPanel.add(getCardLabel(card.getName()), theCons);
			theCons.gridx++;
		}
    }
    
    
}

private JPanel getButtonPanel() {
    final JPanel thePanel = new JPanel();
    thePanel.setLayout( new GridBagLayout() );
    final GridBagConstraints theCons = DomGui.getGridBagConstraints( 2 );
    theCons.fill=GridBagConstraints.NONE;
    theCons.anchor=GridBagConstraints.NORTHWEST;
    //add treasures button
    myAddTreasureBTN = new JButton();
    myAddTreasureBTN.addActionListener(this);
    myAddTreasureBTN.setActionCommand("AddTreasures");
    myAddTreasureBTN.setText("+$0");
    thePanel.add(myAddTreasureBTN,theCons);
    //buy phase button
    myBuyPhaseBTN = new JButton("Buy Phase");
    myBuyPhaseBTN.addActionListener(this);
    myBuyPhaseBTN.setActionCommand("BuyPhase");
    theCons.gridx++;
    thePanel.add(myBuyPhaseBTN,theCons);
    //buy phase button
    myEndTurnBTN = new JButton("End turn");
    myEndTurnBTN.addActionListener(this);
    myEndTurnBTN.setActionCommand("EndTurn");
    theCons.gridx++;
    thePanel.add(myEndTurnBTN,theCons);
    //log button
    myLogBTN = new JButton("Game Log");
    myLogBTN.addActionListener(this);
    myLogBTN.setActionCommand("Log");
    theCons.gridx++;
    thePanel.add(myLogBTN,theCons);
    //place filler
    theCons.gridx++;
    DomGui.addHeavyLabel(thePanel, theCons);
   //Resign button
    myResignBTN = new JButton("Resign");
    myResignBTN.addActionListener(this);
    myResignBTN.setActionCommand("Resign");
    theCons.gridx++;
    thePanel.add(myResignBTN,theCons);

    return thePanel;
}

@Override
public void actionPerformed(ActionEvent e) {
	if (e.getActionCommand().equals("Cancel")){
		dispose();
	}
	if (e.getActionCommand().equals("OK")){
	}
	if (e.getActionCommand().equals("Clear")){
	}
	if (e.getActionCommand().equals("Delete")){
	}
}

public void setBigImage(DomCardName myCardName) {
   myBigImage.setCardName(myCardName); 	
}
}