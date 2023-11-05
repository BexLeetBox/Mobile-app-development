import 'package:flutter/material.dart';
import 'package:sudoku_app/models/difficulty.dart';
import 'package:sudoku_app/storage/sudoku_storage.dart';

import '../model/sudoku_board.dart';

class InputBoardScreen extends StatefulWidget {
  const InputBoardScreen({Key? key}) : super(key: key);

  @override
  createState() => _InputBoardScreenState();
}

class _InputBoardScreenState extends State<InputBoardScreen> {
  final _formKey = GlobalKey<FormState>();
  final List<List<TextEditingController>> _controllers;

  _InputBoardScreenState()
      : _controllers = List.generate(
    9,
        (_) => List.generate(9, (_) => TextEditingController()),
  );

  @override
  void dispose() {
    // Dispose controllers when the screen is disposed
    for (var row in _controllers) {
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
            onPressed: _saveBoard,
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
            return Padding(
              padding: const EdgeInsets.all(2.0),
              child: TextFormField(
                controller: _controllers[row][col],
                keyboardType: TextInputType.number,
                textAlign: TextAlign.center,
                decoration: const InputDecoration(
                  border: OutlineInputBorder(),
                  hintText: '0',
                ),
                onSaved: (value) {
                  int number = int.tryParse(value ?? '') ?? 0;
                  // Store the number in the board
                  // Assuming you have a method in SudokuBoard to set the value
                  // board.setValue(row, col, number);
                },
              ),
            );
          },
        ),
      ),
    );
  }

  void _saveBoard() {
    if (_formKey.currentState!.validate()) {
      _formKey.currentState!.save();
      // Convert the input from controllers into a 2D list of integers
      List<List<int>> boardNumbers = _controllers.map((row) {
        return row.map((controller) {
          return int.tryParse(controller.text) ?? 0;
        }).toList();
      }).toList();

      // Generate a new SudokuBoard with the input numbers
      SudokuBoard newBoard = SudokuBoard(
        board: boardNumbers,
        difficulty: Difficulty.medium, // You might want to let the user select this
      );

      // Save the new board using SudokuStorage
      SudokuStorage.saveBoard(newBoard).then((_) {
        // Show a confirmation message or navigate away
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Board saved successfully')),
        );
      }).catchError((error) {
        // Handle any errors here
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Failed to save board')),
        );
      });
    }
  }
}
