package be.aga.dominionSimulator.gui;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomBotType;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomSet;
import be.aga.dominionSimulator.gui.util.CardRenderer;
import be.aga.dominionSimulator.gui.util.HandCardRenderer;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CardSelector extends JDialog implements ActionListener {
    private final ArrayList<DomCard> myChooseFrom;
    private final int myNumber;
    private final String myQuestion;
    private ArrayList<DomCardName> myChosenCards;
    private JList myChooseFromList;
    private JList myChosenList;

    public CardSelector(Component aComponent, String aQuestion, ArrayList<DomCardName> aChosenCards, ArrayList<DomCard> chooseFrom, int aNumber) {
     setModal(true);
     setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
     addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent we) {
            JOptionPane.showMessageDialog(null,"sorry, can't close");
        }
     });
	 myChosenCards = aChosenCards;
	 myChooseFrom = chooseFrom;
	 myNumber = aNumber;
	 myQuestion = aQuestion;
	 buildGUI();
	 setTitle(aQuestion);
	 pack();
     setLocationRelativeTo(aComponent);
	 setVisible(true);
}

private void buildGUI() {
	setLayout( new BorderLayout() );
	add(getChoicePanel(),BorderLayout.CENTER);
	add(getButtonPanel(),BorderLayout.SOUTH);
//	setPreferredSize(new Dimension(600, 600));
}

private JPanel getChoicePanel() {
    JPanel thePanel = new JPanel();
    thePanel.setLayout( new GridBagLayout() );
    final GridBagConstraints theCons = DomGui.getGridBagConstraints( 2 );
    theCons.fill=GridBagConstraints.BOTH;
    theCons.anchor=GridBagConstraints.CENTER;
    JLabel theLabel = new JLabel(myQuestion);
    theCons.gridwidth=2;
    thePanel.add(theLabel, theCons);
    theCons.gridwidth=1;
    theCons.gridx=0;
    theCons.gridy++;
    thePanel.add(getChooseFromPanel(),theCons);
    theCons.gridx++;
    thePanel.add(getChosenPanel(),theCons);
    return thePanel;
}

private JPanel getButtonPanel() {
    final JPanel thePanel = new JPanel();
    thePanel.setLayout( new GridBagLayout() );
    final GridBagConstraints theCons = DomGui.getGridBagConstraints( 2 );
    theCons.fill=GridBagConstraints.NONE;
    theCons.anchor=GridBagConstraints.CENTER;
    //Clear button
    JButton theBTN = new JButton("OK");
    theBTN.addActionListener(this);
    theBTN.setMnemonic('O');
    theBTN.setActionCommand("OK");
    thePanel.add(theBTN,theCons);
	return thePanel;
}

private JPanel getChooseFromPanel() {
    final JPanel thePanel = new JPanel();
    thePanel.setLayout( new GridBagLayout() );
    final GridBagConstraints theCons = DomGui.getGridBagConstraints( 2 );
    JScrollPane theChooseFromScroller = new JScrollPane(getChooseFromList(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    theChooseFromScroller.setBorder(new TitledBorder( "Choose from these" ));
    theChooseFromScroller.setPreferredSize(new Dimension(150,300));
    thePanel.add(theChooseFromScroller, theCons);
    return thePanel;
}

private JPanel getChosenPanel() {
    final JPanel thePanel = new JPanel();
    thePanel.setLayout( new GridBagLayout() );
    final GridBagConstraints theCons = DomGui.getGridBagConstraints( 2 );
    JScrollPane theChosenScroller = new JScrollPane(getChosenList(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    theChosenScroller.setBorder(new TitledBorder( "Chosen cards" ));
    theChosenScroller.setPreferredSize(new Dimension(150,300));
    thePanel.add(theChosenScroller, theCons);
    return thePanel;
}

private JList getChooseFromList() {
    myChooseFromList = new JList();
//    myChooseFromList.setPreferredSize(new Dimension(100,200));
    myChooseFromList.setCellRenderer(new CardRenderer());
    myChooseFromList.setModel(new DefaultListModel());
    for (DomCard theCard:myChooseFrom)
      ((DefaultListModel)myChooseFromList.getModel()).addElement(theCard.getName());
    myChooseFromList.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton()==MouseEvent.BUTTON1) {
                if (myNumber!=0 && myChosenList.getModel().getSize()==myNumber)
                    return;
                int index = myChooseFromList.locationToIndex(e.getPoint());
                if (index >= 0) {
                    Object o = myChooseFromList.getModel().getElementAt(index);
                    ((DefaultListModel)myChooseFromList.getModel()).removeElement(o);
                    ((DefaultListModel)myChosenList.getModel()).addElement(o);
                }
            }
        }
    });
    return myChooseFromList;
}

private JList getChosenList() {
    myChosenList = new JList();
//    myChosenList.setPreferredSize(new Dimension(100,200));
    myChosenList.setCellRenderer(new CardRenderer());
    myChosenList.setModel(new DefaultListModel());
    for (DomCardName theCard:myChosenCards)
        ((DefaultListModel)myChosenList.getModel()).addElement(theCard);
    myChosenList.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton()==MouseEvent.BUTTON1) {
                int index = myChosenList.locationToIndex(e.getPoint());
                if (index >= 0) {
                    Object o = myChosenList.getModel().getElementAt(index);
                    ((DefaultListModel)myChosenList.getModel()).removeElement(o);
                    ((DefaultListModel)myChooseFromList.getModel()).addElement(o);
                }
            }
        }
    });
    return myChosenList;
}

@Override
public void actionPerformed(ActionEvent e) {
	if (e.getActionCommand().equals("OK")){
	    if (myNumber!=0 && myChosenList.getModel().getSize()!=myNumber)
	        return;
	    for (int i=0; i<myChosenList.getModel().getSize(); i++) {
	        myChosenCards.add((DomCardName) myChosenList.getModel().getElementAt(i));
        }
		dispose();
	}
}
}