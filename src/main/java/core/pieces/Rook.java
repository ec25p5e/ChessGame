package core.pieces;

import core.Utils;
import core.pieces.piece.Piece;

import java.util.Collection;

public final class Rook extends Piece {
    public Rook(final Utils rookUtils, final int piecePosition) {
        super(PieceType.ROOK, rookUtils, piecePosition, true);
    }

    public Rook(final Utils rookUtils, final int piecePosition, final boolean isFirstMove) {
        super(PieceType.ROOK, rookUtils, piecePosition, isFirstMove);
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
