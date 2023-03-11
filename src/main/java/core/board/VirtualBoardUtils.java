package core.board;

import core.pieces.King;
import core.pieces.piece.Piece;
import core.pieces.piece.PieceType;

import java.util.*;

/**
 * Questo metodo contiene gli attributi e metodi utili alla manipolazione da effettuare
 * con la scacchiera virtuale come conversione da notazione numerica ad algebrica e viceversa, identificare le celle della
 * medesima riga.
 */
public enum VirtualBoardUtils {
    INSTANCE;

    public final List<Boolean> FIRST_COLUMN = setupColumn(0);
    public final List<Boolean> SECOND_COLUMN = setupColumn(1);
    public final List<Boolean> THIRD_COLUMN = setupColumn(2);
    public final List<Boolean> FOURTH_COLUMN = setupColumn(3);
    public final List<Boolean> FIFTH_COLUMN = setupColumn(4);
    public final List<Boolean> SIXTH_COLUMN = setupColumn(5);
    public final List<Boolean> SEVENTH_COLUMN = setupColumn(6);
    public final List<Boolean> EIGHTH_COLUMN = setupColumn(7);
    public final List<Boolean> FIRST_ROW = setupRow(0);
    public final List<Boolean> SECOND_ROW = setupRow(8);
    public final List<Boolean> THIRD_ROW = setupRow(16);
    public final List<Boolean> FOURTH_ROW = setupRow(24);
    public final List<Boolean> FIFTH_ROW = setupRow(32);
    public final List<Boolean> SIXTH_ROW = setupRow(40);
    public final List<Boolean> SEVENTH_ROW = setupRow(48);
    public final List<Boolean> EIGHTH_ROW = setupRow(56);
    public final List<String> ALGEBRAIC_NOTATION = setupAlgebraic();
    public final Map<String, Integer> POSITION_TO_COORD = setupPosToCoordMap();

    public static final int START_TILE_INDEX = 0;
    public static final int NUM_TILES_ROW = 8;
    public static final int NUM_TILES = 64;

    /**
     * Questo metodo serve a verificare se una coordinata è all'interno della scacchiera
     * @param coordinate valore da verificare
     * @return valore booleano a "TRUE" se la coordinata è maggiore uguale a 0 e minore di 64
     */
    public static boolean isValidTileCoordinate(int coordinate) {
        return coordinate >= START_TILE_INDEX && coordinate < NUM_TILES;
    }

    /**
     * Questo metodo si occupa di trovare l'id della cella associata alla coordinata in notazione algebrica
     * @param position valore da trovare in notazione algebrica
     * @return Numero intero che identifica le celle. Valore da 0 a 63
     */
    public int getCoordinateAtPosition(final String position) {
        return POSITION_TO_COORD.get(position);
    }

    /**
     * Ritorna l'identificativo della cella nella notazione ALGEBRICA
     * @param coordinate id della cella di riferimento
     * @return stringa contenente la coordinata (carattere + numero)
     */
    public String getPositionAtCoordinate(final int coordinate) {
        return ALGEBRAIC_NOTATION.get(coordinate);
    }

    /**
     * Questo metodo si occupa di verificare quando il giocatore è sotto scacco o è in stallo con le mosse
     * @param board scacchiera "virtuale" di riferimento
     * @return "TRUE" se sotto scacco o in stallo
     */
    public static boolean isGameOver(final VirtualBoard board) {
        return board.getCurrentPlayer().isInCheckMate() || board.getCurrentPlayer().isInStaleMate();
    }

    /**
     * Questo metodo serve a verificare se a una coordinata è posizionato un pedone.
     * Viene confrontato con un RE in base al colore e viene verificato il tipo e che non sia nullo
     * @param board scacchiera "virtuale" di riferimento
     * @param king RE da utilizzare come confronto
     * @param frontTile coordinata da usare come riferimento sulla scacchiera
     * @return "TRUE" se ci sia una pedina, sia un pedone e abbia un colore diverso rispetto al RE. Altrimenti "FALSE"
     */
    public static boolean isKingPawn(final VirtualBoard board, final King king, final int frontTile) {
        final Piece piece = board.getPiece(frontTile);
        return piece != null && piece.getPieceType() == PieceType.PAWN && piece.getPieceUtils() != king.getPieceUtils();
    }

    /**
     * Questo metodo si occupa di creare una Map di coppie di stringhe e d'interi
     * Lo scopo è quello di poter risalire tramite un id a una coordinata algebrica
     * senza dover effettuare calcoli a ogni richiesta.
     * Questo metodo viene chiamato all'inizio del gioco.
     * @return mappa di stringhe e interi
     */
    private Map<String, Integer> setupPosToCoordMap() {
        final Map<String, Integer> posToCoord = new HashMap<>();

        for(int i = START_TILE_INDEX; i < NUM_TILES; i++) {
            posToCoord.put(ALGEBRAIC_NOTATION.get(i), i);
        }

        return Collections.unmodifiableMap(posToCoord);
    }

    /**
     * Crea una lista di coordinate algebriche per identificare le celle della scacchiera
     * @return una lista di stringhe di lunghezza 64
     */
    private static List<String> setupAlgebraic() {
        return List.of("a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8","a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7","a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6","a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5","a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4","a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3","a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2","a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1");
    }

    /**
     * Questo metodo si occupa di creare una lista di valori booleani, basandosi sul numero passato come argomento.
     * Nel caso fosse passato 0 come valore, la matrice conterrebbe 8 valori a "TRUE" e 56 a "FALSE" cosi da poter identificare le celle che appartengono alla medesima riga.
     * Questo metodo viene chiamato 8 volte negli 8 attributi che identificano le 8 diverse righe.
     * @param numRow numero intero utilizzato come riferimento. Progressione di 8 in 8
     * @return una lista di valori booleani
     */
    private static List<Boolean> setupRow(int numRow) {
        final Boolean[] row = new Boolean[NUM_TILES];
        Arrays.fill(row, false);

        do {
            row[numRow] = true;
            numRow++;
        } while(numRow % NUM_TILES_ROW != 0);

        return List.of(row);
    }

    /**
     * Questo metodo si occupa di creare una lista di valori booleani, basandosi sul numero passato come argomento.
     * Nel caso fosse passato 0 come valore, la matrice conterrebbe 8 valori a "TRUE" e 56 a "FALSE" cosi da poter identificare le celle che appartengono alla medesima colonna.
     * Questo metodo viene chiamato 8 volte negli 8 attributi che identificano le 8 diverse colonne.
     * @param numCol numero intero utilizzato come riferimento. Progressione di 8 in 8
     * @return una lista di valori booleani
     */
    private static List<Boolean> setupColumn(int numCol) {
        final Boolean[] column = new Boolean[NUM_TILES];
        Arrays.fill(column, false);

        do {
            column[numCol] = true;
            numCol += NUM_TILES_ROW;
        } while(numCol < NUM_TILES);

        return List.of(column);
    }
}
