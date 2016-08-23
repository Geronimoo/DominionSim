package be.aga.dominionSimulator.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jfree.ui.RefineryUtilities;

import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;

public class DomGameSetup extends EscapeDialog implements ListSelectionListener, ActionListener {
   private DomEngine myEngine;
   
public DomGameSetup(DomEngine anEngine, final DomPlayer aSelectedBot) {
	 myEngine=anEngine;
	 buildGUI();
	 setTitle("Game Setup");
	 pack();
	 RefineryUtilities.centerFrameOnScreen(this);
	 setVisible(true);
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
    theBTN = new JButton("Start Game");
    theBTN.addActionListener(this);
    theBTN.setMnemonic('O');
    theBTN.setActionCommand("Start");
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

    return thePanel;
	
}

private JPanel getSelectionPanel() {
    final JPanel thePanel = new JPanel();
    thePanel.setLayout( new GridBagLayout() );
    final GridBagConstraints theCons = DomGui.getGridBagConstraints( 2 );
//    JScrollPane theTypePane = new JScrollPane(getBotTypeList(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//    theTypePane.setBorder(new TitledBorder( "Type" ));
//    theTypePane.setPreferredSize(new Dimension(150,400));
//    thePanel.add(theTypePane,theCons);
//    theCons.gridx++;
//    JScrollPane theBotPane = new JScrollPane(getBotList(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//    theBotPane.setBorder(new TitledBorder( "Select a strategy" ));
//    theBotPane.setPreferredSize(new Dimension(350,400));
//    thePanel.add(theBotPane,theCons);
    return thePanel;
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

@Override
public void valueChanged(ListSelectionEvent e) {
	// TODO Auto-generated method stub
	
}
}