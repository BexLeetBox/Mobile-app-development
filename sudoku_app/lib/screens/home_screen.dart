import 'package:flutter/material.dart';

import '../models/difficulty.dart';
import 'board_screen.dart';
import 'input_board_screen.dart'; // Assume this is the screen where the user can create a new board


class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  HomeScreenState createState() => HomeScreenState();
}

class HomeScreenState extends State<HomeScreen> {
  Difficulty _selectedDifficulty = Difficulty.easy; // Default selection

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Sudoku Home'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            DropdownButton<Difficulty>(
              value: _selectedDifficulty,
              onChanged: (Difficulty? newValue) {
                setState(() {
                  _selectedDifficulty = newValue ?? Difficulty.easy;
                });
              },
              items: Difficulty.values.map((Difficulty difficulty) {
                return DropdownMenuItem<Difficulty>(
                  value: difficulty,
                  child: Text(difficulty.toString().split('.').last.capitalize()),
                );
              }).toList(),
            ),
            ElevatedButton(
              onPressed: () {
                // Logic to load a game for the selected difficulty
                _loadGameForDifficulty(_selectedDifficulty);
              },
              child: const Text('Load Game'),
            ),
            ElevatedButton(
              onPressed: () {
                // Navigate to the screen to create a new board
                Navigator.push(context, MaterialPageRoute(
                  builder: (context) => InputBoardScreen(difficulty: _selectedDifficulty),
                ));
              },
              child: const Text('Create Game'),
            ),
            // ... Keep the existing buttons for starting a game ...
          ],
        ),
      ),
    );
  }

  void _loadGameForDifficulty(Difficulty difficulty) {
    // Implement the logic to load a game for the given difficulty
    // This could involve reading from local storage or a database
    // For example:
    // SudokuBoard board = _getBoardFromStorage(difficulty);
    // Navigator.push(context, MaterialPageRoute(
    //   builder: (context) => BoardScreen(board: board),
    // ));
  }
}

// Extension to capitalize the difficulty strings for display
extension on String {
  String capitalize() {
    return "${this[0].toUpperCase()}${substring(1).toLowerCase()}";
  }
}










































