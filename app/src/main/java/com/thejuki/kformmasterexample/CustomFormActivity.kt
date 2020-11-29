package com.thejuki.kformmasterexample

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.thejuki.kformmaster.helper.*
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener
import com.thejuki.kformmaster.model.BaseFormElement
import com.thejuki.kformmasterexample.custom.helper.customEx
import com.thejuki.kformmasterexample.custom.helper.placesAutoComplete
import com.thejuki.kformmasterexample.custom.model.FormPlacesAutoCompleteElement
import com.thejuki.kformmasterexample.custom.view.CustomViewRenderer
import com.thejuki.kformmasterexample.custom.view.FormPlacesAutoCompleteViewRenderer
import com.thejuki.kformmasterexample.databinding.ActivityFullscreenFormBinding
import com.thejuki.kformmasterexample.item.PlaceItem

/**
 * Custom Form Activity
 *
 * The Form uses a custom form element
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class CustomFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullscreenFormBinding
    private lateinit var formBuilder: FormBuildHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFullscreenFormBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupToolBar()

        setupForm()

        // Setup Places for custom placesAutoComplete element
        // NOTE: Use your API Key
        Places.initialize(applicationContext, "[APP_KEY]")
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
        CustomElement,
        CustomLayout,
        PlacesAutoComplete
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
        formBuilder = form(binding.recyclerView, listener, formLayouts = FormLayouts(
                textArea = R.layout.form_element_custom
        )) {
            header { title = getString(R.string.custom_form) }
            customEx(Tag.CustomElement.ordinal) {
                title = getString(R.string.Custom_Element)
                titleTextColor = Color.WHITE
                clearable = true
            }
            header { title = getString(R.string.custom_footer_1) }
            textArea(Tag.CustomLayout.ordinal) {
                title = getString(R.string.Custom_Layout)
                maxLines = 4
                titleTextColor = Color.WHITE
                clearable = true
            }
            header { title = getString(R.string.custom_footer_2) }
            placesAutoComplete(Tag.PlacesAutoComplete.ordinal) {
                title = getString(R.string.Places_AutoComplete)
                value = PlaceItem(name = "A place name")
                hint = "Tap to show auto complete"
                placeFields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS)
                autocompleteActivityMode = AutocompleteActivityMode.OVERLAY
                clearable = true
            }
            header { title = getString(R.string.custom_footer_3) }
        }

        // IMPORTANT: Register your custom view binder or you will get a RuntimeException
        // RuntimeException: ViewRenderer not registered for this type
        formBuilder.registerCustomViewRenderer(CustomViewRenderer(formBuilder, layoutID = null).viewRenderer)

        formBuilder.registerCustomViewRenderer(FormPlacesAutoCompleteViewRenderer(formBuilder, layoutID = null).viewRenderer)
    }

    /**
     * Override the activity's onActivityResult(), check the request code, and
     * let the FormPlacesAutoCompleteElement handle the result
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Tag.PlacesAutoComplete.ordinal) {
            val placesElement = formBuilder.getFormElement<FormPlacesAutoCompleteElement>(Tag.PlacesAutoComplete.ordinal)
            placesElement.handleActivityResult(formBuilder, resultCode, data)
        }
    }
}