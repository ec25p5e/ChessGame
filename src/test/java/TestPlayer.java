import core.board.BoardConfigurator;
import core.board.VirtualBoard;
import core.board.VirtualBoardUtils;
import core.move.Move;
import core.move.MoveFactory;
import core.move.MoveTransition;
import core.pieces.*;
import core.player.ai.StandardBoardEvaluator;
import core.utils.Utils;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPlayer {

    @Test
    public void testSimpleEvaluation() {
        final VirtualBoard board = VirtualBoard.initDefaultBoard();
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4")));
        assertTrue(t1.moveStatus().isDone());
        final MoveTransition t2 = t1.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t1.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e7"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5")));
        assertTrue(t2.moveStatus().isDone());
        assertEquals(StandardBoardEvaluator.get().evaluate(t2.toBoard(), 0), 0);
    }

    @Test
    public void testBug() {
        final VirtualBoard board = VirtualBoard.initDefaultBoard();
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c2"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c3")));
        assertTrue(t1.moveStatus().isDone());
        final MoveTransition t2 = t1.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t1.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b8"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a6")));
        assertTrue(t2.moveStatus().isDone());
        final MoveTransition t3 = t2.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t2.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a4")));
        assertTrue(t3.moveStatus().isDone());
        final MoveTransition t4 = t3.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t3.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d7"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d6")));
        assertFalse(t4.moveStatus().isDone());
    }

    @Test
    public void testDiscoveredCheck() {
        final BoardConfigurator builder = new BoardConfigurator();
        // Black Layout
        builder.setPiece(new King(4, Utils.BLACK, false, false));
        builder.setPiece(new Rook(24, Utils.BLACK));
        // White Layout
        builder.setPiece(new Bishop(44, Utils.WHITE));
        builder.setPiece(new Rook(52, Utils.WHITE));
        builder.setPiece(new King(58, Utils.WHITE, false, false));
        // Set the current player
        builder.setMoveMaker(Utils.WHITE);
        final VirtualBoard board = builder.build();
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e3"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b6")));
        assertTrue(t1.moveStatus().isDone());
        assertTrue(t1.toBoard().getCurrentPlayer().isInCheck());
        final MoveTransition t2 = t1.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t1.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a5"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b5")));
        assertFalse(t2.moveStatus().isDone());
        final MoveTransition t3 = t1.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t1.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a5"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5")));
        assertTrue(t3.moveStatus().isDone());
    }

    @Test
    public void testUndoMove() {
        final VirtualBoard board = VirtualBoard.initDefaultBoard();
        final Move m1 = MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"));
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(m1);
        assertTrue(t1.moveStatus().isDone());
        t1.toBoard().getCurrentPlayer().getOpponent().unMakeMove(m1);
    }

    @Test
    public void testIllegalMove() {
        final VirtualBoard board = VirtualBoard.initDefaultBoard();
        final Move m1 = MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e6"));
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(m1);
        assertFalse(t1.moveStatus().isDone());
    }

}