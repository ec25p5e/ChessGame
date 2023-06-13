package pgn;

import core.board.VirtualBoard;
import core.move.Move;
import core.player.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static util.MysqlConfiguration.*;

public class MySqlGamePersistence implements PGNPersistence {

    private final Connection dbConnection;


    private static final String NEXT_BEST_MOVE_QUERY =
            "";

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

    }

    /**
     * @param board
     * @param player
     * @param gameText
     * @return
     */
    @Override
    public Move getNextBestMove(VirtualBoard board, Player player, String gameText) {
        return null;
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
}
