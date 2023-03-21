package core.pieces.piece;

import core.pieces.*;
import core.utils.Utils;

import static util.Constants.*;

/**
 * Questa classe serve a creare nuove pedine in base alla classe.
 * Viene utilizzato dalla classe {@link PieceUtils}
 */
@SuppressWarnings("ALL")
public class PieceAssistant {

    /**
     * Questo metodo serve per creare le istanze delle varie pedine. Il filtro è dato dal modo in cui l'id
     * della cella viene passato. In questo caso i numeri interi, ex: 60.
     * @param pieceClass classe di riferimento della pedina
     * @param coordinate coordinata dove deve essere posizionata la pedina (60)
     * @param utils indicazione degli utilities da utilizzare (black or white)
     * @param firstMove valore booleano che indica se la pedina è la prima volta che si muove
     * @param isQueenCastle indica se è stato messo sotto scacco dalla regina
     * @param isKingCastle indica se è stato messo sotto scacco dal RE
     * @return istanza della pedina corretta. ex: Rook
     * @param <T> qualsiasi oggetto che estenda la classe {@link Piece}
     * @throws ClassNotFoundException eccezione in caso la classe richiesta non sia esistente o non venga trovata
     */
    public <T extends Piece> T init(Class<?> pieceClass, final int coordinate, final Utils utils, final boolean firstMove,
                                    final boolean isQueenCastle, final boolean isKingCastle) throws ClassNotFoundException {
        return switch (pieceClass.getName()) {
            case ROOK_PACKAGE   -> (T) new Rook(coordinate, utils, firstMove);
            case BISHOP_PACKAGE ->(T) new Bishop(coordinate, utils, firstMove);
            case KNIGHT_PACKAGE -> (T) new Knight(coordinate, utils, firstMove);
            case PAWN_PACKAGE   -> (T) new Pawn(coordinate, utils, firstMove);
            case QUEEN_PACKAGE  -> (T) new Queen(coordinate, utils, firstMove);
            case KING_PACKAGE   -> (T) new King(coordinate, utils, isKingCastle, isQueenCastle);
            default             -> throw new ClassNotFoundException(CLASS_NOT_FOUND);
        };
    }

    /**
     * Questo metodo serve per creare le istanze delle varie pedine. Il filtro è dato dal modo in cui l'id
     * della cella viene passato. In questo caso le coordinate algebriche, ex: a5
     * @param pieceClass classe di riferimento della pedina
     * @param coordinate coordinata dove deve essere posizionata la pedina (a5)
     * @param utils indicazione degli utilities da utilizzare (black or white)
     * @param firstMove valore booleano che indica se la pedina è la prima volta che si muove
     * @param isQueenCastle indica se è stato messo sotto scacco dalla regina
     * @param isKingCastle indica se è stato messo sotto scacco dal RE
     * @return istanza della pedina corretta. ex: Rook
     * @param <T> qualsiasi oggetto che estenda la classe {@link Piece}
     * @throws ClassNotFoundException eccezione in caso la classe richiesta non sia esistente o non venga trovata
     */
    public <T extends Piece> T init(Class<?> pieceClass, final String coordinate, final Utils utils, final boolean firstMove,
                                    final boolean isQueenCastle, final boolean isKingCastle) throws ClassNotFoundException {
        return switch (pieceClass.getName()) {
            case ROOK_PACKAGE   -> (T) new Rook(coordinate, utils, firstMove);
            case BISHOP_PACKAGE -> (T) new Bishop(coordinate, utils, firstMove);
            case KNIGHT_PACKAGE -> (T) new Knight(coordinate, utils, firstMove);
            case PAWN_PACKAGE   -> (T) new Pawn(coordinate, utils, firstMove);
            case QUEEN_PACKAGE  -> (T) new Queen(coordinate, utils, firstMove);
            case KING_PACKAGE   -> (T) new King(coordinate, utils, isKingCastle, isQueenCastle);
            default             -> throw new ClassNotFoundException(CLASS_NOT_FOUND);
        };
    }
}