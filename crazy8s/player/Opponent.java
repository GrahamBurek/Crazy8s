package crazy8s.player;

import crazy8s.card.Card;
import java.util.ArrayList;

/**
 * This class implements the opponent with AI.
 * @author Graham Burek and Makai Campbell
 */
public class Opponent implements IPlayer {
    ArrayList<Card> myhand;
    public Card discarded;
    private int suit8;
    private static int score;
    
    public Opponent() {
        this.myhand = new ArrayList<>( );
    }
    
    /**
     * Returns the index of the first card in this IPlayer's hand with a matching rank.
     * @param rank The rank to match
     * @return Returns index of first matching card.
     */
    public int searchRank(int rank) {
        for(int index=0; index < this.myhand.size(); index++) {
            Card card = myhand.get(index);
            if(rank == card.getRank() && card.getRank() != 8)
                return index;
        }
        return -1;
    }
    
     /**
     * Returns the index of the first card in this IPlayer's hand with a matching suit.
     * @param suit The suit to match.
     * @return 
     */
    public int searchSuit(int suit) {
        for(int index=0; index < this.myhand.size(); index++) {
            Card card = myhand.get(index);
            if(suit == card.getSuit() && card.getRank() != 8)
                return index;
        }
        return -1;
    }

    /**
     * Finds first 8 card in this IPlayer's hand.
     * @return The index of first 8 card.
     */
    public int search8s() {
        for(int index=0; index < this.myhand.size(); index++) {
            Card card = myhand.get(index);
            if(card.getRank() == 8)
                return index;
        }
        return -1;
    }
    
    /**
     * Chooses the most numerous suit in the IPlayer's hand.
     * @return An integer representing the most numerous suit.
     */
    protected int chooseSuit() {
        // Part A. Count suits
        int[] counts = new int[5];
        for(Card card : myhand) {
            int suit = card.getSuit();
            if(card.getRank() != 8) {
                int count = counts[suit];
                count++;
                counts[suit] = count;
//                counts[suit]++;
            }
        }
        
        // Part B. Find largest suit
        int maxCount = 0;
        int maxSuit = 0;
        for(int suit=1; suit < counts.length; suit++) {
            if(counts[suit] > maxCount) {
                maxCount = counts[suit];
                maxSuit = suit;
            }    
        }
        
        if(maxSuit == 0)
            return Card.CLUBS;
        
        return maxSuit;
    }
    
    @Override
    public int getCommand() {
        int suit = 0;
        
        if (discarded.getRank() != 8) {
            int index = searchRank(discarded.getRank());
            if (index != -1) {
                return index;
            }
        }
        else 
            suit = suit8;
        
        // If we get here, no rank matches
        if(suit == 0)
            suit = discarded.getSuit();
        
        int index = searchSuit(suit);
        if(index != -1) {
            return index;
        }
        
        index = search8s();
        if(index == -1) {
            return Command.DRAW;
        }
        
        return index;
    }

    @Override
    public ArrayList<Card> getHand() {
        return myhand;
    }

    @Override
    public void played(IPlayer player, Card card) {
        this.discarded = card;
    }
    
    @Override
     public void played(Card card) {
        this.discarded = card;
    }

    @Override
    public void drew(IPlayer player, Card card) {
        //does nothing
    }


    @Override
    public int getSuit() {
        return chooseSuit();
    }

    @Override
    public void setSuit(int suit) {
        this.suit8 = suit;
    }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }
    
    @Override
    public String toString() {
        return "OPPONENT";
    }
    
    @Override
    public Card getDiscard(){
        return this.discarded;
    }

    @Override
    public void played8s(IPlayer player, Card card) {
        //Does nothing
    }
}
