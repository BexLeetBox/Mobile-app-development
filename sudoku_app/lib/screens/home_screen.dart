import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../model/sudoku_board.dart';
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

  Future<void> _loadGameForDifficulty(Difficulty difficulty) async {
    final prefs = await SharedPreferences.getInstance();
    final String? boardJson = prefs.getString('sudoku_board');
    final String? savedDifficulty = prefs.getString('sudoku_difficulty');

    print('Board JSON: $boardJson');
    print('Saved Difficulty: $savedDifficulty');

    // Check if a saved game exists and matches the selected difficulty
    if (boardJson != null && savedDifficulty == difficulty.toString()) {
      // Decode the board
      SudokuBoard loadedBoard = SudokuBoard.fromJson(jsonDecode(boardJson));

      // Navigate to the BoardScreen with the loaded board
      Navigator.push(context, MaterialPageRoute(
        builder: (context) => BoardScreen(
          difficulty: difficulty,
          initialBoard: loadedBoard, // Pass the loaded board
        ),
      ));
    } else {
      // Show a message that no saved game is available
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('No saved game available for ${difficulty.toString().split('.').last} difficulty.'),
          backgroundColor: Colors.blue, // Information message in blue
        ),
      );
    }
  }
}

    // Extension to capitalize the difficulty strings for display
    extension on String {
    String capitalize() {
      return "${this[0].toUpperCase()}${substring(1).toLowerCase()}";
    }


}







































