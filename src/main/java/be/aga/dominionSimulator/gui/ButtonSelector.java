package be.aga.dominionSimulator.gui;

import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.gui.util.CardRenderer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ButtonSelector extends JDialog implements ActionListener {
    private final ArrayList<String> myChooseFrom;
    private final String myQuestion;
    private final String myButtonMessage;
    private int myChosenButton;

    public ButtonSelector(Component aComponent, String aTitle, ArrayList<String> chooseFrom, String aButtonMessage) {
     setModal(true);
     setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
     addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent we) {
            JOptionPane.showMessageDialog(null,"sorry, can't close");
        }
     });
	 myChosenButton = 0;
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
	add(new JScrollPane(getChoicePanel()),BorderLayout.CENTER);
	add(getButtonPanel(), BorderLayout.SOUTH);
}

private JPanel getChoicePanel() {
    JPanel thePanel = new JPanel();
    thePanel.setLayout( new GridBagLayout() );
    final GridBagConstraints theCons = DomGui.getGridBagConstraints( 2 );
    theCons.fill=GridBagConstraints.BOTH;
    theCons.anchor=GridBagConstraints.CENTER;
    for (String theOption : myChooseFrom) {
        JButton theButton = new JButton(theOption);
        theButton.addActionListener(this);
        theButton.setActionCommand(theOption);
        thePanel.add(theButton,theCons);
        theCons.gridy++;
    }
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

@Override
public void actionPerformed(ActionEvent e) {
        for (String theOption:myChooseFrom) {
            if (e.getActionCommand().equals(theOption)) {
                myChosenButton = myChooseFrom.indexOf(theOption);
                dispose();
                return;
            }
        }
        if (myButtonMessage.equals("Mandatory!"))
            return;
        myChosenButton=-1;
        dispose();
}

    public int getChosenOption() {
        return myChosenButton;
    }
}