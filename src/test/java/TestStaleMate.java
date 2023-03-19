import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.board.BoardConfigurator;
import core.board.VirtualBoard;
import core.board.VirtualBoardUtils;
import core.move.MoveFactory;
import core.move.MoveTransition;
import core.pieces.*;
import core.utils.Utils;
import org.junit.Test;

public class TestStaleMate {
    @Test
    public void testAnandKramnikStaleMate() {

        final BoardConfigurator builder = new BoardConfigurator();
        // Black Layout
        builder.setPiece(new Pawn(14, Utils.BLACK));
        builder.setPiece(new Pawn(21, Utils.BLACK));
        builder.setPiece(new King(36, Utils.BLACK, false, false));
        // White Layout
        builder.setPiece(new Pawn(29, Utils.WHITE));
        builder.setPiece(new King(31, Utils.WHITE, false, false));
        builder.setPiece(new Pawn(39, Utils.WHITE));
        // Set the current player
        builder.setMoveMaker(Utils.BLACK);
        final VirtualBoard board = builder.build();
        assertFalse(board.getCurrentPlayer().isInStaleMate());
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f5")));
        assertTrue(t1.moveStatus().isDone());
        assertTrue(t1.toBoard().getCurrentPlayer().isInStaleMate());
        assertFalse(t1.toBoard().getCurrentPlayer().isInCheck());
        assertFalse(t1.toBoard().getCurrentPlayer().isInCheckMate());
    }

    @Test
    public void testAnonymousStaleMate() {
        final BoardConfigurator builder = new BoardConfigurator();
        // Black Layout
        builder.setPiece(new King(2, Utils.BLACK, false, false));
        // White Layout
        builder.setPiece(new Pawn(10, Utils.WHITE));
        builder.setPiece(new King(26, Utils.WHITE, false, false));
        // Set the current player
        builder.setMoveMaker(Utils.WHITE);
        final VirtualBoard board = builder.build();
        assertFalse(board.getCurrentPlayer().isInStaleMate());
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c5"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c6")));
        assertTrue(t1.moveStatus().isDone());
        assertTrue(t1.toBoard().getCurrentPlayer().isInStaleMate());
        assertFalse(t1.toBoard().getCurrentPlayer().isInCheck());
        assertFalse(t1.toBoard().getCurrentPlayer().isInCheckMate());
    }

    @Test
    public void testAnonymousStaleMate2() {
        final BoardConfigurator builder = new BoardConfigurator();
        // Black Layout
        builder.setPiece(new King(0, Utils.BLACK, false, false));
        // White Layout
        builder.setPiece(new Pawn(16, Utils.WHITE));
        builder.setPiece(new King(17, Utils.WHITE, false, false));
        builder.setPiece(new Bishop(19, Utils.WHITE));
        // Set the current player
        builder.setMoveMaker(Utils.WHITE);
        final VirtualBoard board = builder.build();
        assertFalse(board.getCurrentPlayer().isInStaleMate());
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a6"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a7")));
        assertTrue(t1.moveStatus().isDone());
        assertTrue(t1.toBoard().getCurrentPlayer().isInStaleMate());
        assertFalse(t1.toBoard().getCurrentPlayer().isInCheck());
        assertFalse(t1.toBoard().getCurrentPlayer().isInCheckMate());
    }
}