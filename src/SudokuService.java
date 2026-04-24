public interface SudokuService {
    void createBoard();

    void addNumberInTable(Sudoku sudoku, int row, int col, byte num);
}
