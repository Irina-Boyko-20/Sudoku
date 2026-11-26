package sudoku;

import javafx.fxml.FXML;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@NoArgsConstructor
@Slf4j
public class SudokuController {
    SudokuService sudokuService;
    Sudoku sudoku = new Sudoku();

    @FXML
    private SudokuView sudokuView;

    // метод кнопки новой игры
    @FXML
    private void handleNewGame() {
        String level = sudokuView.getLevelComboBox().getValue();

        sudoku = sudokuService.createNewBoard(level);
        sudokuView.updateBoardUI(sudoku);
    }

}
