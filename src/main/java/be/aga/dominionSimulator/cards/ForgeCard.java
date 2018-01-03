package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

public class ForgeCard extends DomCard {
    public ForgeCard () {
      super( DomCardName.Forge);
    }

	@Override
	public void play() {
    	if (owner.isHumanOrPossessedByHuman()) {
			handleHuman();
			return;
		}
		ArrayList<DomCard> theFinalCardsToTrash = new ArrayList<DomCard>();
		for (DomCard theCard : owner.getCardsInHand()) {
			if (theCard.getTrashPriority()<=DomCardName.Copper.getTrashPriority(owner)) {
				theFinalCardsToTrash.add(theCard);
			}
		}
		int theTotalCoinCost = 0;
		for (DomCard theCard:theFinalCardsToTrash) {
			theTotalCoinCost+=theCard.getCoinCost(owner.getCurrentGame());
			owner.trash(owner.removeCardFromHand(theCard));
		}
		DomCardName theDesiredCard = owner.getDesiredCard(new DomCost(theTotalCoinCost, 0), true);
		if (theDesiredCard==null)
			theDesiredCard=owner.getCurrentGame().getBestCardInSupplyFor(owner,null,new DomCost(theTotalCoinCost, 0),true);
		if (theDesiredCard!=null)
			owner.gain(theDesiredCard);
	}

	private void handleHuman() {
		ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
		owner.getEngine().getGameFrame().askToSelectCards("Trash for Forge?" , owner.getCardsInHand(), theChosenCards, 0);
		int theTotalCoinCost = 0;
		for (DomCard theCard : theChosenCards) {
			theTotalCoinCost+=theCard.getCoinCost(owner.getCurrentGame());
			owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theCard.getName()).get(0)));
		}
		ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
		for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
			if (new DomCost(theTotalCoinCost,0).customCompare(theCard.getCost(owner.getCurrentGame()))==0 && owner.getCurrentGame().countInSupply(theCard)>0)
				theChooseFrom.add(theCard);
		}
		if (theChooseFrom.isEmpty())
			return;
		if (theChooseFrom.size()==1) {
			owner.gain(theChooseFrom.get(0));
		} else {
			owner.gain(owner.getEngine().getGameFrame().askToSelectOneCard("Gain from " + this.getName().toString(), theChooseFrom, "Mandatory!"));
		}
	}

	//TODO this implementation will trash without consideration to total money in deck
	//or what the player can buy with the rest of his cards...
    public void advancedPlay() {
      ArrayList<DomCard> cardsInHand = owner.getCardsInHand();
		ArrayList<DomCard> theFinalCardsToTrash = new ArrayList<DomCard>();
		DomCardName theDesiredCardIfForgeNotUsed = owner.getDesiredCard(owner.getTotalPotentialCurrency(), false);
		for (int i=1;i<Math.pow(2,cardsInHand.size());i++) {
			DomCost theTotalCurrency = DomCost.ZERO;
			ArrayList<DomCard> theCardsToTrash = new ArrayList<DomCard>();
			//runSimulation through all combinations by turning hand into a binary number
			String theStringRepr = Integer.toBinaryString(i);
			for (int j=0;j<theStringRepr.length();j++) {
				if (theStringRepr.charAt(j)>'0') {
					theCardsToTrash.add(cardsInHand.get(j));
				} else {
					theTotalCurrency.add(cardsInHand.get(j).getPotentialCurrencyValue());
				}
			}
			DomCardName theForgeTarget = tryForge(theCardsToTrash);
			if (theForgeTarget==null)
				continue;
			if (owner.getDesiredCard(owner.getTotalPotentialCurrency().subtract(theTotalCurrency), false)==null
					|| owner.getDesiredCard(owner.getTotalPotentialCurrency().subtract(theTotalCurrency), false).getTrashPriority(owner)<theDesiredCardIfForgeNotUsed.getTrashPriority(owner)) {
				if (theDesiredCardIfForgeNotUsed!=null && theForgeTarget.getTrashPriority(owner)<theDesiredCardIfForgeNotUsed.getTrashPriority(owner))
					continue;
			}
			if (theFinalCardsToTrash.isEmpty() ||
					(theForgeTarget.getTrashPriority(owner)>=tryForge(theFinalCardsToTrash).getTrashPriority(owner)
							&& countGarbageCardsIn(theCardsToTrash) >= countGarbageCardsIn(theFinalCardsToTrash))) {
				theFinalCardsToTrash=theCardsToTrash;
			}
		}
		if (!theFinalCardsToTrash.isEmpty()) {
			DomCardName theCardToGain = tryForge(theFinalCardsToTrash);
			for (DomCard theCard : theFinalCardsToTrash) {
				if (owner.getCardsInHand().contains((theCard)))
					owner.trash(owner.removeCardFromHand(theCard));
			}
			owner.gain(theCardToGain);
		} else {
			owner.gain(owner.getCurrentGame().getBestCardInSupplyFor(owner, null, DomCost.ZERO));
		}
    }

    private int countGarbageCardsIn(ArrayList<DomCard> theCardsToTrash) {
		int theCount=0;
		for (DomCard theCard : theCardsToTrash)
		  theCount+=theCard.getTrashPriority()<16 ? 1 : 0;
		return theCount;
	}

	private DomCardName tryForge(ArrayList<DomCard> theCardsToTrash) {
    	DomCost theTotalCost = DomCost.ZERO;
		for (DomCard theCard : theCardsToTrash) {
		  theTotalCost=theTotalCost.add(new DomCost(theCard.getName().getCost(owner.getCurrentGame()).getCoins(), 0) );
		}
		DomCardName theDesiredCard = owner.getDesiredCard(theTotalCost, true);
		if (theDesiredCard==null)
			theDesiredCard=owner.getCurrentGame().getBestCardInSupplyFor(owner, null, theTotalCost,true);
		return theDesiredCard;
	}

	@Override
	public boolean wantsToBePlayed() {
		return countGarbageCardsIn(owner.getCardsInHand()) > 2;
	}

	@Override
	public boolean wantsToBeMultiplied() {
		return false;
	}
}