# Google Place Autocomplete

The autocomplete service in the Places SDK for Android returns place predictions in response to user search queries. As the user types, the autocomplete service returns suggestions for places such as businesses, addresses and points of interest.

[Place Autocomplete Reference](https://developers.google.com/places/android-sdk/autocomplete)

!!! note "Note"

    This element is not included with the other form elements because it relies on the Places library, requires an API key, and relies on registerForActivityResult to get the selected Place object.

    This element behaves as just an on click event to display the Places Autocomplete activity. The selected Place will be displayed in the form element text field using the PlaceItem class's toString method.

To use this custom form element in the app folder you will need to do following: 

- Get an [API Key](https://developers.google.com/maps/documentation/places/android-sdk/cloud-setup)
- Add the Places SDK dependency to your app's build.gradle file (Use the latest version)

```text
implementation 'com.google.android.libraries.places:places:1.0.0'
```

- Copy the following files to your app project:
```text
/app/src/main/java/com/thejuki/kformmasterexample/item/PlaceItem.kt
/app/src/main/java/com/thejuki/kformmasterexample/custom/model/FormPlacesAutoCompleteElement.kt
/app/src/main/java/com/thejuki/kformmasterexample/custom/view/FormPlacesAutoCompleteViewRenderer.kt
/app/src/main/java/com/thejuki/kformmasterexample/custom/helper/FormBuilderExtensions.kt
```

### Use the Places Autocomplete Form Element

!!! error "IMPORTANT"

    * Register your custom view binder or you will get a RuntimeException
    * RuntimeException: ViewRenderer not registered for this type

    * Create a registerForActivityResult variable and have the form element call handleActivityResult(formBuilder, resultCode, data)

```kotlin
class CustomFormActivity : AppCompatActivity() {

    // Setup the FormBuildHelper at the class level
    private lateinit var binding: ActivityFullscreenFormBinding
    private lateinit var formBuilder: FormBuildHelper
    private val startPlacesForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        val placesElement = formBuilder.getFormElement<FormPlacesAutoCompleteElement>(Tag.PlacesAutoComplete.ordinal)
        placesElement.handleActivityResult(formBuilder, result.resultCode, result.data)
    }

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

    private enum class Tag {
        PlacesElement
    }

    private fun setupForm() {
        formBuilder = form(binding.recyclerView) {
            placesAutoComplete(Tag.PlacesElement.ordinal) {
                title = getString(R.string.Places_AutoComplete)
                // Set a value initially to show in the textfield
                value = PlaceItem(name = "A place name")
                hint = "Tap to show auto complete"
                // Set place fields to return back from the selected place
                placeFields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS)
                /**
                * Display auto complete in an overlay or fullscreen
                *
                * OVERLAY (Default)
                * FULLSCREEN
                */
                autocompleteActivityMode = AutocompleteActivityMode.OVERLAY
                clearable = true
                activityResultLauncher = startPlacesForResult
            }
        }

        // Required
        // IMPORTANT: Pass in 'this' for the fragment parameter so that startActivityForResult is called from the fragment (If you are using a fragment instead of an activity)
        formBuilder.registerCustomViewRenderer(FormPlacesAutoCompleteViewRenderer(formBuilder, layoutID = null, fragment = null).viewRenderer)
    }
}
```
