import java.util.Random;
import java.util.Scanner;

public class SudokuServiceImpl implements SudokuService {
    Sudoku sudoku = new Sudoku();
    static Random random = new Random();
    static Scanner scanner = new Scanner(System.in);
    private static final int LIMIT = 2;
    private static final int SIZE = 9;

    @Override
    public void createBoard() {
        byte[][] board = new byte[SIZE][SIZE];
        int row;
        int col;
        int number;
        do {
            int count = 0;
            while (count < 5) {
                row = random.nextInt(0, 9);
                col = random.nextInt(0, 9);
                number = random.nextInt(0, 9);
                board[row][col] = (byte) number;
                count++;
            }

        } while (!backtracking(board));

        sudoku.setBoard(board);
        printTable(sudoku);
        System.out.println("Выберете уровень сложность: 1-лёгкий, 2-средний, 3-сложный:");
        switch (scanner.nextInt()) {
            case 1:
                sudoku.setBoard(removeCells(board, 50));
                break;
            case 2:
                sudoku.setBoard(removeCells(board, 35));
                break;
            case 3:
                sudoku.setBoard(removeCells(board, 25));
                break;
            default:
                System.out.println("Выберете 1, 2 или 3.");
        }

        printTable(sudoku);

        do {
            System.out.println("Нужно ввести через 'Enter' сначала номер строки от 0 до 8, "
                    + "затем столбец от 0 до 8 и число от 1 до 9");
            row = scanner.nextInt();
            col = scanner.nextInt();
            number = scanner.nextByte();
            addNumberInTable(sudoku, row - 1, col - 1, (byte) number);
        } while (!valid(sudoku));

        System.out.println("Молодец! Решение судоку завершено!");
    }

    private boolean valid(Sudoku sudoku) {
        for (byte[] cow : sudoku.board) {
            for (int element : cow) {
                if (element == 0) return false;
            }
        }
        return true;
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
            if (printMessages) System.out.println("ОШИБКА! В ячейке уже есть число");
            return false;
        } else if (containsNumberInRow(board, row, num) || containsNumberInColumn(board, col, num)
                || containsNumberInSquare(board, row, col, num)) {
            if (printMessages) System.out.println("ОШИБКА! Число " + num
                    + " уже есть в ряду, столбце или квадрате. Укажи другое число.");
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

    private void printTable(Sudoku sudoku) {
        byte[][] board = sudoku.getBoard();
        System.out.print("   ");
        for (int i = 1; i < 10; i++) {
            System.out.print(i + " ");
            if ((i + 1) % 3 == 1 && i != 9) {
                System.out.print("| ");
            }
        }
        System.out.println();
        System.out.println("---------+-------+------");
        for (int row = 0; row < board.length; row++) {
            if (row % 3 == 0 && row != 0) {
                System.out.println("---------+-------+------");
            }

            System.out.print(row + 1 + "| ");

            for (int col = 0; col < board.length; col++) {
                if (board[row][col] == 0) {
                    System.out.print(". ");
                } else {
                    System.out.print(board[row][col] + " ");
                }

                if ((col + 1) % 3 == 0 && col != 8) {
                    System.out.print("| ");
                }
            }

            System.out.println();
        }

        System.out.println();
    }

    @Override
    public void addNumberInTable(Sudoku sudoku, int row, int col, byte num) {
        byte[][] board = sudoku.getBoard();
        if (isValidMove(board, row, col, num, true)) {
            board[row][col] = num;
            sudoku.setBoard(board);
            printTable(sudoku);
        }
    }
}

// попробовать сделать графический интерфейс

