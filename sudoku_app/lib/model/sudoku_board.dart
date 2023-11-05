
import '../models/difficulty.dart';

class SudokuBoard {
  final List<List<int>> board;
  final Difficulty difficulty;

  // Constructor that takes both board and difficulty
  SudokuBoard({required this.board, required this.difficulty});

  // Method to generate a Sudoku board based on difficulty
  static List<List<int>> generateBoard(Difficulty difficulty) {
    // Actual logic to generate a Sudoku board based on difficulty
    // Placeholder for demonstration purposes
    return List.generate(9, (_) => List.generate(9, (_) => 0));
  }

  // Convert a SudokuBoard to a Map
  Map<String, dynamic> toMap() {
    return {
      'board': board.map((row) => row.toList()).toList(), // Deep copy of the board
      'difficulty': difficulty.toString(), // Convert enum to String
    };
  }

  // Create a SudokuBoard from a Map
  static SudokuBoard fromMap(Map<String, dynamic> map) {
    return SudokuBoard(
      // Deep copy the board data from the map
      board: (map['board'] as List)
          .map((row) => (row as List).cast<int>().toList())
          .toList(),
      difficulty: Difficulty.values.firstWhere(
            (d) => d.toString() == map['difficulty'],
      ),
    );
  }

// ... other methods and properties ...
}
