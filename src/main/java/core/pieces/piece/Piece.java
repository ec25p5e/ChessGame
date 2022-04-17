package core.pieces.piece;

import lombok.Getter;

@Getter
public abstract class Piece implements IPiece {
    protected final PieceType pieceType;
    protected int piecePosition;
    protected final boolean isFirstMove;

    public Piece(final PieceType pieceType, final int piecePosition, final boolean isFirstMove) {
        this.pieceType = pieceType;
        this.piecePosition = piecePosition;
        this.isFirstMove = isFirstMove;
    }

    @Override
    public boolean equals(final Object other) {
        return false;
    }
}
