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

class SudokuApp extends StatefulWidget {
  const SudokuApp({Key? key}) : super(key: key);

  static _SudokuAppState? of(BuildContext context) => context.findAncestorStateOfType<_SudokuAppState>();

  @override
  _SudokuAppState createState() => _SudokuAppState();
}

class _SudokuAppState extends State<SudokuApp> {
  Locale _currentLocale = const Locale('en', ''); // Default to English

  void setLocale(Locale locale) {
    setState(() {
      _currentLocale = locale;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false, // Add this line to hide the debug banner
      title: 'Sudoku App',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      initialRoute: '/',
      routes: {
        '/': (context) => const HomeScreen(),
      },
      onGenerateRoute: (settings) {
        switch (settings.name) {
          case '/board':
            final Difficulty difficulty = settings.arguments as Difficulty;
            return MaterialPageRoute(
              builder: (context) => BoardScreen(difficulty: difficulty),
            );
          case '/input':
            final Difficulty difficulty = settings.arguments as Difficulty;
            return MaterialPageRoute(
              builder: (context) => InputBoardScreen(difficulty: difficulty),
            );
          default:
            return MaterialPageRoute(builder: (context) => const HomeScreen());
        }
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
  SudokuBoard? board; // Initially null, can be nullable
  final Difficulty currentDifficulty = Difficulty.easy; // Example difficulty level

  @override
  void initState() {
    super.initState();
    _generateBoard();
  }

  Future<void> _generateBoard() async {
    // Await the asynchronous operation to complete and then update the state
    SudokuBoard newBoard = await SudokuGenerator.generateBoard(currentDifficulty);
    setState(() {
      board = newBoard;
    });
  }

  void _resetBoard() {
    _generateBoard(); // Call _generateBoard to reset the board
  }

  // Define the callback function that will be passed to SudokuBoardWidget
  void _onCellChanged(int row, int col, int? newValue) {
    // Update the board with the new value
    setState(() {
      board!.board[row][col] = newValue!;
    });
  }

  @override
  Widget build(BuildContext context) {
    // Check if the board is null and show a loading spinner if it is
    if (board == null) {
      return Scaffold(
        appBar: AppBar(
          title: const Text('Loading...'),
        ),
        body: const Center(
          child: CircularProgressIndicator(),
        ),
      );
    }


    return Scaffold(
      appBar: AppBar(
        title: Text('Sudoku - ${board!.difficulty.toString().split('.').last}'),
      ),
      body: SudokuBoardWidget(
        board: board!,
        onCellChanged: _onCellChanged, // Pass the callback function here
      ),
    );
  }
}


AppBar buildAppBar(BuildContext context) {
  return AppBar(
    title: const Text('Sudoku'),
    actions: [
      IconButton(
        icon: Text(SudokuApp.of(context)?._currentLocale.languageCode.toUpperCase() ?? 'EN'),
        onPressed: () {
          var newLocale = Localizations.localeOf(context).languageCode == 'en'
              ? Locale('no', '')
              : Locale('en', '');
          SudokuApp.of(context)?.setLocale(newLocale);
        },
      ),
    ],
  );
}
