package be.aga.dominionSimulator.gui;

import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomBotType;
import be.aga.dominionSimulator.enums.DomCardName;
import org.jfree.ui.RefineryUtilities;

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
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class DomBotSelector extends EscapeDialog
        implements ListSelectionListener, ActionListener, WindowListener, DomCardFilterPanel.CardsChangedListener {
    private DomEngine myEngine;
    private JList myBotTypeList;
    private JList myBotList;
    private DomCardFilterPanel mCardFilterPanel;

    private static int[] sSelectedIndices = new int[]{8, 10};

    Action chooseAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            myEngine.setSelectedBot(myBotList.getSelectedValue());
            dispose();
        }
    };

    public DomBotSelector(DomEngine anEngine, final DomPlayer aSelectedBot) {
        myEngine = anEngine;
        buildGUI();
        addWindowListener(this);
        setTitle("Select a strategy (click to select multiple types)");
        pack();
        RefineryUtilities.centerFrameOnScreen(this);
        setVisible(true);
        myBotList.requestFocus();
        Runnable doScroll = new Runnable() {
            public void run() {
                myBotList.setSelectedValue(aSelectedBot, true);
            }
        };
        SwingUtilities.invokeLater(doScroll);
    }

    private void buildGUI() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(600, 600));
        final GridBagConstraints theCons = DomGui.getGridBagConstraints(2);
        theCons.gridy++;
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        mCardFilterPanel = new DomCardFilterPanel();
        mCardFilterPanel.addListener(this);
        add(mCardFilterPanel, c);
        theCons.weighty = 1.0;
        theCons.weightx = 1.0;
        add(getSelectionPanel(), theCons);
        theCons.weighty = 0.0;
        theCons.gridy++;
        add(getButtonPanel(), theCons);
    }

    private JPanel getButtonPanel() {
        final JPanel thePanel = new JPanel();
        thePanel.setLayout(new GridBagLayout());
        final GridBagConstraints theCons = DomGui.getGridBagConstraints(2);
        theCons.fill = GridBagConstraints.NONE;
        theCons.anchor = GridBagConstraints.CENTER;
        //Clear button
        JButton theBTN = new JButton("Clear selection");
        theBTN.addActionListener(this);
        theBTN.setMnemonic('l');
        theBTN.setActionCommand("Clear");
        theCons.gridx++;
        thePanel.add(theBTN, theCons);
        theCons.gridx++;
        DomGui.addHeavyLabel(thePanel, theCons);
        //OK button
        theBTN = new JButton("OK");
        theBTN.addActionListener(this);
        theBTN.setMnemonic('O');
        theBTN.setActionCommand("OK");
        theCons.gridx++;
        thePanel.add(theBTN, theCons);
        //Cancel button
        theBTN = new JButton("Cancel");
        theBTN.addActionListener(this);
        theBTN.setMnemonic('C');
        theBTN.setActionCommand("Cancel");
        theCons.gridx++;
        thePanel.add(theBTN, theCons);
        theCons.gridx++;
        DomGui.addHeavyLabel(thePanel, theCons);
        //Delete button
        theBTN = new JButton("Delete selected");
        theBTN.addActionListener(this);
        theBTN.setMnemonic('D');
        theBTN.setActionCommand("Delete");
        theCons.gridx++;
        thePanel.add(theBTN, theCons);
        return thePanel;
    }

    private JPanel getSelectionPanel() {
        final JPanel thePanel = new JPanel();
        thePanel.setLayout(new GridBagLayout());
        final GridBagConstraints theCons = DomGui.getGridBagConstraints(2);
        JScrollPane theTypePane = new JScrollPane(getBotTypeList(), JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        theTypePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        theTypePane.setBorder(new TitledBorder("Type"));
        theCons.weighty = 1.0;
        theCons.weightx = 0.0;
        thePanel.add(theTypePane, theCons);
        theCons.gridx++;
        theCons.weightx = 1.0;
        JScrollPane theBotPane = new JScrollPane(getBotList(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        theBotPane.setBorder(new TitledBorder("Select a strategy"));
        thePanel.add(theBotPane, theCons);
        return thePanel;
    }

    private JList getBotTypeList() {
        myBotTypeList = new JList(DomBotType.values());
        myBotTypeList.setSelectionModel(new ToggleListSelectionModel());
        myBotTypeList.setSelectedIndices(sSelectedIndices);
        myBotTypeList.addListSelectionListener(this);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Fix an annoying issue where space is left for the vertical scroll bar after initial layout even
                // when it isn't needed.
                myBotTypeList.revalidate();
                myBotTypeList.repaint();
            }
        });
        return myBotTypeList;
    }

    @SuppressWarnings("serial")
    private JList getBotList() {
        myBotList = new JList(myEngine.getBots(myBotTypeList.getSelectedValues(), new DomCardName[]{})) {
            // This method is called as the cursor moves within the list.
            public String getToolTipText(MouseEvent evt) {
                int index = locationToIndex(evt.getPoint());
                Object item = getModel().getElementAt(index);
                String theDescription = ((DomPlayer) item).getDescription().replaceAll("XXXX", "<br>");
                return "<html>" + theDescription + "</html>";
            }
        };
        new ListAction(myBotList, chooseAction);
        return myBotList;
    }

    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource().equals(myBotTypeList)) {
            updateBotList();
        }
    }

    public void cardsChanged(DomCardName[] cards) {
        updateBotList();
    }

    private void updateBotList() {
        myBotList.setListData(myEngine.getBots(myBotTypeList.getSelectedValues(), mCardFilterPanel.getSelectedCards()));
        myBotList.requestFocus();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Cancel")) {
            dispose();
        }
        if (e.getActionCommand().equals("OK")) {
            myEngine.setSelectedBot(myBotList.getSelectedValue());
            dispose();
        }
        if (e.getActionCommand().equals("Clear")) {
            myEngine.setSelectedBot(null);
            dispose();
        }
        if (e.getActionCommand().equals("Delete")) {
            myEngine.deleteBot((DomPlayer) myBotList.getSelectedValue());
            updateBotList();
        }
    }

    public void windowClosed(WindowEvent arg0) {
        // Store the current type filter for the next time.
        sSelectedIndices = myBotTypeList.getSelectedIndices();
    }

    public void windowActivated(WindowEvent arg0) {
    }

    public void windowClosing(WindowEvent arg0) {
    }

    public void windowDeactivated(WindowEvent arg0) {
    }

    public void windowDeiconified(WindowEvent arg0) {
    }

    public void windowIconified(WindowEvent arg0) {
    }

    public void windowOpened(WindowEvent arg0) {
    }
}
