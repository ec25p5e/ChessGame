package core.pieces;

import core.pieces.piece.Piece;

import java.util.Collection;

public final class Knight extends Piece {
    public Knight(final int piecePosition) {
        super(PieceType.KNIGHT, piecePosition, false);
    }

    public Knight(final int piecePosition, final boolean isFirstMove) {
        super(PieceType.KNIGHT, piecePosition, isFirstMove);
    }

    @Override
    public Piece movePiece() {
        return null;
    }

    @Override
    public Collection<Object> calculateLegalMoves() {
        return null;
    }
}
