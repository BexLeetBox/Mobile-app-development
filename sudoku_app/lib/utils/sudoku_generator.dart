
import 'package:sudoku_app/models/difficulty.dart';

import '../model/sudoku_board.dart';
import '../storage/sudoku_storage.dart';

class SudokuGenerator {
  static Future<SudokuBoard> generateBoard(Difficulty difficulty) async {
    // Generate the board here based on the difficulty
    List<List<int>> newBoard = SudokuBoard.generateBoard(difficulty);
    // Now pass the generated board and difficulty to the SudokuBoard constructor
    // Create a new SudokuBoard with the generated board and difficulty
    SudokuBoard board = SudokuBoard(board: newBoard, difficulty: difficulty);

    // Save the board to local storage
    await SudokuStorage.saveBoard(board); // Make sure this is correct

    // Return the new SudokuBoard
    return board;
  }
}
