package core.move;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static util.Constants.*;
import static util.Constants.NPE_EXCEPTION;

public class MoveLog {
    @Getter
    private final List<Move> moves;

    public MoveLog() {
        this.moves = new ArrayList<>();
    }

    /**
     *
     * @param move
     */
    public void addMove(final Move move) {
        this.moves.add(move);
    }

    /**
     *
     * @param index
     * @return
     */
    public Move removeMove(final int index) {
        Move remove = this.moves.remove(index);

        return remove;
    }

    /**
     *
     * @param move
     * @return
     */
    public boolean removeMove(final Move move) {
        boolean remove = this.moves.remove(move);

        return remove;
    }

    public int size() {
        return this.moves.size();
    }

    public void clear() {
        this.moves.clear();
    }
}
