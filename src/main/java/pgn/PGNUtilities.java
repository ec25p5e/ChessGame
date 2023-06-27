package pgn;

import com.google.common.collect.ImmutableList;
import core.board.VirtualBoard;
import core.board.VirtualBoardUtils;
import core.move.Move;
import core.move.MoveFactory;
import pgn.expcetions.ParsePGNException;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PGNUtilities {

    //
    private static final Pattern PGN_PATTERN = Pattern.compile("\\[(\\w+)\\s+\"(.*?)\"\\]$");
    private static final Pattern KING_SIDE_CASTLE = Pattern.compile("O-O#?\\+?");
    private static final Pattern QUEEN_SIDE_CASTLE = Pattern.compile("O-O-O#?\\+?");
    private static final Pattern PLAIN_PAWN_MOVE = Pattern.compile("^([a-h][0-8])(\\+)?(#)?$");
    private static final Pattern PAWN_ATTACK_MOVE = Pattern.compile("(^[a-h])(x)([a-h][0-8])(\\+)?(#)?$");
    private static final Pattern PLAIN_MAJOR_MOVE = Pattern.compile("^(B|N|R|Q|K)([a-h]|[1-8])?([a-h][0-8])(\\+)?(#)?$");
    private static final Pattern MAJOR_ATTACK_MOVE = Pattern.compile("^(B|N|R|Q|K)([a-h]|[1-8])?(x)([a-h][0-8])(\\+)?(#)?$");
    private static final Pattern PLAIN_PAWN_PROMOTION_MOVE = Pattern.compile("(.*?)=(.*?)");
    private static final Pattern ATTACK_PAWN_PROMOTION_MOVE = Pattern.compile("(.*?)x(.*?)=(.*?)");

    /**
     * Costruttore privato per impedire l'inizializzazione delle utilities.
     * Come tali devono essere usati via qualcificatori statici
     */
    private PGNUtilities() {
        throw new RuntimeException("Not Instantiable");
    }

    public static void persistPGNFile(final File pgnFile) throws IOException {
        int count = 0;
        int validCount = 0;

        try(final BufferedReader br = new BufferedReader(new FileReader(pgnFile))) {
            String line;
            PGNGameTags.TagsBuilder tagsBuilder = new PGNGameTags.TagsBuilder();
            StringBuilder gameTextBuilder = new StringBuilder();

            while((line = br.readLine()) != null) {
                if(!line.isEmpty()) {
                    if(isTag(line)) {
                        final Matcher matcher = PGN_PATTERN.matcher(line);

                        if(matcher.find())
                            tagsBuilder.addTag(matcher.group(1), matcher.group(2));
                    } else if(isEndOfGame(line)) {
                        final String[] ending = line.split(" ");
                        final String outcome = ending[ending.length - 1];
                        gameTextBuilder.append(line.replace(outcome, "")).append(" ");
                        final String gameText = gameTextBuilder.toString().trim();

                        if(!gameText.isEmpty() && gameText.length() > 80) {
                            final Game game = GameFactory.createGame(tagsBuilder.build(), gameText, outcome);
                            System.out.println("(" +(++count)+") Finished parsing " +game+ " count = " + (++count));

                            if(game.isValid()) {
                                MySqlGamePersistence.get().persistGame(game);
                                validCount++;
                            }
                        }

                        gameTextBuilder = new StringBuilder();
                        tagsBuilder = new PGNGameTags.TagsBuilder();
                    } else {
                        gameTextBuilder.append(line).append(" ");
                    }
                }
            }

            br.readLine();
        }

        System.out.println("Libro di costruzione finito dal file pgn: " + pgnFile + " Parsed " +count+ " games, valid = " +validCount);
    }

    /**
     * Questo metodo serve per creare la mossa da una stringa PGN
     * @param board scacchiera virtuale di riferimento
     * @param pgnText testo PGN di riferimento
     * @return la mossa da eseguire
     */
    public static Move createMove(final VirtualBoard board, final String pgnText) {
        final Matcher kingSideCastleMatcher = KING_SIDE_CASTLE.matcher(pgnText);
        final Matcher queenSideCastleMatcher = QUEEN_SIDE_CASTLE.matcher(pgnText);
        final Matcher plainPawnMatcher = PLAIN_PAWN_MOVE.matcher(pgnText);
        final Matcher attackPawnMatcher = PAWN_ATTACK_MOVE.matcher(pgnText);
        final Matcher pawnPromotionMatcher = PLAIN_PAWN_PROMOTION_MOVE.matcher(pgnText);
        final Matcher attackPawnPromotionMatcher = ATTACK_PAWN_PROMOTION_MOVE.matcher(pgnText);
        final Matcher plainMajorMatcher = PLAIN_MAJOR_MOVE.matcher(pgnText);
        final Matcher attackMajorMatcher = MAJOR_ATTACK_MOVE.matcher(pgnText);

        int currentCoordinate;
        int destinationCoordinate;

        if(kingSideCastleMatcher.matches()) {
            return extractCastleMove(board, "O-O");
        } else if (queenSideCastleMatcher.matches()) {
            return extractCastleMove(board, "O-O-O");
        } else if(plainPawnMatcher.matches()) {
            final String destinationSquare = plainPawnMatcher.group(1);
            destinationCoordinate = VirtualBoardUtils.INSTANCE.getCoordinateAtPosition(destinationSquare);
            currentCoordinate = deriveCurrentCoordinate(board, "P", destinationSquare, "");
            return MoveFactory.createMove(board, currentCoordinate, destinationCoordinate);
        } else if(attackPawnMatcher.matches()) {
            final String destinationSquare = attackPawnMatcher.group(3);
            destinationCoordinate = VirtualBoardUtils.INSTANCE.getCoordinateAtPosition(destinationSquare);
            final String disambiguationFile = attackPawnMatcher.group(1) != null ? attackPawnMatcher.group(1) : "";
            currentCoordinate = deriveCurrentCoordinate(board, "P", destinationSquare, disambiguationFile);
            return MoveFactory.createMove(board, currentCoordinate, destinationCoordinate);
        } else if (attackPawnPromotionMatcher.matches()) {
            final String destinationSquare = attackPawnPromotionMatcher.group(2);
            final String disambiguationFile = attackPawnPromotionMatcher.group(1) != null ? attackPawnPromotionMatcher.group(1) : "";
            destinationCoordinate = VirtualBoardUtils.INSTANCE.getCoordinateAtPosition(destinationSquare);
            currentCoordinate = deriveCurrentCoordinate(board, "P", destinationSquare, disambiguationFile);
            return MoveFactory.createMove(board, currentCoordinate, destinationCoordinate);
        } else if(pawnPromotionMatcher.find()) {
            final String destinationSquare = pawnPromotionMatcher.group(1);
            destinationCoordinate = VirtualBoardUtils.INSTANCE.getCoordinateAtPosition(destinationSquare);
            currentCoordinate = deriveCurrentCoordinate(board, "P", destinationSquare, "");
            return MoveFactory.createMove(board, currentCoordinate, destinationCoordinate);
        } else if (plainMajorMatcher.find()) {
            final String destinationSquare = plainMajorMatcher.group(3);
            destinationCoordinate = VirtualBoardUtils.INSTANCE.getCoordinateAtPosition(destinationSquare);
            final String disambiguationFile = plainMajorMatcher.group(2) != null ? plainMajorMatcher.group(2) : "";
            currentCoordinate = deriveCurrentCoordinate(board, plainMajorMatcher.group(1), destinationSquare, disambiguationFile);
            return MoveFactory.createMove(board, currentCoordinate, destinationCoordinate);
        } else if(attackMajorMatcher.find()) {
            final String destinationSquare = attackMajorMatcher.group(4);
            destinationCoordinate = VirtualBoardUtils.INSTANCE.getCoordinateAtPosition(destinationSquare);
            final String disambiguationFile = attackMajorMatcher.group(2) != null ? attackMajorMatcher.group(2) : "";
            currentCoordinate = deriveCurrentCoordinate(board, attackMajorMatcher.group(1), destinationSquare, disambiguationFile);
            return MoveFactory.createMove(board, currentCoordinate, destinationCoordinate);
        }

        return MoveFactory.getNullMove();
    }

    public static List<String> processMoveText(final String gameText) throws ParsePGNException {
        return gameText.isEmpty() ? Collections.emptyList() : createMovesFromPGN(gameText);
    }

    /**
     * Questo metodo serve ad estrarre la mossa di castling eseguita in base al risultato
     * @param board scacchiera virtuale di riferimento
     * @param castleMove stringa con il risultato della mossa eseguita
     * @return mossa di castling eseguita
     */
    private static Move extractCastleMove(final VirtualBoard board, final String castleMove) {
        for (final Move move : board.getCurrentPlayer().getUsableMoves()) {
            if (move.isCastlingMove() && move.toString().equals(castleMove))
                return move;
        }

        return MoveFactory.getNullMove();
    }

    /**
     * Questo metodo serve per derivare la coordinata della mossa corrente
     * @param board scacchiera virtuale di riferimento
     * @param movedPiece pedina mossa
     * @param destinationSquare posizione di destinazione
     * @param disambiguationFile file di differita
     * @return numero intero che rappresenta la coordinata
     * @throws RuntimeException eccezione di runtime generica
     */
    private static int deriveCurrentCoordinate(final VirtualBoard board, final String movedPiece, final String destinationSquare, final String disambiguationFile) throws RuntimeException {
        final List<Move> currentCandidate = new ArrayList<>();
        final int destinationCoordinate = VirtualBoardUtils.INSTANCE.getCoordinateAtPosition(destinationSquare);

        for(final Move move : board.getCurrentPlayer().getUsableMoves()) {
            if(move.getDestinationCoordinate() == destinationCoordinate && move.getPieceToMove().toString().equals(movedPiece))
                currentCandidate.add(move);
        }

        if(currentCandidate.size() == 0)
            return -1;

        return currentCandidate.size() == 1
                ? currentCandidate.iterator().next().getCurrentCoordinate()
                : extractFurther(currentCandidate, movedPiece, disambiguationFile);
    }

    /**
     * Questo metodo serve ad estrarre delle altre coordinate sulla base di una lista di pedina, della pedina mossa e il file disambito
     * @param candidateMoves lista di mosse da esaminare
     * @param movedPiece pedina mossa
     * @param disambiguationFile file con un movimento
     * @return numero intero con la coordinata. Nel peggiore dei casi ritorna "-1"
     */
    private static int extractFurther(final List<Move> candidateMoves, final String movedPiece, final String disambiguationFile) {
        final List<Move> currentCandidates = new ArrayList<>();

        // Controlla se il tipo di pedina è uguale, in quel caso aggiungilo alla lista
        for(final Move move : candidateMoves) {
            if(move.getPieceToMove().getPieceType().toString().equals(movedPiece))
                currentCandidates.add(move);
        }

        // Se c'è almeno una pedina del tipo ricercato ritorna la coordinata
        if(currentCandidates.size() == 1)
            return currentCandidates.iterator().next().getCurrentCoordinate();

        final List<Move> candidatesRefined = new ArrayList<>();

        // Se il tipo di pedina è incluso nel file disambito, aggiungilo alla lista
        for(final Move move : currentCandidates) {
            final String pos = VirtualBoardUtils.INSTANCE.getPositionAtCoordinate(move.getCurrentCoordinate());

            if(pos.contains(disambiguationFile))
                candidatesRefined.add(move);
        }

        // Se è presente almeno una pedina nella lista, ritorna la coordinata di essa
        if(candidatesRefined.size() == 1)
            return candidatesRefined.iterator().next().getCurrentCoordinate();

        // Ultimo caso, ritorno di "indefinito"
        return -1;
    }

    /**
     * Questo metodo serve a capire se il pezzo di stringa fornito sia un tag
     * @param gameText pezzo di stringa da analizzare
     * @return valore booleano
     */
    private static boolean isTag(final String gameText) {
        return gameText.startsWith("[") && gameText.endsWith("]");
    }

    /**
     * Questo metodo serve per capire se si è arrivati alla fine del file.
     * Lo capisce dalla presenza dei punteggi dei giocatori
     * @param gameText pezzo di stringa da confrontare
     * @return valore booleano
     */
    private static boolean isEndOfGame(final String gameText) {
        return gameText.endsWith("1-0") || gameText.endsWith("0-1") ||
                gameText.endsWith("1/2-1/2") || gameText.endsWith("*");
    }

    /**
     *
     * @param moves
     * @return
     */
    private static List<String> removeEmptyText(final List<String> moves) {
        final List<String> result = new ArrayList<>();
        for(final String moveText : moves) {
            if(!moveText.isEmpty())
                result.add(moveText);
        }

        return result;
    }

    /**
     * Questo metodo si occupa di rimuovere tutti gli spazi vuoti
     * @param row pezzo di stringa "sporco"
     * @return stringa senza spazi bianchi (vuoti)
     */
    private static String removeWhiteSpace(final String row) {
        return row.trim().replaceAll("\\s", " ");
    }

    /**
     * Questo metodo è in grado di creare un'elenco di mosse a partire da una stringa PGN
     * @param pgnText strinag PGN di partenza
     * @return lista di mosse da eseguire
     */
    private static List<String> createMovesFromPGN(final String pgnText) {
        if(!pgnText.startsWith("1."))
            return Collections.emptyList();

        final List<String> sanitizedMoves = new LinkedList<>(Arrays.asList(
                removeParenthesis(pgnText).replaceAll(Pattern.quote("$") + "[0-9]+", "").replaceAll("[0-9]+\\s*\\.\\.\\.", "")
                        .split("\\s*[0-9]+" + Pattern.quote("."))));
        final List<String> processedData = removeEmptyText(sanitizedMoves);
        final String[] moveRows = processedData.toArray(new String[processedData.size()]);
        final ImmutableList.Builder<String> moves = new ImmutableList.Builder<>();

        for(final String row : moveRows) {
            final String[] moveContent = removeWhiteSpace(row).split(" ");

            if(moveContent.length == 1) {
                moves.add(moveContent[0]);
            } else if(moveContent.length == 2) {
                moves.add(moveContent[0]);
                moves.add(moveContent[1]);
            } else {
                System.out.println("problem reading: " + pgnText + " skipping!");
                return Collections.emptyList();
            }
        }

        return moves.build();
    }

    /**
     * Questo metodo serve a rimuovere le parentesi da un testo
     * @param gameText stringa da dove rimuovere il testo
     * @return stringa ripulita
     */
    private static String removeParenthesis(final String gameText) {
        int parenthesisCounter = 0;
        final StringBuilder builder = new StringBuilder();

        for (final char c : gameText.toCharArray()) {
            if (c == '(' || c == '{' )
                parenthesisCounter++;

            if (c == ')' || c == '}' )
                parenthesisCounter--;

            if (!(c == '(' || c == '{' || c == ')' || c == '}') && parenthesisCounter == 0)
                builder.append(c);
        }

        return builder.toString();
    }
}
