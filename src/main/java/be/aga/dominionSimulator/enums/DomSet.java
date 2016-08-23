package be.aga.dominionSimulator.enums;

import java.util.ArrayList;

public enum DomSet {
   Base (new DomCardName[] {DomCardName.Cellar
           , DomCardName.Chapel
           , DomCardName.Chancellor
           , DomCardName.Moat
           , DomCardName.Village
           , DomCardName.Woodcutter
           , DomCardName.Workshop
           , DomCardName.Bureaucrat
           , DomCardName.Feast
           , DomCardName.Gardens
           , DomCardName.Militia
           , DomCardName.Moneylender
           , DomCardName.Remodel
           , DomCardName.Smithy
           , DomCardName.Spy
           , DomCardName.Thief
           , DomCardName.Throne_Room
           , DomCardName.Council_Room
           , DomCardName.Festival
           , DomCardName.Laboratory
           , DomCardName.Library
           , DomCardName.Market
           , DomCardName.Mine
           , DomCardName.Witch
           , DomCardName.Adventurer}),

   Intrigue (new DomCardName[] {DomCardName.Courtyard
           , DomCardName.Pawn
           , DomCardName.Secret_Chamber
           , DomCardName.Great_Hall
           , DomCardName.Masquerade
           , DomCardName.Shanty_Town
           , DomCardName.Steward
           , DomCardName.Swindler
           , DomCardName.Wishing_Well
           , DomCardName.Baron
           , DomCardName.Bridge
           , DomCardName.Conspirator
           , DomCardName.Coppersmith
           , DomCardName.Ironworks
           , DomCardName.Mining_Village
           , DomCardName.Scout
           , DomCardName.Duke
           , DomCardName.Minion
           , DomCardName.Saboteur
           , DomCardName.Torturer
           , DomCardName.Trading_Post
           , DomCardName.Tribute
           , DomCardName.Upgrade
           , DomCardName.Harem
           , DomCardName.Nobles}),
   
   Seaside (new DomCardName[] {DomCardName.Embargo
           , DomCardName.Haven
           , DomCardName.Lighthouse
           , DomCardName.Native_Village
           , DomCardName.Pearl_Diver
           , DomCardName.Ambassador
           , DomCardName.Fishing_Village
           , DomCardName.Lookout
           , DomCardName.Smugglers
           , DomCardName.Warehouse
           , DomCardName.Caravan
           , DomCardName.Cutpurse
           , DomCardName.Island
           , DomCardName.Navigator
           , DomCardName.Pirate_Ship
           , DomCardName.Salvager
           , DomCardName.Sea_Hag
           , DomCardName.Treasure_Map
           , DomCardName.Bazaar
           , DomCardName.Explorer
           , DomCardName.Ghost_Ship
           , DomCardName.Merchant_Ship
           , DomCardName.Outpost
           , DomCardName.Tactician
           , DomCardName.Treasury
           , DomCardName.Wharf}),
           
   Alchemy (new DomCardName[] {DomCardName.Transmute
           , DomCardName.Vineyard
           , DomCardName.Apothecary
           , DomCardName.Herbalist
           , DomCardName.Scrying_Pool
           , DomCardName.University
           , DomCardName.Alchemist
           , DomCardName.Familiar
           , DomCardName.Philosopher$s_Stone
           , DomCardName.Golem
           , DomCardName.Apprentice
           , DomCardName.Possession}),
   
   Prosperity (new DomCardName[] {DomCardName.Loan
           , DomCardName.Trade_Route
           , DomCardName.Watchtower
           , DomCardName.Bishop
           , DomCardName.Monument
           , DomCardName.Quarry
           , DomCardName.Talisman
           , DomCardName.Worker$s_Village
           , DomCardName.City
           , DomCardName.Contraband
           , DomCardName.Counting_House
           , DomCardName.Mint
           , DomCardName.Mountebank
           , DomCardName.Rabble
           , DomCardName.Royal_Seal
           , DomCardName.Vault
           , DomCardName.Venture
           , DomCardName.Goons
           , DomCardName.Grand_Market
           , DomCardName.Hoard
           , DomCardName.Bank
           , DomCardName.Expand
           , DomCardName.Forge
           , DomCardName.King$s_Court
           , DomCardName.Peddler}),
           
   Cornucopia (new DomCardName[] {DomCardName.Bag_of_Gold
           , DomCardName.Diadem
           , DomCardName.Followers
           , DomCardName.Princess
           , DomCardName.Trusty_Steed
           , DomCardName.Hamlet
           , DomCardName.Fortune_Teller
           , DomCardName.Menagerie
           , DomCardName.Farming_Village
           , DomCardName.Horse_Traders
           , DomCardName.Remake
           , DomCardName.Tournament
           , DomCardName.Young_Witch
           , DomCardName.Harvest
           , DomCardName.Horn_of_Plenty
           , DomCardName.Hunting_Party
           , DomCardName.Jester
           , DomCardName.Fairgrounds
           }),
                   
   Hinterlands (new DomCardName[] {DomCardName.Border_Village
           , DomCardName.Cache
           , DomCardName.Cartographer
           , DomCardName.Crossroads
           , DomCardName.Develop
           , DomCardName.Duchess
           , DomCardName.Embassy
           , DomCardName.Farmland
           , DomCardName.Fool$s_Gold
           , DomCardName.Haggler
           , DomCardName.Highway
           , DomCardName.Ill_Gotten_Gains
           , DomCardName.Inn
           , DomCardName.Jack_of_all_Trades
           , DomCardName.Mandarin
           , DomCardName.Margrave
           , DomCardName.Noble_Brigand
           , DomCardName.Nomad_Camp
           , DomCardName.Oasis
           , DomCardName.Oracle
           , DomCardName.Scheme
           , DomCardName.Silk_Road
           , DomCardName.Spice_Merchant
           , DomCardName.Stables
           , DomCardName.Trader
           , DomCardName.Tunnel
           }),
           
   Promo (new DomCardName[] {DomCardName.Black_Market
           , DomCardName.Envoy
           , DomCardName.Governor
           , DomCardName.Walled_Village
           , DomCardName.Stash}),
               
   Common (new DomCardName[] {DomCardName.Copper
           , DomCardName.Silver
           , DomCardName.Gold
           , DomCardName.Platinum
           , DomCardName.Potion
           , DomCardName.Curse
           , DomCardName.Estate
           , DomCardName.Duchy
           , DomCardName.Province
           , DomCardName.Colony
           }),

   ;
   
   private final ArrayList< DomCardName> cards = new ArrayList< DomCardName>();
   
   DomSet(DomCardName[] aCards) {
     for (DomCardName theCard : aCards) {
       cards.add( theCard);
     }
   }
   
   public boolean contains(DomCardName aCard) {
	 return cards.contains(aCard);
   }

	public  ArrayList<DomCardName> getCards() {
		return cards;
	}
}