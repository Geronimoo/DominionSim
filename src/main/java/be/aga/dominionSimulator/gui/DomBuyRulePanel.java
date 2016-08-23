package be.aga.dominionSimulator.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import be.aga.dominionSimulator.DomBuyCondition;
import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomBotComparator;
import be.aga.dominionSimulator.enums.DomBotFunction;
import be.aga.dominionSimulator.enums.DomBotOperator;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class DomBuyRulePanel extends JPanel implements ActionListener, ItemListener {

	private JComboBox myCardToBuyBox;
    private ArrayList<DomBuyConditionPanel> myBuyConditionPanels=new ArrayList<DomBuyConditionPanel>();
	private JPanel myConditionsPanel;
	private DomBotEditor myParent;
	private JComboBox myPlayStrategyBox;
	private DomBuyRule domBuyRule;
	private DomBotEditor domBotEditor;
	private DomPlayStrategy thePlaystrategy;
	private JLabel myPlayStrategyLBL;
	private JComboBox myBaneBox;
	private JLabel myBaneLBL;

	public DomBuyRulePanel(DomBuyRule domBuyRule, DomBotEditor domBotEditor) {
		myParent = domBotEditor;
        setLayout( new GridBagLayout() );
        setBorder( new TitledBorder( "" ));
        this.domBuyRule=domBuyRule;
        this.domBotEditor=domBotEditor;
        fill();
 	}

	private void fill() {
        GridBagConstraints theCons = DomGui.getGridBagConstraints( 2 );
		myCardToBuyBox = new JComboBox(DomCardName.getSafeValues());
//        myCardToBuyBox.setRenderer(new CustomComboBoxRenderer());
        myCardToBuyBox.setSelectedItem( domBuyRule.getCardToBuy() );
        myCardToBuyBox.addItemListener(this);
        add(new JLabel("Buy"), theCons);
        theCons.gridx++;
        add(myCardToBuyBox, theCons);

        myPlayStrategyBox = new JComboBox(domBuyRule.getCardToBuy().getPlayStrategies());
        myPlayStrategyBox.setSelectedItem( domBuyRule.getPlayStrategy() );
        theCons.gridx++;
        myPlayStrategyLBL = new JLabel("Play Strategy");
        add(myPlayStrategyLBL, theCons);
        theCons.gridx++;
        add(myPlayStrategyBox, theCons);
        if (myPlayStrategyBox.getItemCount()==1) {
          myPlayStrategyBox.setVisible(false);
          myPlayStrategyLBL.setVisible(false);
        }

        myBaneBox = new JComboBox(DomCardName.getPossibleBaneCards());
        if (domBuyRule.getBane()!=null)
          myBaneBox.setSelectedItem( domBuyRule.getBane() );
        theCons.gridx--;
        myBaneLBL = new JLabel("Bane card");
        add(myBaneLBL, theCons);
        theCons.gridx++;
        add(myBaneBox, theCons);
        if (domBuyRule.getBane()==null) {
          myBaneBox.setVisible(false);
          myBaneLBL.setVisible(false);
        }

        JButton myMoveUpBTN = new JButton(new ImageIcon(getClass().getResource("images/arrow_up.png")));
        myMoveUpBTN.setActionCommand( "Move Up" );
        myMoveUpBTN.putClientProperty("my rule box", this);
        myMoveUpBTN.addActionListener(domBotEditor);
        theCons.gridx++;
        add(myMoveUpBTN, theCons);
        
        JButton myMoveDownBTN = new JButton(new ImageIcon(getClass().getResource("images/arrow_down.png")));
        myMoveDownBTN.setActionCommand( "Move Down" );
        myMoveDownBTN.putClientProperty("my rule box", this);
        myMoveDownBTN.addActionListener(domBotEditor);
        theCons.gridx++;
        add(myMoveDownBTN, theCons);
        
        theCons.gridx++;
        DomGui.addHeavyLabel( this, theCons );

        JButton myDuplicateBTN = new JButton("Duplicate");
        myDuplicateBTN.setActionCommand( "Duplicate" );
        myDuplicateBTN.putClientProperty("my rule box", this);
        myDuplicateBTN.addActionListener(domBotEditor);
        theCons.gridx++;
        add(myDuplicateBTN, theCons);
        
        JButton myDeleteBTN = new JButton(new ImageIcon(getClass().getResource("images/delete_tb.gif")));
        myDeleteBTN.setActionCommand( "Delete" );
        myDeleteBTN.putClientProperty("my rule box", this);
        myDeleteBTN.addActionListener(domBotEditor);
        theCons.gridx++;
        add(myDeleteBTN, theCons);

        for (DomBuyCondition theCondition : domBuyRule.getBuyConditions()){
          myBuyConditionPanels.add(theCondition.getGuiPanel(this));
          add(myBuyConditionPanels.get(myBuyConditionPanels.size()-1),theCons);
        }
        
        myConditionsPanel = getConditionsPanel();
        fillConditionsPanel();
        theCons.gridx=0;
        theCons.gridy++;
        theCons.gridwidth=100;
        add(myConditionsPanel,theCons);
	}

    /**
     * @return
     */
    private JPanel getConditionsPanel() {
      final JPanel thePanel = new JPanel();
      thePanel.setLayout( new GridBagLayout() );
      thePanel.setBorder( new TitledBorder( "Buy conditions" ));
      return thePanel;
    }
	
	@Override
	public void actionPerformed(ActionEvent aE) {
      if (aE.getActionCommand().equals( "Delete" )) {
       	 JButton theButton = (JButton) aE.getSource();
       	 myBuyConditionPanels.remove(theButton.getParent());
 	     fillConditionsPanel();
 		 return;
      }
      if (aE.getActionCommand().equals( "add buy condition" )) {
    	DomBuyCondition theDummyCondition = new DomBuyCondition(DomBotFunction.countCardsInDeck
    			,(DomCardName) myCardToBuyBox.getSelectedItem(), DomCardType.Action , "0"
    			,DomBotComparator.equalTo
    			,DomBotFunction.constant,DomCardName.Copper, DomCardType.Action, "0" , DomBotOperator.plus, "0")  ;
        myBuyConditionPanels.add(theDummyCondition.getGuiPanel(this));
  	    fillConditionsPanel();
  		return;
      }
	}

	private void fillConditionsPanel() {
		myConditionsPanel.removeAll();
        final GridBagConstraints theCons = DomGui.getGridBagConstraints( 2 );
        theCons.fill=GridBagConstraints.NONE;
        JButton theBTN = new JButton("+");
        theBTN.addActionListener(this);
        theBTN.setActionCommand("add buy condition");
        myConditionsPanel.add(theBTN,theCons);
        for (DomBuyConditionPanel thePanel : myBuyConditionPanels){
          theCons.gridx=1;
          if (myBuyConditionPanels.indexOf(thePanel)==0) {
        	  myConditionsPanel.add(new JLabel("If"), theCons);
          } else {
        	  myConditionsPanel.add(new JLabel("and"), theCons);
          }
          theCons.gridx++;
          myConditionsPanel.add(thePanel,theCons);
          theCons.gridx++;
          DomGui.addHeavyLabel(myConditionsPanel, theCons);
          theCons.gridy++;
        }
        myParent.validate();
	}

	public DomBuyRule getBuyRule(DomPlayer theNewPlayer) {
		thePlaystrategy = (DomPlayStrategy) myPlayStrategyBox.getSelectedItem();
		if (thePlaystrategy==null)
			thePlaystrategy=DomPlayStrategy.standard;
		DomCardName cardToBuy = (DomCardName) myCardToBuyBox.getSelectedItem();
		String theBaneCard = null;
		if (cardToBuy==DomCardName.Young_Witch)
			theBaneCard = ((DomCardName) myBaneBox.getSelectedItem()).name();
		DomBuyRule theRule = new DomBuyRule(cardToBuy.name(), thePlaystrategy.name(), theBaneCard);
		if (theRule.getCardToBuy()==DomCardName.Young_Witch)
		  theRule.setBaneCard((DomCardName)myBaneBox.getSelectedItem());
		for (DomBuyConditionPanel theConditionPanel : myBuyConditionPanels) {
		  theRule.addCondition(theConditionPanel.getBuyCondition());	
		}
		if (theRule.getPlayStrategy()!=DomPlayStrategy.standard) {
     		theNewPlayer.addPlayStrategy(theRule.getCardToBuy().name(), theRule.getPlayStrategy().name());
		}
		return theRule;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		myPlayStrategyBox.removeAllItems();
		DomCardName theSelectedCardName = (DomCardName)myCardToBuyBox.getSelectedItem();
		for (Object theStrategy : theSelectedCardName.getPlayStrategies()) {
	      myPlayStrategyBox.addItem(theStrategy);
		}
        if (myPlayStrategyBox.getItemCount()==1) {
          myPlayStrategyBox.setVisible(false);
          myPlayStrategyLBL.setVisible(false);
        } else {
          myPlayStrategyBox.setVisible(true);
          myPlayStrategyLBL.setVisible(true);
        }
        if (myCardToBuyBox.getSelectedItem()==DomCardName.Young_Witch){
            myBaneBox.setVisible(true);
            myBaneLBL.setVisible(true);
        } else {
            myBaneBox.setVisible(false);
            myBaneLBL.setVisible(false);
        }
	}

	public void toggleBuyConditions() {
	  myConditionsPanel.setVisible(!myConditionsPanel.isVisible());
	}
}