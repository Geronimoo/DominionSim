package be.aga.dominionSimulator.enums;

public enum DomBotType {
   Favorite,
   Generated,
   UserCreated,
   BigMoney,
   Attacking,
   Engine,
   Combo, 
   SingleCard, 
   Province,
   Colony,
   TwoPlayer,
   ThreePlayer,
   FourPlayer,
   AnnotatedGames,
   Competitive,
   Casual,
   Fun,
   Optimized,
   AppliesPPR,
   ShuffleOverhand,
   RiffleShuffle
   
   ;
   
   public String toString() {
       switch ( this ) {
       case SingleCard:
           return "Single Card";

       case Generated:
           return "Computer generated";

       case UserCreated:
           return "Created by user";

       case TwoPlayer:
           return "Two player";

       case ThreePlayer:
           return "Three player";

       case FourPlayer:
           return "Four player";

       case AnnotatedGames:
           return "Annotated Games";

       case BigMoney:
           return "Big Money";
           
       case Colony:
           return "Colony";

       case Combo:
           return "Combo";

       case Engine:
           return "Engine";
   
       case AppliesPPR:
           return "Applies PPR";

       case ShuffleOverhand:
           return "Shuffles Overhand";

       case RiffleShuffle:
           return "Riffle Shuffles";

    default :
        return super.toString();
    }
       
   };
   
}
