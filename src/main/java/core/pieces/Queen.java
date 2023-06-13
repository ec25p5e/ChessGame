package core.pieces;

import core.board.VirtualBoard;
import core.board.VirtualBoardUtils;
import core.move.MajorAttackMove;
import core.move.MajorMove;
import core.move.Move;
import core.pieces.piece.Piece;
import core.pieces.piece.PieceType;
import core.pieces.piece.PieceUtils;
import core.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Questa classe serve per rappresentare la regina ed estende la classe {@link Piece}
 * perché la regina è un tipo di pedina
 */
public class Queen extends Piece {

    // Questo array serve a contenere i valori da utilizzare durante il calcolo delle possibili mosse quando si aggiungono
    // i valori alla possibile coordinata.
    private static final int[] OPERATION_MOVE = { -9, -8, -7, -1, 1, 7, 8, 9 };

    /**
     * Questo costruttore viene utilizzato quando la regina sarà in gioco. Quindi non obbligatoriamente sarà la sua prima mossa
     * @param piecePosition coordinata sulla quale è posizionata la torre
     * @param pieceUtils Utility della pedina. Gli utility sono dei metodi o caratteristiche di un gruppo di pedine.
     *                   Ad esempio se la pedina è bianca o nera. Immagazzinare chi fosse il colore avversario,...
     * @param isFirstMove valore booleano che indica se è la prima mossa del pedone
     */
    public Queen(final int piecePosition, final Utils pieceUtils, final boolean isFirstMove) {
        super(PieceType.QUEEN, piecePosition, pieceUtils, isFirstMove, PieceType.QUEEN.getDrawFileName());
    }

    /**
     * Questo costruttore viene utilizzato quando viene deserializzato il file e di conseguenza instantiazo l'oggetto
     * @param pieceCoordinate coordinata sulla quale posizionata la torre. ex: a5
     * @param pieceUtils Utility della pedina. Gli utility sono dei metodi o caratteristiche di un gruppo di pedine.
     *                   Ad esempio se la pedina è bianca o nera. Immagazzinare chi fosse il colore avversario,...
     * @param isFirstMove valore booleano che indica se è la prima mossa del pedone
     */
    public Queen(final String pieceCoordinate, final Utils pieceUtils, final boolean isFirstMove) {
        super(PieceType.QUEEN, pieceCoordinate, pieceUtils, isFirstMove, PieceType.QUEEN.getDrawFileName());
    }

    /**
     * Questo costruttore viene utilizzato quando la regina sarà la sua prima mossa
     * @param piecePosition coordinata sulla quale è posizionata la torre
     * @param pieceUtils Utility della pedina. Gli utility sono dei metodi o caratteristiche di un gruppo di pedine.
     *                   Ad esempio se la pedina è bianca o nera. Immagazzinare chi fosse il colore avversario,...
     */
    public Queen(final int piecePosition, final Utils pieceUtils) {
        super(PieceType.QUEEN, piecePosition, pieceUtils, true, PieceType.QUEEN.getDrawFileName());
    }

    /**
     * Questo metodo viene utilizzato per rilevare quali sono le mosse possibili per la pedina
     * @param board scacchiera virtuale di riferimento
     * @return Una lista di mosse praticabili dalla pedina allo stato corrente
     */
    @Override
    public Collection<Move> calculateMoves(final VirtualBoard board) {
        final List<Move> usableMoves = new ArrayList<>();

        for (final int currentCandidateOffset : OPERATION_MOVE) {
            int candidateDestinationCoordinate = this.piecePosition;

            while (true) {
                if (firstColumnExclusion(currentCandidateOffset, candidateDestinationCoordinate) ||
                        eighthColumnExclusion(currentCandidateOffset, candidateDestinationCoordinate)) {
                    break;
                }

                candidateDestinationCoordinate += currentCandidateOffset;

                if (!VirtualBoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    break;
                } else {
                    final Piece pieceAtDestination = board.getPiece(candidateDestinationCoordinate);

                    if (pieceAtDestination == null) {
                        usableMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                    } else {
                        final Utils pieceAtDestinationAllegiance = pieceAtDestination.getPieceUtils();

                        if (this.pieceUtils != pieceAtDestinationAllegiance)
                            usableMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));

                        break;
                    }
                }
            }
        }

        return Collections.unmodifiableList(usableMoves);
    }

    /**
     * Questo metodo serve per ritornare la pedina alla coordinata in base al movimento scelto
     * @param move mossa scelta dopo aver rilevato la sorgente e la destinazione.
     * @return Una pedina della classe {@link Queen}
     */
    @Override
    public Piece movePiece(final Move move) {
        return PieceUtils.INSTANCE.getPieceAtCoordinate(Queen.class, move.getPieceToMove().getPieceUtils(), move.getDestinationCoordinate());
    }

    /**
     * Questo metodo serve per ritornare un numero intero che indica il bonus
     * del pedone per la coordinata e il tipo.
     * Questo valore viene usato principalmente dall'AI per valutare la scacchiera
     * Tutto questo ha poi una teoria che spiegherò nella wiki github
     *
     * @return numero intero positivo o negativo
     */
    @Override
    public int locationBonus() {
        return this.pieceUtils.queenBonus(this.piecePosition);
    }

    /**
     * @return il carattere identificativo di ogni pedina. Ogni tipo di pedina ha il suo
     */
    @Override
    public String toString() {
        return this.pieceType.toString();
    }

    /**
     * Questo metodo serve a capire se la coordinata candidata andrà sulla prima riga, per il bianco
     * @param candidateOffset valore di calcolo attuale
     * @param candidateCoordinate coordinata della pedina
     * @return "TRUE" se la pedina è posizionata sull'ultima riga
     */
    private static boolean firstColumnExclusion(final int candidateOffset, final int candidateCoordinate) {
        return VirtualBoardUtils.INSTANCE.FIRST_COLUMN.get(candidateCoordinate) && (candidateOffset == -9 || candidateOffset == -1 || candidateOffset == 7);
    }

    /**
     * Questo metodo serve a capire se la coordinata candidata andrà sulla prima riga, per il nero
     * @param candidateOffset valore di calcolo attuale
     * @param candidateCoordinate coordinata della pedina
     * @return "TRUE" se la pedina è posizionata sull'ultima riga
     */
    private static boolean eighthColumnExclusion(final int candidateOffset, final int candidateCoordinate) {
        return VirtualBoardUtils.INSTANCE.EIGHTH_COLUMN.get(candidateCoordinate) && (candidateOffset == -7 || candidateOffset == 1 || candidateOffset == 9);
    }
}
