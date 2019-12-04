package com.thejuki.kformmasterexample

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.github.dhaval2404.imagepicker.constant.ImageProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.redmadrobot.inputmask.MaskedTextChangedListener.ValueListener
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy
import com.thejuki.kformmaster.helper.*
import com.thejuki.kformmaster.model.*
import com.thejuki.kformmaster.widget.FormElementMargins
import com.thejuki.kformmasterexample.FullscreenFormActivity.Tag.*
import com.thejuki.kformmasterexample.adapter.ContactAutoCompleteAdapter
import com.thejuki.kformmasterexample.adapter.EmailAutoCompleteAdapter
import com.thejuki.kformmasterexample.item.ContactItem
import com.thejuki.kformmasterexample.item.ListItem
import com.thejuki.kformmasterexample.item.SegmentedListItem
import kotlinx.android.synthetic.main.activity_fullscreen_form.*
import kotlinx.android.synthetic.main.bottomsheet_image.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.Date


/**
 * Fullscreen Form Activity
 *
 * The Form takes up the whole activity screen
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FullscreenFormActivity : AppCompatActivity() {

    private lateinit var formBuilder: FormBuildHelper
    private lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen_form)

        setupToolBar()

        bottomSheetDialog = BottomSheetDialog(this)
        val sheetView = this.layoutInflater.inflate(R.layout.bottomsheet_image, null)
        bottomSheetDialog.setContentView(sheetView)

        setupForm()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_form, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                R.id.action_validate -> {

                    // IMPORTANT: Make sure to clear focus before validating/submitting the form.
                    // This is because if an edit text was focused and edited it will need
                    // to lose focus in order to update the form element value.
                    currentFocus?.clearFocus()

                    // Hide the soft keyboard
                    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    var view = currentFocus
                    if (view == null) {
                        view = View(this)
                    }
                    imm.hideSoftInputFromWindow(view.windowToken, 0)

                    // Validate the form values
                    validate()

                    true
                }
                R.id.action_show_hide -> {
                    showHideAll()
                    true
                }
                R.id.action_clear -> {
                    clear()
                    true
                }
                android.R.id.home -> {
                    onBackPressed()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    private fun clear() {
        formBuilder.clearAll()
    }

    private fun showHideAll() {
        formBuilder.elements.forEach {
            if (it is FormHeader) {
                it.setAllCollapsed(!it.allCollapsed, formBuilder)
            }
        }
    }

    private fun validate() {
        val alert = AlertDialog.Builder(this@FullscreenFormActivity).create()

        if (formBuilder.isValidForm) {
            alert.setTitle(this@FullscreenFormActivity.getString(R.string.FormValid))
        } else {
            alert.setTitle(this@FullscreenFormActivity.getString(R.string.FormInvalid))

            formBuilder.elements.forEach {
                if (!it.isValid) {
                    if (it is FormEmailEditTextElement) {
                        it.error = "Invalid email!"
                    } else {
                        it.error = "This field is required!"
                    }

                }
            }
        }

        alert.setButton(AlertDialog.BUTTON_POSITIVE, this@FullscreenFormActivity.getString(android.R.string.ok), { _, _ -> })
        alert.show()
    }

    private fun setupToolBar() {

        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.title = getString(R.string.full_screen_form)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
        }
    }

    private val fruits = listOf(ListItem(id = 1, name = "Banana"),
            ListItem(id = 2, name = "Orange"),
            ListItem(id = 3, name = "Mango"),
            ListItem(id = 4, name = "Guava"),
            ListItem(id = 5, name = "Apple")
    )

    private val fruitsSegmented = listOf(SegmentedListItem(id = 1, name = "Banana"),
            SegmentedListItem(id = 2, name = "Orange"),
            SegmentedListItem(id = 3, name = "Avocado"),
            SegmentedListItem(id = 4, name = "Apple")
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
        ImageViewElement
    }

    private fun setupForm() {
        formBuilder = form(this, recyclerView,
                formLayouts = FormLayouts(
                        // Uncomment to replace all text elements with the form_element_custom layout
                        //text = R.layout.form_element_custom
                )) {
            imageView(ImageViewElement.ordinal) {
                displayDivider = false
                imageTransformation = CircleTransform(borderColor = Color.WHITE, borderRadius = 3) //Default value for this is CircleTransform(null) so it makes image round without borders
                required = false
                theme = R.style.CustomDialogPicker // This is to theme the default dialog when onClickListener is not used.
                //defaultImage = R.drawable.default_image
                //value = "https://via.placeholder.com/200" //(String) This needs to be an image URL, data URL, or an image FILE (absolutePath)
                imagePickerOptions = {
                    // This lets you customize the ImagePicker library, specifying Crop, Dimensions and MaxSize options
                    it.cropX = 3f
                    it.cropY = 4f
                    it.maxWidth = 150
                    it.maxHeight = 200
                    it.maxSize = 500
                }
                onSelectImage = { file ->
                    // If file is null, that means an error occurred trying to select the image
                    if (file != null) {
                        Toast.makeText(this@FullscreenFormActivity, file.name, LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@FullscreenFormActivity, "Error getting the image", LENGTH_LONG).show()
                    }
                }

                // Optional: Handle onClickListener yourself. Here I am using a BottomSheet instead of the
                // the default AlertDialog
                bottomSheetDialog.image_bottom_sheet_camera.setOnClickListener {
                    bottomSheetDialog.dismiss()
                    this.openImagePicker(ImageProvider.CAMERA)
                }
                bottomSheetDialog.image_bottom_sheet_gallery.setOnClickListener {
                    bottomSheetDialog.dismiss()
                    this.openImagePicker(ImageProvider.GALLERY)
                }
                bottomSheetDialog.image_bottom_sheet_remove.setOnClickListener {
                    bottomSheetDialog.dismiss()
                    this.clearImage()
                }
                bottomSheetDialog.image_bottom_sheet_close.setOnClickListener {
                    bottomSheetDialog.dismiss()
                }
                onClick = {
                    bottomSheetDialog.show()
                }
            }
            header {
                title = getString(R.string.PersonalInfo)
                collapsible = true
                backgroundColor = Color.parseColor("#DDDDDD")
                titleTextColor = Color.BLACK
                centerText = true
                allCollapsed = false
            }
            email(Email.ordinal) {
                title = getString(R.string.email)
                centerText = true
                displayDivider = false
                hint = getString(R.string.email_hint)
                value = "mail@mail.com"
                maxLines = 3
                maxLength = 100
                backgroundColor = Color.WHITE
                titleTextColor = Color.BLACK
                titleFocusedTextColor = Color.parseColor("#FF4081")
                valueTextColor = Color.BLACK
                errorTextColor = ResourcesCompat.getColor(resources, R.color.colorFormMasterElementErrorTitle, null)
                hintTextColor = Color.BLUE
                enabled = true
                required = true
                clearable = true
                clearOnFocus = false
                validityCheck = {
                    if (value != null) android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches() else false
                }
                valueObservers.add { newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                }
            }
            password(Password.ordinal) {
                title = getString(R.string.password)
                value = "Password123"
                displayDivider = false
                required = true
                maxLength = 100
                rightToLeft = false
                enabled = true
                clearable = true
                clearOnFocus = false
                valueObservers.add { newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                }
            }
            phone(Phone.ordinal) {
                title = getString(R.string.Phone)
                value = "123-456-7890"
                rightToLeft = false
                displayDivider = false
                maxLength = 100
                maxLines = 3
                required = true
                enabled = true
                clearable = true
                clearOnFocus = false
                valueObservers.add { newValue, element ->
                    //Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                }
                inputMaskOptions = InputMaskOptions(primaryFormat = "+1 ([000]) [000]-[0000]",
                        affinityCalculationStrategy = AffinityCalculationStrategy.PREFIX,
                        valueListener = object : ValueListener {
                            override fun onTextChanged(maskFilled: Boolean, extractedValue: String, formattedValue: String) {
                                Toast.makeText(this@FullscreenFormActivity,
                                        "Extracted Value: $extractedValue\nFormatted Value: $formattedValue", LENGTH_SHORT).show()
                            }
                        }
                )
            }
            header { title = getString(R.string.FamilyInfo); collapsible = true }
            text(Location.ordinal) {
                title = getString(R.string.Location)
                value = "Dhaka"
                rightToLeft = false
                displayDivider = false
                maxLength = 100
                required = true
                enabled = true
                clearable = true
                clearOnFocus = false
                valueObservers.add { newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                }
            }
            textArea(Address.ordinal) {
                title = getString(R.string.Address)
                value = "123 Street"
                rightToLeft = false
                displayDivider = false
                maxLines = 2
                maxLength = 100
                required = true
                enabled = true
                updateOnFocusChange = true
                imeOptions = EditorInfo.IME_ACTION_DONE
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
                clearable = true
                clearOnFocus = false
                valueObservers.add { newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                }
            }
            number(ZipCode.ordinal) {
                title = getString(R.string.ZipCode)
                value = "12345"
                numbersOnly = true
                rightToLeft = false
                displayDivider = false
                maxLength = 5
                required = true
                enabled = true
                clearable = true
                clearOnFocus = false
                valueObservers.add { newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                }
            }
            header { title = getString(R.string.Schedule); collapsible = true }
            date(Tag.Date.ordinal) {
                title = getString(R.string.Date)
                theme = R.style.CustomDialogPicker
                //dateValue = Date()
                dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
                minimumDate = dateFormat.parse("01/01/2018")
                maximumDate = dateFormat.parse("12/15/2025")
                startDate = dateFormat.parse("01/02/2018")
                required = true
                maxLines = 1
                confirmEdit = true
                displayDivider = false
                rightToLeft = false
                enabled = true
                clearable = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                }
            }
            time(Time.ordinal) {
                title = getString(R.string.Time)
                theme = R.style.CustomDialogPicker
                is24HourView = true
                dateValue = Date()
                dateFormat = SimpleDateFormat("hh:mm a", Locale.US)
                //startDate = dateFormat.parse("12:40 AM")
                required = true
                maxLines = 1
                confirmEdit = true
                displayDivider = false
                rightToLeft = false
                enabled = true
                clearable = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                }
            }
            dateTime(DateTime.ordinal) {
                title = getString(R.string.DateTime)
                theme = R.style.CustomDialogPicker
                is24HourView = true
                dateValue = Date()
                dateFormat = SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US)
                //startDate = dateFormat.parse("01/01/2018 12:00 AM")
                minimumDate = dateFormat.parse("01/01/2018 12:00 AM")
                maximumDate = dateFormat.parse("12/15/2025 12:00 PM")
                required = true
                maxLines = 1
                confirmEdit = true
                rightToLeft = false
                displayDivider = false
                enabled = true
                clearable = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                }
            }
            header { title = getString(R.string.PreferredItems); collapsible = true }
            dropDown<ListItem>(SingleItem.ordinal) {
                title = getString(R.string.SingleItem)
                dialogTitle = getString(R.string.SingleItem)
                options = fruits
                enabled = true
                rightToLeft = false
                dialogEmptyMessage = "This is Empty!"
                theme = R.style.CustomDialogPicker
                displayValueFor = {
                    if (it != null) {
                        it.name + " (" + options?.indexOf(it) + ")"
                    } else {
                        ""
                    }
                }
                confirmEdit = true
                displayRadioButtons = true
                maxLines = 3
                displayDivider = false
                value = ListItem(id = 1, name = "Banana")
                required = true
                clearable = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                }
            }
            multiCheckBox<List<ListItem>>(MultiItems.ordinal) {
                title = getString(R.string.MultiItems)
                dialogTitle = getString(R.string.MultiItems)
                theme = R.style.CustomDialogPicker
                options = fruits
                enabled = true
                maxLines = 3
                confirmEdit = true
                rightToLeft = false
                displayDivider = false
                value = listOf(ListItem(id = 1, name = "Banana"))
                required = true
                clearable = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                }
            }
            autoComplete<ContactItem>(AutoCompleteElement.ordinal) {
                title = getString(R.string.AutoComplete)
                arrayAdapter = ContactAutoCompleteAdapter(this@FullscreenFormActivity,
                        android.R.layout.simple_list_item_1, defaultItems =
                arrayListOf(ContactItem(id = 0, value = "", label = "Try \"Apple May\"")))
                dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
                value = ContactItem(id = 1, value = "John Smith", label = "John Smith (Tester)")
                enabled = true
                maxLines = 3
                displayDivider = false
                rightToLeft = false
                required = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                }
            }
            autoCompleteToken<List<ContactItem>>(AutoCompleteTokenElement.ordinal) {
                title = getString(R.string.AutoCompleteToken)
                arrayAdapter = EmailAutoCompleteAdapter(this@FullscreenFormActivity,
                        android.R.layout.simple_list_item_1)
                dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
                hint = "Try \"Apple May\""
                value = arrayListOf(ContactItem(id = 1, value = "John.Smith@mail.com", label = "John Smith (Tester)"))
                required = true
                maxLines = 3
                rightToLeft = false
                displayDivider = false
                enabled = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                }
            }
            textView(TextViewElement.ordinal) {
                title = getString(R.string.TextView)
                rightToLeft = false
                maxLines = 1
                value = "This is readonly!"
                displayDivider = false
            }
            label(LabelElement.ordinal) {
                title = getString(R.string.Label)
                rightToLeft = false
                displayDivider = false
                centerText = true
            }
            header { title = getString(R.string.MarkComplete); collapsible = true }
            switch<String>(SwitchElement.ordinal) {
                title = getString(R.string.Switch)
                value = "Yes"
                onValue = "Yes"
                offValue = "No"
                displayDivider = false
                enabled = true
                required = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                }
            }
            slider(SliderElement.ordinal) {
                title = getString(R.string.Slider)
                value = 100
                min = 100
                max = 1000
                displayDivider = false
                incrementBy = 50
                enabled = true
                required = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                }
            }
            checkBox<Boolean>(CheckBoxElement.ordinal) {
                title = getString(R.string.CheckBox)
                value = true
                checkedValue = true
                unCheckedValue = false
                displayDivider = false
                required = true
                enabled = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                }
            }
            progress(ProgressElement.ordinal) {
                title = getString(R.string.Progress)
                //displayTitle = false
                //centerText = true
                progressBarStyle = FormProgressElement.ProgressBarStyle.HorizontalBar
                indeterminate = false
                progress = 25
                secondaryProgress = 50
                min = 0
                max = 100
                displayDivider = false
            }
            segmented<SegmentedListItem>(SegmentedElement.ordinal) {
                // Set the drawables
                fruitsSegmented[0].drawableRes = R.drawable.icons8_banana
                fruitsSegmented[1].drawableRes = R.drawable.icons8_orange
                fruitsSegmented[2].drawableRes = R.drawable.icons8_avocado
                fruitsSegmented[3].drawableRes = R.drawable.icons8_apple

                title = getString(R.string.Segmented)
                options = fruitsSegmented
                enabled = true
                rightToLeft = false
                displayTitle = true
                horizontal = true
                fillSpace = true
                marginDp = 5
                margins = FormElementMargins(16, 16, 16, 0)
                layoutPaddingBottom = 16
                displayDivider = false
                tintColor = Color.parseColor("#FF4081")
                unCheckedTintColor = Color.WHITE
                checkedTextColor = Color.WHITE
                cornerRadius = 0f
                textSize = 12f
                padding = 10
                drawableDirection = FormSegmentedElement.DrawableDirection.Top
                value = fruitsSegmented[0]
                required = true
                valueObservers.add { newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                }
            }
            button(ButtonElement.ordinal) {
                value = getString(R.string.Button)
                displayDivider = false
                enabled = true
                valueObservers.add { newValue, element ->
                    val confirmAlert = AlertDialog.Builder(this@FullscreenFormActivity).create()
                    confirmAlert.setTitle(this@FullscreenFormActivity.getString(R.string.Confirm))
                    confirmAlert.setButton(AlertDialog.BUTTON_POSITIVE, this@FullscreenFormActivity.getString(android.R.string.ok)) { _, _ ->
                        // Could be used to clear another field:
                        val dateToDeleteElement = formBuilder.getFormElement<FormPickerDateElement>(Tag.Date.ordinal)
                        // Display current date
                        Toast.makeText(this@FullscreenFormActivity,
                                dateToDeleteElement.value?.getTime().toString(),
                                Toast.LENGTH_SHORT).show()
                        dateToDeleteElement.clear()
                        formBuilder.onValueChanged(dateToDeleteElement)
                    }
                    confirmAlert.setButton(AlertDialog.BUTTON_NEGATIVE, this@FullscreenFormActivity.getString(android.R.string.cancel)) { _, _ ->
                    }
                    confirmAlert.show()
                }
            }
        }
    }
}