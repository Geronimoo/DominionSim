import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jeroena on 20/04/2017.
 */
public class Deck extends ArrayList<Cow> {

    private ArrayList<Cow> discard = new ArrayList<Cow>();

    public void shuffle() {
        addAll(discard);
        discard.clear();
        Collections.shuffle(this);
    }

    public Cow draw() {
        if (isEmpty()) {
            shuffle();
        }
        if (isEmpty())
            return null;
        return remove(0);
    }

    public void discard(Cow cow) {
        discard.add(cow);
    }
}
