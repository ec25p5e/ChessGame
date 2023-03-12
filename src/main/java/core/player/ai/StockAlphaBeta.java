package core.player.ai;

import core.board.VirtualBoard;
import core.movements.Move;

import java.util.Observable;

public class StockAlphaBeta extends Observable implements IMoveStrategy {
    private final int searchDepth;

    public StockAlphaBeta(final int searchDepth) {
        this.searchDepth = searchDepth;
    }

    @Override
    public long getNumBoardsEvauated() {
        return 0;
    }

    @Override
    public Move execute(final VirtualBoard board) {
        return null;
    }
}
