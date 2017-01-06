package be.aga.dominionSimulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;

import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomSet;

public class DomBoard extends EnumMap< DomCardName, ArrayList<DomCard> > {

    private ArrayList< DomPlayer > players;
    
    private ArrayList< DomCard > trashPile = new ArrayList< DomCard >();
	private ArrayList<DomCard> blackMarketDeck=new ArrayList<DomCard>();
    private ArrayList<DomCard> prizePile=new ArrayList<DomCard>();
    private EnumMap<DomCardName,ArrayList<DomCard>> separatePiles = new EnumMap<DomCardName, ArrayList<DomCard>>(DomCardName.class);

    private EnumMap< DomCardName, Integer > embargoTokens = new EnumMap<DomCardName, Integer>(DomCardName.class);
    private EnumMap< DomCardName, Integer > gatheringVPTokens = new EnumMap<DomCardName, Integer>(DomCardName.class);
    private HashSet<DomCardName> tradeRouteMat = new HashSet<DomCardName>();
    private HashSet<DomCardName> activeLandmarks = new HashSet<DomCardName>();
    private int gainsNeededToEndGame;
    private EnumMap< DomCardName, Integer > taxTokens = new EnumMap<DomCardName, Integer>(DomCardName.class);
    private EnumMap< DomCardName, Integer > landmarkTokens = new EnumMap<DomCardName, Integer>(DomCardName.class);

    public DomBoard ( Class< DomCardName > aKeyType, ArrayList< DomPlayer > aPlayers ) {
      super( aKeyType );
      players = aPlayers;
    }

    public void resetCommon() {
        gainsNeededToEndGame=0;
        clearLandmarks();
        resetTaxTokens();
        resetGatheringTokens();
        if (get(DomCardName.Tax)!=null)
           putTaxTokensOnAll();
        if (isLandmarkActive(DomCardName.Battlefield))
           addVPTokensTo(DomCardName.Battlefield, (6 * players.size()));
        if (isLandmarkActive(DomCardName.Labyrinth))
           addVPTokensTo(DomCardName.Labyrinth, 6 * players.size());
        if (isLandmarkActive(DomCardName.Arena))
            addVPTokensTo(DomCardName.Arena, 6 * players.size());
        if (isLandmarkActive(DomCardName.Basilica))
            addVPTokensTo(DomCardName.Basilica, 6 * players.size());
        if (isLandmarkActive(DomCardName.Baths))
            addVPTokensTo(DomCardName.Baths, 6 * players.size());
        if (isLandmarkActive(DomCardName.Colonnade))
            addVPTokensTo(DomCardName.Colonnade, 6 * players.size());
        if (isLandmarkActive(DomCardName.Defiled_Shrine))
           putShrineTokensOnActions();
        if (isLandmarkActive(DomCardName.Aqueduct))
            putAqueductTokensOnTreasures();
    }

    private void putShrineTokensOnActions() {
        for (DomCardName theCard : keySet()) {
            if (!theCard.hasCardType(DomCardType.Action) || theCard.hasCardType(DomCardType.Gathering))
                continue;
            addVPon(theCard);
            addVPon(theCard);
        }
    }

    private void putAqueductTokensOnTreasures() {
        for (int i=0;i<8;i++) {
            addVPon(DomCardName.Silver);
            addVPon(DomCardName.Gold);
        }
    }

    public void initialize() {
      addCustomKingdoms();
      addTreasures();
      addVictoriesAndCurses();
      createPrizePile();
      if (get(DomCardName.Black_Market)!=null)
    	createBlackMarketDeck();
      markBaneCards();
      resetCommon();
    }

    public void reset() {
        for (DomCard theCard : trashPile) {
            add(theCard);
        }
        trashPile.clear();
        for (DomPlayer thePlayer : players) {
            ArrayList<DomCard> theCards = thePlayer.collectAllCards();
            if (thePlayer.isEstateTokenPlaced())
                theCards.add(thePlayer.removeEstateToken());
            for (DomCard theCard : theCards) {
                theCard.setMarkedForPrince(false);
                add(theCard);
                if (theCard.owner!=null) {
                    theCard.owner.removePhysicalCard(theCard);
                }
                theCard.setEstateCard(null);
            }
        }
        if (get(DomCardName.Ruins)!=null)
            Collections.shuffle(get(DomCardName.Ruins));
        if (get(DomCardName.Knights)!=null)
            Collections.shuffle(get(DomCardName.Knights));
        if (get(DomCardName.Gladiator)!=null)
            Collections.sort(get(DomCardName.Gladiator), DomCard.SORT_BY_COST);
        if (get(DomCardName.Settlers)!=null)
            Collections.sort(get(DomCardName.Settlers), DomCard.SORT_BY_COST);
        if (get(DomCardName.Catapult)!=null)
            Collections.sort(get(DomCardName.Catapult), DomCard.SORT_BY_COST);
        if (get(DomCardName.Patrician)!=null)
            Collections.sort(get(DomCardName.Patrician), DomCard.SORT_BY_COST);
        if (get(DomCardName.Encampment)!=null)
            Collections.sort(get(DomCardName.Encampment), DomCard.SORT_BY_COST);
        if (get(DomCardName.Castles)!=null)
            Collections.sort(get(DomCardName.Castles),DomCard.SORT_BY_COST);
        if (get(DomCardName.Sauna)!=null)
            Collections.sort(get(DomCardName.Sauna), DomCard.SORT_BY_COST);

        embargoTokens=new EnumMap<DomCardName, Integer>(DomCardName.class);
        gatheringVPTokens=new EnumMap<DomCardName, Integer>(DomCardName.class);

        for (DomCardName theCardName : keySet()) {
            for(DomCard theCard : get(theCardName))
                theCard.cleanVariablesFromPreviousGames();
        }

        if (separatePiles.get(DomCardName.Disciple)!=null)
            for (DomCard theCard : separatePiles.get(DomCardName.Disciple))
               theCard.cleanVariablesFromPreviousGames();

        Collections.shuffle(blackMarketDeck);

        for(DomCard theCard : blackMarketDeck)
            theCard.cleanVariablesFromPreviousGames();

        clearTradeRouteMat();
        resetCommon();
    }

    private void resetTaxTokens() {
        taxTokens = new EnumMap<DomCardName, Integer>(DomCardName.class);
    }

    private void putTaxTokensOnAll() {
        for (DomCardName theCard : keySet()) {
            putTaxOn(theCard,1);
        }
    }

    private void clearLandmarks() {
        for (DomCardName theKey : landmarkTokens.keySet()) {
            landmarkTokens.put(theKey, 0);
        }
    }

    private void resetGatheringTokens() {
        for (DomCardName theKey : gatheringVPTokens.keySet()) {
            gatheringVPTokens.put(theKey, 0);
        }
    }

    private void createPrizePile() {
		for (DomCardName cardName : DomCardName.values()){
			if (cardName.hasCardType(DomCardType.Prize)){
				prizePile.add(cardName.createNewCardInstance());
			}
		}
	}

	private void markBaneCards() {
		for (DomPlayer domPlayer : players){
		  for (DomBuyRule buyRule : domPlayer.getBuyRules())
			if (buyRule.getBane()!=null && get(buyRule.getBane())!=null){
  			  for (DomCard card : get(buyRule.getBane())){
				card.setAsBane();
			  }
			}
		}
	}

	public void markAsBane(DomCardName aBane) {
  	  for (DomCard card : get(aBane)){
	    card.setAsBane();
	  }
	}

	private void createBlackMarketDeck() {
    	blackMarketDeck = new ArrayList<DomCard>();
    	for (DomCardName theCardName : DomCardName.values()) {
    	  if (get(theCardName)==null) {
		    if ( theCardName!=DomCardName.Treasure_Map
		    && theCardName.hasCardType(DomCardType.Kingdom)
		    && (theCardName.getPotionCost()==0 || get(DomCardName.Potion)!=null)){
    		  DomCard theCard = theCardName.createNewCardInstance();
    		  theCard.setFromBlackMarket(true);
    		  blackMarketDeck.add(theCard);
   		    }
    	  }
    	}
    	Collections.shuffle(blackMarketDeck);
	}

    private void addCustomKingdoms() {
        for (DomPlayer thePlayer : players) {
          for (DomCardName theCard : thePlayer.getCardsNeededInSupply()){
            switch (theCard) {
                case Knights:
                    addKnightsPile();
                    break;
                case Castles:
                    addCastlesPile(players.size());
                    break;
                case Fortune:
                case Gladiator:
                    addGladiatorPile();
                    break;
                case Settlers:
                case Bustling_Village:
                    addSettlersPile();
                    break;
                case Catapult:
                case Rocks:
                    addCatapultPile();
                    break;
                case Patrician:
                case Emporium:
                    addPatricianPile();
                case Encampment:
                case Plunder:
                    addEncampmentPile();
                case Sauna:
                case Avanto:
                    addSaunaPile();
                default:
                    addCardPile(theCard);
                    break;
            }
            if (theCard.hasCardType(DomCardType.Looter)){
                addRuinsPile();
            }

            if (theCard==DomCardName.Marauder || theCard==DomCardName.Bandit_Camp || theCard==DomCardName.Pillage)
                addSeparatePile(DomCardName.Spoils,15);

            if (theCard==DomCardName.Urchin)
                addSeparatePile(DomCardName.Mercenary, 10);
            if (theCard==DomCardName.Hermit)
                addSeparatePile(DomCardName.Madman, 10);
            if (theCard==DomCardName.Page) {
                addSeparatePile(DomCardName.Treasure_Hunter,5);
                addSeparatePile(DomCardName.Warrior,5);
                addSeparatePile(DomCardName.Hero,5);
                addSeparatePile(DomCardName.Champion,5);
            }
            if (theCard==DomCardName.Peasant) {
                addSeparatePile(DomCardName.Soldier,5);
                addSeparatePile(DomCardName.Fugitive,5);
                addSeparatePile(DomCardName.Disciple,5);
                addSeparatePile(DomCardName.Teacher,5);
            }
          }
          for (DomCardName cardName : thePlayer.getSuggestedBoard()){
                if (cardName.hasCardType(DomCardType.Landmark)) {
                    addLandmark(cardName);
                }
                addCardPile(cardName);
                if (thePlayer.getBaneCard()!=null) {
                    addCardPile(thePlayer.getBaneCard());
                    markAsBane(thePlayer.getBaneCard());
                }
            }
        }
    }

    private void addSaunaPile() {
        put(DomCardName.Sauna, new ArrayList<DomCard>());
        get(DomCardName.Sauna).add(DomCardName.Sauna.createNewCardInstance());
        get(DomCardName.Sauna).add(DomCardName.Sauna.createNewCardInstance());
        get(DomCardName.Sauna).add(DomCardName.Sauna.createNewCardInstance());
        get(DomCardName.Sauna).add(DomCardName.Sauna.createNewCardInstance());
        get(DomCardName.Sauna).add(DomCardName.Sauna.createNewCardInstance());
        get(DomCardName.Sauna).add(DomCardName.Avanto.createNewCardInstance());
        get(DomCardName.Sauna).add(DomCardName.Avanto.createNewCardInstance());
        get(DomCardName.Sauna).add(DomCardName.Avanto.createNewCardInstance());
        get(DomCardName.Sauna).add(DomCardName.Avanto.createNewCardInstance());
        get(DomCardName.Sauna).add(DomCardName.Avanto.createNewCardInstance());
    }

    private void addCatapultPile() {
        put(DomCardName.Catapult, new ArrayList<DomCard>());
        get(DomCardName.Catapult).add(DomCardName.Catapult.createNewCardInstance());
        get(DomCardName.Catapult).add(DomCardName.Catapult.createNewCardInstance());
        get(DomCardName.Catapult).add(DomCardName.Catapult.createNewCardInstance());
        get(DomCardName.Catapult).add(DomCardName.Catapult.createNewCardInstance());
        get(DomCardName.Catapult).add(DomCardName.Catapult.createNewCardInstance());
        get(DomCardName.Catapult).add(DomCardName.Rocks.createNewCardInstance());
        get(DomCardName.Catapult).add(DomCardName.Rocks.createNewCardInstance());
        get(DomCardName.Catapult).add(DomCardName.Rocks.createNewCardInstance());
        get(DomCardName.Catapult).add(DomCardName.Rocks.createNewCardInstance());
        get(DomCardName.Catapult).add(DomCardName.Rocks.createNewCardInstance());
    }

    private void addPatricianPile() {
        put(DomCardName.Patrician, new ArrayList<DomCard>());
        get(DomCardName.Patrician).add(DomCardName.Patrician.createNewCardInstance());
        get(DomCardName.Patrician).add(DomCardName.Patrician.createNewCardInstance());
        get(DomCardName.Patrician).add(DomCardName.Patrician.createNewCardInstance());
        get(DomCardName.Patrician).add(DomCardName.Patrician.createNewCardInstance());
        get(DomCardName.Patrician).add(DomCardName.Patrician.createNewCardInstance());
        get(DomCardName.Patrician).add(DomCardName.Emporium.createNewCardInstance());
        get(DomCardName.Patrician).add(DomCardName.Emporium.createNewCardInstance());
        get(DomCardName.Patrician).add(DomCardName.Emporium.createNewCardInstance());
        get(DomCardName.Patrician).add(DomCardName.Emporium.createNewCardInstance());
        get(DomCardName.Patrician).add(DomCardName.Emporium.createNewCardInstance());
    }

    private void addSettlersPile() {
        put(DomCardName.Settlers, new ArrayList<DomCard>());
        get(DomCardName.Settlers).add(DomCardName.Settlers.createNewCardInstance());
        get(DomCardName.Settlers).add(DomCardName.Settlers.createNewCardInstance());
        get(DomCardName.Settlers).add(DomCardName.Settlers.createNewCardInstance());
        get(DomCardName.Settlers).add(DomCardName.Settlers.createNewCardInstance());
        get(DomCardName.Settlers).add(DomCardName.Settlers.createNewCardInstance());
        get(DomCardName.Settlers).add(DomCardName.Bustling_Village.createNewCardInstance());
        get(DomCardName.Settlers).add(DomCardName.Bustling_Village.createNewCardInstance());
        get(DomCardName.Settlers).add(DomCardName.Bustling_Village.createNewCardInstance());
        get(DomCardName.Settlers).add(DomCardName.Bustling_Village.createNewCardInstance());
        get(DomCardName.Settlers).add(DomCardName.Bustling_Village.createNewCardInstance());
    }

    private void addCastlesPile(int playerCount) {
        put(DomCardName.Castles, new ArrayList<DomCard>());
        get(DomCardName.Castles).add(DomCardName.Humble_Castle.createNewCardInstance());
        get(DomCardName.Castles).add(DomCardName.Crumbling_Castle.createNewCardInstance());
        get(DomCardName.Castles).add(DomCardName.Small_Castle.createNewCardInstance());
        get(DomCardName.Castles).add(DomCardName.Haunted_Castle.createNewCardInstance());
        get(DomCardName.Castles).add(DomCardName.Opulent_Castle.createNewCardInstance());
        get(DomCardName.Castles).add(DomCardName.Sprawling_Castle.createNewCardInstance());
        get(DomCardName.Castles).add(DomCardName.Grand_Castle.createNewCardInstance());
        get(DomCardName.Castles).add(DomCardName.King$s_Castle.createNewCardInstance());

        if (playerCount>2) {
            get(DomCardName.Castles).add(DomCardName.Humble_Castle.createNewCardInstance());
            get(DomCardName.Castles).add(DomCardName.Small_Castle.createNewCardInstance());
            get(DomCardName.Castles).add(DomCardName.Opulent_Castle.createNewCardInstance());
            get(DomCardName.Castles).add(DomCardName.King$s_Castle.createNewCardInstance());
        }

        Collections.sort(get(DomCardName.Castles),DomCard.SORT_BY_COST);
    }

    private void addKnightsPile() {
        put( DomCardName.Knights, new ArrayList< DomCard >() );
        get(DomCardName.Knights).add(DomCardName.Dame_Josephine.createNewCardInstance());
        get(DomCardName.Knights).add(DomCardName.Dame_Anna.createNewCardInstance());
        get(DomCardName.Knights).add(DomCardName.Dame_Molly.createNewCardInstance());
        get(DomCardName.Knights).add(DomCardName.Dame_Sylvia.createNewCardInstance());
        get(DomCardName.Knights).add(DomCardName.Dame_Natalie.createNewCardInstance());
        get(DomCardName.Knights).add(DomCardName.Sir_Vander.createNewCardInstance());
        get(DomCardName.Knights).add(DomCardName.Sir_Michael.createNewCardInstance());
        get(DomCardName.Knights).add(DomCardName.Sir_Martin.createNewCardInstance());
        get(DomCardName.Knights).add(DomCardName.Sir_Destry.createNewCardInstance());
        get(DomCardName.Knights).add(DomCardName.Sir_Bailey.createNewCardInstance());
        Collections.shuffle(get(DomCardName.Knights));
    }

    private void addGladiatorPile() {
        put(DomCardName.Gladiator, new ArrayList<DomCard>());
        get(DomCardName.Gladiator).add(DomCardName.Gladiator.createNewCardInstance());
        get(DomCardName.Gladiator).add(DomCardName.Gladiator.createNewCardInstance());
        get(DomCardName.Gladiator).add(DomCardName.Gladiator.createNewCardInstance());
        get(DomCardName.Gladiator).add(DomCardName.Gladiator.createNewCardInstance());
        get(DomCardName.Gladiator).add(DomCardName.Gladiator.createNewCardInstance());
        get(DomCardName.Gladiator).add(DomCardName.Fortune.createNewCardInstance());
        get(DomCardName.Gladiator).add(DomCardName.Fortune.createNewCardInstance());
        get(DomCardName.Gladiator).add(DomCardName.Fortune.createNewCardInstance());
        get(DomCardName.Gladiator).add(DomCardName.Fortune.createNewCardInstance());
        get(DomCardName.Gladiator).add(DomCardName.Fortune.createNewCardInstance());
    }

    private void addEncampmentPile() {
        put(DomCardName.Encampment, new ArrayList<DomCard>());
        get(DomCardName.Encampment).add(DomCardName.Encampment.createNewCardInstance());
        get(DomCardName.Encampment).add(DomCardName.Encampment.createNewCardInstance());
        get(DomCardName.Encampment).add(DomCardName.Encampment.createNewCardInstance());
        get(DomCardName.Encampment).add(DomCardName.Encampment.createNewCardInstance());
        get(DomCardName.Encampment).add(DomCardName.Encampment.createNewCardInstance());
        get(DomCardName.Encampment).add(DomCardName.Plunder.createNewCardInstance());
        get(DomCardName.Encampment).add(DomCardName.Plunder.createNewCardInstance());
        get(DomCardName.Encampment).add(DomCardName.Plunder.createNewCardInstance());
        get(DomCardName.Encampment).add(DomCardName.Plunder.createNewCardInstance());
        get(DomCardName.Encampment).add(DomCardName.Plunder.createNewCardInstance());
    }

    private void addSeparatePile(DomCardName aCardName, int size) {
        ArrayList<DomCard> theCards = new ArrayList<DomCard>();
        for (int i=0;i<size;i++) {
            theCards.add(aCardName.createNewCardInstance());
        }
        separatePiles.put(aCardName, theCards);
    }

    private void addRuinsPile() {
        put( DomCardName.Ruins, new ArrayList< DomCard >() );
        for (int i=0;i<2;i++) {
            get(DomCardName.Ruins).add(DomCardName.Abandoned_Mine.createNewCardInstance());
            get(DomCardName.Ruins).add(DomCardName.Survivors.createNewCardInstance());
            get(DomCardName.Ruins).add(DomCardName.Ruined_Market.createNewCardInstance());
            get(DomCardName.Ruins).add(DomCardName.Ruined_Village.createNewCardInstance());
            get(DomCardName.Ruins).add(DomCardName.Ruined_Library.createNewCardInstance());
        }
        Collections.shuffle(get(DomCardName.Ruins));
    }

    public void addCardPile( DomCardName aCardName ) {
        if (aCardName.hasCardType(DomCardType.Shelter)) {
            if (separatePiles.get(aCardName)==null){
                separatePiles.put(aCardName, new ArrayList<DomCard>());
            }
            separatePiles.get(aCardName).add(aCardName.createNewCardInstance());
            return;
        }

        if (get(aCardName)!=null)
            return;
        put( aCardName, new ArrayList< DomCard >() );
        int theNumber = 10;

        if (aCardName.hasCardType(DomCardType.Event))
            theNumber=1;
        if (aCardName.hasCardType(DomCardType.Victory)) {
          theNumber = players.size()<3 ? 8 : 12;
        }
        if (aCardName.hasCardType(DomCardType.Shelter)){
            theNumber=players.size();
        }
        switch (aCardName) {
		case Estate:
            boolean shelters = false;
            if (players.get(0).getStartState()!=null) {
                for (DomCardName theCard : players.get(0).getStartState().getDrawDeck()) {
                    if (theCard.hasCardType(DomCardType.Shelter))
                        shelters = true;
                }
            }
			if (!shelters)
                theNumber+=players.size()*3;
			break;
		case Curse:
			theNumber=players.size()==1 ? 10 : (players.size()-1)*10;			
			break;
		case Copper:
			theNumber=60;			
			break;
		case Silver:
			theNumber=40;			
			break;
		case Gold:
			theNumber=30;			
			break;
		case Platinum:
        case Port:
			theNumber=12;			
			break;
        case Rats:
            theNumber=20;
            break;
		default:
			break;
		}
        for (int j =0;j<theNumber;j++) {
          get(aCardName).add( aCardName.createNewCardInstance());
        }
    }

    private void addVictoriesAndCurses() {
      addCardPile(DomCardName.Estate);
      addCardPile(DomCardName.Duchy);
      addCardPile(DomCardName.Province);
      addCardPile(DomCardName.Curse);
    }

    /**
     * 
     */
    private void addTreasures() {
    	addCardPile(DomCardName.Copper);
    	addCardPile(DomCardName.Silver);
    	addCardPile(DomCardName.Gold);
        if (get(DomCardName.Colony)!=null) {
          addCardPile(DomCardName.Platinum);
        }
    }
    
    public DomCard take( DomCardName aCardName ) {
        if (aCardName.hasCardType(DomCardType.Prize)){
    		for (DomCard thePrize : prizePile){
    			if (thePrize.getName()==aCardName)
    				return prizePile.remove(prizePile.indexOf(thePrize));
    		}
    	}
        if (separatePiles.containsKey(aCardName)){
            if (separatePiles.get(aCardName).isEmpty())
                return null;
            return separatePiles.get(aCardName).remove(0);
        }
        ArrayList< DomCard > theList = get(aCardName);
        if (aCardName==DomCardName.Fortune)
            theList = get(DomCardName.Gladiator);
        if (aCardName==DomCardName.Bustling_Village)
            theList = get(DomCardName.Settlers);
        if (aCardName==DomCardName.Rocks)
            theList = get(DomCardName.Catapult);
        if (aCardName==DomCardName.Emporium)
            theList = get(DomCardName.Patrician);
        if (aCardName==DomCardName.Plunder)
            theList = get(DomCardName.Encampment);
        if (aCardName==DomCardName.Avanto)
            theList = get(DomCardName.Sauna);

        if (theList==null || theList.isEmpty())
        	return null;
    	if (aCardName.hasCardType(DomCardType.Victory)){
    	  tradeRouteMat.add(aCardName);
    	}
    	//reset this variable (to speed up the getgainsneeded-method)
    	gainsNeededToEndGame=0;
        if (aCardName==DomCardName.Gladiator
                || aCardName== DomCardName.Fortune
                || aCardName == DomCardName.Settlers
                || aCardName == DomCardName.Bustling_Village
                || aCardName == DomCardName.Catapult
                || aCardName == DomCardName.Rocks
                || aCardName == DomCardName.Patrician
                || aCardName == DomCardName.Emporium
                || aCardName == DomCardName.Encampment
                || aCardName == DomCardName.Plunder
                ) {
            if (theList.get(0).getName()!=aCardName)
                return null;
        }
        return theList.isEmpty() ? null : theList.remove( 0 );
    }

    public int countEmptyPiles() {
        int theEmptyPiles=0;
        for (DomCardName theCardName : keySet()) {
            theEmptyPiles+=get(theCardName).size()==0 ? 1 : 0 ;
        }
        return theEmptyPiles;
    }

    public int count( DomCardName aCardName ) {
        DomCardName theCardName = aCardName;
        if (separatePiles.get(aCardName)!=null)
            return separatePiles.get(aCardName).size();
        if (aCardName==DomCardName.Fortune)
          aCardName=DomCardName.Gladiator;
        if (aCardName==DomCardName.Bustling_Village)
          aCardName=DomCardName.Settlers;
        if (aCardName==DomCardName.Rocks)
          aCardName=DomCardName.Catapult;
        if (aCardName==DomCardName.Emporium)
          aCardName=DomCardName.Patrician;
        if (aCardName==DomCardName.Plunder)
            aCardName=DomCardName.Encampment;
        if (aCardName==DomCardName.Avanto)
            aCardName=DomCardName.Sauna;
        ArrayList<DomCard> theList = get(aCardName);
        if (theList==null || theList.isEmpty())
            return 0;
        if (theList.get(0).getName()!=theCardName && aCardName.hasCardType(DomCardType.Split_Pile) )
            return 0;
        return theList.size();
    }

    public void add( DomCard aCard ) {
       if (aCard.isFromBlackMarket()) {
           blackMarketDeck.add(aCard);
           return;
       }
       if (aCard.hasCardType(DomCardType.Prize)) {
           prizePile.add(aCard);
           return;
       }
       if (separatePiles.containsKey(aCard.getName())) {
           if (separatePiles.get(aCard.getName())==null)
             separatePiles.put(aCard.getName(), new ArrayList<DomCard>());
           separatePiles.get(aCard.getName()).add(aCard);
           return;
       }
       if (aCard.hasCardType(DomCardType.Ruins)) {
           get(DomCardName.Ruins).add(0,aCard);
           return;
       }
       if (aCard.hasCardType(DomCardType.Knight)) {
           get(DomCardName.Knights).add(0,aCard);
           return;
       }
       if (aCard.hasCardType(DomCardType.Castle)) {
           get(DomCardName.Castles).add(0,aCard);
           return;
       }
       if (aCard.getName()==DomCardName.Fortune) {
           get(DomCardName.Gladiator).add(0,aCard);
           return;
       }
       if (aCard.getName()==DomCardName.Bustling_Village) {
           get(DomCardName.Settlers).add(0,aCard);
           return;
       }
       if (aCard.getName()==DomCardName.Rocks) {
           get(DomCardName.Catapult).add(0,aCard);
           return;
       }
       if (aCard.getName()==DomCardName.Emporium) {
           get(DomCardName.Patrician).add(0,aCard);
           return;
       }
       if (aCard.getName()==DomCardName.Plunder) {
           get(DomCardName.Encampment).add(0,aCard);
           return;
       }
        if (aCard.getName()==DomCardName.Avanto) {
            get(DomCardName.Sauna).add(0,aCard);
            return;
        }

       if (get(aCard.getName())==null) {
         put(aCard.getName(), new ArrayList< DomCard >());
       }
       get(aCard.getName()).add( 0, aCard );
    }

    public void addToTrash( DomCard aRemove ) {
      trashPile.add( aRemove );
    }

    public ArrayList< String > getEmptyPiles() {
        ArrayList< String > theList = new ArrayList< String >();
        for (DomCardName theCardName : keySet()) {
          if (get(theCardName).size()== 0){
              theList.add( theCardName.toHTML() );
          }
        }
        return theList;
    }

    public ArrayList< DomCard > getTrashedCards() {
      return trashPile;
    }

    public void clearTradeRouteMat() {
		tradeRouteMat.clear();
	}

    public DomCard removeFromTrash( DomCard aCard ) {
      return trashPile.remove( trashPile.indexOf( aCard ) );
    }

	public ArrayList<DomCard> revealFromBlackMarketDeck() {
		ArrayList<DomCard> theCards = new ArrayList<DomCard>();
		for (int i=0;i<3 && !blackMarketDeck.isEmpty(); i++) {
	      theCards.add(blackMarketDeck.remove(0));
		}
		return theCards;
	}

	public void returnToBlackMarketDeck(DomCard theCard) {
		blackMarketDeck.add(blackMarketDeck.size(), theCard);
	}

	public int getEmbargoTokensOn(DomCardName aCard) {
		if (embargoTokens.get(aCard)==null)
		  return 0;
		return embargoTokens.get(aCard);
	}

	public void putEmbargoTokenOn(DomCardName aCard) {
		Integer theTokens = embargoTokens.get(aCard);
		if (theTokens==null) {
		  embargoTokens.put(aCard, 1);
		} else {
		  embargoTokens.put(aCard, theTokens+1);
		}
	}

	public DomCardName getRandomCardWithEmbargoToken() {
		if (embargoTokens.keySet().isEmpty())
			return DomCardName.Curse;
		ArrayList<DomCardName> theCards = new ArrayList<DomCardName>(Arrays.asList(embargoTokens.keySet().toArray(new DomCardName[0])));
		Collections.shuffle(theCards);
		return theCards.get(0);
	}

	public String getEmbargoInfo() {
		if (embargoTokens.keySet().isEmpty())
			return null;
		String theInfo = "";
		for (DomCardName theCard:embargoTokens.keySet()) {
		  theInfo+=theCard.toHTML()+" ("+embargoTokens.get(theCard).toString()+"), ";
		}
		return theInfo;
	}

	public DomCardName getBestCardInSupplyFor(DomPlayer aPlayer, DomCardType aType, DomCost domCost, boolean anExactCost, DomCardType aForbiddenType) {
		DomCardName theCardToGet = null;
		for (DomCardName theCardName : keySet()){
			if (get(theCardName)!=null
            && (theCardName.hasCardType(DomCardType.Kingdom) || theCardName.hasCardType(DomCardType.Base))
		    && !get(theCardName).isEmpty() 
		    && (aType==null || theCardName.hasCardType(aType))
		    && (aForbiddenType==null || !theCardName.hasCardType(aForbiddenType))
		    && ((!anExactCost && domCost.compareTo(theCardName.getCost(aPlayer.getCurrentGame()))>=0)
		    	|| (anExactCost && domCost.compareTo(theCardName.getCost(aPlayer.getCurrentGame()))==0))
		    && ( theCardToGet==null ||
		      theCardName.getTrashPriority(aPlayer)>theCardToGet.getTrashPriority(aPlayer))
            && !aPlayer.suicideIfBuys(theCardName)) {
				theCardToGet=theCardName;
			}
		}
		return theCardToGet;
	}
	
	public DomCardName getCardForSwindler(DomPlayer aPlayer, DomCost domCost) {
		DomCardName theCardToGet = null;
		for (DomCardName theCardName : keySet()){
            if (!theCardName.hasCardType(DomCardType.Kingdom) && !theCardName.hasCardType(DomCardType.Base))
                continue;
			if (get(theCardName)!=null && !get(theCardName).isEmpty() 
		    && theCardName.getCost(aPlayer.getCurrentGame()).compareTo(domCost)==0
		    && (theCardToGet==null ||
		    	theCardName.getTrashPriority(aPlayer)<theCardToGet.getTrashPriority(aPlayer))) {
			  theCardToGet=theCardName;
			}
		}
		return theCardToGet;
	}

	public int countTradeRouteTokens() {
		return tradeRouteMat.size();
	}

	public boolean isPrizeAvailable(DomCardName cardToBuy) {
		if (cardToBuy==DomCardName.Duchy && count(DomCardName.Duchy)>0)
			return true;
		for (DomCard card : prizePile){
			if (card.getName()==cardToBuy)
				return true;
		}
		return false;
	}

	public double countCardsInSmallestPile() {
		int theSmallest=10;
		for (DomCardName cardName : keySet()){
			if (get(cardName).size()<theSmallest && get(cardName).size()>0)
				theSmallest=get(cardName).size();
		}
		return theSmallest;
	}

	public int getGainsNeededToEndGame() {
		if (gainsNeededToEndGame!=0)
			return gainsNeededToEndGame;
		ArrayList<Integer> theCounts = new ArrayList<Integer>();
		for (DomCardName cardName : keySet()){
			theCounts.add(get(cardName).size());
		}
		Collections.sort(theCounts);
		int theCountGainsToEndGame = theCounts.get(0) + theCounts.get(1) + theCounts.get(2);
		if (get(DomCardName.Colony)!=null && count(DomCardName.Colony)<theCountGainsToEndGame)
			theCountGainsToEndGame=count(DomCardName.Colony);
		if (count(DomCardName.Province)<theCountGainsToEndGame)
			theCountGainsToEndGame=count(DomCardName.Province);
		gainsNeededToEndGame=theCountGainsToEndGame;
		return gainsNeededToEndGame;
	}

	public static ArrayList<DomCardName> getRandomBoard() {
		ArrayList<DomCardName> theCardsToChooseFrom = new ArrayList<DomCardName>();
		for (DomSet set : DomSet.values()){
			if (set!=DomSet.Common && !isExcluded(set)){
				for (DomCardName cardName : set.getCards()){
					if (!isExcluded(cardName)){
						theCardsToChooseFrom.add(cardName);
					}
				}
			}
		}
		Collections.shuffle(theCardsToChooseFrom);
		ArrayList<DomCardName> theChosenCards = new ArrayList<DomCardName>();
		for (int i=0;i<11;i++){
			theChosenCards.add(theCardsToChooseFrom.get(i));
		}
		return theChosenCards;
	}

	private static boolean isExcluded(DomCardName cardName) {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean isExcluded(DomSet set) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String toString() {
		StringBuilder theString= new StringBuilder();
		for (DomCardName cardName : keySet()){
			theString.append(cardName + "[" +get(cardName).size()+"]");
		}
		return theString.toString();
	}

    public int countDifferentTreasuresInTrash() {
        HashSet<DomCardName> theSet = new HashSet<DomCardName>();
        for (DomCard theCard : trashPile) {
            if (theCard.hasCardType(DomCardType.Treasure))
                theSet.add(theCard.getName());
        }
        return theSet.size();
    }

    public boolean isFromSeparatePile(DomCardName theCard) {
        return separatePiles.containsKey(theCard);
    }

    public int countVPon(DomCardName cardName) {
        if (cardName.hasCardType(DomCardType.Landmark)) {
            if (landmarkTokens.get(cardName) == null)
                return 0;
            return landmarkTokens.get(cardName);
        }
        if (gatheringVPTokens.get(cardName)==null)
            return 0;
        return gatheringVPTokens.get(cardName);
    }

    public void resetVPon(DomCardName cardName) {
        gatheringVPTokens.put(cardName,0);
    }

    public void addVPon(DomCardName cardName) {
        if (gatheringVPTokens.get(cardName)==null)
          gatheringVPTokens.put(cardName,0);
        gatheringVPTokens.put(cardName,gatheringVPTokens.get(cardName)+1);
        if (DomEngine.haveToLog) DomEngine.addToLog( cardName + " has " + gatheringVPTokens.get(cardName) + "&#x25BC;" );
    }

    public void addLandmark(DomCardName cardName) {
        activeLandmarks.add(cardName);
    }

    public boolean isLandmarkActive(DomCardName cardName) {
        return activeLandmarks.contains(cardName);
    }

    public int getAllVPFromPile(DomCardName cardName) {
        if (gatheringVPTokens.isEmpty())
            return 0;
        int theTotal = gatheringVPTokens.get(cardName)==null? 0 : gatheringVPTokens.get(cardName);
        resetVPon(cardName);
        return theTotal;
    }

    public void addVPTokensTo(DomCardName aCard, int i) {
        Integer theTokens = landmarkTokens.get(aCard);
        if (theTokens==null) {
            landmarkTokens.put(aCard, i);
        } else {
            landmarkTokens.put(aCard, theTokens+i);
        }
    }

    public int getTaxOn(DomCardName name) {
        if (taxTokens.get(name)==null)
            return 0;
        return taxTokens.get(name);
    }

    public void putTaxOn(DomCardName aCard, int i) {
        Integer theTokens = taxTokens.get(aCard);
        if (theTokens==null) {
            taxTokens.put(aCard, i);
        } else {
            taxTokens.put(aCard, theTokens+i);
        }
    }

    private int removeVPFromGathering(DomCardName aCard, int i) {
        Integer theTokens = gatheringVPTokens.get(aCard);
        if (theTokens==null || theTokens==0) {
            return 0;
        } else {
            gatheringVPTokens.put(aCard, theTokens - i);
            return i;
        }
    }

    public int removeTaxFrom(DomCardName name) {
        Integer theTax = taxTokens.get(name);
        resetTaxOn(name);
        return theTax;
    }

    private void resetTaxOn(DomCardName name) {
        taxTokens.put(name, 0);
    }

    public int removeVPFrom(DomCardName aCard, int i) {
        Integer theTokens = landmarkTokens.get(aCard);
        if (theTokens==null || theTokens==0) {
            return 0;
        } else {
            landmarkTokens.put(aCard, theTokens - i);
            return i;
        }
    }

    public void moveVPFromTo(DomCardName fromCard, DomCardName toCard) {
        int theAmount = removeVPFromGathering(fromCard, 1);
        if (theAmount>0) {
            addVPTokensTo(toCard,1);
            if (DomEngine.haveToLog) DomEngine.addToLog( "1&#x25BC; flows "+fromCard.toHTML()+" to " + toCard.toHTML());
        }
    }
}
