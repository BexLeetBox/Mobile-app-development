package ntnu.idatt2506.datastoring.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ntnu.idatt2506.datastoring.R
import ntnu.idatt2506.datastoring.entity.Movie
enum class AdapterMode {
    MOVIES, ACTORS, SINGLE_TITLE
}


class MovieAdapter(private val mode: AdapterMode, private val items: List<String>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val directorTextView: TextView = itemView.findViewById(R.id.directorTextView)
        val actorsTextView: TextView = itemView.findViewById(R.id.actorsTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (mode) {
            AdapterMode.MOVIES -> {
                val movieData = items[position].split("|") // Assuming delimiter is '|'
                holder.titleTextView.text = movieData[0]
                holder.directorTextView.text = movieData[1]
                holder.actorsTextView.text = movieData[2]
            }
            AdapterMode.SINGLE_TITLE -> {
                holder.titleTextView.text = items[position]
                holder.directorTextView.visibility = View.GONE
                holder.actorsTextView.visibility = View.GONE
            }
            AdapterMode.ACTORS -> {
                holder.titleTextView.text = items[position]
                holder.directorTextView.visibility = View.GONE
                holder.actorsTextView.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = items.size
}

