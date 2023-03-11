package core.pieces.piece;

import core.pieces.*;
import core.utils.Utils;

import static util.Constants.*;

/**
 * Questa classe serve a creare nuove pedine in base alla classe.
 * Viene utilizzato dalla classe {@link PieceUtils}
 * @param <T> tipo di pedina
 */
@SuppressWarnings("ALL")
public class PieceAssistant<T> {
    public <T extends Piece> T init(Class<? extends Piece> pieceClass, final int position, final Utils utils, final boolean isFirstMove, final boolean isQueenCastle, final boolean isKingCastle) throws ClassNotFoundException {
        return switch (pieceClass.getName()) {
            case KING_PACKAGE -> (T) new King(position, utils, isKingCastle, isQueenCastle);
            default -> this.init(pieceClass, position, utils, isFirstMove);
        };
    }

    public <T extends Piece> T init(Class<? extends Piece> pieceClass, final int position, final Utils utils, final boolean firstMove) throws ClassNotFoundException {
        return switch (pieceClass.getName()) {
            case ROOK_PACKAGE -> (T) new Rook(position, utils, firstMove);
            case BISHOP_PACKAGE ->(T) new Bishop(position, utils, firstMove);
            case KNIGHT_PACKAGE -> (T) new Knight(position, utils, firstMove);
            case PAWN_PACKAGE -> (T) new Pawn(position, utils, firstMove);
            case QUEEN_PACKAGE -> (T) new Queen(position, utils, firstMove);
            default -> throw new ClassNotFoundException(CLASS_NOT_FOUND);
        };
    }
}