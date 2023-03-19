import com.google.common.collect.Iterables;
import core.board.BoardConfigurator;
import core.board.VirtualBoard;
import core.board.VirtualBoardUtils;
import core.move.Move;
import core.move.MoveFactory;
import core.move.MoveTransition;
import core.move.MoveUtils;
import core.pieces.*;
import core.pieces.piece.Piece;
import core.player.ai.IBoardEvaluator;
import core.player.ai.StandardBoardEvaluator;
import core.utils.Utils;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestBoard {

    @Test
    public void initialVirtualBoard() {

        final VirtualBoard board = VirtualBoard.initDefaultBoard();
        assertEquals(board.getCurrentPlayer().getUsableMoves().size(), 20);
        assertEquals(board.getCurrentPlayer().getOpponent().getUsableMoves().size(), 20);
        assertFalse(board.getCurrentPlayer().isInCheck());
        assertFalse(board.getCurrentPlayer().isInCheckMate());
        assertFalse(board.getCurrentPlayer().isCastled());
        assertTrue(board.getCurrentPlayer().isKingSideCastleCapable());
        assertTrue(board.getCurrentPlayer().isQueenSideCastleCapable());
        assertEquals(board.getCurrentPlayer(), board.getWhitePlayer());
        assertEquals(board.getCurrentPlayer().getOpponent(), board.getBlackPlayer());
        assertFalse(board.getCurrentPlayer().getOpponent().isInCheck());
        assertFalse(board.getCurrentPlayer().getOpponent().isInCheckMate());
        assertFalse(board.getCurrentPlayer().getOpponent().isCastled());
        assertTrue(board.getCurrentPlayer().getOpponent().isKingSideCastleCapable());
        assertTrue(board.getCurrentPlayer().getOpponent().isQueenSideCastleCapable());
        assertTrue(board.getWhitePlayer().toString().equals("White"));
        assertTrue(board.getBlackPlayer().toString().equals("Black"));

        final Iterable<Piece> allPieces = board.getAllPieces();
        final Iterable<Move> allMoves = Iterables.concat(board.getWhitePlayer().getUsableMoves(), board.getBlackPlayer().getUsableMoves());
        for(final Move move : allMoves) {
            assertFalse(move.isAttack());
            assertFalse(move.isCastlingMove());
            assertEquals(MoveUtils.exchangeScore(move), 1);
        }

        assertEquals(Iterables.size(allMoves), 40);
        assertEquals(Iterables.size(allPieces), 32);
        assertFalse(VirtualBoardUtils.isEndGame(board));
        assertFalse(VirtualBoardUtils.isThreatenedBoardImmediate(board));
        assertEquals(StandardBoardEvaluator.get().evaluate(board, 0), 0);
        assertEquals(board.getPiece(35), null);
    }

    @Test
    public void testPlainKingMove() {

        final BoardConfigurator builder = new BoardConfigurator();
        // Black Layout
        builder.setPiece(new King(4, Utils.BLACK, false, false));
        builder.setPiece(new Pawn(12, Utils.BLACK));
        // White Layout
        builder.setPiece(new Pawn(52, Utils.WHITE));
        builder.setPiece(new King(60, Utils.WHITE, false, false));
        builder.setMoveMaker(Utils.WHITE);
        // Set the current player
        final VirtualBoard board = builder.build();
        System.out.println(board);

        assertEquals(board.getWhitePlayer().getUsableMoves().size(), 6);
        assertEquals(board.getBlackPlayer().getUsableMoves().size(), 6);
        assertFalse(board.getCurrentPlayer().isInCheck());
        assertFalse(board.getCurrentPlayer().isInCheckMate());
        assertFalse(board.getCurrentPlayer().getOpponent().isInCheck());
        assertFalse(board.getCurrentPlayer().getOpponent().isInCheckMate());
        assertEquals(board.getCurrentPlayer(), board.getWhitePlayer());
        assertEquals(board.getCurrentPlayer().getOpponent(), board.getBlackPlayer());
        IBoardEvaluator evaluator = StandardBoardEvaluator.get();
        System.out.println(evaluator.evaluate(board, 0));
        assertEquals(StandardBoardEvaluator.get().evaluate(board, 0), 0);

        final Move move = MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e1"),
                VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f1"));

        final MoveTransition moveTransition = board.getCurrentPlayer()
                .doMove(move);

        assertEquals(moveTransition.transitionMove(), move);
        assertEquals(moveTransition.fromBoard(), board);
        assertEquals(moveTransition.toBoard().getCurrentPlayer(), moveTransition.toBoard().getBlackPlayer());

        assertTrue(moveTransition.moveStatus().isDone());
        assertEquals(moveTransition.toBoard().getWhitePlayer().getPlayerKing().getPiecePosition(), 61);
        System.out.println(moveTransition.toBoard());

    }

    @Test
    public void testVirtualBoardConsistency() {
        final VirtualBoard board = VirtualBoard.initDefaultBoard();
        assertEquals(board.getCurrentPlayer(), board.getWhitePlayer());

        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4")));
        final MoveTransition t2 = t1.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t1.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e7"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5")));

        final MoveTransition t3 = t2.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t2.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f3")));
        final MoveTransition t4 = t3.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t3.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d7"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d5")));

        final MoveTransition t5 = t4.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t4.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d5")));
        final MoveTransition t6 = t5.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t5.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d8"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d5")));

        final MoveTransition t7 = t6.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t6.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f3"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g5")));
        final MoveTransition t8 = t7.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t7.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f7"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f6")));

        final MoveTransition t9 = t8.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t8.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d1"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h5")));
        final MoveTransition t10 = t9.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t9.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g7"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g6")));

        final MoveTransition t11 = t10.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t10.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h5"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h4")));
        final MoveTransition t12 = t11.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t11.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f6"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g5")));

        final MoveTransition t13 = t12.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t12.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h4"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g5")));
        final MoveTransition t14 = t13.toBoard()
                .getCurrentPlayer()
                .doMove(MoveFactory.createMove(t13.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d5"),
                        VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4")));

        assertTrue(t14.toBoard().getWhitePlayer().getActivePieces().size() == calculatedActivesFor(t14.toBoard(), Utils.WHITE));
        assertTrue(t14.toBoard().getBlackPlayer().getActivePieces().size() == calculatedActivesFor(t14.toBoard(), Utils.BLACK));

    }

    @Test(expected=RuntimeException.class)
    public void testInvalidVirtualBoard() {

        final BoardConfigurator builder = new BoardConfigurator();
        // Black Layout
        builder.setPiece(new Rook(0, Utils.BLACK));
        builder.setPiece(new Knight(1, Utils.BLACK));
        builder.setPiece(new Bishop(2, Utils.BLACK));
        builder.setPiece(new Queen(3, Utils.BLACK));
        builder.setPiece(new Bishop(5, Utils.BLACK));
        builder.setPiece(new Knight(6, Utils.BLACK));
        builder.setPiece(new Rook(7, Utils.BLACK));
        builder.setPiece(new Pawn(8, Utils.BLACK));
        builder.setPiece(new Pawn(9, Utils.BLACK));
        builder.setPiece(new Pawn(10, Utils.BLACK));
        builder.setPiece(new Pawn(11, Utils.BLACK));
        builder.setPiece(new Pawn(12, Utils.BLACK));
        builder.setPiece(new Pawn(13, Utils.BLACK));
        builder.setPiece(new Pawn(14, Utils.BLACK));
        builder.setPiece(new Pawn(15, Utils.BLACK));
        // White Layout
        builder.setPiece(new Pawn(48, Utils.WHITE));
        builder.setPiece(new Pawn(49, Utils.WHITE));
        builder.setPiece(new Pawn(50, Utils.WHITE));
        builder.setPiece(new Pawn(51, Utils.WHITE));
        builder.setPiece(new Pawn(52, Utils.WHITE));
        builder.setPiece(new Pawn(53, Utils.WHITE));
        builder.setPiece(new Pawn(54, Utils.WHITE));
        builder.setPiece(new Pawn(55, Utils.WHITE));
        builder.setPiece(new Rook(56, Utils.WHITE));
        builder.setPiece(new Knight(57, Utils.WHITE));
        builder.setPiece(new Bishop(58, Utils.WHITE));
        builder.setPiece(new Queen(59, Utils.WHITE));
        builder.setPiece(new Bishop(61, Utils.WHITE));
        builder.setPiece(new Knight(62, Utils.WHITE));
        builder.setPiece(new Rook(63, Utils.WHITE));
        //white to move
        builder.setMoveMaker(Utils.WHITE);
        //build the board
        builder.build();
    }

    @Test
    public void testAlgebreicNotation() {
        assertEquals(VirtualBoardUtils.INSTANCE.getPositionAtCoordinate(0), "a8");
        assertEquals(VirtualBoardUtils.INSTANCE.getPositionAtCoordinate(1), "b8");
        assertEquals(VirtualBoardUtils.INSTANCE.getPositionAtCoordinate(2), "c8");
        assertEquals(VirtualBoardUtils.INSTANCE.getPositionAtCoordinate(3), "d8");
        assertEquals(VirtualBoardUtils.INSTANCE.getPositionAtCoordinate(4), "e8");
        assertEquals(VirtualBoardUtils.INSTANCE.getPositionAtCoordinate(5), "f8");
        assertEquals(VirtualBoardUtils.INSTANCE.getPositionAtCoordinate(6), "g8");
        assertEquals(VirtualBoardUtils.INSTANCE.getPositionAtCoordinate(7), "h8");
    }

    @Test
    public void mem() {
        final Runtime runtime = Runtime.getRuntime();
        long start, end;
        runtime.gc();
        start = runtime.freeMemory();
        VirtualBoard board = VirtualBoard.initDefaultBoard();
        end =  runtime.freeMemory();
        System.out.println("That took " + (start-end) + " bytes.");

    }
    private static int calculatedActivesFor(final VirtualBoard board,
                                            final Utils alliance) {
        int count = 0;
        for (final Piece p : board.getAllPieces()) {
            if (p.getPieceUtils().equals(alliance)) {
                count++;
            }
        }
        return count;
    }

}