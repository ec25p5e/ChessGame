package core.pieces;

import core.pieces.piece.Piece;

import java.util.Collection;

public final class Queen extends Piece {
    public Queen(final int piecePosition) {
        super(PieceType.QUEEN, piecePosition, false);
    }

    @Override
    public Piece movePiece() {
        return null;
    }

    @Override
    public Collection<Object> calculateLegalMoves() {
        return null;
    }

    public Queen(final int piecePosition, final boolean isFirstMove) {
        super(PieceType.QUEEN, piecePosition, isFirstMove);
    }
}
