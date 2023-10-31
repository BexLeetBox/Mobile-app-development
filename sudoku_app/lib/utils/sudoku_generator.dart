import '../model/sudoku_board.dart';

class SudokuGenerator {
  static SudokuBoard generateBoard() {
    // This is where you'd implement the Sudoku board generation logic
    // For simplicity, let's create an empty board
    List<List<int>> board = List.generate(9, (i) => List.generate(9, (j) => 0));
    return SudokuBoard(board);
  }
}
