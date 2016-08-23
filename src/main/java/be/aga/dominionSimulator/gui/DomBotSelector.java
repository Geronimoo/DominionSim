package be.aga.dominionSimulator.gui;

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

public class DomBotSelector extends EscapeDialog implements ListSelectionListener, ActionListener {
   private DomEngine myEngine;
   private JList myBotTypeList;
   private JList myBotList;

   Action chooseAction = new AbstractAction() {
       public void actionPerformed(ActionEvent e) {
   		 myEngine.setSelectedBot(myBotList.getSelectedValue());
   		 dispose();
       }
   };
   
public DomBotSelector(DomEngine anEngine, final DomPlayer aSelectedBot) {
	 myEngine=anEngine;
	 buildGUI();
	 setTitle("Select a strategy (use CTRL-button to select multiple types)");
	 pack();
	 RefineryUtilities.centerFrameOnScreen(this);
	 setVisible(true);
	 myBotList.requestFocus();
     Runnable doScroll = new Runnable() {
       public void run() {
      	 myBotList.setSelectedValue(aSelectedBot, true);
       }
     };
     SwingUtilities.invokeLater( doScroll );
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
    JScrollPane theTypePane = new JScrollPane(getBotTypeList(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    theTypePane.setBorder(new TitledBorder( "Type" ));
    theTypePane.setPreferredSize(new Dimension(150,400));
    thePanel.add(theTypePane,theCons);
    theCons.gridx++;
    JScrollPane theBotPane = new JScrollPane(getBotList(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    theBotPane.setBorder(new TitledBorder( "Select a strategy" ));
    theBotPane.setPreferredSize(new Dimension(350,400));
    thePanel.add(theBotPane,theCons);
    return thePanel;
}

private JList getBotTypeList() {
	myBotTypeList = new JList(DomBotType.values());
	myBotTypeList.setSelectedIndices(new int[]{8,10});
	myBotTypeList.addListSelectionListener(this);
	return myBotTypeList;
}

@SuppressWarnings("serial")
private JList getBotList() {
	myBotList = new JList(myEngine.getBots(new DomBotType[]{DomBotType.Province, DomBotType.TwoPlayer})) {
    // This method is called as the cursor moves within the list.
	    public String getToolTipText(MouseEvent evt) {
	        int index = locationToIndex(evt.getPoint());
	        Object item = getModel().getElementAt(index);
	        String theDescription = ((DomPlayer)item).getDescription().replaceAll("XXXX", "<br>");
	        return "<html>"+theDescription+"</html>";
	    }
	};
	new ListAction(myBotList, chooseAction);
	return myBotList;
}

@Override
public void valueChanged(ListSelectionEvent e) {
	if ( e.getSource().equals(myBotTypeList)){
	  myBotList.setListData(myEngine.getBots(myBotTypeList.getSelectedValues()));
	  myBotList.requestFocus();
	}
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
		myBotList.setListData(myEngine.getBots(myBotTypeList.getSelectedValues()));
		myBotList.requestFocus();
	}
}
}