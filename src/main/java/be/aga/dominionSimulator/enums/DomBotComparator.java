package be.aga.dominionSimulator.enums;

public enum DomBotComparator {
   equalTo,
   smallerThan,
   smallerOrEqualThan,
   greaterThan,
   greaterOrEqualThan,
   
   ;
   
   public String toString() {
       switch ( this ) {
       case equalTo:
           return "=";
           
       case greaterOrEqualThan :
           return ">=";

       case greaterThan :
           return ">";

       case smallerOrEqualThan :
           return "<=";

       case smallerThan :
           return "<";

    default :
        return "=";
    }
       
   };
   
}
