
import 'package:flutter/material.dart';

class SudokuCellWidget extends StatelessWidget {
  final int number;
  final bool isEditable;
  final Function(int) onNumberSelected;

  const SudokuCellWidget({
    Key? key,
    required this.number,
    this.isEditable = true,
    required this.onNumberSelected,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: isEditable ? () => _onCellTap(context) : null,
      child: Container(
        decoration: BoxDecoration(
          border: Border.all(color: Colors.black),
          color: isEditable ? Colors.white : Colors.grey[300],
        ),
        child: Center(
          child: Text(
            number != 0 ? number.toString() : '',
            style: TextStyle(
              color: isEditable ? Colors.black : Colors.grey[600],
            ),
          ),
        ),
      ),
    );
  }

  void _onCellTap(BuildContext context) {
    // TODO: Implement your number input logic, e.g., show a number picker dialog
    // and then call onNumberSelected with the selected number.
    // For simplicity, let's say the user selected the number 5:
    onNumberSelected(5);
  }
}
