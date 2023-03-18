package core.movements;

import core.board.BoardConfigurator;
import core.board.VirtualBoard;
import core.board.VirtualBoardUtils;
import core.pieces.piece.Piece;
import lombok.Getter;

/**
 * Questa classe rappresenta le mosse delle pedine e una classe la deve ereditare per poterla utilizzare
 */
@Getter
public abstract class Move {
    protected final VirtualBoard board;
    protected final int destinationCoordinate;
    protected final Piece pieceToMove;

    /**
     * @param board scacchiera virtuale di riferimento
     * @param pieceToMove pedina da muovere
     * @param destinationCoordinate coordinata di destinazione
     */
    public Move(final VirtualBoard board, final Piece pieceToMove, final int destinationCoordinate) {
        this.board = board;
        this.pieceToMove = pieceToMove;
        this.destinationCoordinate = destinationCoordinate;
    }

    /**
     * Questo costruttore viene utilizzato quando non si sa quale sia la pedina da muovere.
     * Più semplicemente viene utilizzato dal costruttore della classe {@link NullMove}
     * @param board scacchiera virtuale di riferimento
     * @param destinationCoordinate coordinata di destinazione della pedina
     */
    public Move(final VirtualBoard board, final int destinationCoordinate) {
        this.board = board;
        this.destinationCoordinate = destinationCoordinate;
        this.pieceToMove = null;
    }

    /**
     * Questo metodo serve a capire se un oggetto è lo stesso oggetto creato dalla classe {@link Move}
     * @param other oggetto da confrontare
     * @return "TRUE" se l'oggetto è uguale per indirizzo di memoria o se è un istanza o i suoi attributi solo uguali
     */
    @Override
    public boolean equals(final Object other) {
        if (this == other)
            return true;

        if (!(other instanceof final Move otherMove))
            return false;

        return getCurrentCoordinate() == otherMove.getCurrentCoordinate() && getDestinationCoordinate() == otherMove.getDestinationCoordinate() && getPieceToMove().equals(otherMove.getPieceToMove());
    }

    /**
     * Questo metodo è quello che si occupa di effettuare l'aggiornamento delle pedine
     * @return Una scacchiera virtuale con le posizioni aggiornate.
     * Aggiornate mediante la classe {@link VirtualBoard}
     */
    public VirtualBoard run() {
        final BoardConfigurator builder = new BoardConfigurator();

        this.board.getCurrentPlayer().getActivePieces().stream().filter(piece -> !this.pieceToMove.equals(piece)).forEach(builder::setPiece);
        this.board.getCurrentPlayer().getOpponent().getActivePieces().forEach(builder::setPiece);

        builder.setPiece(this.pieceToMove.movePiece(this));
        builder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getUtils());
        builder.setMoveTransition(this);

        return builder.build();
    }

    public VirtualBoard undo() {
        final BoardConfigurator builder = new BoardConfigurator();
        this.board.getAllPieces().forEach(builder::setPiece);
        builder.setMoveMaker(this.board.getCurrentPlayer().getUtils());

        return builder.build();
    }

    /**
     * @return La pedina che si sta attaccando.
     * All'interno di questa classe il valore è nullo ma nella
     * classe che si occupa di effettuare un movimento di attacco ritornerà una pedina
     */
    public Piece getPieceAttacked() {
        return null;
    }

    /**
     * @return "TRUE" se il movimento è un movimento di castling
     */
    public boolean isCastlingMove() {
        return false;
    }

    /**
     * Questo metodo serve per aggiungere un controllo aggiuntivo di sicurezza
     * sulle mosse e l'identificazione delle pedine
     * @return stringa che indica le coordinate in stringa della mossa
     */
    public String disambiguationFile() {
        for(final Move move : this.board.getCurrentPlayer().getUsableMoves()) {
            if(move.getDestinationCoordinate() == this.destinationCoordinate && !this.equals(move)
                    && this.pieceToMove.getPieceType().equals(move.getPieceToMove().getPieceType())) {
                return VirtualBoardUtils.INSTANCE.getPositionAtCoordinate(this.pieceToMove.getPiecePosition()).substring(0, 1);
            }
        }

        return "";
    }

    /**
     * @return la coordinata di partenza della pedina da muovere
     */
    public int getCurrentCoordinate() {
        return this.pieceToMove.getPiecePosition();
    }

    /**
     * @return Un valore booleano "TRUE" se si sta attaccando un altra pedina.
     * All'interno di questa classe il valore è "FALSE" ma nella
     * classe che si occupa di effettuare un movimento di attacco ritornerà "TRUE"
     */
    public boolean isAttack() {
        return false;
    }
}
