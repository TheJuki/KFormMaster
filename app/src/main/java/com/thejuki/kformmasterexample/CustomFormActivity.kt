package com.thejuki.kformmasterexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.helper.form
import com.thejuki.kformmaster.helper.header
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener
import com.thejuki.kformmaster.model.BaseFormElement
import com.thejuki.kformmasterexample.custom.helper.customEx
import com.thejuki.kformmasterexample.custom.view.CustomViewBinder
import kotlinx.android.synthetic.main.activity_fullscreen_form.*

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

        formBuilder = form(this, recyclerView, listener, true) {
            header { title = getString(R.string.custom_form) }
            customEx(Tag.Custom.ordinal) {
                title = getString(R.string.Custom)
            }
            header { title = getString(R.string.custom_footer) }
        }

        // IMPORTANT: Register your custom view binder or you will get a RuntimeException
        // RuntimeException: ViewRenderer not registered for this type
        formBuilder.registerCustomViewBinder(CustomViewBinder(this, formBuilder).viewBinder)
    }
}