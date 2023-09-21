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

    // Declare a TextView to show the selected item title
    private var titleTextView: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the TextView in your fragment layout
        titleTextView = view.findViewById(R.id.titleTextView)
    }

    // This is the new method to update the displayed title
    fun updateTitle(title: String) {
        titleTextView?.text = title
    }
}
