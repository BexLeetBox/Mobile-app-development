
import 'package:flutter/material.dart';

import '../models/difficulty.dart';
import 'board_screen.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Sudoku Home'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: Difficulty.values.map((difficulty) => ElevatedButton(
            onPressed: () {
              // Pass the selected difficulty level to the BoardScreen
              Navigator.push(context, MaterialPageRoute(
                builder: (context) => BoardScreen(difficulty: difficulty),
              ));
            },
            child: Text('Start ${difficulty.toString().split('.').last} Game'),
          )).toList(),
        ),
      ),
    );
  }
}
