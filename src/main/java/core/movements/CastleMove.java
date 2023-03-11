package core.movements;

import core.board.BoardConfigurator;
import core.board.VirtualBoard;
import core.pieces.Rook;
import core.pieces.piece.Piece;
import lombok.Getter;

/**
 * Questa classe serve per creare delle mosse di arrocco.
 * Viene estesa dalle classi {@link KingSideCastleMove} e {@link QueenSideCastleMove} per capire se l'inversione ha avuto luogo
 * verso il re o verso la regina
 */
@Getter
public abstract class CastleMove extends Move {
    private final Rook castleRook;
    private int castleRookStart;
    private int castleRookDestination;

    public CastleMove(final VirtualBoard board, final Piece pieceToMove, final int destinationCoordinate, final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
        super(board, pieceToMove, destinationCoordinate);
        this.castleRook = castleRook;
        this.castleRookStart = castleRookStart;
        this.castleRookDestination = castleRookDestination;
    }

    /**
     * @return "TRUE" se si sta facendo una mossa di castling
     */
    @Override
    public boolean isCastlingMove() {
        return true;
    }

    /**
     * Questo metodo serve per confrontare un oggetto a un oggetto della classe {@link CastleMove}
     * @param other oggetto da confrontare
     * @return valore booleano di valore "TRUE" se l'oggetto è un'istanza di {@link CastleMove}
     * o se sono uguali tramite i parametri e l'indirizzo di memoria
     */
    @Override
    public boolean equals(final Object other) {
        if(this == other)
            return true;

        if(!(other instanceof final CastleMove otherCastleMove))
            return false;

        return super.equals(otherCastleMove) && this.castleRook.equals(otherCastleMove.getCastleRook());
    }

    /**
     * Questo metodo è quello che si occupa di effettuare l'aggiornamento delle pedine
     * @return Una scacchiera virtuale con le posizioni aggiornate.
     * Aggiornate mediante la classe {@link VirtualBoard}
     */
    @Override
    public VirtualBoard run() {
        final BoardConfigurator configurator = new BoardConfigurator();

        for(final Piece piece : this.board.getAllPieces()) {
            if(!this.pieceToMove.equals(piece) && !this.castleRook.equals(piece))
                configurator.setPiece(piece);
        }

        configurator.setPiece(this.pieceToMove.movePiece(this));
        // non so come mai ma chiamare il metodo "doMove" non funziona, quindi creo una nuova torre
        configurator.setPiece(new Rook(this.castleRookDestination, this.castleRook.getPieceUtils(), false));
        configurator.setMoveMaker(this.board.getCurrentPlayer().getOpponentPlayer().getPlayerColor());
        configurator.setMoveTransition(this);

        return configurator.build();
    }
}
