package ntnu.idatt2506.oving44.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import ntnu.idatt2506.oving44.R
import ntnu.idatt2506.oving44.entity.Item

class ListFragment : Fragment() {

    private var callback: OnItemSelectedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val items = listOf(
            Item(
                "Harry potter",
                getString(R.string.description_harry_potter),  // This retrieves the description for Harry Potter from strings.xml
                R.drawable.hp
            ),
            Item(
                "Lord of the rings",
                getString(R.string.description_lotr),  // This retrieves the description for Lord of the Rings from strings.xml
                R.drawable.lotr
            )
            // ... add more items if needed
        )




        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1,
            items.map { it.title })

        view.findViewById<ListView>(R.id.listView).adapter = adapter

        view.findViewById<ListView>(R.id.listView).setOnItemClickListener { _, _, position, _ ->
            callback?.onItemSelected(items[position])


        }

    }

    interface OnItemSelectedListener {
        fun onItemSelected(item: Item)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnItemSelectedListener) {
            callback = context
        } else {
            throw RuntimeException("$context must implement OnItemSelectedListener")
        }
    }
}




