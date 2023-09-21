package ntnu.idatt2506.oving44

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ntnu.idatt2506.oving44.entity.Item

class ItemAdapter(private val items: List<Item>, private val itemClick: (Item) -> Unit) : RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.title
        holder.image.setImageResource(item.imageResId) // Set image resource based on the item
        holder.itemView.setOnClickListener { itemClick(item) }
    }

    override fun getItemCount() = items.size
}
