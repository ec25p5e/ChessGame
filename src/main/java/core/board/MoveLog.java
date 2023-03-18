package core.board;

import core.movements.Move;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class MoveLog {
    @Getter
    private final List<Move> moves;

    public MoveLog() {
        this.moves = new ArrayList<>();
    }

    public void addMove(final Move move) {
        this.moves.add(move);
    }

    public Move removeMove(final int index) {
        return this.moves.remove(index);
    }

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
