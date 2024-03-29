package core.pieces;

import core.board.VirtualBoard;
import core.board.VirtualBoardUtils;
import core.move.MajorMove;
import core.move.MajorAttackMove;
import core.utils.Utils;
import core.move.Move;
import core.pieces.piece.Piece;
import core.pieces.piece.PieceType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Questa classe serve per rappresentare la pedina del Re ed estende la classe {@link Piece}
 * perché il pedone è un tipo di pedina
 */
public class King extends Piece {

    // Questo array serve a contenere i valori da utilizzare durante il calcolo delle possibili mosse quando si aggiungono
    // i valori alla possibile coordinata.
    private static final int[] OPERATION_MOVE = { -9, -8, -7, -1, 1, 7, 8, 9 };

    private final boolean isCastled;
    private final boolean isCastledByKing;
    private final boolean isCastledByQueen;

    /**
     * Questo costruttore viene utilizzato quando il re sarà in gioco. Quindi non obbligatoriamente sarà la sua prima mossa
     * @param piecePosition coordinata sulla quale è posizionata la torre
     * @param pieceUtils Utility della pedina. Gli utility sono dei metodi o caratteristiche di un gruppo di pedine.
     *                   Ad esempio se la pedina è bianca o nera. Immagazzinare chi fosse il colore avversario,...
     * @param isFirstMove valore booleano che indica se è la prima mossa del pedone
     * @param isCastled indica se è sotto scacco
     * @param isCastledByKing indica se è sotto scacco dal re
     * @param isCastledByQueen indica se è sotto scacco dalla regina
     */
    public King(final int piecePosition, final Utils pieceUtils, final boolean isFirstMove, final boolean isCastled,
                final boolean isCastledByKing, final boolean isCastledByQueen) {
        super(PieceType.KING, piecePosition, pieceUtils, isFirstMove, PieceType.KING.getDrawFileName());

        this.isCastled = isCastled;
        this.isCastledByKing = isCastledByKing;
        this.isCastledByQueen = isCastledByQueen;
    }

    /**
     * Questo costruttore viene utilizzato quando viene deserializzato il file e di conseguenza instantiazo l'oggetto
     * @param pieceCoordinate coordinata sulla quale posizionata la torre. ex: a5
     * @param pieceUtils Utility della pedina. Gli utility sono dei metodi o caratteristiche di un gruppo di pedine.
     *                   Ad esempio se la pedina è bianca o nera. Immagazzinare chi fosse il colore avversario,...
     * @param isFirstMove valore booleano che indica se è la prima mossa del pedone
     */
    public King(final String pieceCoordinate, final Utils pieceUtils, final boolean isFirstMove, final boolean isCastled,
                final boolean isCastledByKing, final boolean isCastledByQueen) {
        super(PieceType.KING, pieceCoordinate, pieceUtils, isFirstMove, PieceType.KING.getDrawFileName());

        this.isCastled = isCastled;
        this.isCastledByKing = isCastledByKing;
        this.isCastledByQueen = isCastledByQueen;
    }

    /**
     * Questo costruttore viene utilizzato quando il re sarà la sua prima mossa
     * @param piecePosition coordinata sulla quale è posizionata la torre
     * @param pieceUtils Utility della pedina. Gli utility sono dei metodi o caratteristiche di un gruppo di pedine.
     *                   Ad esempio se la pedina è bianca o nera. Immagazzinare chi fosse il colore avversario,...
     * @param isCastledByKing indica se è sotto scacco dal re
     * @param isCastledByQueen indica se è sotto scacco dalla regina
     */
    public King(final int piecePosition, final Utils pieceUtils, final boolean isCastledByKing, final boolean isCastledByQueen) {
        super(PieceType.KING, piecePosition, pieceUtils, true, PieceType.KING.getDrawFileName());
        this.isCastled = false;
        this.isCastledByKing = isCastledByKing;
        this.isCastledByQueen = isCastledByQueen;
    }

    /**
     *
     * @param piecePosition
     * @param pieceUtils
     * @param isCastledByKing
     * @param isCastledByQueen
     */
    public King(final String piecePosition, final Utils pieceUtils, final boolean isCastledByKing, final boolean isCastledByQueen) {
        super(PieceType.KING, piecePosition, pieceUtils, true, PieceType.KING.getDrawFileName());
        this.isCastled = false;
        this.isCastledByKing = isCastledByKing;
        this.isCastledByQueen = isCastledByQueen;
    }

    /**
     * Questo metodo viene utilizzato per rilevare quali sono le mosse possibili per la pedina
     * @param board scacchiera virtuale di riferimento
     * @return Una lista di mosse praticabili dalla pedina allo stato corrente
     */
    @Override
    public Collection<Move> calculateMoves(final VirtualBoard board) {
        final List<Move> usableMoves = new ArrayList<>();

        for(final int currentCandidate : OPERATION_MOVE) {
            if(firstColumnExclusion(this.piecePosition, currentCandidate) ||
                eighthColumnExclusion(this.piecePosition, currentCandidate)) {
                continue;
            }

            final int candidateDestination = this.piecePosition + currentCandidate;

            if(VirtualBoardUtils.isValidTileCoordinate(candidateDestination)) {
                final Piece pieceAtDestination = board.getPiece(candidateDestination);

                if(pieceAtDestination == null) {
                    usableMoves.add(new MajorMove(board, this, candidateDestination));
                } else {
                    final Utils pieceAtDestinationUtils = pieceAtDestination.getPieceUtils();

                    if(this.pieceUtils != pieceAtDestinationUtils) {
                        usableMoves.add(new MajorAttackMove(board, this, candidateDestination, pieceAtDestination));
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
     * @return Una pedina della classe {@link King}
     */
    @Override
    public Piece movePiece(final Move move) {
        return new King(move.getDestinationCoordinate(), this.pieceUtils, false, move.isCastlingMove(), false, false);
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
        return this.pieceUtils.kingBonus(this.piecePosition);
    }

    /**
     * Questo metodo indica se il pedone è in castling
     * Viene utilizzato solo dai RE
     *
     * @return valore booleano
     */
    @Override
    public boolean isCastled() {
        return this.isCastled;
    }

    /**
     * Questo metodo serve a indicare se è in castling dal
     * RE. È specifico per il metodo "isCastled"
     *
     * @return valore booleano
     */
    @Override
    public boolean isCastledByKing() {
        return this.isCastledByKing;
    }

    /**
     * Questo metodo serve a indicare se è in castling dalla
     * Regina. È specifico per il metodo "isCastled"
     *
     * @return valore booleano
     */
    @Override
    public boolean isCastledByQueen() {
        return this.isCastledByQueen;
    }

    /**
     *
     * @param other oggetto da confrontare
     * @return
     */
    @Override
    public boolean equals(final Object other) {
        if (this == other)
            return true;

        if (!(other instanceof final King king))
            return false;

        if (!super.equals(other))
            return false;

        return isCastled == king.isCastled;
    }

    /**
     * @return il carattere identificativo di ogni pedina. Ogni tipo di pedina ha il suo
     */
    @Override
    public String toString() {
        return this.pieceType.toString();
    }

    /**
     * Questo metodo ha l'obbiettivo di controllare se la coordinata candidata è situata sulla prima riga, per i bianchi
     * @param currentCandidate candidata corrente dove è situata la pedina
     * @param candidateOffset valore di calcolo corrente
     * @return un valore booleano "TRUE" se le condizioni sono vere, altrimenti "FALSE"
     */
    private static boolean firstColumnExclusion(final int currentCandidate, final int candidateOffset) {
        return VirtualBoardUtils.INSTANCE.FIRST_COLUMN.get(currentCandidate) && (candidateOffset == -9 || candidateOffset == -1 || candidateOffset == 7);
    }

    /**
     * Questo metodo ha l'obbiettivo di controllare se la coordinata candidata è situata sulla ottava riga, per i neri
     * @param currentCandidate candidata corrente dove è situata la pedina
     * @param candidateOffset valore di calcolo corrente
     * @return un valore booleano "TRUE" se le condizioni sono vere, altrimenti "FALSE"
     */
    private static boolean eighthColumnExclusion(final int currentCandidate, final int candidateOffset) {
        return VirtualBoardUtils.INSTANCE.EIGHTH_COLUMN.get(currentCandidate) && (candidateOffset == -7 || candidateOffset == 1 || candidateOffset == 9);
    }
}
