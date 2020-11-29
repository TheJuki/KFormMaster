package com.thejuki.kformmasterexample.fragment

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.thejuki.kformmaster.helper.*
import com.thejuki.kformmaster.model.FormPickerDateElement
import com.thejuki.kformmasterexample.R
import com.thejuki.kformmasterexample.adapter.ContactAutoCompleteAdapter
import com.thejuki.kformmasterexample.adapter.EmailAutoCompleteAdapter
import com.thejuki.kformmasterexample.custom.helper.placesAutoComplete
import com.thejuki.kformmasterexample.custom.model.FormPlacesAutoCompleteElement
import com.thejuki.kformmasterexample.custom.view.FormPlacesAutoCompleteViewRenderer
import com.thejuki.kformmasterexample.databinding.ActivityFullscreenFormBinding
import com.thejuki.kformmasterexample.fragment.FormFragment.Tag.*
import com.thejuki.kformmasterexample.item.ContactItem
import com.thejuki.kformmasterexample.item.ListItem
import com.thejuki.kformmasterexample.item.PlaceItem
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.*
import java.util.Date

/**
 * Form Fragment
 *
 * The form in a fragment
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormFragment : Fragment() {

    private var _binding: ActivityFullscreenFormBinding? = null
    private val binding get() = _binding!!
    private lateinit var formBuilder: FormBuildHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = ActivityFullscreenFormBinding.inflate(inflater, container, false)
        val view = binding.root

        setupForm()

        return view
    }

    private val fruits = listOf(ListItem(id = 1, name = "Banana"),
            ListItem(id = 2, name = "Orange"),
            ListItem(id = 3, name = "Mango"),
            ListItem(id = 4, name = "Guava"),
            ListItem(id = 5, name = "Apple")
    )

    private enum class Tag {
        Email,
        Phone,
        Location,
        Address,
        ZipCode,
        Date,
        Time,
        DateTime,
        InlineDatePicker,
        Password,
        SingleItem,
        MultiItems,
        AutoCompleteElement,
        AutoCompleteTokenElement,
        ButtonElement,
        TextViewElement,
        LabelElement,
        SwitchElement,
        SliderElement,
        ProgressElement,
        CheckBoxElement,
        SegmentedElement,
        PlacesAutoComplete,
        ImageViewElement
    }

    private fun setupForm() {
        val context = context ?: return

        // Setup Places for custom placesAutoComplete element
        // NOTE: Use your API Key
        Places.initialize(context, "[APP_KEY]")

        formBuilder = form(binding.recyclerView, cacheForm = true) {
            imageView(ImageViewElement.ordinal) {
                displayDivider = false
                required = false
                //defaultImage = R.drawable.default_image
                //value = "http://example.com/" //(String) This needs to be an image URL or an image FILE (absolutePath)
                imagePickerOptions = {
                    // This lets you customize the ImagePicker library, specifying Crop, Dimensions and MaxSize options
                }
                onSelectImage = { file ->
                    // If file is null, that means an error occurred trying to select the image
                    if (file != null) {
                        Toast.makeText(context, file.name, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Error getting the image", Toast.LENGTH_LONG).show()
                    }
                }
            }
            header { title = getString(R.string.PersonalInfo); collapsible = true }
            email(Email.ordinal) {
                title = getString(R.string.email)
                hint = getString(R.string.email_hint)
                value = "mail@mail.com"
                editViewGravity = Gravity.START
                maxLines = 3
                enabled = true
                required = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(context, newValue.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            password(Password.ordinal) {
                title = getString(R.string.password)
                value = "Password123"
                required = true
                editViewGravity = Gravity.START
                enabled = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(context, newValue.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            phone(Phone.ordinal) {
                title = getString(R.string.Phone)
                value = "+8801712345678"
                editViewGravity = Gravity.START
                maxLines = 3
                required = true
                enabled = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(context, newValue.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            header { title = getString(R.string.FamilyInfo); collapsible = true }
            text(Location.ordinal) {
                title = getString(R.string.Location)
                value = "Dhaka"
                editViewGravity = Gravity.START
                required = true
                enabled = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(context, newValue.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            textArea(Address.ordinal) {
                title = getString(R.string.Address)
                value = "123 Street"
                editViewGravity = Gravity.START
                maxLines = 2
                required = true
                enabled = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(context, newValue.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            number(ZipCode.ordinal) {
                title = getString(R.string.ZipCode)
                value = "123456"
                numbersOnly = true
                editViewGravity = Gravity.START
                required = true
                enabled = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(context, newValue.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            header { title = getString(R.string.Schedule); collapsible = true }
            date(Tag.Date.ordinal) {
                title = getString(R.string.Date)
                dateValue = Date()
                dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
                required = true
                maxLines = 1
                editViewGravity = Gravity.START
                enabled = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(context, newValue.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            time(Time.ordinal) {
                title = getString(R.string.Time)
                dateValue = Date()
                dateFormat = SimpleDateFormat("hh:mm a", Locale.US)
                required = true
                maxLines = 1
                editViewGravity = Gravity.START
                enabled = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(context, newValue.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            dateTime(DateTime.ordinal) {
                title = getString(R.string.DateTime)
                dateValue = Date()
                dateFormat = SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US)
                required = true
                maxLines = 1
                editViewGravity = Gravity.START
                enabled = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(context, newValue.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            inlineDatePicker(InlineDatePicker.ordinal) {
                title = getString(R.string.InlineDatePicker)
                value = org.threeten.bp.LocalDateTime.now()
                dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a", Locale.US)
            }
            header { title = getString(R.string.PreferredItems); collapsible = true }
            dropDown<ListItem>(SingleItem.ordinal) {
                title = getString(R.string.SingleItem)
                dialogTitle = getString(R.string.SingleItem)
                options = fruits
                enabled = true
                editViewGravity = Gravity.START
                maxLines = 3
                value = ListItem(id = 1, name = "Banana")
                required = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(context, newValue.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            multiCheckBox<List<ListItem>>(MultiItems.ordinal) {
                title = getString(R.string.MultiItems)
                dialogTitle = getString(R.string.MultiItems)
                options = fruits
                enabled = true
                maxLines = 3
                editViewGravity = Gravity.START
                value = listOf(ListItem(id = 1, name = "Banana"))
                required = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(context, newValue.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            autoComplete<ContactItem>(AutoCompleteElement.ordinal) {
                title = getString(R.string.AutoComplete)
                arrayAdapter = ContactAutoCompleteAdapter(context,
                        android.R.layout.simple_list_item_1, defaultItems =
                arrayListOf(ContactItem(id = 0, value = "", label = "Try \"Apple May\"")))
                dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
                value = ContactItem(id = 1, value = "John Smith", label = "John Smith (Tester)")
                enabled = true
                maxLines = 3
                editViewGravity = Gravity.START
                required = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(context, newValue.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            autoCompleteToken<List<ContactItem>>(AutoCompleteTokenElement.ordinal) {
                title = getString(R.string.AutoCompleteToken)
                arrayAdapter = EmailAutoCompleteAdapter(context,
                        android.R.layout.simple_list_item_1)
                dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
                hint = "Try \"Apple May\""
                value = arrayListOf(ContactItem(id = 1, value = "John.Smith@mail.com", label = "John Smith (Tester)"))
                required = true
                maxLines = 3
                editViewGravity = Gravity.START
                enabled = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(context, newValue.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            textView(TextViewElement.ordinal) {
                title = getString(R.string.TextView)
                editViewGravity = Gravity.START
                maxLines = 1
                value = "This is readonly!"
            }
            label(LabelElement.ordinal) {
                title = getString(R.string.Label)
                editViewGravity = Gravity.START
            }
            header { title = getString(R.string.MarkComplete); collapsible = true }
            switch<String>(SwitchElement.ordinal) {
                title = getString(R.string.Switch)
                value = "Yes"
                onValue = "Yes"
                offValue = "No"
                enabled = true
                required = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(context, newValue.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            slider(SliderElement.ordinal) {
                title = getString(R.string.Slider)
                value = 50
                min = 0
                max = 100
                steps = 20
                enabled = true
                required = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(context, newValue.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            checkBox<Boolean>(CheckBoxElement.ordinal) {
                title = getString(R.string.CheckBox)
                value = true
                checkedValue = true
                unCheckedValue = false
                required = true
                enabled = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(context, newValue.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            progress(ProgressElement.ordinal) {
                title = getString(R.string.Progress)
                indeterminate = false
                progress = 25
                secondaryProgress = 50
                min = 0
                max = 100
            }
            segmented<ListItem>(SegmentedElement.ordinal) {
                title = getString(R.string.Segmented)
                options = fruits
                enabled = true
                editViewGravity = Gravity.START
                horizontal = true
                value = ListItem(id = 1, name = "Banana")
                required = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(context, newValue.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            button(ButtonElement.ordinal) {
                value = getString(R.string.Button)
                enabled = true
                valueObservers.add { newValue, element ->
                    val confirmAlert = AlertDialog.Builder(context).create()
                    confirmAlert.setTitle(context.getString(R.string.Confirm))
                    confirmAlert.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(android.R.string.ok)) { _, _ ->
                        // Could be used to clear another field:
                        val dateToDeleteElement = formBuilder.getFormElement<FormPickerDateElement>(Tag.Date.ordinal)
                        // Display current date
                        Toast.makeText(context,
                                dateToDeleteElement.value?.getTime().toString(),
                                Toast.LENGTH_SHORT).show()
                        dateToDeleteElement.clear()
                        formBuilder.onValueChanged(dateToDeleteElement)
                    }
                    confirmAlert.setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(android.R.string.cancel)) { _, _ ->
                    }
                    confirmAlert.show()
                }
            }
            header { title = getString(R.string.Places_AutoComplete); collapsible = true }
            placesAutoComplete(Tag.PlacesAutoComplete.ordinal) {
                title = getString(R.string.Places_AutoComplete)
                value = PlaceItem(name = "A place name")
                hint = "Tap to show auto complete"
                placeFields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS)
                autocompleteActivityMode = AutocompleteActivityMode.OVERLAY
                clearable = true
            }
        }

        // IMPORTANT: Register your custom view binder or you will get a RuntimeException
        // RuntimeException: ViewRenderer not registered for this type

        // IMPORTANT: Pass in 'this' for the fragment parameter so that startActivityForResult is called from the fragment
        formBuilder.registerCustomViewRenderer(FormPlacesAutoCompleteViewRenderer(formBuilder, layoutID = null, fragment = this).viewRenderer)
    }

    companion object {
        fun newInstance(): FormFragment {
            return FormFragment()
        }
    }

    /**
     * Override the fragment's onActivityResult(), check the request code, and
     * let the FormPlacesAutoCompleteElement handle the result
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PlacesAutoComplete.ordinal) {
            val placesElement = formBuilder.getFormElement<FormPlacesAutoCompleteElement>(PlacesAutoComplete.ordinal)
            placesElement.handleActivityResult(formBuilder, resultCode, data)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
