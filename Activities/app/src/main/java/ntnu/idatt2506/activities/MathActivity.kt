package ntnu.idatt2506.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MathActivity: AppCompatActivity() {
    private var firstRandomNumber: Int = 0
    private var secondRandomNumber: Int = 0
    // Declare your views
    private lateinit var numberOne: TextView
    private lateinit var numberTwo: TextView
    private lateinit var userAnswer: EditText
    private lateinit var addButton: Button
    private lateinit var multiplyButton: Button
    private lateinit var upperLimitEditText: EditText
    private lateinit var randomNumberLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator) // replace 'your_layout_file' with the name of your XML layout file

        // Initialize your views
        numberOne = findViewById(R.id.numberOne)
        numberTwo = findViewById(R.id.numberTwo)
        userAnswer = findViewById(R.id.userAnswer)
        upperLimitEditText = findViewById(R.id.upperLimit)
        val addButton: Button = findViewById(R.id.addButton)
        val multiplyButton: Button = findViewById(R.id.multiplyButton)

        // Set onClick listeners
        addButton.setOnClickListener {
            checkAnswer(isAddition = true)
            setRandomNumbers()
        }

        multiplyButton.setOnClickListener {
            checkAnswer(isAddition = false)
            setRandomNumbers()
        }



       randomNumberLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val randomValue = result.data?.getIntExtra("RANDOM_NUMBER", 0)
                if (firstRandomNumber == 0) {
                    firstRandomNumber = randomValue!!
                    launchMainActivityForRandomNumber() // Launch again for the second number.
                } else {
                    secondRandomNumber = randomValue!!
                    // Now you can set these numbers to your TextViews.
                    numberOne.text = firstRandomNumber.toString()
                    numberTwo.text = secondRandomNumber.toString()
                }
            }
        }

        launchMainActivityForRandomNumber()
    }

    private fun checkAnswer(isAddition: Boolean) {
        val num1 = numberOne.text.toString().toInt()
        val num2 = numberTwo.text.toString().toInt()
        val answer = userAnswer.text.toString().toInt()
        val correctAnswer = if (isAddition) num1 + num2 else num1 * num2

        if (answer == correctAnswer) {
            Toast.makeText(this, getString(R.string.correct), Toast.LENGTH_SHORT).show()
        } else {
            val errorMsg = getString(R.string.wrong_answer, correctAnswer)
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setRandomNumbers() {
        val upperLimit = try {
            upperLimitEditText.text.toString().toInt()
        } catch (e: NumberFormatException) {
            // This default value of 10 is set if the input is not a valid integer.
            10
        }

        val firstNumber = (0..upperLimit).random()
        val secondNumber = (0..upperLimit).random()

        numberOne.text = firstNumber.toString()
        numberTwo.text = secondNumber.toString()
    }

    private fun launchMainActivityForRandomNumber() {
        val upperLimit = upperLimitEditText.text.toString().toInt()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("UPPER_LIMIT", upperLimit)  // Assuming you've already fetched the upper limit from EditText.
        randomNumberLauncher.launch(intent)
    }
}