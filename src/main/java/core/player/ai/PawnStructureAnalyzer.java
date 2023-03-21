package core.player.ai;

import core.pieces.piece.Piece;
import core.pieces.piece.PieceType;
import core.player.Player;

import java.util.Collection;
import java.util.stream.Collectors;

public final class PawnStructureAnalyzer {

    private static final PawnStructureAnalyzer INSTANCE = new PawnStructureAnalyzer();

    public static final int ISOLATED_PAWN_PENALTY = -10;
    public static final int DOUBLED_PAWN_PENALTY = -10;

    private PawnStructureAnalyzer() {
    }

    public static PawnStructureAnalyzer get() {
        return INSTANCE;
    }

    public int isolatedPawnPenalty(final Player player) {
        return calculateIsolatedPawnPenalty(createPawnColumnTable(calculatePlayerPawns(player)));
    }

    public int doubledPawnPenalty(final Player player) {
        return calculatePawnColumnStack(createPawnColumnTable(calculatePlayerPawns(player)));
    }

    public int pawnStructureScore(final Player player) {
        final int[] pawnsOnColumnTable = createPawnColumnTable(calculatePlayerPawns(player));
        return calculatePawnColumnStack(pawnsOnColumnTable) + calculateIsolatedPawnPenalty(pawnsOnColumnTable);
    }

    private static Collection<Piece> calculatePlayerPawns(final Player player) {
        return player.getActivePieces().stream().filter(piece -> piece.getPieceType() == PieceType.PAWN).collect(Collectors.toList());
    }

    /**
     * Questo metodo serve per calcolare le pedine sullo stack di colonne
     * @param pawnsOnColumnTable matrice di numeri interi
     * @return numero intero
     */
    private static int calculatePawnColumnStack(final int[] pawnsOnColumnTable) {
        int pawnStackPenalty = 0;
        for(final int pawnStack : pawnsOnColumnTable) {
            if(pawnStack > 1)
                pawnStackPenalty += pawnStack;
        }

        return pawnStackPenalty * DOUBLED_PAWN_PENALTY;
    }

    /**
     * Questo metodo si occupa di calcolare la penalità di isolamente
     * @param pawnsOnColumnTable matrice di numeri interi
     * @return numero intero
     */
    private static int calculateIsolatedPawnPenalty(final int[] pawnsOnColumnTable) {
        int numIsolatedPawns = 0;
        if(pawnsOnColumnTable[0] > 0 && pawnsOnColumnTable[1] == 0)
            numIsolatedPawns += pawnsOnColumnTable[0];

        if(pawnsOnColumnTable[7] > 0 && pawnsOnColumnTable[6] == 0)
            numIsolatedPawns += pawnsOnColumnTable[7];

        for(int i = 1; i < pawnsOnColumnTable.length - 1; i++) {
            if((pawnsOnColumnTable[i-1] == 0 && pawnsOnColumnTable[i+1] == 0))
                numIsolatedPawns += pawnsOnColumnTable[i];
        }

        return numIsolatedPawns * ISOLATED_PAWN_PENALTY;
    }

    /**
     * Questo metodo serve per create la tabella di pedine di tipo pedone
     * @param playerPawns lista di pedine di tipo pedone
     * @return matrice di numeri interi
     */
    private static int[] createPawnColumnTable(final Collection<Piece> playerPawns) {
        final int[] table = new int[8];
        for(final Piece playerPawn : playerPawns) {
            table[playerPawn.getPiecePosition() % 8]++;
        }

        return table;
    }
}

