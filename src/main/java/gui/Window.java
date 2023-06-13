package gui;

import com.google.common.collect.Lists;
import core.move.*;
import core.board.VirtualBoard;
import core.board.VirtualBoardUtils;
import core.pieces.piece.Piece;
import core.player.ai.PlayerType;
import core.player.ai.StockAlphaBeta;
import lombok.Getter;
import lombok.Setter;
import util.Configuration;
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
import static util.Constants.RESOURCE_BASE_PATH;
import static util.Constants.SEARCH_DEPTH;

/**
 * Questa classe rappresenta la GUI e i suoi elementi collegati.
 */
@Getter
@Setter
public final class Window extends Observable {
    private final JFrame windowFrame;
    private final MoveLog moveLog;
    private final TakenPiecesPanel takenPiecesPanel;
    private final DrawingGame drawingGame;
    private final GameSetup gameSetup;

    private VirtualBoard virtualBoard;
    private BoardDirection boardDirection;
    private Piece sourceTile;
    private Piece humanMovedPiece;
    private BoardPanel boardPanel;
    private Move computerMove;
    private boolean highlightLegalMoves;
    private String pieceIconPath;

    private static final Window INSTANCE = new Window();

    private Window() {
        this.windowFrame = new JFrame(Constants.WINDOW_TITLE);
        final JMenuBar tableMenuBar = new JMenuBar();
        this.populateMenuBar(tableMenuBar);
        this.windowFrame.setJMenuBar(tableMenuBar);
        this.windowFrame.setLayout(new BorderLayout());
        this.virtualBoard = Configuration.isDrawingMode ? VirtualBoard.clearVirtualBoard() : VirtualBoard.getDefaultBoard();
        this.boardDirection = BoardDirection.NORMAL;
        this.highlightLegalMoves = false;
        this.boardPanel = new BoardPanel();
        this.moveLog = new MoveLog();
        this.pieceIconPath = "src/main/resources/icons/fancy/";
        this.takenPiecesPanel = new TakenPiecesPanel();
        this.drawingGame = new DrawingGame(this.windowFrame, true);
        this.gameSetup = new GameSetup(this.windowFrame, true);
        this.addObserver(new WindowGameAIWatcher());
        this.windowFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
        this.windowFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.windowFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.windowFrame.setSize(Constants.WINDOW_DIMENSION);
        this.windowFrame.setVisible(true);
    }

    public static Window get() {
        return INSTANCE;
    }

    /**
     * Questo metodo è l'entry point della scacchiera.
     * Qui tutte le cose vengono create
     */
    public void start() {
        Window.get().getMoveLog().clear();
        Window.get().getTakenPiecesPanel().redo(Window.get().getMoveLog());
        Window.get().getBoardPanel().drawBoard(Window.get().getVirtualBoard());
    }

    /**
     * Questo metodo serve per aggiornare le informazioni degli observers
     * su chi tocca a giocare
     * @param playerType
     */
    private void moveMadeUpdate(final PlayerType playerType) {
        setChanged();
        notifyObservers(playerType);
    }

    /**
     * Questo metodo serve ad aggiornare la scacchiera virtuale
     * È UN WRAPPER PER IL SETTER
     * @param board
     */
    private void updateGameBoard(final VirtualBoard board) {
        this.virtualBoard = board;
    }

    /**
     * Questo metodo serve a impostare la mossa effettuata dall'AI
     * È UN WRAPPER AL SETTER
     * @param move
     */
    private void updateComputerMove(final Move move) {
        this.computerMove = move;
    }

    /**
     * Questo metodo serve a chiamare tutti gli altri metodi che creano a loro volta
     * le voci del menu
     * @param tableMenuBar menu
     */
    private void populateMenuBar(final JMenuBar tableMenuBar) {
        tableMenuBar.add(this.createFilesMenu());
        tableMenuBar.add(this.createOptionsMenu());
        tableMenuBar.add(this.createPreferencesMenu());
    }

    /**
     *
     * @return
     */
    private JMenu createFilesMenu() {
       final JMenu filesMenu = new JMenu("File");
       filesMenu.setMnemonic(KeyEvent.VK_F);

        // Disegna situazione
        final JMenuItem drawGame = new JMenuItem("Draw game", KeyEvent.VK_D);
        drawGame.addActionListener(e -> this.drawGame());
        filesMenu.add(drawGame);

       return filesMenu;
    }

    /**
     * Questo metodo serve per creare il menu chiamato "OPZIONI"
     * @return menu popolato con tutte le sue voci
     */
    private JMenu createOptionsMenu() {
        final JMenu optionsMenu = new JMenu("Opzioni");
        optionsMenu.setMnemonic(KeyEvent.VK_O);

        // Nuova partita
        final JMenuItem resetMenuItem = new JMenuItem("New Game", KeyEvent.VK_P);
        resetMenuItem.addActionListener(e -> this.undoAllMoves());
        optionsMenu.add(resetMenuItem);

        // Annullare ultima mossa
        final JMenuItem undoLastMove = new JMenuItem("Annulla ultima mossa", KeyEvent.VK_Z);
        undoLastMove.addActionListener(e -> {
            if(Window.get().getMoveLog().size() > 0)
                this.undoLastMove();
        });
        optionsMenu.add(undoLastMove);

        return optionsMenu;
    }

    /**
     * Questo metodo serve per creare il menu chiamato "PREFERENZE"
     * @return menu popolato con tutte le sue voci
     */
    private JMenu createPreferencesMenu() {
        final JMenu preferencesMenu = new JMenu("Preferenze");
        preferencesMenu.setMnemonic(KeyEvent.VK_P);

        // Scegli il colore della board
        final JMenu colorChooserSubMenu = new JMenu("Scegli i colori della scacchiera");
        colorChooserSubMenu.setMnemonic(KeyEvent.VK_S);

        final JMenuItem chooseDarkMenuItem = new JMenuItem("Colore celle scure");
        colorChooserSubMenu.add(chooseDarkMenuItem);

        final JMenuItem chooseLightMenuItem = new JMenuItem("Colore celle chiare");
        colorChooserSubMenu.add(chooseLightMenuItem);

        preferencesMenu.add(colorChooserSubMenu);

        // Scelta del set d'icone da usare
        final JMenu chessMenChoiceSubMenu = new JMenu("Set di icone");

        final JMenuItem holyWarriorsMenuItem = new JMenuItem("Holy Warriors");
        chessMenChoiceSubMenu.add(holyWarriorsMenuItem);

        final JMenuItem fancyMan = new JMenuItem("Fancy");
        chessMenChoiceSubMenu.add(fancyMan);

        final JMenuItem abstractMenMenuItem = new JMenuItem("Abstract Men");
        chessMenChoiceSubMenu.add(abstractMenMenuItem);

        holyWarriorsMenuItem.addActionListener(e -> {
            pieceIconPath = "src/main/resources/icons/holywarriors/";
            Window.get().getBoardPanel().drawBoard(virtualBoard);
            Window.get().getTakenPiecesPanel().redo(Window.get().getMoveLog());
        });

        fancyMan.addActionListener(e -> {
            pieceIconPath = "src/main/resources/icons/fancy/";
            Window.get().getBoardPanel().drawBoard(virtualBoard);
            Window.get().getTakenPiecesPanel().redo(Window.get().getMoveLog());
        });

        abstractMenMenuItem.addActionListener(e -> {
            pieceIconPath = "src/main/resources/icons/simple/";
            Window.get().getBoardPanel().drawBoard(virtualBoard);
            Window.get().getTakenPiecesPanel().redo(Window.get().getMoveLog());
        });

        preferencesMenu.add(chessMenChoiceSubMenu);

        chooseDarkMenuItem.addActionListener(e -> {
            final Color colorChoice = JColorChooser.showDialog(Window.get().getWindowFrame(), "Choose Dark Tile Color",
                    Window.get().getWindowFrame().getBackground());
            if (colorChoice != null) {
                Window.get().getBoardPanel().setTileDarkColor(this.virtualBoard, colorChoice);
            }
        });

        chooseLightMenuItem.addActionListener(e -> {
            final Color colorChoice = JColorChooser.showDialog(Window.get().getWindowFrame(), "Choose Light Tile Color",
                    Window.get().getWindowFrame().getBackground());
            if (colorChoice != null) {
                Window.get().getBoardPanel().setTileLightColor(this.virtualBoard, colorChoice);
            }
        });

        // Invertire la board (flip board)
        final JMenuItem flipBoardItem = new JMenuItem("Inverti scacchiera");
        flipBoardItem.addActionListener(e -> {
            this.boardDirection = this.boardDirection.opposite();
            this.boardPanel.drawBoard(this.virtualBoard);
        });
        preferencesMenu.add(flipBoardItem);

        // Configurare la scacchiera
        final JMenuItem gameSetup = new JMenuItem("Impostazioni");
        gameSetup.addActionListener(e -> {
            Window.get().getGameSetup().promptUser();

        });
        preferencesMenu.add(gameSetup);

        // Aggiungi una riga per dividere il menu
        preferencesMenu.addSeparator();

        // Evidenzia le mosse possibili
        final JCheckBoxMenuItem cbLegalMoveHighlighter = new JCheckBoxMenuItem(
                "Mostra aiuto mosse", false);

        cbLegalMoveHighlighter.addActionListener(e -> highlightLegalMoves = cbLegalMoveHighlighter.isSelected());

        preferencesMenu.add(cbLegalMoveHighlighter);

        return preferencesMenu;
    }

    /**
     * Questo metodo viene utilizzato per annullare l'ultima mossa effettuata
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
     * Questo metodo serve per la funzionalità di disegnare una partita allo stato desiderato.
     * Il metodo si occupa di pulire la board da tutte le pedine e mostrarle nel riquadro accanto
     */
    private void drawGame() {
        Configuration.isDrawingMode = true;
        this.virtualBoard = VirtualBoard.clearVirtualBoard();
        this.computerMove = null;

        Window.get().getMoveLog().clear();
        Window.get().getTakenPiecesPanel().redo(Window.get().getMoveLog());
        Window.get().getBoardPanel().drawBoard(this.virtualBoard);
    }

    /**
     * Questo metodo serve per annullare tutte le mosse eseguite.
     * Viene utilizzato come metodo per reinizializzare una partita
     */
    private void undoAllMoves() {
        for(int i = Window.get().getMoveLog().size() - 1; i >= 0; i--) {
            final Move lastMove = Window.get().getMoveLog().removeMove(Window.get().getMoveLog().size() - 1);
            this.virtualBoard = this.virtualBoard.getCurrentPlayer().unMakeMove(lastMove).toBoard();
        }

        this.computerMove = null;
        Window.get().getMoveLog().clear();
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

        /**
         * Questo metodo serve a impostare le celle scure della scacchiera con il colore desiderato
         * @param board scacchiera virtuale di riferimento
         * @param darkColor colore scuro da impostare
         */
        public void setTileDarkColor(final VirtualBoard board, final Color darkColor) {
            for (final Tile boardTile : boardTiles) {
                boardTile.setDarkTileColor(darkColor);
            }

            drawBoard(board);
        }

        /**
         * Questo metodo serve a impostare le celle chiare della scacchiera con il colore desiderato
         * @param board scacchiera virtuale di riferimento
         * @param lightColor colore chiaro da impostare
         */
        public void setTileLightColor(final VirtualBoard board, final Color lightColor) {
            for (final Tile boardTile : boardTiles) {
                boardTile.setLightTileColor(lightColor);
            }

            drawBoard(board);
        }
    }



    /**
     * Questa classe serve a rappresentare la singola cella della scacchiera "fisica"
     */
    private class Tile extends JPanel {
        private final int tileId;

        @Setter
        private Color lightTileColor = Color.decode("#FFFACD");
        @Setter
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
                    if(!Configuration.isDrawingMode) {
                        // Se il gioco è finito blocca le mosse
                        if (VirtualBoardUtils.isEndGame(Window.get().getVirtualBoard()))
                            return;

                        if (isRightMouseButton(e)) {
                            sourceTile = null;
                            humanMovedPiece = null;
                        } else if (isLeftMouseButton(e)) {
                            // Se l'attributo della pedina selezionata è vuoto, imposta una nuova pedina
                            // Altrimenti esegui il movimento della pedina
                            if (sourceTile == null) {
                                sourceTile = virtualBoard.getPiece(tileId);
                                humanMovedPiece = sourceTile;

                                if (humanMovedPiece == null)
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
                    } else {
                        if(isLeftMouseButton(e)) {
                            Window.get().getDrawingGame().promptUser(tileId);
                        }
                    }
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
                            pieceIconPath +  "" + actualPiece.getPieceUtils().toString().charAt(0) + "" + actualPiece + ".gif"));
                    this.add(new JLabel(new ImageIcon(image)));
                } catch(final IOException e) {
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
            if(isHighlightLegalMoves()) {
                for (final Move move : this.pieceUsableMoves(board)) {
                    if (move.getDestinationCoordinate() == this.tileId) {
                        try {
                            this.add(new JLabel(new ImageIcon(ImageIO.read(new File(RESOURCE_BASE_PATH + "icons/misc/greenIndicator.png")))));
                        } catch (final IOException e) {
                            e.printStackTrace();
                        }
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
    private static class WindowGameAIWatcher implements Observer {

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
            final StockAlphaBeta strategy = new StockAlphaBeta(SEARCH_DEPTH);
            return strategy.execute(Window.get().getVirtualBoard());
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
