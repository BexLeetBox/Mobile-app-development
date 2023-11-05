import 'package:flutter/material.dart';
import 'package:sudoku_app/models/difficulty.dart'; // Assuming you have this enum

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});




  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Sudoku Home'),
      ),
      body: Center(
        child: ElevatedButton(
          onPressed: () {
            // When the user presses the button, navigate to the BoardScreen
            // with the chosen difficulty level.
            Navigator.pushNamed(
              context,
              '/board',
              arguments: Difficulty.medium, // Pass the selected difficulty
            );
          },
          child: const Text('Start Game'),
        ),
      ),
    );
  }
}
