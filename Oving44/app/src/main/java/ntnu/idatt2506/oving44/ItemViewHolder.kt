package ntnu.idatt2506.oving44

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title: TextView = view.findViewById(R.id.itemTitle)
    // If you have more views in your layout, declare them here.
}
