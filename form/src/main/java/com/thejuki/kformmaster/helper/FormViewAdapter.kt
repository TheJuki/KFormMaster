package com.thejuki.kformmaster.helper

import android.view.View
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter

/**
 * Form View Adapter
 *
 * Form View Adapter used to wrap RendererRecyclerViewAdapter
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormViewAdapter : RendererRecyclerViewAdapter() {
    init {
        registerViewFinder { itemView: View? -> FormViewFinder(itemView!!) }
    }
}