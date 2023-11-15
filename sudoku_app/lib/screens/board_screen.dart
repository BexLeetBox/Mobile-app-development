import 'package:flutter/material.dart';
import 'package:sudoku_app/models/difficulty.dart';

import '../model/sudoku_board.dart';
import '../utils/sudoku_generator.dart';
import '../widgets/sudoku_board_widget.dart';

class BoardScreen extends StatefulWidget {
  final Difficulty difficulty;
  final SudokuBoard? initialBoard;

  const BoardScreen({
    Key? key,
    required this.difficulty,
    this.initialBoard, // Add this line
  }) : super(key: key);


  @override
  BoardScreenState createState() => BoardScreenState();
}

class BoardScreenState extends State<BoardScreen> {
  SudokuBoard? board; // Initially null, will be set in initState

  @override
  void initState() {
    super.initState();
    if (widget.initialBoard != null) {
      // If an initial board is provided, use it
      setState(() {
        board = widget.initialBoard;
      });
    } else {
      // Otherwise, generate a new board
      _generateBoard();
    }
  }
  void _checkSolution() {
    if (!isBoardComplete()) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('The board is not complete.'),
          backgroundColor: Colors.orange,
        ),
      );
      return;
    }

    if (isBoardValid()) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Congratulations! The solution is correct.'),
          backgroundColor: Colors.green,
        ),
      );
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('The solution is not correct. Please try again.'),
          backgroundColor: Colors.red,
        ),
      );
    }
  }


  bool isBoardValid() {
    for (int i = 0; i < 9; i++) {
      if (!isRowValid(i) || !isColumnValid(i)) {
        return false;
      }
    }
    if (!areAllBoxesValid()) {
      return false;
    }
    return true;
  }

  bool isRowValid(int row) {
    List<bool> seen = List.filled(9, false);
    for (int i = 0; i < 9; i++) {
      int? number = board!.board[row][i]; // This is now a nullable int
      if (number != null) {
        if (seen[number - 1]) return false;
        seen[number - 1] = true;
      }
    }
    return true;
  }

  bool isColumnValid(int column) {
    List<bool> seen = List.filled(9, false);
    for (int i = 0; i < 9; i++) {
      int? number = board!.board[i][column];
      if (number != null) {
        if (seen[number - 1]) return false; // Number already seen in this column
        seen[number - 1] = true;
      }
    }
    return true;
  }

  bool isBoxValid(int boxStartRow, int boxStartCol) {
    List<bool> seen = List.filled(9, false);
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        int? number = board!.board[boxStartRow + row][boxStartCol + col];
        if (number != null) {
          if (seen[number - 1]) return false; // Number already seen in this 3x3 box
          seen[number - 1] = true;
        }
      }
    }
    return true;
  }

  bool areAllBoxesValid() {
    for (int row = 0; row < 9; row += 3) {
      for (int col = 0; col < 9; col += 3) {
        if (!isBoxValid(row, col)) return false;
      }
    }
    return true;
  }

  bool isBoardComplete() {
    for (var row in board!.board) {
      for (var value in row) {
        if (value == null) {
          return false;
        }
      }
    }
    return true;
  }


  Future<void> _generateBoard() async {
    // Generate the board and set it in the state
    SudokuBoard generatedBoard = await SudokuGenerator.generateBoard(widget.difficulty);
    setState(() {
      board = generatedBoard;
    });
  }

// Define the callback function that will be passed to SudokuBoardWidget
  void _onCellChanged(int row, int col, int? newValue) {
    // Update the board with the new value, which may be null
    setState(() {
      // Make sure to handle the case where newValue is null
      if (newValue != null) {
        board!.board[row][col] = newValue;
      } else {
        // Handle the cell clearing logic here, if newValue is null
        // For example, you might set it to a default value or keep it null
        board!.board[row][col] = null; // or some other default value
      }
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
      body: Column(
        children: [
          Expanded(
            child: SudokuBoardWidget(
              board: board!,
              onCellChanged: _onCellChanged,
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: ElevatedButton(
              onPressed: _checkSolution, // Call _checkSolution here
              style: ElevatedButton.styleFrom(
                minimumSize: const Size(160, 50), // Full-width button with a fixed height
                padding: const EdgeInsets.symmetric(horizontal: 12.0, vertical: 8.0),
              ),
              child: const Text('Check Solution'),
            ),

          )
        ],
      ),
    );


  }
}