package core.board;

import com.google.common.annotations.VisibleForTesting;
import core.movements.MoveFactory;
import core.pieces.*;
import core.utils.Utils;
import core.movements.Move;
import core.player.BlackPlayer;
import core.player.Player;
import core.player.WhitePlayer;
import core.pieces.piece.Piece;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Questa classe serve per rappresentare la scacchiera in formato virtuale.
 */
@Getter
public final class VirtualBoard {
    private final Map<Integer, Piece> configuration;
    private final Player currentPlayer;
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;
    private final Pawn enPassantPawn;
    private final Move transitionMove;

    private static final VirtualBoard DEFAULT_BOARD = initDefaultBoard(); // initDefaultBoardTest(); // initDefaultBoard();
    
    /**
     * All'interno del costruttore vengono creati i giocatori, calcolate le mosse usabili alla prima mossa,
     * scegliere il primo giocatore che dovrà muovere e altri parametri del {@link VirtualBoard}
     * @param boardConfigurator che serve per mappare le pedine, transizioni,... sulla scacchiera
     */
    public VirtualBoard(final BoardConfigurator boardConfigurator) {
        this.configuration = Collections.unmodifiableMap(boardConfigurator.getConfiguration());
        this.whitePieces = calculateActivePiecesByUtils(boardConfigurator, Utils.WHITE);
        this.blackPieces = calculateActivePiecesByUtils(boardConfigurator, Utils.BLACK);
        this.enPassantPawn = boardConfigurator.getEnPassant();

        final Collection<Move> whiteUsableMoves = this.calculateUsableMoves(this.whitePieces);
        final Collection<Move> blackUsableMoves = this.calculateUsableMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, whiteUsableMoves, blackUsableMoves);
        this.blackPlayer = new BlackPlayer(this, blackUsableMoves, whiteUsableMoves);
        this.currentPlayer = boardConfigurator.getNextMoveMaker().selectPlayerByUtils(this.whitePlayer, this.blackPlayer);
        this.transitionMove = boardConfigurator.getMoveTransition() != null ? boardConfigurator.getMoveTransition() : MoveFactory.getNullMove();
    }

    /**
     * Questo metodo serve per ottenere la pedina situata a una coordinata
     * @param id coordinata da utilizzare come target
     * @return pedina situata alla coordinata
     */
    public Piece getPiece(final int id) {
        return this.configuration.get(id);
    }

    /**
     * Questo metodo si occupa di concatenare tutte le mosse eseguibili dal player bianco e nero.
     * @return collection di mosse eseguibili
     */
    public Collection<Move> getAllUsableMoves() {
        return Stream.concat(
                this.whitePlayer.getUsableMoves().stream(),
                this.blackPlayer.getUsableMoves().stream()
        ).collect(Collectors.toList());
    }

    /**
     * Questo metodo serve per commutare le due collection di pedine in una unica
     * @return collection di pedine bianche e nere
     */
    public Collection<Piece> getAllPieces() {
        return Stream.concat(
                this.blackPieces.stream(),
                this.whitePieces.stream()
        ).collect(Collectors.toList());
    }

    /**
     * @return la scacchiera virtuale con le pedine posizionate alla GUI ({@link gui.Window})
     */
    public static VirtualBoard getDefaultBoard() {
        return DEFAULT_BOARD;
    }

    /**
     * All'interno di questo metodo vengono definiti i tipi, colori e coordinate delle pedine
     * oltre al colore che deve muovere per primo
     * @return la scacchiera virtuale con le pedine posizionate
     */
    private static VirtualBoard initDefaultBoard() {
        final BoardConfigurator configurator = new BoardConfigurator();

        // Black pieces
        for(int i = 8; i <= 15; i++)
            configurator.setPiece(new Pawn(i, Utils.BLACK));

        configurator.setPiece(new Rook(0, Utils.BLACK));
        configurator.setPiece(new Knight(1, Utils.BLACK));
        configurator.setPiece(new Bishop(2, Utils.BLACK));
        configurator.setPiece(new Queen(3, Utils.BLACK));
        configurator.setPiece(new King(4, Utils.BLACK, true, true));
        configurator.setPiece(new Bishop(5, Utils.BLACK));
        configurator.setPiece(new Knight(6, Utils.BLACK));
        configurator.setPiece(new Rook(7, Utils.BLACK));

        // White pieces
        for(int k = 48; k <= 55; k++)
            configurator.setPiece(new Pawn(k, Utils.WHITE));

        configurator.setPiece(new Rook(56, Utils.WHITE));
        configurator.setPiece(new Knight(57, Utils.WHITE));
        configurator.setPiece(new Bishop(58, Utils.WHITE));
        configurator.setPiece(new Queen(59, Utils.WHITE));
        configurator.setPiece(new King(60, Utils.WHITE, true, true));
        configurator.setPiece(new Bishop(61, Utils.WHITE));
        configurator.setPiece(new Knight(62, Utils.WHITE));
        configurator.setPiece(new Rook(63, Utils.WHITE));

        // Imposta il giocatore che inizia a muovere la prima mossa
        configurator.setMoveMaker(Utils.WHITE);

        // "Compila" la scacchiera virtuale
        return configurator.build();
    }

    @VisibleForTesting
    private static VirtualBoard initDefaultBoardTest() {
        final BoardConfigurator configurator = new BoardConfigurator();



        // Pedina da mattere sotto scacco
        configurator.setPiece(new King(63, Utils.BLACK, true, true));


        // Attaccanti
        configurator.setPiece(new King(54, Utils.WHITE, true, true));





        // Imposta il giocatore che inizia a muovere la prima mossa
        configurator.setMoveMaker(Utils.WHITE);

        // "Compila" la scacchiera virtuale
        return configurator.build();
    }

    /**
     * Questo metodo si occupa di raccogliere tutti le possibili mosse della lista di pedina passata
     * @param pieces pedine per la quale si vuole conoscere le mosse
     * @return collection di movimenti
     */
    private Collection<Move> calculateUsableMoves(final Collection<Piece> pieces) {
        return pieces.stream().flatMap(piece -> piece.calculateMoves(this).stream())
                .collect(Collectors.toList());
    }

    /**
     * Questo metodo serve per capire quali pedine sono attive nel gioco in base al loro colore
     * per poterle dividere nei due attributi della classe {@link VirtualBoard}
     * @param boardConfigurator serve per ottenere le pedine
     * @param utils serve a filtrare il colore delle pedine
     * @return collection di pedine attive
     */
    private static Collection<Piece> calculateActivePiecesByUtils(final BoardConfigurator boardConfigurator, final Utils utils) {
        return boardConfigurator.getConfiguration().values().stream()
                .filter(piece -> piece.getPieceUtils() == utils)
                .collect(Collectors.toList());
    }
}
