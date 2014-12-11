package crazy8s.player;

/**
 * Valid commands "other" not including card indexes.
 * Human players use all of these commands whereas
 * artificially intelligent computer players use
 * only DRAW and card index commands.
 * @author Graham Burek and Makai Campbell
 */
public class Command {
  public final static int NO_COMMAND = -1;
  public final static int SHOW = 53;
  public final static int REFRESH = 54;
  public final static int DRAW = 55;
  public final static int QUIT = 56;
}
