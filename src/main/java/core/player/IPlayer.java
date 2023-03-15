package core.player;

import core.movements.Move;
import core.utils.Utils;
import core.pieces.piece.Piece;

import java.util.Collection;

/**
 * Questa interfaccia contiene i metodi dei {@link WhitePlayer} e {@link BlackPlayer}
 */
public interface IPlayer {

    /**
     * @return gli utility dei giocatori
     */
    Utils getUtils();

    /**
     * @return Il giocatore avversario. Quindi nella classe {@link WhitePlayer} ritornerà il giocatore nero e viceversa.
     */
    Player getOpponent();

    /**
     * @return Tutte le pedine attive per il giocatore
     */
    Collection<Piece> getActivePieces();

    /**
     * Questo metodo serve per calcolare tutte le mosse che mettono sotto scacco i RE
     * Ogni giocatore ha una specializzazione del metodo stesso
     * @param playerUsableMoves tutte le mosse praticabili dal giocatore corrente
     * @param opponentPlayerMoves tutte le mosse praticabili dal giocatore opposto
     * @return tutte le mosse che soddisfino la condizione, cosi da poterle aggiungere
     */
    Collection<Move> calculateKingCastles(Collection<Move> playerUsableMoves, Collection<Move> opponentPlayerMoves);
}
