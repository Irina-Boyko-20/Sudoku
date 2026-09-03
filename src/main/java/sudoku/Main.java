package sudoku;

import javax.swing.*;

class Main {
    public static void main(String[] args) {
        /*sudoku.SudokuServiceImpl service = new sudoku.SudokuServiceImpl();
        service.createBoard();*/

        SudokuFrame frame = new SudokuFrame();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.createGUI();
            }
        });
    }
}
