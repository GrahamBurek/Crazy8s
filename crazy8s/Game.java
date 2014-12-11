package crazy8s;

import crazy8s.card.Card;
import crazy8s.card.Deck;
import crazy8s.player.Command;
import crazy8s.player.Human;
import crazy8s.player.IPlayer;
import crazy8s.player.Opponent;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Game class which implements Crazy 8s play rules.
 *
 * @author Graham Burek, Makai Campbell
 */
public class Game {

    protected ArrayList<IPlayer> players = new ArrayList<>();
    protected Deck deck = new Deck();

    /**
     * Constructor, creates new Human and Opponent IPlayers, and shuffles their
     * order.
     */
    public Game() {
        players.add(new Human());
        players.add(new Opponent());
        Collections.shuffle(players);
    }

    /**
     * Game loop method.
     */
    public void go() {
        // Set up the players' hands.
        this.deal();
        // Put an initial card in the discard pile.
        Card discard = deck.draw();
        deck.discard(discard);
        this.distribute(discard);

        while (true) {
            // Loop through the players.
            playerLoop:
            for (IPlayer player : players) {
                // Check to see if a player has won before moving on to the next player.
                this.checkWin();
                // Let the players input commands.
                do {
                    // Get a command from the current player.
                    int command = player.getCommand();
                    // Checks for a draw, show, quit, or refresh command.
                    boolean newCommand = this.executeCommand(command, player);
                    // Checks whether to continue (to check if the player played a card) or to ask for a new command.
                    if (newCommand == true) {
                        continue;
                    }
                    // Check if the player played a card in their hand.
                    this.cardPlayed(player, command);
                    //shows the Human player's hand if Opponent has played.
                    if (player instanceof Opponent) {
                        for (IPlayer p : players) {
                            if (p instanceof Human) {
                                Game.show(p);
                            }
                        }
                    }
                    continue playerLoop;
                } while (true);
            }
        }
    }

    /**
     * Checks given command to see if it should be executed.
     *
     * @param cmd Command that the IPlayer entered.
     * @param player The IPlayer inputing the command.
     * @return Returns true if the command was executed and false otherwise.
     */
    protected boolean executeCommand(int cmd, IPlayer player) {
        switch (cmd) {
            case Command.DRAW:
                Card card = deck.draw();
                player.getHand().add(card);
                for (IPlayer twoplayers : players) {
                    twoplayers.drew(player, card);
                }
                if(player instanceof Human){
                    Game.show(player);
                }
                return true;
            case Command.SHOW:
                Game.show(player);
                return true;
            case Command.REFRESH:
                IPlayer opponent = null;
                for (IPlayer p : players) {
                    if (!(p instanceof Human)) {
                        opponent = p;
                    }
                }
                // Shows the player's hand.
                Game.show(player);
                
                // Shows the Opponent's score and number of cards in its hand. Also shows number of cards in deck and discard pile.
                System.out.println("Cards in deck: " + deck.getDeckSize());
                System.out.println("Cards in discard pile: " + deck.getDiscardSize());
                System.out.println("________________________________");
                System.out.println(opponent);
                System.out.println("Score: " + opponent.getScore());
                System.out.println("Cards in hand: " + opponent.getHand().size());
                System.out.println("________________________________");
                return true;
            case Command.QUIT:
                System.out.println("Ending game...");
                System.exit(0);
            case Command.NO_COMMAND:
                System.out.println("Invalid command. Try again.");
                return true;
            default:
                return false;
        }
    }

    /**
     * Tells both players who played what to the discard pile.
     *
     * @param player IPlayer playing the card.
     * @param card The Card played to the discard pile.
     */
    protected void distribute(IPlayer player, Card card) {
        for (IPlayer p : players) {
            p.played(player, card);
        }
    }

    /**
     * Tells both players what the game plays to the discard pile.
     *
     * @param burned The Card played to the discard pile.
     */
    protected void distribute(Card burned) {
        for (IPlayer player : players) {
            player.played(burned);
        }
    }

    /**
     * Tells both players an 8 card has been played.
     *
     * @param player The IPlayer playing the 8.
     * @param card The 8 card being played.
     * 
     */
    protected void distribute8s(IPlayer player, Card card) {
        for (IPlayer p : players) {
            p.played8s(player, card);
        }
    }

    /**
     * Checks if either player has won the game.
     */
    protected void checkWin() {
        for (IPlayer p : players) {
            if (p.getHand().isEmpty()) {
                endGame(p);
            }
        }
    }

    /**
     * Plays a card from an IPlayer's hand to the discard pile.
     *
     * @param player The IPlayer playing the card.
     * @param command The index of the card in the IPlayer's hand.
     */
    protected void cardPlayed(IPlayer player, int command) {
        Card played = player.getHand().remove(command);
        deck.discard(played);
        if (played.getRank() == 8) {
            int suit = player.getSuit();
            this.distribute(player, played);
            played.setSuit(suit);
            for (IPlayer p : players) {
                p.setSuit(suit);
                p.played8s(player, played);
            }
            this.distribute(played);
            return;
        }
        this.distribute(player, played);
    }

    /**
     * Shows a given IPlayer's hand.
     *
     * @param player IPlayer's hand to show.
     */
    public static void show(IPlayer player) {
        ArrayList<Card> hand = player.getHand();
        System.out.println("________________________________");
        System.out.println(player);
        System.out.println("Score: " + player.getScore());
        for (int index = 0; index < hand.size(); index++) {
            System.out.println((index + 1) + ". " + hand.get(index));
        }
        System.out.println("________________________________");
        System.out.println("Discard card is " + player.getDiscard());
        System.out.println("________________________________");

    }

    /**
     * Deals out a hand to each IPlayer.
     */
    protected void deal() {
        for (int index = 0; index < 8; index++) {
            for (IPlayer player : players) {
                Card card = deck.draw();
                ArrayList<Card> hand = player.getHand();
                hand.add(card);
//                players.get(playerId).getHand().add(deck.draw());
            }
        }
    }

    /**
     * Scores the game after it ends.
     *
     * @param winner IPlayer who won the game.
     * @return Returns the score to add to the winning IPlayer's current score.
     */
    protected int scoreGame(IPlayer winner) {
        int scoreToAdd = 0;
        int value = 0;
        for (IPlayer player : players) {
            for (int index = 0; index < player.getHand().size(); index++) {
                if (player.getHand().get(index).getRank() != 8) {
                    if(player.getHand().get(index).getRank() > 10){
                        value = 10;
                    } else {
                    value = player.getHand().get(index).getRank();
                    }
                } else {
                    value = 50;
                }
                scoreToAdd = scoreToAdd + value;
            }
        }
        winner.setScore(winner.getScore() + scoreToAdd);
        return scoreToAdd;
    }

    /**
     * Scores and sets up a new Crazy8s game.
     *
     * @param winner The IPlayer that won the game.
     */
    protected void endGame(IPlayer winner) {
        System.out.println("Game over.");
        int score = this.scoreGame(winner);
        System.out.println(winner + " scored " + score);
        for (IPlayer p : players) {
            System.out.println(p + " score: " + p.getScore());
        }
        System.out.println("Starting new game.");
        Game game = new Game();
        game.go();
    }

    /**
     * Start here when we execute this class.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Welcome to Crazy8s!");
        System.out.println("The commands are: s to show your hand and score, r to show the number of cards the opponent has and his score, d to draw a card, and q to quit the game. Type the number of a card in your hand to play it.");
        Game game = new Game();
        game.go();
    }
}
