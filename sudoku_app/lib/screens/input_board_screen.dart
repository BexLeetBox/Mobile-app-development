import 'package:flutter/material.dart';
import 'package:sudoku_app/models/difficulty.dart';
import 'package:sudoku_app/storage/sudoku_storage.dart';
import 'package:sudoku_app/widgets/sudoku_cell_widget.dart';

import '../model/sudoku_board.dart';

class InputBoardScreen extends StatefulWidget {
  const InputBoardScreen({Key? key}) : super(key: key);

  @override
  createState() => _InputBoardScreenState();
}

class _InputBoardScreenState extends State<InputBoardScreen> {
  final _formKey = GlobalKey<FormState>();
  List<List<int>> startingBoard = List.generate(9, (_) => List.generate(9, (_) => 0));
  List<List<TextEditingController>> controllers = List.generate(
    9,
        (_) => List.generate(9, (_) => TextEditingController()),
  );

  @override
  void dispose() {
    for (var row in controllers) {
      for (var controller in row) {
        controller.dispose();
      }
    }
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Input Starting Board'),
        actions: [
          IconButton(
            icon: const Icon(Icons.save),
            onPressed: () {
              if (_formKey.currentState!.validate()) {
                _formKey.currentState!.save();
                // Save the board here
              }
            },
          ),
        ],
      ),
      body: Form(
        key: _formKey,
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
              onSaved: (newValue) {
                setState(() {
                  startingBoard[row][col] = newValue ?? 0;
                });
              },
            );
          },
        ),
      ),
    );
  }

  void _saveBoard() async {
    // Assuming you have a way to get the difficulty selected by the user
    Difficulty selectedDifficulty = Difficulty.easy; // This should be set by the user
    SudokuBoard newBoard = SudokuBoard(board: startingBoard, difficulty: selectedDifficulty);
    await SudokuStorage.saveBoard(newBoard);
    // Show confirmation or navigate away
  }
}
