import 'dart:convert';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:sudoku_app/models/difficulty.dart';

import '../model/sudoku_board.dart';

class SudokuStorage {
  static const _storageKey = 'sudokuBoards';

  // Save a Sudoku board to local storage
  static Future<void> saveBoard(SudokuBoard board) async {
    final prefs = await SharedPreferences.getInstance();

    // Convert the SudokuBoard to a Map and then to a JSON string
    String boardJson = jsonEncode(board.toMap());

    // Get the current list of saved boards and add the new board
    List<String> allBoards = prefs.getStringList(_storageKey) ?? [];
    allBoards.add(boardJson);

    // Save the updated list of boards back to local storage
    await prefs.setStringList(_storageKey, allBoards);
  }

  // Load all Sudoku boards of a specific difficulty from local storage
  static Future<List<SudokuBoard>> loadBoards(Difficulty difficulty) async {
    final prefs = await SharedPreferences.getInstance();

    // Get the saved boards JSON strings and convert them to SudokuBoard objects
    List<String> boardsJson = prefs.getStringList(_storageKey) ?? [];
    List<SudokuBoard> boards = boardsJson
        .map((boardJson) => SudokuBoard.fromMap(jsonDecode(boardJson)))
        .where((board) => board.difficulty == difficulty)
        .toList();

    return boards;
  }
}
