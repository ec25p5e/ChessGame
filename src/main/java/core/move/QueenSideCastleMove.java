package core.move;

import core.board.VirtualBoard;
import core.pieces.Rook;
import core.pieces.piece.Piece;

/**
 * Questa classe serve per creare una mossa d'arrocco verso il lato della regina
 */
public class QueenSideCastleMove extends CastleMove {
    public QueenSideCastleMove(final VirtualBoard board, final Piece pieceToMove, final int destinationCoordinate, final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
        super(board, pieceToMove, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
    }

    /**
     * Questo metodo serve per confrontare un oggetto a un oggetto della classe {@link QueenSideCastleMove}
     * @param other oggetto da confrontare
     * @return valore booleano di valore "TRUE" se l'oggetto è un'istanza di {@link QueenSideCastleMove}
     * o se sono uguali tramite i parametri e l'indirizzo di memoria
     */
    @Override
    public boolean equals(final Object other) {
        if (this == other)
            return true;

        if (!(other instanceof final QueenSideCastleMove otherKingSideCastleMove))
            return false;

        return super.equals(otherKingSideCastleMove) && this.getCastleRook().equals(otherKingSideCastleMove.getCastleRook());
    }

    /**
     * @return la stesura per quando fa un arrocco del re "O-O"
     */
    @Override
    public String toString() {
        return "O-O-O";
    }
}
