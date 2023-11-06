import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class SudokuCellWidget extends StatefulWidget {
  final int number;
  final bool isEditable;
  final Function(int?) onSaved; // Callback to save the input number
  final TextStyle textStyle;

  const SudokuCellWidget({
    Key? key,
    required this.number,
    this.isEditable = true,
    required this.onSaved,
    required this.textStyle,
  }) : super(key: key);

  @override
  SudokuCellWidgetState createState() => SudokuCellWidgetState();
}

class SudokuCellWidgetState extends State<SudokuCellWidget> {
  bool _isUncertain = false; // State to keep track of uncertainty

  void _toggleUncertain() {
    setState(() {
      _isUncertain = !_isUncertain; // Toggle the state
    });
  }

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onDoubleTap: widget.isEditable ? _toggleUncertain : null, // Handle double tap if editable
      child: Container(
        decoration: BoxDecoration(
          color: _isUncertain ? Colors.red : Colors.white, // Change color based on uncertainty
          border: Border.all(color: Colors.black38),
        ),
        child: TextFormField(
          initialValue: widget.number == 0 ? '' : widget.number.toString(),
          keyboardType: TextInputType.number,
          textAlign: TextAlign.center,
          style: widget.textStyle.copyWith(color: _isUncertain ? Colors.white : Colors.black), // Change text color based on uncertainty
          decoration: const InputDecoration(
            border: InputBorder.none,
            hintText: '0',
          ),
          enabled: widget.isEditable,
          onSaved: (value) => widget.onSaved(int.tryParse(value ?? '')),
          inputFormatters: [
            FilteringTextInputFormatter.allow(RegExp('[1-9]')),
            LengthLimitingTextInputFormatter(1),
          ],
          validator: (value) {
            if (value == null || value.isEmpty) {
              return 'Enter a number';
            }
            if (int.tryParse(value)! < 1 || int.tryParse(value)! > 9) {
              return '1-9 only';
            }
            return null;
          },
        ),
      ),
    );
  }
}
