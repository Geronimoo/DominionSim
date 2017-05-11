/**
 * Created by jeroena on 20/04/2017.
 */
public class GWT {

    private static final int NUMBER_OF_TRIALS = 10000;
    private static Deck deck;

    public static void main(String[] args) {
//        createNormalDeck();
        createCulledDeck();
        testDeck();
    }

    private static void createCulledDeck() {
        deck = new Deck();
        for (int i=0;i<5;i++)
            deck.add(Cow.grey);
        for (int i=0;i<2;i++) {
            deck.add(Cow.black);
            deck.add(Cow.green);
            deck.add(Cow.white);
        }
        deck.add(Cow.blue);
        deck.add(Cow.red);
        deck.add(Cow.yellow);
        deck.add(Cow.brown);
        deck.add(Cow.purple);
    }

    private static void testDeck() {

        int teller = 0;
        Hand hand = new Hand();
        for (int i=0;i<NUMBER_OF_TRIALS;i++) {
            deck.shuffle();
            for (int j=0;j<5;j++)
                hand.add(deck.draw());
            while (hand.getValue()<16) {
//                System.out.println("Hand Value = " + hand.getValue());
//                System.out.println(hand + " " +  hand.getValue());
                teller++;
                hand.add(deck.draw());
                hand.add(deck.draw());
//                System.out.println(hand);
                deck.discard(hand.discardBadCard());
//                System.out.println(hand);
                deck.discard(hand.discardBadCard());
//                System.out.println(hand);
            }
            deck.addAll(hand);
            hand.clear();
        }
        System.out.println("Average number of actions: " + teller*100/NUMBER_OF_TRIALS/100.0);
    }

    private static void createNormalDeck() {
        deck = new Deck();
        for (int i=0;i<5;i++)
            deck.add(Cow.grey);
        for (int i=0;i<3;i++) {
            deck.add(Cow.black);
            deck.add(Cow.green);
            deck.add(Cow.white);
        }
        deck.add(Cow.blue);
        deck.add(Cow.red);
        deck.add(Cow.yellow);
        deck.add(Cow.brown);
        deck.add(Cow.purple);
    }
}