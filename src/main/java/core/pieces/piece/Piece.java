package core.pieces.piece;

import core.utils.Utils;
import lombok.Getter;

/**
 * Questa classe rappresenta la pedina che però deve essere implementata da ogni singola classe di pedina
 */
@Getter
public abstract class Piece implements IPiece {
    protected final int piecePosition;
    protected final Utils pieceUtils;
    protected final PieceType pieceType;
    protected final boolean isFirstMove;

    /**
     * @param pieceType tipo di pedina. 6 disponibili
     * @param piecePosition coordinata dove è posizionata
     * @param pieceUtils colore e altri utils come la direzione opposta, ecc...
     * @param isFirstMove indica se la pedina è alla prima mossa
     */
    public Piece(final PieceType pieceType, final int piecePosition, final Utils pieceUtils, final boolean isFirstMove) {
        this.pieceType = pieceType;
        this.piecePosition = piecePosition;
        this.pieceUtils = pieceUtils;
        this.isFirstMove = isFirstMove;
    }

    /**
     * Questo metodo serve a capire se un oggetto è lo stesso oggetto creato da una classe che estende {@link Piece}.
     * @param other oggetto da confrontare
     * @return "TRUE" se l'oggetto è uguale per indirizzo di memoria o se è un istanza o i suoi attributi solo uguali
     */
    @Override
    public boolean equals(final Object other) {
        if(this == other)
            return true;

        if(!(other instanceof final Piece otherPiece))
            return false;

        return this.piecePosition == otherPiece.getPiecePosition() && this.pieceType == otherPiece.getPieceType() &&
                this.pieceUtils == otherPiece.getPieceUtils() && this.isFirstMove == otherPiece.isFirstMove();
    }

    /**
     * Questo metodo serve per ritornare il valore del tipo di pedina.
     * Si potrebbe utilizzare anche il modo esteso inserito nel metodo in altre parti del codice.
     * È UN METODO WRAPPER
     * @return valore del tipo di pedina.
     */
    public int getPieceValue() {
        return this.pieceType.getPieceValue();
    }

    /**
     * @return il carattere identificativo di ogni pedina. Ogni tipo di pedina ha il suo
     */
    @Override
    public String toString() {
        return this.pieceType.toString();
    }
}
