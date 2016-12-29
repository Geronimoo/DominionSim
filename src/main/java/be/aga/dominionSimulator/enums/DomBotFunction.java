package be.aga.dominionSimulator.enums;

public enum DomBotFunction {
   constant,
   countCardsInDeck,
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
   countCardsInOpponentsDecks,
   countVP,
   countMAXOpponentVP,
   tokensOnDefiledShrine,
   getTotalMoneyExcludingNativeVillage,
   countOnTavernMat, isSwampHagActive,
   isTravellingFairActive,
   countGainedCards,
   countVPon;

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

       case getTotalMoneyExcludingNativeVillage :
           return "total $ in deck (exclude mats)";

       case countCardsInDeck :
           return "count in deck";

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

       case countGainedCards:
           return "count gained cards";

       case countVPon:
           return "count VP on";

    default :
        return super.toString();
    }
   };
}