package be.aga.dominionSimulator.gui;

import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Created by jeroena on 19/02/2016.
 */
public class PlayPreparationDialog extends EscapeDialog implements ActionListener {

    private final DomEngine myEngine;
    private final ArrayList<DomPlayer> myBots;
    private ArrayList<DomCardName> myBoard;
    private JTextField myBoardField;
    private JTextField myBaneField;
    private ButtonGroup myBTNGroup;

    public PlayPreparationDialog(DomEngine anEngine) {
        myEngine=anEngine;
        myEngine.getGui().getGlassPane().setVisible(true);
        myBots = myEngine.getGui().getBots();
        if (myBots.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please select (a) bot(s) to play against!", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            myEngine.getGui().getGlassPane().setVisible(false);
            return;
        }
        setTitle("Prepare to play against " + myBots);
        buildGUI();
        pack();
        setVisible( true );
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    private void buildGUI() {
        setLayout( new GridBagLayout() );
        final GridBagConstraints theCons = DomGui.getGridBagConstraints( 2 );
        add(getBoardPanel(), theCons);
        theCons.gridy++;
        add(getStartStatePanel(), theCons);
        theCons.gridy++;
        add(getButtonPanel(), theCons);
    }

    private JPanel getBoardPanel() {
        final JPanel thePanel = new JPanel();
        thePanel.setLayout( new GridBagLayout() );
        final GridBagConstraints theCons = DomGui.getGridBagConstraints( 2 );
        thePanel.add(new JLabel("Board"),theCons);
        myBoard = myBots.get(0).getSuggestedBoard();
        myBoardField = new JTextField(myBoard.toString().replaceAll("\\[|\\]", "") , 50);
        theCons.gridx++;
        thePanel.add(myBoardField,theCons);
        theCons.gridx=0;
        theCons.gridy++;
        theCons.fill=GridBagConstraints.NONE;
        thePanel.add(new JLabel("Bane Card"),theCons);
        myBaneField = new JTextField(myBots.get(0).getBaneCardAsString(), 15);
        theCons.gridx++;
        thePanel.add(myBaneField,theCons);
        return thePanel;
    }

    private JPanel getStartStatePanel() {
        final JPanel thePanel = new JPanel();
        thePanel.setLayout( new GridBagLayout() );
        final GridBagConstraints theCons = DomGui.getGridBagConstraints( 2 );
        myBTNGroup = getRadioBTNsForStartState();
        for (Enumeration< AbstractButton > theEnum = myBTNGroup.getElements();theEnum.hasMoreElements();) {
            thePanel.add( theEnum.nextElement(), theCons);
            theCons.gridx++;
        }
        return thePanel;
    }

    private ButtonGroup getRadioBTNsForStartState() {
        ButtonGroup theStartStateRBTNGroup = new ButtonGroup();
        JRadioButton theBTN = new JRadioButton("random start");
        theBTN.setSelected( true );
        theStartStateRBTNGroup.add(theBTN);
        theStartStateRBTNGroup.add(new JRadioButton("4/3 start"));
        theStartStateRBTNGroup.add(new JRadioButton("5/2 start"));
        return theStartStateRBTNGroup;
    }

    private JPanel getButtonPanel() {
        final JPanel thePanel = new JPanel();
        thePanel.setLayout( new GridBagLayout() );
        final GridBagConstraints theCons = DomGui.getGridBagConstraints( 2 );
        theCons.fill=GridBagConstraints.NONE;
        theCons.anchor=GridBagConstraints.CENTER;
        JButton theBTN = new JButton("Start");
        theBTN = new JButton("Start");
        theBTN.addActionListener(this);
        theBTN.setActionCommand("Start");
        theBTN.setMnemonic('S');
        theCons.gridx++;
        thePanel.add(theBTN,theCons);
        //Cancel button
        theBTN = new JButton("Cancel");
        theBTN.addActionListener(this);
        theBTN.setMnemonic('C');
        theBTN.setActionCommand("Cancel");
        theCons.gridx++;
        thePanel.add(theBTN,theCons);
        return thePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Cancel")) {
            dispose();
            return;
        }

        DomPlayer theHumanPlayer = new DomPlayer("Human");
//        if (!theHumanPlayer.addBoard(resolveAttrib(uri, "contents", attribs, null), resolveAttrib(uri, "bane", attribs, null), myBoardField.getText(), myBaneField.getText())) {
//            JOptionPane.showMessageDialog(this, "An error was found in the Board and/or Bane", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
        int j=0;
        for ( Enumeration< AbstractButton > theEnum = myBTNGroup.getElements();theEnum.hasMoreElements();j++) {
            if (theEnum.nextElement().isSelected() ){
                if (j==1)
                    theHumanPlayer.forceStart( 43 );
                if (j==2)
                    theHumanPlayer.forceStart( 52 );
            }
        }
    }

    @Override
    public void dispose() {
        myEngine.getGui().getGlassPane().setVisible(false);
        super.dispose();
    }
}