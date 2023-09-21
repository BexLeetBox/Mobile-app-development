package ntnu.idatt2506.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import ntnu.idatt2506.views.entity.Friend

class EditFriendActivity : AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var birthdatePicker: DatePicker
    private lateinit var currentFriend: Friend

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_friend)

        nameEditText = findViewById(R.id.nameEditText)
        birthdatePicker = findViewById(R.id.birthdatePicker)
        val saveButton: Button = findViewById(R.id.saveButton)

        currentFriend = intent.getSerializableExtra("friend") as Friend
        nameEditText.setText(currentFriend.name)
        // Set birthdatePicker values based on currentFriend's birthdate. Extract day, month, year from birthdate string and set.

        saveButton.setOnClickListener {
            currentFriend.name = nameEditText.text.toString()
            currentFriend.birthdate = "${birthdatePicker.dayOfMonth}/${birthdatePicker.month + 1}/${birthdatePicker.year}"

            val returnIntent = Intent().apply {
                putExtra("updatedFriend", currentFriend)
                putExtra("position", intent.getIntExtra("position", -1))
            }
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }
}
