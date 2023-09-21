package ntnu.idatt2506.oving_2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class CallingActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val upperLimit = 150 // Change this to the desired upper limit
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("upperLimit", upperLimit)
        startActivity(intent)
        finish()
    }
}