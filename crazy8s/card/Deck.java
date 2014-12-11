package crazy8s.card;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a deck of Cards.
 *
 * @author Graham Burek and Makai Campbell
 */
public final class Deck {

    int rank;
    int suit;

    Random ran = new Random();
    ArrayList<Card> cards = new ArrayList<>();
    ArrayList<Card> discards = new ArrayList<>();

    /**
     * Constructor.
     */
    public Deck() {
        for (int r = 1; r <= 13; r++) {
            for (int s = 1; s <= 4; s++) {
                Card card = new Card(r, s);
                cards.add(card);
            }
        }
        this.shuffle();
    }

    /**
     * Draws a card from the deck.
     * @return The Card drawn from the deck.
     */
    public Card draw() {
        if (!(cards.isEmpty())) {

            Card card = cards.remove(0);
            return card;
        }
       
        this.discardToDraw();
        this.shuffle();
        Card card = cards.remove(0);
        return card;
    }

    /**
     * Discards a card from the deck.
     * @param card The card to discard.
     */
    public void discard(Card card) {
        discards.add(0, card);
        rank = card.rank;
        suit = card.suit;
    }
    
    /**
     * Adds discard pile to deck.
     */
    public void discardToDraw(){
        System.out.println("Reshuffling deck!");
         for (int i = discards.size() - 1; i > 0; i--) {
            Card card = discards.remove(i);
            cards.add(card);

        }
    }
    
    
/**
 * Gets discard rank.
 * @return Rank of discard card.
 */
    public int getDiscardRank() {
        return rank;
    }

    /**
     * Gets discard suit.
     * @return Suit of discard card. 
     */
    public int getDiscardSuit() {
        return suit;
    }
    
    /**
     * Gets the size of the discard pile
     * @return The amount of cards in the discard pile.
     */
    public int getDiscardSize(){
        return this.discards.size();
    }
    
    /**
     * Gets the size of the deck.
     * @return The amount of cards in the deck.
     */
     public int getDeckSize(){
        return this.cards.size();
    }

    /**
     * Sets discard suit
     * @param suit Suit to set.
     */
    public void setDiscardSuit(int suit) {
        this.suit = suit;
    }

    /**
     * Shuffles the deck.
     */
    public void shuffle() {
        for (int index = 0; index < cards.size(); index++) {
            Card card1 = cards.get(index);

            int lottery = ran.nextInt(cards.size());

            Card card2 = cards.get(lottery);

            cards.set(index, card2);

            cards.set(lottery, card1);
        }
    }

    /**
     * Converts me to String.
     * @return A String representation of Deck.
     */
    @Override
    public String toString() {
        String s = "";
        for (int index = 0; index <= 10; ++index) {
            Card card = cards.get(index);

            String t = card + "\n";

            s = s + (index + 1) + ". " + t;
        }
        return s;
    }

    public static void main(String[] args) {
        Deck deck = new Deck();
        System.out.println(deck);
    }
}
