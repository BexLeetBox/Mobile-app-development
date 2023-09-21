package ntnu.idatt2506.oving44.fragment

import android.os.Bundle
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleTextView = view.findViewById(R.id.titleTextView)
        imageView = view.findViewById(R.id.imageView)  // Add this line
    }

    fun updateItem(item: Item) {
        titleTextView?.text = item.title
        imageView?.setImageResource(item.imageResId)  // Set the image resource
    }
}

