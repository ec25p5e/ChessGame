package core.pieces;

import core.board.VirtualBoard;
import core.board.VirtualBoardUtils;
import core.move.SimpleMove;
import core.move.Move;
import core.move.SimpleAttackMove;
import core.pieces.piece.Piece;
import core.pieces.piece.PieceType;
import core.pieces.piece.PieceUtils;
import core.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Knight extends Piece {

    // Questo array serve a contenere i valori da utilizzare durante il calcolo delle possibili mosse quando si aggiungono
    // i valori alla possibile coordinata.
    private static final int[] OPERATION_MOVE = { -17, -15, -10, -6, 6, 10, 15, 17 };

    /**
     * Questo costruttore viene utilizzato quando il cavallo sarà in gioco. Quindi non obbligatoriamente sarà la sua prima mossa
     * @param piecePosition coordinata sulla quale è posizionata la torre
     * @param pieceUtils Utility della pedina. Gli utility sono dei metodi o caratteristiche di un gruppo di pedine.
     *                   Ad esempio se la pedina è bianca o nera. Immagazzinare chi fosse il colore avversario,...
     * @param isFirstMove valore booleano che indica se è la prima mossa del pedone
     */
    public Knight(final int piecePosition, final Utils pieceUtils, final boolean isFirstMove) {
        super(PieceType.KNIGHT, piecePosition, pieceUtils, isFirstMove);
    }

    /**
     * Questo costruttore viene utilizzato quando viene deserializzato il file e di conseguenza instantiazo l'oggetto
     * @param pieceCoordinate coordinata sulla quale posizionata la torre. ex: a5
     * @param pieceUtils Utility della pedina. Gli utility sono dei metodi o caratteristiche di un gruppo di pedine.
     *                   Ad esempio se la pedina è bianca o nera. Immagazzinare chi fosse il colore avversario,...
     * @param isFirstMove valore booleano che indica se è la prima mossa del pedone
     */
    public Knight(final String pieceCoordinate, final Utils pieceUtils, final boolean isFirstMove) {
        super(PieceType.KNIGHT, pieceCoordinate, pieceUtils, isFirstMove);
    }

    /**
     * Questo costruttore viene utilizzato quando il cavallo sarà la sua prima mossa
     * @param piecePosition coordinata sulla quale è posizionata la torre
     * @param pieceUtils Utility della pedina. Gli utility sono dei metodi o caratteristiche di un gruppo di pedine.
     *                   Ad esempio se la pedina è bianca o nera. Immagazzinare chi fosse il colore avversario,...
     */
    public Knight(final int piecePosition, final Utils pieceUtils) {
        super(PieceType.KNIGHT, piecePosition, pieceUtils, true);
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
            // Se la torre capita sulla prima o seconda colonna continua perché cosi può saltare via le pedine allo stato iniziale
            if(firstColumnExclusion(this.piecePosition, candidateOffset) ||
                secondColumnExclusion(this.piecePosition, candidateOffset) ||
                seventhColumnExclusion(this.piecePosition, candidateOffset) ||
                eighthColumnExclusion(this.piecePosition, candidateOffset))
                continue;

            // Imposta la coordinata con sommata quella di calcolo
            final int candidateCoordinate = this.piecePosition + candidateOffset;

            // Controlla che non sia uscito dalla scacchiera
            if(VirtualBoardUtils.isValidTileCoordinate(candidateCoordinate)) {
                // Prendi il contenuto della cella di destinazione
                final Piece pieceAtDestination = board.getPiece(candidateCoordinate);

                // Se la destinazione è vuota crea una mossa normale/semplice
                if(pieceAtDestination == null)
                    usableMoves.add(new SimpleMove(board, this, candidateCoordinate));
                else {
                    // Altrimenti se il colore è diverso dal corrente
                    final Utils pieceAtDestinationUtils = pieceAtDestination.getPieceUtils();

                    // Crea una mossa di attacco
                    if(pieceAtDestinationUtils != this.pieceUtils)
                        usableMoves.add(new SimpleAttackMove(board, this, candidateCoordinate, pieceAtDestination));
                }
            }
        }

        // Ritorna la lista completa di tutti i movimenti possibili
        return Collections.unmodifiableList(usableMoves);
    }

    /**
     * Questo metodo serve per ritornare la pedina alla coordinata in base al movimento scelto
     * @param move mossa scelta dopo aver rilevato la sorgente e la destinazione.
     * @return Una pedina della classe {@link Knight}
     */
    @Override
    public Piece movePiece(final Move move) {
        return PieceUtils.INSTANCE.getPieceAtCoordinate(Knight.class, move.getPieceToMove().getPieceUtils(), move.getDestinationCoordinate());
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
        return this.pieceUtils.knightBonus(this.piecePosition);
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
     * @param currentCoordinate coordinata della pedina
     * @param candidateOffset valore di calcolo attuale
     * @return "TRUE" se la pedina è posizionata sulla prima riga
     */
    private static boolean firstColumnExclusion(final int currentCoordinate, final int candidateOffset) {
        return VirtualBoardUtils.INSTANCE.FIRST_COLUMN.get(currentCoordinate) && (candidateOffset == -17 || candidateOffset == -10 || candidateOffset == 6 || candidateOffset == 15);
    }

    /**
     * Questo metodo serve a capire se la coordinata candidata andrà sulla seconda riga, per il bianco
     * @param currentCoordinate coordinata della pedina
     * @param candidateOffset valore di calcolo attuale
     * @return "TRUE" se la pedina è posizionata sulla seconda riga
     */
    private static boolean secondColumnExclusion(final int currentCoordinate, final int candidateOffset) {
        return VirtualBoardUtils.INSTANCE.SECOND_COLUMN.get(currentCoordinate) && (candidateOffset == -10 || candidateOffset == 6);
    }

    /**
     * Questo metodo serve a capire se la coordinata candidata andrà sulla seconda riga, per il nero
     * @param currentCoordinate coordinata della pedina
     * @param candidateOffset valore di calcolo attuale
     * @return "TRUE" se la pedina è posizionata sulla seconda riga
     */
    private static boolean seventhColumnExclusion(final int currentCoordinate, final int candidateOffset) {
        return VirtualBoardUtils.INSTANCE.SEVENTH_COLUMN.get(currentCoordinate) && (candidateOffset == -6 || candidateOffset == 10);
    }

    /**
     * Questo metodo serve a capire se la coordinata candidata andrà sulla seconda riga, per il nero
     * @param currentCoordinate coordinata della pedina
     * @param candidateOffset valore di calcolo attuale
     * @return "TRUE" se la pedina è posizionata sulla seconda riga
     */
    private static boolean eighthColumnExclusion(final int currentCoordinate, final int candidateOffset) {
        return VirtualBoardUtils.INSTANCE.EIGHTH_COLUMN.get(currentCoordinate) && (candidateOffset == -15 || candidateOffset == -6 || candidateOffset == 10 || candidateOffset == 17);
    }
}
