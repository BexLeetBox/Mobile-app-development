// sudoku_cell_widget.dart

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

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
      style: textStyle,

      decoration: const InputDecoration(
        border: OutlineInputBorder(),
        hintText: '0',
      ),



      enabled: isEditable,
      onSaved: (value) => onSaved(int.tryParse(value ?? '')),

      inputFormatters: [
        FilteringTextInputFormatter.allow(RegExp('[1-9]')), // Allow numbers 1-9 only
        LengthLimitingTextInputFormatter(1), // Limit input to a single character

      ],

      validator: (value) {
      // Allow empty strings as a representation of '0'
        if (value == null || value.isEmpty) {
          return 'Enter a number'; // Empty field
        }
        if (int.tryParse(value)! < 1 || int.tryParse(value)! > 9) {
          return '1-9 only'; // Out of range number
        }
        return null; // No error
    },

    );
  }
}
