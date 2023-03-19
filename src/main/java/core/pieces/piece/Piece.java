package core.pieces.piece;

import core.board.VirtualBoardUtils;
import core.utils.Utils;
import lombok.Getter;

/**
 * Questa classe rappresenta la pedina che però deve essere implementata da ogni singola classe di pedina
 */
@Getter
public abstract class Piece implements IPiece {
    protected final String pieceCoordinate;
    protected final Utils pieceUtils;
    protected final PieceType pieceType;
    protected final boolean isFirstMove;
    protected transient final int piecePosition;
    protected transient final int cachedHashCode;

    /**
     * @param pieceType tipo di pedina. 6 disponibili
     * @param piecePosition coordinata dove è posizionata
     * @param pieceUtils colore e altri utils come la direzione opposta, ecc...
     * @param isFirstMove indica se la pedina è alla prima mossa
     */
    public Piece(final PieceType pieceType, final int piecePosition, final Utils pieceUtils, final boolean isFirstMove) {
        this.pieceType = pieceType;
        this.piecePosition = piecePosition;
        this.pieceCoordinate = VirtualBoardUtils.INSTANCE.getPositionAtCoordinate(this.piecePosition);
        this.pieceUtils = pieceUtils;
        this.isFirstMove = isFirstMove;
        this.cachedHashCode = computeHashCode();
    }

    /**
     * Questo costruttore viene utilizzato quando viene deserializzato il file con le pedine alle posizioni
     * @param pieceType tipo di pedina. 6 disponibili
     * @param pieceCoordinate coordinata dove è posizionata. ex: a5
     * @param pieceUtils colore e altri utils come la direzione opposta, ecc...
     * @param isFirstMove indica se la pedina è alla prima mossa
     */
    public Piece(final PieceType pieceType, final String pieceCoordinate, final Utils pieceUtils, final boolean isFirstMove) {
        this.pieceType = pieceType;
        this.piecePosition = VirtualBoardUtils.INSTANCE.getCoordinateAtPosition(pieceCoordinate);
        this.pieceCoordinate = pieceCoordinate;
        this.pieceUtils = pieceUtils;
        this.isFirstMove = isFirstMove;
        this.cachedHashCode = computeHashCode();
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
     * Getter per ritornare l'hashCode della pedina
     * @return hashcode della pedina
     */
    @Override
    public int hashCode() {
        return this.cachedHashCode;
    }

    /**
     * Questo metodo indica se il pedone è in castling
     * Viene utilizzato solo dai RE
     *
     * @return valore booleano
     */
    @Override
    public boolean isCastled() {
        return false;
    }

    /**
     * Questo metodo serve a indicare se è in castling dal
     * RE. È specifico per il metodo "isCastled"
     *
     * @return valore booleano
     */
    @Override
    public boolean isCastledByKing() {
        return false;
    }

    /**
     * Questo metodo serve a indicare se è in castling dalla
     * Regina. È specifico per il metodo "isCastled"
     *
     * @return valore booleano
     */
    @Override
    public boolean isCastledByQueen() {
        return false;
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
     * Questo metodo serve a calcolare l'hashcode della pedina
     * @return numero intero utilizzato come hash
     */
    private int computeHashCode() {
        int result = this.pieceType.hashCode();
        result = 31 * result + this.pieceUtils.hashCode();
        result = 31 * result + this.piecePosition;
        result = 31 * result + (this.isFirstMove ? 1 : 0);

        return result;
    }
}
