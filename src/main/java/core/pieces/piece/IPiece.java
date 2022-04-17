package core.pieces.piece;

import java.util.Collection;

public interface IPiece {
    Piece movePiece();
    Collection<Object> calculateLegalMoves();
}
