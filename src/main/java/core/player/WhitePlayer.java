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
        if(!hasCastleOpportunities())
            return Collections.emptyList();

        final List<Move> kingCastles = new ArrayList<>();

        if(this.playerKing.isFirstMove() && this.playerKing.getPiecePosition() == 60 && !this.isInCheck()) {

            //whites king side castle
            if(this.board.getPiece(61) == null && this.board.getPiece(62) == null) {
                final Piece kingSideRook = this.board.getPiece(63);

                if(kingSideRook != null && kingSideRook.isFirstMove()) {
                    if(Player.calculateAttacksOnTile(61, opponentPlayerMoves).isEmpty() &&
                            Player.calculateAttacksOnTile(62, opponentPlayerMoves).isEmpty() &&
                            kingSideRook.getPieceType() == ROOK) {

                        if(!VirtualBoardUtils.isKingPawnTrap(this.board, this.playerKing, 52))
                            kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 62, (Rook) kingSideRook, kingSideRook.getPiecePosition(), 61));
                    }
                }
            }

            //whites queen side castle
            if(this.board.getPiece(59) == null && this.board.getPiece(58) == null &&
                    this.board.getPiece(57) == null) {
                final Piece queenSideRook = this.board.getPiece(56);

                if(queenSideRook != null && queenSideRook.isFirstMove()) {
                    if(Player.calculateAttacksOnTile(58, opponentPlayerMoves).isEmpty() &&
                            Player.calculateAttacksOnTile(59, opponentPlayerMoves).isEmpty() && queenSideRook.getPieceType() == ROOK) {

                        if(!VirtualBoardUtils.isKingPawnTrap(this.board, this.playerKing, 52))
                            kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 58, (Rook) queenSideRook, queenSideRook.getPiecePosition(), 59));
                    }
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
