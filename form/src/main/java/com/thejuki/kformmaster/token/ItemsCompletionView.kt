package com.thejuki.kformmaster.token

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import com.thejuki.kformmaster.R
import com.tokenautocomplete.TokenCompleteTextView

/**
 * Items Completion View
 *
 * TokenCompleteTextView implementation
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class ItemsCompletionView : TokenCompleteTextView<Any> {

    private var testAccessibleInputConnection: InputConnection? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun getViewForObject(item: Any): View {
        val l = context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val token = l.inflate(R.layout.item_token, parent as ViewGroup, false) as TokenTextView
        token.text = item.toString()
        return token
    }

    override fun defaultObject(completionText: String): Any? {
        return Any()
    }

    override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection {
        testAccessibleInputConnection = super.onCreateInputConnection(outAttrs)
        return testAccessibleInputConnection!!
    }
}