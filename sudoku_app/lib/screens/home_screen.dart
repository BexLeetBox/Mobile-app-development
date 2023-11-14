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

    print('Board JSON: $boardJson');

    if (boardJson != null) {
      // Decode the board
      Map<String, dynamic> boardData = jsonDecode(boardJson);
      SudokuBoard loadedBoard = SudokuBoard.fromJson(boardData);

      // Extract the difficulty from the board JSON
      String? loadedDifficultyString = boardData['difficulty'];
      Difficulty loadedDifficulty = Difficulty.values.firstWhere(
            (d) => d.toString() == loadedDifficultyString,
        orElse: () => Difficulty.easy, // or handle this case appropriately
      );

      print('Loaded Difficulty: $loadedDifficulty');

      // Check if the loaded difficulty matches the selected difficulty
      if (loadedDifficulty == difficulty) {
        // Navigate to the BoardScreen with the loaded board
        Navigator.push(context, MaterialPageRoute(
          builder: (context) => BoardScreen(
            difficulty: loadedDifficulty,
            initialBoard: loadedBoard,
          ),
        ));
      } else {
        // Show a message that no saved game is available for the selected difficulty
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('No saved game available for ${difficulty.toString().split('.').last} difficulty.'),
            backgroundColor: Colors.blue,
          ),
        );
      }
    } else {
      // Show a message that no saved game is available
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('No saved game available.'),
          backgroundColor: Colors.blue,
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







































