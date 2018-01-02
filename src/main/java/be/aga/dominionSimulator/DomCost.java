package be.aga.dominionSimulator;

/**
 * Represents a cost to be paid in the game on three dimensions: coins, potions, and debt.
 */
public class DomCost implements Comparable< DomCost >{

    public static final DomCost ZERO = new DomCost( 0, 0 );
    int coins = 0;
    int potions = 0;
    int debt = 0;

    /**
     * Returns the coin cost.
     * @return number of coins in cost
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Sets number of coins in cost.
     * @param aCoins number of coins in cost
     */
    public void setCoins( int aCoins ) {
        coins = aCoins;
    }

    /**
     * Returns the potions cost.
     * @return number of potions in cost
     */
    public int getPotions() {
        return potions;
    }

    /**
     * Sets number of potions in cost.
     * @param aPotions number of potions in cost
     */
    public void setPotions( int aPotions ) {
        potions = aPotions;
    }

    /**
     * Returns the debt cost.
     * @return number of debt in cost
     */
    public int getDebt() {
        return debt;
    }

    /**
     * Sets number of debt in cost.
     * @param aPotions number of debt in cost
     */
    public void setDebt(int debt) {
        this.debt = debt;
    }

    /**
     * Sole constructor.
     * @param aCoinCost number of coins in cost
     * @param aPotionCost number of potions in cost
     */
    public DomCost ( int aCoinCost , int aPotionCost ) {
        coins = aCoinCost;
        potions = aPotionCost;
        debt = 0;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo( DomCost aO ) {
    	//CAREFUL when using this!!
    	//the cost of King's Court for example will be smaller then the cost of Transmute
    	//and the cost of Transmute will also be smaller than the cost of King's Court! 
        if (this.coins<aO.getCoins() || this.potions<aO.getPotions() || this.debt<aO.getDebt() )
           return -1;
        
        if (this.coins==aO.getCoins() && this.potions==aO.getPotions() && this.debt==aO.getDebt())
            return 0;

        return 1;
    }

    /**
     * Returns new {@code DomCost} representing the addition of this cost and {@code aCostToAdd}.
     * @return DomCost that is addition of this and argument
     */
    public DomCost add( DomCost aCostToAdd ) {
      DomCost theNewCost = new DomCost(  coins + aCostToAdd.coins, potions + aCostToAdd.potions );
      theNewCost.setDebt(debt + aCostToAdd.getDebt());
//      if (theNewCost.compareTo(ZERO)<0)
//    	  theNewCost=ZERO;
      return theNewCost; 
    }

	public DomCost subtract(DomCost aCostToSubtract) {
	      DomCost theNewCost = new DomCost(  coins - aCostToSubtract.coins, potions - aCostToSubtract.potions );
          theNewCost.setDebt(debt - aCostToSubtract.getDebt());
//	      if (theNewCost.compareTo(ZERO)<0)
//	    	  theNewCost=ZERO;
	      return theNewCost; 
	}

    @Override
    public String toString() {
        return "$"+getCoins()+ (getPotions()>0 ? "P" : "");
    }

    public int compareButIgnoreDebtTo(DomCost aO) {
        if (this.coins<aO.getCoins() || this.potions<aO.getPotions() )
            return -1;

        if (this.coins==aO.getCoins() && this.potions==aO.getPotions() )
            return 0;

        return 1;
    }
}