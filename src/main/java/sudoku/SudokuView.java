package sudoku;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

public class SudokuView {
    private final GridPane gridPane;
    public final ComboBox<String> levelComboBox;
    private final HBox buttonPanel;
    private final TextField[][] cells = new TextField[9][9];

    public SudokuView(GridPane gridPane, ComboBox<String> levelComboBox, HBox buttonPanel) {
        this.gridPane = gridPane;
        this.levelComboBox = levelComboBox;
        this.buttonPanel = buttonPanel;
    }

    // создание поля перед игрой
    public void init() {
        // инициализация таблицы
        for (int i = 0; i < 9; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPrefWidth(50);
            gridPane.getColumnConstraints().add(colConst);

            RowConstraints rowConst = new RowConstraints();
            rowConst.setPrefHeight(50);
            gridPane.getRowConstraints().add(rowConst);
        }

        // инициализация ячеек
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField textField = new TextField();
                textField.setPrefSize(50, 50);
                textField.setStyle("-fx-alignment: center; -fx-font-size: 20px; -fx-font-weight: bold;");
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("[1-9]?")) {
                        textField.setText(oldValue);
                    }
                });

                textField.getStyleClass().add("sudoku-textField");
                cells[row][col] = textField;
                gridPane.add(textField, col, row);

                if ((col + 1) % 3 == 0 && col != 8) {
                    textField.getStyleClass().add("cell-right-border");
                }
                if ((row + 1) % 3 == 0 && row != 8) {
                    textField.getStyleClass().add("cell-bottom-border");
                }
            }
        }

        gridPane.setAlignment(Pos.TOP_CENTER);

        // инициализация кнопок с цифрами
        for (int i = 1; i <= 9; i++) {
            Button numButton = new Button(String.valueOf(i));
            numButton.setPrefSize(50, 50);
            numButton.setStyle("-fx-font-size: 16px;");

            final int number = i;
            numButton.setOnAction(e -> {
                System.out.println("Нажата цифра: " + number);
                // Здесь будет логика вставки числа в выбранную ячейку
            });

            buttonPanel.getChildren().add(numButton);
        }

        // инициализация кнопки очистки
        Button eraseButton = new Button("Clean");
        eraseButton.setPrefSize(60, 50);
        eraseButton.setStyle("-fx-background-color: #ffcccc; -fx-text-fill: red;");
        eraseButton.setOnAction(e -> {
            System.out.println("Delete!");
        });

        buttonPanel.getChildren().add(eraseButton);
        BorderPane.setMargin(buttonPanel, new Insets(0, 0, 50, 0));

        levelComboBox.getItems().addAll(Level.EASY.name(), Level.MEDIUM.name(), Level.HARD.name()); // уровни
    }

    public void updateBoardUI(Sudoku sudoku) {
        byte[][] board = sudoku.getBoard();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                byte value = board[row][col];
                TextField cell = cells[row][col];

                // Очищаем старые стили ошибок при обновлении
                cell.getStyleClass().remove("sudoku-cell-error");

                if (value == 0) {
                    // Пустая ячейка для игры
                    cell.setText("");
                    cell.setEditable(true);
                    cell.setStyle("-fx-background-color: white;");
                } else {
                    // Ячейка с подсказкой (сгенерированное число)
                    cell.setText(String.valueOf(value));
                    cell.setEditable(false); // Запрещаем менять подсказки
                    cell.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: #333333; -fx-font-weight: bold;");
                }
            }
        }
    }
}
