package core.player;

import core.utils.Utils;
import core.pieces.piece.Piece;

import java.util.Collection;

/**
 * Questa interfaccia contiene i metodi dei {@link WhitePlayer} e {@link BlackPlayer}
 */
public interface IPlayer {

    /**
     * @return l'attributo degli utils riguardante il colore del giocatore
     * Questo metodo diventerà obsoleto con l'aggiunta di "getUtils"
     */
    Utils getPlayerColor();

    /**
     * @return gli utility dei giocatori
     */
    Utils getUtils();

    /**
     * @return Il giocatore avversario. Quindi nella classe {@link WhitePlayer} ritornerà il giocatore nero e viceversa.
     */
    Player getOpponentPlayer();

    /**
     * @return Tutte le pedine attive per il giocatore
     */
    Collection<Piece> getActivePieces();
}
