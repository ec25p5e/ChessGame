import core.board.BoardConfigurator;
import core.board.VirtualBoard;
import core.board.VirtualBoardUtils;
import core.move.Move;
import core.move.MoveFactory;
import core.move.MoveTransition;
import core.pieces.*;
import core.player.ai.IMoveStrategy;
import core.player.ai.StockAlphaBeta;
import core.utils.Utils;
import org.junit.Test;
import pgn.FenUtilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestCheckMate {

    @Test
    public void testFoolsMate() {

        final VirtualBoard board = VirtualBoard.initDefaultBoard();
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f2"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f3")));

        assertTrue(t1.moveStatus().isDone());

        final MoveTransition t2 = t1.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t1.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e7"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5")));

        assertTrue(t2.moveStatus().isDone());

        final MoveTransition t3 = t2.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t2.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g2"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g4")));

        assertTrue(t3.moveStatus().isDone());

        final MoveTransition t4 = t3.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t3.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d8"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h4")));

        assertTrue(t4.moveStatus().isDone());

        assertTrue(t4.toBoard().getCurrentPlayer().isInCheckMate());

    }

    @Test
    public void testScholarsMate() {

        final VirtualBoard board = VirtualBoard.initDefaultBoard();
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4")));

        assertTrue(t1.moveStatus().isDone());

        final MoveTransition t2 = t1.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t1.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a7"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a6")));

        assertTrue(t2.moveStatus().isDone());

        final MoveTransition t3 = t2.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t2.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f3")));

        assertTrue(t3.moveStatus().isDone());

        final MoveTransition t4 = t3.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t3.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a6"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a5")));

        assertTrue(t4.moveStatus().isDone());

        final MoveTransition t5 = t4.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t4.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c4")));

        assertTrue(t5.moveStatus().isDone());

        final MoveTransition t6 = t5.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t5.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a5"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a4")));

        assertTrue(t6.moveStatus().isDone());

        final MoveTransition t7 = t6.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t6.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f3"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f7")));

        assertTrue(t7.moveStatus().isDone());
        assertTrue(t7.toBoard().getCurrentPlayer().isInCheckMate());

    }

    @Test
    public void testLegalsMate() {

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
                .doMove(MoveFactory.createMove(t2.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c4")));

        assertTrue(t3.moveStatus().isDone());

        final MoveTransition t4 = t3.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t3.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d7"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d6")));

        assertTrue(t4.moveStatus().isDone());

        final MoveTransition t5 = t4.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t4.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f3")));

        assertTrue(t5.moveStatus().isDone());

        final MoveTransition t6 = t5.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t5.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c8"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g4")));

        assertTrue(t6.moveStatus().isDone());

        final MoveTransition t7 = t6.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t6.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c3")));

        assertTrue(t7.moveStatus().isDone());

        final MoveTransition t8 = t7.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t7.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g7"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g6")));

        assertTrue(t8.moveStatus().isDone());

        final MoveTransition t9 = t8.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t8.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f3"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5")));

        assertTrue(t9.moveStatus().isDone());

        final MoveTransition t10 = t9.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t9.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g4"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d1")));

        assertTrue(t10.moveStatus().isDone());

        final MoveTransition t11 = t10.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t10.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c4"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f7")));

        assertTrue(t11.moveStatus().isDone());

        final MoveTransition t12 = t11.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t11.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e8"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e7")));

        assertTrue(t12.moveStatus().isDone());

        final MoveTransition t13 = t12.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t12.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c3"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d5")));

        assertTrue(t13.moveStatus().isDone());
        assertTrue(t13.toBoard().getCurrentPlayer().isInCheckMate());

    }

    @Test
    public void testSevenMoveMate() {

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
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4")));

        assertTrue(t3.moveStatus().isDone());

        final MoveTransition t4 = t3.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t3.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d7"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d6")));

        assertTrue(t4.moveStatus().isDone());

        final MoveTransition t5 = t4.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t4.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5")));

        assertTrue(t5.moveStatus().isDone());

        final MoveTransition t6 = t5.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t5.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d8"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e7")));

        assertTrue(t6.moveStatus().isDone());

        final MoveTransition t7 = t6.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t6.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d6")));

        assertTrue(t7.moveStatus().isDone());

        final MoveTransition t8 = t7.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t7.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e7"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4")));

        assertTrue(t8.moveStatus().isDone());

        final MoveTransition t9 = t8.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t8.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e2")));

        assertTrue(t9.moveStatus().isDone());

        final MoveTransition t10 = t9.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t9.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g2")));

        assertTrue(t10.moveStatus().isDone());

        final MoveTransition t11 = t10.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t10.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d6"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c7")));

        assertTrue(t11.moveStatus().isDone());

        final MoveTransition t12 = t11.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t11.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g2"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h1")));

        assertTrue(t12.moveStatus().isDone());

        final MoveTransition t13 = t12.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t12.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d8")));

        assertTrue(t13.moveStatus().isDone());
        assertTrue(t13.toBoard().getCurrentPlayer().isInCheckMate());

    }

    @Test
    public void testGrecoGame() {

        final VirtualBoard board = VirtualBoard.initDefaultBoard();
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d2"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4")));

        assertTrue(t1.moveStatus().isDone());

        final MoveTransition t2 = t1.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t1.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g8"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f6")));

        assertTrue(t2.moveStatus().isDone());

        final MoveTransition t3 = t2.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t2.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d2")));

        assertTrue(t3.moveStatus().isDone());

        final MoveTransition t4 = t3.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t3.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e7"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5")));

        assertTrue(t4.moveStatus().isDone());

        final MoveTransition t5 = t4.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t4.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5")));

        assertTrue(t5.moveStatus().isDone());

        final MoveTransition t6 = t5.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t5.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f6"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g4")));


        assertTrue(t6.moveStatus().isDone());

        final MoveTransition t7 = t6.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t6.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h2"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h3")));

        assertTrue(t7.moveStatus().isDone());

        final MoveTransition t8 = t7.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t7.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g4"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e3")));

        assertTrue(t8.moveStatus().isDone());

        final MoveTransition t9 = t8.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t8.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f2"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e3")));

        assertTrue(t9.moveStatus().isDone());

        final MoveTransition t10 = t9.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t9.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d8"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h4")));

        assertTrue(t10.moveStatus().isDone());

        final MoveTransition t11 = t10.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t10.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g2"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g3")));

        assertTrue(t11.moveStatus().isDone());

        final MoveTransition t12 = t11.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t11.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h4"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g3")));

        assertTrue(t12.moveStatus().isDone());
        assertTrue(t12.toBoard().getCurrentPlayer().isInCheckMate());
    }

    @Test
    public void testOlympicGame() {

        final VirtualBoard board = VirtualBoard.initDefaultBoard();
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4")));

        assertTrue(t1.moveStatus().isDone());

        final MoveTransition t2 = t1.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t1.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c7"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c6")));

        assertTrue(t2.moveStatus().isDone());

        final MoveTransition t3 = t2.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t2.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f3")));

        assertTrue(t3.moveStatus().isDone());

        final MoveTransition t4 = t3.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t3.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d7"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d5")));

        assertTrue(t4.moveStatus().isDone());

        final MoveTransition t5 = t4.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t4.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c3")));

        assertTrue(t5.moveStatus().isDone());

        final MoveTransition t6 = t5.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t5.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d5"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4")));

        assertTrue(t6.moveStatus().isDone());

        final MoveTransition t7 = t6.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t6.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c3"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4")));

        assertTrue(t7.moveStatus().isDone());

        final MoveTransition t8 = t7.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t7.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b8"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d7")));

        assertTrue(t8.moveStatus().isDone());

        final MoveTransition t9 = t8.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t8.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e2")));

        assertTrue(t9.moveStatus().isDone());

        final MoveTransition t10 = t9.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t9.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g8"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f6")));

        assertTrue(t10.moveStatus().isDone());

        final MoveTransition t11 = t10.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t10.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d6")));

        assertTrue(t11.moveStatus().isDone());
        assertTrue(t11.toBoard().getCurrentPlayer().isInCheckMate());

    }

    @Test
    public void testAnotherGame() {

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
                .doMove(MoveFactory.createMove(t3.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b8"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c6")));

        assertTrue(t4.moveStatus().isDone());

        final MoveTransition t5 = t4.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t4.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c4")));

        assertTrue(t5.moveStatus().isDone());

        final MoveTransition t6 = t5.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t5.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c6"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4")));

        assertTrue(t6.moveStatus().isDone());

        final MoveTransition t7 = t6.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t6.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f3"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5")));

        assertTrue(t7.moveStatus().isDone());

        final MoveTransition t8 = t7.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t7.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d8"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g5")));

        assertTrue(t8.moveStatus().isDone());

        final MoveTransition t9 = t8.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t8.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f7")));

        assertTrue(t9.moveStatus().isDone());

        final MoveTransition t10 = t9.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t9.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g5"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g2")));

        assertTrue(t10.moveStatus().isDone());

        final MoveTransition t11 = t10.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t10.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f1")));

        assertTrue(t11.moveStatus().isDone());

        final MoveTransition t12 = t11.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t11.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g2"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4")));

        assertTrue(t12.moveStatus().isDone());

        final MoveTransition t13 = t12.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t12.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c4"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e2")));

        assertTrue(t13.moveStatus().isDone());

        final MoveTransition t14 = t13.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t13.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f3")));

        assertTrue(t14.moveStatus().isDone());
        assertTrue(t14.toBoard().getCurrentPlayer().isInCheckMate());

    }

    @Test
    public void testSmotheredMate() {

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
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e2")));

        assertTrue(t3.moveStatus().isDone());

        final MoveTransition t4 = t3.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t3.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b8"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c6")));

        assertTrue(t4.moveStatus().isDone());

        final MoveTransition t5 = t4.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t4.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c3")));

        assertTrue(t4.moveStatus().isDone());

        final MoveTransition t6 = t5.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t5.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c6"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4")));

        assertTrue(t6.moveStatus().isDone());

        final MoveTransition t7 = t6.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t6.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g2"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g3")));

        assertTrue(t7.moveStatus().isDone());

        final MoveTransition t8 = t7.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t7.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f3")));

        assertTrue(t8.moveStatus().isDone());
        assertTrue(t8.toBoard().getCurrentPlayer().isInCheckMate());

    }

    @Test
    public void testHippopotamusMate() {

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
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e2")));

        assertTrue(t3.moveStatus().isDone());

        final MoveTransition t4 = t3.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t3.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d8"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h4")));

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
                .doMove(MoveFactory.createMove(t6.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g2"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g3")));

        assertTrue(t7.moveStatus().isDone());

        final MoveTransition t8 = t7.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t7.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h4"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g5")));


        assertTrue(t8.moveStatus().isDone());

        final MoveTransition t9 = t8.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t8.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d2"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4")));

        assertTrue(t9.moveStatus().isDone());

        final MoveTransition t10 = t9.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t9.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c6"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4")));

        assertTrue(t10.moveStatus().isDone());

        final MoveTransition t11 = t10.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t10.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g5")));

        assertTrue(t11.moveStatus().isDone());

        final MoveTransition t12 = t11.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t11.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f3")));

        assertTrue(t12.moveStatus().isDone());
        assertTrue(t12.toBoard().getCurrentPlayer().isInCheckMate());

    }

    @Test
    public void testBlackburneShillingMate() {

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
                .doMove(MoveFactory.createMove(t3.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b8"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c6")));

        assertTrue(t4.moveStatus().isDone());

        final MoveTransition t5 = t4.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t4.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c4")));

        assertTrue(t5.moveStatus().isDone());

        final MoveTransition t6 = t5.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t5.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c6"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4")));

        assertTrue(t6.moveStatus().isDone());

        final MoveTransition t7 = t6.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t6.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f3"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5")));

        assertTrue(t7.moveStatus().isDone());

        final MoveTransition t8 = t7.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t7.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d8"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g5")));

        assertTrue(t8.moveStatus().isDone());

        final MoveTransition t9 = t8.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t8.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f7")));

        assertTrue(t9.moveStatus().isDone());

        final MoveTransition t10 = t9.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t9.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g5"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g2")));

        assertTrue(t10.moveStatus().isDone());

        final MoveTransition t11 = t10.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t10.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f1")));

        assertTrue(t11.moveStatus().isDone());

        final MoveTransition t12 = t11.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t11.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g2"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4")));

        assertTrue(t12.moveStatus().isDone());

        final MoveTransition t13 = t12.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t12.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c4"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e2")));

        assertTrue(t13.moveStatus().isDone());

        final MoveTransition t14 = t13.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t13.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f3")));

        assertTrue(t14.moveStatus().isDone());
        assertTrue(t14.toBoard().getCurrentPlayer().isInCheckMate());

    }

    @Test
    public void testAnastasiaMate() {

        final BoardConfigurator builder = new BoardConfigurator();

        // Black Layout
        builder.setPiece(new Rook(0, Utils.BLACK));
        builder.setPiece(new Rook(5, Utils.BLACK));
        builder.setPiece(new Pawn(8, Utils.BLACK));
        builder.setPiece(new Pawn(9, Utils.BLACK));
        builder.setPiece(new Pawn(10, Utils.BLACK));
        builder.setPiece(new Pawn(13, Utils.BLACK));
        builder.setPiece(new Pawn(14, Utils.BLACK));
        builder.setPiece(new King(15, Utils.BLACK, false, false));
        // White Layout
        builder.setPiece(new Knight(12, Utils.WHITE));
        builder.setPiece(new Rook(27, Utils.WHITE));
        builder.setPiece(new Pawn(41, Utils.WHITE));
        builder.setPiece(new Pawn(48, Utils.WHITE));
        builder.setPiece(new Pawn(53, Utils.WHITE));
        builder.setPiece(new Pawn(54, Utils.WHITE));
        builder.setPiece(new Pawn(55, Utils.WHITE));
        builder.setPiece(new King(62, Utils.WHITE, false, false));
        // Set the current player
        builder.setMoveMaker(Utils.WHITE);

        final VirtualBoard board = builder.build();
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d5"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h5")));

        assertTrue(t1.moveStatus().isDone());
        assertTrue(t1.toBoard().getCurrentPlayer().isInCheckMate());
    }

    @Test
    public void testTwoBishopMate() {

        final BoardConfigurator builder = new BoardConfigurator();

        builder.setPiece(new King(7, Utils.BLACK, false, false));
        builder.setPiece(new Pawn(8, Utils.BLACK));
        builder.setPiece(new Pawn(10, Utils.BLACK));
        builder.setPiece(new Pawn(15, Utils.BLACK));
        builder.setPiece(new Pawn(17, Utils.BLACK));
        // White Layout
        builder.setPiece(new Bishop(40, Utils.WHITE));
        builder.setPiece(new Bishop(48, Utils.WHITE));
        builder.setPiece(new King(53, Utils.WHITE, false, false));
        // Set the current player
        builder.setMoveMaker(Utils.WHITE);

        final VirtualBoard board = builder.build();
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a3"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b2")));

        assertTrue(t1.moveStatus().isDone());
        assertTrue(t1.toBoard().getCurrentPlayer().isInCheckMate());
    }

    @Test
    public void testQueenRookMate() {

        final BoardConfigurator builder = new BoardConfigurator();

        // Black Layout
        builder.setPiece(new King(5, Utils.BLACK, false, false));
        // White Layout
        builder.setPiece(new Rook(9, Utils.WHITE));
        builder.setPiece(new Queen(16, Utils.WHITE));
        builder.setPiece(new King(59, Utils.WHITE, false, false));
        // Set the current player
        builder.setMoveMaker(Utils.WHITE);

        final VirtualBoard board = builder.build();
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a6"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a8")));

        assertTrue(t1.moveStatus().isDone());
        assertTrue(t1.toBoard().getCurrentPlayer().isInCheckMate());

    }

    @Test
    public void testQueenKnightMate() {

        final BoardConfigurator builder = new BoardConfigurator();

        // Black Layout
        builder.setPiece(new King(4, Utils.BLACK, false, false));
        // White Layout
        builder.setPiece(new Queen(15, Utils.WHITE));
        builder.setPiece(new Knight(29, Utils.WHITE));
        builder.setPiece(new Pawn(55, Utils.WHITE));
        builder.setPiece(new King(60, Utils.WHITE, false, false));
        // Set the current player
        builder.setMoveMaker(Utils.WHITE);

        final VirtualBoard board = builder.build();
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h7"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e7")));

        assertTrue(t1.moveStatus().isDone());
        assertTrue(t1.toBoard().getCurrentPlayer().isInCheckMate());

    }

    @Test
    public void testBackRankMate() {

        final BoardConfigurator builder = new BoardConfigurator();
        // Black Layout
        builder.setPiece(new King(4, Utils.BLACK, false, false));
        builder.setPiece(new Rook(18, Utils.BLACK));
        // White Layout
        builder.setPiece(new Pawn(53, Utils.WHITE));
        builder.setPiece(new Pawn(54, Utils.WHITE));
        builder.setPiece(new Pawn(55, Utils.WHITE));
        builder.setPiece(new King(62, Utils.WHITE, false, false));
        // Set the current player
        builder.setMoveMaker(Utils.BLACK);

        final VirtualBoard board = builder.build();

        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c6"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c1")));

        assertTrue(t1.moveStatus().isDone());
        assertTrue(t1.toBoard().getCurrentPlayer().isInCheckMate());

    }

    @Test
    public void testMateInTwoTest1() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("6k1/1b4pp/1B1Q4/4p1P1/p3q3/2P3r1/P1P2PP1/R5K1 w - - 1 0");
        final IMoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d6"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e6")));
    }

    @Test
    public void testMateInTwoTest2() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("3r3r/1Q5p/p3q2k/3NBp1B/3p3n/5P2/PP4PP/4R2K w - - 1 0");
        final IMoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(
                bestMove,
                MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b7"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g7")));
    }

    @Test
    public void testMateInTwoTest3() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("rn3rk1/1R3ppp/2p5/8/PQ2P3/1P5P/2P1qPP1/3R2K1 w - - 1 0");
        final IMoveStrategy alphaBeta = new StockAlphaBeta(1);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f8")));
    }

    @Test
    public void testMateInFourTest1() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("7k/4r2B/1pb5/2P5/4p2Q/2q5/2P2R2/1K6 w - - 1 0");
        final IMoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f2"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f8")));
    }

    @Test
    public void testMagnusBlackToMoveAndWinTest1() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("2rr2k1/pb3pp1/4q2p/2pn4/2Q1P3/P4P2/1P3BPP/2KR2NR b - - 0 1");
        final IMoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d5"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e3")));
    }
}
