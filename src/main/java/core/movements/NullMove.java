package core.movements;

import core.board.VirtualBoard;

/**
 * Questa classe si occupa di creare una mossa senza alcun valore per il gioco.
 * Contiene tutti i metodi della classe {@link Move} ma senza la possibilità di far evolvere lo stato della partita.
 * Viene utilizzata quando il giocatore vuole muovere una pedina dell'avversario o una mossa non usabile,...
 */
public class NullMove extends Move {

    public NullMove() {
        super(null, -1);
    }

    @Override
    public int getCurrentCoordinate() {
        return -1;
    }

    @Override
    public int getDestinationCoordinate() {
        return -1;
    }

    /**
     * Questo metodo è quello che servirebbe per aggiornare le pedine ed eseguire la mossa
     * In questo caso genera un'eccezione perché è una mossa nulla, quindi nulla viene mosso
     * @return scacchiera "virtuale" senza alcun aggiornamento eseguito
     */
    @Override
    public VirtualBoard run() {
        throw new RuntimeException("Ma secondo te posso eseguire una mossa nulla ?!?!");
    }

    @Override
    public String toString() {
        return "Null Move";
    }
}
