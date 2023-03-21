package pgn;

import core.board.VirtualBoard;
import core.board.VirtualBoardUtils;
import core.move.Move;
import core.move.MoveFactory;
import core.move.MoveLog;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PGNUtilities {
    private static final Pattern PGN_PATTERN = Pattern.compile("\\[(\\w+)\\s+\"(.*?)\"\\]$");
    private static final Pattern KING_SIDE_CASTLE = Pattern.compile("O-O#?\\+?");
    private static final Pattern QUEEN_SIDE_CASTLE = Pattern.compile("O-O-O#?\\+?");
    private static final Pattern PLAIN_PAWN_MOVE = Pattern.compile("^([a-h][0-8])(\\+)?(#)?$");
    private static final Pattern PAWN_ATTACK_MOVE = Pattern.compile("(^[a-h])(x)([a-h][0-8])(\\+)?(#)?$");
    private static final Pattern PLAIN_MAJOR_MOVE = Pattern.compile("^(B|N|R|Q|K)([a-h]|[1-8])?([a-h][0-8])(\\+)?(#)?$");
    private static final Pattern MAJOR_ATTACK_MOVE = Pattern.compile("^(B|N|R|Q|K)([a-h]|[1-8])?(x)([a-h][0-8])(\\+)?(#)?$");
    private static final Pattern PLAIN_PAWN_PROMOTION_MOVE = Pattern.compile("(.*?)=(.*?)");
    private static final Pattern ATTACK_PAWN_PROMOTION_MOVE = Pattern.compile("(.*?)x(.*?)=(.*?)");

    private PGNUtilities() {
        throw new RuntimeException("Not Instantiable!");
    }

    public static void persistPGNFile(final File pgnFile) throws IOException {
        int count = 0;
        int validCount = 0;

        /* try (final BufferedReader br = new BufferedReader(new FileReader(pgnFile))) {
            String line;
            PGNGameTags.TagsBuilder tagsBuilder = new PGNGameTags.TagsBuilder();
            StringBuilder gameTextBuilder = new StringBuilder();
            while((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    if (isTag(line)) {
                        final Matcher matcher = PGN_PATTERN.matcher(line);
                        if (matcher.find()) {
                            tagsBuilder.addTag(matcher.group(1), matcher.group(2));
                        }
                    }
                    else if (isEndOfGame(line)) {
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
                    }
                    else {
                        gameTextBuilder.append(line).append(" ");
                    }
                }
            }
            br.readLine();
        } */
        System.out.println("Finished building book from pgn file: " + pgnFile + " Parsed " +count+ " games, valid = " +validCount);
    }

    /**
     * Questo metodo serve come entry point per capire se creare le mosse dal file
     * @param gameText stringa di testo del file
     * @return lista di stringhe, contenenti la lista delle mosse da fare
     * @throws ParsePGNException eccezione nel caso ci fossero problemi (custom)
     */
    public static List<String> processMoveText(final String gameText) throws ParsePGNException {
        return gameText.isEmpty() ? Collections.emptyList() : createMovesFromPGN(gameText);
    }

    /**
     * Questo metodo serve per create la mossa in base a quanto scritto nella stringa del file PGN
     * @param board scacchiera virtuale di riferimento
     * @param pgnText stringa di origine del file
     * @return mossa da effettuare
     */
    public static Move createMove(final VirtualBoard board, final String pgnText){
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

    /**
     *
     * @param pgnFile
     * @param moveLog
     * @throws IOException
     */
    public static void writeGameToPGNFile(final File pgnFile, final MoveLog moveLog) throws IOException {
        final StringBuilder builder = new StringBuilder();
        builder.append(calculateEventString()).append("\n");
        builder.append(calculateDateString()).append("\n");
        builder.append(calculatePlyCountString(moveLog)).append("\n");
        for(final Move move : moveLog.getMoves()) {
            builder.append(move.toString()).append(" ");
        }
        try (final Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pgnFile, true)))) {
            writer.write(builder.toString());
        }
    }

    /**
     * Questo metodo serve per calcolare la event string
     * @return stringa con l'event tag
     */
    private static String calculateEventString() {
        return "[Event \"" + "Black Widow Game" + "\"]";
    }

    /**
     * Questo metodo serve per calcolare il tag della data di creazione del file
     * @return stringa con la data di creazione del file
     */
    private static String calculateDateString() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        return "[Date \"" + dateFormat.format(new Date()) + "\"]";
    }

    /**
     * Questo metodo serve per calcolare la stringa di conteggio del Ply
     * @param moveLog lista delle pedine catturate
     * @return stringa con il conteggio
     */
    private static String calculatePlyCountString(final MoveLog moveLog) {
        return "[PlyCount \"" + moveLog.size() + "\"]";
    }

    /**
     * Questo metodo serve per creare una serie di mosse sulla base di un file PGN
     * @param pgnText stringa di riferimento del file PGN
     * @return lista di mosse da effettuare (toString)
     */
    private static List<String> createMovesFromPGN(final String pgnText) {
        if(!pgnText.startsWith("1."))
            return Collections.emptyList();

        final List<String> sanitizedMoves = new LinkedList<>(Arrays.asList(
                removeParenthesis(pgnText).replaceAll(Pattern.quote("$") + "[0-9]+", "").replaceAll("[0-9]+\\s*\\.\\.\\.", "")
                        .split("\\s*[0-9]+" + Pattern.quote("."))));

        final List<String> processedData = removeEmptyText(sanitizedMoves);
        final String[] moveRows = processedData.toArray(new String[processedData.size()]);
        final ImmutableList.Builder<String> moves = new Builder<>();

        for(final String row : moveRows) {
            final String[] moveContent = removeWhiteSpace(row).split(" ");

            if(moveContent.length == 1) {
                moves.add(moveContent[0]);
            } else if(moveContent.length == 2) {
                moves.add(moveContent[0]);
                moves.add(moveContent[1]);
            } else {
                System.out.println("problema di lettura: " +pgnText+ " saltata la procedura!");
                return Collections.emptyList();
            }
        }

        return moves.build();
    }

    /**
     * Questo metodo serve per rimuovere gli spazi vuoti dal testo
     * @param moves lista di mosse testuali
     * @return testo delle mosse senza spazi vuoti
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
     * Questo metodo serve per rimuovere gli spazi vuoti nella riga
     * @param row riga di testo dove rimuovere gli spazi
     * @return stringa pulita
     */
    private static String removeWhiteSpace(final String row) {
        return row.trim().replaceAll("\\s+", " ");
    }

    /**
     * Questo metodo serve per identificare se il pezzo di file è un tag,
     * cercando una parentesi quadra aperta e chiusa.
     * @param gameText stringa di testo del file di gioco
     * @return valore booleano
     */
    private static boolean isTag(final String gameText) {
        return gameText.startsWith("[") && gameText.endsWith("]");
    }

    /**
     * Questo metodo serve per verificare se il gioco è arrivato alla fine,
     * cercando le indicazioni sul punteggio
     * @param gameText stringa di testo del file di gioco
     * @return valore booleano
     */
    private static boolean isEndOfGame(final String gameText) {
        return gameText.endsWith("1-0") || gameText.endsWith("0-1") ||
                gameText.endsWith("1/2-1/2") || gameText.endsWith("*");
    }

    /**
     * Questo metodo serve per rimuovere le parentesi da un testo.
     * Le parentesi sono le tonde e le graffe.
     * @param gameText testo con le parentesi da rimuovere
     * @return testo pulito
     */
    private static String removeParenthesis(final String gameText) {
        int parenthesisCounter = 0;
        final StringBuilder builder = new StringBuilder();

        for(final char c : gameText.toCharArray()) {
            if(c == '(' || c == '{')
                parenthesisCounter++;

            if(c == ')' || c == '}')
                parenthesisCounter--;

            if(!(c == '(' || c == '{' || c == ')' || c == '}') && parenthesisCounter == 0)
                builder.append(c);
        }

        return builder.toString();
    }

    /**
     * Questo metodo serve ad estrarre la mossa di scacco
     * @param board scacchiera virtuale di riferimento
     * @param castleMove mossa di scacco per il confronto
     * @return mossa di scacco effettiva
     */
    private static Move extractCastleMove(final VirtualBoard board, final String castleMove) {
        for(final Move move : board.getCurrentPlayer().getUsableMoves()) {
            if(move.isCastlingMove() && move.toString().equals(castleMove))
                return move;
        }

        return MoveFactory.getNullMove();
    }

    /**
     * Questo metodo serve per derivare la coordinata corrente
     * @param board scacchiera virtuale di riferimento
     * @param movedPiece pedina mossa
     * @param destinationSquare coordinata di destinazione
     * @param disambiguationFile identificativo di strina univoco
     * @return numero intero che indica la coordinata
     * @throws RuntimeException eccezione di runtime normale in caso di errore
     * con la ricerca delle coordinate reali delle pedine
     */
    private static int deriveCurrentCoordinate(final VirtualBoard board, final String movedPiece, final String destinationSquare, final String disambiguationFile) throws RuntimeException {
        final List<Move> currentCandidates = new ArrayList<>();
        final int destinationCoordinate = VirtualBoardUtils.INSTANCE.getCoordinateAtPosition(destinationSquare);

        for(final Move move : board.getCurrentPlayer().getUsableMoves()) {
            if(move.getDestinationCoordinate() == destinationCoordinate && move.getPieceToMove().toString().equals(movedPiece))
                currentCandidates.add(move);
        }

        if(currentCandidates.size() == 0)
            return -1;

        return currentCandidates.size() == 1
                ? currentCandidates.iterator().next().getCurrentCoordinate()
                : extractFurther(currentCandidates, movedPiece, disambiguationFile);
    }

    /**
     * Questo metodo serve per estrarre tutte le altre mosse non specializzate
     * @param candidateMoves lista di mosse possibili
     * @param movedPiece pedina mossa
     * @param disambiguationFile stringa di riconoscimento delle ambiguità
     * @return numero intero positivo che indica la coordinata
     */
    private static int extractFurther(final List<Move> candidateMoves, final String movedPiece, final String disambiguationFile) {
        final List<Move> currentCandidates = new ArrayList<>();

        for(final Move move : candidateMoves) {
            if(move.getPieceToMove().getPieceType().toString().equals(movedPiece))
                currentCandidates.add(move);
        }

        if(currentCandidates.size() == 1)
            return currentCandidates.iterator().next().getCurrentCoordinate();

        final List<Move> candidatesRefined = new ArrayList<>();

        for (final Move move : currentCandidates) {
            final String pos = VirtualBoardUtils.INSTANCE.getPositionAtCoordinate(move.getCurrentCoordinate());
            if (pos.contains(disambiguationFile))
                candidatesRefined.add(move);
        }

        if(candidatesRefined.size() == 1)
            return candidatesRefined.iterator().next().getCurrentCoordinate();

        return -1;
    }
}
