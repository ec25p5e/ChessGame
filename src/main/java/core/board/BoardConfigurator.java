package core.board;

import core.pieces.Pawn;
import core.utils.Utils;
import core.move.Move;
import core.pieces.piece.Piece;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Questa classe serve per la configurare le pedine e altri parametri sulla scacchiera "virtuale"
 */
@Getter
public class BoardConfigurator {
    private Map<Integer, Piece> configuration;
    private Utils nextMoveMaker;
    private Move moveTransition;
    private Pawn enPassant;

    public BoardConfigurator() {
        this.configuration = new HashMap<>(32, 1.0f);
    }

    /**
     * Questo metodo serve ad aggiungere una pedina al hashmap
     * @param piece pedina da aggiungere
     * @return {@link BoardConfigurator} con le pedine/valori aggiornati a dopo l'inserimento
     */
    public BoardConfigurator setPiece(final Piece piece) {
        this.configuration.put(piece.getPiecePosition(), piece);
        return this;
    }

    /**
     * Questo metodo serve per impostare il colore del giocatore che farà la prossima mossa
     * @param nextMoveMaker colore del giocatore. Dopo bianco è nero e viceversa
     * @return {@link BoardConfigurator} con le pedine/valori aggiornati a dopo l'inserimento
     */
    public BoardConfigurator setMoveMaker(final Utils nextMoveMaker) {
        this.nextMoveMaker = nextMoveMaker;
        return this;
    }

    /**
     * Questo metodo imposta l'oggetto di movimento che sta venendo eseguito
     * @param moveTransition oggetto del movimento corrente
     * @return {@link BoardConfigurator} con le pedine/valori aggiornati a dopo l'inserimento
     */
    public BoardConfigurator setMoveTransition(final Move moveTransition) {
        this.moveTransition = moveTransition;
        return this;
    }

    /**
     * Questo metodo serve a impostare il pedone che viene catturato con la mossa particolare "enpassant"
     * @param enPassant pedina
     * @return {@link BoardConfigurator} con le pedine/valori aggiornati a dopo l'inserimento
     */
    public BoardConfigurator setEnPassant(final Pawn enPassant) {
        this.enPassant = enPassant;
        return this;
    }

    /**
     * @return una board "virtuale" con gli elementi posizionati.
     * Le pedine alle coordinate, il prossimo colore a muovere, la mossa in corso e se c'� un pedone da promuovere
     */
    public VirtualBoard build() {
        return new VirtualBoard(this);
    }
}