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
     * @return l'attributo degli utils riguardante il colore del giocatore
     */
    @Override
    public Utils getPlayerColor() {
        return Utils.WHITE;
    }

    /**
     * @return Il giocatore avversario. Quindi nella classe {@link WhitePlayer} ritornerà il giocatore nero e viceversa.
     */
    @Override
    public BlackPlayer getOpponentPlayer() {
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
