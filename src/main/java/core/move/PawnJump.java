package core.move;

import core.board.BoardConfigurator;
import core.board.VirtualBoardUtils;
import core.board.VirtualBoard;
import core.pieces.Pawn;
import core.pieces.piece.Piece;

/**
 * Questa classe serve a rappresentare il salto di un pedone
 * Un pedone salta quando procede in avanti di due celle in una volta sola
 */
public class PawnJump extends Move {

    /**
     * @param board scacchiera virtuale di riferimento
     * @param pieceToMove pedina che si vuole muovere
     * @param destinationCoordinate coordinata di destinazione
     */
    public PawnJump(final VirtualBoard board, final Piece pieceToMove, final int destinationCoordinate) {
        super(board, pieceToMove, destinationCoordinate);
    }

    /**
     * Questo metodo serve per verificare se un oggetto è uguale a un oggetto dell'istanza {@link PawnJump}
     * @param other oggetto da confrontare
     * @return valore booleano uguale a "TRUE" se l'oggetto è un'istanza di {@link PawnJump} o se è lo stesso indirizzo di memoria.
     */
    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof PawnJump && super.equals(other);
    }

    /**
     * Questo metodo è quello che si occupa di effettuare l'aggiornamento delle pedine e d'impostare il pedone in movimento
     * @return Una scacchiera virtuale con le posizioni aggiornate.
     * Aggiornate mediante la classe {@link BoardConfigurator}
     */
    @Override
    public VirtualBoard run() {
        final BoardConfigurator builder = new BoardConfigurator();

        this.board.getCurrentPlayer().getActivePieces().stream().filter(piece -> !this.pieceToMove.equals(piece)).forEach(builder::setPiece);
        this.board.getCurrentPlayer().getOpponent().getActivePieces().forEach(builder::setPiece);

        final Pawn movedPawn = (Pawn) this.pieceToMove.movePiece(this);

        builder.setPiece(movedPawn);
        builder.setEnPassant(movedPawn);
        builder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getUtils());
        builder.setMoveTransition(this);

        return builder.build();
    }

    /**
     * @return una stringa contenente la destinazione della mossa
     */
    @Override
    public String toString() {
        return VirtualBoardUtils.INSTANCE.getPositionAtCoordinate(this.destinationCoordinate);
    }
}
