import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class SudokuCellWidget extends StatelessWidget {
  final int number;

  const SudokuCellWidget({Key? key, required this.number}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      // Styling for the cell
      decoration: BoxDecoration(
        border: Border.all(color: Colors.black),
      ),
      child: Center(
        child: Text(number.toString()),
      ),
    );
  }
}