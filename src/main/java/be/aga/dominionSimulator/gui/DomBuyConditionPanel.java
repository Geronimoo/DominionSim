package be.aga.dominionSimulator.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import be.aga.dominionSimulator.DomBuyCondition;
import be.aga.dominionSimulator.enums.DomBotComparator;
import be.aga.dominionSimulator.enums.DomBotFunction;
import be.aga.dominionSimulator.enums.DomBotOperator;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class DomBuyConditionPanel extends JPanel implements ItemListener {
	private static final long serialVersionUID = 1L;
    Object[] numberValues = new Object[100];
	private JComboBox myLeftFunctionBox;
	private JComboBox myLeftCardBox;
	private JComboBox myLeftTypeBox;
	private JComboBox myRightFunctionBox;
	private JComboBox myRightCardBox;
	private JComboBox myRightTypeBox;
	private JComboBox myExtraOperator;
	private JComboBox myComparatorBox;
	private JComboBox myExtraValue;
	
	public DomBuyConditionPanel(DomBuyCondition aBuyCondition, DomBuyRulePanel aBuyRulePanel) {
		for (int i=0;i<100;i++){
		  numberValues[i]=i;	
		}
		setLayout( new GridBagLayout() );
        GridBagConstraints theCons = DomGui.getGridBagConstraints( 2 );
        
        //left function
        ArrayList<Object> theValues = new ArrayList<Object>();
        Collections.addAll(theValues, DomBotFunction.values());
        Collections.addAll(theValues, numberValues);
        theValues.remove(DomBotFunction.constant);
        myLeftFunctionBox = new JComboBox(theValues.toArray());
        if (aBuyCondition.getLeftFunction()==DomBotFunction.constant){
          myLeftFunctionBox.setSelectedItem((int)aBuyCondition.getLeftValue());
        } else {
          myLeftFunctionBox.setSelectedItem( aBuyCondition.getLeftFunction());
        }
        myLeftFunctionBox.addItemListener(this);
        add(myLeftFunctionBox, theCons);

        //add the comparator
        myComparatorBox = new JComboBox(DomBotComparator.values());
        myComparatorBox.setSelectedItem( aBuyCondition.getComparator());
        theCons.gridx++;
        theCons.gridheight=2;
        theCons.fill=GridBagConstraints.NONE;
        theCons.anchor=GridBagConstraints.CENTER;
        add(myComparatorBox, theCons); 
        theCons.gridheight=1;
        theCons.fill=GridBagConstraints.BOTH;
        theCons.anchor=GridBagConstraints.NORTHWEST;

        //right function
        myRightFunctionBox = new JComboBox(theValues.toArray());
        if (aBuyCondition.getRightFunction()==DomBotFunction.constant){
          myRightFunctionBox.setSelectedItem((int)aBuyCondition.getRightValue());
        } else {
          myRightFunctionBox.setSelectedItem( aBuyCondition.getRightFunction());
        }
        myRightFunctionBox.addItemListener(this);
        theCons.gridx++;
        add(myRightFunctionBox, theCons);

        //add the extra operator        
        myExtraOperator = new JComboBox(DomBotOperator.values());
        myExtraOperator.setSelectedItem( aBuyCondition.getExtraOperator() == null ? DomBotOperator.plus : aBuyCondition.getExtraOperator());
        theCons.gridx++;
        theCons.gridheight=2;
        theCons.fill=GridBagConstraints.NONE;
        theCons.anchor=GridBagConstraints.CENTER;
        add(myExtraOperator, theCons);
        myExtraValue = new JComboBox(numberValues);
        myExtraValue .setSelectedItem((int)aBuyCondition.getExtraAttribute());
        theCons.gridx++;
        add(myExtraValue, theCons);

        theCons.gridx++;
        DomGui.addHeavyLabel( this, theCons );
        
        JButton myDeleteBTN = new JButton(new ImageIcon(getClass().getResource("images/delete.gif")));
        myDeleteBTN.setActionCommand( "Delete" );
        myDeleteBTN.addActionListener(aBuyRulePanel);
        theCons.gridx++;
        add(myDeleteBTN, theCons);
        theCons.gridheight=1;
        theCons.fill=GridBagConstraints.BOTH;
        theCons.anchor=GridBagConstraints.NORTHWEST;

        theCons.gridy++;
        theCons.gridx=0;
        //left card name box
        myLeftCardBox=new JComboBox(DomCardName.values());
//        myLeftCardBox.setRenderer(new CustomComboBoxRenderer());
        myLeftCardBox.setVisible(false);
        if (aBuyCondition.getLeftFunction() == DomBotFunction.countCardsInDeck
         || aBuyCondition.getLeftFunction() == DomBotFunction.countCardsInSupply
         || aBuyCondition.getLeftFunction() == DomBotFunction.countCardsInOpponentsDecks
         || aBuyCondition.getLeftFunction() == DomBotFunction.countCardsInHand
         || aBuyCondition.getLeftFunction() == DomBotFunction.countCardsInPlay
         || aBuyCondition.getLeftFunction() == DomBotFunction.countOnTavernMat
         || aBuyCondition.getLeftFunction() == DomBotFunction.isPlusOneActionTokenSet
         || aBuyCondition.getLeftFunction() == DomBotFunction.isPlusOneCardTokenSet
                || aBuyCondition.getLeftFunction() == DomBotFunction.isPlusOneBuyTokenSet
                || aBuyCondition.getLeftFunction() == DomBotFunction.isMinus$2TokenSet
                || aBuyCondition.getLeftFunction() == DomBotFunction.isEstateTokenPlaced
                || aBuyCondition.getLeftFunction() == DomBotFunction.isTrashingTokenPlaced
                || aBuyCondition.getLeftFunction() == DomBotFunction.isPlusOneCoinTokenSet
                || aBuyCondition.getLeftFunction() == DomBotFunction.countVPon
                ) {
        	myLeftCardBox.setVisible(true);
        	myLeftCardBox.setSelectedItem( aBuyCondition.getLeftCardName() );
        }
//        theCons.gridx++;
        add(myLeftCardBox, theCons);
        
        //left card type box (same position as the card name box)
        myLeftTypeBox=new JComboBox(DomCardType.values());
        myLeftTypeBox.setVisible(false);
        if (aBuyCondition.getLeftFunction() == DomBotFunction.countCardTypeInDeck){
        	myLeftTypeBox.setVisible(true);
        	myLeftTypeBox.setSelectedItem( aBuyCondition.getLeftCardType());
        }
        add(myLeftTypeBox, theCons);
        //space for the Comparator
        theCons.gridx++;
        //Right card name box
        myRightCardBox=new JComboBox(DomCardName.values());
        myRightCardBox.setRenderer(new CustomComboBoxRenderer());
        myRightCardBox.setVisible(false);
        if (aBuyCondition.getRightFunction() == DomBotFunction.countCardsInDeck
         || aBuyCondition.getRightFunction() == DomBotFunction.countCardsInOpponentsDecks
         || aBuyCondition.getRightFunction() == DomBotFunction.countCardsInSupply
         || aBuyCondition.getRightFunction() == DomBotFunction.countCardsInHand
         || aBuyCondition.getRightFunction() == DomBotFunction.countCardsInPlay
         || aBuyCondition.getRightFunction() == DomBotFunction.countOnTavernMat
         || aBuyCondition.getRightFunction() == DomBotFunction.isPlusOneActionTokenSet
         || aBuyCondition.getRightFunction() == DomBotFunction.isPlusOneCardTokenSet
                || aBuyCondition.getRightFunction() == DomBotFunction.isPlusOneBuyTokenSet
                || aBuyCondition.getRightFunction() == DomBotFunction.isMinus$2TokenSet
                || aBuyCondition.getRightFunction() == DomBotFunction.isEstateTokenPlaced
                || aBuyCondition.getRightFunction() == DomBotFunction.isTrashingTokenPlaced
                || aBuyCondition.getRightFunction() == DomBotFunction.isPlusOneCoinTokenSet
                || aBuyCondition.getRightFunction() == DomBotFunction.countVPon
                ) {
        	myRightCardBox.setVisible(true);
        	myRightCardBox.setSelectedItem( aBuyCondition.getRightCardName() );
        }
        theCons.gridx++;
        add(myRightCardBox, theCons);
        
        //Right card type box (same position as the card name box)
        myRightTypeBox=new JComboBox(DomCardType.values());
        myRightTypeBox.setVisible(false);
        if (aBuyCondition.getRightFunction() == DomBotFunction.countCardTypeInDeck){
        	myRightTypeBox.setVisible(true);
        	myRightTypeBox.setSelectedItem( aBuyCondition.getRightCardType());
        }
        add(myRightTypeBox, theCons);
	}
	
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getSource()==myLeftFunctionBox) {
				myLeftCardBox.setVisible(false);
				myLeftTypeBox.setVisible(false);
				if (myLeftFunctionBox.getSelectedItem() == DomBotFunction.countCardsInDeck
				 || myLeftFunctionBox.getSelectedItem() == DomBotFunction.countCardsInOpponentsDecks
				 || myLeftFunctionBox.getSelectedItem() == DomBotFunction.countCardsInSupply
				 || myLeftFunctionBox.getSelectedItem() == DomBotFunction.countCardsInHand
				 || myLeftFunctionBox.getSelectedItem() == DomBotFunction.countCardsInPlay
                 || myLeftFunctionBox.getSelectedItem() == DomBotFunction.countOnTavernMat
                 || myLeftFunctionBox.getSelectedItem() == DomBotFunction.isPlusOneActionTokenSet
                 || myLeftFunctionBox.getSelectedItem() == DomBotFunction.isPlusOneCardTokenSet
                        || myLeftFunctionBox.getSelectedItem() == DomBotFunction.isPlusOneBuyTokenSet
                        || myLeftFunctionBox.getSelectedItem() == DomBotFunction.isMinus$2TokenSet
                        || myLeftFunctionBox.getSelectedItem() == DomBotFunction.isEstateTokenPlaced
                        || myLeftFunctionBox.getSelectedItem() == DomBotFunction.isTrashingTokenPlaced
                        || myLeftFunctionBox.getSelectedItem() == DomBotFunction.isPlusOneCoinTokenSet
                        || myLeftFunctionBox.getSelectedItem() == DomBotFunction.countVPon
                        ) {
    				myLeftCardBox.setVisible(true);
				}
				if (myLeftFunctionBox.getSelectedItem() == DomBotFunction.countCardTypeInDeck) {
    				myLeftTypeBox.setVisible(true);
				}
			}
			if (e.getSource()==myRightFunctionBox) {
				myRightCardBox.setVisible(false);
				myRightTypeBox.setVisible(false);
				if (myRightFunctionBox.getSelectedItem() == DomBotFunction.countCardsInDeck
			     || myRightFunctionBox.getSelectedItem() == DomBotFunction.countCardsInOpponentsDecks
		         || myRightFunctionBox.getSelectedItem() == DomBotFunction.countCardsInSupply
		         || myRightFunctionBox.getSelectedItem() == DomBotFunction.countCardsInHand
				 || myRightFunctionBox.getSelectedItem() == DomBotFunction.countCardsInPlay
                 || myRightFunctionBox.getSelectedItem() == DomBotFunction.countOnTavernMat
                 || myRightFunctionBox.getSelectedItem() == DomBotFunction.isPlusOneActionTokenSet
                 || myRightFunctionBox.getSelectedItem() == DomBotFunction.isPlusOneCardTokenSet
                        || myRightFunctionBox.getSelectedItem() == DomBotFunction.isPlusOneBuyTokenSet
                        || myRightFunctionBox.getSelectedItem() == DomBotFunction.isMinus$2TokenSet
                        || myRightFunctionBox.getSelectedItem() == DomBotFunction.isEstateTokenPlaced
                        || myRightFunctionBox.getSelectedItem() == DomBotFunction.isTrashingTokenPlaced
                        || myRightFunctionBox.getSelectedItem() == DomBotFunction.isPlusOneCoinTokenSet
                        || myRightFunctionBox.getSelectedItem() == DomBotFunction.countVPon
                       ) {
    				myRightCardBox.setVisible(true);
				}
				if (myRightFunctionBox.getSelectedItem() == DomBotFunction.countCardTypeInDeck) {
    				myRightTypeBox.setVisible(true);
				}
			}
		}
		
		public DomBuyCondition getBuyCondition() {
			DomBotFunction theLeftFunction;
			if (myLeftFunctionBox.getSelectedItem().getClass().equals(DomBotFunction.class))
			  theLeftFunction =(DomBotFunction)myLeftFunctionBox.getSelectedItem();
			else
			  theLeftFunction=DomBotFunction.constant;

			DomBotFunction theRightFunction;
			if (myRightFunctionBox.getSelectedItem().getClass().equals(DomBotFunction.class))
			  theRightFunction =(DomBotFunction)myRightFunctionBox.getSelectedItem();
			else
			  theRightFunction=DomBotFunction.constant;

			DomBuyCondition theCondition = 
				new DomBuyCondition( 
				theLeftFunction,
               	(DomCardName) myLeftCardBox.getSelectedItem()  ,
               	(DomCardType) myLeftTypeBox.getSelectedItem() ,
               	theLeftFunction==DomBotFunction.constant? myLeftFunctionBox.getSelectedItem().toString():"0",
               	(DomBotComparator) myComparatorBox.getSelectedItem() ,
               	theRightFunction ,
               	(DomCardName) myRightCardBox.getSelectedItem()  ,
               	(DomCardType) myRightTypeBox.getSelectedItem() ,
               	theRightFunction==DomBotFunction.constant? myRightFunctionBox.getSelectedItem().toString():"0",
               	(DomBotOperator) myExtraOperator.getSelectedItem() ,
               	myExtraValue.getSelectedItem().toString() );
			return theCondition;
		}
}