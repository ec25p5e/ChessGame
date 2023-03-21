package core.move;

import core.board.VirtualBoard;
import core.pieces.piece.Piece;

/**
 * Questa classe rappresenta le mosse di attacco delle pedine.
 * Ovvero, una pedina quando vuole muoversi crea un'istanza di una classe che estenderà la classe {@link AttackMove}
 * cosi da poter registrare la pedina da "attaccare" (rimuovere).
 */
public abstract class AttackMove extends Move {
    private final Piece pieceToAttack;

    /**
     * @param board scacchiera virtuale di riferimento
     * @param pieceToMove pedina che si vuole muovere
     * @param destinationCoordinate coordinata di destinazione
     * @param pieceToAttack pedina da rimuovere
     */
    public AttackMove(final VirtualBoard board, final Piece pieceToMove, final int destinationCoordinate, final Piece pieceToAttack) {
        super(board, pieceToMove, destinationCoordinate);
        this.pieceToAttack = pieceToAttack;
    }

    /**
     * Questo metodo serve per confrontare un oggetto a un oggetto della classe {@link AttackMove}
     * @param other oggetto da confrontare
     * @return valore booleano di valore "TRUE" se l'oggetto è un'istanza di {@link AttackMove}
     * o se sono uguali tramite i parametri e l'indirizzo di memoria
     */
    @Override
    public boolean equals(final Object other) {
        if(this == other)
            return true;

        if(!(other instanceof final AttackMove otherAttackMove))
            return false;

        return super.equals(otherAttackMove) && this.getPieceAttacked().equals(otherAttackMove.getPieceAttacked());
    }

    /**
     * @return il pezzo che si sta attaccando (pezzo da rimuovere dalla scacchiera)
     */
    @Override
    public Piece getPieceAttacked() {
        return this.pieceToAttack;
    }

    /**
     * @return se attualmente si è in procinto di attaccare una qualsiasi pedina
     */
    @Override
    public boolean isAttack() {
        return true;
    }
}
