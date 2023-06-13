package core.pieces.piece;

import lombok.Getter;

/**
 * Questa classe enum serve per contenere i vari tipi di pedina con un valore rappresentativo
 */
public enum PieceType {
    PAWN(100, "P", "Pawn.json"),
    KNIGHT(300, "N", "Knight.json"),
    BISHOP(330, "B", "Bishop.json"),
    ROOK(500, "R", "Rook.json"),
    QUEEN(900, "Q", "Queen.json"),
    KING(10000, "K", "King.json");

    private final int value;
    private final String pieceName;
    private final String drawFileName;

    PieceType(final int value, final String pieceName, final String drawFileName) {
        this.value = value;
        this.pieceName = pieceName;
        this.drawFileName = drawFileName;
    }

    /**
     * Metodo GETTER per incapsulare l'attributo "value"
     * @return valore numerico del tipo di pedina
     */
    public int getPieceValue() {
        return this.value;
    }

    /**
     * Metodo GETTER per incapsulare l'attributo che indica il nome del file di riferimento
     * @return stringa con il nome del file di riferimento
     */
    public String getDrawFileName() {
        return this.drawFileName;
    }

    @Override
    public String toString() {
        return this.pieceName;
    }
}
