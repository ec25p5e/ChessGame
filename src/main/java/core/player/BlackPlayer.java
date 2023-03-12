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
    public BlackPlayer(final VirtualBoard board, final Collection<Move> blackUsableMoves, final Collection<Move> whiteUsableMoves) {
        super(board, blackUsableMoves, whiteUsableMoves);
    }

    /**
     * @return l'attributo degli utils riguardante il colore del giocatore
     * Questo metodo diventerà obsoleto con l'aggiunta di "getUtils"
     */
    @Override
    public Utils getPlayerColor() {
        return Utils.BLACK;
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
    public WhitePlayer getOpponentPlayer() {
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
