package core.player.ai;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import core.board.VirtualBoard;
import core.board.VirtualBoardUtils;
import core.move.Move;
import core.move.MoveFactory;
import core.move.MoveTransition;
import core.player.Player;
import lombok.Getter;

import java.util.Collection;
import java.util.Comparator;
import java.util.Observable;

import static core.board.VirtualBoardUtils.mvvlva;

/**
 * Questa classe serve come punto d'entrata per l'AI
 */
@Getter
public class StockAlphaBeta extends Observable implements IMoveStrategy {
    private final IBoardEvaluator evaluator;
    private final int searchDepth;
    private long boardsEvaluated;
    private int quiescenceCount;
    private static final int MAX_QUIESCENCE = 5000 * 5;

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

        abstract  Collection<Move> sort(Collection<Move> moves);
    }


    public StockAlphaBeta(final int searchDepth) {
        this.evaluator = StandardBoardEvaluator.get();
        this.searchDepth = searchDepth;
        this.boardsEvaluated = 0;
        this.quiescenceCount = 0;
    }

    @Override
    public String toString() {
        return "StockAB";
    }

    @Override
    public Move execute(final VirtualBoard board) {
        final long startTime = System.currentTimeMillis();
        final Player getCurrentPlayer = board.getCurrentPlayer();
        Move bestMove = MoveFactory.getNullMove();
        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue;
        System.out.println(board.getCurrentPlayer() + " THINKING with depth = " + this.searchDepth);
        int moveCounter = 1;
        int numMoves = board.getCurrentPlayer().getUsableMoves().size();
        for (final Move move : MoveSorter.EXPENSIVE.sort((board.getCurrentPlayer().getUsableMoves()))) {
            final MoveTransition moveTransition = board.getCurrentPlayer().doMove(move);
            this.quiescenceCount = 0;
            final String s;
            if (moveTransition.moveStatus().isDone()) {
                final long candidateMoveStartTime = System.nanoTime();
                currentValue = getCurrentPlayer.getUtils().isWhite() ?
                        min(moveTransition.toBoard(), this.searchDepth - 1, highestSeenValue, lowestSeenValue) :
                        max(moveTransition.toBoard(), this.searchDepth - 1, highestSeenValue, lowestSeenValue);
                if (getCurrentPlayer.getUtils().isWhite() && currentValue > highestSeenValue) {
                    highestSeenValue = currentValue;
                    bestMove = move;
                    if(moveTransition.toBoard().getBlackPlayer().isInCheckMate()) {
                        break;
                    }
                }
                else if (getCurrentPlayer.getUtils().isBlack() && currentValue < lowestSeenValue) {
                    lowestSeenValue = currentValue;
                    bestMove = move;
                    if(moveTransition.toBoard().getWhitePlayer().isInCheckMate()) {
                        break;
                    }
                }

                final String quiescenceInfo = " " + score(getCurrentPlayer, highestSeenValue, lowestSeenValue) + " q: " +this.quiescenceCount;
                s = "\t" + this + "(" +this.searchDepth+ "), m: (" +moveCounter+ "/" +numMoves+ ") " + move + ", best:  " + bestMove

                        + quiescenceInfo + ", t: " +calculateTimeTaken(candidateMoveStartTime, System.nanoTime());
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

    private static String score(final Player getCurrentPlayer,
                                final int highestSeenValue,
                                final int lowestSeenValue) {

        if(getCurrentPlayer.getUtils().isWhite()) {
            return "[score: " +highestSeenValue + "]";
        } else if(getCurrentPlayer.getUtils().isBlack()) {
            return "[score: " +lowestSeenValue+ "]";
        }
        throw new RuntimeException("bad bad boy!");
    }

    private int max(final VirtualBoard board,
                    final int depth,
                    final int highest,
                    final int lowest) {
        if (depth == 0 || VirtualBoardUtils.isEndGame(board)) {
            this.boardsEvaluated++;
            return this.evaluator.evaluate(board, depth);
        }
        int currentHighest = highest;
        for (final Move move : MoveSorter.STANDARD.sort((board.getCurrentPlayer().getUsableMoves()))) {
            final MoveTransition moveTransition = board.getCurrentPlayer().doMove(move);
            if (moveTransition.moveStatus().isDone()) {
                final VirtualBoard toVirtualBoard = moveTransition.toBoard();
                currentHighest = Math.max(currentHighest, min(toVirtualBoard,
                        calculateQuiescenceDepth(toVirtualBoard, depth), currentHighest, lowest));
                if (currentHighest >= lowest) {
                    return lowest;
                }
            }
        }
        return currentHighest;
    }

    private int min(final VirtualBoard board,
                    final int depth,
                    final int highest,
                    final int lowest) {
        if (depth == 0 || VirtualBoardUtils.isEndGame(board)) {
            this.boardsEvaluated++;
            return this.evaluator.evaluate(board, depth);
        }
        int currentLowest = lowest;
        for (final Move move : MoveSorter.STANDARD.sort((board.getCurrentPlayer().getUsableMoves()))) {
            final MoveTransition moveTransition = board.getCurrentPlayer().doMove(move);
            if (moveTransition.moveStatus().isDone()) {
                final VirtualBoard toVirtualBoard = moveTransition.toBoard();
                currentLowest = Math.min(currentLowest, max(toVirtualBoard,
                        calculateQuiescenceDepth(toVirtualBoard, depth), highest, currentLowest));
                if (currentLowest <= highest) {
                    return highest;
                }
            }
        }
        return currentLowest;
    }

    private int calculateQuiescenceDepth(final VirtualBoard toVirtualBoard,
                                         final int depth) {
        if(depth == 1 && this.quiescenceCount < MAX_QUIESCENCE) {
            int activityMeasure = 0;
            if (toVirtualBoard.getCurrentPlayer().isInCheck()) {
                activityMeasure += 1;
            }
            for(final Move move: VirtualBoardUtils.lastNMoves(toVirtualBoard, 2)) {
                if(move.isAttack()) {
                    activityMeasure += 1;
                }
            }
            if(activityMeasure >= 2) {
                this.quiescenceCount++;
                return 2;
            }
        }
        return depth - 1;
    }

    private static String calculateTimeTaken(final long start, final long end) {
        final long timeTaken = (end - start) / 1000000;
        return timeTaken + " ms";
    }
}
