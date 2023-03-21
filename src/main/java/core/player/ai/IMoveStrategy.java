package core.player.ai;

import core.board.VirtualBoard;
import core.move.Move;

public interface IMoveStrategy {

    /**
     * Questo metodo serve per eseguire la logica della AI
     * @param board scacchiera virtuale di riferimento
     * @return la mossa "migliore" da eseguire secondo l'AI
     */
    Move execute(VirtualBoard board);
}
