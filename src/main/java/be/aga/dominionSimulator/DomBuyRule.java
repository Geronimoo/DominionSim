package be.aga.dominionSimulator;

import java.util.ArrayList;

import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;
import be.aga.dominionSimulator.gui.DomBotEditor;
import be.aga.dominionSimulator.gui.DomBuyRulePanel;

public class DomBuyRule {
	private final DomCardName cardToBuy;
	private ArrayList<DomBuyCondition> buyConditions=new ArrayList<DomBuyCondition>();
	private DomPlayStrategy playStrategy=DomPlayStrategy.standard;
	private DomCardName baneCard=null;
	
	public DomBuyRule(String aName, String aPlayStrategy, String aBane) {
       cardToBuy = DomCardName.valueOf(aName);
       if (aPlayStrategy!=null)
         playStrategy = DomPlayStrategy.valueOf(aPlayStrategy);
       if (aBane!=null)
         baneCard = DomCardName.valueOf(aBane);
	}

	public void addCondition(DomBuyCondition tmp) {
		buyConditions.add(tmp);
	}

	public ArrayList<DomBuyCondition> getBuyConditions() {
		return buyConditions;
	}

    /**
     * @return
     */
    public DomCardName getCardToBuy() {
        return cardToBuy;
    }

    /**
     * @param domBotEditor 
     * @return
     */
    public DomBuyRulePanel getGuiPanel(DomBotEditor domBotEditor) {
        return new DomBuyRulePanel(this, domBotEditor);
    }

	public String getXML() {
        StringBuilder theXML = new StringBuilder();
        String newline = System.getProperty( "line.separator" );
        String theIndentation = "   ";
        theXML.append(theIndentation);
        theXML.append("<buy name=\"").append(getCardToBuy().name()).append("\"");
        if (getPlayStrategy()!=DomPlayStrategy.standard) {
        	theXML.append(" strategy=\"").append(getPlayStrategy().name()).append("\"");
        }
        if (getBane()!=null) {
        	theXML.append(" bane=\"").append(getBane().name()).append("\"");
        }
        if (getBuyConditions().isEmpty()) {
        	theXML.append("/>").append(newline);
        } else {
        	theXML.append(">").append(newline);
            for (DomBuyCondition theCondition : getBuyConditions()){
              theXML.append(theCondition.getXML(theIndentation));
            }
            theXML.append(theIndentation);
            theXML.append("</buy>").append(newline);
        }
		return theXML.toString();
	}

	public DomPlayStrategy getPlayStrategy() {
		return playStrategy;
	}

	public DomCardName getBane() {
		return baneCard;
	}

	public void setBaneCard(DomCardName aCardName) {
		baneCard=aCardName;
	}

	public boolean wantsToBuyOrGainNow(DomPlayer owner) {
		if (owner.getPossessor()!=null)
			return wantsToBuyOrGainNow(owner.getPossessor());
		for (DomBuyCondition buyCondition : getBuyConditions()){
			if (!buyCondition.isTrue(owner))
				return false;
		}
		return true;
	}
}