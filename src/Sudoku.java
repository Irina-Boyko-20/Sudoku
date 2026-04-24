import java.util.Arrays;
import java.util.Objects;

public class Sudoku {
    private static final int SIZE = 9;
    byte[][] board = new byte[SIZE][SIZE];

    public Sudoku() {
    }

    public Sudoku(byte[][] board) {
        this.board = board;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Sudoku sudoku = (Sudoku) o;
        return Objects.deepEquals(board, sudoku.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    public void setBoard(byte[][] board) {
        this.board = board;
    }

    public byte[][] getBoard() {
        return board;
    }
}
