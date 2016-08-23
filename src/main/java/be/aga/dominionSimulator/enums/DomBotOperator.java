package be.aga.dominionSimulator.enums;

public enum DomBotOperator {
   plus,
   minus,
   multiplyWith,
   divideBy,
   
   ;
   
   public String toString() {
       switch ( this ) {
       case plus :
           return "+";

       case minus :
           return "-";

       case multiplyWith:
           return "X";

       case divideBy:
           return "/";
       default :
          return "+";
       }
   }

}