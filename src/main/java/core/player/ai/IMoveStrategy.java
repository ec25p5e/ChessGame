package core.player.ai;

import core.board.VirtualBoard;
import core.movements.Move;

public interface IMoveStrategy {

    long getNumBoardsEvauated();

    Move execute(VirtualBoard board);
}
