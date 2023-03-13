package core.player.ai;

import core.board.VirtualBoard;

public class StandardBoardEvaluator implements IBoardEvaluator {

    private static final StandardBoardEvaluator INSTANCE = new StandardBoardEvaluator();

    private StandardBoardEvaluator(){
    }

    public static StandardBoardEvaluator get() {
        return INSTANCE;
    }

    @Override
    public int evaluate(final VirtualBoard board, final int depth) {
        return 0;
    }
}
