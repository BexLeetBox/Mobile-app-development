import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../model/sudoku_board.dart';
import '../models/difficulty.dart';
import '../utils/sudoku_generator.dart';
import '../widgets/sudoku_board_widget.dart';

class BoardScreen extends StatelessWidget {
  final SudokuBoard board;

  BoardScreen({Key? key, required Difficulty difficulty})
      : board = SudokuGenerator.generateBoard(difficulty),
        super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Sudoku - ${board.difficulty.toString().split('.').last}'),
      ),
      body: SudokuBoardWidget(board: board), // Make sure this line is correct
    );
  }
}
