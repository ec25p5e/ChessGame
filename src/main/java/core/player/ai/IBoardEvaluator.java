package core.player.ai;

import core.board.VirtualBoard;

public interface IBoardEvaluator {

    int evaluate(VirtualBoard board, int depth);
}
