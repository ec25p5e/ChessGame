package core.player;

import core.board.VirtualBoard;
import core.movements.Move;
import core.movements.MoveStatus;
import core.movements.MoveTransition;
import core.pieces.King;
import core.utils.Utils;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static core.pieces.piece.PieceType.KING;

/**
 * Questa classe serve a rappresentare il giocatore generico che deve essere estesa dal {@link BlackPlayer} e {@link WhitePlayer}
 *
 */
@Getter
public abstract class Player implements IPlayer {
    protected final VirtualBoard board;
    protected final King playerKing;
    protected final Collection<Move> usableMoves;
    protected final boolean isInCheck;

    public Player(final VirtualBoard board, final Collection<Move> playerUsableMoves, final Collection<Move> opponentPlayerMoves) {
        this.board = board;
        this.playerKing = this.detectKing();
        this.isInCheck = !calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentPlayerMoves).isEmpty();
        this.usableMoves = Collections.unmodifiableCollection(playerUsableMoves);
    }

    public boolean isInCheckMate() {
        return this.isInCheck && !this.findEscapeMoves();
    }

    public boolean isInStaleMate() {
        return !this.isInCheck && !this.findEscapeMoves();
    }

    /**
     * Questo metodo si può definire come il punto di entrata nell'esecuzione dei movimenti;
     * in quanto viene chiamato nella GUI {@link gui.Window}
     * @param move mossa da effettuare
     * @return Una mossa di transizione che può essere "terminata", "sotto scacco". Altrimenti è "invalida"
     */
    public MoveTransition doMove(final Move move) {
        // Se la mossa non è tra quelle eseguibili termina e ritorna un animazione di movimento non valida
        if(!this.usableMoves.contains(move))
            return new MoveTransition(this.board, this.board, move, MoveStatus.ILLEGAL_MOVES);

        // Esegui il movimento
        final VirtualBoard transitionMove = move.run();

        // Se il giocatore è in scacco ritorna una mossa di transizione con lo stato di "in scacco",
        // quindi non è possibile eseguire altre mosse.
        // Altrimenti crea una mossa di transizione con lo stato di terminata, quindi la mossa è stata eseguita
        return transitionMove.getCurrentPlayer().getOpponentPlayer().isInCheck() ?
                new MoveTransition(this.board, this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK) :
                new MoveTransition(this.board, transitionMove, move, MoveStatus.DONE);
    }

    /**
     * Questo metodo serve per trovare le mosse possibili che portano ad arrivare alla coordinata passata
     * @param tileId coordinata di riferimento ==> coordinata di destinazione
     * @param moves lista di movimenti tra la quale cercare
     * @return movimenti filtrati in funzione di "tileId"
     */
    public static Collection<Move> calculateAttacksOnTile(final int tileId, final Collection<Move> moves) {
        return moves.stream()
                .filter(move -> move.getDestinationCoordinate() == tileId)
                .collect(collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    /**
     * Questo metodo serve a trovare il re tra le pedine attive
     * @return il re per poterlo impostare
     */
    private King detectKing() {
        return (King) getActivePieces().stream()
                .filter(piece -> piece.getPieceType() == KING)
                .findAny()
                .orElseThrow(RuntimeException::new);
    }

    /**
     * Questo metodo esegue tutte le mosse possibili e se ce ne sono ancora che andranno a buon fine.
     * @return "TRUE" se ci sono ancora mosse eseguibili con esito positivo. Altrimenti "FALSE"
     */
    private boolean findEscapeMoves() {
        return this.usableMoves.stream()
                .anyMatch(move -> doMove(move)
                        .moveStatus().isDone());
    }
}
