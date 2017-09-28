package be.aga.dominionSimulator.gui;

import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.gui.util.CardRenderer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OneCardSelector extends JDialog implements ActionListener {
    private final ArrayList<DomCardName> myChooseFrom;
    private final String myQuestion;
    private final String myButtonMessage;
    private DomCardName myChosenCard;
    private JList myChooseFromList;

    public OneCardSelector(Component aComponent, String aTitle, ArrayList<DomCardName> chooseFrom, String aButtonMessage) {
     setModal(true);
     setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
     addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent we) {
            JOptionPane.showMessageDialog(null,"sorry, can't close");
        }
     });
	 myChosenCard = null;
	 myChooseFrom = chooseFrom;
	 myButtonMessage = aButtonMessage;
	 myQuestion = aTitle;
	 buildGUI();
	 setTitle(aTitle);
	 pack();
	 setLocationRelativeTo(aComponent);
	 setVisible(true);
}

private void buildGUI() {
	setLayout( new BorderLayout() );
	add(getChoicePanel(),BorderLayout.CENTER);
	add(getButtonPanel(), BorderLayout.SOUTH);
}

private JPanel getChoicePanel() {
    JPanel thePanel = new JPanel();
    thePanel.setLayout( new GridBagLayout() );
    final GridBagConstraints theCons = DomGui.getGridBagConstraints( 2 );
    theCons.fill=GridBagConstraints.BOTH;
    theCons.anchor=GridBagConstraints.CENTER;
    thePanel.add(getChooseFromPanel(),theCons);
    return thePanel;
}

private JPanel getButtonPanel() {
    final JPanel thePanel = new JPanel();
    thePanel.setLayout( new GridBagLayout() );
    final GridBagConstraints theCons = DomGui.getGridBagConstraints( 2 );
    theCons.fill=GridBagConstraints.NONE;
    theCons.anchor=GridBagConstraints.CENTER;
    //Clear button
    JButton theBTN = new JButton(myButtonMessage);
    theBTN.addActionListener(this);
    thePanel.add(theBTN,theCons);
	return thePanel;
}

private JPanel getChooseFromPanel() {
    final JPanel thePanel = new JPanel();
    thePanel.setLayout( new GridBagLayout() );
    final GridBagConstraints theCons = DomGui.getGridBagConstraints( 2 );
    JScrollPane theChooseFromScroller = new JScrollPane(getChooseFromList(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    theChooseFromScroller.setBorder(new TitledBorder( "Choose a card" ));
    theChooseFromScroller.setPreferredSize(new Dimension(150,300));
    thePanel.add(theChooseFromScroller, theCons);
    return thePanel;
}

private JList getChooseFromList() {
    myChooseFromList = new JList();
//    myChooseFromList.setPreferredSize(new Dimension(100,200));
    myChooseFromList.setCellRenderer(new CardRenderer());
    myChooseFromList.setModel(new DefaultListModel());
    for (DomCardName theCard:myChooseFrom)
      ((DefaultListModel)myChooseFromList.getModel()).addElement(theCard);
    myChooseFromList.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton()==MouseEvent.BUTTON1) {
                int index = myChooseFromList.locationToIndex(e.getPoint());
                if (index >= 0) {
                    DomCardName theChosenCard = (DomCardName) myChooseFromList.getModel().getElementAt(index);
                    for (DomCardName theCard:myChooseFrom) {
                        if (theCard==theChosenCard) {
                            myChosenCard = theCard;
                            dispose();
                            break;
                        }
                    }
                }
            }
        }
    });
    return myChooseFromList;
}

@Override
public void actionPerformed(ActionEvent e) {
        if (myButtonMessage.equals("Mandatory!"))
            return;
        dispose();
}

    public DomCardName getChosenCard() {
        return myChosenCard;
    }
}