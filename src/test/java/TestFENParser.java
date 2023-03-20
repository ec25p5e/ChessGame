import core.board.VirtualBoard;
import core.board.VirtualBoardUtils;
import core.move.MoveFactory;
import core.move.MoveTransition;
import org.junit.Test;
import pgn.FenUtilities;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestFENParser {

    @Test
    public void testWriteFEN1() throws IOException {
        final VirtualBoard board = VirtualBoard.initDefaultBoard();
        final String fenString = FenUtilities.createFENFromGame(board);
        assertEquals(fenString, "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    @Test
    public void testWriteFEN2() throws IOException {
        final VirtualBoard board = VirtualBoard.initDefaultBoard();
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4")));
        assertTrue(t1.moveStatus().isDone());
        final String fenString = FenUtilities.createFENFromGame(t1.toBoard());
        assertEquals(fenString, "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1");
        final MoveTransition t2 = t1.toBoard().getCurrentPlayer()
                .doMove(MoveFactory.createMove(t1.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c7"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c5")));
        assertTrue(t2.moveStatus().isDone());
        final String fenString2 = FenUtilities.createFENFromGame(t2.toBoard());
        assertEquals(fenString2, "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 1");

    }

}