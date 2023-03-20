package pgn;

import core.board.BoardConfigurator;
import core.board.VirtualBoard;
import core.board.VirtualBoardUtils;
import core.pieces.*;
import core.utils.Utils;

public class FenUtilities {

    private FenUtilities() {
        throw new RuntimeException("Not Instantiable!");
    }

    public static VirtualBoard createGameFromFEN(final String fenString) {
        return parseFEN(fenString);
    }

    public static String createFENFromGame(final VirtualBoard board) {
        return calculateVirtualBoardText(board) + " " +
                calculateCurrentPlayerText(board) + " " +
                calculateCastleText(board) + " " +
                calculateEnPassantSquare(board) + " " +
                "0 1";
    }

    private static VirtualBoard parseFEN(final String fenString) {
        final String[] fenPartitions = fenString.trim().split(" ");
        final BoardConfigurator builder = new BoardConfigurator();
        final boolean whiteKingSideCastle = whiteKingSideCastle(fenPartitions[2]);
        final boolean whiteQueenSideCastle = whiteQueenSideCastle(fenPartitions[2]);
        final boolean blackKingSideCastle = blackKingSideCastle(fenPartitions[2]);
        final boolean blackQueenSideCastle = blackQueenSideCastle(fenPartitions[2]);
        final String gameConfiguration = fenPartitions[0];
        final char[] boardTiles = gameConfiguration.replaceAll("/", "")
                .replaceAll("8", "--------")
                .replaceAll("7", "-------")
                .replaceAll("6", "------")
                .replaceAll("5", "-----")
                .replaceAll("4", "----")
                .replaceAll("3", "---")
                .replaceAll("2", "--")
                .replaceAll("1", "-")
                .toCharArray();

        int i = 0;

        while (i < boardTiles.length) {
            switch (boardTiles[i]) {
                case 'r':
                    builder.setPiece(new Rook(i, Utils.BLACK));
                    i++;
                    break;
                case 'n':
                    builder.setPiece(new Knight(i, Utils.BLACK));
                    i++;
                    break;
                case 'b':
                    builder.setPiece(new Bishop(i, Utils.BLACK));
                    i++;
                    break;
                case 'q':
                    builder.setPiece(new Queen(i, Utils.BLACK));
                    i++;
                    break;
                case 'k':
                    final boolean isCastled = !blackKingSideCastle && !blackQueenSideCastle;
                    builder.setPiece(new King(i, Utils.BLACK, blackKingSideCastle, blackQueenSideCastle));
                    i++;
                    break;
                case 'p':
                    builder.setPiece(new Pawn(i, Utils.BLACK));
                    i++;
                    break;
                case 'R':
                    builder.setPiece(new Rook(i, Utils.WHITE));
                    i++;
                    break;
                case 'N':
                    builder.setPiece(new Knight(i, Utils.WHITE));
                    i++;
                    break;
                case 'B':
                    builder.setPiece(new Bishop(i, Utils.WHITE));
                    i++;
                    break;
                case 'Q':
                    builder.setPiece(new Queen(i, Utils.WHITE));
                    i++;
                    break;
                case 'K':
                    builder.setPiece(new King(i, Utils.WHITE, whiteKingSideCastle, whiteQueenSideCastle));
                    i++;
                    break;
                case 'P':
                    builder.setPiece(new Pawn(i, Utils.WHITE));
                    i++;
                    break;
                case '-':
                    i++;
                    break;
                default:
                    throw new RuntimeException("Invalid FEN String " +gameConfiguration);
            }
        }

        builder.setMoveMaker(moveMaker(fenPartitions[1]));
        return builder.build();
    }

    private static Utils moveMaker(final String moveMakerString) {
        if(moveMakerString.equals("w"))
            return Utils.WHITE;
        else if(moveMakerString.equals("b"))
            return Utils.BLACK;

        throw new RuntimeException("Invalid FEN String " +moveMakerString);
    }

    private static boolean whiteKingSideCastle(final String fenCastleString) {
        return fenCastleString.contains("K");
    }

    private static boolean whiteQueenSideCastle(final String fenCastleString) {
        return fenCastleString.contains("Q");
    }

    private static boolean blackKingSideCastle(final String fenCastleString) {
        return fenCastleString.contains("k");
    }

    private static boolean blackQueenSideCastle(final String fenCastleString) {
        return fenCastleString.contains("q");
    }

    private static String calculateCastleText(final VirtualBoard board) {
        final StringBuilder builder = new StringBuilder();
        
        if(board.getWhitePlayer().isKingSideCastleCapable())
            builder.append("K");
            
        if(board.getWhitePlayer().isQueenSideCastleCapable())
            builder.append("Q");
        
        if(board.getBlackPlayer().isKingSideCastleCapable())
            builder.append("k");
        
        if(board.getBlackPlayer().isQueenSideCastleCapable())
            builder.append("q");
        
        final String result = builder.toString();
        return result.isEmpty() ? "-" : result;
    }

    private static String calculateEnPassantSquare(final VirtualBoard board) {
        final Pawn enPassantPawn = board.getEnPassantPawn();
        if(enPassantPawn != null) {
            return VirtualBoardUtils.INSTANCE.getPositionAtCoordinate(enPassantPawn.getPiecePosition() +
                    (8) * enPassantPawn.getPieceUtils().getOppositeDirection());
        }
        return "-";
    }

    private static String calculateVirtualBoardText(final VirtualBoard board) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < VirtualBoardUtils.NUM_TILES; i++) {
            final String tileText = board.getPiece(i) == null ? "-" :
                    board.getPiece(i).getPieceUtils().isWhite() ? board.getPiece(i).toString() :
                            board.getPiece(i).toString().toLowerCase();
            builder.append(tileText);
        }
        builder.insert(8, "/");
        builder.insert(17, "/");
        builder.insert(26, "/");
        builder.insert(35, "/");
        builder.insert(44, "/");
        builder.insert(53, "/");
        builder.insert(62, "/");
        return builder.toString()
                .replaceAll("--------", "8")
                .replaceAll("-------", "7")
                .replaceAll("------", "6")
                .replaceAll("-----", "5")
                .replaceAll("----", "4")
                .replaceAll("---", "3")
                .replaceAll("--", "2")
                .replaceAll("-", "1");
    }

    private static String calculateCurrentPlayerText(final VirtualBoard board) {
        return board.getCurrentPlayer().toString().substring(0, 1).toLowerCase();
    }
}
