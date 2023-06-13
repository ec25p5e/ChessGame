package gui;

import core.board.BoardConfigurator;
import core.board.VirtualBoard;
import core.board.VirtualBoardUtils;
import core.pieces.piece.Piece;
import core.utils.Utils;
import org.fluttercode.datafactory.impl.DataFactory;
import org.w3c.dom.Text;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;

public class DrawingGame extends JDialog {

    public DrawingGame(final JFrame frame, final boolean modal) {
        super(frame, modal);

        final JPanel myPanel = new JPanel(new GridLayout(0, 1));
        final TableModel tableModel = createTableModel();
        final JTable table = new JTable(tableModel);
        final JTextField filterField = RowFilterUtil.createRowFilter(table);

        getContentPane().add(myPanel);
        myPanel.add(filterField, BorderLayout.NORTH);
        final JScrollPane pane = new JScrollPane(table);
        myPanel.add(pane, BorderLayout.CENTER);

        setLocationRelativeTo(frame);
        pack();
        setVisible(false);
    }

    public void promptUser(final int tileId) {
        setVisible(true);
        repaint();
    }

    private static TableModel createTableModel() {
        Vector<String> columns = new Vector<>(Arrays.asList("Name", "Address", "Age"));
        Vector<Vector<Object>> rows = new Vector<>();

        DataFactory dataFactory = new DataFactory();
        for (int i = 1; i <= 30; i++) {
            Vector<Object> v = new Vector<>();
            v.add(dataFactory.getName());
            v.add(dataFactory.getAddress() + ", " + dataFactory.getCity());
            v.add(dataFactory.getNumberBetween(18, 80));
            rows.add(v);
        }

        DefaultTableModel dtm = new DefaultTableModel(rows, columns) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 2 ? Integer.class : String.class;
            }
        };
        return dtm;
    }

    /* public void promptUser(final int tileId) {
        setVisible(true);
        repaint();

        // Esegui la logica in terminale
        System.out.println("Selezionata la coordinata: " + tileId + "; " + VirtualBoardUtils.INSTANCE.getPositionAtCoordinate(tileId));
        this.readAllPieces(Utils.BLACK);
    } */


    /* private void readAllPieces(Utils color) {
        VirtualBoard virtualBoard = VirtualBoard.initDefaultBoard();
        Collection<Piece> pieces = virtualBoard.getConfiguration().values();
        Collection<Piece> filtered;

        if(color != null) {
            filtered = pieces.stream()
                    .filter(piece -> piece.getPieceUtils() == color)
                    .collect(collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
        } else {
            filtered = pieces;
        }

        for(Piece piece : filtered) {
            System.out.println(piece + "; " + piece.getPieceCoordinate());
        }
    } */
}
