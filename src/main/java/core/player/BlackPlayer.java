package core.player;

import core.board.VirtualBoard;
import core.board.VirtualBoardUtils;
import core.movements.KingSideCastleMove;
import core.movements.QueenSideCastleMove;
import core.pieces.Rook;
import core.utils.Utils;
import core.movements.Move;
import core.pieces.piece.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static core.pieces.piece.PieceType.ROOK;

/**
 * Questa classe serve per rappresentare il giocatore nero
 */
public class BlackPlayer extends Player {

    /**
     * @param board scacchiera "virtuale" di riferimento
     * @param blackUsableMoves mosse usabili dal giocatore nero
     * @param whiteUsableMoves mosse usabili dal giocatore bianco, avversario
     */
    public BlackPlayer(final VirtualBoard board, final Collection<Move> whiteUsableMoves, final Collection<Move> blackUsableMoves) {
        super(board, blackUsableMoves, whiteUsableMoves);
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

        /* Controlla che le condizioni siano soddisfate:
            1. Non sia la prima mossa del RE            AND
            2. Il re non sia in posizione iniziale      AND
            3. Il giocatore non sia sotto scacco
         */
        if(this.playerKing.isFirstMove() && this.playerKing.getPiecePosition() == 4 && !this.isInCheck) {

            // Pedine nere che mettono in scacco il RE
            if(this.board.getPiece(5) == null && this.board.getPiece(6) == null) {
                final Piece kingSideRook = this.board.getPiece(7);

                if(kingSideRook != null && kingSideRook.isFirstMove() &&
                    Player.calculateAttacksOnTile(5, opponentPlayerMoves).isEmpty() &&
                    Player.calculateAttacksOnTile(6, opponentPlayerMoves).isEmpty() &&
                    kingSideRook.getPieceType() == ROOK) {

                    kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 6,
                            (Rook) kingSideRook, kingSideRook.getPiecePosition(), 5));
                }
            }

            // Pedine nere che mettono in scacco la regina
            if(this.board.getPiece(1) == null && this.board.getPiece(2) == null &&
                this.board.getPiece(3) == null) {
                final Piece queenSideRook = this.board.getPiece(0);

                if(queenSideRook != null && queenSideRook.isFirstMove() &&
                    Player.calculateAttacksOnTile(2, opponentPlayerMoves).isEmpty() &&
                    Player.calculateAttacksOnTile(3, opponentPlayerMoves).isEmpty() &&
                    queenSideRook.getPieceType() == ROOK) {

                    kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 2,
                            (Rook) queenSideRook, queenSideRook.getPiecePosition(), 3));
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
        return Utils.BLACK;
    }

    /**
     * @return Il giocatore avversario. Quindi nella classe {@link WhitePlayer} ritornerà il giocatore nero e viceversa.
     */
    @Override
    public WhitePlayer getOpponent() {
        return this.board.getWhitePlayer();
    }

    /**
     * @return Tutte le pedine attive per il giocatore
     */
    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    /**
     * @return la stesura del colore del giocatore
     */
    @Override
    public String toString() {
        return Utils.BLACK.toString();
    }
}
