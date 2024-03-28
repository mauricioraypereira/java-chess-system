package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	private ChessMatch chessMatch;
	
	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}
	
	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) {
		ChessPiece piece = (ChessPiece)getBoard().piece(position);
		
		return piece == null || piece.getColor() != getColor();
	}
	
	private boolean testRookCastling(Position position) {
		ChessPiece piece = (ChessPiece)getBoard().piece(position);
		return piece != null 
				&& piece instanceof Rook 
				&& piece.getColor() == getColor() 
				&& piece.getMoveCount() == 0;
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0,0);
		
		// acima
		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			possibleMoves[p.getRow()][p.getColumn()] = true;
		}
		
		// ABAIXO
		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			possibleMoves[p.getRow()][p.getColumn()] = true;
		}
		
		// esquerda
		p.setValues(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			possibleMoves[p.getRow()][p.getColumn()] = true;
		}
		
		// direita
		p.setValues(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			possibleMoves[p.getRow()][p.getColumn()] = true;
		}
		
		// noroeste
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			possibleMoves[p.getRow()][p.getColumn()] = true;
		}
		
		// nordeste
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			possibleMoves[p.getRow()][p.getColumn()] = true;
		}
		
		// sudoeste
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			possibleMoves[p.getRow()][p.getColumn()] = true;
		}
		
		// sudeste
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			possibleMoves[p.getRow()][p.getColumn()] = true;
		}
		
		// castling
		if (getMoveCount() == 0 && !chessMatch.getCheck()) {
			// castling kingside rook
			Position positionRookOne = new Position(position.getRow(), position.getColumn() + 3);
			if (testRookCastling(positionRookOne)) {
				Position p1 = new Position(position.getRow(), position.getColumn() + 1);
				Position p2 = new Position(position.getRow(), position.getColumn() + 2);
				
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
					possibleMoves[position.getRow()][position.getColumn() + 2] = true;
				}
			}
			
			// castling queenside rook
			Position positionRookTwo = new Position(position.getRow(), position.getColumn() - 4);
			if (testRookCastling(positionRookTwo)) {
				Position p1 = new Position(position.getRow(), position.getColumn() - 1);
				Position p2 = new Position(position.getRow(), position.getColumn() - 2);
				Position p3 = new Position(position.getRow(), position.getColumn() - 3);
				
				if (getBoard().piece(p1) == null 
						&& getBoard().piece(p2) == null
						&& getBoard().piece(p3) == null) {
					possibleMoves[position.getRow()][position.getColumn() - 2] = true;
				}
			}
		}
		
		return possibleMoves;
	}
}
