package core.board;

import java.util.*;

public enum BoardUtils {
    INSTANCE;

    public final List<Boolean> FIRST_ROW = initRow(0);
    public final List<Boolean> SECOND_ROW = initRow(8);
    public final List<Boolean> THIRD_ROW = initRow(16);
    public final List<Boolean> FOURTH_ROW = initRow(24);
    public final List<Boolean> FIFTH_ROW = initRow(32);
    public final List<Boolean> SIXTH_ROW = initRow(40);
    public final List<Boolean> SEVENTH_ROW = initRow(48);
    public final List<Boolean> EIGHTH_ROW = initRow(56);
    public final List<String> NOTATION = setupNotation();
    public final Map<String, Integer> POSITION_TO_COORDINATE = setupPositionToCoordinateMap();

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_ROW = 8;
    public static final int START_TILE_INDEX = 0;

    public static boolean isValidTileCoordinate(final int coordinate) {
        return coordinate >= START_TILE_INDEX && coordinate < NUM_TILES;
    }

    public int getCoordinateAtPosition(final String position) {
        return POSITION_TO_COORDINATE.get(position);
    }

    public String getPositionAtCoordinate(final int coordinate) {
        return NOTATION.get(coordinate);
    }

    private Map<String, Integer> setupPositionToCoordinateMap() {
        final Map<String, Integer> positionToCoordinate = new HashMap<>();
        for(int i = START_TILE_INDEX; i < NUM_TILES; i++) {
            positionToCoordinate.put(NOTATION.get(i), i);
        }
        return Collections.unmodifiableMap(positionToCoordinate);
    }

    private static List<Boolean> initRow(int rowNum) {
        final Boolean[] row = new Boolean[NUM_TILES];
        Arrays.fill(row, false);

        do {
            row[rowNum] = true;
            rowNum++;
        } while(rowNum % NUM_TILES_ROW != 0);

        return List.of(row);
    }

    private static List<String> setupNotation() {
        return List.of(
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"
        );
    }
}
