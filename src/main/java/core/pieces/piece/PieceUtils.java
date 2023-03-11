package core.pieces.piece;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import core.pieces.*;
import core.utils.Utils;
import core.board.VirtualBoardUtils;

import static util.Constants.*;

/**
 * Questa classe serve a contenere gli utils per le pedine.
 * Una funzione di utilità e quella di creare tutte le pedine di tutti e 6 tipi su layer diversi e poterle ottenere.
 * Cosi che il gioco sia più veloce perché gli oggetti sono già stati creati prima di effettuare una mossa.
 */
@SuppressWarnings("ALL")
public enum PieceUtils {

    INSTANCE;

    private final Table<Utils, Integer, Rook> ALL_ROOKS = PieceUtils.createAllPieces(Rook.class);
    private final Table<Utils, Integer, Bishop> ALL_BISHOPS = PieceUtils.createAllPieces(Bishop.class);
    private final Table<Utils, Integer, Knight> ALL_KNIGHTS = PieceUtils.createAllPieces(Knight.class);
    private final Table<Utils, Integer, Pawn> ALL_PAWNS = PieceUtils.createAllPieces(Pawn.class);
    private final Table<Utils, Integer, Queen> ALL_QUEENS = PieceUtils.createAllPieces(Queen.class);

    /**
     * Questo metodo serve a ritornare una pedina
     * @param utils colore della pedina da cercare
     * @param destinationCoordinate coordinata dove si vuole atterrare con il movimento
     * @return una pedina
     */
    public <T extends Piece> T getPieceAtCoordinate(final Class<T> pieceClass, final Utils utils, final int destinationCoordinate) {
        return switch (pieceClass.getName()) {
            case ROOK_PACKAGE -> (T) ALL_ROOKS.get(utils, destinationCoordinate);
            case BISHOP_PACKAGE -> (T) ALL_BISHOPS.get(utils, destinationCoordinate);
            case KNIGHT_PACKAGE -> (T) ALL_KNIGHTS.get(utils, destinationCoordinate);
            case PAWN_PACKAGE -> (T) ALL_PAWNS.get(utils, destinationCoordinate);
            case QUEEN_PACKAGE -> (T) ALL_QUEENS.get(utils, destinationCoordinate);
            default -> {
                try {
                    throw new ClassNotFoundException(CLASS_NOT_FOUND);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(RUNTIME_EXCEPTION);
                }
            }
        };
    }

    /**
     * Questo metodo serve a creare una tabella con 64 pedine dello stesso tipo, cosi da poter prendere la pedina alla coordinata
     * di destinazione già creata in precedenza
     * @param classPiece classe dell'oggetto da creare
     * @return tabella con le 64 pedine, etichettate da Il colore, l'intero (posizione) e l'oggetto
     * @param <T> tipo della pedina
     */
    private static <T extends Piece> Table<Utils, Integer, T> createAllPieces(final Class<T> classPiece) {
        final ImmutableTable.Builder<Utils, Integer, T> pieces = ImmutableTable.builder();
        final PieceAssistant<T> pieceAssistant = new PieceAssistant<>();

        for(final Utils utils : Utils.values()) {
            for(int i = 0; i < VirtualBoardUtils.NUM_TILES; i++) {
                try {
                    pieces.put(utils, i, pieceAssistant.init(classPiece, i, utils, false, true, true));
                } catch(final ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return pieces.build();
    }
}
