package be.aga.dominionSimulator;

import java.util.Comparator;

import be.aga.dominionSimulator.cards.EstateCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;


public class DomCard implements Comparable< DomCard >{
    public static final DomCard NONEXISTANT_CARD = new DomCard(null);
    DomCardName name;
    public DomPlayer owner = null;
	private boolean isFromBlackMarket=false;
	private boolean isTaggedByHerbalist;
	private boolean isBane=false;
	private boolean isTaggedByScheme;
	private boolean discardAtCleanUp=true;

    public static final Comparator<DomCard> SORT_BY_NAME = new Comparator<DomCard>(){
        public int compare( DomCard aO1, DomCard aO2 ) {
            return aO1.getName().compareTo(aO2.getName());
        }
    };

    public static final Comparator<DomCard> SORT_BY_COIN_COST = new Comparator<DomCard>(){
        public int compare( DomCard aO1, DomCard aO2 ) {
            if (aO1.getName().getCoinCost(null)< aO2.getName().getCoinCost(null))
                return -1;
            if (aO1.getName().getCoinCost(null)> aO2.getName().getCoinCost(null))
                return 1;
            return 0;
        }
    };

    public static final Comparator<DomCard> SORT_FOR_TRASHING = new Comparator<DomCard>(){
        public int compare( DomCard aO1, DomCard aO2 ) {
            if (aO1.getTrashPriority()< aO2.getTrashPriority())
                return -1;
            if (aO1.getTrashPriority() > aO2.getTrashPriority())
                return 1;
            return 0;
        }
      };

      public static final Comparator<DomCard> SORT_FOR_PLAYING = new Comparator<DomCard>(){
          public int compare( DomCard aO1, DomCard aO2 ) {
              if (aO1.getPlayPriority()< aO2.getPlayPriority())
                  return -1;
              if (aO1.getPlayPriority() > aO2.getPlayPriority())
                  return 1;
              return 0;
          }
        };

      public static final Comparator<DomCard> SORT_FOR_DISCARDING = new Comparator<DomCard>(){
          public int compare( DomCard aO1, DomCard aO2 ) {
              if (aO1.getDiscardPriority(1)< aO2.getDiscardPriority(1))
                  return -1;
              if (aO1.getDiscardPriority(1) > aO2.getDiscardPriority(1))
                  return 1;
              return 0;
          }
        };

    public static final Comparator<DomCard> SORT_FOR_DISCARDING_REVERSE = new Comparator<DomCard>(){
        public int compare( DomCard aO1, DomCard aO2 ) {
            if (aO1.getDiscardPriority(1)> aO2.getDiscardPriority(1))
                return -1;
            if (aO1.getDiscardPriority(1) < aO2.getDiscardPriority(1))
                return 1;
            return 0;
        }
    };

    public static final Comparator<DomCard> SORT_FOR_DISCARD_FROM_HAND = new Comparator<DomCard>(){
            public int compare( DomCard aO1, DomCard aO2 ) {
                int theActionsLeft = aO1.owner.getActionsAndVillagersLeft();
                if (aO1.getDiscardPriority(theActionsLeft)< aO2.getDiscardPriority(theActionsLeft))
                    return -1;
                if (aO1.getDiscardPriority(theActionsLeft) > aO2.getDiscardPriority(theActionsLeft))
                    return 1;
                return 0;
            }
          };
    private boolean markedForPrince=false;
    private DomCard shapeshifterCard =null;
    private EstateCard estateCard;
    private boolean hasReacted;

    /**
     * @param aCardName
     */
    public DomCard ( DomCardName aCardName ) {
      name = aCardName;
    }

    /**
     * @return
     */
    public DomCardName getName() {
      return name;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return name.toHTML();
    }

    /**
     * @return
     */
    public boolean hasCardType( DomCardType aType ) {
      return name.hasCardType( aType );
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#customCompare(java.lang.Object)
     */
    public int compareTo( DomCard aO ) {
        if (getName().getCoinValue()< aO.getName().getCoinValue())
            return 1;
        if (getName().getCoinValue()> aO.getName().getCoinValue())
            return -1;
        return 0;
    }

    /**
     * @return
     */
    public int getPlayPriority() {
        if (owner!=null && owner.plusOneActionTokenOn()==name && hasCardType(DomCardType.Terminal))
            return DomCardName.Great_Hall.getPlayPriority();
        return name.getPlayPriority();
    }

    /**
     * @return
     */
    public double getPotentialCoinValue() {
        if (owner==null)
            return 0;
      if (owner.getActionsAndVillagersLeft()==0 && hasCardType( DomCardType.Action ) && owner.getCardsInHand().contains( this ) ) {
        return 0;
      } else {
        return name.getCoinValue();
      }
    }

    /**
     * @param aActionsLeft
     * @return
     */
    public int getDiscardPriority( int aActionsLeft ) {
//    	//TODO problem with Royal Seal whic
//        if (aActionsLeft>0 && getTrashPriority()<16 && owner.getCardsFromHand(DomCardType.Trasher).size()>0)
//          //added this to make sure player keeps Curse in hand when he also has a Bishop for instance 
//          return 36-getTrashPriority();
        return name.getDiscardPriority(aActionsLeft);
    }

    /**
     * @return
     */
    public int getVictoryValue() {
      return name.getVictoryValue(owner);
    }

    /**
     * @return
     */
    public int getTrashPriority() {
      return name.getTrashPriority();
    }

    /**
     * @return
     */
    public boolean wantsToBePlayed() {
      return true;
    }

    /**
     * @param aPlayer
     */
    public void setOwner( DomPlayer aPlayer ) {
      owner = aPlayer;
    }

    /**
     * 
     */
    public void play() {
      owner.addAvailableCoinsSilent(getCoinValue());
      owner.availablePotions+=getPotionValue();        
    }

    /**
     * @return
     */
    public int getPotionValue() {
      return 0;
    }

    public int getCoinValue() {
        return name.getCoinValue();
    }

    public void resolveDuration() {}

    public int getCoinCost( DomGame aGame) {
        return name.getCoinCost( aGame.getActivePlayer() );
    }

    public void handleCleanUpPhase() {
        if (!discardAtCleanUp()) {
            owner.addToCardsToStayInPlay(this);
            return;
        }
        if (isMarkedForPrince() && !hasCardType(DomCardType.Duration)) {
            if (shapeshifterCard !=null)
                owner.setAsideForPrince(shapeshifterCard);
            else
                owner.setAsideForPrince(this);
            return;
        }

        if (isTaggedByHerbalist() || isTaggedByScheme()) {
            if (getName()==DomCardName.Hermit && owner.getBoughtCards().isEmpty()) {
                if (owner.isHumanOrPossessedByHuman()) {
                    if (owner.getEngine().getGameFrame().askPlayer("<html>Trash " + DomCardName.Hermit.toHTML() +" ? (Scheme interaction!!)</html>", "Resolving " + this.getName().toString()))
                        if (shapeshifterCard!=null)
                          owner.trash(shapeshifterCard);
                        else
                            if (estateCard!=null)
                              owner.trash(estateCard);
                            else
                              owner.trash(this);
                    else
                        if (shapeshifterCard!=null)
                          owner.putOnTopOfDeck(shapeshifterCard);
                        else
                          if (estateCard!=null)
                            owner.putOnTopOfDeck(estateCard);
                          else
                            owner.putOnTopOfDeck(this);
                } else {
                    if (shapeshifterCard!=null)
                        owner.putOnTopOfDeck(shapeshifterCard);
                    else
                      if (estateCard!=null)
                          owner.putOnTopOfDeck(estateCard);
                      else
                          owner.putOnTopOfDeck(this);
                }
                owner.gain(DomCardName.Madman);
            } else {
                if (shapeshifterCard != null)
                    owner.putOnTopOfDeck(shapeshifterCard);
                else
                    if (estateCard!=null)
                      owner.putOnTopOfDeck(estateCard);
                    else
                      owner.putOnTopOfDeck(this);
            }
            //reset this boolean
            isTaggedByHerbalist = false;
            isTaggedByScheme = false;
            return;
        }
        owner.getDeck().discard(this);
    }

    public int getPotionCost() {
      return name.getPotionCost();
    }

    public int getDebtCost() {
        return name.getDebtCost();
    }

    /**
     * @return
     */
    public DomCost getPotentialCurrencyValue() {
      return new DomCost( (int) getPotentialCoinValue(), getPotionValue());
    }

	public void setFromBlackMarket(boolean b) {
		isFromBlackMarket=b;
	}

	public boolean isFromBlackMarket() {
		return isFromBlackMarket;
	}

	public void addHerbalistTag() {
		isTaggedByHerbalist=true;
	}

	public boolean isTaggedByHerbalist() {
		return isTaggedByHerbalist;
	}

	public  DomCost getCost(DomGame aDomGame) {
		return name.getCost(aDomGame);
	}

	public boolean isBane() {
		return isBane;
	}

	public void setAsBane() {
		isBane=true;
	}
	
	public void doWhenGained() {}

	public void doWhenDiscarded() {}

	public boolean isTaggedByScheme() {
		return isTaggedByScheme;
	}

	public void addSchemeTag() {
		isTaggedByScheme=true;
	}

	public boolean discardAtCleanUp() {
		return discardAtCleanUp; 
	}

	public void setDiscardAtCleanup(boolean b) {
	  discardAtCleanUp=b;
	}

    public void doWhenTrashed() {}

    public void markForPrince() {
        markedForPrince = true;
    }

    public void setMarkedForPrince(boolean markedForPrince) {
        this.markedForPrince = markedForPrince;
    }

    public boolean isMarkedForPrince() {
        return markedForPrince;
    }

    public void doWhenCalled() {}

    public void setShapeshifterCard(DomCard shapeshifterCard) {
        this.shapeshifterCard = shapeshifterCard;
    }

    public DomCard getShapeshifterCard() {
        return shapeshifterCard;
    }

    public void setEstateCard(EstateCard estateCard) {
        this.estateCard = estateCard;
    }

    public EstateCard getEstateCard() {
        return estateCard;
    }

    public void cleanVariablesFromPreviousGames() {}

    public boolean mustStayInPlay() {
        return false;
    }

    public boolean wantsToBeMultiplied() {
        return true;
    }

    public DomCardName isFromPile() {
        return name.isFromPile();
    }

    public void continuePlaying(DomCard theCard) {}

    public boolean react() {
        return false;
    }

    public boolean hasReacted() {
        return hasReacted;
    }

    public boolean reactForHuman() {
        return true;
    }

    public void setReacted(boolean reacted) {
        this.hasReacted = reacted;
    }

    public boolean canReact() {
        return true;
    }

    public int countTypes() {
        int theCount = 0;
        for (DomCardType theType : DomCardType.values()) {
            theCount+=theType.isLegal() && hasCardType(theType) ? 1 : 0;
        }
        return theCount;
    }

    public boolean durationFailed() {
        return false;
    }

    public void trigger() {
    }

    public boolean getDiscardAtCleanUp() {
        return discardAtCleanUp;
    }

    public boolean wantsToBeCalled() {
        return true;
    }

    public abstract class nextTurnAbility {

    }
}