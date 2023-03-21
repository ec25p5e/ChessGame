package core.move;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class MoveLog {
    @Getter
    private final List<Move> moves;

    public MoveLog() {
        this.moves = new ArrayList<>();
    }

    /**
     * Questo metodo serve per aggiungere una mossa nel log
     * @param move mossa effettuata da aggiungere
     */
    public void addMove(final Move move) {
        this.moves.add(move);
    }

    /**
     * Questo metodo serve per rimuovere una mossa dai log
     * @param index indice di riferimento
     * @return mossa appena rimossa
     */
    public Move removeMove(final int index) {
        return this.moves.remove(index);
    }

    /**
     * Questo metodo serve per rimuovere una mossa dai log
     * @param move mossa da cercare nei log
     * @return valore booleano "TRUE" se la mossa è stata rimossa
     */
    public boolean removeMove(final Move move) {
        return this.moves.remove(move);
    }

    public int size() {
        return this.moves.size();
    }

    public void clear() {
        this.moves.clear();
    }
}
