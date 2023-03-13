package core.pieces.piece;

import lombok.Getter;

/**
 * Questa classe enum serve per contenere i vari tipi di pedina con un valore rappresentativo
 */
public enum PieceType {
    PAWN(100, "P"),
    KNIGHT(300, "N"),
    BISHOP(330, "B"),
    ROOK(500, "R"),
    QUEEN(900, "Q"),
    KING(10000, "K");

    private final int value;
    private final String pieceName;

    PieceType(final int value, final String pieceName) {
        this.value = value;
        this.pieceName = pieceName;
    }

    /**
     * Metodo GETTER per incapsulare l'attributo "value"
     * @return valore numerico del tipo di pedina
     */
    public int getPieceValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.pieceName;
    }
}
