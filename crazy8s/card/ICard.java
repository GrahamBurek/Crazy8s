package crazy8s.card;

/**
 * Interface for all cards.
 * @author Ron Coleman
 */
public interface ICard {
    /**
     * Gets the card's rank
     * @return An integer representing the card rank.
     */
  public int getRank( );
  
  /**
   * Gets the card's suit.
   * @return An integer representing the card rank.
   */
  public int getSuit( );
}
