// board_screen.dart
import 'package:flutter/material.dart';

import 'package:sudoku_app/models/difficulty.dart';

import '../model/sudoku_board.dart';

class BoardScreen extends StatelessWidget {
  final SudokuBoard board;

  BoardScreen({Key? key, required Difficulty difficulty})
      : board = SudokuBoard(difficulty: difficulty),  // Pass Difficulty to SudokuBoard
        super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Sudoku - ${board.difficulty.toString().split('.').last}'),
      ),
      body: const Center(
        // Your board display widget will go here
      ),
    );
  }
}
