package core.movements;

import core.board.VirtualBoard;

/**
 * Questa classe è pensata per essere una classe contenitore.
 * Viene utilizzata per contenere i dati relativi alla mossa(MoveTransition).
 * @param fromBoard scacchiera virtuale, allo stato iniziale
 * @param toBoard scacchiera virtuale, allo stato finale
 * @param transitionMove mossa che viene praticata dal giocatore
 * @param moveStatus Stato della mossa. Indica se la mossa è andata a buon fine o ha avuto un eccezione di gioco
 */
public record MoveTransition(VirtualBoard fromBoard, VirtualBoard toBoard, Move transitionMove, MoveStatus moveStatus) {
}