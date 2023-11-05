import 'package:flutter/cupertino.dart';
import 'package:sudoku_app/widgets/sudoku_cell_widget.dart';

import '../model/sudoku_board.dart';

class SudokuBoardWidget extends StatelessWidget {
  final SudokuBoard board;

  const SudokuBoardWidget({Key? key, required this.board}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GridView.builder(
      gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
        crossAxisCount: 9,
        childAspectRatio: 1.0,
      ),
      itemCount: 81,
      itemBuilder: (context, index) {
        int row = index ~/ 9;
        int col = index % 9;
        return SudokuCellWidget(
          number: board.board[row][col],
          onNumberSelected: (selectedNumber) {
            // Here you handle the event when a number is selected for a cell
            // For example, updating the board's data
            // Make sure you update the board in a way that triggers a rebuild
            // to reflect the new state, such as using setState in a StatefulWidget
          },
        );
      },
    );
  }
}
