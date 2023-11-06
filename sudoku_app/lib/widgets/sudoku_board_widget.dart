import 'package:flutter/material.dart';
import 'package:sudoku_app/widgets/sudoku_cell_widget.dart';
import '../model/sudoku_board.dart';

class SudokuBoardWidget extends StatelessWidget {
  final SudokuBoard board;

  const SudokuBoardWidget({Key? key, required this.board}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    // Get screen size
    var screenSize = MediaQuery.of(context).size;

    // Calculate the size of the grid
    double gridWidth = screenSize.width * 0.4; // for example, 80% of screen width
    double gridHeight = gridWidth; // To maintain the grid aspect ratio

    return Center(
      child: Container(
        width: gridWidth,
        height: gridHeight,
        padding: const EdgeInsets.all(16.0), // Add padding around the grid
        child: GridView.builder(
          gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
            crossAxisCount: 9,
            childAspectRatio: 1.0, // Cells are square-shaped
          ),
          itemCount: 81, // 9x9 Sudoku grid
          itemBuilder: (context, index) {
            int row = index ~/ 9;
            int col = index % 9;
            return SudokuCellWidget(
              number: board.board[row][col],
              isEditable: true, // or some logic to determine if the cell is editable
              onSaved: (newValue) {
                // Implement logic to handle number selection
                // and update the board state accordingly
                // This will likely require a stateful widget and calling setState
              },
            );
          },
        ),
      ),
    );
  }
}
