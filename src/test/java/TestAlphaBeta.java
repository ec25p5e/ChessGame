import core.board.VirtualBoard;
import core.board.BoardConfigurator;
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

public class TestAlphaBeta {

    @Test
    public void testOpeningDepth4BlackMovesFirst() {
        final BoardConfigurator builder = new BoardConfigurator();
        // Black Layout
        builder.setPiece(new Rook(0, Utils.BLACK));
        builder.setPiece(new Knight(1, Utils.BLACK));
        builder.setPiece(new Bishop(2, Utils.BLACK));
        builder.setPiece(new Queen(3, Utils.BLACK));
        builder.setPiece(new King(4, Utils.BLACK, false, false));
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
        builder.setPiece(new King(60, Utils.WHITE, false, false));
        builder.setPiece(new Bishop(61, Utils.WHITE));
        builder.setPiece(new Knight(62, Utils.WHITE));
        builder.setPiece(new Rook(63, Utils.WHITE));
        // Set the current player
        builder.setMoveMaker(Utils.BLACK);
        final VirtualBoard board = builder.build();
        System.out.println(FenUtilities.createFENFromGame(board));
        final IMoveStrategy alphaBeta = new StockAlphaBeta(4);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e7"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5")));
    }

    @Test
    public void advancedLevelProblem2NakamuraShirov() {
        final BoardConfigurator builder = new BoardConfigurator();
        // Black Layout
        builder.setPiece(new King(5, Utils.BLACK, false, false));
        builder.setPiece(new Pawn(10, Utils.BLACK));
        builder.setPiece(new Rook(25, Utils.BLACK));
        builder.setPiece(new Bishop(29, Utils.BLACK));
        // White Layout
        builder.setPiece(new Knight(27, Utils.WHITE));
        builder.setPiece(new Rook(36, Utils.WHITE));
        builder.setPiece(new Pawn(39, Utils.WHITE));
        builder.setPiece(new King(42, Utils.WHITE, false, false));
        builder.setPiece(new Pawn(46, Utils.WHITE));
        // Set the current player
        builder.setMoveMaker(Utils.WHITE);

        final VirtualBoard board = builder.build();
        final IMoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d5"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c7")));
    }

    @Test
    public void eloTest1() {
        final BoardConfigurator builder = new BoardConfigurator();
        // Black Layout
        builder.setPiece(new Rook(0, Utils.BLACK));
        builder.setPiece(new Bishop(2, Utils.BLACK));
        builder.setPiece(new King(6, Utils.BLACK, false, false));
        builder.setPiece(new Pawn(14, Utils.BLACK));
        builder.setPiece(new Knight(18, Utils.BLACK));
        builder.setPiece(new Pawn(20, Utils.BLACK));
        builder.setPiece(new Rook(21, Utils.BLACK));
        builder.setPiece(new Pawn(23, Utils.BLACK));
        builder.setPiece(new Queen(24, Utils.BLACK));
        builder.setPiece(new Pawn(26, Utils.BLACK));
        builder.setPiece(new Bishop(33, Utils.BLACK));
        // White Layout
        builder.setPiece(new Pawn(16, Utils.WHITE));
        builder.setPiece(new Pawn(35, Utils.WHITE));
        builder.setPiece(new Knight(42, Utils.WHITE));
        builder.setPiece(new Knight(45, Utils.WHITE));
        builder.setPiece(new Pawn(48, Utils.WHITE));
        builder.setPiece(new Pawn(49, Utils.WHITE));
        builder.setPiece(new Queen(51, Utils.WHITE));
        builder.setPiece(new Bishop(52, Utils.WHITE));
        builder.setPiece(new Pawn(53, Utils.WHITE));
        builder.setPiece(new Pawn(54, Utils.WHITE));
        builder.setPiece(new Pawn(55, Utils.WHITE));
        builder.setPiece(new Rook(56, Utils.WHITE));
        builder.setPiece(new King(60, Utils.WHITE, false, false));
        builder.setPiece(new Rook(63, Utils.WHITE));
        // Set the current player
        builder.setMoveMaker(Utils.BLACK);
        final VirtualBoard board = builder.build();
        final String fen = FenUtilities.createFENFromGame(board);
        System.out.println(fen);
        final IMoveStrategy alphaBeta = new StockAlphaBeta(8);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c8"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a6")));
    }

    @Test
    public void testQualityDepth6() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("4k2r/1R3R2/p3p1pp/4b3/1BnNr3/8/P1P5/5K2 w - - 1 0");
        final IMoveStrategy alphaBeta = new StockAlphaBeta(7);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f7"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e7")));
    }

    @Test
    public void testQualityTwoDepth6() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("6k1/3b3r/1p1p4/p1n2p2/1PPNpP1q/P3Q1p1/1R1RB1P1/5K2 b - - 0-1");
        final IMoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f4")));
    }

    @Test
    public void testQualityThreeDepth6() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("r2r1n2/pp2bk2/2p1p2p/3q4/3PN1QP/2P3R1/P4PP1/5RK1 w - - 0 1");
        final IMoveStrategy alphaBeta = new StockAlphaBeta(7);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g7")));
    }

    @Test
    public void testQualityFourDepth6() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("r1b1k2r/pp3pbp/1qn1p1p1/2pnP3/3p1PP1/1P1P1NBP/P1P5/RN1QKB1R b KQkq - 2 11");
        final IMoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e8"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g8")));
    }

    @Test
    public void eloTest2() {
        final BoardConfigurator builder = new BoardConfigurator();
        // Black Layout
        builder.setPiece(new Knight(2, Utils.BLACK));
        builder.setPiece(new Queen(3, Utils.BLACK));
        builder.setPiece(new Knight(5, Utils.BLACK));
        builder.setPiece(new King(6, Utils.BLACK, false, false));
        builder.setPiece(new Pawn(13, Utils.BLACK));
        builder.setPiece(new Pawn(15, Utils.BLACK));
        builder.setPiece(new Pawn(20, Utils.BLACK));
        builder.setPiece(new Pawn(22, Utils.BLACK));
        builder.setPiece(new Pawn(24, Utils.BLACK));
        builder.setPiece(new Bishop(25, Utils.BLACK));
        builder.setPiece(new Pawn(27, Utils.BLACK));
        builder.setPiece(new Pawn(33, Utils.BLACK));
        // White Layout
        builder.setPiece(new Queen(23, Utils.WHITE));
        builder.setPiece(new Pawn(28, Utils.WHITE));
        builder.setPiece(new Knight(30, Utils.WHITE));
        builder.setPiece(new Pawn(31, Utils.WHITE));
        builder.setPiece(new Pawn(35, Utils.WHITE));
        builder.setPiece(new Pawn(38, Utils.WHITE));
        builder.setPiece(new Pawn(41, Utils.WHITE));
        builder.setPiece(new Knight(46, Utils.WHITE));
        builder.setPiece(new Pawn(48, Utils.WHITE));
        builder.setPiece(new Pawn(53, Utils.WHITE));
        builder.setPiece(new Bishop(54, Utils.WHITE));
        builder.setPiece(new King(62, Utils.WHITE, false, false));
        // Set the current player
        builder.setMoveMaker(Utils.WHITE);
        final VirtualBoard board = builder.build();
        System.out.println(FenUtilities.createFENFromGame(board));
        final IMoveStrategy alphaBeta = new StockAlphaBeta(8);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h5"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g6")));
    }

    @Test
    public void eloTest3() {
        final BoardConfigurator builder = new BoardConfigurator();
        // Black Layout
        builder.setPiece(new Rook(11, Utils.BLACK));
        builder.setPiece(new Pawn(14, Utils.BLACK));
        builder.setPiece(new Pawn(16, Utils.BLACK));
        builder.setPiece(new Pawn(17, Utils.BLACK));
        builder.setPiece(new Pawn(20, Utils.BLACK));
        builder.setPiece(new Pawn(22, Utils.BLACK));
        builder.setPiece(new King(25, Utils.BLACK, false, false));
        builder.setPiece(new Knight(33, Utils.BLACK));
        // White Layout
        builder.setPiece(new Bishop(19, Utils.WHITE));
        builder.setPiece(new Pawn(26, Utils.WHITE));
        builder.setPiece(new King(36, Utils.WHITE, false, false));
        builder.setPiece(new Rook(46, Utils.WHITE));
        builder.setPiece(new Pawn(49, Utils.WHITE));
        builder.setPiece(new Pawn(53, Utils.WHITE));
        // Set the current player
        builder.setMoveMaker(Utils.WHITE);
        final VirtualBoard board = builder.build();
        System.out.println(FenUtilities.createFENFromGame(board));
        final IMoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g3"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g6")));
    }

    @Test
    public void blackWidowLoss1() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("r2qkb1r/3p1pp1/p1n1p2p/1p1bP3/P2p4/1PP5/5PPP/RNBQNRK1 w kq - 0 13");
        final IMoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("c3"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d4")));
    }

    @Test
    public void testCheckmateHorizon() {
        final BoardConfigurator builder = new BoardConfigurator();
        // Black Layout
        builder.setPiece(new Rook(11, Utils.BLACK));
        builder.setPiece(new Pawn(16, Utils.BLACK));
        builder.setPiece(new Bishop(27, Utils.BLACK));
        builder.setPiece(new King(29, Utils.BLACK, false, false));
        // White Layout
        builder.setPiece(new Rook(17, Utils.WHITE));
        builder.setPiece(new Rook(26, Utils.WHITE));
        builder.setPiece(new Pawn(35, Utils.WHITE));
        builder.setPiece(new Pawn(45, Utils.WHITE));
        builder.setPiece(new Bishop(51, Utils.WHITE));
        builder.setPiece(new Pawn(54, Utils.WHITE));
        builder.setPiece(new Pawn(55, Utils.WHITE));
        builder.setPiece(new King(63, Utils.WHITE, false, false));
        // Set the current player
        builder.setMoveMaker(Utils.WHITE);
        final VirtualBoard board = builder.build();
        final IMoveStrategy alphaBeta = new StockAlphaBeta(4);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g2"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g4")));
    }

    @Test
    public void testBlackInTrouble() {
        final BoardConfigurator builder = new BoardConfigurator();
        // Black Layout
        builder.setPiece(new King(7, Utils.BLACK, false, false));
        builder.setPiece(new Pawn(8, Utils.BLACK));
        builder.setPiece(new Pawn(9, Utils.BLACK));
        builder.setPiece(new Pawn(10, Utils.BLACK));
        builder.setPiece(new Queen(11, Utils.BLACK));
        builder.setPiece(new Rook(14, Utils.BLACK));
        builder.setPiece(new Pawn(15, Utils.BLACK));
        builder.setPiece(new Bishop(17, Utils.BLACK));
        builder.setPiece(new Knight(18, Utils.BLACK));
        builder.setPiece(new Pawn(19, Utils.BLACK));
        builder.setPiece(new Pawn(21, Utils.BLACK));
        // White Layout
        builder.setPiece(new Knight(31, Utils.WHITE));
        builder.setPiece(new Pawn(35, Utils.WHITE));
        builder.setPiece(new Rook(36, Utils.WHITE));
        builder.setPiece(new Queen(46, Utils.WHITE));
        builder.setPiece(new Pawn(48, Utils.WHITE));
        builder.setPiece(new Pawn(53, Utils.WHITE));
        builder.setPiece(new Pawn(54, Utils.WHITE));
        builder.setPiece(new Pawn(55, Utils.WHITE));
        builder.setPiece(new King(62, Utils.WHITE, false, false));
        // Set the current player
        builder.setMoveMaker(Utils.WHITE);
        final VirtualBoard board = builder.build();
        final IMoveStrategy alphaBeta = new StockAlphaBeta(4);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e8")));
    }

    @Test
    public void findMate3() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("5rk1/5Npp/8/3Q4/8/8/8/7K w - - 0");
        final IMoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f7"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h6")));
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(bestMove);
        assertTrue(t1.moveStatus().isDone());
    }

    @Test
    public void runawayPawnMakesIt() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("2k5/8/8/8/p7/8/8/4K3 b - - 0 1");
        final IMoveStrategy alphaBeta = new StockAlphaBeta(5);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("a3")));
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(bestMove);
        assertTrue(t1.moveStatus().isDone());
    }

    @Test
    public void testMackHackScenario() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("1r1k1r2/p5Q1/2p3p1/8/1q1p2n1/3P2P1/P3RPP1/4RK2 b - - 0 1");
        final IMoveStrategy alphaBeta = new StockAlphaBeta(8);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f8"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("f2")));
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(bestMove);
        assertTrue(t1.moveStatus().isDone());
    }

    @Test
    public void testAutoResponseVsPrinChess() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("r2q1rk1/p1p2pp1/3p1b2/2p2QNb/4PB1P/6R1/PPPR4/2K5 b - - 0 1");
        final IMoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("h5"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("g6")));
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(bestMove);
        assertTrue(t1.moveStatus().isDone());
    }

    @Test
    public void testBratcoKopec1() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("1k1r4/pp1b1R2/3q2pp/4p3/2B5/4Q3/PPP2B2/2K5 b - - 0 1");
        final IMoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d6"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d1")));
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(bestMove);
        assertTrue(t1.moveStatus().isDone());
    }

    @Test
    public void testBratcoKopec2() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("3r1k2/4npp1/1ppr3p/p6P/P2PPPP1/1NR5/5K2/2R5 w - - 0 1");
        final IMoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e4"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("e5")));
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(bestMove);
        assertTrue(t1.moveStatus().isDone());
    }

    @Test
    public void testBratcoKopec19() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("3rr3/2pq2pk/p2p1pnp/8/2QBPP2/1P6/P5PP/4RRK1 b - -");
        final IMoveStrategy alphaBeta = new StockAlphaBeta(2);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, MoveFactory
                .createMove(board, VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d6"), VirtualBoardUtils.INSTANCE.getCoordinateAtPosition("d5")));
        final MoveTransition t1 = board.getCurrentPlayer()
                .doMove(bestMove);
        assertTrue(t1.moveStatus().isDone());
    }

}
