
package crazy8s.player;

import crazy8s.card.Card;
import java.util.ArrayList;

/**
 * Interface that describes attributes of Crazy8s players.
 * @author Graham Burek, Makai Campbell
 */
public interface IPlayer {
  /**
   * Gets a command from the player.
   * @return Encoded command
   */
  public int getCommand( );
  
  /**
   * Gets the player's hand.
   * @return Player's hand.
   */
  public ArrayList<Card> getHand( );
  
  /**
   * Reports that the game made a play.
   * 
   * @param card Card they played.
   */
  public void played(Card card);
  /**
   * Reports who played what.
   * @param player Player playing the card.
   * @param card Card they played.
   */
  public void played(IPlayer player,Card card);
  
  /**
   * Reports who drew from deck.
   * @param player Player drawing a card.
   * @param card Card drawn from deck.
   */
  public void drew(IPlayer player,Card card);
  
  /**
   * Gets the 8s suit.
   * @return Integer representing the suit.
   */
  public int getSuit();
  
  /**
   * Sets the 8s suit.
   * @param suit 8s suit
   */
  public void setSuit(int suit);
  
  /**
   * Gets the player's score
   * @return Score
   */
  public int getScore();
  
  /**
   * Sets the player's score
   * @param score New score
   */
  public void setScore(int score);
  
  /**
   * Gets the card at the top of the discard pile.
   * @return Top discard card from Deck.
   */
  public Card getDiscard();

  /**
   * Reports that an IPlayer played an 8 card.
   * @param player The player that played an 8.
   * @param card The 8 Card that was played.
   * @param suit The suit the 8 card changes to.
   */
    public void played8s(IPlayer player, Card card);
}



