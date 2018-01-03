package be.aga.dominionSimulator.gui;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.gui.util.CardRenderer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

public class CardSelector extends JDialog implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1537143106231692584L;
    private final ArrayList<DomCard> myChooseFrom;
    private final int myNumber;
    private final String myQuestion;
    private ArrayList<DomCard> myChosenCards;
    private JList<DomCard> myChooseFromList;
    private JList<DomCard> myChosenList;

    public CardSelector(Component aComponent, String aQuestion, ArrayList<DomCard> aChosenCards, ArrayList<DomCard> chooseFrom, int aNumber) {
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
    theChooseFromScroller.setPreferredSize(new Dimension(150,200));
    thePanel.add(theChooseFromScroller, theCons);
    return thePanel;
}

private JPanel getChosenPanel() {
    final JPanel thePanel = new JPanel();
    thePanel.setLayout( new GridBagLayout() );
    final GridBagConstraints theCons = DomGui.getGridBagConstraints( 2 );
    JScrollPane theChosenScroller = new JScrollPane(getChosenList(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    theChosenScroller.setBorder(new TitledBorder( "Chosen cards" ));
    theChosenScroller.setPreferredSize(new Dimension(150,200));
    thePanel.add(theChosenScroller, theCons);
    return thePanel;
}

private JList<DomCard> getChooseFromList() {
    Collections.sort(myChooseFrom,DomCard.SORT_BY_NAME);
    myChooseFromList = new JList<DomCard>();
//    myChooseFromList.setPreferredSize(new Dimension(100,200));
    myChooseFromList.setCellRenderer(new CardRenderer<DomCard>());
    final DefaultListModel<DomCard> myChooseFromListModel = new DefaultListModel<DomCard>();
	myChooseFromList.setModel(myChooseFromListModel);
    for (DomCard theCard:myChooseFrom)
    	myChooseFromListModel.addElement(theCard);
    myChooseFromList.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton()==MouseEvent.BUTTON1) {
                if (myNumber!=0 && myChosenList.getModel().getSize()==myNumber)
                    return;
                int index = myChooseFromList.locationToIndex(e.getPoint());
                if (index >= 0) {
                	DomCard o = myChooseFromList.getModel().getElementAt(index);
                	myChooseFromListModel.removeElement(o);
                    ((DefaultListModel<DomCard>)myChosenList.getModel()).addElement(o);
                }
            }
            if (e.getButton()==MouseEvent.BUTTON3) {
                int index = myChooseFromList.locationToIndex(e.getPoint());
                if (index >= 0)
                    DomGameFrame.showWiki(myChooseFromListModel.getElementAt(index));
            }
        }
    });
    return myChooseFromList;
}

private JList<DomCard> getChosenList() {
    myChosenList = new JList<DomCard>();
//    myChosenList.setPreferredSize(new Dimension(100,200));
    myChosenList.setCellRenderer(new CardRenderer<DomCard>());
    final DefaultListModel<DomCard> myChosenListModel = new DefaultListModel<DomCard>();
	myChosenList.setModel(myChosenListModel);
    for (DomCard theCard:myChosenCards)
    	myChosenListModel.addElement(theCard);
    myChosenList.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton()==MouseEvent.BUTTON1) {
                int index = myChosenList.locationToIndex(e.getPoint());
                if (index >= 0) {
                    DomCard o = myChosenList.getModel().getElementAt(index);
                    myChosenListModel.removeElement(o);
                    ((DefaultListModel<DomCard>)myChooseFromList.getModel()).addElement(o);
                }
            }
            if (e.getButton()==MouseEvent.BUTTON3) {
                int index = myChosenList.locationToIndex(e.getPoint());
                if (index >= 0)
                    DomGameFrame.showWiki(myChosenListModel.getElementAt(index));
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
	        myChosenCards.add((DomCard) myChosenList.getModel().getElementAt(i));
        }
		dispose();
	}
}
}