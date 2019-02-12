package be.aga.dominionSimulator.gui.util;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import javax.swing.JOptionPane;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class KingdomViewer {

	public static void showKingDom(DomPlayer theSelectedBot) {
		HashSet<DomCardName> myBoard = new HashSet<DomCardName>();
		try {
			Integer theSize = 150;
			try {
				theSize = Integer.valueOf(JOptionPane.showInputDialog("Size (default=150)"));
			} catch (NumberFormatException e){
			}
			StringBuilder theStr = new StringBuilder();
 		      File tempfile = File.createTempFile("KingdomView", ".html");
 		      theStr.append("<table><tbody><tr>");
			  if (!theSelectedBot.getSuggestedBoard().isEmpty()) {
				  myBoard.addAll(theSelectedBot.getSuggestedBoard());
			  }
			  for (DomBuyRule theRule:theSelectedBot.getBuyRules()) {
				  if (theRule.getCardToBuy().hasCardType(DomCardType.Kingdom) || theRule.getCardToBuy()== DomCardName.Platinum || theRule.getCardToBuy()==DomCardName.Colony )
					  myBoard.add(theRule.getCardToBuy());
			  }
			  int theCount = 0;
			  for (DomCardName theCard : myBoard) {
			  	 if (theCard.hasCardType(DomCardType.Event) || theCard.hasCardType(DomCardType.Landmark) || theCard.hasCardType(DomCardType.Project))
			  		continue;
			  	 if (theCount++==5)
			  	 	theStr.append("</tr><tr>");
				  theStr.append("<td><img src='"+theCard.getImageLink()+"' width="+theSize+"></td>");
			  }
			theStr.append("</tr><tr>");
			for (DomCardName theCard : myBoard) {
				if (!theCard.hasCardType(DomCardType.Event) && !theCard.hasCardType(DomCardType.Landmark) && !theCard.hasCardType(DomCardType.Project))
					continue;
				theStr.append("<td><img src='"+theCard.getImageLink()+"' width="+theSize*1.6+"></td>");
			}
			if (theSelectedBot.getBaneCard()!=null) {
				theStr.append("</tr><tr>");
				theStr.append("<td><img src='"+theSelectedBot.getBaneCard().getImageLink()+"' width="+theSize+"></td>");
				theStr.append("</tr><tr><td>Bane</td>");
			}
			theStr.append("</tr></tbody></table>");
 		    BufferedWriter out = new BufferedWriter(new FileWriter(tempfile));
			out.write(theStr.toString());
			out.close();
			Desktop.getDesktop().open(tempfile);
			tempfile.deleteOnExit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
