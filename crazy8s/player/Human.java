package crazy8s.player;

import crazy8s.card.Card;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This class implements a human player.
 *
 * @author Graham Burek, Makai Campbell
 */
public class Human implements IPlayer {

    /**
     * My score
     */
    private static int humanScore;
    Opponent opponent;

    /**
     * My hand
     */
    ArrayList<Card> myhand;

    /**
     * Buffered reader connect to the standard input device
     */
    BufferedReader br;
    public Card discarded;

    /**
     * Constructs human player which has access to the keyboard.
     */
    public Human() {
        try {
            myhand = new ArrayList<>();

            br = new BufferedReader(new InputStreamReader(System.in));
        } catch (Exception e) {
            System.err.println("constructor failed: " + e);
        }
    }

    /**
     * Gets the encoded command from the player. That is, the player provides
     * some input in the form of "s", "r", "d", "q", or a number string "1",
     * "2", ... "52", and this method converts this input into an integer. The
     * letter commands are in the class, Command. A number represents itself as
     * an index in the player's hand.
     *
     * @return Encoded command
     */
    @Override
    public int getCommand() {
        if (br == null) {
            return 0;
        }

        try {
            // Show the command prompt
            System.out.print("Input command => ");

            String input = br.readLine();

            Integer cmd = encodeCommand(input);
            switch (cmd) {
                case Command.DRAW:
                    return this.checkDraw(cmd);
                case Command.QUIT:
                    return cmd;
                case Command.SHOW:
                    return cmd;
                case Command.REFRESH:
                    return cmd;
                default:
                    
                        //get the rank and suit of the card on top of the discard pile for comparison
                        int rank2 = this.getDiscard().getRank();
                        int suit2 = this.getDiscard().getSuit();

                        if (this.myhand.get(cmd).getRank() == rank2) {
                            return cmd;
                        }

                        if (this.myhand.get(cmd).getSuit() == suit2) {
                            return cmd;
                        }

                        if (this.search8s() != -1) {
                            if (this.myhand.get(cmd).getRank() == 8) {
                                return cmd;
                            }
                        }
                        break;
                    
            }
        } catch (Exception e) {

        }
        return Command.NO_COMMAND;
    }

    /**
     * Encodes a command string.
     *
     * @param input User input to encode
     * @return <0 if encoding fails, 0 to (n-1) if card index, >52 if other
     * command
     * @see Command
     */
    protected int encodeCommand(String input) {
        input = input.toLowerCase();
        input = input.trim();

        switch (input) {
            case "s":
                return Command.SHOW;
            case "q":
                return Command.QUIT;
            case "d":
                return Command.DRAW;
            case "r":
                return Command.REFRESH;
        }
            int cmd = Integer.parseInt(input.replaceAll("[\\D]", ""));
            for (int index = 1; index <= this.myhand.size(); index++) {
                if (cmd == index) {
                    return --cmd;
                }
            }
        return Command.NO_COMMAND;
    }

    /**
     * Method that converts string input for suit to integers.
     * @param input Command to change the suit on a played 8 card. 
     * @return Returns an integer representation of a suit.
     */
    protected int encode8Command(String input) {
        input = input.toLowerCase();
        input = input.trim();

        if (input.equals("c")) {
            return Card.CLUBS;
        } else if (input.equals("d")) {
            return Card.DIAMONDS;
        } else if (input.equals("h")) {
            return Card.HEARTS;
        } else if (input.equals("s")) {
            return Card.SPADES;
        }
        return Command.NO_COMMAND;
    }

    /**
     * Checks to see if the player is allowed to draw a card.
     * @param cmd Command.DRAW
     * @return Returns Command.DRAW, allowing the player to draw.
     */
    protected int checkDraw(int cmd) {
        for (Card card : myhand) {
            int suit = discarded.getSuit();
            int rank = discarded.getRank();

            if (this.searchSuit(suit) == -1 && this.searchRank(rank) == -1 && this.search8s() == -1) {
            } else {
                System.out.println("You can make a play.");
                return Command.NO_COMMAND;
            }
        }
        return cmd;
    }

    /**
     * Gets my hand
     */
    @Override
    public ArrayList<Card> getHand() {
        return myhand;
    }

    @Override
    public void played(IPlayer player, Card card) {
        this.discarded = card;
        System.out.println(player + " played a " + card + ".");
    }

    @Override
    public void played(Card card) {
        this.discarded = card;
    }
    
    @Override
    public void played8s(IPlayer player, Card card){
        System.out.println(player + " changed suit to " + card.decodeSuit() + ".");
    }

    @Override
    public void drew(IPlayer player, Card card) {
        if(player == this){
            System.out.println("You drew a " + card + ".");
            return;
        }
        System.out.println(player + " drew a card.");
    }

    @Override
    public int getSuit() {
        try {
            System.out.print("Choose a suit (c, d, h, s) => ");
            String command = br.readLine();

            int cmd = this.encode8Command(command);
            if (cmd == Command.NO_COMMAND) {
                System.out.println("Invalid command. Try again");
                return getSuit();
            }
            return cmd;

        } catch (Exception e) {

        }
        return Command.NO_COMMAND;
    }

    @Override
    public void setSuit(int suit) {
        discarded.setSuit(suit);
    }

    /**
     * Converts me to a string.
     */
    @Override
    public String toString() {
        return "HUMAN";
    }

    /**
     * Test the human player
     */
    public static void main(String[] args) {
        Human player = new Human();

        while (true) {
            Integer command = player.getCommand();
            System.out.println(player + ": sent encoded command " + command);

            if (command == Command.QUIT) {
                break;
            }
        }
    }

    @Override
    public int getScore() {
        return humanScore;
    }

    @Override
    public void setScore(int score) {
        this.humanScore = score;
    }

    /**
     * Gets the current top discarded card.
     * @return The discarded card.
     */
    public Card getDiscard() {
        return discarded;
    }

    /**
     * Returns the index of the first card in this IPlayer's hand with a matching rank.
     * @param rank The rank to match
     * @return Returns index of first matching card.
     */
    public int searchRank(int rank) {
        for (int index = 0; index < this.myhand.size(); index++) {
            Card card = myhand.get(index);
            if (rank == card.getRank() && card.getRank() != 8) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the first card in this IPlayer's hand with a matching suit.
     * @param suit The suit to match.
     * @return 
     */
    public int searchSuit(int suit) {
        for (int index = 0; index < this.myhand.size(); index++) {
            Card card = myhand.get(index);
            if (suit == card.getSuit() && card.getRank() != 8) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Finds first 8 card in this IPlayer's hand.
     * @return The index of first 8 card.
     */
    public int search8s() {
        for (int index = 0; index < this.myhand.size(); index++) {
            Card card = myhand.get(index);
            if (card.getRank() == 8) {
                return index;
            }
        }
        return -1;
    }

}
