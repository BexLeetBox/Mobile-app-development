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


  void _saveBoard() async {
    printCurrentGridValues();



    final isValid = _formKey.currentState?.validate() ?? false;
    if (isValid) {
      _formKey.currentState?.save();

      // Convert your 2D list to a single string to save it
      final String boardString = jsonEncode(startingBoard);

      // Obtain shared preferences
      final prefs = await SharedPreferences.getInstance();

      // Save the board string along with the difficulty level
      await prefs.setString('sudoku_board', boardString);
      await prefs.setString('sudoku_difficulty', widget.difficulty.toString());

      // Show a confirmation message
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Board saved successfully')),
      );
    } else {
      // Handle the case where the board is not valid
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Please fix the errors before saving')),
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


