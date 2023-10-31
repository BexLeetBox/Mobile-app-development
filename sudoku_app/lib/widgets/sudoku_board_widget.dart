import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../model/sudoku_board.dart';
import 'sudoku_cell_widget.dart';

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
          // Additional parameters like whether the cell is editable
          // and callbacks for when the cell is tapped can be added
        );
      },
    );
  }
}


