package com.thejuki.kformmasterexample.custom.model

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.BaseFormElement
import com.thejuki.kformmasterexample.item.PlaceItem

/**
 * Form Places AutoComplete Element
 *
 * Form element for AppCompatEditText
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPlacesAutoCompleteElement(tag: Int = -1) : BaseFormElement<PlaceItem>(tag) {

    /**
     * List of Place Fields returned
     */
    var placeFields: List<Place.Field> = listOf(Place.Field.ID, Place.Field.NAME)

    /**
     * Display auto complete in an overlay or fullscreen
     *
     * OVERLAY (Default)
     * FULLSCREEN
     */
    var autocompleteActivityMode: AutocompleteActivityMode = AutocompleteActivityMode.OVERLAY

    /**
     * Activity Result Launcher for [AutocompleteActivity]
     */
    var activityResultLauncher: ActivityResultLauncher<Intent>? = null

    /**
     * Handles the Activity Result for the [AutocompleteActivity]
     */
    fun handleActivityResult(formBuilder: FormBuildHelper, resultCode: Int, data: Intent?) {
        data?.let {
            if (resultCode == Activity.RESULT_OK) {
                val place = Autocomplete.getPlaceFromIntent(it)
                value = PlaceItem(place)
                formBuilder.onValueChanged(this)
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                val status = Autocomplete.getStatusFromIntent(it)
                error = status.statusMessage
            }
        }
    }
}