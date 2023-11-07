import 'package:flutter/material.dart';
import 'package:sudoku_app/models/difficulty.dart';
import 'package:sudoku_app/widgets/sudoku_cell_widget.dart';

import '../model/sudoku_board.dart';
import '../storage/sudoku_storage.dart';

class InputBoardScreen extends StatefulWidget {
  final Difficulty difficulty;

  const InputBoardScreen({Key? key, required this.difficulty}) : super(key: key);

  @override
  createState() => _InputBoardScreenState();
}

class _InputBoardScreenState extends State<InputBoardScreen> {
  final _formKey = GlobalKey<FormState>();
  List<List<int>> startingBoard = List.generate(9, (_) => List.generate(9, (_) => 0));

  @override
  Widget build(BuildContext context) {
    // Get the screen size
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;

    // Set a maximum width and height for the Sudoku grid
    double maxBoardDimension = screenWidth > 600 ? 600 : screenWidth * 0.9;
    double maxBoardHeight = screenHeight > 600 ? 600 : screenHeight * 0.8; // Ensure it's less than the screen height

    // Calculate the size for the cells, ensuring they fit within our maximum dimensions
    double cellSize = maxBoardDimension / 9;

    // Define text style for the cells
    TextStyle cellTextStyle = TextStyle(
      fontSize: cellSize / 3, // Or any other appropriate calculation
    );

    return Scaffold(
      appBar: AppBar(
        title: const Text('Input Starting Board'),
        actions: [
          IconButton(
            icon: const Icon(Icons.save),
            onPressed: _saveBoard, // Reference to the method that saves the board
          ),
        ],
      ),
      body: Center(
        child: ConstrainedBox(
          constraints: BoxConstraints(
            maxWidth: maxBoardDimension,
            maxHeight: maxBoardHeight,
          ),
          child: GridView.builder(
            gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
              crossAxisCount: 9,
              childAspectRatio: 1.0,
            ),
            itemCount: 81,
            itemBuilder: (context, index) {
              int row = index ~/ 9;
              int col = index % 9;
              return SudokuCellWidget(
                number: startingBoard[row][col],
                isEditable: true,
                textStyle: cellTextStyle,
                onSaved: (newValue) {
                  setState(() {
                    startingBoard[row][col] = newValue ?? 0;
                  });

                },
              );
            },
          ),
        ),
      ),
    );
  }

  void _saveBoard() async {
    // TODO: Implement the save logic
    // This should involve validating the board and then saving it using SudokuStorage
    // Show a confirmation message or handle errors accordingly
    if (_formKey.currentState!.validate()) {
      _formKey.currentState!.save();
      // You might want to add logic to check if the board is a valid Sudoku board before saving
      // For now, let's just save it.
      await SudokuStorage.saveBoard(
        SudokuBoard(board: startingBoard, difficulty: widget.difficulty),
      );
      // Show confirmation or navigate away
    }
  }
}
