package gui;

import com.google.common.primitives.Ints;
import core.board.MoveLog;
import core.movements.Move;
import core.pieces.piece.Piece;
import util.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TakenPiecesPanel extends JPanel {
    private final JPanel northPanel;
    private final JPanel southPanel;

    private static final long serialVersionUID = 1L;
    private static final Color PANEL_COLOR = Color.decode("0xFDF5E6");
    private static final Dimension TAKEN_PIECES_PANEL_DIMENSION = new Dimension(40, 80);
    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);

    public TakenPiecesPanel() {
        super(new BorderLayout());
        setBackground(Color.decode("0xFDF5E6"));
        setBorder(PANEL_BORDER);
        this.northPanel = new JPanel(new GridLayout(8, 2));
        this.southPanel = new JPanel(new GridLayout(8, 2));
        this.northPanel.setBackground(PANEL_COLOR);
        this.southPanel.setBackground(PANEL_COLOR);
        add(this.northPanel, BorderLayout.NORTH);
        add(this.southPanel, BorderLayout.SOUTH);
        setPreferredSize(TAKEN_PIECES_PANEL_DIMENSION);
    }

    public void redo(final MoveLog moveLog) {
        // Pulisci entrambe le colonne
        this.southPanel.removeAll();
        this.northPanel.removeAll();

        // Array con le pedine catturate (uno per colore)
        final List<Piece> whiteTakenPieces = new ArrayList<>();
        final List<Piece> blackTakenPieces = new ArrayList<>();

        // Cicla tutte le mosse e determina quali sono state delle mosse di attacco
        // Per tutte le mosse di attacco dividi il colore del pedone mangiato
        // e inseriscile nei rispettivi array
        for(final Move move : moveLog.getMoves()) {
            if(move.isAttack()) {
                final Piece takenPiece = move.getPieceAttacked();

                if(takenPiece.getPieceUtils().isWhite())
                    whiteTakenPieces.add(takenPiece);
                else if(takenPiece.getPieceUtils().isBlack())
                    blackTakenPieces.add(takenPiece);
                else
                    throw new RuntimeException("Identità della pedina non valida!");
            }
        }

        // Ordina le pedine bianche per il valore asc
        whiteTakenPieces.sort((p1, p2) -> Ints.compare(p1.getPieceValue(), p2.getPieceValue()));

        // Ordina le pedine nere per valore asc
        blackTakenPieces.sort((p1, p2) -> Ints.compare(p1.getPieceValue(), p2.getPieceValue()));

        // Mostra le pedine bianche nella colonna
        for (final Piece takenPiece : whiteTakenPieces) {
            try {
                final BufferedImage image = ImageIO.read(new File(Constants.RESOURCE_BASE_PATH + "pieceIcon/"
                        + takenPiece.getPieceUtils().toString().substring(0, 1) + "" + takenPiece.toString()
                        + ".gif"));
                final ImageIcon ic = new ImageIcon(image);
                final JLabel imageLabel = new JLabel(new ImageIcon(ic.getImage().getScaledInstance(
                        ic.getIconWidth() - 15, ic.getIconWidth() - 15, Image.SCALE_SMOOTH)));
                this.southPanel.add(imageLabel);
            }
            catch (final IOException e) {
                e.printStackTrace();
            }
        }

        // Mostra le pedine nere nella colonna
        for (final Piece takenPiece : blackTakenPieces) {
            try {
                final BufferedImage image = ImageIO.read(new File(Constants.RESOURCE_BASE_PATH + "pieceIcon/"
                        + takenPiece.getPieceUtils().toString().substring(0, 1) + "" + takenPiece.toString()
                        + ".gif"));
                final ImageIcon ic = new ImageIcon(image);
                final JLabel imageLabel = new JLabel(new ImageIcon(ic.getImage().getScaledInstance(
                        ic.getIconWidth() - 15, ic.getIconWidth() - 15, Image.SCALE_SMOOTH)));
                this.northPanel.add(imageLabel);

            } catch (final IOException e) {
                e.printStackTrace();
            }
        }

        validate();
    }
}
