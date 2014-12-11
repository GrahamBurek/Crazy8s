package crazy8s.card;

/**
 * Creates a card for Crazy8s.
 * @author Graham Burek, Makai Campbell
 */
public class Card implements ICard {
    public final static int CLUBS = 1;
    public final static int DIAMONDS = 2;
    public final static int HEARTS = 3;
    public final static int SPADES = 4;
    
    int rank;
    int suit;

    public static void main(String[] args) {
        Card card = new Card(2, 1);
        System.out.println(card);

        Card card2 = new Card(11, 2);
        System.out.println(card2);

        Card card3 = new Card(1, 4);
        System.out.println(card3);

    }

    /**
     * Constructor.
     * @param rank Rank of the card.
     * @param suit Suit of the Card.
     */
    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public int getRank() {
        return rank;
    }

    @Override
    public int getSuit() {
        return suit;
    }
    
    /**
     * Sets the suit of a card.
     * @param suit The suit to set.
     */
    public void setSuit(int suit){
        this.suit = suit;
    }

    @Override
    public String toString() {
        return decodeRank() + " of " + decodeSuit();
    }

    /**
     * A String representation of a rank.
     * @return The String representation.
     */
    public String decodeRank() {
        if (rank == 1) {
            return "Ace";
        }

        if (rank == 11) {
            return "Jack";
        }

        if (rank == 12) {
            return "Queen";
        }

        if (rank == 13) {
            return "King";
        }

        if ((rank >= 2) && (rank <= 10)) {
            return rank + "";
        }

        return "";
    }

    /**
     * A String representation of a suit.
     * @return String of a suit.
     */
    public String decodeSuit() {
        if (suit == CLUBS) {
            return "Clubs";
        }

        if (suit == DIAMONDS) {
            return "Diamonds";
        }

        if (suit == HEARTS) {
            return "Hearts";
        }

        if (suit == SPADES) {
            return "Spades";
        }

        return "";
    }
}
