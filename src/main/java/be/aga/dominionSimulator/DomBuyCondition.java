package be.aga.dominionSimulator;

import be.aga.dominionSimulator.enums.DomBotComparator;
import be.aga.dominionSimulator.enums.DomBotFunction;
import be.aga.dominionSimulator.enums.DomBotOperator;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.gui.DomBuyConditionPanel;
import be.aga.dominionSimulator.gui.DomBuyRulePanel;


public class DomBuyCondition {
    private DomBotFunction leftFunction;
    private double leftValue;
    private DomCardName leftCardName;
    private DomBotComparator comparator;
    private DomBotFunction rightFunction;
    private double rightValue;
    private DomCardName rightCardName;
    private DomBotOperator extraOperator=DomBotOperator.plus;
    private double extraAttribute;
    private DomCardType rightCardType;
    private DomCardType leftCardType;
            
	public DomBuyCondition(DomBotFunction aLeftFunction,
			DomCardName aLeftCardName, 
			DomCardType aLeftCardType, 
			String aLeftValue,
			DomBotComparator aComparator, 
			DomBotFunction aRightFunction,
			DomCardName aRightCardName, 
			DomCardType aRightCardType, 
			String aRightValue,			
			DomBotOperator anExtraOperator, 
			String anExtraAttribute) {
		leftFunction=aLeftFunction;
		leftCardName=aLeftCardName;
		leftCardType=aLeftCardType;
		leftValue=new Double(aLeftValue).doubleValue();
		comparator=aComparator;
		rightFunction=aRightFunction;
		rightCardName=aRightCardName;
		rightCardType=aRightCardType;
		rightValue=new Double(aRightValue).doubleValue();
		extraOperator=anExtraOperator;
		if (anExtraAttribute==null || anExtraAttribute.equals("")) {
			extraAttribute=0;
	    } else { 
		  extraAttribute=new Double(anExtraAttribute).doubleValue();
	    }
	}

	public DomBuyCondition() {
	}

	public boolean isTrue(DomPlayer owner) {
		switch(leftFunction){
          case countCardsInDeck:
            leftValue=owner.countInDeck(leftCardName);
            break;
          case countCardTypeInDeck:
            leftValue=owner.count(leftCardType);
            break;
          case countCardsInSupply:
            leftValue=owner.getCurrentGame().countInSupply(leftCardName);
            break;
          case countCardsInPlay:
              leftValue=owner.getCardsFromPlay(leftCardName).size();
              break;
          case countCardsInHand:
              leftValue=owner.getCardsFromHand(leftCardName).size();
              break;
          case countAllCardsInDeck:
              leftValue=owner.countAllCards();
              break;
          case countAvailableMoney:
              leftValue=owner.getAvailableCoins();
              break;
          case countTurns:
              leftValue=owner.getTurns();
              break;
          case gainsNeededToEndGame:
              leftValue=owner.getCurrentGame().getGainsNeededToEndGame();
              break;
          case isActionPhase:
              leftValue=owner.isInBuyPhase()?0:1;
              break;
          case countBuysLeft:
              leftValue=owner.buysLeft;
              break;
          case getTotalMoney:
              leftValue=owner.getTotalMoneyInDeck();
              break;
          case getTotalMoneyExcludingNativeVillage:
              leftValue=owner.getTotalMoneyExcludingNativeVillage();
              break;
          case countVP:
              leftValue=owner.countVictoryPoints();
              break;
          case countMAXOpponentVP:
              leftValue=owner.countMaxOpponentsVictoryPoints();
              break;
          case countCardsLeftInDrawDeck:
              leftValue=owner.getDeck().getDrawDeckSize();
              break;
          case countEmptyPiles:
              leftValue=owner.getCurrentGame().countEmptyPiles();
              break;
          case countCardsLeftInSmallestPile:
              leftValue=owner.getCurrentGame().countCardsInSmallestPile();
              break;
          case countCardsInOpponentsDecks:
        	  leftValue=0;
        	  for (DomPlayer player : owner.getOpponents())
        		 leftValue+=player.countInDeck(leftCardName);
              break;
          case isPlusOneBuyTokenSet:
              leftValue=owner.isPlusOneBuyTokenSet() ? 1:0;
              break;
          case isPlusOneCardTokenSet:
              leftValue=owner.isPlusOneCardTokenSet() ? 1:0;
              break;
          case isPlusOneActionTokenSet:
              leftValue=owner.isPlusOneActionTokenSet() ? 1:0;
              break;
          case isPlusOneCoinTokenSet:
              leftValue=owner.isPlusOneCoinTokenSet() ? 1:0;
              break;
          case isMinus$2TokenSet:
              leftValue=owner.getMinus$2TokenOn()==null?0:1;
              break;
          case isEstateTokenPlaced:
              leftValue=owner.getEstateTokenOn()==null?0:1;
              break;
          case isTrashingTokenPlaced:
              leftValue=owner.isTrashingTokenSet()?1:0;
              break;
          case countOnTavernMat:
              leftValue=owner.countOnTavernMat(leftCardName);
              break;
          case isSwampHagActive:
              leftValue=owner.getCurrentGame().isSwampHagActive() ? 1 : 0;
              break;
          case countGainedCards:
              leftValue=owner.getCardsGainedLastTurn().size();
              break;
          case tokensOnDefiledShrine:
              leftValue=owner.getCurrentGame().getBoard().countVPon(DomCardName.Defiled_Shrine);
              break;
          case isTravellingFairActive:
              leftValue=owner.isTravellingFairActive() ? 1 : 0;
              break;
          case countVPon:
              leftValue=owner.getCurrentGame().getBoard().countVPon(leftCardName);
              break;
        }
		switch(rightFunction){
		  case countCardsInDeck:
			rightValue=owner.countInDeck(rightCardName);
			break;
          case countCardTypeInDeck:
            rightValue=owner.count(rightCardType);
            break;
		  case countCardsInSupply:
			rightValue=owner.getCurrentGame().countInSupply(rightCardName);
			break;
          case countCardsInHand:
              rightValue=owner.getCardsFromHand(rightCardName).size();
              break;
          case countCardsInPlay:
              rightValue=owner.getCardsFromPlay(rightCardName).size();
              break;
          case countAllCardsInDeck:
              rightValue=owner.countAllCards();
              break;
          case countAvailableMoney:
            rightValue=owner.getAvailableCoins();
            break;
          case gainsNeededToEndGame:
              rightValue=owner.getCurrentGame().getGainsNeededToEndGame();
              break;
          case countTurns:
              rightValue=owner.getTurns();
              break;
          case isActionPhase:
              rightValue=owner.isInBuyPhase()?0:1;
              break;
          case countBuysLeft:
              rightValue=owner.buysLeft;
              break;
          case getTotalMoney:
              rightValue=owner.getTotalMoneyInDeck();
              break;
          case getTotalMoneyExcludingNativeVillage:
            rightValue=owner.getTotalMoneyExcludingNativeVillage();
            break;
          case countVP:
              rightValue=owner.countVictoryPoints();
              break;
          case countMAXOpponentVP:
              rightValue=owner.countMaxOpponentsVictoryPoints();
              break;
          case countCardsLeftInDrawDeck:
              rightValue=owner.getDeck().getDrawDeckSize();
              break;
          case countEmptyPiles:
              rightValue=owner.getCurrentGame().countEmptyPiles();
              break;
          case countCardsLeftInSmallestPile:
              rightValue=owner.getCurrentGame().countCardsInSmallestPile();
              break;
          case countCardsInOpponentsDecks:
        	  rightValue=0;
        	  for (DomPlayer player : owner.getOpponents())
        		 rightValue+=player.countInDeck(rightCardName);
              break;
          case isPlusOneBuyTokenSet:
              rightValue=owner.isPlusOneBuyTokenSet() ? 1:0;
              break;
          case isPlusOneCardTokenSet:
              rightValue=owner.isPlusOneCardTokenSet() ? 1:0;
              break;
          case isPlusOneActionTokenSet:
              rightValue=owner.isPlusOneActionTokenSet() ? 1:0;
              break;
          case isPlusOneCoinTokenSet:
              rightValue=owner.isPlusOneCoinTokenSet() ? 1:0;
              break;
          case isMinus$2TokenSet:
              rightValue=owner.getMinus$2TokenOn()==null?0:1;
              break;
          case isEstateTokenPlaced:
              rightValue=owner.getEstateTokenOn()==null?0:1;
              break;
          case isTrashingTokenPlaced:
              rightValue=owner.isTrashingTokenSet()?1:0;
              break;
          case countOnTavernMat:
              rightValue=owner.countOnTavernMat(leftCardName);
              break;
          case isSwampHagActive:
              rightValue=owner.getCurrentGame().isSwampHagActive() ? 1 : 0;
              break;
          case countGainedCards:
              rightValue=owner.getCardsGainedLastTurn().size();
              break;
         case tokensOnDefiledShrine:
              rightValue=owner.getCurrentGame().getBoard().countVPon(DomCardName.Defiled_Shrine);
              break;
         case isTravellingFairActive:
              rightValue=owner.isTravellingFairActive() ? 1 : 0;
              break;
         case countVPon:
              rightValue=owner.getCurrentGame().getBoard().countVPon(leftCardName);
              break;
        }
		double theRightValue=rightValue;
		switch(extraOperator) {
		  case plus:
			theRightValue = rightValue+extraAttribute;
			break;
		  case minus:
	  	    theRightValue = rightValue-extraAttribute;
			break;
		  case multiplyWith:
			theRightValue = rightValue*extraAttribute;
			break;
		  case divideBy:
			theRightValue = rightValue/extraAttribute;
			break;
		}
		switch(comparator){
		  case equalTo:
			  return leftValue==theRightValue;
		  case smallerOrEqualThan:
			  return leftValue<=theRightValue;
		  case smallerThan:
			  return leftValue<theRightValue;
		  case greaterOrEqualThan:
			  return leftValue>=theRightValue;
		  case greaterThan:
			  return leftValue>theRightValue;
		}
		
		return false;
	}

	public void addRightHand(String aType, String anAttribute) {
	   rightFunction=DomBotFunction.valueOf(aType);
       if (anAttribute==null)
           return;
       if (rightFunction==DomBotFunction.constant) {
	     rightValue=Double.valueOf(anAttribute).doubleValue();
	     return;
	   } 
       if (rightFunction==DomBotFunction.countCardTypeInDeck) {
         rightCardType=DomCardType.valueOf(anAttribute);
         return;
       }
       rightCardName=DomCardName.valueOf(anAttribute);
	}

	public void addLeftHand(String aType, String anAttribute) {
       leftFunction=DomBotFunction.valueOf(aType);
       if (anAttribute==null)
           return;
       if (leftFunction==DomBotFunction.constant) {
         leftValue=Double.valueOf(anAttribute).doubleValue();
         return;
       } 
       if (leftFunction==DomBotFunction.countCardTypeInDeck) {
         leftCardType=DomCardType.valueOf(anAttribute);
         return;
       }
       leftCardName=DomCardName.valueOf(anAttribute);
    }

	public void addComparator(String aComparator) {
       comparator=DomBotComparator.valueOf(aComparator);	
	}

    /**
     */
    public void addExtraOperation( String aType, String anAttribute ) {
        extraOperator=DomBotOperator.valueOf(aType);   
        extraAttribute=Double.valueOf(anAttribute).doubleValue();   
    }

    public DomBotFunction getLeftFunction() {
		return leftFunction;
	}

	public void setLeftFunction(DomBotFunction leftFunction) {
		this.leftFunction = leftFunction;
	}

	public double getLeftValue() {
		return leftValue;
	}

	public void setLeftValue(double leftValue) {
		this.leftValue = leftValue;
	}

	public DomCardName getLeftCardName() {
		return leftCardName;
	}

	public void setLeftCardName(DomCardName leftCardName) {
		this.leftCardName = leftCardName;
	}

	public DomBotComparator getComparator() {
		return comparator;
	}

	public void setComparator(DomBotComparator comparator) {
		this.comparator = comparator;
	}

	public DomBotFunction getRightFunction() {
		return rightFunction;
	}

	public void setRightFunction(DomBotFunction rightFunction) {
		this.rightFunction = rightFunction;
	}

	public double getRightValue() {
		return rightValue;
	}

	public void setRightValue(double rightValue) {
		this.rightValue = rightValue;
	}

	public DomCardName getRightCardName() {
		return rightCardName;
	}

	public void setRightCardName(DomCardName rightCardName) {
		this.rightCardName = rightCardName;
	}

	public DomBotOperator getExtraOperator() {
		return extraOperator;
	}

	public void setExtraOperator(DomBotOperator extraOperator) {
		this.extraOperator = extraOperator;
	}

	public double getExtraAttribute() {
		return extraAttribute;
	}

	public void setExtraAttribute(double extraAttribute) {
		this.extraAttribute = extraAttribute;
	}

	public DomCardType getRightCardType() {
		return rightCardType;
	}

	public void setRightCardType(DomCardType rightCardType) {
		this.rightCardType = rightCardType;
	}

	public DomCardType getLeftCardType() {
		return leftCardType;
	}

	public void setLeftCardType(DomCardType leftCardType) {
		this.leftCardType = leftCardType;
	}

	/**
     * @param domBuyRulePanel 
     * @return
     */
    public DomBuyConditionPanel getGuiPanel(DomBuyRulePanel domBuyRulePanel) {
        return new DomBuyConditionPanel(this, domBuyRulePanel);
    }

	public String getXML(String theRuleIndentation) {
        StringBuilder theXML = new StringBuilder();
        String newline = System.getProperty( "line.separator" );
        String theIndentation = "   ";
        theXML.append(theRuleIndentation).append(theIndentation);
        theXML.append("<condition>").append(newline);
        theXML.append(theRuleIndentation).append(theIndentation).append(theIndentation);
        theXML.append("<left type=\"").append(leftFunction.name()).append("\"");
        if (leftFunction==DomBotFunction.constant) {
          theXML.append(" attribute=\"").append(leftValue).append("\"");
        } 
        if (leftFunction==DomBotFunction.countCardTypeInDeck) {
          theXML.append(" attribute=\"").append(leftCardType.name()).append("\"");
        }
        if (leftFunction==DomBotFunction.countCardsInDeck
                || leftFunction==DomBotFunction.countCardsInSupply
                || leftFunction==DomBotFunction.countCardsInOpponentsDecks
                || leftFunction==DomBotFunction.countCardsInHand
            || leftFunction==DomBotFunction.countCardsInPlay
                || leftFunction==DomBotFunction.countOnTavernMat
                || leftFunction==DomBotFunction.isPlusOneActionTokenSet
                || leftFunction==DomBotFunction.isPlusOneCardTokenSet
                || leftFunction==DomBotFunction.isPlusOneBuyTokenSet
                || leftFunction==DomBotFunction.isMinus$2TokenSet
                || leftFunction==DomBotFunction.isEstateTokenPlaced
                || leftFunction==DomBotFunction.isTrashingTokenPlaced
                || leftFunction==DomBotFunction.isPlusOneCoinTokenSet
                || leftFunction==DomBotFunction.countVPon
                ) {
          theXML.append(" attribute=\"").append(leftCardName.name()).append("\"");
        }
        theXML.append("/>").append(newline);
        theXML.append(theRuleIndentation).append(theIndentation).append(theIndentation);
        theXML.append("<operator type=\"").append(comparator.name()).append("\" />").append(newline);
        theXML.append(theRuleIndentation).append(theIndentation).append(theIndentation);
        theXML.append("<right type=\"").append(rightFunction.name()).append("\"");
        if (rightFunction==DomBotFunction.constant) {
          theXML.append(" attribute=\"").append(rightValue).append("\"");
        } 
        if (rightFunction==DomBotFunction.countCardTypeInDeck) {
          theXML.append(" attribute=\"").append(rightCardType.name()).append("\"");
        }
        if (rightFunction==DomBotFunction.countCardsInDeck
         || rightFunction==DomBotFunction.countCardsInSupply
         || rightFunction==DomBotFunction.countCardsInOpponentsDecks
         || rightFunction==DomBotFunction.countCardsInHand
         || rightFunction==DomBotFunction.countCardsInPlay
         || rightFunction==DomBotFunction.countOnTavernMat
         || rightFunction==DomBotFunction.isPlusOneActionTokenSet
         || rightFunction==DomBotFunction.isPlusOneCardTokenSet
                || rightFunction==DomBotFunction.isPlusOneBuyTokenSet
                || rightFunction==DomBotFunction.isMinus$2TokenSet
                || rightFunction==DomBotFunction.isEstateTokenPlaced
                || rightFunction==DomBotFunction.isTrashingTokenPlaced
                || rightFunction==DomBotFunction.isPlusOneCoinTokenSet
                || rightFunction==DomBotFunction.countVPon
                ) {
          theXML.append(" attribute=\"").append(rightCardName.name()).append("\"");
        }
        theXML.append("/>").append(newline);
        if (extraAttribute!=0) {
          theXML.append(theRuleIndentation).append(theIndentation).append(theIndentation);
          theXML.append("<extra_operation type=\"").append(extraOperator.name()).append("\"");
          theXML.append(" attribute=\"").append(extraAttribute).append("\" />").append(newline);
        }
        theXML.append(theRuleIndentation).append(theIndentation);
        theXML.append("</condition>").append(newline);
		return theXML.toString();
	}
}