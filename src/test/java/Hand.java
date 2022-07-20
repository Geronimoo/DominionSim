import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by jeroena on 20/04/2017.
 */
public class Hand extends ArrayList<Cow> {
    private int value;

    public int getValue() {
        HashSet<Cow> theUniques = new HashSet<Cow>();
        for (Cow theCow:this) {
            theUniques.add(theCow);
        }
        int theSum = 0;
        for (Cow theCow:theUniques){
            theSum+=theCow.getValue();
        }
        return theSum;
    }

    public Cow discardBadCard() {
        Collections.sort(this);
        for (Cow aCow:Cow.values()) {
            if (count(aCow)>1)
                return remove(indexOf(aCow));
        }
        for (Cow aCow:Cow.values()) {
            if (count(aCow)>0)
                return remove(indexOf(aCow));
        }
        return remove(0);
    }

    private int count(Cow aCow) {
        int teller=0;
        for (Cow theCow:this) {
            teller+=theCow==aCow?1:0;
        }
        return teller;
    }

    public boolean isDead() {
        int count=0;
        for (Cow card : this) {
            if (card==Cow.black)
                count++;
            if (count>1)
                return true;
        }
        return false;
    }
}
