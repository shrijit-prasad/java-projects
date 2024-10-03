import java.util.Scanner;

public class SudokuSolver {

    public static final int SIZE = 9;

    // Method to check if placing a number is valid
    public static boolean isValid(int[][] board, int row, int col, int num) {
        // Check the row
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num) {
                return false;
            }
        }

        // Check the column
        for (int i = 0; i < SIZE; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }

        // Check the 3x3 box
        int boxRowStart = row - row % 3;
        int boxColStart = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i + boxRowStart][j + boxColStart] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    // Method to solve the Sudoku using backtracking
    public static boolean solveSudoku(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                // Find an empty space
                if (board[row][col] == 0) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num; // Place the number

                            // Recursively call solveSudoku
                            if (solveSudoku(board)) {
                                return true; // If solved, return true
                            }

                            // Backtrack
                            board[row][col] = 0;
                        }
                    }
                    return false; // If no number can be placed, return false
                }
            }
        }
        return true; // Solved
    }

    // Method to print the board
    public static void printBoard(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] board = new int[SIZE][SIZE];

        // Input the Sudoku puzzle
        System.out.println("Enter the Sudoku puzzle (use 0 for empty cells):");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = scanner.nextInt();
            }
        }

        if (solveSudoku(board)) {
            System.out.println("Solved Sudoku:");
            printBoard(board);
        } else {
            System.out.println("No solution exists.");
        }

        scanner.close();
    }
}
