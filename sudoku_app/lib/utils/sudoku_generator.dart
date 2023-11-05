import '../model/sudoku_board.dart';
import '../models/difficulty.dart';

class SudokuGenerator {
  static SudokuBoard generateBoard(Difficulty difficulty) {
    // No need to pass a newBoard, the SudokuBoard constructor will handle the board generation
    return SudokuBoard(difficulty: difficulty);
  }
}
