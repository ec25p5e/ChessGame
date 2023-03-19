package core.board;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import core.move.MoveFactory;
import core.pieces.*;
import core.pieces.piece.PieceAssistant;
import core.pieces.piece.PieceDeserializer;
import core.utils.Utils;
import core.move.Move;
import core.player.BlackPlayer;
import core.player.Player;
import core.player.WhitePlayer;
import core.pieces.piece.Piece;
import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static core.utils.Utils.BLACK;
import static core.utils.Utils.WHITE;
import static util.Constants.*;

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

    private static final VirtualBoard DEFAULT_BOARD = initDefaultBoard();
    
    /**
     * All'interno del costruttore vengono creati i giocatori, calcolate le mosse usabili alla prima mossa,
     * scegliere il primo giocatore che dovrà muovere e altri parametri del {@link VirtualBoard}
     * @param boardConfigurator che serve per mappare le pedine, transizioni,... sulla scacchiera
     */
    public VirtualBoard(final BoardConfigurator boardConfigurator) {
        this.configuration = Collections.unmodifiableMap(boardConfigurator.getConfiguration());
        this.whitePieces = calculateActivePiecesByUtils(boardConfigurator, WHITE);
        this.blackPieces = calculateActivePiecesByUtils(boardConfigurator, Utils.BLACK);
        this.enPassantPawn = boardConfigurator.getEnPassant();

        final Collection<Move> whiteUsableMoves = this.calculateUsableMoves(this.whitePieces);
        final Collection<Move> blackUsableMoves = this.calculateUsableMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, whiteUsableMoves, blackUsableMoves);
        this.blackPlayer = new BlackPlayer(this, whiteUsableMoves, blackUsableMoves);
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
    public static VirtualBoard initDefaultBoard() {
        final BoardConfigurator configurator = new BoardConfigurator();
        final Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Piece.class, new PieceDeserializer())
                .enableComplexMapKeySerialization()
                .create();
        String inFile = "";

        try {
            inFile = new String(Files.readAllBytes(Paths.get(SERIALIZATION_PATH + BASE_GAME_FILE)));
        } catch(IOException e) {
            e.printStackTrace();
        }

        if(!inFile.equals("")) {
            final PieceAssistant pieceAssistant = new PieceAssistant();
            final Piece[] objs = gson.fromJson(inFile, Piece[].class);

            for(Piece obj : objs) {
                try {
                    configurator.setPiece(pieceAssistant.init(
                            Class.forName(obj.getClass().getCanonicalName()),
                            obj.getPieceCoordinate(),
                            (obj.getPieceUtils() == WHITE ? WHITE : BLACK),
                            obj.isFirstMove(),
                            obj.isCastledByQueen(),
                            obj.isCastledByKing()
                    ));
                } catch(ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            configurator.setMoveMaker(WHITE);
        }

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
