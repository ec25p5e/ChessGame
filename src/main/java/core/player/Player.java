package core.player;

import core.board.VirtualBoard;
import core.move.Move;
import core.move.MoveStatus;
import core.move.MoveTransition;
import core.pieces.King;
import lombok.Getter;
import util.Configuration;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static core.pieces.piece.PieceType.KING;

/**
 * Questa classe serve a rappresentare il giocatore generico che deve essere
 * estesa dal {@link BlackPlayer} e {@link WhitePlayer}
 */
@Getter
public abstract class Player implements IPlayer {
    protected final VirtualBoard board;
    protected final King playerKing;
    protected final Collection<Move> usableMoves;
    protected final boolean isInCheck;

    /**
     *
     * @param board
     * @param playerUsable
     * @param opponentUsable
     */
    public Player(final VirtualBoard board, final Collection<Move> playerUsable, final Collection<Move> opponentUsable, boolean isDrawingMode) {
        this.board = board;

        if(!isDrawingMode) {
            this.playerKing = this.detectKing();
            this.isInCheck = !calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentUsable).isEmpty();
            playerUsable.addAll(calculateKingCastles(playerUsable, opponentUsable));
            this.usableMoves = Collections.unmodifiableCollection(playerUsable);
        } else {
            this.playerKing = null;
            this.isInCheck = false;
            this.usableMoves = null;
        }
    }

    /**
     * Questo metodo serve a indicare se il giocatore è sotto scacco matto
     * @return valore booleano "TRUE" se in scacco matto
     */
    public boolean isInCheckMate() {
        return this.isInCheck && !this.findEscapeMoves();
    }

    /**
     * Questo metodo serve a indicare se il giocatore è in stallo
     * @return valore booleano "TRUE" se in stallo
     */
    public boolean isInStaleMate() {
        return !this.isInCheck && !this.findEscapeMoves();
    }

    /**
     * Questo metodo serve a indicare se il giocatore è stato messo in arrocco
     * Il RE implementa questa funzionalità
     * @return valore booleano "TRUE" se in arrocco
     */
    public boolean isCastled() {
        return this.playerKing.isCastled();
    }

    /**
     * Questo metodo indica se il RE è in grado di gestirsi o se è sotto scacco
     * Metodo specifico per il RE
     * @return valore booleano
     */
    public boolean isKingSideCastleCapable() {
        return this.playerKing.isCastledByKing();
    }

    /**
     * Questo metodo indica se la regina è in grado di gestirsi o se è sotto scacco
     * Metodo specifico per la regina
     * @return valore booleano
     */
    public boolean isQueenSideCastleCapable() {
        return this.playerKing.isCastledByQueen();
    }


    /**
     * Questo metodo si può definire come il punto di entrata nell'esecuzione dei movimenti;
     * in quanto viene chiamato nella GUI {@link gui.Window}
     * @param move mossa da effettuare
     * @return Una mossa di transizione che può essere "terminata", "sotto scacco". Altrimenti è "invalida"
     */
    public MoveTransition doMove(final Move move) {
        if (!this.usableMoves.contains(move)) {
            System.out.println(move.getDestinationCoordinate());
            return new MoveTransition(this.board, this.board, move, MoveStatus.ILLEGAL_MOVE);
        }

        final VirtualBoard transitionedBoard = move.run();

        return transitionedBoard.getCurrentPlayer().getOpponent().isInCheck() ?
                new MoveTransition(this.board, this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK) :
                new MoveTransition(this.board, transitionedBoard, move, MoveStatus.DONE);
    }

    /**
     * Questo metodo serve ad annullare una mossa. Infatti ritorna lo stato precedente alla mossa
     * @param move mossa di riferimento per capire la situazione precedente
     * @return transizione di mossa con la situazione precedente
     */
    public MoveTransition unMakeMove(final Move move) {
        return new MoveTransition(this.board, move.undo(), move, MoveStatus.DONE);
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
     * Questo metodo serve a capire se ci sono ancora apportunità di scacco
     * @return valore booleano
     */
    protected boolean hasCastleOpportunities() {
        return !this.isInCheck && !this.playerKing.isCastled() &&
                (this.playerKing.isCastledByKing() || this.playerKing.isCastledByQueen());
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
