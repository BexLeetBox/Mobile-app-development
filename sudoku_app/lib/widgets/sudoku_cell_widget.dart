import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class SudokuCellWidget extends StatefulWidget {
  final int? number;
  final bool isEditable;
  final Function(int?) onSaved; // Callback to save the input number
  final TextStyle textStyle;
  final Function(int?) onChanged;

  const SudokuCellWidget({
    Key? key,
    required this.number, // No longer required, can be null
    this.isEditable = true,
    required this.onSaved,
    required this.textStyle,
    required this.onChanged,
  }) : super(key: key);

  @override
  SudokuCellWidgetState createState() => SudokuCellWidgetState();
}

class SudokuCellWidgetState extends State<SudokuCellWidget> {
  bool _isUncertain = false; // State to keep track of uncertainty


  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }


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
          initialValue: widget.number?.toString() ?? '',
          keyboardType: TextInputType.number,
          textAlign: TextAlign.center,
          style: widget.textStyle.copyWith(color: _isUncertain ? Colors.white : Colors.black), // Change text color based on uncertainty
          onChanged: (value) {
            int? newValue = value.isNotEmpty ? int.tryParse(value) : null;
            widget.onChanged(newValue);
          },
          decoration: const InputDecoration(
            border: InputBorder.none,
            hintText: '',
          ),
          enabled: widget.isEditable,
          //TODO considering to remove this
          /*onSaved: (value) => widget.onSaved(int.tryParse(value ?? '')),*/

          inputFormatters: [
            FilteringTextInputFormatter.allow(RegExp('[1-9]')),
            LengthLimitingTextInputFormatter(1),
          ],
          validator: (value) {
            if (value == null || value.isEmpty) {
              debugPrint("Enter a number");
              return 'Enter a number';
            }
            if (int.tryParse(value)! < 1 || int.tryParse(value)! > 9) {
              debugPrint("1-9 only");
              return '1-9 only';
            }

            debugPrint("very safe");
            return null;
          },

        ),
      ),
    );
  }
}
