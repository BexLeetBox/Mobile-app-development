package ntnu.idatt2506.oving44.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import ntnu.idatt2506.oving44.R
import ntnu.idatt2506.oving44.entity.Item

class DetailFragment : Fragment() {

    private var titleTextView: TextView? = null
    private var imageView: ImageView? = null  // Add this line
    private var descriptionTextView: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleTextView = view.findViewById(R.id.titleTextView)
        imageView = view.findViewById(R.id.imageView)
        descriptionTextView = view.findViewById(R.id.descriptionTextView)
    }

    fun updateItem(item: Item) {
        Log.d("DetailFragment", "Updating item: ${item.title} with description: ${item.description}")

        titleTextView?.text = item.title
        descriptionTextView?.text = item.description
        imageView?.setImageResource(item.imageResId)  // Set the image resource
    }
}

