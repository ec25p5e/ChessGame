package core.movements;

import core.board.VirtualBoard;
import core.board.VirtualBoardUtils;
import core.pieces.piece.Piece;

/**
 * Questa classe crea un mossa di movimento. Ma un movimento di attacco, ovvero,
 * la pedina si vuole muovere su una coordinata dove è già presente una pedina.
 * Questa classe è stata creata per avere un altro oggetto uguale a {@link SimpleAttackMove}
 * solo per avere una distinzione di quando una pedina attacca rispetto a una generica
 */
public class PawnAttackMove extends AttackMove {

    /**
     * @param board scacchiera virtuale di riferimento
     * @param pieceToMove pedina che si desidera muovere
     * @param destinationCoordinate coordinata di destinazione della pedina
     * @param pieceToAttack pedina da rimuovere(attaccare) dalla scacchiera
     */
    public PawnAttackMove(final VirtualBoard board, final Piece pieceToMove, final int destinationCoordinate, final Piece pieceToAttack) {
        super(board, pieceToMove, destinationCoordinate, pieceToAttack);
    }

    /**
     * Questo metodo serve per verificare se un oggetto è uguale a un oggetto dell'istanza {@link PawnAttackMove}
     * @param other oggetto da confrontare
     * @return valore booleano uguale a "TRUE" se l'oggetto è un'istanza di {@link PawnAttackMove} o se è lo stesso indirizzo di memoria.
     */
    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof PawnAttackMove && super.equals(other);
    }

    /**
     * @return una stringa contenente l'id della pedina concatenata a una "X" e la coordinata
     */
    @Override
    public String toString() {
        return VirtualBoardUtils.INSTANCE.getPositionAtCoordinate(this.pieceToMove.getPiecePosition()).charAt(0) + "x" + VirtualBoardUtils.INSTANCE.getPositionAtCoordinate(this.destinationCoordinate);
    }
}
