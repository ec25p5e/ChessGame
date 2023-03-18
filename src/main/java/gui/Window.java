package gui;

import com.google.common.collect.Lists;
import core.board.MoveLog;
import core.board.VirtualBoard;
import core.board.VirtualBoardUtils;
import core.movements.Move;
import core.movements.MoveFactory;
import core.movements.MoveTransition;
import core.pieces.piece.Piece;
import core.player.ai.PlayerType;
import core.player.ai.StockAlphaBeta;
import lombok.Getter;
import lombok.Setter;
import util.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static javax.swing.SwingUtilities.*;

/**
 * Questa classe rappresenta la GUI e i suoi elementi collegati.
 */
@Getter
@Setter
public final class Window extends Observable {
    private final JFrame windowFrame;
    private final MoveLog moveLog;
    private final TakenPiecesPanel takenPiecesPanel;

    private VirtualBoard virtualBoard;
    private BoardDirection boardDirection;
    private Piece sourceTile;
    private Piece humanMovedPiece;
    private BoardPanel boardPanel;
    private Move computerMove;

    private static final Window INSTANCE = new Window();

    private Window() {
        this.windowFrame = new JFrame(Constants.WINDOW_TITLE);
        final JMenuBar tableMenuBar = new JMenuBar();
        this.populateMenuBar(tableMenuBar);
        this.windowFrame.setJMenuBar(tableMenuBar);
        this.windowFrame.setLayout(new BorderLayout());
        this.virtualBoard = VirtualBoard.getDefaultBoard();
        this.boardDirection = BoardDirection.NORMAL;
        this.boardPanel = new BoardPanel();
        this.moveLog = new MoveLog();
        this.takenPiecesPanel = new TakenPiecesPanel();
        this.addObserver(new TableGameAIWatcher());
        this.windowFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
        this.windowFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.windowFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.windowFrame.setSize(Constants.WINDOW_DIMENSION);
        this.windowFrame.setVisible(true);
    }

    public static Window get() {
        return INSTANCE;
    }

    public void start() {
        Window.get().getMoveLog().clear();
        Window.get().getTakenPiecesPanel().redo(Window.get().getMoveLog());
        Window.get().getBoardPanel().drawBoard(Window.get().getVirtualBoard());
    }

    /**
     *
     * @param playerType
     */
    private void moveMadeUpdate(final PlayerType playerType) {
        setChanged();
        notifyObservers(playerType);
    }

    private void updateGameBoard(final VirtualBoard board) {
        this.virtualBoard = board;
    }

    private void updateComputerMove(final Move move) {
        this.computerMove = move;
    }

    private void populateMenuBar(final JMenuBar tableMenuBar) {
        tableMenuBar.add(this.createFileMenu());
        tableMenuBar.add(this.createOptionsMenu());
        tableMenuBar.add(this.createPreferencesMenu());
    }

    private JMenu createFileMenu() {
        final JMenu filesMenu = new JMenu("File");
        filesMenu.setMnemonic(KeyEvent.VK_F);

        return filesMenu;
    }

    private JMenu createOptionsMenu() {
        final JMenu optionsMenu = new JMenu("Opzioni");
        optionsMenu.setMnemonic(KeyEvent.VK_O);


        // Annullare ultima mossa
        final JMenuItem undoLastMove = new JMenuItem("Annulla ultima mossa", KeyEvent.VK_Z);
        undoLastMove.addActionListener(e -> {
            if(Window.get().getMoveLog().size() > 0)
                this.undoLastMove();
        });
        optionsMenu.add(undoLastMove);

        return optionsMenu;
    }

    private JMenu createPreferencesMenu() {
        final JMenu preferencesMenu = new JMenu("Preferenze");
        preferencesMenu.setMnemonic(KeyEvent.VK_P);


        // Invertire la board (flip board)
        final JMenuItem flipBoardItem = new JMenuItem("Inverti scacchiera");
        flipBoardItem.addActionListener(e -> {
            this.boardDirection = this.boardDirection.opposite();
            this.boardPanel.drawBoard(this.virtualBoard);
        });
        preferencesMenu.add(flipBoardItem);

        return preferencesMenu;
    }

    /**
     *
     */
    private void undoLastMove() {
       final Move lastMove = Window.get().getMoveLog().removeMove(Window.get().getMoveLog().size() - 1);
       this.virtualBoard = this.virtualBoard.getCurrentPlayer().unMakeMove(lastMove).toBoard();
       this.computerMove = null;

       Window.get().getMoveLog().removeMove(lastMove);
       Window.get().getTakenPiecesPanel().redo(Window.get().getMoveLog());
       Window.get().getBoardPanel().drawBoard(this.virtualBoard);
    }



    /**
     * Questa classe rappresenta la scacchiera "fisica" che viene disegnata nella GUI
     */
    private class BoardPanel extends JPanel {
        private final List<Tile> boardTiles;

        public BoardPanel() {
            super(new GridLayout(8, 8));
            this.boardTiles = new ArrayList<>();

            for(int i = 0; i < 64; i++) {
                final Tile tile = new Tile(this, i);
                this.boardTiles.add(tile);
                this.add(tile);
            }

            this.setPreferredSize(Constants.BOARD_DIMENSION);
            this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            this.setBackground(Constants.BOARD_PANEL_BACKGROUND);
            this.validate();
        }

        /**
         * Questo metodo serve a ri-disegnare la scacchiera quando viene spostata una pedina o inizia il gioco
         * @param board scacchiera "virtuale di riferimento"
         */
        public void drawBoard(final VirtualBoard board) {
            this.removeAll();

            for (final Tile boardTile : boardDirection.traverse(boardTiles)) {
                boardTile.drawTile(board);
                add(boardTile);
            }

            validate();
            repaint();
        }
    }



    /**
     * Questa classe serve a rappresentare la singola cella della scacchiera "fisica"
     */
    private class Tile extends JPanel {
        private final int tileId;

        private Color lightTileColor = Color.decode("#FFFACD");
        private Color darkTileColor = Color.decode("#593E1A");

        /**
         * Questo costruttore oltre a occuparsi di disegnare e impostare gli attributi, gestisce anche il click e lo spostamento
         * @param boardPanel scacchiera "fisica" di riferimento
         * @param tileId numero della cella. Da 0 a 63
         */
        public Tile(final BoardPanel boardPanel, final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            this.setPreferredSize(Constants.TILE_DIMENSION);
            this.setTileColor();
            this.setPieceIcon(virtualBoard);
            this.highlightTileBorder(virtualBoard);
            this.highlightUsable(virtualBoard);
            this.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Se il gioco è finito blocca le mosse
                    if(VirtualBoardUtils.isEndGame(Window.get().getVirtualBoard()))
                        return;

                    if(isRightMouseButton(e)) {
                        sourceTile = null;
                        humanMovedPiece = null;
                    } else if(isLeftMouseButton(e)) {
                        // Se l'attributo della pedina selezionata è vuoto, imposta una nuova pedina
                        // Altrimenti esegui il movimento della pedina
                        if(sourceTile == null) {
                            sourceTile = virtualBoard.getPiece(tileId);
                            humanMovedPiece = sourceTile;

                            if(humanMovedPiece == null)
                                sourceTile = null;
                        } else {
                            final Move move = MoveFactory.createMove(virtualBoard, sourceTile.getPiecePosition(), tileId);
                            final MoveTransition transition = virtualBoard.getCurrentPlayer().doMove(move);

                            if (transition.moveStatus().isDone()) {
                                virtualBoard = transition.toBoard();
                                moveLog.addMove(move);
                            }

                            sourceTile = null;
                            humanMovedPiece = null;
                        }
                    }

                    invokeLater(() -> {
                        takenPiecesPanel.redo(moveLog);
                        Window.get().moveMadeUpdate(PlayerType.HUMAN);
                        boardPanel.drawBoard(virtualBoard);
                    });
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

        /**
         * Questo metodo serve per ri-disegnare la cella dopo che è stata eseguita una mossa
         * @param board scacchiera "virtuale" di riferimento
         */
        public void drawTile(final VirtualBoard board) {
            this.setTileColor();
            this.setPieceIcon(board);
            this.highlightTileBorder(board);
            this.highlightUsable(board);
            this.validate();
            this.repaint();
        }

        /**
         * Questo metodo si occupa di assegnare il colore di background alla cella
         * L'attributo FIRST_ROW contiene 8 true corrispondenti agli indici della prima riga, il resto a false.
         * Se il numero della cella è un modulo di 2 imposta il colore chiaro, altrimenti quello scuro.
         * Questo ragionamento si applica anche all'elseIf che però controlla le righe pari, cosi da poter avere due tipi di righe diverse, intercalati
         */
        private void setTileColor() {
            if (VirtualBoardUtils.INSTANCE.FIRST_ROW.get(this.tileId)      ||
                    VirtualBoardUtils.INSTANCE.THIRD_ROW.get(this.tileId)  ||
                    VirtualBoardUtils.INSTANCE.FIFTH_ROW.get(this.tileId)  ||
                    VirtualBoardUtils.INSTANCE.SEVENTH_ROW.get(this.tileId)
            ) {
                this.setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
            } else if(VirtualBoardUtils.INSTANCE.SECOND_ROW.get(this.tileId)   ||
                    VirtualBoardUtils.INSTANCE.FOURTH_ROW.get(this.tileId)     ||
                    VirtualBoardUtils.INSTANCE.SIXTH_ROW.get(this.tileId)      ||
                    VirtualBoardUtils.INSTANCE.EIGHTH_ROW.get(this.tileId)
            ) {
                this.setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);
            }
        }

        /**
         * Questo metodo si occupa di mostrare l'immagine dell'icona che corrisponde a ogni pedina posizionata
         * @param board scacchiera virtuale con la posizione delle pedine
         */
        private void setPieceIcon(final VirtualBoard board) {
            this.removeAll();
            final Piece actualPiece = board.getPiece(this.tileId);

            if(actualPiece != null) {
                try {
                    final BufferedImage image = ImageIO.read(new File(
                            Constants.RESOURCE_BASE_PATH + "pieceIcon/" + actualPiece.getPieceUtils().toString().charAt(0) + "" + actualPiece + ".gif"));
                    this.add(new JLabel(new ImageIcon(image)));
                } catch(final IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * Questo metodo viene utilizzato per evidenziare di blu quando una cella è selezionata.
         * Altrimenti i bordi sono color grigio
         * @param board scacchiera "virtuale" di riferimento
         */
        private void highlightTileBorder(final VirtualBoard board) {
            if(sourceTile != null && sourceTile.getPieceUtils() == board.getCurrentPlayer().getUtils() && sourceTile.getPiecePosition() == this.tileId)
                setBorder(BorderFactory.createLineBorder(Color.cyan));
            else
                setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }

        /**
         * Questo metodo si occupa di aggiungere un rettangolino verde quando a una coordinata può essere posizionata
         * la pedina selezionata
         * @param board scacchiera "virtuale" di riferimento
         */
        private void highlightUsable(final VirtualBoard board) {
            for(final Move move : this.pieceUsableMoves(board)) {
                if (move.getDestinationCoordinate() == this.tileId) {
                    try {
                        this.add(new JLabel(new ImageIcon(ImageIO.read(new File(Constants.RESOURCE_BASE_PATH + "game/greenIndicator.png")))));
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * Questo metodo serve a ottenere le mosse possibili in questo momento per sapere quale mettere in rettangolino
         * @param board scacchiera "virtuale" di riferimento
         * @return una lista di mosse
         */
        private Collection<Move> pieceUsableMoves(final VirtualBoard board) {
            if(sourceTile != null && sourceTile.getPieceUtils() == board.getCurrentPlayer().getUtils())
                return sourceTile.calculateMoves(board);

            return Collections.emptyList();
        }
    }



    /**
     *
     */
    private static class TableGameAIWatcher implements Observer {

        /**
         *
         * @param o     the observable object.
         * @param arg   an argument passed to the {@code notifyObservers}
         *                 method.
         */
        @Override
        public void update(Observable o, Object arg) {
            if(Window.get().getVirtualBoard().getCurrentPlayer().getUtils().isBlack() &&
                !Window.get().getVirtualBoard().getCurrentPlayer().isInCheckMate() &&
                !Window.get().getVirtualBoard().getCurrentPlayer().isInStaleMate()) {
                final AIThinkThank thinkThank = new AIThinkThank();
                thinkThank.execute();
            }

            if (Window.get().getVirtualBoard().getCurrentPlayer().isInCheckMate()) {
                JOptionPane.showMessageDialog(Window.get().getBoardPanel(),
                        "Game Over: Giocatore " + Window.get().getVirtualBoard().getCurrentPlayer() + " è sotto scacco matto!", "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            if (Window.get().getVirtualBoard().getCurrentPlayer().isInStaleMate()) {
                JOptionPane.showMessageDialog(Window.get().getBoardPanel(),
                        "Game Over: Giocatore " + Window.get().getVirtualBoard().getCurrentPlayer() + " è in una situazione di blocco (stallo)!", "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }



    /**
     *
     */
    private static class AIThinkThank extends SwingWorker<Move, String>{

        private AIThinkThank() {
        }

        /**
         * Questo metodo viene chiamato dall'observer dell'AI e si occupa di eseguire
         * la logica della AI del giocatore.
         * Valuterà dunque tutte le mosse e sceglierà la migliore
         * @return
         */
        @Override
        protected Move doInBackground() {
            final StockAlphaBeta strategy = new StockAlphaBeta(2);
            final Move bestMove = strategy.execute(Window.get().getVirtualBoard());

            return bestMove;
        }

        /**
         *
         */
        @Override
        protected void done() {
            try {
                final Move bestMove = get();
                Window.get().updateComputerMove(bestMove);
                Window.get().updateGameBoard(Window.get().getVirtualBoard().getCurrentPlayer().doMove(bestMove).toBoard());
                Window.get().getMoveLog().addMove(bestMove);
                Window.get().getTakenPiecesPanel().redo(Window.get().getMoveLog());
                Window.get().getBoardPanel().drawBoard(Window.get().getVirtualBoard());
                Window.get().moveMadeUpdate(PlayerType.COMPUTER);
            } catch(final Exception e) {
                e.printStackTrace();
            }
        }
    }



    /**
     *
     */
    private enum BoardDirection {
        NORMAL {
            @Override
            List<Tile> traverse(final List<Tile> boardTiles) {
                return boardTiles;
            }

            @Override
            BoardDirection opposite() {
                return FLIPPED;
            }
        },
        FLIPPED {
            @Override
            List<Tile> traverse(final List<Tile> boardTiles) {
                return Lists.reverse(boardTiles);
            }

            @Override
            BoardDirection opposite() {
                return NORMAL;
            }
        };

        /**
         * Questo metodo serve a invertire la board
         * @param boardTiles celle della scacchiera fisica
         * @return lista di celle ordinate
         */
        abstract List<Tile> traverse(final List<Tile> boardTiles);

        /**
         * Questo metodo serve a trovare l'opposto della configurazione attuale
         * @return l'opposto. ex: NORMAL --> FLIPPED
         */
        abstract BoardDirection opposite();
    }

}
