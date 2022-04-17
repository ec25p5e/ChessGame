package core.pieces;

import core.pieces.piece.Piece;

import java.util.Collection;

public final class Rook extends Piece {
    public Rook(final int piecePosition) {
        super(PieceType.ROOK, piecePosition, true);
    }

    public Rook(final int piecePosition, final boolean isFirstMove) {
        super(PieceType.ROOK, piecePosition, isFirstMove);
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
