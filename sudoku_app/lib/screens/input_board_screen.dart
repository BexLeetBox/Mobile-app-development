import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:sudoku_app/models/difficulty.dart';
import 'package:sudoku_app/widgets/sudoku_cell_widget.dart';



class InputBoardScreen extends StatefulWidget {
  final Difficulty difficulty;

  const InputBoardScreen({Key? key, required this.difficulty}) : super(key: key);

  @override
  createState() => _InputBoardScreenState();
}

class _InputBoardScreenState extends State<InputBoardScreen> {
  final _formKey = GlobalKey<FormState>();
  List<List<int>> startingBoard = List.generate(9, (_) => List.generate(9, (_) => 0));

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
      int number = startingBoard[row][i];
      if (number != 0) {
        if (seen[number - 1]) return false; // Number already seen in this row
        seen[number - 1] = true;
      }
    }
    return true;
  }

  bool isColumnValid(int column) {
    List<bool> seen = List.filled(9, false);
    for (int i = 0; i < 9; i++) {
      int number = startingBoard[i][column];
      if (number != 0) {
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
        int number = startingBoard[boxStartRow + row][boxStartCol + col];
        if (number != 0) {
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
            onPressed: _saveBoard,
          ),
        ],
      ),
      body: Column(
        children: [
          Expanded(
            child: Center(
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
                        int newNumber = newValue ?? 0;
                        setState(() {
                          startingBoard[row][col] = newNumber;
                          //TODO remove when finishing project
                          printCurrentGridValues();
                        });
                      },
                      onChanged: (newValue) {
                        int newNumber = newValue ?? 0;
                        setState(() {
                          startingBoard[row][col] = newNumber;
                        });
                        printCurrentGridValues();
                      },
                    );
                  },
                ),
              ),
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: ElevatedButton(
              onPressed: _saveBoard,
              style: ElevatedButton.styleFrom(
                minimumSize: const Size(160, 50), // Full-width button with a fixed height
                padding: const EdgeInsets.symmetric(horizontal: 12.0, vertical: 8.0),
              ),
              child: const Text('Save Board'),
            ),
          ),
        ],
      ),
    );
  }


  @override
  void initState() {
    super.initState();
    // Load a known valid Sudoku solution into startingBoard
    startingBoard = [
      [5, 3, 4, 6, 7, 8, 9, 1, 2],
      [6, 7, 2, 1, 9, 5, 3, 4, 8],
      [1, 9, 8, 3, 4, 2, 5, 6, 7],
      [8, 5, 9, 7, 6, 1, 4, 2, 3],
      [4, 2, 6, 8, 5, 3, 7, 9, 1],
      [7, 1, 3, 9, 2, 4, 8, 5, 6],
      [9, 6, 1, 5, 3, 7, 2, 8, 4],
      [2, 8, 7, 4, 1, 9, 6, 3, 5],
      [3, 4, 5, 2, 8, 6, 1, 7, 9],
    ];
    // Immediately check if the startingBoard is valid
    bool valid = isBoardValid();
    print('Is the known solution valid? $valid'); // This should print true
  }


  void _saveBoard() async {
    printCurrentGridValues();
    if (isBoardValid()) {
      // Save the board because it's valid
      // ... existing save logic ...

      // Display a success message with green background
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Board saved successfully for ${widget.difficulty.toString().split('.').last} difficulty.'),
          backgroundColor: Colors.green, // Success message in green
        ),
      );
    } else {
      // Notify the user that the board is invalid with a red background
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Invalid board configuration. Please correct it before saving.'),
          backgroundColor: Colors.red, // Error message in red
        ),
      );
    }
  }





  Future<void> _loadBoard() async {
    final prefs = await SharedPreferences.getInstance();
    final String? boardString = prefs.getString('sudoku_board');
    final String? difficultyString = prefs.getString('sudoku_difficulty');

    if (boardString != null && difficultyString != null) {
      List<List<int>> loadedBoard = (jsonDecode(boardString) as List)
          .map((list) => (list as List).map((item) => item as int).toList())
          .toList();
      Difficulty loadedDifficulty = Difficulty.values.firstWhere(
            (d) => d.toString() == difficultyString,
        orElse: () => Difficulty.easy,
      );

      // Now set your loadedBoard and loadedDifficulty to your state
      setState(() {
        startingBoard = loadedBoard;
        // Set any other state you need to with loadedDifficulty
      });
    }
  }



  void printCurrentGridValues() {
    for (var row in startingBoard) {
      print(row); // This will print each row of the grid
    }
    print("-----------------------"); // Just to separate each print statement for clarity
  }

}


