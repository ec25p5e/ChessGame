import com.google.common.collect.Sets;
import core.board.BoardConfigurator;
import core.board.VirtualBoard;
import core.board.VirtualBoardUtils;
import core.move.Move;
import core.move.MoveFactory;
import core.move.MoveTransition;
import core.pieces.*;
import core.pieces.piece.Piece;
import core.utils.Utils;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

public class TestPieces {

    @Test
    public void testMiddleQueenOnEmptyVirtualBoard() {
        final BoardConfigurator builder = new BoardConfigurator();
        // Black Layout
        builder.setPiece(new King(4, Utils.BLACK, false, false));
        // White Layout
        builder.setPiece(new Queen(36, Utils.WHITE));
        builder.setPiece(new King(60, Utils.WHITE, false, false));
        // Set the current player
        builder.setMoveMaker(Utils.WHITE);
        final VirtualBoard board = builder.build();
        final Collection<Move> whiteLegals = board.getWhitePlayer().getUsableMoves();
        final Collection<Move> blackLegals = board.getBlackPlayer().getUsableMoves();
        assertEquals(whiteLegals.size(), 31);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e8"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e7"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e6"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e3"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e2"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a4"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b4"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c4"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f4"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g4"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h4"))));
    }

    @Test
    public void testLegalMoveAllAvailable() {

        final BoardConfigurator boardBuilder = new BoardConfigurator();
        // Black Layout
        boardBuilder.setPiece(new King(4, Utils.BLACK, false, false));
        boardBuilder.setPiece(new Knight(28, Utils.BLACK));
        // White Layout
        boardBuilder.setPiece(new Knight(36, Utils.WHITE));
        boardBuilder.setPiece(new King(60, Utils.WHITE, false, false));
        // Set the current player
        boardBuilder.setMoveMaker(Utils.WHITE);
        final VirtualBoard board = boardBuilder.build();
        final Collection<Move> whiteLegals = board.getWhitePlayer().getUsableMoves();
        assertEquals(whiteLegals.size(), 13);
        final Move wm1 = MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d6"));
        final Move wm2 = MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f6"));
        final Move wm3 = MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c5"));
        final Move wm4 = MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g5"));
        final Move wm5 = MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c3"));
        final Move wm6 = MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g3"));
        final Move wm7 = MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d2"));
        final Move wm8 = MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f2"));

        assertTrue(whiteLegals.contains(wm1));
        assertTrue(whiteLegals.contains(wm2));
        assertTrue(whiteLegals.contains(wm3));
        assertTrue(whiteLegals.contains(wm4));
        assertTrue(whiteLegals.contains(wm5));
        assertTrue(whiteLegals.contains(wm6));
        assertTrue(whiteLegals.contains(wm7));
        assertTrue(whiteLegals.contains(wm8));

        final BoardConfigurator boardBuilder2 = new BoardConfigurator();
        // Black Layout
        boardBuilder2.setPiece(new King(4, Utils.BLACK, false, false));
        boardBuilder2.setPiece(new Knight(28, Utils.BLACK));
        // White Layout
        boardBuilder2.setPiece(new Knight(36, Utils.WHITE));
        boardBuilder2.setPiece(new King(60, Utils.WHITE, false, false));
        // Set the current player
        boardBuilder2.setMoveMaker(Utils.BLACK);
        final VirtualBoard board2 = boardBuilder2.build();
        final Collection<Move> blackLegals = board.getBlackPlayer().getUsableMoves();

        final Move bm1 = MoveFactory
                .createMove(board2, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d7"));
        final Move bm2 = MoveFactory
                .createMove(board2, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f7"));
        final Move bm3 = MoveFactory
                .createMove(board2, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c6"));
        final Move bm4 = MoveFactory
                .createMove(board2, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g6"));
        final Move bm5 = MoveFactory
                .createMove(board2, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c4"));
        final Move bm6 = MoveFactory
                .createMove(board2, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g4"));
        final Move bm7 = MoveFactory
                .createMove(board2, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d3"));
        final Move bm8 = MoveFactory
                .createMove(board2, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f3"));

        assertEquals(blackLegals.size(), 13);

        assertTrue(blackLegals.contains(bm1));
        assertTrue(blackLegals.contains(bm2));
        assertTrue(blackLegals.contains(bm3));
        assertTrue(blackLegals.contains(bm4));
        assertTrue(blackLegals.contains(bm5));
        assertTrue(blackLegals.contains(bm6));
        assertTrue(blackLegals.contains(bm7));
        assertTrue(blackLegals.contains(bm8));
    }

    @Test
    public void testKnightInCorners() {
        final BoardConfigurator boardBuilder = new BoardConfigurator();
        boardBuilder.setPiece(new King(4, Utils.BLACK, false, false));
        boardBuilder.setPiece(new Knight(0, Utils.BLACK));
        boardBuilder.setPiece(new Knight(56, Utils.WHITE));
        boardBuilder.setPiece(new King(60, Utils.WHITE, false, false));
        boardBuilder.setMoveMaker(Utils.WHITE);
        final VirtualBoard board = boardBuilder.build();
        final Collection<Move> whiteLegals = board.getWhitePlayer().getUsableMoves();
        final Collection<Move> blackLegals = board.getBlackPlayer().getUsableMoves();
        assertEquals(whiteLegals.size(), 7);
        assertEquals(blackLegals.size(), 7);
        final Move wm1 = MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a1"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b3"));
        final Move wm2 = MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a1"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c2"));
        assertTrue(whiteLegals.contains(wm1));
        assertTrue(whiteLegals.contains(wm2));
        final Move bm1 = MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a8"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b6"));
        final Move bm2 = MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a8"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c7"));
        assertTrue(blackLegals.contains(bm1));
        assertTrue(blackLegals.contains(bm2));

    }

    @Test
    public void testMiddleBishopOnEmptyVirtualBoard() {
        final BoardConfigurator builder = new BoardConfigurator();
        // Black Layout
        builder.setPiece(new King(4, Utils.BLACK, false, false));
        // White Layout
        builder.setPiece(new Bishop(35, Utils.WHITE));
        builder.setPiece(new King(60, Utils.WHITE, false, false));
        // Set the current player
        builder.setMoveMaker(Utils.WHITE);
        //build the board
        final VirtualBoard board = builder.build();
        final Collection<Move> whiteLegals = board.getWhitePlayer().getUsableMoves();
        final Collection<Move> blackLegals = board.getBlackPlayer().getUsableMoves();
        assertEquals(whiteLegals.size(), 18);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a7"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b6"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c5"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e3"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f2"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g1"))));
    }

    @Test
    public void testTopLeftBishopOnEmptyVirtualBoard() {
        BoardConfigurator builder = new BoardConfigurator();
        // Black Layout
        builder.setPiece(new King(4, Utils.BLACK, false, false));
        // White Layout
        builder.setPiece(new Bishop(0, Utils.WHITE));
        builder.setPiece(new King(60, Utils.WHITE, false, false));
        // Set the current player
        builder.setMoveMaker(Utils.WHITE);
        //build the board
        final VirtualBoard board = builder.build();
        final Collection<Move> whiteLegals = board.getWhitePlayer().getUsableMoves();
        final Collection<Move> blackLegals = board.getBlackPlayer().getUsableMoves();
        assertEquals(board.getPiece(0), board.getPiece(0));
        assertNotNull(board.getPiece(0));
        assertEquals(whiteLegals.size(), 12);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a8"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b7"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a8"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c6"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a8"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d5"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a8"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a8"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f3"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a8"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g2"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a8"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h1"))));
    }

    @Test
    public void testTopRightBishopOnEmptyVirtualBoard() {
        BoardConfigurator builder = new BoardConfigurator();
        // Black Layout
        builder.setPiece(new King(4, Utils.BLACK, false, false));
        // White Layout
        builder.setPiece(new Bishop(7, Utils.WHITE));
        builder.setPiece(new King(60, Utils.WHITE, false, false));
        // Set the current player
        builder.setMoveMaker(Utils.WHITE);
        //build the board
        final VirtualBoard board = builder.build();
        final Collection<Move> whiteLegals = board.getWhitePlayer().getUsableMoves();
        final Collection<Move> blackLegals = board.getBlackPlayer().getUsableMoves();
        assertEquals(whiteLegals.size(), 12);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h8"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g7"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h8"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f6"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h8"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h8"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h8"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c3"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h8"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b2"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h8"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a1"))));
    }

    @Test
    public void testBottomLeftBishopOnEmptyVirtualBoard() {
        BoardConfigurator builder = new BoardConfigurator();
        // Black Layout
        builder.setPiece(new King(4, Utils.BLACK, false, false));
        // White Layout
        builder.setPiece(new Bishop(56, Utils.WHITE));
        builder.setPiece(new King(60, Utils.WHITE, false, false));
        // Set the current player
        builder.setMoveMaker(Utils.WHITE);
        //build the board
        final VirtualBoard board = builder.build();
        final Collection<Move> whiteLegals = board.getWhitePlayer().getUsableMoves();
        final Collection<Move> blackLegals = board.getBlackPlayer().getUsableMoves();
        assertEquals(whiteLegals.size(), 12);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a1"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b2"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a1"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c3"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a1"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a1"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a1"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f6"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a1"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g7"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a1"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h8"))));
    }

    @Test
    public void testBottomRightBishopOnEmptyVirtualBoard() {
        BoardConfigurator builder = new BoardConfigurator();
        // Black Layout
        builder.setPiece(new King(4, Utils.BLACK, false, false));
        // White Layout
        builder.setPiece(new Bishop(63, Utils.WHITE));
        builder.setPiece(new King(60, Utils.WHITE, false, false));
        // Set the current player
        builder.setMoveMaker(Utils.WHITE);
        //build the board
        final VirtualBoard board = builder.build();
        final Collection<Move> whiteLegals = board.getWhitePlayer().getUsableMoves();
        final Collection<Move> blackLegals = board.getBlackPlayer().getUsableMoves();
        assertEquals(whiteLegals.size(), 12);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h1"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g2"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h1"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f3"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h1"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h1"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d5"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h1"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c6"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h1"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b7"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h1"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a8"))));
    }

    @Test
    public void testMiddleRookOnEmptyBoard() {
        final BoardConfigurator builder = new BoardConfigurator();
        // Black Layout
        builder.setPiece(new King(4, Utils.BLACK, false, false));
        // White Layout
        builder.setPiece(new Rook(36, Utils.WHITE));
        builder.setPiece(new King(60, Utils.WHITE, false, false));
        // Set the current player
        builder.setMoveMaker(Utils.WHITE);
        final VirtualBoard board = builder.build();
        final Collection<Move> whiteLegals = board.getWhitePlayer().getUsableMoves();
        final Collection<Move> blackLegals = board.getBlackPlayer().getUsableMoves();
        assertEquals(whiteLegals.size(), 18);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e8"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e7"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e6"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e3"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e2"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a4"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("b4"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c4"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f4"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g4"))));
        assertTrue(whiteLegals.contains(MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h4"))));}

    @Test
    public void testPawnPromotion() {
        final BoardConfigurator builder = new BoardConfigurator();
        // Black Layout
        builder.setPiece(new Rook(3, Utils.BLACK));
        builder.setPiece(new King(22, Utils.BLACK, false, false));
        // White Layout
        builder.setPiece(new Pawn(15, Utils.WHITE));
        builder.setPiece(new King(52, Utils.WHITE, false, false));
        // Set the current player
        builder.setMoveMaker(Utils.WHITE);
        final VirtualBoard board = builder.build();
        final Move m1 = MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition(
                "h7"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h8"));
        final MoveTransition t1 = board.getCurrentPlayer().doMove(m1);
        Assert.assertTrue(t1.moveStatus().isDone());
        final Move m2 = MoveFactory.createMove(t1.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d8"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h8"));
        final MoveTransition t2 = t1.toBoard().getCurrentPlayer().doMove(m2);
        Assert.assertTrue(t2.moveStatus().isDone());
        final Move m3 = MoveFactory.createMove(t2.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e2"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d2"));
        final MoveTransition t3 = board.getCurrentPlayer().doMove(m3);
        Assert.assertTrue(t3.moveStatus().isDone());
    }

    @Test
    public void testSimpleWhiteEnPassant() {
        final BoardConfigurator builder = new BoardConfigurator();
        // Black Layout
        builder.setPiece(new King(4, Utils.BLACK, false, false));
        builder.setPiece(new Pawn(11, Utils.BLACK));
        // White Layout
        builder.setPiece(new Pawn(52, Utils.WHITE));
        builder.setPiece(new King(60, Utils.WHITE, false, false));
        // Set the current player
        builder.setMoveMaker(Utils.WHITE);
        final VirtualBoard board = builder.build();
        final Move m1 = MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition(
                "e2"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"));
        final MoveTransition t1 = board.getCurrentPlayer().doMove(m1);
        Assert.assertTrue(t1.moveStatus().isDone());
        final Move m2 = MoveFactory.createMove(t1.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e8"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d8"));
        final MoveTransition t2 = t1.toBoard().getCurrentPlayer().doMove(m2);
        Assert.assertTrue(t2.moveStatus().isDone());
        final Move m3 = MoveFactory.createMove(t2.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5"));
        final MoveTransition t3 = t2.toBoard().getCurrentPlayer().doMove(m3);
        Assert.assertTrue(t3.moveStatus().isDone());
        final Move m4 = MoveFactory.createMove(t3.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d7"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d5"));
        final MoveTransition t4 = t3.toBoard().getCurrentPlayer().doMove(m4);
        Assert.assertTrue(t4.moveStatus().isDone());
        final Move m5 = MoveFactory.createMove(t4.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d6"));
        final MoveTransition t5 = t4.toBoard().getCurrentPlayer().doMove(m5);
        Assert.assertTrue(t5.moveStatus().isDone());
    }

    @Test
    public void testSimpleBlackEnPassant() {
        final BoardConfigurator builder = new BoardConfigurator();
        // Black Layout
        builder.setPiece(new King(4, Utils.BLACK, false, false));
        builder.setPiece(new Pawn(11, Utils.BLACK));
        // White Layout
        builder.setPiece(new Pawn(52, Utils.WHITE));
        builder.setPiece(new King(60, Utils.WHITE, false, false));
        // Set the current player
        builder.setMoveMaker(Utils.WHITE);
        final VirtualBoard board = builder.build();
        final Move m1 = MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition(
                "e1"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d1"));
        final MoveTransition t1 = board.getCurrentPlayer().doMove(m1);
        assertTrue(t1.moveStatus().isDone());
        final Move m2 = MoveFactory.createMove(t1.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d7"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d5"));
        final MoveTransition t2 = t1.toBoard().getCurrentPlayer().doMove(m2);
        assertTrue(t2.moveStatus().isDone());
        final Move m3 = MoveFactory.createMove(t2.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d1"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c1"));
        final MoveTransition t3 = t2.toBoard().getCurrentPlayer().doMove(m3);
        assertTrue(t3.moveStatus().isDone());
        final Move m4 = MoveFactory.createMove(t3.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d5"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4"));
        final MoveTransition t4 = t3.toBoard().getCurrentPlayer().doMove(m4);
        assertTrue(t4.moveStatus().isDone());
        final Move m5 = MoveFactory.createMove(t4.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e2"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"));
        final MoveTransition t5 = t4.toBoard().getCurrentPlayer().doMove(m5);
        assertTrue(t5.moveStatus().isDone());
        final Move m6 = MoveFactory.createMove(t5.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e3"));
        final MoveTransition t6 = t5.toBoard().getCurrentPlayer().doMove(m6);
        Assert.assertTrue(t6.moveStatus().isDone());
    }

    @Test
    public void testEnPassant2() {
        final VirtualBoard board = VirtualBoard.initDefaultBoard();
        final Move m1 = MoveFactory.createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition(
                "e2"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e3"));
        final MoveTransition t1 = board.getCurrentPlayer().doMove(m1);
        assertTrue(t1.moveStatus().isDone());
        final Move m2 = MoveFactory.createMove(t1.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h7"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h5"));
        final MoveTransition t2 = t1.toBoard().getCurrentPlayer().doMove(m2);
        assertTrue(t2.moveStatus().isDone());
        final Move m3 = MoveFactory.createMove(t2.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e3"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"));
        final MoveTransition t3 = t2.toBoard().getCurrentPlayer().doMove(m3);
        assertTrue(t3.moveStatus().isDone());
        final Move m4 = MoveFactory.createMove(t3.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h5"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h4"));
        final MoveTransition t4 = t3.toBoard().getCurrentPlayer().doMove(m4);
        assertTrue(t4.moveStatus().isDone());
        final Move m5 = MoveFactory.createMove(t4.toBoard(), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g2"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g4"));
        final MoveTransition t5 = t4.toBoard().getCurrentPlayer().doMove(m5);
        assertTrue(t5.moveStatus().isDone());
    }

    @Test
    public void testKingEquality() {
        final VirtualBoard board = VirtualBoard.initDefaultBoard();
        final VirtualBoard board2 = VirtualBoard.initDefaultBoard();
        assertEquals(board.getPiece(60), board2.getPiece(60));
        TestCase.assertNotNull(board.getPiece(60));
    }

    @Test
    public void testHashCode() {
        final VirtualBoard board = VirtualBoard.initDefaultBoard();
        final Set<Piece> pieceSet = Sets.newHashSet(board.getAllPieces());
        final Set<Piece> whitePieceSet = Sets.newHashSet(board.getWhitePieces());
        final Set<Piece> blackPieceSet = Sets.newHashSet(board.getBlackPieces());
        assertEquals(32, pieceSet.size());
        assertEquals(16, whitePieceSet.size());
        assertEquals(16, blackPieceSet.size());
    }
}
