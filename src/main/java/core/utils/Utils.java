package core.utils;

import core.board.VirtualBoardUtils;
import core.player.BlackPlayer;
import core.player.Player;
import core.player.WhitePlayer;

import static core.utils.AIUtils.*;

public enum Utils implements IUtils {
    WHITE() {
        /**
         * @return valore booleano "TRUE" se la pedina è di color bianco
         */
        @Override
        public boolean isWhite() {
            return true;
        }

        /**
         * @return valore booleano "TRUE" se la pedina è di color nero
         */
        @Override
        public boolean isBlack() {
            return false;
        }

        /**
         * Esegue il controllo se il pedone è sulla prima riga della scacchiera. Dunque è giunto dall'altro lato.
         * La prima riga per i bianchi sarà la prima effettiva mentre per i neri sarà l'ottava riga
         * @param position coordinata usata per controllare la prima riga
         * @return Valore booleano "TRUE" se è arrivato sulla prima riga. Altrimenti "FALSE"
         */
        @Override
        public boolean isPawnPromotionSquare(int position) {
            return VirtualBoardUtils.INSTANCE.FIRST_ROW.get(position);
        }

        /**
         * @return valore intero che indica la direzione da intraprendere per muoversi avanti verso il giocatore nemico
         * Il numero oscilla tra -1 e 1
         */
        @Override
        public int getDirection() {
            return UP_DIRECTION;
        }

        /**
         * Questo metodo ritorna il valore opposto al metodo "getDirection" in {@link IUtils}
         * @return valore intero che indica la direzione da intraprendere per indietro e avanti verso il giocatore nemico
         *         Il numero oscilla tra 1 e -1
         */
        @Override
        public int getOppositeDirection() {
            return DOWN_DIRECTION;
        }

        /**
         * Scegli tra i due colori di giocatori quale usare.
         * Implementato nel "WHITE" ritornerà il giocatore bianco e viceversa
         * @param whitePlayer giocatore di colore bianco
         * @param blackPlayer giocatore di colore nero
         * @return Un giocatore tra i due (bianco/nero)
         */
        @Override
        public Player selectPlayerByUtils(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            return whitePlayer;
        }

        @Override
        public int pawnBonus(final int position) {
            return WHITE_PAWN_PREFERRED_COORDINATES[position];
        }

        @Override
        public int knightBonus(final int position) {
            return WHITE_KNIGHT_PREFERRED_COORDINATES[position];
        }

        @Override
        public int bishopBonus(final int position) {
            return WHITE_BISHOP_PREFERRED_COORDINATES[position];
        }

        @Override
        public int rookBonus(final int position) {
            return WHITE_ROOK_PREFERRED_COORDINATES[position];
        }

        @Override
        public int queenBonus(final int position) {
            return WHITE_QUEEN_PREFERRED_COORDINATES[position];
        }

        @Override
        public int kingBonus(final int position) {
            return WHITE_KING_PREFERRED_COORDINATES[position];
        }

        /**
         *
         * @return
         */
        @Override
        public String toString() {
            return "White";
        }
    },
    BLACK() {
        /**
         * @return valore booleano "TRUE" se la pedina è di color bianco
         */
        @Override
        public boolean isWhite() {
            return false;
        }

        /**
         * @return valore booleano "TRUE" se la pedina è di color nero
         */
        @Override
        public boolean isBlack() {
            return true;
        }

        /**
         * Esegue il controllo se il pedone è sulla prima riga della scacchiera. Dunque è giunto dall'altro lato
         * La prima riga per i bianchi sarà la prima effettiva mentre per i neri sarà l'ottava riga
         * @param position coordinata usata per controllare la prima riga
         * @return Valore booleano "TRUE" se è arrivato sulla prima riga. Altrimenti "FALSE"
         */
        @Override
        public boolean isPawnPromotionSquare(int position) {
            return VirtualBoardUtils.INSTANCE.EIGHTH_ROW.get(position);
        }

        /**
         * @return valore intero che indica la direzione da intraprendere per muoversi avanti verso il giocatore nemico
         * Il numero oscilla tra -1 e 1
         */
        @Override
        public int getDirection() {
            return DOWN_DIRECTION;
        }

        /**
         * Questo metodo ritorna il valore opposto al metodo "getDirection" in {@link IUtils}
         * @return valore intero che indica la direzione da intraprendere per indietro e avanti verso il giocatore nemico
         *         Il numero oscilla tra 1 e -1
         */
        @Override
        public int getOppositeDirection() {
            return UP_DIRECTION;
        }

        /**
         * Scegli tra i due colori di giocatori quale usare.
         * Implementato nel "WHITE" ritornerà il giocatore bianco e viceversa
         * @param whitePlayer giocatore di colore bianco
         * @param blackPlayer giocatore di colore nero
         * @return Un giocatore tra i due (bianco/nero)
         */
        @Override
        public Player selectPlayerByUtils(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            return blackPlayer;
        }

        @Override
        public int pawnBonus(final int position) {
            return BLACK_PAWN_PREFERRED_COORDINATES[position];
        }

        @Override
        public int knightBonus(final int position) {
            return BLACK_KNIGHT_PREFERRED_COORDINATES[position];
        }

        @Override
        public int bishopBonus(final int position) {
            return BLACK_BISHOP_PREFERRED_COORDINATES[position];
        }

        @Override
        public int rookBonus(final int position) {
            return BLACK_ROOK_PREFERRED_COORDINATES[position];
        }

        @Override
        public int queenBonus(final int position) {
            return BLACK_QUEEN_PREFERRED_COORDINATES[position];
        }

        @Override
        public int kingBonus(final int position) {
            return BLACK_KING_PREFERRED_COORDINATES[position];
        }

        /**
         *
         * @return
         */
        @Override
        public String toString() {
            return "Black";
        }
    };

    // Valore utilizzato per sapere di quanto muoversi in una determinata direzione
    // Per salire -1
    private static final int UP_DIRECTION = -1;

    // Valore utilizzato per sapere di quanto muoversi in una determinata direzione
    // Per scendere 1
    private static final int DOWN_DIRECTION = 1;
}
