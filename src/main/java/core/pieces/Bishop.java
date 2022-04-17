package core.pieces;

import core.Utils;
import core.pieces.piece.Piece;

import java.util.Collection;

public final class Bishop extends Piece {
    public Bishop(final Utils bishopUtils, final int piecePosition) {
        super(PieceType.BISHOP, bishopUtils, piecePosition, false);
    }

    public Bishop(final Utils bishopUtils, final int piecePosition, final boolean isFirstMove) {
        super(PieceType.BISHOP, bishopUtils, piecePosition, isFirstMove);
    }

    @Override
    public Piece movePiece() {
        return null;
    }

    @Override
    public Collection<Object> calculateLegalMoves() {
        return null;
    }

    @Override
    public String toString() {
        return this.pieceType.toString();
    }
}
