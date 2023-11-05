import 'package:flutter/material.dart';
import 'package:sudoku_app/utils/sudoku_generator.dart';
import 'package:sudoku_app/widgets/sudoku_board_widget.dart';

import 'model/sudoku_board.dart';


void main() {
  runApp(const SudokuApp());
}

class SudokuApp extends StatelessWidget {
  const SudokuApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Sudoku App',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const SudokuHomePage(),
    );
  }
}

class SudokuHomePage extends StatefulWidget {
  const SudokuHomePage({Key? key}) : super(key: key);

  @override
  State<SudokuHomePage> createState() => _SudokuHomePageState();
}

class _SudokuHomePageState extends State<SudokuHomePage> {
  late SudokuBoard board;

  @override
  void initState() {
    super.initState();
    board = SudokuGenerator.generateBoard(); // Pass the difficulty level if needed
  }

  void _resetBoard() {
    setState(() {
      board = SudokuGenerator.generateBoard(); // Pass the difficulty level if needed
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Sudoku'),
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: _resetBoard,
          ),
        ],
      ),
      body: SudokuBoardWidget(board: board),
    );
  }
}
