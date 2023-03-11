package core.pieces.piece;

import core.board.VirtualBoard;
import core.movements.Move;

import java.util.Collection;

public interface IPiece {

    /**
     * Questo metodo viene utilizzato per rilevare quali sono le mosse possibili per la pedina
     * @param board scacchiera virtuale di riferimento
     * @return Una lista di mosse praticabili dalla pedina allo stato corrente
     */
    Collection<Move> calculateMoves(final VirtualBoard board);

    /**
     * Questo metodo serve per ritornare la pedina alla coordinata in base al movimento scelto
     * @param move mossa scelta dopo aver rilevato la sorgente e la destinazione.
     * @return Una pedina
     */
    Piece movePiece(final Move move);
}
