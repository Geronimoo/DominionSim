package be.aga.dominionSimulator.enums;

import java.util.ArrayList;

public enum DomSet {
   Base (new DomCardName[] {DomCardName.Cellar
           , DomCardName.Chapel
           , DomCardName.Moat
           , DomCardName.Village
           , DomCardName.Workshop
           , DomCardName.Bureaucrat
           , DomCardName.Gardens
           , DomCardName.Militia
           , DomCardName.Moneylender
           , DomCardName.Remodel
           , DomCardName.Smithy
           , DomCardName.Throne_Room
           , DomCardName.Council_Room
           , DomCardName.Festival
           , DomCardName.Laboratory
           , DomCardName.Library
           , DomCardName.Market
           , DomCardName.Mine
           , DomCardName.Witch
           , DomCardName.Harbinger
           , DomCardName.Merchant
           , DomCardName.Vassal
           , DomCardName.Poacher
           , DomCardName.Bandit
           , DomCardName.Sentry
           , DomCardName.Artisan}),

    OldBase (new DomCardName[] {DomCardName.Chancellor
            , DomCardName.Woodcutter
            , DomCardName.Feast
            , DomCardName.Spy
            , DomCardName.Thief
            , DomCardName.Adventurer}),

    Intrigue (new DomCardName[] {DomCardName.Courtyard
           , DomCardName.Pawn
           , DomCardName.Lurker
           , DomCardName.Diplomat
           , DomCardName.Mill
           , DomCardName.Secret_Passage
           , DomCardName.Courtier
           , DomCardName.Patrol
           , DomCardName.Replace
           , DomCardName.Masquerade
           , DomCardName.Shanty_Town
           , DomCardName.Steward
           , DomCardName.Swindler
           , DomCardName.Wishing_Well
           , DomCardName.Baron
           , DomCardName.Bridge
           , DomCardName.Conspirator
           , DomCardName.Ironworks
           , DomCardName.Mining_Village
           , DomCardName.Duke
           , DomCardName.Minion
           , DomCardName.Torturer
           , DomCardName.Trading_Post
           , DomCardName.Upgrade
           , DomCardName.Harem
           , DomCardName.Nobles}),

    OldIntrigue (new DomCardName[] {
            DomCardName.Secret_Chamber
            , DomCardName.Great_Hall
            , DomCardName.Coppersmith
            , DomCardName.Scout
            , DomCardName.Saboteur
            , DomCardName.Tribute
    }),

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

   Dark_Ages (new DomCardName[] {DomCardName.Poor_House
           , DomCardName.Beggar
           , DomCardName.Squire
           , DomCardName.Vagrant
           , DomCardName.Forager
           , DomCardName.Hermit
           , DomCardName.Market_Square
           , DomCardName.Sage
           , DomCardName.Storeroom
           , DomCardName.Urchin
           , DomCardName.Armory
           , DomCardName.Death_Cart
           , DomCardName.Feodum
           , DomCardName.Fortress
           , DomCardName.Ironmonger
           , DomCardName.Marauder
           , DomCardName.Procession
           , DomCardName.Rats
           , DomCardName.Scavenger
           , DomCardName.Wandering_Minstrel
           , DomCardName.Band_of_Misfits
           , DomCardName.Bandit_Camp
           , DomCardName.Catacombs
           , DomCardName.Count
           , DomCardName.Counterfeit
           , DomCardName.Cultist
           , DomCardName.Graverobber
           , DomCardName.Junk_Dealer
           , DomCardName.Knights
           , DomCardName.Mystic
           , DomCardName.Pillage
           , DomCardName.Rebuild
           , DomCardName.Rogue
           , DomCardName.Altar
           , DomCardName.Hunting_Grounds
           }),

   Guilds (new DomCardName[]{DomCardName.Candlestick_Maker
           , DomCardName.Stonemason
           , DomCardName.Doctor
           , DomCardName.Masterpiece
           , DomCardName.Advisor
           , DomCardName.Plaza
           , DomCardName.Taxman
           , DomCardName.Herald
           , DomCardName.Baker
           , DomCardName.Butcher
           , DomCardName.Journeyman
           , DomCardName.Merchant_Guild
           , DomCardName.Soothsayer
          }),

   Adventures (new DomCardName[]{DomCardName.Coin_of_the_Realm
           , DomCardName.Page
           , DomCardName.Peasant
           , DomCardName.Ratcatcher
           , DomCardName.Raze
           , DomCardName.Amulet
           , DomCardName.Caravan_Guard
           , DomCardName.Dungeon
           , DomCardName.Gear
           , DomCardName.Guide
           , DomCardName.Duplicate
           , DomCardName.Magpie
           , DomCardName.Messenger
           , DomCardName.Miser
           , DomCardName.Port
           , DomCardName.Ranger
           , DomCardName.Transmogrify
           , DomCardName.Artificer
           , DomCardName.Bridge_Troll
           , DomCardName.Distant_Lands
           , DomCardName.Giant
           , DomCardName.Haunted_Woods
           , DomCardName.Lost_City
           , DomCardName.Relic
           , DomCardName.Royal_Carriage
           , DomCardName.Storyteller
           , DomCardName.Swamp_Hag
           , DomCardName.Treasure_Trove
           , DomCardName.Wine_Merchant
           , DomCardName.Hireling
           , DomCardName.Alms
           , DomCardName.Borrow
           , DomCardName.Quest
           , DomCardName.Save
           , DomCardName.Scouting_Party
           , DomCardName.Travelling_Fair
           , DomCardName.Bonfire
           , DomCardName.Expedition
           , DomCardName.Ferry
           , DomCardName.Plan
           , DomCardName.Mission
           , DomCardName.Pilgrimage
           , DomCardName.Ball
           , DomCardName.Raid
           , DomCardName.Seaway
           , DomCardName.Trade
           , DomCardName.Lost_Arts
           , DomCardName.Training
           , DomCardName.Inheritance
           , DomCardName.Pathfinding}),

   Empires (new DomCardName[] {DomCardName.Engineer
           ,DomCardName.City_Quarter
           ,DomCardName.Overlord
           ,DomCardName.Royal_Blacksmith
           ,DomCardName.Encampment
           ,DomCardName.Patrician
           ,DomCardName.Settlers
           ,DomCardName.Castles
           ,DomCardName.Catapult
           ,DomCardName.Chariot_Race
           ,DomCardName.Enchantress
           ,DomCardName.Farmers$_Market
           ,DomCardName.Gladiator
           ,DomCardName.Sacrifice
           ,DomCardName.Temple
           ,DomCardName.Villa
           ,DomCardName.Archive
           ,DomCardName.Capital
           ,DomCardName.Charm
           ,DomCardName.Crown
           ,DomCardName.Forum
           ,DomCardName.Groundskeeper
           ,DomCardName.Legionary
           ,DomCardName.Wild_Hunt
           ,DomCardName.Triumph
           ,DomCardName.Annex
           ,DomCardName.Donate
           ,DomCardName.Advance
           ,DomCardName.Delve
           ,DomCardName.Tax
           ,DomCardName.Banquet
           ,DomCardName.Ritual
           ,DomCardName.Salt_the_Earth
           ,DomCardName.Wedding
           ,DomCardName.Windfall
           ,DomCardName.Conquest
           ,DomCardName.Dominate
           ,DomCardName.Aqueduct
           ,DomCardName.Arena
           ,DomCardName.Bandit_Fort
           ,DomCardName.Basilica
           ,DomCardName.Baths
           ,DomCardName.Battlefield
           ,DomCardName.Colonnade
           ,DomCardName.Defiled_Shrine
           ,DomCardName.Fountain
           ,DomCardName.Keep
           ,DomCardName.Labyrinth
           ,DomCardName.Mountain_Pass
           ,DomCardName.Museum
           ,DomCardName.Obelisk
           ,DomCardName.Orchard
           ,DomCardName.Palace
           ,DomCardName.Tomb
           ,DomCardName.Tower
           ,DomCardName.Triumphal_Arch
           ,DomCardName.Wall
           ,DomCardName.Wolf_Den   }),

    Nocturne (new DomCardName[]{DomCardName.Ghost_Town
            ,DomCardName.Devil$s_Workshop
            ,DomCardName.Raider
            ,DomCardName.Necromancer
            ,DomCardName.Crypt
            ,DomCardName.Shepherd
            ,DomCardName.Pooka}),

    Promo (new DomCardName[] {DomCardName.Black_Market
           , DomCardName.Envoy
           , DomCardName.Governor
           , DomCardName.Walled_Village
           , DomCardName.Stash
           , DomCardName.Summon
           , DomCardName.Sauna
           , DomCardName.Dismantle}),
               
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

    @Override
    public String toString() {
        return super.toString().replaceAll("_","");
    }
}