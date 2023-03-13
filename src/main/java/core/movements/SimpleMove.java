package core.movements;

import core.board.VirtualBoardUtils;
import core.board.VirtualBoard;
import core.pieces.piece.Piece;

/**
 * Questa classe si occupa di effettuare un movimento sulla scacchiera.
 * Un movimento da una coordinata all'altra senza dover saltare/mangiare altre pedine
 */
public class SimpleMove extends Move {

    /**
     * @param board scacchiera virtuale di riferimento
     * @param pieceToMove pedina che si vuole muovere
     * @param destinationCoordinate coordinata di destinazione della pedina
     */
    public SimpleMove(final VirtualBoard board, final Piece pieceToMove, final int destinationCoordinate) {
        super(board, pieceToMove, destinationCoordinate);
    }

    /**
     * Questo metodo serve per verificare se un oggetto è uguale a un oggetto dell'istanza {@link SimpleMove}
     * @param other oggetto da confrontare
     * @return valore booleano uguale a "TRUE" se l'oggetto è un'istanza di {@link SimpleMove} o se è lo stesso indirizzo di memoria.
     */
    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof SimpleMove && super.equals(other);
    }

    /**
     * @return una stringa contenente il carattere che identifica la pedina, concatenato con la posizione
     */
    @Override
    public String toString() {
        return this.pieceToMove.getPieceType().toString() + this.disambiguationFile()
                + VirtualBoardUtils.INSTANCE.getPositionAtCoordinate(this.destinationCoordinate);
    }
}
