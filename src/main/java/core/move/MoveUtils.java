package core.move;


public enum MoveUtils {

    INSTANCE;

    /**
     * Questo metodo serve per calcolare il valore della mossa
     * @param move mossa di riferimento
     * @return valore intero per indicare il valore
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
