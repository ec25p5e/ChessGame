import core.board.BoardConfigurator;
import core.board.VirtualBoard;
import core.pieces.*;
import core.player.ai.PawnStructureAnalyzer;
import core.player.ai.StandardBoardEvaluator;
import core.utils.Utils;
import org.junit.Test;
import pgn.FenUtilities;

import static org.junit.Assert.assertEquals;

public class TestPawnStructure {

    @Test
    public void testIsolatedPawnsOnStandardVirtualBoard() {
        final VirtualBoard board = VirtualBoard.initDefaultBoard();
        assertEquals(PawnStructureAnalyzer.get().isolatedPawnPenalty(board.getWhitePlayer()), 0);
        assertEquals(PawnStructureAnalyzer.get().isolatedPawnPenalty(board.getBlackPlayer()), 0);
    }

    @Test
    public void testIsolatedPawnByExample1() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("r1bq1rk1/pp2bppp/1np2n2/6B1/3P4/1BNQ4/PP2NPPP/R3R1K1 b - - 0 13");
        assertEquals(PawnStructureAnalyzer.get().isolatedPawnPenalty(board.getWhitePlayer()), PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY);
        assertEquals(PawnStructureAnalyzer.get().isolatedPawnPenalty(board.getBlackPlayer()), 0);
    }

    @Test
    public void testIsolatedPawnByExample2() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("r1bq1rk1/p3bppp/1np2n2/6B1/3P4/1BNQ4/PP2NPPP/R3R1K1 b - - 0 1");
        assertEquals(PawnStructureAnalyzer.get().isolatedPawnPenalty(board.getWhitePlayer()), PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY);
        assertEquals(PawnStructureAnalyzer.get().isolatedPawnPenalty(board.getBlackPlayer()), PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY * 2);
    }

    @Test
    public void testIsolatedPawnByExample3() {
        final BoardConfigurator builder = new BoardConfigurator();
        // Black Layout
        builder.setPiece(new King(4, Utils.BLACK, false, false));
        builder.setPiece(new Pawn(12, Utils.BLACK));
        builder.setPiece(new Pawn(20, Utils.BLACK));
        builder.setPiece(new Pawn(28, Utils.BLACK));
        builder.setPiece(new Pawn(8, Utils.BLACK));
        builder.setPiece(new Pawn(16, Utils.BLACK));
        // White Layout
        builder.setPiece(new Pawn(52, Utils.WHITE));
        builder.setPiece(new King(60, Utils.WHITE, false, false));
        builder.setMoveMaker(Utils.WHITE);
        // Set the current player
        final VirtualBoard board = builder.build();
        System.out.println(FenUtilities.createFENFromGame(board));

        assertEquals(PawnStructureAnalyzer.get().isolatedPawnPenalty(board.getWhitePlayer()), PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY);
        assertEquals(PawnStructureAnalyzer.get().isolatedPawnPenalty(board.getBlackPlayer()), PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY * 5);
    }

    @Test
    public void testIsolatedPawnByExample4() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("4k3/2p1p1p1/8/8/8/8/2P1P1P1/4K3 w KQkq -");
        assertEquals(PawnStructureAnalyzer.get().isolatedPawnPenalty(board.getWhitePlayer()), PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY * 3);
        assertEquals(PawnStructureAnalyzer.get().isolatedPawnPenalty(board.getBlackPlayer()), PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY * 3);
        final StandardBoardEvaluator boardEvaluator = StandardBoardEvaluator.get();
        assertEquals(boardEvaluator.evaluate(board, 1), 0);
    }

    @Test
    public void testIsolatedPawnByExample5() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("6k1/p6p/8/8/8/8/P6P/6K1 b - - 0 1");
        assertEquals(PawnStructureAnalyzer.get().isolatedPawnPenalty(board.getWhitePlayer()), PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY * 2);
        assertEquals(PawnStructureAnalyzer.get().isolatedPawnPenalty(board.getBlackPlayer()), PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY * 2);
        final StandardBoardEvaluator boardEvaluator = StandardBoardEvaluator.get();
        assertEquals(boardEvaluator.evaluate(board, 1), 0);
    }

    @Test
    public void testIsolatedPawnByExample6() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("6k1/4p3/4p3/8/8/4P3/4P3/6K1 b - - 0 1");
        assertEquals(PawnStructureAnalyzer.get().isolatedPawnPenalty(board.getWhitePlayer()), PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY * 2);
        assertEquals(PawnStructureAnalyzer.get().isolatedPawnPenalty(board.getBlackPlayer()), PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY * 2);
        final StandardBoardEvaluator boardEvaluator = StandardBoardEvaluator.get();
        assertEquals(boardEvaluator.evaluate(board, 1), 0);
    }

    @Test
    public void testDoubledPawnByExample1() {
        final VirtualBoard board = VirtualBoard.initDefaultBoard();
        assertEquals(PawnStructureAnalyzer.get().doubledPawnPenalty(board.getWhitePlayer()), 0);
        assertEquals(PawnStructureAnalyzer.get().doubledPawnPenalty(board.getBlackPlayer()), 0);
        final StandardBoardEvaluator boardEvaluator = StandardBoardEvaluator.get();
        assertEquals(boardEvaluator.evaluate(board, 1), 0);
    }

    @Test
    public void testDoubledPawnByExample2() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("6k1/4p3/4p3/8/8/4P3/4P3/6K1 b - - 0 1");
        assertEquals(PawnStructureAnalyzer.get().doubledPawnPenalty(board.getWhitePlayer()), PawnStructureAnalyzer.DOUBLED_PAWN_PENALTY * 2);
        assertEquals(PawnStructureAnalyzer.get().doubledPawnPenalty(board.getBlackPlayer()), PawnStructureAnalyzer.DOUBLED_PAWN_PENALTY * 2);
        final StandardBoardEvaluator boardEvaluator = StandardBoardEvaluator.get();
        assertEquals(boardEvaluator.evaluate(board, 1), 0);
    }

    @Test
    public void testDoubledPawnByExample3() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("6k1/8/8/P7/P7/P7/8/6K1 b - - 0 1");
        assertEquals(PawnStructureAnalyzer.get().doubledPawnPenalty(board.getWhitePlayer()), PawnStructureAnalyzer.DOUBLED_PAWN_PENALTY * 3);
        assertEquals(PawnStructureAnalyzer.get().doubledPawnPenalty(board.getBlackPlayer()), 0);
    }

    @Test
    public void testDoubledPawnByExample4() {
        final VirtualBoard board = FenUtilities.createGameFromFEN("6k1/8/8/P6p/P6p/P6p/8/6K1 b - - 0 1");
        assertEquals(PawnStructureAnalyzer.get().doubledPawnPenalty(board.getWhitePlayer()), PawnStructureAnalyzer.DOUBLED_PAWN_PENALTY * 3);
        assertEquals(PawnStructureAnalyzer.get().doubledPawnPenalty(board.getBlackPlayer()), PawnStructureAnalyzer.DOUBLED_PAWN_PENALTY * 3);
        assertEquals(PawnStructureAnalyzer.get().isolatedPawnPenalty(board.getWhitePlayer()), PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY * 3);
        assertEquals(PawnStructureAnalyzer.get().isolatedPawnPenalty(board.getBlackPlayer()), PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY * 3);
    }


}