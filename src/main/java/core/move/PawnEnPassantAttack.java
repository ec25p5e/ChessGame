package core.move;

import core.board.BoardConfigurator;
import core.board.VirtualBoard;
import core.pieces.piece.Piece;

/**
 * Questa classe serve a rappresentare l'azione di attacco verso un pedone quando si è durante l'enpassant
 */
public class PawnEnPassantAttack extends PawnAttackMove {

    /**
     * @param board scacchiera "virtuale" di riferimento
     * @param pieceToMove pedina che si sta muovendo
     * @param destinationCoordinate coordinata di destinazione dove dovrà atterrare
     * @param pieceToAttack pedone da attaccare, da mangiare
     */
    public PawnEnPassantAttack(final VirtualBoard board, final Piece pieceToMove, final int destinationCoordinate, final Piece pieceToAttack) {
        super(board, pieceToMove, destinationCoordinate, pieceToAttack);
    }

    /**
     * Questo metodo serve per verificare se un oggetto è uguale a un oggetto dell'istanza {@link PawnEnPassantAttack}
     * @param other oggetto da confrontare
     * @return valore booleano uguale a "TRUE" se l'oggetto è un'istanza di {@link PawnEnPassantAttack} o se è lo stesso indirizzo di memoria.
     */
    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof PawnEnPassantAttack && super.equals(other);
    }

    /**
     * Questo metodo è quello che si occupa di effettuare l'aggiornamento delle pedine
     * @return Una scacchiera virtuale con le posizioni aggiornate.
     * Aggiornate mediante la classe {@link VirtualBoard}
     */
    @Override
    public VirtualBoard run() {
        final BoardConfigurator configurator = new BoardConfigurator();

        this.board.getCurrentPlayer().getActivePieces().stream().filter(piece -> !this.pieceToMove.equals(piece)).forEach(configurator::setPiece);
        this.board.getCurrentPlayer().getOpponent().getActivePieces().stream().filter(piece -> !piece.equals(this.getPieceAttacked())).forEach(configurator::setPiece);

        configurator.setPiece(this.pieceToMove.movePiece(this));
        configurator.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getUtils());
        configurator.setMoveTransition(this);

        return configurator.build();
    }
}
