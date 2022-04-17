package core.pieces;

import core.pieces.piece.Piece;

import java.util.Collection;

public final class Pawn extends Piece {
    public Pawn(final int piecePosition) {
        super(PieceType.PAWN, piecePosition, true);
    }

    public Pawn(final int piecePosition, final boolean isFirstMove) {
        super(PieceType.PAWN, piecePosition, isFirstMove);
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
