package core.utils;

import core.player.BlackPlayer;
import core.player.Player;
import core.player.WhitePlayer;

public interface IUtils {

    /**
     * @return valore booleano "TRUE" se la pedina è di color bianco
     */
    boolean isWhite();

    /**
     * @return valore booleano "TRUE" se la pedina è di color nero
     */
    boolean isBlack();

    /**
     * Esegue il controllo se il pedone è sulla prima riga della scacchiera. Dunque è giunto dall'altro lato
     * La prima riga per i bianchi sarà la prima effettiva mentre per i neri sarà l'ottava riga
     * @param position coordinata usata per controllare la prima riga
     * @return Valore booleano "TRUE" se è arrivato sulla prima riga. Altrimenti "FALSE"
     */
    boolean isPawnPromotionSquare(final int position);

    /**
     * @return valore intero che indica la direzione da intraprendere per muoversi avanti verso il giocatore nemico
     *         Il numero oscilla tra -1 e 1
     */
    int getDirection();

    /**
     * Questo metodo ritorna il valore opposto al metodo "getDirection" in {@link IUtils}
     * @return valore intero che indica la direzione da intraprendere per indietro e avanti verso il giocatore nemico
     *         Il numero oscilla tra 1 e -1
     */
    int getOppositeDirection();

    /**
     * Scegli tra i due colori di giocatori quale usare.
     * Implementato nel "WHITE" ritornerà il giocatore bianco e viceversa
     * @param whitePlayer giocatore di colore bianco
     * @param blackPlayer giocatore di colore nero
     * @return Un giocatore tra i due (bianco/nero)
     */
    Player selectPlayerByUtils(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer);

    /**
     *
     * @param position
     * @return
     */
    int pawnBonus(int position);

    /**
     *
     * @param position
     * @return
     */
    int knightBonus(int position);

    /**
     *
     * @param position
     * @return
     */
    int bishopBonus(int position);

    /**
     *
     * @param position
     * @return
     */
    int rookBonus(int position);

    /**
     *
     * @param position
     * @return
     */
    int kingBonus(int position);

    /**
     *
     * @param position
     * @return
     */
    int queenBonus(int position);

    /**
     * @return la stesura del colore (bianco/nero)
     */
    @Override
    String toString();

    /**
     * @return metodo wrapper del toString, e ritorna tutto in uppercase
     */
    String toStringUpper();

    /**
     * @return metodo wrapper del toString, e ritorna tutto in lowercase
     */
    String toStringLower();
}
