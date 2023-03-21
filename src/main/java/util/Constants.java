package util;

import java.awt.*;

public class Constants {
    public static final String WINDOW_TITLE = "!!! SCACCHI !!!";
    public static final String RESOURCE_BASE_PATH = "src/main/resources/";
    public static final String SERIALIZATION_PATH = "src/main/resources/files/";
    public static final String BASE_GAME_FILE = "initGame.json";
    public static final Color BOARD_PANEL_BACKGROUND = Color.decode("#8B4726");
    public static final Dimension WINDOW_DIMENSION = new Dimension(600, 600);
    public static final Dimension BOARD_DIMENSION = new Dimension(400, 350);
    public static final Dimension TILE_DIMENSION = new Dimension(10, 10);

    // Core variable
    public static final String PIECES_PACKAGE = "core.pieces.";
    public static final String PAWN_PACKAGE = PIECES_PACKAGE + "Pawn";
    public static final String ROOK_PACKAGE = PIECES_PACKAGE + "Rook";
    public static final String BISHOP_PACKAGE = PIECES_PACKAGE + "Bishop";
    public static final String KNIGHT_PACKAGE = PIECES_PACKAGE + "Knight";
    public static final String QUEEN_PACKAGE = PIECES_PACKAGE + "Queen";
    public static final String KING_PACKAGE = PIECES_PACKAGE + "King";

    // Throw error message
    public static final String CLASS_NOT_FOUND = "Classe non trovata!";
    public static final String RUNTIME_EXCEPTION = "Eccezione di runtime";
    public static final String NPE_EXCEPTION = "Eccezione di puntatore nullo";
    public static final String IO_EXCEPTION = "Eccezione di IO";
}
