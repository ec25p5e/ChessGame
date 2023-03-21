package core.move;

import core.board.BoardConfigurator;
import core.board.VirtualBoardUtils;
import core.board.VirtualBoard;
import core.pieces.Pawn;
import core.pieces.piece.Piece;

/**
 * Questa classe serve a rappresentare la promozione del pedone
 * Quindi quando arriva sull'ultima riga può essere sostituito con regina, torre, alfiere o cavallo
 */
public class PawnPromotion extends PawnMove {
    private final Move moveDecorator;
    private final Pawn promotedPawn;
    private final Piece promotionPiece;

    /**
     * @param moveDecorator il movimento eseguito
     * @param promotionPiece il pezzo che da promuovere
     */
    public PawnPromotion(final Move moveDecorator, final Piece promotionPiece) {
        super(moveDecorator.getBoard(), moveDecorator.getPieceToMove(), moveDecorator.getDestinationCoordinate());
        this.moveDecorator = moveDecorator;
        this.promotedPawn = (Pawn) moveDecorator.getPieceToMove();
        this.promotionPiece = promotionPiece;
    }

    /**
     * Questo metodo serve per verificare se un oggetto è uguale a un oggetto dell'istanza {@link PawnPromotion}
     * @param other oggetto da confrontare
     * @return valore booleano uguale a "TRUE" se l'oggetto è un'istanza di {@link PawnPromotion} o se è lo stesso indirizzo di memoria.
     */
    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof PawnPromotion && super.equals(other);
    }

    /**
     * Questo metodo è quello che si occupa di effettuare l'aggiornamento delle pedine
     * @return Una scacchiera virtuale con le posizioni aggiornate.
     * Aggiornate mediante la classe {@link VirtualBoard}
     */
    @Override
    public VirtualBoard run() {
        final VirtualBoard movedPawnBoard = this.moveDecorator.run();
        final BoardConfigurator builder = new BoardConfigurator();

        movedPawnBoard.getCurrentPlayer().getActivePieces().stream().filter(piece -> !this.promotedPawn.equals(piece)).forEach(builder::setPiece);
        movedPawnBoard.getCurrentPlayer().getOpponent().getActivePieces().forEach(builder::setPiece);

        builder.setPiece(this.promotionPiece.movePiece(this));
        builder.setMoveMaker(movedPawnBoard.getCurrentPlayer().getUtils());
        builder.setMoveTransition(this);

        return builder.build();
    }

    /**
     * @return la pedina sotto attacco
     */
    @Override
    public Piece getPieceAttacked() {
        return this.moveDecorator.getPieceAttacked();
    }

    /**
     * @return "TRUE" se è sotto attacco, altrimenti false
     */
    @Override
    public boolean isAttack() {
        return this.moveDecorator.isAttack();
    }

    /**
     * @return stesura della mossa contenente la coordinata di sorgente e quella di destinazione = il tipo del pedone che si usa per promuovere
     */
    @Override
    public String toString() {
        return VirtualBoardUtils.INSTANCE.getPositionAtCoordinate(this.pieceToMove.getPiecePosition()) + "-" +
                VirtualBoardUtils.INSTANCE.getPositionAtCoordinate(this.destinationCoordinate) + "=" + this.promotionPiece.getPieceType();
    }
}
