package com.thejuki.kformmaster.helper

import android.view.View
import com.github.vivchar.rendererrecyclerviewadapter.ViewFinderImpl

/**
 * Form View Finder
 *
 * Form View Finder used to wrap ViewFinderImpl
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormViewFinder(itemView: View) : ViewFinderImpl<FormViewFinder>(itemView)