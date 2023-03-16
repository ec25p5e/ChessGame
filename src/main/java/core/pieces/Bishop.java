package core.pieces;

import core.board.VirtualBoard;
import core.board.VirtualBoardUtils;
import core.movements.*;
import core.pieces.piece.Piece;
import core.pieces.piece.PieceType;
import core.pieces.piece.PieceUtils;
import core.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Questa classe serve per rappresentare l'alfiere ed estende la classe {@link Piece}
 * perché l'alfiere è un tipo di pedina
 */
public class Bishop extends Piece {

    // Questo array serve a contenere i valori da utilizzare durante il calcolo delle possibili mosse quando si aggiungono
    // i valori alla possibile coordinata.
    private static final int[] OPERATION_MOVE = { -9, -7, 7, 9 };

    /**
     * Questo costruttore viene utilizzato quando l'alfiere sarà in gioco. Quindi non obbligatoriamente sarà la sua prima mossa
     * @param piecePosition coordinata sulla quale è posizionata la torre
     * @param pieceUtils Utility della pedina. Gli utility sono dei metodi o caratteristiche di un gruppo di pedine.
     *                   Ad esempio se la pedina è bianca o nera. Immagazzinare chi fosse il colore avversario,...
     * @param isFirstMove valore booleano che indica se è la prima mossa del pedone
     */
    public Bishop(final int piecePosition, final Utils pieceUtils, final boolean isFirstMove) {
        super(PieceType.BISHOP, piecePosition, pieceUtils, isFirstMove);
    }

    /**
     * Questo costruttore viene utilizzato quando l'alfiere sarà la sua prima mossa
     * @param piecePosition coordinata sulla quale è posizionata la torre
     * @param pieceUtils Utility della pedina. Gli utility sono dei metodi o caratteristiche di un gruppo di pedine.
     *                   Ad esempio se la pedina è bianca o nera. Immagazzinare chi fosse il colore avversario,...
     */
    public Bishop(final int piecePosition, final Utils pieceUtils) {
        super(PieceType.BISHOP, piecePosition, pieceUtils, true);
    }

    /**
     * Questo metodo viene utilizzato per rilevare quali sono le mosse possibili per la pedina
     * @param board scacchiera virtuale di riferimento
     * @return Una lista di mosse praticabili dalla pedina allo stato corrente
     */
    @Override
    public Collection<Move> calculateMoves(final VirtualBoard board) {
        final List<Move> usableMoves = new ArrayList<>();

        // Viene percorso l'array contenente i valori di calcolo
        for(final int candidateOffset : OPERATION_MOVE) {
            // Viene impostata la variabile con la posizione corrente della torre
            int candidateCoordinate = this.piecePosition;

            // Viene avviato un ciclo finché il calcolo della posizione candidata genera un valore al di fuori del range di valori della scacchiera.
            // Oppure se alla coordinata candidata è già presente una pedina non dei nostri oppure se si capita sulla prima/ultima riga della scacchiera
            while(VirtualBoardUtils.isValidTileCoordinate(candidateCoordinate)) {
                if(firstColumnExclusion(candidateOffset, candidateCoordinate) ||
                    eighthColumnExclusion(candidateOffset, candidateCoordinate))
                    break;

                // Aggiungi il valore di calcolo alla coordinata corrente
                candidateCoordinate += candidateOffset;

                // Controlla che il nuovo valore sia valido
                if(VirtualBoardUtils.isValidTileCoordinate(candidateCoordinate)) {
                    // Cattura il contenuto della cella di destinazione
                    final Piece pieceAtDestination = board.getPiece(candidateCoordinate);

                    // Se è nulla significa che è vuota, crea una mossa di movimento
                    if(pieceAtDestination == null)
                        usableMoves.add(new SimpleMove(board, this, candidateCoordinate));
                    else {
                        // Altrimenti se non è vuota controlla che sia dell'avversario e crea una mossa di attacco e termina il ciclo
                        // e procedi al prossimo valore di calcolo
                        final Utils pieceAtDestinationUtils = pieceAtDestination.getPieceUtils();

                        if(this.pieceUtils != pieceAtDestinationUtils)
                            usableMoves.add(new SimpleAttackMove(board, this, candidateCoordinate, pieceAtDestination));

                        break;
                    }
                }
            }
        }

        // Ritorna la lista completa di tutti i movimenti possibili
        return Collections.unmodifiableList(usableMoves);
    }

    /**
     * Questo metodo serve per ritornare la pedina alla coordinata in base al movimento scelto
     * @param move mossa scelta dopo aver rilevato la sorgente e la destinazione.
     * @return Una pedina della classe {@link Bishop}
     */
    @Override
    public Piece movePiece(final Move move) {
        return PieceUtils.INSTANCE.getPieceAtCoordinate(Bishop.class, move.getPieceToMove().getPieceUtils(), move.getDestinationCoordinate());
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
        return this.pieceUtils.bishopBonus(this.piecePosition);
    }

    /**
     * @return il carattere identificativo di ogni pedina. Ogni tipo di pedina ha il suo
     */
    @Override
    public String toString() {
        return this.pieceType.toString();
    }

    /**
     * Questo metodo serve a capire se la coordinata candidata andrà sull'ultima riga, per il bianco
     * @param candidateOffset valore di calcolo attuale
     * @param candidateCoordinate coordinata della pedina
     * @return "TRUE" se la pedina è posizionata sull'ultima riga
     */
    private static boolean firstColumnExclusion(final int candidateOffset, final int candidateCoordinate) {
        return VirtualBoardUtils.INSTANCE.FIRST_COLUMN.get(candidateCoordinate) && (candidateOffset == -9 || candidateOffset == 7);
    }

    /**
     * Questo metodo serve a capire se la coordinata candidata andrà sull'ultima riga, per il nero
     * @param candidateOffset valore di calcolo attuale
     * @param candidateCoordinate coordinata della pedina
     * @return "TRUE" se la pedina è posizionata sull'ultima riga
     */
    private static boolean eighthColumnExclusion(final int candidateOffset, final int candidateCoordinate) {
        return VirtualBoardUtils.INSTANCE.EIGHTH_COLUMN.get(candidateCoordinate) && (candidateOffset == -7 || candidateOffset == 9);
    }
}
