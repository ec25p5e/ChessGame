package core.player.ai;

import core.board.VirtualBoard;

public interface IBoardEvaluator {

    /**
     * Questo metodo serve per valutare la scacchiera, come un'entry point method
     * @param board scacchiera virtuale di riferimento
     * @param depth profondità di pensiero del valutatore
     * @return numero intero
     */
    int evaluate(VirtualBoard board, int depth);
}
