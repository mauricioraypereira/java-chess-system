package application;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Main {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> capturedPieces = new ArrayList<>();
		
		while (!chessMatch.getCheckMate()) {
			try {
				UI.clearScreen();
				UI.printMatch(chessMatch, capturedPieces);
				System.out.println();
				System.out.print("Posição de inicio da peça: ");
				ChessPosition source = UI.readChessPosition(scanner);
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				
				System.out.println();
				System.out.print("Posição de destino da peça: ");
				ChessPosition target = UI.readChessPosition(scanner);
				
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				
				if (capturedPiece != null) {
					capturedPieces.add(capturedPiece);
				}
				
				if (chessMatch.getPromoted() != null) {
					System.out.print("Escolha uma peça para promoção (B/H/R/Q): ");
					String type = scanner.nextLine().toUpperCase();
					
					while (!type.equals("B") 
							&& !type.equals("H") 
							&& !type.equals("R")
							&& !type.equals("Q")) {
						System.out.print("Valor inválido... Escolha uma peça para promoção (B/H/R/Q): ");
						type = scanner.nextLine().toUpperCase();
					}
					
					chessMatch.replacePromotedPiece(type);
				}
				
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				scanner.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				scanner.nextLine();
			}
		}
		
		UI.clearScreen();
		UI.printMatch(chessMatch, capturedPieces);
		
	}

}
