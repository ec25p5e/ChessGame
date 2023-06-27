package pgn;

import core.board.VirtualBoard;
import core.move.Move;
import core.player.Player;

import java.sql.*;

import static util.MysqlConfiguration.*;

public class MySqlGamePersistence implements IPGNPersistence {

    private final Connection dbConnection;


    private static final String NEXT_BEST_MOVE_QUERY =
            "SELECT SUBSTR(g1.moves, LENGTH('%s') + %d, INSTR(SUBSTR(g1.moves, LENGTH('%s') + %d, LENGTH(g1.moves)), ',') - 1), " +
                    "COUNT(*) FROM game g1 WHERE g1.moves LIKE '%s%%' AND (outcome = '%s') GROUP BY substr(g1.moves, LENGTH('%s') + %d, " +
                    "INSTR(substr(g1.moves, LENGTH('%s') + %d, LENGTH(g1.moves)), ',') - 1) ORDER BY 2 DESC";

    private static MySqlGamePersistence INSTANCE = new MySqlGamePersistence();

    private MySqlGamePersistence() {
        // Stabilisci la connessione al DB
        this.dbConnection = createDBConnection();

        // Crea la tabella del gioco
        this.createGameTable();
    }

    /**
     *
     * @return
     */
    public static MySqlGamePersistence get() {
        return INSTANCE;
    }

    /**
     * @param game
     */
    @Override
    public void persistGame(Game game) {
        this.executePersist(game);
    }

    /**
     * @param board
     * @param player
     * @param gameText
     * @return
     */
    @Override
    public Move getNextBestMove(VirtualBoard board, Player player, String gameText) {
        return this.queryBestMove(board, player, gameText);
    }

    public int getMaxGameRow() {
        int maxId = 0;

        try {
            final String sqlString = "SELECT MAX(ID) FROM Game";
            final Statement gameStatement = this.dbConnection.createStatement();
            gameStatement.execute(sqlString);
            final ResultSet rs2 = gameStatement.getResultSet();

            if(rs2.next())
                maxId = rs2.getInt(1);

            gameStatement.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }

        return maxId;
    }

    /**
     * Questo metodo si occupa di create la connessione al DB mediante le costanti di configurazione
     * @return la connessione al DB oppure un throw exception nel caso ci fossero errori
     */
    private static Connection createDBConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch(final ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void createGameTable() {
        try {
            final Statement statement = this.dbConnection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS Game(id int primary key, outcome varchar(10), moves varchar(5000));");
            statement.close();
        } catch(final SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Questo metodo serve per determinare quale sia la mossa migliore da fare in base alle mosse precedentemente effettuate nel DB
     * @param board scacchiera virtuale di riferimento
     * @param player giocatore di riferimento
     * @param gameText stringa del movimento da valutare
     * @return la mossa considerata "migliore"
     */
    private Move queryBestMove(final VirtualBoard board, final Player player, final String gameText) {
        String bestMove = "";
        String count = "0";

        try {
            final int offset = gameText.isEmpty() ? 1 : 3;
            final String sqlString = String.format(NEXT_BEST_MOVE_QUERY, gameText, offset, gameText, offset,
                    gameText, player.getUtils().name(), gameText, offset, gameText, offset);

            System.out.println(sqlString);

            final Statement gameStatement = this.dbConnection.createStatement();
            gameStatement.execute(sqlString);

            final ResultSet rs2 = gameStatement.getResultSet();
            if(rs2.next()) {
                bestMove = rs2.getString(1);
                count = rs2.getString(2);
            }

            gameStatement.close();
        } catch(final SQLException e) {
            e.printStackTrace();
        }

        System.out.println("\tmossa archiviata selezionata = " + bestMove + " con " + count + " voti");
        return PGNUtilities.createMove(board, bestMove);
    }

    private void executePersist(final Game game) {
        try {
            final String gameSqlString = "INSERT INTO Game(id, outcome, moves) VALUES(?, ?, ?);";
            final PreparedStatement gameStatement = this.dbConnection.prepareStatement(gameSqlString);

            gameStatement.setInt(1, getMaxGameRow() + 1);
            gameStatement.setString(2, game.getWinner());
            gameStatement.setString(3, game.getMoves().toString().replaceAll("\\[", "").replaceAll("\\]", ""));
            gameStatement.executeUpdate();
            gameStatement.close();
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }

    }
}
