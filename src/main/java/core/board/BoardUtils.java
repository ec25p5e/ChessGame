package core.board;

import java.util.Arrays;
import java.util.List;

public enum BoardUtils {
    INSTANCE;

    public final List<Boolean> FIRST_ROW = initRow(0);
    public final List<Boolean> SECOND_ROW = initRow(1);
    public final List<Boolean> THIRD_ROW = initRow(2);
    public final List<Boolean> FOURTH_ROW = initRow(3);
    public final List<Boolean> FIFTH_ROW = initRow(4);
    public final List<Boolean> SIXTH_ROW = initRow(5);
    public final List<Boolean> SEVENTH_ROW = initRow(6);
    public final List<Boolean> EIGHTH_ROW = initRow(7);

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_ROW = 8;

    private static List<Boolean> initRow(int rowNum) {
        final Boolean[] row = new Boolean[NUM_TILES];
        Arrays.fill(row, false);

        do {
            row[rowNum] = true;
            rowNum++;
        } while(rowNum % NUM_TILES_ROW != 0);

        return List.of(row);
    }
}
