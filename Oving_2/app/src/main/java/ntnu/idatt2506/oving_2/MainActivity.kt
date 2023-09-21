package ntnu.idatt2506.oving_2

import android.app.Activity
import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val upperLimit = intent.getIntExtra("upperLimit", 100)
        generateRandomNumberAndShowResult(upperLimit)
    }

    private fun generateRandomNumberAndShowResult(upperLimit: Int) {
        val randomValue = (0..upperLimit).random()
        val resultIntent = Intent().apply {
            putExtra("randomNumber", randomValue)
        }
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}
