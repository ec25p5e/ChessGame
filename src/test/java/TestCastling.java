import core.board.VirtualBoard;
import core.board.VirtualBoardUtils;
import core.movements.MoveTransition;
import core.movements.MoveFactory;
import core.player.ai.IMoveStrategy;
import core.player.ai.StockAlphaBeta;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestCastling {

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
                .doMove(MoveFactory.createMove(t10.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b1"),
                                VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c3")));
        assertTrue(t11.moveStatus().isDone());

        final IMoveStrategy moveStrategy = new StockAlphaBeta(6);
        moveStrategy.execute(t11.toBoard());
    }

}
