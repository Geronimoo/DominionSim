package be.aga.dominionSimulator.enums;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomGame;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.cards.*;
import be.aga.dominionSimulator.gui.EscapeDialog;

public enum DomCardName  {
    //common cards
    Curse (0, 0, 0, -1, 100, 10, new DomCardType[]{DomCardType.Curse,DomCardType.Base, DomCardType.Junk}),
    Copper (0, 0, 1, 0, 55, 15, new DomCardType[]{DomCardType.Treasure,DomCardType.Base, DomCardType.Junk}),
    Silver (3, 0, 2, 0, 25, 20, new DomCardType[]{DomCardType.Treasure,DomCardType.Base}),
    Gold (6, 0, 3, 0, 30, 24, new DomCardType[]{DomCardType.Treasure,DomCardType.Base}),
    Platinum (9, 0, 5, 0, 23, 33, new DomCardType[]{DomCardType.Treasure,DomCardType.Base}),
    Potion (4, 0, 0, 0, 40, 22, new DomCardType[]{DomCardType.Treasure,DomCardType.Base}),
    Estate (2, 0, 0, 1, 100, 9, new DomCardType[]{DomCardType.Victory,DomCardType.Base, DomCardType.Junk}),
    Duchy (5, 0, 0, 3, 100, 8, new DomCardType[]{DomCardType.Victory,DomCardType.Base}),
    Province (8, 0, 0, 6, 100, 7, new DomCardType[]{DomCardType.Victory,DomCardType.Base}),
    Colony (11, 0, 0, 10, 100, 6, new DomCardType[]{DomCardType.Victory,DomCardType.Base}),

    //kingdom cards
    Abandoned_Mine (0, 0, 0, 0, 58, 13, new DomCardType[]{DomCardType.Action, DomCardType.Ruins, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Junk}) {},
    Abundance (4, 0, 0, 0, 20, 21, new DomCardType[]{DomCardType.Treasure, DomCardType.Kingdom, DomCardType.Duration}),
    Academy (5, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Project}),
    Acting_Troupe (3, 0, 0, 0, 6, 21, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Village}),
    Advance (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Adventurer (6, 0, 0, 0, 22, 40, new DomCardType[]{DomCardType.Action , DomCardType.Kingdom, DomCardType.Terminal}) {},
    Advisor (4, 0, 0, 0, 7, 30, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Card_Advantage}),
    Alchemist (3, 1, 0, 0, 9, 40 , new DomCardType[]{DomCardType.Action , DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Card_Advantage}),
    Alley (4, 0, 0, 0, 5, 16, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Shadow, DomCardType.Cycler}),
    Alliance (10, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Alms (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Altar (6, 0, 0, 0, 27, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher}),
    Amass (2, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Ambassador (3, 0, 0, 0, 35, 18, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher}),
    Amphora (7, 0, 3, 0, 20, 17, new DomCardType[]{DomCardType.Treasure, DomCardType.Loot}),
    Amulet (3, 0, 1, 0, 25, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Duration, DomCardType.Terminal, DomCardType.Trasher}),
    Animal_Fair (7, 0, 4, 0, 19, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Annex (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Anvil (3, 0, 1, 0, 1, 18, new DomCardType[]{DomCardType.Treasure, DomCardType.Kingdom}),
    Apothecary (2, 1, 0, 0, 19, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Card_Advantage}),
    Apprentice (5, 0, 0, 0, 20, 19, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Trasher, DomCardType.Cycler, DomCardType.Card_Advantage, DomCardType.TrashForBenefit}),
    Aqueduct (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Landmark}),
    Archive (5, 0, 0, 0, 9, 29, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Duration, DomCardType.Cycler, DomCardType.Card_Advantage}),
    Arena (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Landmark}),
    Aristocrat (3, 0, 0, 0, 23, 30, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom}),
    Armory (4, 0, 0, 0, 38, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Artificer (5, 0, 1, 0, 12, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Artisan (6, 0, 0, 0, 30, 27, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Astrolabe (3, 0, 1, 0, 10, 17, new DomCardType[]{DomCardType.Treasure, DomCardType.Kingdom, DomCardType.Duration}),
    Avanto (5, 0, 0, 0, 25, 24, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage, DomCardType.Split_Pile, DomCardType.Split_Pile_Bottom}),
    Avoid (2, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Bad_Omens (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Hex}),
    Bag_of_Gold (0, 0, 0, 0, 7, 25, new DomCardType[]{DomCardType.Action, DomCardType.Prize}),
    Baker (5, 0, 1, 0, 12, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Ball (5, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Band_of_Misfits (5, 0, 0, 0, 12, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom}),
    Bandit (5, 0, 0, 0, 23, 23, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal}),
    Bandit_Camp (5, 0, 0, 0, 5, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Village}),
    Bandit_Fort (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Landmark}),
    Banish (4, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Bank (7, 0, 0, 0, 1000, 30, new DomCardType[]{DomCardType.Treasure, DomCardType.Kingdom}),
    Banquet (3, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Barbarian (5, 0, 2, 0, 20, 23, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal}),
    Bard (4, 0, 2, 0, 28, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Fate}),
    Bargain (4, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Barge (5, 0, 0, 0, 19, 32, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Duration, DomCardType.Card_Advantage}),
    Baron (4, 0, 4, 0, 22, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Barracks (6, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Project}),
    Basilica (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Landmark}),
    Bat (2, 0, 0, 0, 8, 22, new DomCardType[]{DomCardType.Trasher,DomCardType.Night}),
    Baths (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Landmark}),
    Battlefield (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Landmark}),
    Bauble (2, 0, 1, 0, 10, 17, new DomCardType[]{DomCardType.Treasure, DomCardType.Kingdom, DomCardType.Liaison}),
    Bazaar (5, 0, 1, 0, 5, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Village}),
    Beggar (2, 0, 3, 0, 35, 20, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal,DomCardType.Reaction}),
    Berserker (5, 0, 0, 0, 30, 25, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal}),
    Bishop (4, 0, 0, 0, 22, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher, DomCardType.TrashForBenefit}),
    Black_Cat (2, 0, 0, 0, 29, 16, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Reaction, DomCardType.Attack}),
    Black_Market (3, 0, 2, 0, 32, 20, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Blessed_Village (4, 0, 0, 0, 5, 21, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Village, DomCardType.Fate}),
    Blockade (4, 0, 0, 0, 38, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Duration, DomCardType.Attack}),
    Bonfire (3, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Border_Guard (2, 0, 0, 0, 18, 20, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Border_Village (6, 0, 0, 0, 5, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Village}),
    Borrow (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Bounty_Hunter (4, 0, 0, 0, 17, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Trasher}),
    Bridge (4, 0, 2, 0, 25, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Bridge_Troll (5, 0, 1, 0, 25, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Attack, DomCardType.Duration}),
    Bureaucracy (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Prophecy}),
    Bureaucrat (4, 0, 0, 0, 29, 20, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal}),
    Buried_Treasure (5, 0, 0, 0, 10, 17, new DomCardType[]{DomCardType.Treasure, DomCardType.Kingdom, DomCardType.Duration}),
    Bury (1, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Bustling_Village (5, 0, 0, 0, 5, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Village, DomCardType.Split_Pile, DomCardType.Split_Pile_Bottom}),
    Butcher (5, 0, 2, 0, 19, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher}),
    Cabin_Boy (4, 0, 0, 0, 17, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Duration}),
    Cache (5, 0, 3, 0, 100, 31, new DomCardType[]{DomCardType.Treasure, DomCardType.Kingdom}),
    Camel_Train (3, 0, 0, 0, 30, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Canal (7, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Project}),
    Candlestick_Maker (2, 0, 1, 0, 7, 19, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom}),
    Capital (5, 0, 6, 0, 19, 25, new DomCardType[]{DomCardType.Kingdom, DomCardType.Treasure}),
    Capitalism (5, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Project}),
    Captain (6, 0, 2, 0, 19, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Duration}),
    Caravan (4, 0, 0, 0, 8, 27, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Duration, DomCardType.Cycler, DomCardType.Card_Advantage}),
    Caravan_Guard (3, 0, 1, 0, 8, 27, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Duration, DomCardType.Cycler, DomCardType.Reaction}),
    Cardinal (4, 0, 2, 0, 25, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Attack, DomCardType.Terminal}),
    Cargo_Ship (3, 0, 2, 0, 28, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Duration}),
    Carnival (5, 0, 0, 0, 19, 32, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Carpenter (4, 0, 0, 0, 25, 24, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Trasher}),
    Cartographer (5, 0, 0, 0, 19, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Catacombs (5, 0, 0, 0, 19, 32, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Catapult (3, 0, 0, 0, 37, 21, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher, DomCardType.Attack,DomCardType.Split_Pile}),
    Cathedral (3, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Project}),
    Castles (0, 0, 0, 0, 100, 13, new DomCardType[]{DomCardType.Kingdom, DomCardType.Castle,DomCardType.Victory}),
    Cavalry (4, 0, 0, 0, 38, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Cellar (2, 0, 0, 0, 16, 17, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Cemetery (4, 0, 0, 2, 100, 9, new DomCardType[]{DomCardType.Victory, DomCardType.Kingdom}),
    Champion (6, 0, 0, 0, 0, 35, new DomCardType[]{DomCardType.Action, DomCardType.Duration,DomCardType.Traveller}),
    Chancellor (3, 0, 2, 0, 30, 20, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Change (4, 0, 0, 0, 24, 18, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher, DomCardType.TrashForBenefit}),
    Changeling (3, 0, 0, 0, 35, 20, new DomCardType[]{DomCardType.Night, DomCardType.Kingdom}),
    Chapel (2, 0, 0, 0, 37, 18, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher}),
    Chariot_Race (3, 0, 0, 0, 6, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Charlatan (5, 0, 3, 0, 18, 30, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal}),
    Charm (5, 0, 0, 0, 500, 25, new DomCardType[]{DomCardType.Kingdom, DomCardType.Treasure}),
    Church (3, 0, 0, 0, 19, 21, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Duration, DomCardType.Trasher}),
    Citadel (8, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Project}),
    City (5, 0, 0, 0, 5, 30, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Village}),
    City_Gate (3, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Project}),
    City_Quarter (0, 0, 0, 0, 2, 30, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Village}),
    Clerk (4, 0, 2, 0, 27, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Reaction, DomCardType.Attack}),
    Cobbler (5, 0, 0, 0, 20, 25, new DomCardType[]{DomCardType.Kingdom, DomCardType.Duration, DomCardType.Night}),
    Coin_of_the_Realm (2, 0, 0, 0, 10, 22, new DomCardType[]{DomCardType.Treasure, DomCardType.Kingdom, DomCardType.Reserve}),
    Collection (5, 0, 2, 0, 15, 27, new DomCardType[]{DomCardType.Kingdom, DomCardType.Treasure}),
    Colonnade (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Landmark}),
    Commerce (5, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Conclave (4, 0, 2, 0, 25, 23, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom}),
    Conquest (6, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Conspirator (4, 0, 2, 0, 35, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Continue (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Contraband (5, 0, 3, 0, 5, 6, new DomCardType[]{DomCardType.Treasure, DomCardType.Kingdom}),
    Coppersmith (4, 0, 0, 0, 26, 21, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Corsair (5, 0, 2, 0, 19, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Duration, DomCardType.Attack}),
    Council_Room (5, 0, 0, 0, 25, 27, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Count (5, 0, 3, 0, 25, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher}),
    Counterfeit (5, 0, 2, 0, 1, 27, new DomCardType[]{DomCardType.Kingdom, DomCardType.Treasure}),
    Counting_House (5, 0, 0, 0, 25, 24, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Courier (4, 0, 1, 0, 35, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Courtier (5, 0, 3, 0, 20, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom}),
    Courtyard (2, 0, 0, 0, 24, 24, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Coven (5, 0, 2, 0, 18, 30, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom}),
    Craftsman (3, 0, 0, 0, 24, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Credit (2, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Crew (5, 0, 0, 0, 19, 32, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Duration, DomCardType.Card_Advantage}),
    Crop_Rotation (6, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Project}),
    Crossroads (2, 0, 0, 0, 3, 17, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Village, DomCardType.Card_Advantage}),
    Crown (5, 0, 0, 0, 5, 27, new DomCardType[]{DomCardType.Kingdom, DomCardType.Treasure, DomCardType.Multiplier,DomCardType.Action}),
    Crumbling_Castle (4, 0, 0, 1, 0, 14, new DomCardType[]{DomCardType.Victory,DomCardType.Kingdom,DomCardType.Castle}),
    Crypt (5, 0, 0, 0, 20, 29, new DomCardType[]{DomCardType.Night, DomCardType.Kingdom, DomCardType.Duration}),
    Cultist (5, 0, 0, 0, 18, 40, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage, DomCardType.Looter}),
    Cursed_Gold (4, 0, 0, 0, 30, 5, new DomCardType[]{DomCardType.Treasure,DomCardType.Heirloom,DomCardType.Kingdom}),
    Cursed_Village (5, 0, 0, 0, 15, 35, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Village, DomCardType.Doom, DomCardType.Card_Advantage}),
    Cutpurse (4, 0, 2, 0, 32, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Attack, DomCardType.Terminal}),
    Daimyo (0, 0, 0, 0, 16, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Command, DomCardType.Duration}),
    Dame_Anna (5, 0, 0, 0, 25, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Attack, DomCardType.Terminal, DomCardType.Trasher,DomCardType.Knight}),
    Dame_Josephine (5, 0, 0, 2, 30, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Attack, DomCardType.Terminal, DomCardType.Victory,DomCardType.Knight}),
    Dame_Molly (5, 0, 0, 0, 5, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Attack, DomCardType.Terminal, DomCardType.Village,DomCardType.Knight}),
    Dame_Natalie (5, 0, 0, 0, 25, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Attack, DomCardType.Terminal,DomCardType.Knight}),
    Dame_Sylvia (5, 0, 2, 0, 25, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Attack, DomCardType.Terminal, DomCardType.Knight}),
    Death_Cart (4, 0, 5, 0, 25, 23, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Looter}),
    Defiled_Shrine (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Landmark}),
    Deliver (2, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Delusion (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Hex}),
    Delve (2, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Demand (5, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Den_of_Sin (5, 0, 0, 0, 1, 25, new DomCardType[]{DomCardType.Kingdom, DomCardType.Duration, DomCardType.Night, DomCardType.Card_Advantage}),
    Desperation (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Destrier (6, 0, 0, 0, 8, 40, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Card_Advantage}),
    Develop (3, 0, 0, 0, 33, 16, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher, DomCardType.TrashForBenefit}),
    Devil$s_Workshop (4, 0, 0, 0, 38, 22, new DomCardType[]{DomCardType.Night, DomCardType.Kingdom}),
    Diadem (0, 0, 2, 0, 50, 30, new DomCardType[]{DomCardType.Prize, DomCardType.Treasure}),
    Diplomat (4, 0, 0, 0, 27, 35, new DomCardType[]{DomCardType.Action, DomCardType.Reaction, DomCardType.Kingdom, DomCardType.Card_Advantage}),
    Disciple (5, 0, 0, 0, 7, 25, new DomCardType[]{DomCardType.Action, DomCardType.Traveller, DomCardType.Multiplier}),
    Dismantle (4, 0, 0, 0, 25, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher}),
    Displace (5, 0, 0, 0, 25, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Distant_Lands (5, 0, 0, 0, 25, 25, new DomCardType[]{DomCardType.Victory, DomCardType.Kingdom,DomCardType.Action}),
    Doctor (3, 0, 0, 0, 25, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher}),
    Dominate (14, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Donate (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Druid (2, 0, 0, 0, 28, 21, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Fate}),
    Ducat (2, 0, 1, 0, 10, 17, new DomCardType[]{DomCardType.Treasure, DomCardType.Kingdom}),
    Duchess (2, 0, 2, 0, 35, 19, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Duke (5, 0, 0, 3, 100, 8, new DomCardType[]{DomCardType.Victory, DomCardType.Kingdom}),
    Dungeon (3, 0, 0, 0, 10, 18, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Duration}),
    Duplicate (4, 0, 0, 0, 30, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Reserve}),
    Embargo (2, 0, 2, 0, 25, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Embassy (5, 0, 0, 0, 25, 24, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Emporium (5, 0, 1, 0, 13, 30, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler,DomCardType.Split_Pile, DomCardType.Split_Pile_Bottom}),
    Encampment (2, 0, 0, 0, 5, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Village,DomCardType.Split_Pile}),
    Enchantress (3, 0, 0, 0, 22, 22, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Duration,DomCardType.Card_Advantage}),
    Enclave (8, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Engineer (0, 0, 0, 0, 38, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Enlightenment (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Prophecy}),
    Envy (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Hex}),
    Envoy (4, 0, 0, 0, 24, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Exorcist (4, 0, 0, 0, 35, 20, new DomCardType[]{DomCardType.Night, DomCardType.Kingdom}),
    Expand (7, 0, 0, 0, 30, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher, DomCardType.TrashForBenefit}) {},
    Expedition (3, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Experiment (3, 0, 0, 0, 8, 40, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Card_Advantage}),
    Exploration (4, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Project}),
    Explorer (5, 0, 0, 0, 37, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Fair (4, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Project}),
    Fairgrounds (6, 0, 0, 0, 100, 7, new DomCardType[]{DomCardType.Victory, DomCardType.Kingdom}),
    Faithful_Hound (2, 0, 0, 0, 33, 4, new DomCardType[]{DomCardType.Action, DomCardType.Reaction, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Falconer (5, 0, 0, 0, 38, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Reaction}),
    Familiar (3, 1, 0, 0, 15, 40, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Cycler}),
    Famine (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Hex}),
    Farmers$_Market(3, 0, 0, 0, 24, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Gathering}),
    Farming_Village (4, 0, 0, 0, 3, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Village, DomCardType.Cycler}),
    Farmland (6, 0, 0, 2, 100, 9, new DomCardType[]{DomCardType.Victory, DomCardType.Kingdom}),
    Farrier (2, 0, 0, 0, 8, 21, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Fear (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Hex}),
    Feast (4, 0, 0, 0, 28, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Feodum (4, 0, 0, 0, 100, 7, new DomCardType[]{DomCardType.Victory, DomCardType.Kingdom}),
    Ferry (3, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Festival (5, 0, 2, 0, 3, 26, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Village}),
    Fisherman (5, 0, 1, 0, 13, 30, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Fishing_Village (3, 0, 1, 0, 3, 21, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Duration, DomCardType.Village}),
    Fishmonger (2, 0, 1, 0, 40, 21, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Shadow}),
    Flag_Bearer(4, 0, 2, 0, 29, 20, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Fleet (5, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Project}),
    Flourishing_Trade (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Prophecy}),
    Followers (0, 0, 0, 0, 18, 31, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Prize, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Fool (3, 0, 0, 0, 25, 21, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Fate}),
    Fool$s_Gold(2, 0, 2, 0, 85, 27, new DomCardType[]{DomCardType.Reaction, DomCardType.Kingdom, DomCardType.Treasure}),
    Footpad (5, 0, 2, 0, 30, 25, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal}),
    Forager (3, 0, 0, 0, 17, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Trasher}),
    Foresight (2, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Forge (7, 0, 0, 0, 30, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher, DomCardType.TrashForBenefit}),
    Fortress (4, 0, 0, 0, 5, 18, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Village}),
    Fortune (8, 0, 0, 0, 1100, 29, new DomCardType[]{DomCardType.Kingdom, DomCardType.Treasure, DomCardType.Split_Pile, DomCardType.Split_Pile_Bottom}),
    Fortune_Hunter (4, 0, 2, 0, 33, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Fortune_Teller (3, 0, 2, 0, 33, 22, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal}),
    Forum (5, 0, 0, 0, 8, 40, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Fountain (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Landmark}),
    Frigate (5, 0, 3, 0, 19, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Duration, DomCardType.Attack}),
    Fugitive (4, 0, 0, 0, 17, 25, new DomCardType[]{DomCardType.Action, DomCardType.Traveller, DomCardType.Cycler}),
    Gamble (2, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Gang_of_Pickpockets (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Ally}),
    Gardens (4, 0, 0, 0, 100, 9, new DomCardType[]{DomCardType.Victory, DomCardType.Kingdom}),
    Gatekeeper (5, 0, 0, 0, 19, 27, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Duration}),
    Gear (3, 0, 0, 0, 21, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Duration, DomCardType.Card_Advantage}),
    Ghost (4, 0, 0, 0, 35, 29, new DomCardType[]{DomCardType.Night, DomCardType.Duration, DomCardType.Multiplier, DomCardType.Spirit}),
    Ghost_Ship (5, 0, 0, 0, 21, 26, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Ghost_Town (3, 0, 0, 0, 1, 23, new DomCardType[]{DomCardType.Kingdom, DomCardType.Duration, DomCardType.Night, DomCardType.Village}),
    Giant (5, 0, 1, 0, 22, 30, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal}),
    Gladiator (3, 0, 2, 0, 28, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Split_Pile}),
    Goat (2, 0, 1, 0, 3, 18, new DomCardType[]{DomCardType.Treasure,DomCardType.Heirloom,DomCardType.Kingdom, DomCardType.Trasher}),
    Goatherd (3, 0, 0, 0, 17, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Trasher}),
    Gold_Mine (5, 0, 0, 0, 5, 19, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Golem (4, 1, 0, 0, 18, 40, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Village, DomCardType.Card_Advantage}),
    Goons (6, 0, 2, 0, 20, 31, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal}),
    Governor (5, 0, 0, 0,9, 40, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Card_Advantage, DomCardType.TrashForBenefit}),
    Grand_Castle (9, 0, 0, 5, 0, 6, new DomCardType[]{DomCardType.Victory,DomCardType.Kingdom,DomCardType.Castle}),
    Grand_Market (6, 0, 2, 0, 12, 39, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Graverobber (5, 0, 0, 0, 30, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher}),
    Great_Hall (3, 0, 0, 1, 5, 16, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Victory, DomCardType.Cycler}),
    Greed (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Hex}),
    Groom (4, 0, 0, 0, 25, 24, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Grotto (2, 0, 0, 0, 15, 19, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Duration}),
    Groundskeeper (5, 0, 0, 0, 6, 16, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Guardian (2, 0, 1, 0, 9, 21, new DomCardType[]{DomCardType.Night, DomCardType.Kingdom, DomCardType.Duration}),
    Guide (3, 0, 0, 0, 4, 16, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Reserve}),
    Guildhall (5, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Project}),
    Haggler (5, 0, 2, 0, 24, 27, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Hamlet (2, 0, 0, 0, 5, 20, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Village}),
    Harbinger (3, 0, 0, 0, 5, 16, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Farm(6, 0, 2, 2, 55, 25, new DomCardType[]{DomCardType.Victory, DomCardType.Kingdom, DomCardType.Treasure}),
    Harvest (5, 0, 3, 0, 19, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Haunted_Castle (6, 0, 0, 2, 0, 8, new DomCardType[]{DomCardType.Victory,DomCardType.Kingdom,DomCardType.Castle}),
    Haunted_Mirror (0, 0, 1, 0, 25, 18, new DomCardType[]{DomCardType.Treasure,DomCardType.Heirloom,DomCardType.Kingdom}),
    Haunted_Woods (5, 0, 0, 0, 21, 32, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Duration, DomCardType.Card_Advantage,DomCardType.Attack}),
    Haunting (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Hex}),
    Haven (2, 0, 0, 0, 15, 19, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Duration, DomCardType.Cycler}),
    Herald (4, 0, 0, 0, 5, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Herbalist (2, 0, 1, 0, 40, 21, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Hermit (3, 0, 0, 0, 26, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher}),
    Hero (5, 0, 2, 0, 20, 29, new DomCardType[]{DomCardType.Action, DomCardType.Traveller, DomCardType.Terminal}),
    Hideout (4, 0, 0, 0, 9, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Village, DomCardType.Cycler, DomCardType.Trasher}),
    Highway (5, 0, 1, 0, 7, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Hireling (6, 0, 0, 0, 22, 30, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Duration, DomCardType.Card_Advantage, DomCardType.Terminal}),
    Hoard (6, 0, 2, 0, 60, 32, new DomCardType[]{DomCardType.Kingdom, DomCardType.Treasure}),
    Horn_of_Plenty (5, 0, 0, 0, 1001, 26, new DomCardType[]{DomCardType.Kingdom, DomCardType.Treasure}),
    Horse (3, 0, 0, 0, 8, 40, new DomCardType[]{DomCardType.Action, DomCardType.Cycler, DomCardType.Card_Advantage}),
    Horse_Traders (4, 0, 1, 0, 29, 20, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Reaction}),
    Hostelry (4, 0, 0, 0, 6, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Village, DomCardType.Cycler}),
    Hovel (1, 0, 0, 0, 29, 10, new DomCardType[]{DomCardType.Reaction, DomCardType.Shelter, DomCardType.Junk}),
    Humble_Castle (3, 0, 1, 0, 58, 20, new DomCardType[]{DomCardType.Victory, DomCardType.Kingdom, DomCardType.Treasure, DomCardType.Castle}),
    Hunter (5, 0, 0, 0, 8, 40, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Card_Advantage}),
    Hunting_Grounds (6, 0, 0, 0, 19, 32, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Hunting_Lodge (5, 0, 0, 0, 7, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Village}),
    Hunting_Party (5, 0, 0, 0, 13, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler,DomCardType.Card_Advantage}),
    Idol (5, 0, 2, 0, 2, 27, new DomCardType[]{DomCardType.Kingdom, DomCardType.Treasure, DomCardType.Fate, DomCardType.Attack}),
    Ill_Gotten_Gains (5, 0, 2, 0, 95, 15, new DomCardType[]{DomCardType.Treasure, DomCardType.Kingdom}),
    Imp (2, 0, 0, 0, 33, 23, new DomCardType[]{DomCardType.Action, DomCardType.Terminal, DomCardType.Card_Advantage, DomCardType.Spirit}),
    Imperial_Envoy (5, 0, 0, 0, 25, 27, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Importer (3, 0, 0, 0, 25, 24, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Duration}),
    Improve (3, 0, 2, 0, 23, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Inheritance (7, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Inn (5, 0, 0, 0, 7, 20, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Village}),
    Innkeeper (4, 0, 0, 0, 10, 18, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Innovation (6, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Project}),
    Inventor (4, 0, 0, 0, 25, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Invest (4, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Investment (4, 0, 1, 0, 5, 17, new DomCardType[]{DomCardType.Treasure, DomCardType.Kingdom}),
    Ironmonger (4, 0, 0, 0, 5, 24, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Ironworks (4, 0, 0, 0, 25, 24, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom}),
    Island (4, 0, 0, 2, 37, 22, new DomCardType[]{DomCardType.Victory, DomCardType.Kingdom, DomCardType.Action, DomCardType.Terminal}),
    Jack_of_all_Trades (4, 0, 0, 0, 25, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Jester (5, 0, 2, 0, 19, 26, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal}),
    Journeyman (5, 0, 0, 0, 19, 32, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Junk_Dealer (5, 0, 1, 0, 16, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Trasher}),
    Keep (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Landmark}),
    Kiln (5, 0, 2, 0, 19, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    King$s_Castle (10, 0, 0, 0, 0, 4, new DomCardType[]{DomCardType.Victory,DomCardType.Kingdom,DomCardType.Castle}),
    King$s_Court (7, 0, 0, 0, 5, 34, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Multiplier}),
    Kintsugi (3, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Knights (5, 0, 0, 0, 100, 13, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Knight}),
    Laboratory (5, 0, 0, 0, 8, 40, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Card_Advantage}),
    Labyrinth (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Landmark}),
    Lackeys (2, 0, 0, 0, 29, 23, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Landing_Party (4, 0, 0, 0, 9, 30, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Village, DomCardType.Card_Advantage, DomCardType.Duration, DomCardType.NextTime}),
    League_of_Bankers (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Ally}),
    Legionary (5, 0, 3, 0, 28, 26, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal}),
    Leprechaun (3, 0, 0, 0, 26, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Doom}),
    Library (5, 0, 0, 0, 20, 30, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Lighthouse (2, 0, 1, 0, 9, 21, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Duration}),
    Litter (5, 0, 0, 0, 4, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Village, DomCardType.Card_Advantage}),
    Livery (5, 0, 3, 0, 25, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Loan (3, 0, 1, 0, 10, 17, new DomCardType[]{DomCardType.Treasure, DomCardType.Kingdom}),
    Locusts (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Hex}),
    Longship (5, 0, 0, 0, 6, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Village, DomCardType.Duration}),
    Lookout (3, 0, 0, 0, 3, 16, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom}),
    Loot (0, 0, 0, 0, 100, 13, new DomCardType[]{DomCardType.Treasure, DomCardType.Loot}),
    Lost_Arts (6, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Lost_City (5, 0, 0, 0, 9, 30, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Village, DomCardType.Card_Advantage}),
    Lost_In_The_Woods (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.State}),
    Lucky_Coin (4, 0, 1, 0, 25, 21, new DomCardType[]{DomCardType.Treasure,DomCardType.Heirloom,DomCardType.Kingdom}),
    Lurker (2, 0, 0, 0, 17, 20, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom}),
    Madman (0, 0, 0, 0, 1, 35, new DomCardType[]{DomCardType.Action, DomCardType.Village,DomCardType.Card_Advantage}),
    Magic_Lamp (0, 0, 1, 0, 30, 18, new DomCardType[]{DomCardType.Treasure,DomCardType.Heirloom,DomCardType.Kingdom}),
    Magnate (5, 0, 0, 0, 25, 24, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Magpie (4, 0, 0, 0, 7, 16, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Mandarin (5, 0, 3, 0, 26, 24, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Marauder (4, 0, 0, 0, 19, 27, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Looter}),
    Marchland (5, 0, 0, 0, 100, 7, new DomCardType[]{DomCardType.Victory, DomCardType.Kingdom}),
    Margrave (5, 0, 0, 0, 24, 26, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage, DomCardType.Attack}),
    Market (5, 0, 1, 0, 13, 30, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Market_Square (3, 0, 0, 0, 12, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Masquerade (3, 0, 0, 0, 27, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage, DomCardType.Trasher}),
    Mastermind (5, 0, 0, 0, 19, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Duration}),
    Masterpiece (3, 0, 1, 0, 150, 14, new DomCardType[]{DomCardType.Kingdom, DomCardType.Treasure}),
    Menagerie (3, 0, 0, 0, 1, 20, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Cycler}),
    Mercenary (0, 0, 2, 0, 22, 25, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Terminal, DomCardType.Trasher, DomCardType.Card_Advantage}),
    Merchant (3, 0, 0, 0, 7, 19, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Merchant_Camp (3, 0, 1, 0, 7, 19, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Village}),
    Merchant_Guild (5, 0, 1, 0, 19, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Merchant_Ship (5, 0, 2, 0, 19, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Duration}),
    Messenger (4, 0, 2, 0, 28, 21, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Militia (4, 0, 2, 0, 30, 25, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal}),
    Mill (4, 0, 0, 1, 5, 16, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Victory, DomCardType.Cycler}),
    Mine (5, 0, 0, 0, 24, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Minion (5, 0, 2, 0, 17, 40, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom}),
    Mining_Road (5, 0, 2, 0, 17, 40, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom}),
    Mining_Village (4, 0, 0, 0, 9, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Village, DomCardType.Cycler}),
    Mint (5, 0, 0, 0, 40, 19, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Miser (4, 0, 0, 0, 23, 21, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Misery (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Hex}),
    Mission (4, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Moat (2, 0, 0, 0, 33, 23, new DomCardType[]{DomCardType.Action, DomCardType.Reaction, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Monastery (2, 0, 0, 0, 35, 18, new DomCardType[]{DomCardType.Night, DomCardType.Kingdom, DomCardType.Trasher}),
    Moneylender (4, 0, 1, 0, 23, 21, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Monkey (3, 0, 0, 0, 22, 21, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Duration, DomCardType.Terminal}),
    Monument (4, 0, 2, 0, 22, 26, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Mountain_Pass (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Landmark}),
    Mountain_Shrine (0, 0, 0, 0, 28, 30, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Omen}),
    Mountain_Village (4, 0, 0, 0, 5, 21, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Village}),
    Mountebank (5, 0, 2, 0, 18, 30, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal}),
    Museum (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Landmark}),
    Mystic (5, 0, 2, 0, 15, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom}),
    Native_Village (2, 0, 0, 0, 3, 17, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Village}),
    Navigator (4, 0, 2, 0, 29, 20, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Necromancer (4, 0, 0, 0, 22, 25, new DomCardType[]{DomCardType.Action,DomCardType.Kingdom}),
    Necropolis (1, 0, 0, 0, 5, 15, new DomCardType[]{DomCardType.Village,DomCardType.Action,DomCardType.Shelter}),
    Night_Watchman (3, 0, 0, 0, 30, 22, new DomCardType[]{DomCardType.Night, DomCardType.Kingdom}),
    Ninja (4, 0, 2, 0, 30, 25, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Shadow}),
    Noble_Brigand (4, 0, 1, 0, 23, 21, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal}),
    Nomad_Camp (4, 0, 2, 0, 29, 20, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Nobles (6, 0, 0, 2, 12, 32, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Victory, DomCardType.Village,DomCardType.Card_Advantage}),
    Oasis (3, 0, 1, 0, 17, 21, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Obelisk (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Landmark}),
    Old_Witch (5, 0, 0, 0, 19, 35, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Opulent_Castle (7, 0, 0, 3, 27, 21, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Victory, DomCardType.Castle}),
    Oracle (3, 0, 0, 0, 20, 22, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Card_Advantage, DomCardType.Terminal}),
    Orchard (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Landmark}),
    Overlord (0, 0, 0, 0, 11, 29, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom}),
    Outpost (5, 0, 0, 0, 25, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Duration, DomCardType.Terminal}),
    Overgrown_Estate (1, 0, 0, 0, 0, 14, new DomCardType[]{DomCardType.Victory,DomCardType.Shelter, DomCardType.Junk}),
    Paddock (5, 0, 2, 0, 26, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Page (2, 0, 0, 0, 5, 16, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Traveller}),
    Pageant (3, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Project}),
    Palace (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Landmark}),
    Panic (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Prophecy}),
    Pasture (2, 0, 1, 0, 55, 16, new DomCardType[]{DomCardType.Treasure,DomCardType.Victory, DomCardType.Heirloom, DomCardType.Kingdom}),
    Pathfinding (8, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Patrician (2, 0, 0, 0, 4, 16, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler,DomCardType.Split_Pile}),
    Patrol(5, 0, 0, 0, 19, 32, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Patron (4, 0, 2, 0, 21, 20, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Reaction}),
    Pawn (2, 0, 1, 0, 11, 17, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Pearl_Diver (2, 0, 0, 0, 4, 16, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Peasant (2, 0, 0, 0, 29, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Traveller,DomCardType.Terminal}),
    Peddler (8, 0, 1, 0, 10, 30, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Philosopher$s_Stone(3, 1, 0, 0, 8,30, new DomCardType[]{DomCardType.Kingdom, DomCardType.Treasure}),
    Piazza (5, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Project}),
    Pilgrim (5, 0, 0, 0, 24, 24, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Pilgrimage (4, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Pillage (5, 0, 2, 0, 27, 28, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal}),
    Pirate (5, 0, 0, 0, 30, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal,DomCardType.Reaction,DomCardType.Duration}),
    Pirate_Ship (4, 0, 0, 0, 20, 20, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal}),
    Pixie (2, 0, 0, 0, 5, 18, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Fate, DomCardType.Cycler}),
    Plague (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Hex}),
    Plan (3, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Plateau_Shepherds (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Ally}),
    Plaza (4, 0, 0, 0, 5, 17, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Village}),
    Plunder (5, 0, 2, 0, 35, 28, new DomCardType[]{DomCardType.Kingdom, DomCardType.Treasure,DomCardType.Split_Pile, DomCardType.Split_Pile_Bottom}),
    Poacher (4, 0, 1, 0, 10, 30, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Poet (4, 0, 0, 0, 10, 30, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Omen}),
    Pooka (5, 0, 0, 0, 26, 26, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Poor_House (1, 0, 4, 0, 36, 21, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Port (4, 0, 0, 0, 5, 17, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Village}),
    Possession (6, 1, 0, 0, 22, 45, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Pouch (2, 0, 1, 0, 30, 18, new DomCardType[]{DomCardType.Treasure,DomCardType.Heirloom,DomCardType.Kingdom}),
    Poverty (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Hex}),
    Practice (3, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Prepare (3, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Priest (4, 0, 2, 0, 23, 21, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher}),
    Prince (8, 0, 0, 0, 29, 20, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Princess (0, 0, 2, 0, 23, 31, new DomCardType[]{DomCardType.Action, DomCardType.Prize, DomCardType.Terminal}),
    Procession (4, 0, 0, 0, 7, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Multiplier}),
    Quarry (4, 0, 3, 0, 65, 29, new DomCardType[]{DomCardType.Kingdom, DomCardType.Treasure}),
    Quartermaster (5, 0, 0, 0, 28, 30, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Duration, DomCardType.Terminal}),
    Quest (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Rabble (5, 0, 0, 0, 19, 29, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Raid (5, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Raider (6, 0, 0, 0, 19, 27, new DomCardType[]{DomCardType.Night, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Duration}),
    Ranger (4, 0, 0, 0, 24, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Rapid_Expansion (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Prophecy}),
    Ratcatcher (2, 0, 0, 0, 8, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Trasher,DomCardType.Cycler, DomCardType.Reserve}),
    Rats (4, 0, 0, 0, 15, 16, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Trasher,DomCardType.Cycler}),
    Raze (2, 0, 0, 0, 8, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Trasher,DomCardType.Cycler}),
    Reap (7, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Rebuild (5, 0, 0, 0, 20, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, }),
    Recruiter (5, 0, 0, 0, 25, 24, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage, DomCardType.Trasher}),
    Relic (5, 0, 2, 0, 5, 27, new DomCardType[]{DomCardType.Treasure, DomCardType.Kingdom, DomCardType.Attack}),
    Remake (4, 0, 0, 0, 35, 16, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher, DomCardType.TrashForBenefit}),
    Remodel (4, 0, 0, 0, 24, 18, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher, DomCardType.TrashForBenefit}),
    Replace (5, 0, 0, 0, 30, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher, DomCardType.TrashForBenefit, DomCardType.Attack}),
    Research (4, 0, 0, 0, 15, 19, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Duration, DomCardType.Trasher, DomCardType.TrashForBenefit}),
    Rice (7, 0, 0, 0, 999, 30, new DomCardType[]{DomCardType.Treasure, DomCardType.Kingdom}),
    Ritual (4, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Riverboat (3, 0, 0, 0, 28, 30, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Duration, DomCardType.Terminal}),
    Road_Network (5, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Project}),
    Rocks (4, 0, 1, 0, 95, 15, new DomCardType[]{DomCardType.Treasure, DomCardType.Kingdom,DomCardType.Split_Pile, DomCardType.Split_Pile_Bottom}),
    Rogue (5, 0, 2, 0, 25, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Attack, DomCardType.Terminal}),
    Ronin (5, 0, 0, 0, 20, 30, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage, DomCardType.Shadow}),
    Root_Cellar (3, 0, 0, 0, 10, 18, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Royal_Blacksmith (0, 0, 0, 0, 20, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Royal_Carriage (5, 0, 0, 0, 0, 20, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Multiplier,DomCardType.Reserve}),
    Royal_Galley (4, 0, 0, 0, 15, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Duration}),
    Royal_Seal (5, 0, 2, 0, 70, 25, new DomCardType[]{DomCardType.Kingdom, DomCardType.Treasure}),
    Ruined_Library (0, 0, 0, 0, 55, 13, new DomCardType[]{DomCardType.Action, DomCardType.Ruins, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Junk}),
    Ruined_Market (0, 0, 0, 0, 60, 13, new DomCardType[]{DomCardType.Action, DomCardType.Ruins, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Junk}),
    Ruined_Village (0, 0, 0, 0, 60, 13, new DomCardType[]{DomCardType.Action, DomCardType.Ruins, DomCardType.Kingdom, DomCardType.Junk}),
    Ruins (0, 0, 0, 0, 100, 13, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Ruins, DomCardType.Junk}),
    Rush (2, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Rustic_Village (4, 0, 0, 0, 5, 30, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Village, DomCardType.Omen}),
    Saboteur (5, 0, 0, 0, 20, 23, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal}),
    Sack_of_Loot (6, 0, 1, 0, 10, 17, new DomCardType[]{DomCardType.Treasure, DomCardType.Kingdom}),
    Sacred_Grove (5, 0, 3, 0, 25, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Fate}),
    Sacrifice (4, 0, 0, 0, 29, 18, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher}),
    Sage (3, 0, 0, 0, 12, 18, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Sailor (4, 0, 0, 0, 17, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Trasher, DomCardType.Duration}),
    Salt_the_Earth (4, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Salvager (4, 0, 0, 0, 25, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher, DomCardType.TrashForBenefit}),
    Samurai (6, 0, 2, 0, 30, 25, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Duration}),
    Sanctuary (5, 0, 0, 0, 12, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Trasher}),
    Sauna (4, 0, 0, 0, 12, 18, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Trasher, DomCardType.Split_Pile}),
    Save (1, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Scavenger (4, 0, 2, 0, 30, 20, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Scepter (5, 0, 2, 0, 100, 25, new DomCardType[]{DomCardType.Treasure, DomCardType.Kingdom}),
    Scheme (3, 0, 0, 0, 10, 16, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Scholar (5, 0, 0, 0, 25, 24, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Scout (4, 0, 0, 0, 2, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Scouting_Party (2, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Scrap (3, 0, 0, 0, 17, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Trasher}),
    Scrying_Pool (2, 1, 0, 0, 7, 35 , new DomCardType[]{DomCardType.Action , DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Card_Advantage, DomCardType.Attack}),
    Sculptor (5, 0, 0, 0, 30, 27, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Sea_Chart (3, 0, 0, 0, 17, 20, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Sea_Hag (4, 0, 0, 0, 19, 27, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal}),
    Sea_Witch (5, 0, 0, 0, 19, 27, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Duration, DomCardType.Card_Advantage}),
    Seaway (5, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Sentinel (3, 0, 0, 0, 37, 18, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher}),
    Secret_Cave (3, 0, 0, 0, 17, 27, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Duration}),
    Secret_Chamber (2, 0, 0, 0, 40, 16, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Reaction}),
    Secret_Passage (4, 0, 0, 0, 12, 27, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Seer (5, 0, 0, 0, 19, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Card_Advantage}),
    Sentry (5, 0, 0, 0, 2, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Settlers (2, 0, 0, 0, 7, 21, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler,DomCardType.Split_Pile}),
    Sewers (3, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Project}),
    Shaman (2, 0, 1, 0, 17, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Trasher}),
    Shanty_Town (3, 0, 0, 0, 8, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Village, DomCardType.Card_Advantage}),
    Sheepdog (3, 0, 0, 0, 33, 23, new DomCardType[]{DomCardType.Action, DomCardType.Reaction, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Shepherd (4, 0, 0, 0, 16, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom}),
    Shop (3, 0, 1, 0, 18, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Silk_Merchant (4, 0, 0, 0, 33, 23, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Silk_Road (4, 0, 0, 0, 100, 9, new DomCardType[]{DomCardType.Victory, DomCardType.Kingdom}),
    Silos (4, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Project}),
    Sinister_Plot (4, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Project}),
    Sir_Bailey (5, 0, 0, 0, 15, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Attack, DomCardType.Cycler,DomCardType.Knight}),
    Sir_Destry (5, 0, 0, 0, 24, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Attack, DomCardType.Card_Advantage,DomCardType.Terminal, DomCardType.Knight}),
    Sir_Martin (4, 0, 0, 0, 29, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Attack, DomCardType.Terminal, DomCardType.Knight}),
    Sir_Michael (5, 0, 0, 0, 25, 30, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Attack, DomCardType.Terminal, DomCardType.Knight}),
    Sir_Vander (5, 0, 0, 0, 30, 30, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Attack, DomCardType.Terminal, DomCardType.Knight}),
    Siren (3, 0, 0, 0, 19, 27, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Duration}),
    Skulk (4, 0, 0, 0, 25, 25, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Doom}),
    Sleigh (2, 0, 0, 0, 38, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Reaction}),
    Small_Castle (5, 0, 0, 2, 29, 13, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Victory, DomCardType.Castle}),
    Small_Potatoes (2, 0, 0, 0, 65, 29, new DomCardType[]{DomCardType.Kingdom, DomCardType.Treasure}),
    Smithy (4, 0, 0, 0, 25, 24, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Smugglers (3, 0, 0, 0, 30, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Snake_Witch (2, 0, 0, 0, 15, 25, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Cycler}),
    Snowy_Village (3, 0, 0, 0, 5, 21, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Village}),
    Soldier (3, 0, 2, 0, 22, 25, new DomCardType[]{DomCardType.Action, DomCardType.Traveller, DomCardType.Terminal,DomCardType.Attack}),
    Soothsayer (5, 0, 0, 0, 18, 40, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal}),
    Souk (5, 0, 0, 0, 19, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Spice_Merchant (4, 0, 0, 0, 9, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Spices (5, 0, 2, 0, 100, 25, new DomCardType[]{DomCardType.Treasure, DomCardType.Kingdom}),
    Spoils (0, 0, 0, 0, 30, 31, new DomCardType[]{DomCardType.Treasure}),
    Sprawling_Castle (8, 0, 0, 4, 0, 7, new DomCardType[]{DomCardType.Victory,DomCardType.Kingdom,DomCardType.Castle}),
    Spy (4, 0, 0, 0, 5, 22, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Cycler}),
    Squire (2, 0, 1, 0, 5, 17, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Village}),
    Stables (5, 0, 0, 0, 19, 26, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Card_Advantage}),
    Stampede (5, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Star_Chart (3, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Project}),
    Stash (5, 0, 2, 0, 75, 23, new DomCardType[]{DomCardType.Kingdom, DomCardType.Treasure}),
    Steward (3, 0, 2, 0, 27, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher}),
    Stockpile (3, 0, 3, 0, 70, 25, new DomCardType[]{DomCardType.Kingdom, DomCardType.Treasure}),
    Stonemason (2, 0, 0, 0, 35, 7, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Trasher, DomCardType.Terminal}),
    Storeroom (3, 0, 0, 0, 30, 18, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Storyteller (5, 0, 0, 0, 10, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Summon (5, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Supplies (2, 0, 1, 0, 19, 25, new DomCardType[]{DomCardType.Kingdom, DomCardType.Treasure}),
    Survivors (0, 0, 0, 0, 59, 13, new DomCardType[]{DomCardType.Action, DomCardType.Ruins, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Junk}),
    Swamp_Hag (5, 0, 0, 0, 19, 27, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Duration}),
    Swamp_Shacks (4, 0, 0, 0, 5, 22, new DomCardType[]{DomCardType.Action, DomCardType.Village, DomCardType.Kingdom}),
    Swashbuckler (5, 0, 0, 0, 19, 32, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Swindler (3, 0, 2, 0, 25, 23, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal}),
    Sycophant (2, 0, 0, 0, 18, 13, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Liaison}),
    Tactician (5, 0, 0, 0, 19, 27, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Duration, DomCardType.Terminal,DomCardType.Card_Advantage}),
    Talisman (4, 0, 1, 0, 80, 23, new DomCardType[]{DomCardType.Kingdom, DomCardType.Treasure}),
    Tanuki (5, 0, 0, 0, 23, 18, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher, DomCardType.TrashForBenefit, DomCardType.Shadow}),
    Taskmaster (3, 0, 0, 0, 18, 21, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Duration}),
    Tax (2, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Taxman (4, 0, 2, 0, 32, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Attack, DomCardType.Terminal}),
    Tea_House (5, 0, 0, 0, 16, 30, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Omen}),
    Teacher (6, 0, 0, 0, 20, 30, new DomCardType[]{DomCardType.Action, DomCardType.Terminal, DomCardType.Reserve,DomCardType.Traveller}),
    Temple (4, 0, 0, 0, 37, 18, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher, DomCardType.Gathering}),
    Tent (3, 0, 2, 0, 32, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Territory (6, 0, 0, 0, 100, 7, new DomCardType[]{DomCardType.Victory, DomCardType.Kingdom}),
    The_Earth$s_Gift (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Boon}),
    The_Field$s_Gift (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Boon}),
    The_Flame$s_Gift (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Boon}),
    The_Forest$s_Gift (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Boon}),
    The_Moon$s_Gift (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Boon}),
    The_Mountain$s_Gift (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Boon}),
    The_River$s_Gift (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Boon}),
    The_Sea$s_Gift (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Boon}),
    The_Sky$s_Gift (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Boon}),
    The_Sun$s_Gift (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Boon}),
    The_Swamp$s_Gift (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Boon}),
    The_Wind$s_Gift (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Boon}),
    Thief (4, 0, 0, 0, 30, 20, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal}),
    Throne_Room (4, 0, 0, 0, 7, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Multiplier}),
    Tiara (4, 0, 0, 0, 1, 27, new DomCardType[]{DomCardType.Kingdom, DomCardType.Treasure}),
    Tide_Pools (4, 0, 0, 0, 8, 27, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Duration, DomCardType.Cycler, DomCardType.Card_Advantage}),
    Tomb (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Landmark}),
    Tools (4, 0, 0, 0, 1000, 26, new DomCardType[]{DomCardType.Kingdom, DomCardType.Treasure}),
    Tormentor (5, 0, 2, 0, 25, 27, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Doom}),
    Torturer (5, 0, 0, 0, 20, 27, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Tournament (4, 0, 1, 0, 8, 31, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Tower (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Landmark}),
    Tracker (2, 0, 1, 0, 5, 18, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Fate, DomCardType.Terminal}),
    Trade (5, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Trader (4, 0, 0, 0, 35, 20, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher, DomCardType.Reaction, DomCardType.TrashForBenefit}),
    Trade_Route (3, 0, 0, 0, 25, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher}),
    Trading_Post (5, 0, 0, 0, 25, 24, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher}),
    Tragic_Hero (5, 0, 0, 0, 21, 29, new DomCardType[]{DomCardType.Action, DomCardType.Terminal, DomCardType.Card_Advantage, DomCardType.Kingdom}),
    Trail (4, 0, 0, 0, 12, 0, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Reaction}),
    Training (6, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Transmogrify (4, 0, 0, 0, 10, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Trasher, DomCardType.TrashForBenefit,DomCardType.Reserve}),
    Transmute (0, 1, 0, 0, 35, 19, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Trasher}),
    Travelling_Fair (2, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Treasure_Hunter (3, 0, 1, 0, 7, 25, new DomCardType[]{DomCardType.Action, DomCardType.Traveller}),
    Treasure_Map (4, 0, 0, 0, 20, 35, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Treasure_Trove (5, 0, 2, 0, 100, 25, new DomCardType[]{DomCardType.Treasure, DomCardType.Kingdom}),
    Treasurer (5, 0, 3, 0, 25, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Treasury (5, 0, 1, 0, 15, 30, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Tribute (5, 0, 0, 0, 24, 26, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Triumph (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Triumphal_Arch (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Landmark}),
    Trusty_Steed (0, 0, 0, 0, 6, 45, new DomCardType[]{DomCardType.Action, DomCardType.Prize, DomCardType.Cycler, DomCardType.Village, DomCardType.Card_Advantage}),
    Tunnel (3, 0, 0, 2, 100, 0, new DomCardType[]{DomCardType.Victory, DomCardType.Kingdom}),
    UberDomination (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Underling (3, 0, 0, 0, 15, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Liaison}),
    University (2, 1, 0, 0, 5, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Village}),
    Upgrade (5, 0, 0, 0, 16, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Trasher, DomCardType.TrashForBenefit}),
    Urchin (3, 0, 0, 0, 5, 20, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Attack, DomCardType.Cycler}),
    Vagrant (2, 0, 0, 0, 4, 16, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Vampire (5, 0, 0, 0, 19, 27, new DomCardType[]{DomCardType.Night, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Doom}),
    Vassal (3, 0, 2, 0, 25, 23, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Vault (5, 0, 0, 0, 20, 26, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Venture (5, 0, 2, 0, 500, 29, new DomCardType[]{DomCardType.Kingdom, DomCardType.Treasure}),
    Villa (4, 0, 1, 0, 5, 20, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Village}),
    Village (3, 0, 0, 0, 5, 21, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Village}),
    Village_Green (4, 0, 0, 0, 5, 0, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Village, DomCardType.Reaction, DomCardType.Duration}),
    Villain (5, 0, 2, 0, 27, 27, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal}),
    Vineyard (0, 1, 0, 0, 100, 7, new DomCardType[]{DomCardType.Victory, DomCardType.Kingdom}),
    Wall (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Landmark}),
    Walled_Village (4, 0, 0, 0, 5, 18, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Village}),
    Wandering_Minstrel (4, 0, 0, 0, 5, 18, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.Village}),
    War (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Hex}),
    Warehouse (3, 0, 0, 0, 10, 18, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Warrior (4, 0, 0, 0, 20, 23, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Terminal, DomCardType.Traveller, DomCardType.Card_Advantage}),
    Watchtower (3, 0, 0, 0, 27, 25, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Reaction, DomCardType.Card_Advantage}),
    Way_of_the_Ox (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Way}),
    Way_of_the_Pig (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Way}),
    Wayfarer (6, 0, 0, 0, 19, 32, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Weaver (4, 0, 0, 0, 25, 24, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Reaction}),
    Wedding (4, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Werewolf (5, 0, 0, 0, 21, 27, new DomCardType[]{DomCardType.Night, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Doom, DomCardType.Action}),
    Wharf (5, 0, 0, 0, 19, 32, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Duration, DomCardType.Card_Advantage}),
    Wild_Hunt (5, 0, 0, 0, 19, 32, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage, DomCardType.Gathering}),
    Will_o$_Wisp (0, 0, 0, 0, 4, 18, new DomCardType[]{DomCardType.Action, DomCardType.Cycler, DomCardType.Spirit}),
    Windfall (5, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Event}),
    Wine_Merchant (5, 0, 4, 0, 21, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Wish (0, 0, 0, 0, 17, 30, new DomCardType[]{DomCardType.Action}),
    Wishing_Well (3, 0, 0, 0, 6, 20, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    Witch (5, 0, 0, 0, 18, 40, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Witch$s_Hut (5, 0, 0, 0, 18, 40, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal, DomCardType.Card_Advantage}),
    Wolf_Den (0, 0, 0, 0, 0, 0, new DomCardType[]{DomCardType.Landmark}),
    Woodcutter (3, 0, 2, 0, 29, 20, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Worker$s_Village (4, 0, 0, 0, 3, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Village, DomCardType.Cycler}),
    Workshop (3, 0, 0, 0, 38, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Young_Witch (4, 0, 0, 0, 18, 26, new DomCardType[]{DomCardType.Action, DomCardType.Attack, DomCardType.Kingdom, DomCardType.Terminal}),
    Zombie_Apprentice (3, 0, 0, 0, 20, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Card_Advantage, DomCardType.TrashForBenefit}),
    Zombie_Mason (3, 0, 0, 0, 27, 23, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Terminal}),
    Zombie_Spy (3, 0, 0, 0, 5, 22, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler}),
    ZZZtest (4, 0, 0, 0, 12, 28, new DomCardType[]{DomCardType.Action, DomCardType.Kingdom, DomCardType.Cycler, DomCardType.DominionDevelopment}),

    ;

    public static final Comparator<DomCardName> SORT_FOR_TRASHING = new Comparator<DomCardName>(){
        public int compare( DomCardName aO1, DomCardName aO2 ) {
            if (aO1.getTrashPriority()< aO2.getTrashPriority())
                return -1;
            if (aO1.getTrashPriority() > aO2.getTrashPriority())
                return 1;
            return 0;
        }
    };

    private DomCost cost = null;
    private final HashSet< DomCardType > types = new HashSet< DomCardType >();
    private int coinValue;
    private int victoryValue;
    private int playPriority;
    private int discardPriority;

    public static final Comparator<DomCardName> FOR_TRASHING = new Comparator<DomCardName>(){
        public int compare( DomCardName aO1, DomCardName aO2 ) {
            if (aO1.getTrashPriority()< aO2.getTrashPriority())
                return -1;
            if (aO1.getTrashPriority() > aO2.getTrashPriority())
                return 1;
            return 0;
        }
    };


    /**
     * Used solely for the non-existent card (meant to represent a lack of cards).
     */
    private DomCardName() {
    	
    }
    /**
     * Sole constructor.
     * <p>
     * Note that coin cost, potion cost, coin value, and victory value are all printed on card, while play priority and discard priority are not.
     * <p>
     * The coin value and victory value are the non-variable values printed as "+ x" on the card, not the values that require any sort of calculation.
     * @param aCoinCost int the coin cost as printed on card
     * @param aPotionCost int potion cost as printed on card
     * @param aCoinValue int number of non-variable coins added to money pool
     * @param aVictoryValue int number of non-variable victory points added to score
     * @param aPlayPriority int play priority
     * @param aDiscardPriority int discard priority
     * @param aTypes DomCardType array of all types
     */
    DomCardName(int aCoinCost, int aPotionCost, int aCoinValue, int aVictoryValue, int aPlayPriority, int aDiscardPriority, DomCardType[] aTypes) {
      cost = new DomCost(aCoinCost, aPotionCost);
      coinValue = aCoinValue;
      victoryValue = aVictoryValue;
      playPriority = aPlayPriority;
      discardPriority = aDiscardPriority;
      for (DomCardType theType : aTypes) {
        types.add( theType );
      }
      //TODO Fix constructor to include debt cost
      if ("Continue".equals(name())||"Donate".equals(name()) || "Royal_Blacksmith".equals(name()) || "Fortune".equals(name()) || "Overlord".equals(name()) || "Annex".equals(name()) ||"City_Quarter".equals(name())) {
          cost.setDebt(8);
      }
      if ("Daimyo".equals(name())) {
          cost.setDebt(6);
      }
      if ("Triumph".equals(name())||"Mountain_Shrine".equals(name())) {
          cost.setDebt(5);
      }
      if ("Engineer".equals(name())) {
          cost.setDebt(4);
      }
      if ("Wedding".equals(name())) {
          cost.setDebt(3);
      }
    }

    
    public static DomCardName[] getSafeValues() {
        ArrayList<DomCardName> theValues = new ArrayList<DomCardName>();
        for (DomCardName theCard : values()){
            if (theCard.hasCardType(DomCardType.Knight)&&theCard!=Knights)
                continue;
            if (theCard.hasCardType(DomCardType.Castle)&&theCard!=Castles)
                continue;
            if (theCard.hasCardType(DomCardType.Ruins)&&theCard!=Ruins)
                continue;
            theValues.add(theCard);
        }
        if (DomEngine.developmentMode) {
            DomCardName[] theArr = new DomCardName[1];
            return (DomCardName[]) theValues.toArray(theArr);
        } else {
            ArrayList<DomCardName> theCards = new ArrayList<DomCardName>();
            for (DomCardName theCard : theValues){
                if (!theCard.hasCardType(DomCardType.DominionDevelopment))
                    theCards.add(theCard);
            }
            DomCardName[] theArr = new DomCardName[1];
            return (DomCardName[]) theCards.toArray(theArr);
        }
    }

    /**
     * @return
     */
    public DomCard createNewCardInstance() {
      //TODO look into Dynamic Class Loading (tried it, but took way too long)
      switch (this) {
        case Abandoned_Mine:
            return new Abandoned_MineCard();
        case Abundance:
            return new AbundanceCard();
        case Acting_Troupe:
            return new Acting_TroupeCard();
        case Advance:
            return new AdvanceCard();
        case Adventurer:
          return new AdventurerCard();
        case Advisor:
            return new AdvisorCard();
        case Alchemist:
            return new AlchemistCard();
        case Alley:
            return new AlleyCard();
        case Alliance:
            return new AllianceCard();
        case Alms:
            return new AlmsCard();
        case Altar:
            return new AltarCard();
        case Amass:
            return new AmassCard();
        case Ambassador:
            return new AmbassadorCard();
        case Amphora:
            return new AmphoraCard();
        case Amulet:
            return new AmuletCard();
        case Animal_Fair:
            return new Animal_FairCard();
        case Annex:
            return new AnnexCard();
        case Anvil:
            return new AnvilCard();
        case Apprentice:
            return new ApprenticeCard();
        case Apothecary:
            return new ApothecaryCard();
        case Archive:
            return new ArchiveCard();
        case Aristocrat:
            return new AristocratCard();
        case Armory:
            return new ArmoryCard();
        case Artificer:
            return new ArtificerCard();
        case Artisan:
            return new ArtisanCard();
        case Astrolabe:
            return new AstrolabeCard();
        case Avanto:
            return new AvantoCard();
        case Avoid:
            return new AvoidCard();
        case Bad_Omens:
            return new Bad_OmensCard();
        case Bag_of_Gold:
            return new Bag_of_GoldCard();
        case Baker:
            return new BakerCard();
        case Ball:
            return new BallCard();
        case Band_of_Misfits:
            return new Band_of_MisfitsCard();
        case Bandit:
            return new BanditCard();
        case Bandit_Camp:
            return new Bandit_CampCard();
        case Banish:
            return new BanishCard();
        case Bank:
            return new BankCard();
        case Banquet:
            return new BanquetCard();
        case Barbarian:
            return new BarbarianCard();
        case Bard:
            return new BardCard();
        case Bargain:
            return new BargainCard();
        case Barge:
            return new BargeCard();
        case Baron:
            return new BaronCard();
        case Bat:
            return new BatCard();
        case Bauble:
            return new BaubleCard();
        case Bazaar:
            return new BazaarCard();
        case Beggar:
            return new BeggarCard();
        case Berserker:
            return new BerserkerCard();
        case Bishop:
            return new BishopCard();
        case Black_Cat:
            return new Black_CatCard();
        case Black_Market:
            return new Black_MarketCard();
        case Blessed_Village:
            return new Blessed_VillageCard();
        case Blockade:
            return new BlockadeCard();
        case Bonfire:
            return new BonfireCard();
        case Border_Guard:
            return new Border_GuardCard();
        case Border_Village:
            return new Border_VillageCard();
        case Borrow:
            return new BorrowCard();
        case Bounty_Hunter:
            return new Bounty_HunterCard();
        case Bridge:
            return new BridgeCard();
        case Bridge_Troll:
            return new Bridge_TrollCard();
        case Bureaucrat:
            return new BureaucratCard();
        case Buried_Treasure:
            return new Buried_TreasureCard();
        case Bury:
            return new BuryCard();
        case Bustling_Village:
            return new Bustling_VillageCard();
        case Butcher:
            return new ButcherCard();
        case Cabin_Boy:
            return new Cabin_BoyCard();
        case Cache:
            return new CacheCard();
        case Camel_Train:
            return new Camel_TrainCard();
        case Candlestick_Maker:
            return new Candlestick_MakerCard();
        case Capital:
            return new CapitalCard();
        case Captain:
            return new CaptainCard();
        case Caravan:
            return new CaravanCard();
        case Caravan_Guard:
            return new Caravan_GuardCard();
        case Cardinal:
            return new CardinalCard();
        case Cargo_Ship:
            return new Cargo_ShipCard();
        case Carnival:
            return new CarnivalCard();
        case Carpenter:
            return new CarpenterCard();
        case Cartographer:
            return new CartographerCard();
        case Catacombs:
            return new CatacombsCard();
        case Catapult:
            return new CatapultCard();
        case Cathedral:
            return new CathedralCard();
        case Cavalry:
            return new CavalryCard();
        case Cellar:
            return new CellarCard();
        case Cemetery:
            return new CemeteryCard();
        case Champion:
            return new ChampionCard();
        case Chancellor:
            return new ChancellorCard();
        case Change:
            return new ChangeCard();
        case Changeling:
            return new ChangelingCard();
        case Chapel:
            return new ChapelCard();
        case Chariot_Race:
            return new Chariot_RaceCard();
        case Charlatan:
            return new CharlatanCard();
        case Charm:
            return new CharmCard();
        case Church:
            return new ChurchCard();
        case City:
            return new CityCard();
        case City_Quarter:
            return new City_QuarterCard();
        case Clerk:
            return new ClerkCard();
        case Cobbler:
            return new CobblerCard();
        case Coin_of_the_Realm:
            return new Coin_of_the_RealmCard();
        case Collection:
            return new CollectionCard();
        case Colony:
            return new ColonyCard();
        case Commerce:
        	return new CommerceCard();
        case Conclave:
            return new ConclaveCard();
        case Conquest:
            return new ConquestCard();
        case Conspirator:
            return new ConspiratorCard();
        case Continue:
            return new ContinueCard();
        case Contraband:
            return new ContrabandCard();
        case Copper:
            return new CopperCard();
        case Coppersmith:
            return new CoppersmithCard();
        case Corsair:
            return new CorsairCard();
        case Council_Room:
            return new Council_RoomCard();
        case Count:
            return new CountCard();
        case Counterfeit:
            return new CounterfeitCard();
        case Counting_House:
            return new Counting_HouseCard();
        case Courier:
            return new CourierCard();
        case Courtier:
            return new CourtierCard();
        case Courtyard:
            return new CourtyardCard();
        case Coven:
            return new CovenCard();
        case Craftsman:
            return new CraftsmanCard();
        case Credit:
            return new CreditCard();
        case Crew:
            return new CrewCard();
        case Crossroads:
            return new CrossroadsCard();
        case Crown:
            return new CrownCard();
        case Crumbling_Castle:
            return new Crumbling_CastleCard();
        case Crypt:
            return new CryptCard();
        case Cultist:
            return new CultistCard();
        case Curse:
            return new CurseCard();
        case Cursed_Gold:
            return new Cursed_GoldCard();
        case Cursed_Village:
            return new Cursed_VillageCard();
        case Cutpurse:
            return new CutpurseCard();
        case Daimyo:
            return new DaimyoCard();
        case Dame_Anna:
            return new Dame_AnnaCard();
        case Dame_Josephine:
            return new Dame_JosephineCard();
        case Dame_Molly:
            return new Dame_MollyCard();
        case Dame_Natalie:
            return new Dame_NatalieCard();
        case Dame_Sylvia:
            return new Dame_SylviaCard();
        case Death_Cart:
            return new Death_CartCard();
        case Deliver:
            return new DeliverCard();
        case Delusion:
            return new DelusionCard();
        case Delve:
            return new DelveCard();
        case Demand:
            return new DemandCard();
        case Den_of_Sin:
            return new Den_of_SinCard();
        case Desperation:
            return new DesperationCard();
        case Destrier:
            return new DestrierCard();
        case Develop:
            return new DevelopCard();
        case Devil$s_Workshop:
            return new Devil$s_WorkshopCard();
        case Diadem:
            return new DiademCard();
        case Diplomat:
            return new DiplomatCard();
        case Disciple:
            return new DiscipleCard();
        case Dismantle:
            return new DismantleCard();
        case Displace:
            return new DisplaceCard();
        case Distant_Lands:
            return new Distant_LandsCard();
        case Doctor:
            return new DoctorCard();
        case Dominate:
            return new DominateCard();
        case Donate:
            return new DonateCard();
        case Druid:
            return new DruidCard();
        case Ducat:
            return new DucatCard();
        case Duchess:
            return new DuchessCard();
        case Duchy:
            return new DuchyCard();
        case Duke:
            return new DukeCard();
        case Dungeon:
            return new DungeonCard();
        case Duplicate:
            return new DuplicateCard();
        case Embargo:
            return new EmbargoCard();
        case Embassy:
            return new EmbassyCard();
        case Emporium:
            return new EmporiumCard();
        case Encampment:
            return new EncampmentCard();
        case Enchantress:
            return new EnchantressCard();
        case Enclave:
            return new EnclaveCard();
        case Engineer:
            return new EngineerCard();
        case Envy:
            return new EnvyCard();
        case Envoy:
            return new EnvoyCard();
        case Estate:
            return new EstateCard();
        case Exorcist:
            return new ExorcistCard();
        case Expand:
            return new ExpandCard();
        case Expedition:
            return new ExpeditionCard();
          case Experiment:
            return new ExperimentCard();
        case Explorer:
            return new ExplorerCard();
        case Faithful_Hound:
            return new Faithful_HoundCard();
        case Fairgrounds:
            return new FairgroundsCard();
        case Falconer:
            return new FalconerCard();
        case Familiar:
            return new FamiliarCard();
        case Farmers$_Market:
            return new Farmers$_MarketCard();
        case Farming_Village:
            return new Farming_VillageCard();
        case Farmland:
            return new FarmlandCard();
        case Farrier:
            return new FarrierCard();
        case Famine:
            return new FamineCard();
        case Fear:
            return new FearCard();
        case Feast:
            return new FeastCard();
        case Feodum:
            return new FeodumCard();
        case Ferry:
            return new FerryCard();
        case Festival:
            return new FestivalCard();
        case Fisherman:
            return new FishermanCard();
        case Fishing_Village:
            return new Fishing_VillageCard();
        case Fishmonger:
            return new FishmongerCard();
        case Flag_Bearer:
            return new Flag_BearerCard();
        case Fool:
            return new FoolCard();
        case Fool$s_Gold:
            return new Fool$s_GoldCard();
        case Followers:
            return new FollowersCard();
        case Footpad:
            return new FootpadCard();
        case Forager:
            return new ForagerCard();
        case Foresight:
            return new ForesightCard();
        case Forge:
            return new ForgeCard();
        case Fortress:
            return new FortressCard();
        case Fortune:
            return new FortuneCard();
        case Fortune_Hunter:
            return new Fortune_HunterCard();
        case Fortune_Teller:
            return new Fortune_TellerCard();
        case Forum:
            return new ForumCard();
        case Frigate:
            return new FrigateCard();
        case Fugitive:
            return new FugitiveCard();
        case Gamble:
            return new GambleCard();
        case Gardens:
            return new GardensCard();
        case Gatekeeper:
            return new GatekeeperCard();
        case Gear:
            return new GearCard();
        case Ghost:
            return new GhostCard();
        case Ghost_Ship:
            return new Ghost_ShipCard();
        case Ghost_Town:
            return new Ghost_TownCard();
        case Giant:
            return new GiantCard();
        case Gladiator:
            return new GladiatorCard();
        case Goat:
            return new GoatCard();
        case Goatherd:
            return new GoatherdCard();
        case Gold:
            return new GoldCard();
        case Gold_Mine:
            return new Gold_MineCard();
        case Golem:
            return new GolemCard();
        case Goons:
            return new GoonsCard();
        case Governor:
            return new GovernorCard();
        case Grand_Castle:
            return new Grand_CastleCard();
        case Grand_Market:
            return new Grand_MarketCard();
        case Graverobber:
            return new GraverobberCard();
        case Great_Hall:
            return new Great_HallCard();
        case Greed:
            return new GreedCard();
        case Groom:
            return new GroomCard();
        case Grotto:
            return new GrottoCard();
        case Groundskeeper:
            return new GroundskeeperCard();
        case Guardian:
            return new GuardianCard();
        case Guide:
            return new GuideCard();
        case Haggler:
            return new HagglerCard();
        case Hamlet:
            return new HamletCard();
        case Harbinger:
            return new HarbingerCard();
        case Farm:
            return new FarmCard();
        case Harvest:
            return new HarvestCard();
        case Haunted_Castle:
            return new Haunted_CastleCard();
        case Haunted_Mirror:
            return new Haunted_MirrorCard();
        case Haunted_Woods:
            return new Haunted_WoodsCard();
        case Haunting:
            return new HauntingCard();
        case Haven:
            return new HavenCard();
        case Herald:
            return new HeraldCard();
        case Herbalist:
            return new HerbalistCard();
        case Hermit:
            return new HermitCard();
        case Hero:
            return new HeroCard();
        case Hideout:
            return new HideoutCard();
        case Highway:
            return new HighwayCard();
        case Hireling:
            return new HirelingCard();
        case Hoard:
            return new HoardCard();
        case Horn_of_Plenty:
            return new Horn_of_PlentyCard();
        case Horse:
            return new HorseCard();
        case Horse_Traders:
            return new Horse_TradersCard();
        case Hostelry:
            return new HostelryCard();
        case Hovel:
            return new HovelCard();
        case Humble_Castle:
            return new Humble_CastleCard();
        case Hunter:
            return new HunterCard();
        case Hunting_Grounds:
            return new Hunting_GroundsCard();
        case Hunting_Lodge:
            return new Hunting_LodgeCard();
        case Hunting_Party:
            return new Hunting_PartyCard();
        case Idol:
            return new IdolCard();
        case Ill_Gotten_Gains:
            return new Ill_Gotten_GainsCard();
        case Inheritance:
            return new InheritanceCard();
        case Inn:
            return new InnCard();
        case Innkeeper:
            return new InnkeeperCard();
        case Inventor:
            return new InventorCard();
        case Invest:
            return new InvestCard();
        case Imp:
            return new ImpCard();
        case Imperial_Envoy:
            return new Imperial_EnvoyCard();
        case Importer:
            return new ImporterCard();
        case Improve:
            return new ImproveCard();
        case Investment:
            return new InvestmentCard();
        case Ironmonger:
            return new IronmongerCard();
        case Ironworks:
            return new IronworksCard();
        case Island:
            return new IslandCard();
        case Jack_of_all_Trades:
            return new Jack_of_all_TradesCard();
        case Jester:
            return new JesterCard();
        case Journeyman:
            return new JourneymanCard();
        case Junk_Dealer:
            return new Junk_DealerCard();
        case Kiln:
            return new KilnCard();
        case King$s_Castle:
            return new King$s_CastleCard();
        case King$s_Court:
            return new King$s_CourtCard();
        case Kintsugi:
            return new KintsugiCard();
        case Laboratory:
            return new LaboratoryCard();
        case Lackeys:
            return new LackeysCard();
        case Landing_Party:
            return new Landing_PartyCard();
        case Legionary:
            return new LegionaryCard();
        case Leprechaun:
            return new LeprechaunCard();
        case Library:
            return new LibraryCard();
        case Lighthouse:
            return new LighthouseCard();
        case Litter:
            return new LitterCard();
        case Livery:
            return new LiveryCard();
        case Loan:
            return new LoanCard();
        case Locusts:
            return new LocustsCard();
        case Longship:
            return new LongshipCard();
        case Lookout:
            return new LookoutCard();
        case Lost_Arts:
            return new Lost_ArtsCard();
        case Lost_City:
            return new Lost_CityCard();
        case Lucky_Coin:
            return new Lucky_CoinCard();
        case Lurker:
            return new LurkerCard();
        case Madman:
            return new MadmanCard();
        case Magic_Lamp:
            return new Magic_LampCard();
        case Magnate:
            return new MagnateCard();
        case Magpie:
            return new MagpieCard();
        case Mandarin:
            return new MandarinCard();
        case Marauder:
            return new MarauderCard();
        case Marchland:
            return new MarchlandCard();
        case Margrave:
            return new MargraveCard();
        case Market:
            return new MarketCard();
        case Market_Square:
            return new Market_SquareCard();
        case Masquerade:
            return new MasqueradeCard();
        case Mastermind:
            return new MastermindCard();
        case Masterpiece:
            return new MasterpieceCard();
        case Menagerie:
            return new MenagerieCard();
        case Merchant:
            return new MerchantCard();
        case Merchant_Camp:
            return new Merchant_CampCard();
        case Merchant_Guild:
            return new Merchant_GuildCard();
        case Merchant_Ship:
            return new Merchant_ShipCard();
        case Mercenary:
            return new MercenaryCard();
        case Messenger:
            return new MessengerCard();
        case Militia:
            return new MilitiaCard();
        case Mill:
            return new MillCard();
        case Mine:
            return new MineCard();
        case Minion:
            return new MinionCard();
        case Mining_Road:
            return new Mining_RoadCard();
        case Mining_Village:
            return new Mining_VillageCard();
        case Mint:
            return new MintCard();
        case Miser:
            return new MiserCard();
        case Misery:
            return new MiseryCard();
        case Mission:
            return new MissionCard();
        case Moat:
            return new MoatCard();
        case Monastery:
            return new MonasteryCard();
        case Moneylender:
            return new MoneylenderCard();
        case Monkey:
            return new MonkeyCard();
        case Monument:
            return new MonumentCard();
          case Mountain_Shrine:
            return new Mountain_ShrineCard();
        case Mountain_Village:
            return new Mountain_VillageCard();
        case Mountebank:
            return new MountebankCard();
        case Mystic:
            return new MysticCard();
        case Native_Village:
            return new Native_VillageCard();
        case Navigator:
            return new NavigatorCard();
        case Necromancer:
            return new NecromancerCard();
        case Necropolis:
            return new NecropolisCard();
        case Night_Watchman:
            return new Night_WatchmanCard();
        case Patrol:
            return new PatrolCard();
        case Patron:
            return new PatronCard();
        case Ninja:
            return new NinjaCard();
        case Noble_Brigand:
            return new Noble_BrigandCard();
        case Nomad_Camp:
            return new Nomad_CampCard();
        case Nobles:
            return new NoblesCard();
        case Oasis:
            return new OasisCard();
        case Old_Witch:
            return new Old_WitchCard();
        case Opulent_Castle:
            return new Opulent_CastleCard();
        case Oracle:
            return new OracleCard();
        case Outpost:
            return new OutpostCard();
        case Overgrown_Estate:
            return new Overgrown_EstateCard();
        case Overlord:
            return new OverlordCard();
        case Paddock:
            return new PaddockCard();
        case Page:
            return new PageCard();
        case Pathfinding:
            return new PathfindingCard();
        case Patrician:
            return new PatricianCard();
	    case Pawn:
	        return new PawnCard();
	    case Pearl_Diver:
	        return new Pearl_DiverCard();
        case Peasant:
            return new PeasantCard();
	    case Peddler:
	        return new PeddlerCard();
        case Philosopher$s_Stone:
            return new Philosopher$s_StoneCard();
        case Pilgrim:
            return new PilgrimCard();
        case Pilgrimage:
            return new PilgrimageCard();
        case Pillage:
            return new PillageCard();
        case Pirate:
            return new PirateCard();
        case Pirate_Ship:
            return new Pirate_ShipCard();
        case Pixie:
            return new PixieCard();
        case Plague:
            return new PlagueCard();
        case Plan:
            return new PlanCard();
        case Plaza:
            return new PlazaCard();
        case Plunder:
            return new PlunderCard();
        case Poacher:
            return new PoacherCard();
        case Poet:
            return new PoetCard();
        case Pooka:
            return new PookaCard();
        case Poor_House:
            return new Poor_HouseCard();
        case Port:
            return new PortCard();
        case Possession:
            return new PossessionCard();
        case Potion:
            return new PotionCard();
        case Pouch:
            return new PouchCard();
        case Poverty:
            return new PovertyCard();
        case Practice:
            return new PracticeCard();
        case Prepare:
            return new PrepareCard();
        case Priest:
            return new PriestCard();
        case Prince:
            return new PrinceCard();
        case Princess:
            return new PrincessCard();
        case Procession:
            return new ProcessionCard();
        case Province:
            return new ProvinceCard();
        case Quarry:
            return new QuarryCard();
        case Quartermaster:
            return new QuartermasterCard();
        case Quest:
            return new QuestCard();
        case Rabble:
            return new RabbleCard();
        case Raid:
            return new RaidCard();
        case Raider:
            return new RaiderCard();
        case Ranger:
            return new RangerCard();
        case Ratcatcher:
            return new RatcatcherCard();
        case Rats:
            return new RatsCard();
        case Raze:
            return new RazeCard();
        case Reap:
            return new ReapCard();
        case Rebuild:
            return new RebuildCard();
        case Recruiter:
            return new RecruiterCard();
        case Relic:
            return new RelicCard();
        case Remake:
            return new RemakeCard();
        case Remodel:
            return new RemodelCard();
        case Replace:
            return new ReplaceCard();
        case Research:
            return new ResearchCard();
        case Rice:
            return new RiceCard();
        case Ritual:
            return new RitualCard();
        case Riverboat:
            return new RiverboatCard();
        case Rocks:
            return new RocksCard();
        case Rogue:
            return new RogueCard();
        case Ronin:
            return new RoninCard();
        case Root_Cellar:
            return new Root_CellarCard();
        case Royal_Blacksmith:
            return new Royal_BlacksmithCard();
        case Royal_Carriage:
            return new Royal_CarriageCard();
        case Royal_Galley:
            return new Royal_GalleyCard();
        case Royal_Seal:
            return new Royal_SealCard();
        case Ruined_Market:
            return new Ruined_MarketCard();
        case Ruined_Library:
            return  new Ruined_LibraryCard();
        case Ruined_Village:
            return new Ruined_VillageCard();
        case Rush:
            return new RushCard();
        case Rustic_Village:
            return new Rustic_VillageCard();
        case Saboteur:
            return new SaboteurCard();
        case Sacred_Grove:
            return new Sacred_GroveCard();
        case Sacrifice:
            return new SacrificeCard();
        case Sage:
            return new SageCard();
        case Sailor:
            return new SailorCard();
        case Salt_the_Earth:
            return new Salt_the_EarthCard();
        case Salvager:
            return new SalvagerCard();
        case Samurai:
            return new SamuraiCard();
        case Save:
            return new SaveCard();
        case Sanctuary:
            return new SanctuaryCard();
        case Sauna:
            return new SaunaCard();
        case Scavenger:
            return new ScavengerCard();
        case Scepter:
            return new ScepterCard();
        case Scheme:
            return new SchemeCard();
        case Scholar:
            return new ScholarCard();
        case Scout:
            return new ScoutCard();
        case Scouting_Party:
            return new Scouting_PartyCard();
        case Scrap:
            return new ScrapCard();
        case Scrying_Pool:
            return new Scrying_PoolCard();
        case Sculptor:
            return new SculptorCard();
        case Sea_Chart:
            return new Sea_ChartCard();
        case Sea_Hag:
            return new Sea_HagCard();
        case Sea_Witch:
            return new Sea_WitchCard();
        case Seaway:
            return new SeawayCard();
        case Sentinel:
            return new SentinelCard();
        case Secret_Cave:
            return new Secret_CaveCard();
        case Secret_Chamber:
            return new Secret_ChamberCard();
        case Secret_Passage:
            return new Secret_PassageCard();
        case Seer:
            return new SeerCard();
        case Sentry:
            return new SentryCard();
        case Settlers:
            return new SettlersCard();
        case Sewers:
            return new SewersCard();
        case Shaman:
            return new ShamanCard();
        case Shanty_Town:
            return new Shanty_TownCard();
        case Shepherd:
            return new ShepherdCard();
        case Sheepdog:
            return new SheepdogCard();
        case Shop:
            return new ShopCard();
        case Silk_Merchant:
            return new Silk_MerchantCard();
        case Silk_Road:
            return new Silk_RoadCard();
        case Silos:
            return new SilosCard();
        case Sinister_Plot:
            return new Sinister_PlotCard();
        case Silver:
            return new SilverCard();
        case Sir_Bailey:
            return new Sir_BaileyCard();
        case Sir_Destry:
            return new Sir_DestryCard();
        case Sir_Martin:
            return new Sir_MartinCard();
        case Sir_Michael:
            return new Sir_MichaelCard();
        case Sir_Vander:
            return new Sir_VanderCard();
        case Siren:
            return new SirenCard();
        case Skulk:
            return new SkulkCard();
        case Sleigh:
            return new SleighCard();
        case Small_Castle:
            return new Small_CastleCard();
          case Small_Potatoes:
              return new Small_PotatoesCard();
        case Smithy:
            return new SmithyCard();
        case Smugglers:
            return new SmugglersCard();
        case Snake_Witch:
            return new Snake_WitchCard();
        case Snowy_Village:
            return new Snowy_VillageCard();
        case Soldier:
            return new SoldierCard();
        case Soothsayer:
            return new SoothsayerCard();
        case Souk:
            return new SoukCard();
        case Spice_Merchant:
            return new Spice_MerchantCard();
        case Spices:
            return new SpicesCard();
        case Spoils:
            return new SpoilsCard();
        case Sprawling_Castle:
            return new Sprawling_CastleCard();
        case Spy:
            return new SpyCard();
        case Squire:
            return new SquireCard();
        case Stables:
            return new StablesCard();
        case Stampede:
            return new StampedeCard();
        case Stash:
            return new StashCard();
        case Steward:
            return new StewardCard();
        case Stockpile:
            return new StockpileCard();
        case Stonemason:
            return new StonemasonCard();
        case Storeroom:
            return new StoreroomCard();
        case Storyteller:
            return new StorytellerCard();
        case Summon:
            return new SummonCard();
        case Supplies:
            return new SuppliesCard();
        case Survivors:
            return new SurvivorsCard();
        case Swamp_Hag:
            return new Swamp_HagCard();
        case Swamp_Shacks:
            return new Swamp_ShacksCard();
        case Swashbuckler:
            return new SwashbucklerCard();
        case Swindler:
            return new SwindlerCard();
        case Sycophant:
            return new SycophantCard();
        case Tactician:
            return new TacticianCard();
        case Talisman:
            return new TalismanCard();
        case Tanuki:
            return new TanukiCard();
        case Taskmaster:
            return new TaskmasterCard();
        case Tax:
            return new TaxCard();
        case Taxman:
            return new TaxmanCard();
        case Tea_House:
            return new Tea_HouseCard();
        case Teacher:
            return new TeacherCard();
        case Tent:
            return new TentCard();
        case Territory:
            return new TerritoryCard();
        case Temple:
            return new TempleCard();
        case The_Earth$s_Gift:
            return new The_Earth$s_GiftCard();
        case The_Field$s_Gift:
            return new The_Field$s_GiftCard();
        case The_Flame$s_Gift:
            return new The_Flame$s_GiftCard();
        case The_Forest$s_Gift:
            return new The_Forest$s_GiftCard();
        case The_Moon$s_Gift:
            return new The_Moon$s_GiftCard();
        case The_Mountain$s_Gift:
            return new The_Mountain$s_GiftCard();
        case The_River$s_Gift:
            return new The_River$s_GiftCard();
        case The_Sea$s_Gift:
            return new The_Sea$s_GiftCard();
        case The_Sky$s_Gift:
            return new The_Sky$s_GiftCard();
        case The_Sun$s_Gift:
            return new The_Sun$s_GiftCard();
        case The_Swamp$s_Gift:
            return new The_Swamp$s_GiftCard();
        case The_Wind$s_Gift:
            return new The_Wind$s_GiftCard();
        case Thief:
            return new ThiefCard();
        case Throne_Room:
            return new Throne_RoomCard();
        case Tiara:
            return new TiaraCard();
        case Tide_Pools:
            return new Tide_PoolsCard();
        case Tools:
            return new ToolsCard();
        case Tormentor:
            return new TormentorCard();
        case Torturer:
            return new TorturerCard();
        case Tournament:
            return new TournamentCard();
        case Tracker:
            return new TrackerCard();
        case Trade:
            return new TradeCard();
        case Trader:
            return new TraderCard();
        case Trade_Route:
            return new Trade_RouteCard();
        case Trading_Post:
            return new Trading_PostCard();
        case Tragic_Hero:
            return new Tragic_HeroCard();
        case Trail:
            return new TrailCard();
        case Training:
            return new TrainingCard();
        case Transmogrify:
            return new TransmogrifyCard();
        case Transmute:
            return new TransmuteCard();
        case Travelling_Fair:
            return new Travelling_FairCard();
        case Treasure_Hunter:
            return new Treasure_HunterCard();
        case Treasure_Map:
            return new Treasure_MapCard();
        case Treasure_Trove:
            return new Treasure_TroveCard();
        case Treasurer:
            return new TreasurerCard();
        case Treasury:
            return new TreasuryCard();
        case Tribute:
            return new TributeCard();
        case Triumph:
            return new TriumphCard();
        case Trusty_Steed:
            return new Trusty_SteedCard();
        case Tunnel:
            return new TunnelCard();
        case UberDomination:
            return new UberDominationCard();
        case Underling:
            return new UnderlingCard();
        case University:
            return new UniversityCard();
        case Upgrade:
            return new UpgradeCard();
        case Urchin:
            return new UrchinCard();
        case Vagrant:
            return new VagrantCard();
        case Vampire:
           return new VampireCard();
        case Vassal:
            return new VassalCard();
        case Vault:
            return new VaultCard();
        case Venture:
            return new VentureCard();
        case Villa:
            return new VillaCard();
        case Village:
            return new VillageCard();
        case Village_Green:
            return new Village_GreenCard();
        case Villain:
            return new VillainCard();
        case Vineyard:
            return new VineyardCard();
        case Walled_Village:
            return new Walled_VillageCard();
        case Wandering_Minstrel:
            return new Wandering_MinstrelCard();
        case War:
            return new WarCard();
        case Warehouse:
            return new WarehouseCard();
        case Warrior:
            return new WarriorCard();
          case Watchtower:
              return new WatchtowerCard();
          case Wayfarer:
              return new WayfarerCard();
        case Weaver:
            return new WeaverCard();
        case Wedding:
            return new WeddingCard();
        case Werewolf:
            return new WerewolfCard();
        case Wharf:
            return new WharfCard();
        case Wild_Hunt:
            return new Wild_HuntCard();
        case Will_o$_Wisp:
            return new Will_o$_WispCard();
        case Windfall:
            return new WindfallCard();
        case Wine_Merchant:
            return new Wine_MerchantCard();
        case Wish:
            return new WishCard();
        case Wishing_Well:
            return new Wishing_WellCard();
        case Witch:
            return new WitchCard();
        case Witch$s_Hut:
            return new Witch$s_HutCard();
        case Woodcutter:
            return new WoodcutterCard();
        case Worker$s_Village:
            return new Worker$s_VillageCard();
        case Workshop:
            return new WorkshopCard();
        case Young_Witch:
            return new Young_WitchCard();
        case Zombie_Apprentice:
            return new Zombie_ApprenticeCard();
        case Zombie_Mason:
            return new Zombie_MasonCard();
        case Zombie_Spy:
            return new Zombie_SpyCard();
        case ZZZtest:
            return new ZZZtestCard();
        default:
        return new DomCard( this );
    }
    }

    public DomCost getCost() {
      return cost;
    }

    public HashSet<DomCardType> types() {
      return types;
    }

    /**
     * Returns true iff this card has {@code aCardType}.
     * @return true iff this card has {@code aCardType}
     */
    public boolean hasCardType( DomCardType aCardType ) {
      return types.contains( aCardType);
    }

    public DomPlayStrategy[] getPlayStrategies() {
    	ArrayList<DomPlayStrategy> theStrategies = new ArrayList<DomPlayStrategy>();
    	switch (this) {
		case Ambassador:
            theStrategies.add(DomPlayStrategy.ambassadorWar);
			theStrategies.add(DomPlayStrategy.aggressiveTrashing);
			break;

        case Amulet:
            theStrategies.add(DomPlayStrategy.silverGainer);
            theStrategies.add(DomPlayStrategy.aggressiveTrashing);
            break;

    	case Apothecary:
			theStrategies.add(DomPlayStrategy.ApothecaryNativeVillage);
			break;

        case Bauble:
            theStrategies.add(DomPlayStrategy.PlateauShepherds);
            break;

		case Chapel:
            theStrategies.add(DomPlayStrategy.aggressiveTrashing);
            theStrategies.add(DomPlayStrategy.keepPayload);
			break;

        case Duplicate:
            theStrategies.add(DomPlayStrategy.dukeEnabler);
            break;

		case Governor:
			theStrategies.add(DomPlayStrategy.GoldEarlyTrashMid);
			break;

        case Hermit:
            theStrategies.add(DomPlayStrategy.MarketSquareCombo);
            theStrategies.add(DomPlayStrategy.bigTurnBridge);
            break;

        case Lurker:
            theStrategies.add(DomPlayStrategy.playOnlyIfGaining);
            break;

        case Mine:
            theStrategies.add(DomPlayStrategy.mineCopperFirst);
            break;

        case Mining_Village:
            theStrategies.add(DomPlayStrategy.forEngines);
            break;

        case Miser:
            theStrategies.add(DomPlayStrategy.removeAllCoppers);
            break;

        case Mystic:
            theStrategies.add(DomPlayStrategy.goodDeckTracker);
            theStrategies.add(DomPlayStrategy.greedyDeckTracker);
            break;

		case Native_Village:
			theStrategies.add(DomPlayStrategy.bigTurnBridge);
			theStrategies.add(DomPlayStrategy.bigTurnGoons);
			theStrategies.add(DomPlayStrategy.ApothecaryNativeVillage);
			break;

        case Necropolis:
            theStrategies.add(DomPlayStrategy.trashWhenObsolete);
            break;

        case Peddler:
            theStrategies.add(DomPlayStrategy.combo);
            break;

		case Pirate_Ship:
			theStrategies.add(DomPlayStrategy.attackUntil5Coins);
			break;

        case Ratcatcher:
            theStrategies.add(DomPlayStrategy.aggressiveTrashing);
            break;

        case Rats:
            theStrategies.add(DomPlayStrategy.DominateTraining);
            break;

        case Royal_Carriage:
            theStrategies.add(DomPlayStrategy.bigTurnBridge);
            break;

        case Silver:
            theStrategies.add(DomPlayStrategy.trashWhenObsolete);
            break;

		case Smithy:
			theStrategies.add(DomPlayStrategy.playIfNotBuyingTopCard);
			break;

		case Spice_Merchant:
			theStrategies.add(DomPlayStrategy.FoolsGoldEnabler);
			break;

        case Squire:
            theStrategies.add(DomPlayStrategy.silverGainer);
            break;

        case Steward:
            theStrategies.add(DomPlayStrategy.modestTrashing);
            theStrategies.add(DomPlayStrategy.aggressiveTrashing);
            break;

        case Stonemason:
            theStrategies.add(DomPlayStrategy.combo);
            break;

        case Storeroom:
            theStrategies.add(DomPlayStrategy.cityQuarterCombo);
            theStrategies.add(DomPlayStrategy.crossroadsCombo);
            break;

		case Tactician:
			theStrategies.add(DomPlayStrategy.playIfNotBuyingTopCard);
			break;

        case Treasurer:
            theStrategies.add(DomPlayStrategy.aggressiveTrashing);
            break;

        case Wild_Hunt:
            theStrategies.add(DomPlayStrategy.forEngines);
            break;

        case Wishing_Well:
            theStrategies.add(DomPlayStrategy.goodDeckTracker);
            theStrategies.add(DomPlayStrategy.greedyDeckTracker);
            break;

		default:
			break;
		}
		theStrategies.add(0,DomPlayStrategy.standard);
        return theStrategies.toArray(new DomPlayStrategy[theStrategies.size()]);
    }

    /**
     * @return
     */
    public int getVictoryValue(DomPlayer aPlayer) {
      switch (this) {
		case Duke:
	  	    if (aPlayer != null ) {
	 	      return aPlayer.count(Duchy);
		    }
	        break;

		case Fairgrounds:
			if (aPlayer!=null) {
		      return aPlayer.countDifferentCardsInDeck()/5*2;
			}
		    break;

        case Feodum:
            if (aPlayer!=null) {
                return aPlayer.count(Silver)/3;
            }
            break;

        case Gardens:
            if (aPlayer != null ) {
               return aPlayer.countAllCards()/10;
            }
            break;

        case Humble_Castle:
           if (aPlayer != null ) {
               return aPlayer.count(DomCardType.Castle);
           }
           break;

        case King$s_Castle:
           if (aPlayer != null ) {
             return aPlayer.count(DomCardType.Castle)*2;
           }
           break;

        case Pasture:
           if (aPlayer != null ) {
               return aPlayer.count(Estate);
           }
           break;

        case Silk_Road:
           if (aPlayer != null ) {
              return aPlayer.count(DomCardType.Victory)/4;
           }
           break;

        case Territory:
              if (aPlayer!=null) {
                  return aPlayer.countDifferentCardsOfType(DomCardType.Victory);
              }
              break;

          case Vineyard:
	        if (aPlayer!=null) {
                return aPlayer.count(DomCardType.Action) / 3;
            }
		    break;

          case Marchland:
              if (aPlayer!=null) {
                  return aPlayer.count(DomCardType.Victory) / 3;
              }
              break;

 		default:
    	    break;
	  }
      return victoryValue;
    }

    /**
     * @return
     */
    public int getCoinValue() {
      return coinValue;
    }

    /**
     * @return
     */
    public int getPlayPriority() {
      return playPriority;
    }

    /**
     * @param aActionsLeft
     * @return
     */
    public int getDiscardPriority(int aActionsLeft) {
      //TODO to review (warehouse draws village + Terminal...)
      if (aActionsLeft<1 && hasCardType( DomCardType.Action ) )
        return 1;
      return discardPriority;
    }

    /**
     * @return
     */
    public int getTrashPriority() {
      return getDiscardPriority( 1 );
    }

    public String toString(){
      String theString = super.toString().replaceAll( "_", " " ).replaceAll( "\\$", "'" );
      if (this==DomCardName.Ill_Gotten_Gains)
    	  return "Ill-Gotten Gains";
      if (this==DomCardName.Will_o$_Wisp)
          return "Will-o'-Wisp";
	  return theString;
    }

    public final int getCoinCost(DomPlayer player) {
        if (hasCardType(DomCardType.Event) || hasCardType(DomCardType.Project))
            return getCost().getCoins();
        int theCoins = getCost().getCoins();
        if (player!=null ) {
          if (this==DomCardName.Peddler && player.getCurrentGame().isBuyPhase()) {
            theCoins-=player.getCurrentGame().countActionsInPlay()*2;
          }
          if (this==DomCardName.Destrier ) {
            theCoins-=player.getCurrentGame().getActivePlayer().getCardsGainedLastTurn().size();
          }
          if (this==DomCardName.Fisherman && player==player.getCurrentGame().getActivePlayer() && player.getDeck().getDiscardPile().isEmpty()) {
            theCoins-=3;
          }
          if (this== DomCardName.Province) {
              theCoins-=3*player.getSmallPotatoesPlayed();
          }
          if (player.getCurrentGame().getBoard().getActiveProphecy() == DomCardName.Flourishing_Trade
               && player.getCurrentGame().getBoard().getProphecyCount()==0){
              theCoins--;
          }
          theCoins-=player.getCurrentGame().getBridgesPlayed();
          theCoins-=player.getCurrentGame().getBridgetrollPlayed();
          theCoins-=player.getCurrentGame().getInventorsPlayed();
          theCoins-=player.getCurrentGame().getPrincessesInPlay()*2;
          if (hasCardType(DomCardType.Action))
            theCoins-=player.getCurrentGame().getQuarriesInPlay()*2;
          theCoins-=player.getCurrentGame().getHighwaysInPlay();
          if (player.getCurrentGame().getActivePlayer()!=null) {
              theCoins -= player.getCurrentGame().getActivePlayer().getMinus$2TokenOn() == isFromPile() ? 2 : 0;
              if (player.getCurrentGame().getActivePlayer().hasBuiltProject(Canal))
                  theCoins--;
          }
          if (this==DomCardName.Wayfarer && !player.getCardsGainedLastTurn().isEmpty() && player.getLastGainedCardNotWayfarer()!=null) {
              return player.getLastGainedCardNotWayfarer().getCoinCost(player);
          }

        }
        return theCoins<0 ? 0 : theCoins;
    }

    /**
     * @return
     */
    public final DomCost getCost( DomGame aDomGame ) {
      DomCost theCost = new DomCost(getCoinCost(aDomGame.getActivePlayer()), getPotionCost());
      theCost.setDebt(cost.getDebt());
      return theCost;
    }

    /**
     * @return
     */
    public final int getPotionCost() {
      return getCost().getPotions();
    }

	public String getImageLocation() {
      StringBuilder theLocation = new StringBuilder();
      theLocation.append("images/");
      theLocation.append(super.toString()).append(".jpg");
      return theLocation.toString().toLowerCase().replaceAll( "_", "" ).replaceAll( "\\$", "" );
	}

	public int getTrashPriority(DomPlayer player) {
		//trash priorities depend on the owner of the card which is unknown in this enum
		//so we quickly make a DomCard object and assign it to the player
		//this way we get a correct trash priority for that player
		DomCard theIntermediateCard = createNewCardInstance();
		theIntermediateCard.owner=player.getPossessor()==null ? player : player.getPossessor();
		return theIntermediateCard.getTrashPriority();
	}

	public static DomCardName[] getPossibleBaneCards() {
	    ArrayList<DomCardName> possibleBanes = new ArrayList<DomCardName>();
		for (DomCardName cardName : values()) {
			if (cardName.getCost().customCompare(new DomCost(2, 0))==0
			 || cardName.getCost().customCompare(new DomCost(3, 0))==0){
				if (cardName.hasCardType(DomCardType.Kingdom))
			  	  possibleBanes.add(cardName);
			}
		}
        return possibleBanes.toArray(new DomCardName[possibleBanes.size()]);
	}

	public String toHTML() {
      String theString = toString();
      if (DomEngine.showColoredLog) {
          if (hasCardType(DomCardType.Curse))
              return "<FONT style=\"BACKGROUND-COLOR: #CC8BC7\">" + theString + "</FONT>";
          if (hasCardType(DomCardType.Treasure) && hasCardType(DomCardType.Victory))
              return "<FONT style=\"BACKGROUND-COLOR: #A9E96E\">" + theString + "</FONT>";
          if (hasCardType(DomCardType.Action) && hasCardType(DomCardType.Victory))
              return "<FONT style=\"BACKGROUND-COLOR: #6EE9C2\">" + theString + "</FONT>";
          if (hasCardType(DomCardType.Reaction) && hasCardType(DomCardType.Treasure))
              return "<FONT style=\"BACKGROUND-COLOR: #CCFF99\">" + theString + "</FONT>";
          if (hasCardType(DomCardType.Reaction))
              return "<FONT style=\"BACKGROUND-COLOR: #91A4FC\">" + theString + "</FONT>";
          if (hasCardType(DomCardType.Treasure))
              return "<FONT style=\"BACKGROUND-COLOR: #F3F584\">" + theString + "</FONT>";
          if (hasCardType(DomCardType.Victory))
              return "<FONT style=\"BACKGROUND-COLOR: #8EBF75\">" + theString + "</FONT>";
          if (hasCardType(DomCardType.Duration))
              return "<FONT style=\"BACKGROUND-COLOR: #F88017\">" + theString + "</FONT>";
          if (hasCardType(DomCardType.Action))
              return "<FONT style=\"BACKGROUND-COLOR: #D9D9D9 \">" + theString + "</FONT>";
          if (hasCardType(DomCardType.Event))
              return "<FONT style=\"BACKGROUND-COLOR: #996633 \">" + theString + "</FONT>";
          if (hasCardType(DomCardType.Landmark))
              return "<FONT style=\"BACKGROUND-COLOR: #339966 \">" + theString + "</FONT>";
          if (hasCardType(DomCardType.Project))
              return "<FONT style=\"BACKGROUND-COLOR: #F47983 \">" + theString + "</FONT>";
          if (hasCardType(DomCardType.Night))
              return "<FONT style=\"BACKGROUND-COLOR: #808080 \">" + theString + "</FONT>";
          if (hasCardType(DomCardType.Ally))
              return "<FONT style=\"BACKGROUND-COLOR: #e3e0cd \">" + theString + "</FONT>";
          if (hasCardType(DomCardType.Prophecy))
              return "<FONT style=\"BACKGROUND-COLOR: #41fdfe \">" + theString + "</FONT>";

      }
      return theString;
	}

	public DomSet getSet() {
		for (DomSet set : DomSet.values()){
			if (set.contains(this))
				return set;
		}
		return null;
	}

	public URL getImageURL() {
//		return null;
		//TODO this should be called in some other way
		return new EscapeDialog().getClass().getResource(getImageLocation());
	}
	public String getCompleteImageLocation() {
//		return null;
		//TODO this should be called in some other way
//		return new EscapeDialog().getClass().getResource(getImageLocation());
		return  "C:/Documents and Settings/djag492/My Documents/Jeroen/Dominion/" + getImageLocation();
//		return  "C:/Users/MEDION/Pictures/" + getImageLocation();
	}

	public static DomCardName[] getKingdomCards() {
		ArrayList<DomCardName> theCards = new ArrayList<DomCardName>();
		for (DomCardName cardName : values()){
			if (!DomSet.Base.contains(cardName))
				theCards.add(cardName);
		}
        return theCards.toArray(new DomCardName[theCards.size()]);
	}

	public int getOrderInBuyRules(DomPlayer owner) {
		int i=0;
		if (owner==null)
			return 1000;
		for (DomBuyRule rule : owner.getBuyRules()){
		  if (rule.getCardToBuy()==this && owner.wantsToGainOrKeep(this)){
		     return i;
		  }
		  i++;
		}
		return 1000;
	}

    public int getDebtCost() {
        return getCost().getDebt();
    }

    public DomCardName isFromPile() {
        if (hasCardType(DomCardType.Split_Pile) ) {
            switch (this) {
                case Settlers:
                case Bustling_Village:
                    return DomCardName.Settlers;
                case Catapult:
                case Rocks:
                    return DomCardName.Catapult;
                case Patrician:
                case Emporium:
                    return DomCardName.Patrician;
                case Encampment:
                case Plunder:
                    return DomCardName.Encampment;
                case Gladiator:
                case Fortune:
                    return DomCardName.Gladiator;
                case Sauna:
                case Avanto:
                    return DomCardName.Sauna;
                default:
                	System.err.println("Attempted to determine the pile of the split pile card \"" + toString() + "\" that isn't on the recognized list of split pile cards!");
            }
        }

        if (hasCardType(DomCardType.Ruins))
            return DomCardName.Ruins;

        if (hasCardType(DomCardType.Knight))
            return DomCardName.Knights;

        if (hasCardType(DomCardType.Castle))
            return DomCardName.Castles;

        return this;
    }

    public static DomCardName getRandomKingdomCard(HashSet<DomSet> aValidSets) {
        Random theRand = new Random();
        List<DomCardName> theValues = Arrays.asList(values());
        DomCardName theCard=null;
        while (theCard==null||!theCard.hasCardType(DomCardType.Kingdom)||!aValidSets.contains(theCard.getSet())) {
            theCard = theValues.get(theRand.nextInt(theValues.size()));
        }
        return theCard;
    }

    public String getImageLink() {
        return "Not supported anymore";
    }

    public static DomCardName getRandomCardShapedThing(HashSet<DomSet> aValidSets) {
        Random theRand = new Random();
        List<DomCardName> theValues = Arrays.asList(values());
        DomCardName theCard=null;
        while (theCard==null||(!theCard.hasCardType(DomCardType.Event)&&!theCard.hasCardType(DomCardType.Landmark)&&!theCard.hasCardType(DomCardType.Project)) ||!aValidSets.contains(theCard.getSet())) {
            theCard = theValues.get(theRand.nextInt(theValues.size()));
        }
        return theCard;
    }

    public boolean sharesTypeWith(DomCard card) {
        for (DomCardType theType : types) {
            if (theType.isLegal() && card.hasCardType(theType))
                return true;
        }
        return false;
    }
}