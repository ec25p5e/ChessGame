package core.player;

import core.board.VirtualBoard;
import core.board.VirtualBoardUtils;
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
 * Questa classe serve per rappresentare il giocatore nero
 */
public class BlackPlayer extends Player {

    /**
     * @param board scacchiera "virtuale" di riferimento
     * @param blackUsableMoves mosse usabili dal giocatore nero
     * @param whiteUsableMoves mosse usabili dal giocatore bianco, avversario
     */
    public BlackPlayer(final VirtualBoard board, final Collection<Move> whiteUsableMoves,
                       final Collection<Move> blackUsableMoves, boolean isDrawingMode) {
        super(board, blackUsableMoves, whiteUsableMoves, isDrawingMode);
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
        if (!hasCastleOpportunities())
            return Collections.emptyList();

        final List<Move> kingCastles = new ArrayList<>();

        if (this.playerKing.isFirstMove() && this.playerKing.getPiecePosition() == 4 && !this.isInCheck) {

            //blacks king side castle
            if (this.board.getPiece(5) == null && this.board.getPiece(6) == null) {
                final Piece kingSideRook = this.board.getPiece(7);

                if (kingSideRook != null && kingSideRook.isFirstMove() &&
                        Player.calculateAttacksOnTile(5, opponentPlayerMoves).isEmpty() &&
                        Player.calculateAttacksOnTile(6, opponentPlayerMoves).isEmpty() &&
                        kingSideRook.getPieceType() == ROOK) {

                    if (!VirtualBoardUtils.isKingPawnTrap(this.board, this.playerKing, 12))
                        kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 6, (Rook) kingSideRook, kingSideRook.getPiecePosition(), 5));
                }
            }

            //blacks queen side castle
            if (this.board.getPiece(1) == null && this.board.getPiece(2) == null &&
                    this.board.getPiece(3) == null) {
                final Piece queenSideRook = this.board.getPiece(0);

                if (queenSideRook != null && queenSideRook.isFirstMove() &&
                        Player.calculateAttacksOnTile(2, opponentPlayerMoves).isEmpty() &&
                        Player.calculateAttacksOnTile(3, opponentPlayerMoves).isEmpty() &&
                        queenSideRook.getPieceType() == ROOK) {

                    if (!VirtualBoardUtils.isKingPawnTrap(this.board, this.playerKing, 12))
                        kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 2, (Rook) queenSideRook, queenSideRook.getPiecePosition(), 3));
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
