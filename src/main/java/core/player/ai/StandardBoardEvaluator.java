package core.player.ai;

import core.board.VirtualBoard;
import core.move.Move;
import core.pieces.piece.Piece;
import core.pieces.piece.PieceType;
import core.player.Player;

public class StandardBoardEvaluator implements IBoardEvaluator {

    private final static int CHECK_MATE_BONUS = 10000;
    private final static int CHECK_BONUS = 45;
    private final static int CASTLE_BONUS = 25;
    private final static int MOBILITY_MULTIPLAYER = 5;
    private final static int ATTACK_MULTIPLAYER = 1;
    private final static int TWO_BISHOPS_BONUS = 25;
    private static final StandardBoardEvaluator INSTANCE = new StandardBoardEvaluator();

    private StandardBoardEvaluator(){
    }

    public static StandardBoardEvaluator get() {
        return INSTANCE;
    }

    /**
     * Questo metodo serve per valutare la scacchiera, come un'entry point method
     * @param board scacchiera virtuale di riferimento
     * @param depth profondità di pensiero del valutatore
     * @return numero intero
     */
    @Override
    public int evaluate(final VirtualBoard board, final int depth) {
        return score(board.getWhitePlayer(), depth) - score(board.getBlackPlayer(), depth);
    }

    /**
     * Questo metodo serve per calcolare il punteggio del giocatore
     * @param player giocatore corrente
     * @param depth profondità di pensiero
     * @return numero intero, indica il punteggio
     */
    private static int score(final Player player, final int depth) {
        return mobility(player) +
                kingThreats(player, depth) +
                attacks(player) +
                castle(player) +
                pieceEvaluations(player) +
                pawnStructure(player);
    }

    /**
     * Questo metodo viene utilizzato per calcolare il punteggio.
     * In questo caso calcola l'attacco del player
     * @param player giocatore corrente
     * @return numero intero
     */
    private static int attacks(final Player player) {
        int attackScore = 0;
        for(final Move move : player.getUsableMoves()) {
            if(move.isAttack()) {
                final Piece movedPiece = move.getPieceToMove();
                final Piece attackedPiece = move.getPieceAttacked();

                if(movedPiece.getPieceValue() <= attackedPiece.getPieceValue())
                    attackScore++;
            }
        }

        return attackScore * ATTACK_MULTIPLAYER;
    }

    /**
     * Questo metodo viene utilizzato per calcolare il punteggio.
     * In questo caso calcola il valore delle pedine attive
     * @param player giocatore corrente
     * @return numero intero
     */
    private static int pieceEvaluations(final Player player) {
        int pieceValuationScore = 0;
        int numBishops = 0;

        for(final Piece piece : player.getActivePieces()) {
            pieceValuationScore += piece.getPieceValue() + piece.locationBonus();

            if(piece.getPieceType() == PieceType.BISHOP)
                numBishops++;
        }

        return pieceValuationScore + (numBishops == 2 ? TWO_BISHOPS_BONUS : 0);
    }

    /**
     * Questo metodo viene utilizzato per calcolare il punteggio.
     * In questo caso calcola la mobilità del player
     * @param player giocatore corrente
     * @return numero intero
     */
    private static int mobility(final Player player) {
        return MOBILITY_MULTIPLAYER * mobilityRatio(player);
    }

    /**
     * Questo metodo viene utilizzato per calcolare il punteggio.
     * In questo caso il rateo di mobilità del player
     * @param player giocatore corrente
     * @return numero intero
     */
    private static int mobilityRatio(final Player player) {
        return (int)((player.getUsableMoves().size() * 10.0f) / player.getOpponent().getUsableMoves().size());
    }

    /**
     * Questo metodo viene utilizzato per calcolare il punteggio.
     * In questo caso calcola lo scacco matto del re
     * @param player giocatore corrente
     * @return numero intero
     */
    private static int kingThreats(final Player player, final int depth) {
        return player.getOpponent().isInCheckMate() ? CHECK_MATE_BONUS  * depthBonus(depth) : check(player);
    }

    /**
     * Questo metodo viene utilizzato per calcolare il punteggio.
     * In questo caso calcola il bonus della profondità di pensiero
     * @param depth profondità di pensiero
     * @return numero intero
     */
    private static int depthBonus(final int depth) {
        return depth == 0 ? 1 : 100 * depth;
    }

    /**
     * Questo metodo viene utilizzato per calcolare il punteggio.
     * In questo caso calcola se il giocatore è sotto scacco
     * @param player giocatore corrente
     * @return numero intero
     */
    private static int check(final Player player) {
        return player.getOpponent().isInCheck() ? CHECK_BONUS : 0;
    }


    /**
     * Questo metodo viene utilizzato per calcolare il punteggio.
     * @param player giocatore corrente
     * @return numero intero
     */
    private static int castle(final Player player) {
        return player.isCastled() ? CASTLE_BONUS : 0;
    }

    /**
     * Questo metodo viene utilizzato per calcolare il punteggio.
     * In questo caso calcola la struttura dei pedoni
     * @param player giocatore corrente
     * @return numero intero
     */
    private static int pawnStructure(final Player player) {
        return PawnStructureAnalyzer.get().pawnStructureScore(player);
    }
}
