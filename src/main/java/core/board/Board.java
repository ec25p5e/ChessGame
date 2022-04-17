package core.board;

import core.pieces.Knight;
import core.pieces.Rook;
import core.pieces.piece.Piece;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
public final class Board {
    private final Map<Integer, Piece> boardConfig;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;

    private static final Board DEFAULT_BOARD = createDefaultBoardImpl();

    private Board(final Builder boardBuilder) {
        this.boardConfig = Collections.unmodifiableMap(boardBuilder.boardConfig);
        this.whitePieces = null;
        this.blackPieces = null;
    }

    public Piece getPiece(final int coordinate) {
        return this.boardConfig.get(coordinate);
    }

    public static Board createDefaultBoard() {
        return DEFAULT_BOARD;
    }

    public static class Builder {
        private Map<Integer, Piece> boardConfig;

        public Builder() {
            this.boardConfig = new HashMap<>(32, 1.0f);
        }

        public Builder setPiece(final Piece piece) {
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }

        public Board build() {
            return new Board(this);
        }
    }

    private static Board createDefaultBoardImpl() {
        final Builder builder = new Builder();

        builder.setPiece(new Rook(0));
        builder.setPiece(new Knight(1));

        return builder.build();
    }
}
