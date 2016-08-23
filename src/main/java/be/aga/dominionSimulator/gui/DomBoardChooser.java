package be.aga.dominionSimulator.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jfree.ui.RefineryUtilities;

import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomBotType;
import be.aga.dominionSimulator.enums.DomCardName;

public class DomBoardChooser extends EscapeDialog implements ActionListener {
   private DomEngine myEngine;
   private JList myCardNameList;
   private JList myBoardList;

   Action chooseAction = new AbstractAction() {
       public void actionPerformed(ActionEvent e) {
//   		 myEngine.setSelectedBoard(myBoardList.getSelectedValues());
//   		 dispose();
       }
   };
   
public DomBoardChooser(DomEngine anEngine, final DomPlayer aSelectedBot) {
	 myEngine=anEngine;
	 buildGUI();
	 setTitle("Select a board");
	 pack();
	 RefineryUtilities.centerFrameOnScreen(this);
	 setVisible(true);
//	 myBoardList.requestFocus();
}

private void buildGUI() {
	setLayout( new GridBagLayout() );
    final GridBagConstraints theCons = DomGui.getGridBagConstraints( 2 );
	add(getSelectionPanel(), theCons);
	theCons.gridy++;
	add(getButtonPanel(), theCons);
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
    JScrollPane theCardNamePane = new JScrollPane(getCardNameList(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    theCardNamePane.setBorder(new TitledBorder( "Type" ));
    theCardNamePane.setPreferredSize(new Dimension(150,400));
    thePanel.add(theCardNamePane,theCons);
    theCons.gridx++;
    JScrollPane theBotPane = new JScrollPane(getBoardList(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    theBotPane.setBorder(new TitledBorder( "Select a strategy" ));
    theBotPane.setPreferredSize(new Dimension(350,400));
    thePanel.add(theBotPane,theCons);
    return thePanel;
}

private JList getCardNameList() {
	myCardNameList= new JList(DomCardName.getKingdomCards());
	return myCardNameList;
}

@SuppressWarnings("serial")
private JList getBoardList() {
	myBoardList = new JList();
	new ListAction(myBoardList, chooseAction);
	return myBoardList;
}

@Override
public void actionPerformed(ActionEvent e) {
	if (e.getActionCommand().equals("Cancel")){
		dispose();
	}
	if (e.getActionCommand().equals("OK")){
		dispose();
	}
	if (e.getActionCommand().equals("Clear")){
		myEngine.setSelectedBot(null);
		dispose();
	}
}
}