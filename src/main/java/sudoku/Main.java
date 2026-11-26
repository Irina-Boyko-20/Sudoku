package sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public Main() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/window.fxml")
        );

        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        primaryStage.setTitle("Game in Sudoku");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

        /*sudoku.SudokuServiceImpl service = new sudoku.SudokuServiceImpl();
        service.createBoard();*/

        /*SudokuFrame frame = new SudokuFrame();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.createGUI();
            }
        });*/
    }
}
