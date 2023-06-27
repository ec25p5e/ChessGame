package pgn;

import core.board.VirtualBoard;
import core.move.Move;
import core.player.Player;

public interface IPGNPersistence {

    /**
     *
     * @param game
     */
    void persistGame(final Game game);

    /**
     *
     * @param board
     * @param player
     * @param gameText
     * @return
     */
    Move getNextBestMove(final VirtualBoard board, final Player player, final String gameText);
}
