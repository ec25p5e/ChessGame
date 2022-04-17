package core.pieces.piece;

import core.Utils;
import lombok.Getter;

@Getter
public abstract class Piece implements IPiece {
    protected final PieceType pieceType;
    protected final Utils pieceUtils;
    protected int piecePosition;
    protected final boolean isFirstMove;

    public Piece(final PieceType pieceType, final Utils pieceUtils, final int piecePosition, final boolean isFirstMove) {
        this.pieceType = pieceType;
        this.pieceUtils = pieceUtils;
        this.piecePosition = piecePosition;
        this.isFirstMove = isFirstMove;
    }

    @Override
    public boolean equals(final Object other) {
        return false;
    }

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
}
