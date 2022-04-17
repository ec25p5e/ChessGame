package core.board;

import core.Utils;
import core.pieces.*;
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
        private Utils moveMakerColor;

        public Builder() {
            this.boardConfig = new HashMap<>(32, 1.0f);
        }

        public Builder setPiece(final Piece piece) {
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }

        public Builder setMoveMaker(final Utils moveMakerColor) {
            this.moveMakerColor = moveMakerColor;
            return this;
        }

        public Board build() {
            return new Board(this);
        }
    }

    private static Board createDefaultBoardImpl() {
        final Builder builder = new Builder();

        // Black pieces
        builder.setPiece(new Rook(Utils.BLACK, 0));
        builder.setPiece(new Knight(Utils.BLACK, 1));
        builder.setPiece(new Bishop(Utils.BLACK, 2));
        builder.setPiece(new Queen(Utils.BLACK, 3));
        builder.setPiece(new King(Utils.BLACK, 4));
        builder.setPiece(new Bishop(Utils.BLACK, 5));
        builder.setPiece(new Knight(Utils.BLACK, 6));
        builder.setPiece(new Rook(Utils.BLACK, 7));
        builder.setPiece(new Pawn(Utils.BLACK, 8));
        builder.setPiece(new Pawn(Utils.BLACK, 9));
        builder.setPiece(new Pawn(Utils.BLACK, 10));
        builder.setPiece(new Pawn(Utils.BLACK, 11));
        builder.setPiece(new Pawn(Utils.BLACK, 12));
        builder.setPiece(new Pawn(Utils.BLACK, 13));
        builder.setPiece(new Pawn(Utils.BLACK, 14));
        builder.setPiece(new Pawn(Utils.BLACK, 15));


        builder.setPiece(new Pawn(Utils.WHITE, 48));
        builder.setPiece(new Pawn(Utils.WHITE, 49));
        builder.setPiece(new Pawn(Utils.WHITE, 50));
        builder.setPiece(new Pawn(Utils.WHITE, 51));
        builder.setPiece(new Pawn(Utils.WHITE, 52));
        builder.setPiece(new Pawn(Utils.WHITE, 53));
        builder.setPiece(new Pawn(Utils.WHITE, 54));
        builder.setPiece(new Pawn(Utils.WHITE, 55));
        builder.setPiece(new Rook(Utils.WHITE, 56));
        builder.setPiece(new Knight(Utils.WHITE, 57));
        builder.setPiece(new Bishop(Utils.WHITE, 58));
        builder.setPiece(new Queen(Utils.WHITE, 59));
        builder.setPiece(new King(Utils.WHITE, 60));
        builder.setPiece(new Bishop(Utils.WHITE, 61));
        builder.setPiece(new Knight(Utils.WHITE, 62));
        builder.setPiece(new Rook(Utils.WHITE, 63));

        // Set piece color to move
        builder.setMoveMaker(Utils.WHITE);

        // Build the entire board
        return builder.build();
    }
}
