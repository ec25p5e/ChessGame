import core.board.BoardConfigurator;
import core.board.VirtualBoard;
import core.board.VirtualBoardUtils;
import core.movements.Move;
import core.movements.MoveFactory;
import core.movements.MoveTransition;
import core.pieces.Bishop;
import core.pieces.King;
import core.pieces.Rook;
import core.utils.Utils;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestPlayer {

    @Test
    public void testIllegalMove() {
        final VirtualBoard board = VirtualBoard.initDefaultBoard();
        final Move m1 = MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e6"));
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(m1);
        assertFalse(t1.moveStatus().isDone());
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

}
