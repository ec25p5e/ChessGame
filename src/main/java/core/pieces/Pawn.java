package core.pieces;

import core.board.VirtualBoard;
import core.movements.*;
import core.utils.Utils;
import core.board.VirtualBoardUtils;
import core.pieces.piece.Piece;
import core.pieces.piece.PieceType;
import core.pieces.piece.PieceUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Questa classe serve per rappresentare la pedina del pedone ed estende la classe {@link Piece}
 * perché il pedone è un tipo di pedina
 */
public class Pawn extends Piece {

    // Questo array serve a contenere i valori da utilizzare durante il calcolo delle possibili mosse quando si aggiungono
    // i valori alla possibile coordinata.
    private static final int[] OPERATION_MOVE = { 8, 16, 7, 9 };

    /**
     * Questo costruttore viene utilizzato quando il pedone sarà in gioco. Quindi non obbligatoriamente sarà la sua prima mossa
     * @param piecePosition coordinata sulla quale è posizionata la torre
     * @param pieceUtils Utility della pedina. Gli utility sono dei metodi o caratteristiche di un gruppo di pedine.
     *                   Ad esempio se la pedina è bianca o nera. Immagazzinare chi fosse il colore avversario,...
     * @param isFirstMove valore booleano che indica se è la prima mossa del pedone
     */
    public Pawn(final int piecePosition, final Utils pieceUtils, final boolean isFirstMove) {
        super(PieceType.PAWN, piecePosition, pieceUtils, isFirstMove);
    }

    /**
     * Questo costruttore viene utilizzato quando per il pedone sarà la sua prima mossa
     * @param piecePosition coordinata sulla quale è posizionata la torre
     * @param pieceUtils Utility della pedina. Gli utility sono dei metodi o caratteristiche di un gruppo di pedine.
     *                   Ad esempio se la pedina è bianca o nera. Immagazzinare chi fosse il colore avversario,...
     */
    public Pawn(final int piecePosition, final Utils pieceUtils) {
        super(PieceType.PAWN, piecePosition, pieceUtils, true);
    }

    /**
     * Questo metodo viene utilizzato per rilevare quali sono le mosse possibili per la pedina
     * @param board scacchiera virtuale di riferimento
     * @return Una lista di mosse praticabili dalla pedina allo stato corrente
     */
    @Override
    public Collection<Move> calculateMoves(final VirtualBoard board) {
        final List<Move> usableMoves = new ArrayList<>();

        // Viene percorso l'array contenente i valori di calcolo
        for(final int candidateOffset : OPERATION_MOVE) {
            // Imposta la coordinata della pedina e somma la direzione da intraprendere per il valore di calcolo
            // In caso il pedone si trova alla coordinata 40 il risultato sarebbe 32, quindi la riga dopo
            int candidateCoordinate = this.piecePosition + (this.pieceUtils.getDirection() * candidateOffset);

            // Se la coordinata non è nella scacchiera non è grave, continua
            if(!VirtualBoardUtils.isValidTileCoordinate(candidateCoordinate))
                continue;

            // Movimento sulla riga successiva che sia anche vuota
            if(candidateOffset == 8 && board.getPiece(candidateCoordinate) == null) {
                // Se il pedone ha raggiunto l'altra sponda, può essere promosso con una della 4 pedine, no re e regina
                if(this.pieceUtils.isPawnPromotionSquare(candidateCoordinate)) {
                    usableMoves.add(new PawnPromotion(new PawnMove(board, this, candidateCoordinate), PieceUtils.INSTANCE.getPieceAtCoordinate(Queen.class, this.pieceUtils, candidateCoordinate)));
                    usableMoves.add(new PawnPromotion(new PawnMove(board, this, candidateCoordinate), PieceUtils.INSTANCE.getPieceAtCoordinate(Rook.class, this.pieceUtils, candidateCoordinate)));
                    usableMoves.add(new PawnPromotion(new PawnMove(board, this, candidateCoordinate), PieceUtils.INSTANCE.getPieceAtCoordinate(Bishop.class, this.pieceUtils, candidateCoordinate)));
                    usableMoves.add(new PawnPromotion(new PawnMove(board, this, candidateCoordinate), PieceUtils.INSTANCE.getPieceAtCoordinate(Knight.class, this.pieceUtils, candidateCoordinate)));
                } else
                    // Altrimenti crea una semplice mossa
                    usableMoves.add(new PawnMove(board, this, candidateCoordinate));
            }

            // Quando il pedone è alla prima mossa e posizionato sulla rispettive celle di partenza può muoversi di due righe
            else if(candidateOffset == 16 && this.isFirstMove() && ((VirtualBoardUtils.INSTANCE.SECOND_ROW.get(this.piecePosition) && this.pieceUtils.isBlack()) || (VirtualBoardUtils.INSTANCE.SEVENTH_ROW.get(this.piecePosition) && this.pieceUtils.isWhite()))) {

                // Coordinata della cella avanti la posizione di partenza e prima della destinazione
                final int behindCandidateCoordinate = this.piecePosition + (this.pieceUtils.getDirection() * 8);

                // Se la coordinata di destinazione è vuota come quella dietro, significa che il pedone può muoversi e quindi saltare via la cella in mezzo
                if(board.getPiece(candidateCoordinate) == null && board.getPiece(behindCandidateCoordinate) == null)
                    usableMoves.add(new PawnJump(board, this, candidateCoordinate));
            }

            // Quando il pedone si muove per l'attacco e non sia sulla ultima riga, altrimenti fuori dalla scacchiera
            else if (candidateOffset == 7 && !((VirtualBoardUtils.INSTANCE.EIGHTH_COLUMN.get(this.piecePosition) && this.pieceUtils.isWhite()) || (VirtualBoardUtils.INSTANCE.FIRST_COLUMN.get(this.piecePosition) && this.pieceUtils.isBlack()))) {
                // Se la coordinata di destinazione non è vuota
                if(board.getPiece(candidateCoordinate) != null) {
                    final Piece pieceAtDestination = board.getPiece(candidateCoordinate);

                    // Controlla che il pedone sia diverso dal corrente
                    if(this.pieceUtils != pieceAtDestination.getPieceUtils()) {
                        // Controlla se il pedone sarà arrivato sulla sponda addiacente e quindi promuovi il pedone
                        if(this.pieceUtils.isPawnPromotionSquare(candidateCoordinate)) {
                            usableMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateCoordinate, pieceAtDestination), PieceUtils.INSTANCE.getPieceAtCoordinate(Queen.class, this.pieceUtils, candidateCoordinate)));
                            usableMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateCoordinate, pieceAtDestination), PieceUtils.INSTANCE.getPieceAtCoordinate(Rook.class, this.pieceUtils, candidateCoordinate)));
                            usableMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateCoordinate, pieceAtDestination), PieceUtils.INSTANCE.getPieceAtCoordinate(Bishop.class, this.pieceUtils, candidateCoordinate)));
                            usableMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateCoordinate, pieceAtDestination), PieceUtils.INSTANCE.getPieceAtCoordinate(Knight.class, this.pieceUtils, candidateCoordinate)));
                        } else
                            // Altrimenti muovi il pedone con un attacco dato che deve mangiare la pedina
                            usableMoves.add(new PawnAttackMove(board, this, candidateCoordinate, pieceAtDestination));
                    }
                }

                // Se si sta facendo la mossa di enpassant analizza la destinazione se è anche essa enpassant
                else if(board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePosition() == (this.piecePosition + (this.pieceUtils.getOppositeDirection()))) {
                    final Piece pieceAtDestination = board.getEnPassantPawn();

                    // Se la destinazione è diversa dalla corrente, quindi colori diversi
                    if(this.pieceUtils != pieceAtDestination.getPieceUtils())
                        usableMoves.add(new PawnEnPassantAttack(board, this, candidateCoordinate, pieceAtDestination));
                }
            }

            // Quando il pedone si muove per l'attacco e non sia sulla ultima riga, altrimenti fuori dalla scacchiera. Questo è per quando è sulla posizione di partenza
            else if (candidateOffset == 9 && !((VirtualBoardUtils.INSTANCE.FIRST_COLUMN.get(this.piecePosition) && this.pieceUtils.isWhite()) || (VirtualBoardUtils.INSTANCE.EIGHTH_COLUMN.get(this.piecePosition) && this.pieceUtils.isBlack()))) {
                // Se la coordinata di destinazione non è vuota
                if(board.getPiece(candidateCoordinate) != null) {
                    // Controlla che il pedone sia diverso dal corrente
                    if (this.pieceUtils != board.getPiece(candidateCoordinate).getPieceUtils()) {
                        // Controlla se il pedone sarà arrivato sulla sponda addiacente e quindi promuovi il pedone
                        if (this.pieceUtils.isPawnPromotionSquare(candidateCoordinate)) {
                            usableMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateCoordinate, board.getPiece(candidateCoordinate)), PieceUtils.INSTANCE.getPieceAtCoordinate(Queen.class, this.pieceUtils, candidateCoordinate)));
                            usableMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateCoordinate, board.getPiece(candidateCoordinate)), PieceUtils.INSTANCE.getPieceAtCoordinate(Rook.class, this.pieceUtils, candidateCoordinate)));
                            usableMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateCoordinate, board.getPiece(candidateCoordinate)), PieceUtils.INSTANCE.getPieceAtCoordinate(Bishop.class, this.pieceUtils, candidateCoordinate)));
                            usableMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateCoordinate, board.getPiece(candidateCoordinate)), PieceUtils.INSTANCE.getPieceAtCoordinate(Knight.class, this.pieceUtils, candidateCoordinate)));
                        } else
                            // Altrimenti muovi il pedone con un attacco dato che deve mangiare la pedina
                            usableMoves.add(new PawnAttackMove(board, this, candidateCoordinate, board.getPiece(candidateCoordinate)));
                    }
                }

                // Se si sta facendo la mossa di enpassant analizza la destinazione se è anche essa enpassant
                else if (board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePosition() == (this.piecePosition - (this.pieceUtils.getOppositeDirection()))) {
                    final Piece pieceAtDestination = board.getEnPassantPawn();

                    // Se la destinazione è diversa dalla corrente, quindi colori diversi
                    if (this.pieceUtils != pieceAtDestination.getPieceUtils())
                        usableMoves.add(new PawnEnPassantAttack(board, this, candidateCoordinate, pieceAtDestination));
                }
            }
        }

        // Ritorna la lista completa di tutti i movimenti possibili
        return Collections.unmodifiableList(usableMoves);
    }

    /**
     * Questo metodo serve per ritornare la pedina alla coordinata in base al movimento scelto
     * @param move mossa scelta dopo aver rilevato la sorgente e la destinazione.
     * @return Una pedina della classe {@link Pawn}
     */
    @Override
    public Piece movePiece(final Move move) {
        return PieceUtils.INSTANCE.getPieceAtCoordinate(Pawn.class, move.getPieceToMove().getPieceUtils(), move.getDestinationCoordinate());
    }
}
