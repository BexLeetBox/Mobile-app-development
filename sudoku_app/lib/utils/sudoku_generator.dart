import '../model/sudoku_board.dart';
import '../models/difficulty.dart'; // Make sure the path to your Difficulty enum is correct

class SudokuGenerator {
  static SudokuBoard generateBoard(Difficulty difficulty) {
    // The Sudoku board generation logic will depend on the difficulty
    // For simplicity, let's create an empty board for now
    // and assume we will fill it based on the difficulty later

    return SudokuBoard(difficulty: difficulty);
  }
}
