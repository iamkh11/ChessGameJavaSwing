package com.esprit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.esprit.beans.Piece;
import com.esprit.beans.PieceColor;
import com.esprit.beans.Position;
import com.esprit.components.ChessSquareComponent;
import com.esprit.utils.ChessBoard;
import com.esprit.utils.ChessGame;
import com.esprit.utils.ChessPieces;

public class ChessGameGUI extends JFrame {

    private final ChessSquareComponent[][] squares = new ChessSquareComponent[8][8];
    private final ChessGame game = new ChessGame();

    ChessPieces chessPieces = new ChessPieces();
    private Map<Class<? extends Piece>, String> pieceUnicodeMap = chessPieces.getPieceUnicodeMap();

    public ChessGameGUI() {
        setMinimumSize(new Dimension(650, 650));
        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));
        initializeBoard();
        addGameResetOption();
        pack();
        setVisible(true);
    }

    private void initializeBoard() {
        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                final int finalRow = row;
                final int finalCol = col;
                ChessSquareComponent square = new ChessSquareComponent(row, col);
                square.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleSquareClick(finalRow, finalCol);
                    }
                });
                add(square);
                squares[row][col] = square;
            }
        }
        refreshBoard();
    }

    private void refreshBoard() {
        ChessBoard board = game.getBoard();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece != null) {
                    // If using Unicode symbols:
                    String symbol = pieceUnicodeMap.get(piece.getClass());
                    Color color = (piece.getColor() == PieceColor.WHITE) ? Color.WHITE : Color.BLACK;
                    squares[row][col].setPieceSymbol(symbol, color);
                } else {
                    squares[row][col].clearPieceSymbol();
                }
            }
        }
    }

    private void handleSquareClick(int row, int col) {
        boolean moveResult = game.handleSquareSelection(row, col);
        clearHighlights();
        if (moveResult) {
            refreshBoard();
            checkGameState();
            checkGameOver();
        } else if (game.isPieceSelected()) {
            highlightLegalMoves(new Position(row, col));
        }
        refreshBoard();
    }

    private void checkGameState() {
        PieceColor currentPlayer = game.getCurrentPlayerColor();
        boolean inCheck = game.isInCheck(currentPlayer);

        if (inCheck) {
            JOptionPane.showMessageDialog(this, currentPlayer + " is in check!");
        }
    }

    private void highlightLegalMoves(Position position) {
        List<Position> legalMoves = game.getLegalMovesForPieceAt(position);
        for (Position move : legalMoves) {
            squares[move.getRow()][move.getColumn()].setBackground(new Color(80, 200, 120));
        }
    }

    private void clearHighlights() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col].setBackground((row + col) % 2 == 0 ? Color.LIGHT_GRAY : Color.DARK_GRAY);
            }
        }
    }

    private void addGameResetOption() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem resetItem = new JMenuItem("Reset");
        resetItem.addActionListener(e -> resetGame());
        gameMenu.add(resetItem);
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }

    private void resetGame() {
        game.resetGame();
        refreshBoard();
    }

    private void checkGameOver() {
        if (game.isCheckmate(game.getCurrentPlayerColor())) {
            int response = JOptionPane.showConfirmDialog(this, "Checkmate! Would you like to play again?", "Game Over",
                    JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                resetGame();
            } else {
                System.exit(0);
            }
        }
    }
}
