import core.board.VirtualBoard;
import core.board.VirtualBoardUtils;
import core.move.Move;
import core.move.MoveFactory;
import core.move.MoveTransition;
import core.player.ai.IMoveStrategy;
import core.player.ai.StockAlphaBeta;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestCastling {

    @Test
    public void testWhiteKingSideCastle() {
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
        final MoveTransition t3 = t2.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t2.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f3")));
        assertTrue(t3.moveStatus().isDone());
        final MoveTransition t4 = t3.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t3.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d7"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d6")));
        assertTrue(t4.moveStatus().isDone());
        final MoveTransition t5 = t4.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t4.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e2")));
        assertTrue(t5.moveStatus().isDone());
        final MoveTransition t6 = t5.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t5.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d6"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d5")));
        assertTrue(t6.moveStatus().isDone());
        final Move wm1 = MoveFactory
                .createMove(t6.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition(
                        "e1"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g1"));
        assertTrue(t6.toBoard().getCurrentPlayer().getUsableMoves().contains(wm1));
        final MoveTransition t7 = t6.toBoard().getCurrentPlayer().doMove(wm1);
        assertTrue(t7.moveStatus().isDone());
        assertTrue(t7.toBoard().getWhitePlayer().isCastled());
        assertFalse(t7.toBoard().getWhitePlayer().isKingSideCastleCapable());
        assertFalse(t7.toBoard().getWhitePlayer().isQueenSideCastleCapable());
    }

    @Test
    public void testWhiteQueenSideCastle() {
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
        final MoveTransition t3 = t2.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t2.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d2"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d3")));
        assertTrue(t3.moveStatus().isDone());
        final MoveTransition t4 = t3.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t3.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d7"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d6")));
        assertTrue(t4.moveStatus().isDone());
        final MoveTransition t5 = t4.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t4.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d2")));
        assertTrue(t5.moveStatus().isDone());
        final MoveTransition t6 = t5.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t5.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d6"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d5")));
        assertTrue(t6.moveStatus().isDone());
        final MoveTransition t7 = t6.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t6.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e2")));
        assertTrue(t7.moveStatus().isDone());
        final MoveTransition t8 = t7.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t7.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h7"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h6")));
        assertTrue(t8.moveStatus().isDone());
        final MoveTransition t9 = t8.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t8.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c3")));
        assertTrue(t9.moveStatus().isDone());
        final MoveTransition t10 = t9.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t9.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h6"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h5")));
        assertTrue(t10.moveStatus().isDone());
        final Move wm1 = MoveFactory
                .createMove(t10.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition(
                        "e1"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c1"));
        assertTrue(t10.toBoard().getCurrentPlayer().getUsableMoves().contains(wm1));
        final MoveTransition t11 = t10.toBoard().getCurrentPlayer().doMove(wm1);
        assertTrue(t11.moveStatus().isDone());
        assertTrue(t11.toBoard().getWhitePlayer().isCastled());
        assertFalse(t11.toBoard().getWhitePlayer().isKingSideCastleCapable());
        assertFalse(t11.toBoard().getWhitePlayer().isQueenSideCastleCapable());
    }

    @Test
    public void testBlackKingSideCastle() {
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
        final MoveTransition t3 = t2.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t2.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d2"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d3")));
        assertTrue(t3.moveStatus().isDone());
        final MoveTransition t4 = t3.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t3.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g8"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f6")));
        assertTrue(t4.moveStatus().isDone());
        final MoveTransition t5 = t4.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t4.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d3"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4")));
        assertTrue(t5.moveStatus().isDone());
        final MoveTransition t6 = t5.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t5.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f8"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e7")));
        assertTrue(t6.moveStatus().isDone());
        final MoveTransition t7 = t6.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t6.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d5")));
        assertTrue(t7.moveStatus().isDone());
        final Move wm1 = MoveFactory
                .createMove(t7.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition(
                        "e8"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g8"));
        assertTrue(t7.toBoard().getCurrentPlayer().getUsableMoves().contains(wm1));
        final MoveTransition t8 = t7.toBoard().getCurrentPlayer().doMove(wm1);
        assertTrue(t8.moveStatus().isDone());
        assertTrue(t8.toBoard().getBlackPlayer().isCastled());
        assertFalse(t8.toBoard().getBlackPlayer().isKingSideCastleCapable());
        assertFalse(t8.toBoard().getBlackPlayer().isQueenSideCastleCapable());
    }

    @Test
    public void testBlackQueenSideCastle() {
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
        final MoveTransition t3 = t2.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t2.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d2"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d3")));
        assertTrue(t3.moveStatus().isDone());
        final MoveTransition t4 = t3.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t3.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d8"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e7")));
        assertTrue(t4.moveStatus().isDone());
        final MoveTransition t5 = t4.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t4.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c3")));
        assertTrue(t5.moveStatus().isDone());
        final MoveTransition t6 = t5.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t5.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b8"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c6")));
        assertTrue(t6.moveStatus().isDone());
        final MoveTransition t7 = t6.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t6.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d2")));
        assertTrue(t7.moveStatus().isDone());
        final MoveTransition t8 = t7.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t7.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d7"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d6")));
        assertTrue(t8.moveStatus().isDone());
        final MoveTransition t9 = t8.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t8.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e2")));
        assertTrue(t9.moveStatus().isDone());
        final MoveTransition t10 = t9.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t9.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c8"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d7")));
        assertTrue(t10.moveStatus().isDone());
        final MoveTransition t11 = t10.toBoard()
                .getCurrentPlayer()
                .doMove(
                        MoveFactory.createMove(t10.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g1"),
                                VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f3")));
        assertTrue(t11.moveStatus().isDone());
        final Move wm1 = MoveFactory
                .createMove(t11.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition(
                        "e8"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c8"));
        assertTrue(t11.toBoard().getCurrentPlayer().getUsableMoves().contains(wm1));
        final MoveTransition t12 = t11.toBoard().getCurrentPlayer().doMove(wm1);
        assertTrue(t12.moveStatus().isDone());
        assertTrue(t12.toBoard().getBlackPlayer().isCastled());
        assertFalse(t12.toBoard().getBlackPlayer().isKingSideCastleCapable());
        assertFalse(t12.toBoard().getBlackPlayer().isQueenSideCastleCapable());
    }

    @Test
    public void testCastleBugOne() {
        final VirtualBoard board = VirtualBoard.initDefaultBoard();
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4")));
        assertTrue(t1.moveStatus().isDone());
        final MoveTransition t2 = t1.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t1.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d7"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d5")));
        assertTrue(t2.moveStatus().isDone());
        final MoveTransition t3 = t2.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t2.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5")));
        assertTrue(t3.moveStatus().isDone());
        final MoveTransition t4 = t3.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t3.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c8"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f5")));
        assertTrue(t4.moveStatus().isDone());
        final MoveTransition t5 = t4.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t4.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d3")));
        assertTrue(t5.moveStatus().isDone());
        final MoveTransition t6 = t5.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t5.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f5"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d3")));
        assertTrue(t6.moveStatus().isDone());
        final MoveTransition t7 = t6.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t6.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c2"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d3")));
        assertTrue(t7.moveStatus().isDone());
        final MoveTransition t8 = t7.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t7.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e7"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e6")));
        assertTrue(t8.moveStatus().isDone());
        final MoveTransition t9 = t8.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t8.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a4")));
        assertTrue(t9.moveStatus().isDone());
        final MoveTransition t10 = t9.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t9.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d8"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d7")));
        assertTrue(t10.moveStatus().isDone());
        final MoveTransition t11 = t10.toBoard()
                .getCurrentPlayer()
                .doMove(
                        MoveFactory.createMove(t10.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b1"),
                                VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c3")));
        assertTrue(t11.moveStatus().isDone());

        final IMoveStrategy moveStrategy = new StockAlphaBeta(6);
        moveStrategy.execute(t11.toBoard());
    }

    /* @Test
    public void testNoCastlingOutOfCheck() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("r3k2r/1pN1nppp/p3p3/3p4/8/8/PPPK1PPP/R6R b kq - 1 18");
        final Move illegalCastleMove = MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e8"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c8"));
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(illegalCastleMove);
        assertFalse(t1.moveStatus().isDone());
    } */

}
