import 'package:flutter/material.dart';
import 'package:sudoku_app/screens/board_screen.dart';
import 'package:sudoku_app/screens/home_screen.dart';
import 'package:sudoku_app/screens/input_board_screen.dart';
import 'package:sudoku_app/utils/sudoku_generator.dart';
import 'package:sudoku_app/widgets/sudoku_board_widget.dart';

import 'model/sudoku_board.dart';
import 'models/difficulty.dart';


void main() {
  runApp(const SudokuApp());
  // In main.dart
  // main.dart
  MaterialApp(
    // ...
    onGenerateRoute: (RouteSettings settings) {
      if (settings.name == '/board') {
        final args = settings.arguments;
        if (args is Difficulty) {
          return MaterialPageRoute(
            builder: (context) => BoardScreen(difficulty: args),
          );
        }
        // Handle other types of arguments or throw an error
      }
      // Define other routes
      return null;
    },
  );


}


class SudokuApp extends StatelessWidget {
  const SudokuApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Sudoku App',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      initialRoute: '/',
      routes: {
        '/': (context) => const HomeScreen(),
      },
      onGenerateRoute: (settings) {
        // Handle any routes with arguments here
        if (settings.name == '/board') {
          final Difficulty difficulty = settings.arguments as Difficulty;
          return MaterialPageRoute(
            builder: (context) => BoardScreen(difficulty: difficulty),
          );
        } else if (settings.name == '/input') {
          return MaterialPageRoute(
            builder: (context) => const InputBoardScreen(),
          );
        }
        // Define other routes as needed
        return null; // Implement error handling for undefined routes
      },
    );
  }
}

class SudokuHomePage extends StatefulWidget {
  const SudokuHomePage({Key? key}) : super(key: key);

  @override
  State<SudokuHomePage> createState() => _SudokuHomePageState();
}

class _SudokuHomePageState extends State<SudokuHomePage> {
  late SudokuBoard board;
  final Difficulty currentDifficulty = Difficulty.easy; // Example difficulty level
  @override
  void initState() {
    super.initState();
    board = SudokuGenerator.generateBoard(currentDifficulty);
  }

  void _resetBoard() {
    setState(() {
      board = SudokuGenerator.generateBoard(currentDifficulty);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Sudoku'),
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: _resetBoard,
          ),
        ],
      ),
      body: SudokuBoardWidget(board: board),
    );
  }
}
