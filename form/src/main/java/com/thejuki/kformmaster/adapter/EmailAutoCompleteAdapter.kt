package com.thejuki.kformmaster.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.thejuki.kformmaster.item.ContactItem
import java.util.*

/**
 * Email AutoComplete Adapter
 *
 * Example AutoCompleteToken Adapter for asynchronous filtering
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class EmailAutoCompleteAdapter(context: Context, textViewResourceId: Int) :
        ArrayAdapter<ContactItem>(context, textViewResourceId), Filterable {

    internal var items: ArrayList<ContactItem>

    init {
        items = ArrayList()
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(index: Int): ContactItem? {
        return items[index]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var mConvertView = convertView
        if (convertView == null) {
            val inflater = context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            mConvertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        }
        (mConvertView?.findViewById(android.R.id.text1) as TextView).text = getItem(position)?.label
        return mConvertView
    }

    override fun getFilter(): Filter {

        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null) {
                    // Use your API here instead
                    items = ArrayList(listOf(ContactItem(id = 1, value = "John.Smith@mail.com", label = "John Smith (Tester)"),
                            ContactItem(id = 2, value = "Apple.May@mail.com", label = "Apple May (Reviewer)"),
                            ContactItem(id = 3, value = "Kotlin.Contact@mail.com", label = "Kotlin Contact (Coder)")
                    ).filter { it.value.orEmpty().contains(constraint, true) })
                    filterResults.values = items
                    filterResults.count = items.size
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }

        }

    }
}