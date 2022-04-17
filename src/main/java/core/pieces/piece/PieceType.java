package core.pieces.piece;

import lombok.Getter;

/**
 *
 */
public enum PieceType {
    PAWN(100, "P"),
    KNIGHT(300, "N"),
    BISHOP(330, "B"),
    ROOK(500, "R"),
    QUEEN(900, "Q"),
    KING(10000, "K");

    @Getter
    private final int val;
    private final String pieceName;

    PieceType(final int val, final String pieceName) {
        this.val = val;
        this.pieceName = pieceName;
    }

    @Override
    public String toString() {
        return this.pieceName;
    }
}
