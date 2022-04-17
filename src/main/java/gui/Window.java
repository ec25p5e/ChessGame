package gui;

import com.google.common.collect.Lists;

import core.board.Board;
import core.board.BoardUtils;
import util.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;

/**
 *
 */
public class Window {
    private final JFrame windowFrame;
    private final DebugPanel debugPanel;
    private final BoardPanel boardPanel;
    private String pieceIconPath;
    private Board chessBoard;
    private BoardDirection boardDirection;
    private Color lightTileColor = Color.decode("#FFFACD");
    private Color darkTileColor = Color.decode("#593E1A");

    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private static final Dimension WINDOW_DIMENSION = new Dimension(600, 600);
    private static final Window INSTANCE = new Window();

    private Window() {
        this.windowFrame = new JFrame(Constants.WINDOW_TITLE);
        this.windowFrame.setLayout(new BorderLayout());
        this.chessBoard = Board.createDefaultBoard();
        this.boardDirection = BoardDirection.NORMAL;
        this.debugPanel = new DebugPanel();
        this.boardPanel = new BoardPanel();
        this.pieceIconPath = "src/resources/pieces/";
        setDefaultLookAndFeelDecorated(true);
        this.windowFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.windowFrame.add(this.debugPanel, BorderLayout.SOUTH);
        this.windowFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.windowFrame.setSize(WINDOW_DIMENSION);
        center(this.windowFrame);
        this.windowFrame.setVisible(true);
    }

    public static Window get() {
        return INSTANCE;
    }

    public void display() {
        Window.get().getBoardPanel().drawBoard(Window.get().getGameBoard());
    }

    private JFrame getWindowFrame() {
        return this.windowFrame;
    }

    private BoardPanel getBoardPanel() {
        return this.boardPanel;
    }

    private Board getGameBoard() {
        return this.chessBoard;
    }

    private static void center(final JFrame frame) {
        final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        final int w = frame.getSize().width;
        final int h = frame.getSize().height;
        final int x = (dim.width - w) / 2;
        final int y = (dim.height - h) / 2;
        frame.setLocation(x, y);
    }

    private class BoardPanel extends JPanel {
        private final List<TilePanel> boardTiles;

        public BoardPanel() {
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();

            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                this.add(tilePanel);
            }

            this.setPreferredSize(BOARD_PANEL_DIMENSION);
            this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            this.setBackground(Color.decode("#8B4726"));
            this.validate();
        }

        public void drawBoard(final Board board) {
            this.removeAll();

            for(final TilePanel boardTile: boardDirection.traverse(this.boardTiles)) {
                boardTile.drawTile(board);
                this.add(boardTile);
            }

            this.validate();
            this.repaint();
        }
    }

    private class TilePanel extends JPanel {
        private final int tileId;

        public TilePanel(final BoardPanel boardPanel, final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            this.setPreferredSize(TILE_PANEL_DIMENSION);
            this.assignTileColor();
            this.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            this.validate();
        }

        public void drawTile(final Board board) {
            this.assignTileColor();
            this.assignTilePieceIcon(board);
            this.validate();
            this.repaint();
        }

        private void assignTilePieceIcon(final Board board) {
            this.removeAll();

            if(board.getPiece(this.tileId) != null) {
                try {
                    final BufferedImage image = ImageIO.read(
                            new File(
                                    pieceIconPath +
                                    board.getPiece(this.tileId)));

                    this.add(new JLabel(new ImageIcon(image)));
                } catch(final IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void assignTileColor() {
            if (BoardUtils.INSTANCE.FIRST_ROW.get(this.tileId) ||
                    BoardUtils.INSTANCE.THIRD_ROW.get(this.tileId) ||
                    BoardUtils.INSTANCE.FIFTH_ROW.get(this.tileId) ||
                    BoardUtils.INSTANCE.SEVENTH_ROW.get(this.tileId)) {
                setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
            } else if(BoardUtils.INSTANCE.SECOND_ROW.get(this.tileId) ||
                    BoardUtils.INSTANCE.FOURTH_ROW.get(this.tileId) ||
                    BoardUtils.INSTANCE.SIXTH_ROW.get(this.tileId)  ||
                    BoardUtils.INSTANCE.EIGHTH_ROW.get(this.tileId)) {
                setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);
            }
        }
    }

    private enum BoardDirection {
        NORMAL {
            @Override
            List<TilePanel> traverse(List<TilePanel> boardTiles) {
                return boardTiles;
            }

            @Override
            BoardDirection opposite() {
                return FLIPPED;
            }
        },
        FLIPPED {
            @Override
            List<TilePanel> traverse(List<TilePanel> boardTiles) {
                return Lists.reverse(boardTiles);
            }

            @Override
            BoardDirection opposite() {
                return NORMAL;
            }
        };

        abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);
        abstract BoardDirection opposite();
    }
}
