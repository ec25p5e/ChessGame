package pgn;

import core.utils.Utils;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class Game implements IPlayable {

    protected final PGNGameTags tags;
    protected final List<String> moves;
    protected final String winner;

    public Game(final PGNGameTags tags, final List<String> moves, final String outcome) {
        this.tags = tags;
        this.moves = moves;
        this.winner = calculateWinner(outcome);
    }

    @Override
    public String toString() {
        return this.tags.toString();
    }

    private static String calculateWinner(final String gameOutcome) {
        if(gameOutcome.equals("1-O"))
            return Utils.WHITE.toString();

        if(gameOutcome.equals("O-1"))
            return Utils.BLACK.toString();

        if(gameOutcome.equals("1/2-1/2"))
            return "Tie";

        return "None";
    }
}
