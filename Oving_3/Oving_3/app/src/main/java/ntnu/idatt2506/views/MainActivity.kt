package ntnu.idatt2506.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import ntnu.idatt2506.views.adapters.FriendAdapter
import ntnu.idatt2506.views.entity.Friend

class MainActivity : AppCompatActivity() {

    private val friendsList = mutableListOf<Friend>()
    private lateinit var adapter: ArrayAdapter<Friend>
    private lateinit var nameEditText: EditText
    private lateinit var birthdatePicker: DatePicker
    private lateinit var listView: ListView
    private lateinit var addButton: Button

    private val editFriendLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data != null) {
                val updatedFriend = data.getSerializableExtra("updatedFriend") as Friend
                val position = data.getIntExtra("position", -1)
                if (position != -1) {
                    friendsList[position] = updatedFriend
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        nameEditText = findViewById(R.id.nameEditText)
        birthdatePicker = findViewById(R.id.birthdatePicker)
        addButton = findViewById(R.id.addButton)

        adapter = FriendAdapter(this, friendsList)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, EditFriendActivity::class.java)
            intent.putExtra("friend", friendsList[position])
            intent.putExtra("position", position)
            editFriendLauncher.launch(intent)
        }

        addButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val day = birthdatePicker.dayOfMonth
            val month = birthdatePicker.month + 1
            val year = birthdatePicker.year
            val formattedDate = "$day/$month/$year"

            val friend = Friend(name, formattedDate)
            friendsList.add(friend)
            adapter.notifyDataSetChanged()
            nameEditText.text.clear()
        }
    }
}

