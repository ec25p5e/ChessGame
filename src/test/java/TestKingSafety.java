import core.board.BoardConfigurator;
import core.board.VirtualBoard;
import core.pieces.*;
import core.utils.Utils;
import org.junit.Test;

public class TestKingSafety {

    @Test
    public void test1() {
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

        //assertEquals(KingSafetyAnalyzer.get().calculateKingTropism(board.whitePlayer()).tropismScore(), 40);
    }

}