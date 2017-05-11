/**
 * Created by jeroena on 20/04/2017.
 */
public enum Cow  {
    grey(1),
    black(2),
    white(2),
    green(2),
    blue(3),
    yellow(3),
    red(3),
    brown(4),
    purple(5);

    private final int value;

    Cow(int i) {
        value=i;
    }

    public int getValue() {
        return value;
    }
}
