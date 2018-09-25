package com.thejuki.kformmasterexample

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import com.thejuki.kformmaster.helper.*
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener
import com.thejuki.kformmaster.model.BaseFormElement
import com.thejuki.kformmasterexample.custom.helper.customEx
import com.thejuki.kformmasterexample.custom.view.CustomViewBinder

/**
 * Custom Form Activity
 *
 * The Form uses a custom form element
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class CustomFormActivity : AppCompatActivity() {

    private lateinit var formBuilder: FormBuildHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen_form)

        setupToolBar()

        setupForm()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolBar() {

        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.title = getString(R.string.custom_form)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
        }

    }

    private enum class Tag {
        Custom
    }

    private fun setupForm() {
        val listener: OnFormElementValueChangedListener = object : OnFormElementValueChangedListener {

            override fun onValueChanged(formElement: BaseFormElement<*>) {

            }
        }

        /**
         * This form displays a custom form element in two ways:
         *
         * CustomEx - An entire custom form element: Layout, View, Model, State, and Helper
         * textArea (FormLayouts) - textArea elements will use the form_element_custom layout
         */
        formBuilder = form(this, recyclerView, listener, formLayouts = FormLayouts(
                textArea = R.layout.form_element_custom
        )) {
            header { title = getString(R.string.custom_form) }
            customEx(Tag.Custom.ordinal) {
                title = getString(R.string.Custom_Element)
                titleTextColor = Color.WHITE
            }
            header { title = getString(R.string.custom_footer_1) }
            textArea(Tag.Custom.ordinal) {
                title = getString(R.string.Custom_Layout)
                maxLines = 4
                titleTextColor = Color.WHITE
            }
            header { title = getString(R.string.custom_footer_2) }
        }

        // IMPORTANT: Register your custom view binder or you will get a RuntimeException
        // RuntimeException: ViewRenderer not registered for this type
        formBuilder.registerCustomViewBinder(CustomViewBinder(this, formBuilder).viewBinder)
    }
}