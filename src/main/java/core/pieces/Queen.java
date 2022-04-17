package core.pieces;

import core.Utils;
import core.pieces.piece.Piece;

import java.util.Collection;

public final class Queen extends Piece {
    public Queen(final Utils queenUtils, final int piecePosition) {
        super(PieceType.QUEEN, queenUtils, piecePosition, false);
    }

    public Queen(final Utils queenUtils, final int piecePosition, final boolean isFirstMove) {
        super(PieceType.QUEEN, queenUtils, piecePosition, isFirstMove);
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
