package core.movements;

import core.board.VirtualBoardUtils;
import core.board.VirtualBoard;
import core.pieces.piece.Piece;

/**
 * Questa classe serve a rappresentare il movimento semplice del pedone
 */
public class PawnMove extends Move {

    /**
     * @param board scacchiera virtuale di riferimento
     * @param pieceToMove pedina da muovere
     * @param destinationCoordinate coordinata di destinazione
     */
    public PawnMove(final VirtualBoard board, final Piece pieceToMove, final int destinationCoordinate) {
        super(board, pieceToMove, destinationCoordinate);
    }

    /**
     * Questo metodo serve per verificare se un oggetto è uguale a un oggetto dell'istanza {@link PawnMove}
     * @param other oggetto da confrontare
     * @return valore booleano uguale a "TRUE" se l'oggetto è un'istanza di {@link PawnMove} o se è lo stesso indirizzo di memoria.
     */
    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof PawnMove && super.equals(other);
    }

    /**
     * @return una stringa contenente la destinazione della mossa
     */
    @Override
    public String toString() {
        return VirtualBoardUtils.INSTANCE.getPositionAtCoordinate(this.destinationCoordinate);
    }
}
