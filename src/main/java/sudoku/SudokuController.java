package sudoku;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SudokuController {
    private final SudokuService sudokuService = new SudokuServiceImpl();
    private SudokuView sudokuView;

    @FXML
    private GridPane gridPane;

    @FXML
    public ComboBox<String> levelComboBox;

    @FXML
    private HBox buttonPanel;

    @FXML
    public void initialize() {
        // Создаем View, передавая ему элементы из FXML
        sudokuView = new SudokuView(gridPane, levelComboBox, buttonPanel);
        sudokuView.init();
    }

    // метод кнопки новой игры
    public void handleNewGame() {
        String level = levelComboBox.getValue();
        if (level == null) return;

        Sudoku sudoku = sudokuService.createNewBoard(level);
        sudokuView.updateBoardUI(sudoku);
    }
}
