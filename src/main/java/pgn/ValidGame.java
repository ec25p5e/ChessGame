package pgn;

import java.util.List;

public class ValidGame extends Game {

    public ValidGame(final PGNGameTags tags, final List<String> moves, final String outcome) {
        super(tags, moves, outcome);
    }

    /**
     * @return
     */
    @Override
    public boolean isValid() {
        return true;
    }
}
