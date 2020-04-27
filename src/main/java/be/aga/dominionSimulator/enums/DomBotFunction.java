package be.aga.dominionSimulator.enums;

public enum DomBotFunction {
   constant,
    countCardsInDeck,
    countCardsInDeckNoMats,
   countCardTypeInDeck,
   countCardsInSupply,
   gainsNeededToEndGame,
   countAllCardsInDeck,
   countAvailableMoney,
   countTurns,
   isActionPhase,
   countEmptyPiles,
   countBuysLeft,
   getTotalMoney,
   actionsLeft,
   isPlusOneBuyTokenSet,
   isPlusOneCardTokenSet,
   isPlusOneActionTokenSet,
   isPlusOneCoinTokenSet,
   isMinus$2TokenSet,
   isEstateTokenPlaced,
   isTrashingTokenPlaced,
   countCardsLeftInDrawDeck,
   countCardsLeftInSmallestPile,
   countCardsInPlay,
   countCardsInHand,
   countAllCardsInOpponentsDeck,
   countCardsInOpponentsDecks,
   countVP,
   countMAXOpponentVP,
   tokensOnDefiledShrine,
    getTotalMoneyExcludingMats,
   countOnTavernMat, isSwampHagActive,
   isTravellingFairActive,
   countGainedCards,
   countVPon,
   countVillagers,
   hasFlag,
   hasBuiltProject,
    doesNotSpendCoffers;

   public String toString() {
       switch ( this ) {
       case countVP :
           return "count VP";

       case countMAXOpponentVP :
           return "Highest VP among opponents";

       case constant :
           return "the number";

       case getTotalMoney :
           return "total $ in deck";

       case getTotalMoneyExcludingMats:
           return "total $ in deck (exclude mats)";

           case countCardsInDeck :
               return "count in deck";

           case countCardsInDeckNoMats:
               return "count in deck (no mats)";

           case countCardsInSupply :
           return "count in supply";

       case gainsNeededToEndGame:
           return "#buys/gains needed to end game";
 
       case countCardTypeInDeck:
           return "count Type in deck";

       case countAllCardsInDeck:
           return "count all cards in deck";

       case countAvailableMoney :
           return "available $";

       case countTurns :
           return "current turn#";

       case isActionPhase :
           return "still in action phase (1=true)";

       case countBuysLeft:
           return "#buys left";

       case countCardsLeftInDrawDeck:
           return "cards left in draw deck";

       case countCardsInPlay:
           return "count in play";

       case countCardsInHand:
           return "count in hand";

       case countEmptyPiles:
           return "empty piles in supply";
       
       case countCardsLeftInSmallestPile:
           return "cards in smallest supply pile";

       case countCardsInOpponentsDecks :
           return "count in all opponents' decks";

       case countAllCardsInOpponentsDeck:
           return "count all cards in opponent's deck";

       case countGainedCards:
           return "count gained cards";

       case countVPon:
           return "count VP on";

       case countVillagers:
           return  "count Villagers";

       case hasFlag:
           return "has flag (1=true)";

       case hasBuiltProject:
           return "has Built Project (1=true)";

       case doesNotSpendCoffers:
           return "does not spend Coffers";

       default :
        return super.toString();
    }
   };
}