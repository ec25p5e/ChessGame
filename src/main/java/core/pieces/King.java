package core.pieces;

import core.Utils;
import core.pieces.piece.Piece;

import java.util.Collection;

public final class King extends Piece {
    public King(final Utils kingUtils, final int piecePosition) {
        super(PieceType.KING, kingUtils, piecePosition, false);
    }

    public King(final Utils kingUtils, final int piecePosition, final boolean isFirstMove) {
        super(PieceType.KING, kingUtils, piecePosition, isFirstMove);
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
