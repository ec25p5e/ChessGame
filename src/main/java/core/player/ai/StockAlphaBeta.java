package core.player.ai;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import core.board.VirtualBoard;
import core.board.VirtualBoardUtils;
import core.movements.Move;
import core.movements.MoveFactory;
import core.movements.MoveTransition;
import core.player.Player;
import core.utils.Utils;
import lombok.Getter;

import java.util.Collection;
import java.util.Comparator;
import java.util.Observable;

import static core.board.VirtualBoardUtils.mvvlva;

/**
 * Questa classe serve come punto d'entrata per l'AI
 */
public class StockAlphaBeta extends Observable implements IMoveStrategy {
    private final int searchDepth;
    private final IBoardEvaluator evaluator;
    private int quiescenceCount;
    @Getter
    private int boardsEvaluated;
    private static final int MAX_QUIESCENCE = 5000 * 5;

    /**
     * Questa classe "enum" serve per ordinare le mosse e poterle
     * utilizzare facilmente
     */
    private enum MoveSorter {
        STANDARD {
            @Override
            Collection<Move> sort(final Collection<Move> moves) {
                return Ordering.from((Comparator<Move>) (move1, move2) -> ComparisonChain.start()
                        .compareTrueFirst(move1.isCastlingMove(), move2.isCastlingMove())
                        .compare(mvvlva(move2), mvvlva(move1))
                        .result()).immutableSortedCopy(moves);
            }
        },
        EXPENSIVE {
            @Override
            Collection<Move> sort(final Collection<Move> moves) {
                return Ordering.from((Comparator<Move>) (move1, move2) -> ComparisonChain.start()
                        .compareTrueFirst(VirtualBoardUtils.kingThreat(move1), VirtualBoardUtils.kingThreat(move2))
                        .compareTrueFirst(move1.isCastlingMove(), move2.isCastlingMove())
                        .compare(mvvlva(move2), mvvlva(move1))
                        .result()).immutableSortedCopy(moves);
            }
        };

        /**
         * Questo metodo serve per ordinare le mosse
         * @param moves lista di mosse disordinate
         * @return le mosse ordinate
         */
        abstract Collection<Move> sort(Collection<Move> moves);
    }

    public StockAlphaBeta(final int searchDepth) {
        this.evaluator = StandardBoardEvaluator.get();
        this.searchDepth = searchDepth;
        this.quiescenceCount = 0;
        this.boardsEvaluated = 0;
    }

    @Override
    public String toString() {
        return "StockAB";
    }

    /**
     * Questo metodo serve per eseguire la logica della AI
     * @param board scacchiera virtuale di riferimento
     * @return la mossa "migliore" da eseguire secondo l'AI
     */
    @Override
    public Move execute(final VirtualBoard board) {
        final long startTime = System.currentTimeMillis();
        final Player currentPlayer = board.getCurrentPlayer();

        Move bestMove = MoveFactory.getNullMove();
        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int moveCounter = 1;
        int numMoves = board.getCurrentPlayer().getUsableMoves().size();
        int currentValue;

        System.out.println(board.getCurrentPlayer() + " PENSA con la profondità di " + this.searchDepth);

        for(final Move move : MoveSorter.EXPENSIVE.sort(board.getCurrentPlayer().getUsableMoves())) {
            final MoveTransition moveTransition = board.getCurrentPlayer().doMove(move);
            final String s;

            this.quiescenceCount = 0;

            if(moveTransition.moveStatus().isDone()) {
                final long candidateMoveStartTime = System.nanoTime();
                currentValue = currentPlayer.getUtils().isWhite()
                        ? min(moveTransition.toBoard(), this.searchDepth - 1, highestSeenValue, lowestSeenValue)
                        : max(moveTransition.toBoard(), this.searchDepth - 1, highestSeenValue, lowestSeenValue);

                if(currentPlayer.getUtils().isWhite() && currentValue > highestSeenValue) {
                    highestSeenValue = currentValue;
                    bestMove = move;

                    if(moveTransition.toBoard().getBlackPlayer().isInCheckMate())
                        break;
                } else if(currentPlayer.getUtils().isBlack() && currentValue < lowestSeenValue) {
                    lowestSeenValue = currentValue;
                    bestMove = move;

                    if(moveTransition.toBoard().getWhitePlayer().isInCheckMate())
                        break;
                }

                final String quiescenceInfo = " " + score(currentPlayer, highestSeenValue, lowestSeenValue) + " q: " +this.quiescenceCount;
                s = "\t" + this + "(" +this.searchDepth+ "), m: (" +moveCounter+ "/" +numMoves+ ") " + move + ", best:  " + bestMove

                        + quiescenceInfo + ", t: " + calculateTimeTaken(candidateMoveStartTime, System.nanoTime());
            } else {
                s = "\t" + this + "(" +this.searchDepth + ")" + ", m: (" +moveCounter+ "/" +numMoves+ ") " + move + " is illegal! best: " +bestMove;
            }

            System.out.println(s);
            setChanged();
            notifyObservers(s);
            moveCounter++;
        }

        final long executionTime = System.currentTimeMillis() - startTime;
        final String result = board.getCurrentPlayer() + " SELECTS " +bestMove+ " [#boards evaluated = " +this.boardsEvaluated+
                " time taken = " + executionTime /1000+ " rate = " +(1000 * ((double)this.boardsEvaluated/ executionTime));
        System.out.printf("%s SELECTS %s [#boards evaluated = %d, time taken = %d ms, rate = %.1f\n", board.getCurrentPlayer(),
                bestMove, this.boardsEvaluated, executionTime, (1000 * ((double)this.boardsEvaluated/ executionTime)));

        setChanged();
        notifyObservers(result);

        return bestMove;
    }

    /**
     *
     * @param board
     * @param depth
     * @param highest
     * @param lowest
     * @return
     */
    private int max(final VirtualBoard board, final int depth, final int highest, final int lowest) {
        if (depth == 0 || VirtualBoardUtils.isEndGame(board)) {
            this.boardsEvaluated++;
            return this.evaluator.evaluate(board, depth);
        }

        int currentHighest = highest;

        for (final Move move : MoveSorter.STANDARD.sort((board.getCurrentPlayer().getUsableMoves()))) {
            final MoveTransition moveTransition = board.getCurrentPlayer().doMove(move);

            if (moveTransition.moveStatus().isDone()) {
                final VirtualBoard toBoard = moveTransition.toBoard();

                currentHighest = Math.max(currentHighest, min(toBoard,
                        calculateQuiescenceDepth(toBoard, depth), currentHighest, lowest));

                if (currentHighest >= lowest)
                    return lowest;
            }
        }

        return currentHighest;
    }

    /**
     *
     * @param board
     * @param depth
     * @param highest
     * @param lowest
     * @return
     */
    private int min(final VirtualBoard board, final int depth, final int highest, final int lowest) {
        if (depth == 0 || VirtualBoardUtils.isEndGame(board)) {
            this.boardsEvaluated++;
            return this.evaluator.evaluate(board, depth);
        }

        int currentLowest = lowest;

        for (final Move move : MoveSorter.STANDARD.sort((board.getCurrentPlayer().getUsableMoves()))) {
            final MoveTransition moveTransition = board.getCurrentPlayer().doMove(move);
            if (moveTransition.moveStatus().isDone()) {
                final VirtualBoard toBoard = moveTransition.toBoard();

                currentLowest = Math.min(currentLowest, max(toBoard,
                        calculateQuiescenceDepth(toBoard, depth), highest, currentLowest));

                if (currentLowest <= highest)
                    return highest;
            }
        }
        return currentLowest;
    }

    /**
     *
     * @param toBoard
     * @param depth
     * @return
     */
    private int calculateQuiescenceDepth(final VirtualBoard toBoard, final int depth) {
       if(depth == 1 && this.quiescenceCount < MAX_QUIESCENCE) {
           int activityMeasure = 0;

           if(toBoard.getCurrentPlayer().isInCheck())
               activityMeasure += 1;

           for(final Move move : VirtualBoardUtils.lastNMoves(toBoard, 2)) {
               if(move.isAttack())
                   activityMeasure += 1;
           }

           if(activityMeasure >= 2) {
               this.quiescenceCount++;
               return 2;
           }
       }

       return depth - 1;
    }

    /**
     *
     * @param currentPlayer
     * @param highestSeenValue
     * @param lowestSeenValue
     * @return
     */
    private static String score(final Player currentPlayer, final int highestSeenValue, final int lowestSeenValue) {
        if(currentPlayer.getUtils().isWhite())
            return "[score: " + highestSeenValue + "]";
        else if(currentPlayer.getUtils().isBlack())
            return "[score: " + lowestSeenValue + "]";

        throw new RuntimeException("StockAlphaBeta[-score]: runtime exception!");
    }

    /**
     *
     * @param start
     * @param end
     * @return
     */
    private static String calculateTimeTaken(final long start, final long end) {
        final long timeTaken = (end - start) / 1000000;
        return timeTaken + " ms";
    }
}
