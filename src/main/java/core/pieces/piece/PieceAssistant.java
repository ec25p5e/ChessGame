package core.pieces.piece;

import core.pieces.*;
import core.utils.Utils;

import static util.Constants.*;

/**
 * Questa classe serve a creare nuove pedine in base alla classe.
 * Viene utilizzato dalla classe {@link PieceUtils}
 */
public class PieceAssistant {

    @SuppressWarnings("unchecked")
    public <T extends Piece> T init(Class<?> pieceClass, final int coordinate, final Utils utils, final boolean firstMove,
                                    final boolean isQueenCastle, final boolean isKingCastle) throws ClassNotFoundException {
        return switch (pieceClass.getName()) {
            case ROOK_PACKAGE -> (T) new Rook(coordinate, utils, firstMove);
            case BISHOP_PACKAGE ->(T) new Bishop(coordinate, utils, firstMove);
            case KNIGHT_PACKAGE -> (T) new Knight(coordinate, utils, firstMove);
            case PAWN_PACKAGE -> (T) new Pawn(coordinate, utils, firstMove);
            case QUEEN_PACKAGE -> (T) new Queen(coordinate, utils, firstMove);
            case KING_PACKAGE -> (T) new King(coordinate, utils, isKingCastle, isQueenCastle);
            default -> throw new ClassNotFoundException(CLASS_NOT_FOUND);
        };
    }
}