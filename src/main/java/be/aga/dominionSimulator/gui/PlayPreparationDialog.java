package be.aga.dominionSimulator.gui;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomSet;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

/**
 * Created by jeroena on 19/02/2016.
 */
public class PlayPreparationDialog extends EscapeDialog implements ActionListener {

    private final DomEngine myEngine;
    private final ArrayList<DomPlayer> myBots;
    private HashSet<DomCardName> myBoard = new HashSet<DomCardName>();
    private JTextField myBoardField;
    private JTextField myBaneField;
    private ButtonGroup myBTNGroup;
    private DomCardName myBane;
    private HashSet<DomSet> myValidSets = new HashSet<DomSet>();

    public PlayPreparationDialog(DomEngine anEngine) {
        myValidSets.add(DomSet.Base);
        myEngine=anEngine;
        myEngine.getGui().getGlassPane().setVisible(true);
        myBots = myEngine.getGui().getBots();
        if (myBots.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please select (a) bot(s) to play against!", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
        resetBoard();
        resetBane();
        if (!checkValidSets()) {
            JOptionPane.showMessageDialog(this, "<html>Bot not supported (yet)<br>Supported Set: Base Dominion</html>", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
        setTitle("Prepare to play against " + myBots);
        buildGUI();
        pack();
        setVisible( true );
        RefineryUtilities.positionDialogRelativeToParent(this,1,1);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    private boolean checkValidSets() {
        for (DomCardName theCard:myBoard) {
            if (!myValidSets.contains(theCard.getSet()))
                return false;
        }
        if (myBane!=null && !myValidSets.contains(myBane.getSet()))
            return false;
        return true;
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
        myBoardField = new JTextField(myBoard.toString().replaceAll("\\[|\\]", "") , 50);
        theCons.gridx++;
        theCons.gridwidth=2;
        thePanel.add(myBoardField,theCons);
        theCons.gridx=0;
        theCons.gridy++;
        theCons.gridwidth=1;
        theCons.fill=GridBagConstraints.NONE;
        thePanel.add(new JLabel("Bane Card"),theCons);
        resetBane();
        myBaneField = new JTextField(myBane!=null?myBane.toString():"", 15);
        theCons.gridx++;
        thePanel.add(myBaneField,theCons);
        //randomize board
        JButton theBTN = new JButton("Randomize (rest of) the board");
        theBTN.addActionListener(this);
        theBTN.setActionCommand("Randomize");
        theBTN.setMnemonic('R');
        theCons.gridx++;
        theCons.gridwidth=1;
        theCons.anchor=GridBagConstraints.EAST;
        thePanel.add(theBTN,theCons);
        return thePanel;
    }

    private void resetBoard() {
        myBoard.clear();
        for (DomPlayer theBot : myBots) {
            if (!theBot.getSuggestedBoard().isEmpty()) {
                myBoard.addAll(theBot.getSuggestedBoard());
            }
        }
        for (DomPlayer theBot : myBots) {
            for (DomBuyRule theRule:theBot.getBuyRules()) {
                if (theRule.getCardToBuy().hasCardType(DomCardType.Kingdom) || theRule.getCardToBuy()== DomCardName.Platinum || theRule.getCardToBuy()==DomCardName.Colony )
                    myBoard.add(theRule.getCardToBuy());
            }
        }
    }

    private void resetBane() {
        myBane=null;
        for (DomPlayer theBot : myBots) {
            if (theBot.getBaneCard()!=null) {
                myBane=theBot.getBaneCard();
                myBaneField.setText(myBane.toString());
            }
        }
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
        if (e.getActionCommand().equals("Randomize")) {
            resetBoard();
            resetBane();
            while (myBoard.size()<10) {
                myBoard.add(DomCardName.getRandomKingdomCard());
            }
            if (myBoard.contains(DomCardName.Young_Witch) && myBane==null){
                Object[] theBanes = DomCardName.getPossibleBaneCards();
                DomCardName theRandomBane = ((DomCardName) theBanes[new Random().nextInt(theBanes.length)]);
                while (myBoard.contains(theRandomBane)) {
                    theRandomBane = ((DomCardName) theBanes[new Random().nextInt(theBanes.length)]);
                }
                myBane=theRandomBane;
            }
            update();
            return;
        }
        if (e.getActionCommand().equals("Start")) {
            DomPlayer theHumanPlayer = new DomPlayer("Human");
            theHumanPlayer.setHuman();
            if (!theHumanPlayer.addBoard(myBoardField.getText(),myBaneField.getText(),"0","")) {
              JOptionPane.showMessageDialog(this, "An error was found in the Board and/or Bane", "Error", JOptionPane.ERROR_MESSAGE);
              return;
            }
            int j = 0;
            for (Enumeration<AbstractButton> theEnum = myBTNGroup.getElements(); theEnum.hasMoreElements(); j++) {
                if (theEnum.nextElement().isSelected()) {
                    if (j == 1)
                        theHumanPlayer.forceStart(43);
                    if (j == 2)
                        theHumanPlayer.forceStart(52);
                }
            }
            myEngine.startHumanGame(theHumanPlayer);
            dispose();
        }
    }

    @Override
    public void dispose() {
        myEngine.getGui().getGlassPane().setVisible(false);
        super.dispose();
    }

    public void update() {
        myBoardField.setText(myBoard.toString().replaceAll("\\[|\\]", ""));
        myBaneField.setText(myBane!=null?myBane.toString():"");
    }
}