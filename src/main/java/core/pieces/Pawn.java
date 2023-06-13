package core.pieces;

import core.board.VirtualBoard;
import core.move.*;
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
        super(PieceType.PAWN, piecePosition, pieceUtils, isFirstMove, PieceType.PAWN.getDrawFileName());
    }

    /**
     * Questo costruttore viene utilizzato quando viene deserializzato il file e di conseguenza instantiazo l'oggetto
     * @param pieceCoordinate coordinata sulla quale posizionata la torre. ex: a5
     * @param pieceUtils Utility della pedina. Gli utility sono dei metodi o caratteristiche di un gruppo di pedine.
     *                   Ad esempio se la pedina è bianca o nera. Immagazzinare chi fosse il colore avversario,...
     * @param isFirstMove valore booleano che indica se è la prima mossa del pedone
     */
    public Pawn(final String pieceCoordinate, final Utils pieceUtils, final boolean isFirstMove) {
        super(PieceType.PAWN, pieceCoordinate, pieceUtils, isFirstMove, PieceType.PAWN.getDrawFileName());
    }

    /**
     * Questo costruttore viene utilizzato quando per il pedone sarà la sua prima mossa
     * @param piecePosition coordinata sulla quale è posizionata la torre
     * @param pieceUtils Utility della pedina. Gli utility sono dei metodi o caratteristiche di un gruppo di pedine.
     *                   Ad esempio se la pedina è bianca o nera. Immagazzinare chi fosse il colore avversario,...
     */
    public Pawn(final int piecePosition, final Utils pieceUtils) {
        super(PieceType.PAWN, piecePosition, pieceUtils, true, PieceType.PAWN.getDrawFileName());
    }

    /**
     * Questo metodo viene utilizzato per rilevare quali sono le mosse possibili per la pedina
     * @param board scacchiera virtuale di riferimento
     * @return Una lista di mosse praticabili dalla pedina allo stato corrente
     */
    @Override
    public Collection<Move> calculateMoves(final VirtualBoard board) {
        final List<Move> usableMoves = new ArrayList<>();

        for(final int candidateOffset : OPERATION_MOVE) {
            int candidateCoordinate = this.piecePosition + (this.pieceUtils.getDirection() * candidateOffset);

            if(!VirtualBoardUtils.isValidTileCoordinate(candidateCoordinate))
                continue;

            if(candidateOffset == 8 && board.getPiece(candidateCoordinate) == null) {
                if(this.pieceUtils.isPawnPromotionSquare(candidateCoordinate)) {
                    usableMoves.add(new PawnPromotion(new PawnMove(board, this, candidateCoordinate), PieceUtils.INSTANCE.getPieceAtCoordinate(Queen.class, this.pieceUtils, candidateCoordinate)));
                    usableMoves.add(new PawnPromotion(new PawnMove(board, this, candidateCoordinate), PieceUtils.INSTANCE.getPieceAtCoordinate(Rook.class, this.pieceUtils, candidateCoordinate)));
                    usableMoves.add(new PawnPromotion(new PawnMove(board, this, candidateCoordinate), PieceUtils.INSTANCE.getPieceAtCoordinate(Bishop.class, this.pieceUtils, candidateCoordinate)));
                    usableMoves.add(new PawnPromotion(new PawnMove(board, this, candidateCoordinate), PieceUtils.INSTANCE.getPieceAtCoordinate(Knight.class, this.pieceUtils, candidateCoordinate)));
                } else {
                    usableMoves.add(new PawnMove(board, this, candidateCoordinate));
                }
            } else if(candidateOffset == 16 && this.isFirstMove() && ((VirtualBoardUtils.INSTANCE.SECOND_ROW.get(this.piecePosition)
                    && this.pieceUtils.isBlack()) || (VirtualBoardUtils.INSTANCE.SEVENTH_ROW.get(this.piecePosition)
                    && this.pieceUtils.isWhite()))) {
                final int behindCandidateCoordinate = this.piecePosition + (this.pieceUtils.getDirection() * 8);

                if(board.getPiece(candidateCoordinate) == null && board.getPiece(behindCandidateCoordinate) == null)
                    usableMoves.add(new PawnJump(board, this, candidateCoordinate));
            } else if (candidateOffset == 7 && !((VirtualBoardUtils.INSTANCE.EIGHTH_COLUMN.get(this.piecePosition)
                    && this.pieceUtils.isWhite()) || (VirtualBoardUtils.INSTANCE.FIRST_COLUMN.get(this.piecePosition)
                    && this.pieceUtils.isBlack()))) {
                if(board.getPiece(candidateCoordinate) != null) {
                    final Piece pieceAtDestination = board.getPiece(candidateCoordinate);

                    if(this.pieceUtils != pieceAtDestination.getPieceUtils()) {
                        if(this.pieceUtils.isPawnPromotionSquare(candidateCoordinate)) {
                            usableMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateCoordinate, pieceAtDestination), PieceUtils.INSTANCE.getPieceAtCoordinate(Queen.class, this.pieceUtils, candidateCoordinate)));
                            usableMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateCoordinate, pieceAtDestination), PieceUtils.INSTANCE.getPieceAtCoordinate(Rook.class, this.pieceUtils, candidateCoordinate)));
                            usableMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateCoordinate, pieceAtDestination), PieceUtils.INSTANCE.getPieceAtCoordinate(Bishop.class, this.pieceUtils, candidateCoordinate)));
                            usableMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateCoordinate, pieceAtDestination), PieceUtils.INSTANCE.getPieceAtCoordinate(Knight.class, this.pieceUtils, candidateCoordinate)));
                        } else {
                            usableMoves.add(new PawnAttackMove(board, this, candidateCoordinate, pieceAtDestination));
                        }
                    }
                } else if(board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePosition() ==
                        (this.piecePosition + (this.pieceUtils.getOppositeDirection()))) {
                    final Piece pieceAtDestination = board.getEnPassantPawn();

                    if(this.pieceUtils != pieceAtDestination.getPieceUtils())
                        usableMoves.add(new PawnEnPassantAttack(board, this, candidateCoordinate, pieceAtDestination));
                }
            } else if (candidateOffset == 9 && !((VirtualBoardUtils.INSTANCE.FIRST_COLUMN.get(this.piecePosition)
                    && this.pieceUtils.isWhite()) || (VirtualBoardUtils.INSTANCE.EIGHTH_COLUMN.get(this.piecePosition)
                    && this.pieceUtils.isBlack()))) {
                if(board.getPiece(candidateCoordinate) != null) {
                    if (this.pieceUtils != board.getPiece(candidateCoordinate).getPieceUtils()) {
                        if (this.pieceUtils.isPawnPromotionSquare(candidateCoordinate)) {
                            usableMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateCoordinate, board.getPiece(candidateCoordinate)), PieceUtils.INSTANCE.getPieceAtCoordinate(Queen.class, this.pieceUtils, candidateCoordinate)));
                            usableMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateCoordinate, board.getPiece(candidateCoordinate)), PieceUtils.INSTANCE.getPieceAtCoordinate(Rook.class, this.pieceUtils, candidateCoordinate)));
                            usableMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateCoordinate, board.getPiece(candidateCoordinate)), PieceUtils.INSTANCE.getPieceAtCoordinate(Bishop.class, this.pieceUtils, candidateCoordinate)));
                            usableMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateCoordinate, board.getPiece(candidateCoordinate)), PieceUtils.INSTANCE.getPieceAtCoordinate(Knight.class, this.pieceUtils, candidateCoordinate)));
                        } else {
                            usableMoves.add(new PawnAttackMove(board, this, candidateCoordinate, board.getPiece(candidateCoordinate)));
                        }
                    }
                } else if (board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePosition() ==
                        (this.piecePosition - (this.pieceUtils.getOppositeDirection()))) {
                    final Piece pieceAtDestination = board.getEnPassantPawn();

                    if (this.pieceUtils != pieceAtDestination.getPieceUtils())
                        usableMoves.add(new PawnEnPassantAttack(board, this, candidateCoordinate, pieceAtDestination));
                }
            }
        }

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

    /**
     * Questo metodo serve per ritornare un numero intero che indica il bonus
     * del pedone per la coordinata e il tipo.
     * Questo valore viene usato principalmente dall'AI per valutare la scacchiera
     * Tutto questo ha poi una teoria che spiegherò nella wiki github
     *
     * @return numero intero positivo o negativo
     */
    @Override
    public int locationBonus() {
        return this.pieceUtils.pawnBonus(this.piecePosition);
    }

    /**
     * @return il carattere identificativo di ogni pedina. Ogni tipo di pedina ha il suo
     */
    @Override
    public String toString() {
        return this.pieceType.toString();
    }
}
