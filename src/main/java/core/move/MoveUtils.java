package core.move;

/**
 *
 */
public enum MoveUtils {

    INSTANCE;

    /**
     *
     * @param move
     * @return
     */
    public static int exchangeScore(final Move move) {
        if(move == MoveFactory.getNullMove()) {
            return 1;
        }
        return move.isAttack() ?
                5 * exchangeScore(move.getBoard().getTransitionMove()) :
                exchangeScore(move.getBoard().getTransitionMove());

    }

}
