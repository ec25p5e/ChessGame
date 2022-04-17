package core.pieces;

import core.pieces.piece.Piece;

import java.util.Collection;

public final class King extends Piece {
    public King(final int piecePosition) {
        super(PieceType.KING, piecePosition, false);
    }

    public King(final int piecePosition, final boolean isFirstMove) {
        super(PieceType.KING, piecePosition, isFirstMove);
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
