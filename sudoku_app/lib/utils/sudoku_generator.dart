
import 'package:sudoku_app/models/difficulty.dart';

import '../model/sudoku_board.dart';

class SudokuGenerator {
  static SudokuBoard generateBoard(Difficulty difficulty) {
    // Generate the board here based on the difficulty
    List<List<int>> newBoard = SudokuBoard.generateBoard(difficulty);
    // Now pass the generated board and difficulty to the SudokuBoard constructor
    return SudokuBoard(board: newBoard, difficulty: difficulty);
  }
}
