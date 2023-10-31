import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:sudoku_app/utils/sudoku_generator.dart';
import 'package:sudoku_app/widgets/sudoku_board_widget.dart';

import 'model/sudoku_board.dart';

void main() {
  runApp(SudokuApp());
}

class SudokuApp extends StatelessWidget {
  const SudokuApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Sudoku App',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: SudokuHomePage(),
    );
  }
}

class SudokuHomePage extends StatelessWidget {
  const SudokuHomePage({super.key});

  @override
  Widget build(BuildContext context) {
    SudokuBoard board = SudokuGenerator.generateBoard();

    return Scaffold(
      appBar: AppBar(
        title: const Text('Sudoku'),
      ),
      body: Center(
        child: SudokuBoardWidget(board: board),
      ),
    );
  }
}
