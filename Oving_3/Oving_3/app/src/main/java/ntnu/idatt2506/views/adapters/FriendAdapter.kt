package ntnu.idatt2506.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import ntnu.idatt2506.views.R
import ntnu.idatt2506.views.entity.Friend

class FriendAdapter(context: Context, private val friends: List<Friend>) : ArrayAdapter<Friend>(context, 0, friends) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_friend, parent, false)
        val friend = friends[position]

        val nameTextView: TextView = view.findViewById(R.id.tv_friend_name)
        val birthdateTextView: TextView = view.findViewById(R.id.tv_friend_birthdate)

        nameTextView.text = friend.name
        birthdateTextView.text = friend.birthdate

        return view
    }
}

