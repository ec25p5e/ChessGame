import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestPieces.class,
        TestBoard.class,
        TestStaleMate.class,
        TestPlayer.class,
        TestCastling.class,
        TestCheckMate.class,
        /* TestMiniMax.class,
        TestAlphaBeta.class,
        TestPawnStructure.class,
        TestFENParser.class,
        */})
public class ChessTestSuite {
}