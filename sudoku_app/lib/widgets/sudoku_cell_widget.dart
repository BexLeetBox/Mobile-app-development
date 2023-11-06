// sudoku_cell_widget.dart

import 'package:flutter/material.dart';

class SudokuCellWidget extends StatelessWidget {
  final int number;
  final bool isEditable;
  final Function(int?) onSaved; // Callback to save the input number
  final TextStyle textStyle; // Add this line

  const SudokuCellWidget({
    Key? key,
    required this.number,
    this.isEditable = true,
    required this.onSaved,
    required this.textStyle,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    // Get the size of the screen or parent widget
    double width = MediaQuery.of(context).size.width;
    // Calculate the text size based on the width
    double textSize = width / 50; // Example calculation


    return TextFormField(
      initialValue: number == 0 ? '' : number.toString(),
      keyboardType: TextInputType.number,
      textAlign: TextAlign.center,
      style: TextStyle(
        fontSize: textSize, // Use the calculated text size
        color: Colors.black, // Text color

      ),


      decoration: const InputDecoration(
        border: OutlineInputBorder(),
        hintText: '0',
      ),



      enabled: isEditable,
      onSaved: (value) => onSaved(int.tryParse(value ?? '')),
      validator: (value) {
        if (value == null || value.isEmpty) {
          return null; // Empty is allowed for unsolved cells
        }
        if (int.tryParse(value) == null) {
          return 'Enter a number'; // Not a number
        }
        if (int.tryParse(value)! < 1 || int.tryParse(value)! > 9) {
          return '1-9'; // Not in range
        }
        // Add more Sudoku-specific validation if needed
        return null;
      },
    );
  }
}
