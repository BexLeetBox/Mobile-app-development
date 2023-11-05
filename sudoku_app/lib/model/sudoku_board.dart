class SudokuBoard {
  List<List<int>> board;
  List<List<int>> _board = List.generate(9, (i) => List.generate(9, (j) => 0));
  SudokuBoard(this.board);



  int getValue(int row, int col) => _board[row][col];

  void setValue(int row, int col, int value) {
    // Here you might want to include logic to check if the move is valid
    _board[row][col] = value;
  }
// Add methods for board manipulation and checking the rules here
}
