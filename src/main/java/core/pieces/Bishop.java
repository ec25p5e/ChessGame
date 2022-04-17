package core.pieces;

import core.pieces.piece.Piece;

import java.util.Collection;

public final class Bishop extends Piece {
    public Bishop(final int piecePosition) {
        super(PieceType.BISHOP, piecePosition, false);
    }

    public Bishop(final int piecePosition, final boolean isFirstMove) {
        super(PieceType.BISHOP, piecePosition, isFirstMove);
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
