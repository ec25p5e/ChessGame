package core.board;

import core.pieces.piece.Piece;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
public final class Board {
    private final Map<Integer, Piece> boardConfig;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;

    private Board(final Builder boardBuilder) {
        this.boardConfig = Collections.unmodifiableMap(boardBuilder.boardConfig);
        this.whitePieces = null;
        this.blackPieces = null;
    }

    public Piece getPiece(final int coordinate) {
        return this.boardConfig.get(coordinate);
    }

    private static Board createDefaultBoard() {
        final Builder builder = new Builder();
        return builder.build();
    }

    @Getter
    @Setter
    public static class Builder {
        private Map<Integer, Piece> boardConfig;

        public Builder() {
            this.boardConfig = new HashMap<>(32, 1.0f);
        }

        public Board build() {
            return new Board(this);
        }
    }
}
