package core.player;

import core.board.VirtualBoard;
import core.move.KingSideCastleMove;
import core.move.QueenSideCastleMove;
import core.pieces.Rook;
import core.utils.Utils;
import core.move.Move;
import core.pieces.piece.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static core.pieces.piece.PieceType.ROOK;

/**
 * Questa classe serve per rappresentare il giocatore bianco
 */
public class WhitePlayer extends Player {

    /**
     * @param board scacchiera "virtuale" di riferimento
     * @param whiteUsableMoves mosse usabili dal giocatore bianco
     * @param blackUsableMoves mosse usabili dal giocatore nero, avversario
     */
    public WhitePlayer(final VirtualBoard board, final Collection<Move> whiteUsableMoves, final Collection<Move> blackUsableMoves) {
        super(board, whiteUsableMoves, blackUsableMoves);
    }

    /**
     * Questo metodo serve per calcolare tutte le mosse che mettono sotto scacco i RE
     * Ogni giocatore ha una specializzazione del metodo stesso
     *
     * @param playerUsableMoves   tutte le mosse praticabili dal giocatore corrente
     * @param opponentPlayerMoves tutte le mosse praticabili dal giocatore opposto
     * @return tutte le mosse che soddisfino la condizione, cosi da poterle aggiungere
     */
    @Override
    public Collection<Move> calculateKingCastles(Collection<Move> playerUsableMoves, Collection<Move> opponentPlayerMoves) {
        final List<Move> kingCastles = new ArrayList<>();

        if(!hasCastleOpportunities())
            return Collections.emptyList();

        /* Controlla che le condizioni siano soddisfate:
            1. Non sia la prima mossa del RE            AND
            2. Il re non sia in posizione iniziale      AND
            3. Il giocatore non sia sotto scacco
         */
        if(this.playerKing.isFirstMove() && this.playerKing.getPiecePosition() == 60 && !this.isInCheck) {

            // Pedine nere che mettono in scacco il RE
            if(this.board.getPiece(61) == null && this.board.getPiece(62) == null) {
                final Piece kingSideRook = this.board.getPiece(63);

                if(kingSideRook != null && kingSideRook.isFirstMove() &&
                        Player.calculateAttacksOnTile(61, opponentPlayerMoves).isEmpty() &&
                        Player.calculateAttacksOnTile(62, opponentPlayerMoves).isEmpty() &&
                        kingSideRook.getPieceType() == ROOK) {

                    kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 62,
                            (Rook) kingSideRook, kingSideRook.getPiecePosition(), 61));
                }
            }

            // Pedine nere che mettono in scacco la regina
            if(this.board.getPiece(59) == null && this.board.getPiece(50) == null &&
                    this.board.getPiece(57) == null) {
                final Piece queenSideRook = this.board.getPiece(56);

                if(queenSideRook != null && queenSideRook.isFirstMove() &&
                        Player.calculateAttacksOnTile(2, opponentPlayerMoves).isEmpty() &&
                        Player.calculateAttacksOnTile(3, opponentPlayerMoves).isEmpty() &&
                        queenSideRook.getPieceType() == ROOK) {

                    kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 58,
                            (Rook) queenSideRook, queenSideRook.getPiecePosition(), 59));
                }
            }
        }

        return Collections.unmodifiableList(kingCastles);
    }

    /**
     * @return gli utility dei giocatori
     */
    @Override
    public Utils getUtils() {
        return Utils.WHITE;
    }

    /**
     * @return Il giocatore avversario. Quindi nella classe {@link WhitePlayer} ritornerà il giocatore nero e viceversa.
     */
    @Override
    public BlackPlayer getOpponent() {
        return this.board.getBlackPlayer();
    }

    /**
     * @return Tutte le pedine attive per il giocatore
     */
    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    /**
     * @return la stesura del colore del giocatore
     */
    @Override
    public String toString() {
        return Utils.WHITE.toString();
    }
}
