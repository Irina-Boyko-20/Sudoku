package sudoku;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
@NoArgsConstructor
public class SudokuServiceImpl implements SudokuService {
    Sudoku sudoku = new Sudoku();
    static Random random = new Random();
    private static final int LIMIT = 2;
    private static final int SIZE = 9;

    @Override
    public Sudoku createNewBoard(String level) {
        sudoku = new Sudoku();
        log.debug("Выбран уровень: {}", level);
        switch (level) {
            case "EASY":
                sudoku.setBoard(removeCells(createBoard(), 50));
                break;
            case "MEDIUM":
                sudoku.setBoard(removeCells(createBoard(), 35));
                break;
            case "HARD":
                sudoku.setBoard(removeCells(createBoard(), 25));
                break;
        }

        return sudoku;
    }

    private byte[][] createBoard() {
        byte[][] board = new byte[SIZE][SIZE];
        int row, col, number;
        do {
            board = new byte[SIZE][SIZE];
            int count = 0;
            while (count < 5) {
                row = random.nextInt(0, 9);
                col = random.nextInt(0, 9);
                number = random.nextInt(0, 9);
                board[row][col] = (byte) number;
                count++;
            }

        } while (!backtracking(board));

        return board;
    }

    private boolean backtracking(byte[][] board) { // метод заполнения таблицы цифрами и что задача решена
        int[] position = findAnEmptyCell(board);
        if (position == null) {
            return true;
        }

        int row = position[0];
        int col = position[1];

        for (byte k = 1; k <= 9; k++) {
            if (isValidMove(board, row, col, k, false)) {
                board[row][col] = k;

                if (backtracking(board)) {
                    return true;
                }

                board[row][col] = 0;
            }
        }

        return false;
    }

    private int[] findAnEmptyCell(byte[][] board) {
        int[] position = new int[2];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    position[0] = i;
                    position[1] = j;

                    return position;
                }
            }
        }

        return null;
    }

    private boolean isValidMove(byte[][] board, int row, int col, byte num, boolean printMessages) {
        if (!isEmpty(board, row, col)) {
            if (printMessages) log.debug("ОШИБКА! В ячейке уже есть число"); // добавить визуал в графике
            return false;
        } else if (containsNumberInRow(board, row, num) || containsNumberInColumn(board, col, num)
                || containsNumberInSquare(board, row, col, num)) {
            if (printMessages) // добавить визуал в графике
                log.debug("ОШИБКА! Число {} уже есть в ряду, столбце или квадрате. Укажи другое число.", num);
            return false;
        }

        return true;
    }

    private boolean isEmpty(byte[][] board, int row, int col) {
        return board[row][col] == 0;
    }

    private boolean containsNumberInRow(byte[][] board, int row, byte num) {
        for (int c = 0; c < board[row].length; c++) {
            if (board[row][c] == num) {
                return true;
            }
        }

        return false;
    }

    private boolean containsNumberInColumn(byte[][] board, int col, byte num) {
        for (byte[] bytes : board) {
            if (bytes[col] == num) {
                return true;
            }
        }

        return false;
    }

    private boolean containsNumberInSquare(byte[][] board, int row, int col, byte num) {
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (board[r][c] == num) {
                    return true;
                }
            }
        }

        return false;
    }

    private byte[][] removeCells(byte[][] board, int countTips) {// метод удаления цифр при формировании таблицы
        int tips = 0;
        for (byte[] row : board) {
            tips += row.length;
        }

        while (tips > countTips) {
            int row = random.nextInt(0, 9);
            int col = random.nextInt(0, 9);
            byte number = board[row][col];
            if (board[row][col] != 0) {
                board[row][col] = 0;
                tips--;
            }

            if (!hasUniqueSolution(board)) {
                board[row][col] = number;
                tips++;
            }
        }

        return board;
    }

    private boolean hasUniqueSolution(byte[][] board) { // метод подтверждения, что решение уникально
        int solutions = countSolutions(board);
        return solutions == 1;
    }

    private int countSolutions(byte[][] board) { // метод для проверки уникальности
        int[] position = findAnEmptyCell(board);
        int count = 0;
        int row;
        int col;
        if (position != null) {
            row = position[0];
            col = position[1];
        } else {
            return 1;
        }

        for (byte k = 1; k <= 9; k++) {
            if (isValidMove(board, row, col, k, false)) {
                board[row][col] = k;
                count += countSolutions(board);
                board[row][col] = 0;
                if (LIMIT <= count) {
                    break;
                }
            }
        }

        return count;
    }
}

