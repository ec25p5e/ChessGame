package core.pieces;

import core.Utils;
import core.pieces.piece.Piece;

import java.util.Collection;

public final class Pawn extends Piece {
    public Pawn(final Utils pawnUtils, final int piecePosition) {
        super(PieceType.PAWN, pawnUtils, piecePosition, true);
    }

    public Pawn(final Utils pawnUtils, final int piecePosition, final boolean isFirstMove) {
        super(PieceType.PAWN, pawnUtils, piecePosition, isFirstMove);
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
