package core.pieces;

import core.Utils;
import core.pieces.piece.Piece;

import java.util.Collection;

public final class Knight extends Piece {
    public Knight(final Utils knightUtils, final int piecePosition) {
        super(PieceType.KNIGHT, knightUtils, piecePosition, false);
    }

    public Knight(final Utils knightUtils, final int piecePosition, final boolean isFirstMove) {
        super(PieceType.KNIGHT, knightUtils, piecePosition, isFirstMove);
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
