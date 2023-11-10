import 'package:flutter/material.dart';
import 'package:sudoku_app/widgets/sudoku_cell_widget.dart';
import '../model/sudoku_board.dart';

class SudokuBoardWidget extends StatelessWidget {
  final SudokuBoard board;
  final Function(int, int, int?) onCellChanged;

  const SudokuBoardWidget({
    Key? key,
    required this.board,
    required this.onCellChanged, // Add this line
  }) : super(key: key);


  @override
  Widget build(BuildContext context) {
    // Use media query to get screen size
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;

    // Calculate the maximum board size
    double maxBoardSize = screenWidth < screenHeight ? screenWidth : screenHeight;
    double boardSize = maxBoardSize * 0.8; // Use 80% of the smallest screen dimension
    double cellSize = boardSize / 9; // There are 9 cells in each row and column

    return Center(
      // Wrap in an AspectRatio to maintain the square shape
      child: AspectRatio(
        aspectRatio: 1,
        child: Container(
          // Apply padding to the container
          padding: const EdgeInsets.all(16.0),
          // Set a maximum size to the container
          width: boardSize,
          height: boardSize,
          child: GridView.builder(
            physics: const NeverScrollableScrollPhysics(), // Disables GridView's scrolling
            gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
              crossAxisCount: 9,
              childAspectRatio: 1, // Maintains the cell as square
            ),
            itemCount: 81, // 9x9 Sudoku grid
            itemBuilder: (context, index) {
              int row = index ~/ 9;
              int col = index % 9;
              return SudokuCellWidget(
                number: board.board[row][col],
                isEditable: true, // or some logic to determine if the cell is editable
                onChanged: (int? newValue) {
                  // Pass the new value and cell position to the onCellChanged callback
                  onCellChanged(row, col, newValue);
                },
                onSaved: (newValue) {
                  // Implement logic to handle number selection
                  // and update the board state accordingly
                  // This will likely require a stateful widget and calling setState
                },
                textStyle: TextStyle(
                  fontSize: cellSize / 3, // Adjust text size relative to cell size
                ),
              );
            },
          ),
        ),
      ),
    );
  }
}
