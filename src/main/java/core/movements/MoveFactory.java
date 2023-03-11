package core.movements;

import core.board.VirtualBoard;

/**
 * Questa classe serve per creare le mosse.
 * È pensata per essere come una creatrice di movimenti, passando la scacchiera virtuale, la sorgente e la destinazione.
 */
public class MoveFactory {

    private static final Move NULL_MOVE = new NullMove();

    /**
     * Il costruttore della classe è private perché questa classe è pensata per essere adibita a creatrice e a non poter esistere una sua istanza
     * ma avere solamente la possibilità di utilizzare i suoi metodi statici.
     */
    private MoveFactory() {
        throw new RuntimeException("Ma come pensi di potermi istanziare!?");
    }

    /**
     * @return una mossa ma senza nulla che possa fare, una mossa inutile (nulla)
     */
    public static Move getNullMove() {
        return NULL_MOVE;
    }

    /**
     * Questo metodo si occupa di ritornare le mosse attuabili tra tutte quelle attuabili su tutta la scacchiera
     * filtrando per coordinata di partenza e destinazione
     * @param board scacchiera virtuale di riferimento
     * @param sourceCoordinate coordinata di partenza della pedina
     * @param destinationCoordinate coordinata di destinazione della pedina
     * @return La mossa attuabile. Può essere solo una perché un movimento equivale a una mossa.
     */
    public static Move createMove(final VirtualBoard board, final int sourceCoordinate, final int destinationCoordinate) {
        for(final Move move : board.getAllUsableMoves()) {
            if(move.getCurrentCoordinate() == sourceCoordinate && move.getDestinationCoordinate() == destinationCoordinate)
                return move;
        }

        return NULL_MOVE;
    }
}