import 'package:flutter/material.dart';
import 'package:sudoku_app/models/difficulty.dart';

import '../model/sudoku_board.dart';
import '../utils/sudoku_generator.dart';
import '../widgets/sudoku_board_widget.dart';

class BoardScreen extends StatefulWidget {
  final Difficulty difficulty;

  const BoardScreen({Key? key, required this.difficulty}) : super(key: key);

  @override
  BoardScreenState createState() => BoardScreenState();
}

class BoardScreenState extends State<BoardScreen> {
  SudokuBoard? board; // Initially null, will be set in initState

  @override
  void initState() {
    super.initState();
    _generateBoard();
  }

  Future<void> _generateBoard() async {
    // Generate the board and set it in the state
    SudokuBoard generatedBoard = await SudokuGenerator.generateBoard(widget.difficulty);
    setState(() {
      board = generatedBoard;
    });
  }


// Define the callback function that will be passed to SudokuBoardWidget
  void _onCellChanged(int row, int col, int newValue) {
    // Update the board with the new value
    setState(() {
      board!.board[row][col] = newValue;
    });
  }



  @override
  Widget build(BuildContext context) {
    // Show a loading indicator while the board is null
    if (board == null) {
      return Scaffold(
        appBar: AppBar(
          title: const Text('Loading Sudoku...'),
        ),
        body: const Center(child: CircularProgressIndicator()),
      );
    }

    // Once the board is loaded, show it
    // Once the board is loaded, show it
    return Scaffold(
      appBar: AppBar(
        title: Text('Sudoku - ${widget.difficulty.toString().split('.').last}'),
      ),
      body: SudokuBoardWidget(
        board: board!,
        onCellChanged: _onCellChanged, // Pass the callback function here
      ),
    );
  }
}