package be.aga.dominionSimulator.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;

import be.aga.dominionSimulator.DomCard;

public class DomCardPanel extends JPanel {

	private static final int FIRST_TRESHOLD = 9;
	private static final Dimension MINIMUM_SIZE = new Dimension(600,150);
	private static final int SECOND_TRESHOLD = 11;
	private static final int THIRD_TRESHOLD = 35;
	private JSplitPane myBigSplit;
	private JSplitPane myMainSplit;
	private JPanel myMainPanel;
	private JPanel mySecondPanel;
	private JPanel myFourthPanel;
	private JPanel myThirdPanel;
	private JSplitPane myExtraSplit;
	private DomGameFrame myGameFrame;

	public DomCardPanel(String string, DomGameFrame aGameFrame) {
	    myGameFrame=aGameFrame;
//		setPreferredSize(new Dimension(800,300));
//		setMinimumSize(MINIMUM_SIZE);
		setLayout(new GridBagLayout());
		setBorder(new TitledBorder(string));
		GridBagConstraints theCons = DomGui.getGridBagConstraints(2);
	}

	public void setCards(ArrayList<DomCard> aCards) {
//		int theNumberOfRows = 1;
//		//2X^2 + 5X - "number of cards to display" > 0
//		while (theNumberOfRows*theNumberOfRows*2 + 5*theNumberOfRows - aCards.size()<0 || theNumberOfRows>=4)
//		  theNumberOfRows*=2;
//		if (theNumberOfRows==1){
//			GridBagConstraints theCons = DomGui.getGridBagConstraints(2);
//			for (DomCard card : aCards){
//				add(new DomCardLabel(card.getName()), theCons);
//				theCons.gridx++;
//			}
//		}
//		if (theNumberOfRows==2){
//			int i=0;
//			GridBagConstraints theCons = DomGui.getGridBagConstraints(2);
//			while (i<FIRST_TRESHOLD){
//				add(new DomCardLabel(aCards.get(i++).getName(), theNumberOfRows), theCons);
//				theCons.gridx++;
//			}
//			theCons = DomGui.getGridBagConstraints(2);
//			theCons.gridy++;
//			while (i<aCards.size()){
//				add(new DomCardLabel(aCards.get(i++).getName(), theNumberOfRows), theCons);
//				theCons.gridx++;
//			}
//		}
		if (aCards.size()>FIRST_TRESHOLD){
			int i=0;
			GridBagConstraints theCons = DomGui.getGridBagConstraints(2);
			while (i<aCards.size()){
	  	      add(new DomCardLabel(aCards.get(i++).getName(), myGameFrame,  false), theCons);
	  	      theCons.gridx++;
	  	      if (i%SECOND_TRESHOLD==0){
	  	    	  theCons.gridx=0;
	  	    	  theCons.gridy++;
	  	      }
			}
		} else{
			int i=0;
			GridBagConstraints theCons = DomGui.getGridBagConstraints(2);
			while (i<aCards.size()){
	  	      add(new DomCardLabel(aCards.get(i++).getName(), myGameFrame), theCons);
	  	      theCons.gridx++;
			}
		}
	}
}