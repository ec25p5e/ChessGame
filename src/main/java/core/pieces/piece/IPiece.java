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

    /**
     * Questo metodo serve per ritornare un numero intero che indica il bonus
     * del pedone per la coordinata e il tipo.
     * Questo valore viene usato principalmente dall'AI per valutare la scacchiera
     * Tutto questo ha poi una teoria che spiegherò nella wiki github
     * @return numero intero positivo o negativo
     */
    int locationBonus();

    /**
     * @return il carattere identificativo di ogni pedina. Ogni tipo di pedina ha il suo
     */
    String toString();

    /**
     * Questo metodo indica se il pedone è in castling
     * Viene utilizzato solo dai RE
     * @return valore booleano
     */
    boolean isCastled();

    /**
     * Questo metodo serve a indicare se è in castling dal
     * RE. È specifico per il metodo "isCastled"
     * @return valore booleano
     */
    boolean isCastledByKing();

    /**
     * Questo metodo serve a indicare se è in castling dalla
     * Regina. È specifico per il metodo "isCastled"
     * @return valore booleano
     */
    boolean isCastledByQueen();
}
