package core.movements;

/**
 * Questa classe "enum" viene utilizzata per rappresentare i vari stati di una mossa.
 * Ovvero se la mossa è andata a buon fine o meno basandosi sul metodo "isDone"
 */
public enum MoveStatus {
    DONE {
        @Override
        public boolean isDone() {
            return true;
        }
    },
    LEAVES_PLAYER_IN_CHECK {
        @Override
        public boolean isDone() {
            return false;
        }
    },
    ILLEGAL_MOVE {
        @Override
        public boolean isDone() {
            return false;
        }
    };

    /**
     * Metodo che viene implementato nei due possibili tipi di stati
     * @return valore booleano di valore "TRUE" solamente se la mossa è andata a buon fine, altrimenti "FALSE"
     */
    public abstract boolean isDone();
}
