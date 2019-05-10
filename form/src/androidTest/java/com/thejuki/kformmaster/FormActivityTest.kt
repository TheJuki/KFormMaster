package com.thejuki.kformmaster

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.thejuki.kformmaster.FormActivityTest.Tag.*
import com.thejuki.kformmaster.adapter.ContactAutoCompleteAdapter
import com.thejuki.kformmaster.adapter.EmailAutoCompleteAdapter
import com.thejuki.kformmaster.helper.*
import com.thejuki.kformmaster.item.ContactItem
import com.thejuki.kformmaster.item.ListItem
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener
import com.thejuki.kformmaster.model.BaseFormElement
import com.thejuki.kformmaster.model.FormButtonElement
import com.thejuki.kformmaster.model.FormPickerDateElement
import kotlinx.android.synthetic.main.activity_form_test.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.Date

/**
 * Form Activity Test
 *
 * The Great Form Activity Test
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormActivityTest : AppCompatActivity() {

    lateinit var formBuilder: FormBuildHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_test)

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
            actionBar.title = "Test"
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
        }
    }

    private val fruits = listOf(ListItem(id = 1, name = "Banana"),
            ListItem(id = 2, name = "Orange"),
            ListItem(id = 3, name = "Mango"),
            ListItem(id = 4, name = "Guava")
    )

    enum class Tag {
        Hidden,
        Disabled,
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
        SwitchElement,
        SliderElement,
        ProgressElement,
        CheckBoxElement,
        SegmentedElement,
        LabelElement
    }

    private fun setupForm() {
        val listener: OnFormElementValueChangedListener = object : OnFormElementValueChangedListener {
            override fun onValueChanged(formElement: BaseFormElement<*>) {

            }
        }

        formBuilder = form(this, recyclerView, listener, true) {
            header { title = "Header 1"; collapsible = true }
            email(Email.ordinal) {
                title = "Email"
                value = "test@example.com"
                required = true
                hint = "Email Hint"
                maxLines = 3
                maxLength = 100
                backgroundColor = Color.WHITE
                titleTextColor = Color.BLACK
                titleFocusedTextColor = Color.parseColor("#FF4081")
                valueTextColor = Color.BLACK
                errorTextColor = ResourcesCompat.getColor(resources, R.color.colorFormMasterElementErrorTitle, null)
                hintTextColor = Color.BLUE
                enabled = true
            }
            password(Password.ordinal) {
                title = "Password"
            }
            phone(Phone.ordinal) {
                title = "Phone"
                value = "+8801712345678"
            }
            header { title = "Header 2" }
            text(Location.ordinal) {
                title = "Location"
                value = "Dhaka"
            }
            textArea(Address.ordinal) {
                title = "Address"
                value = ""
            }
            number(ZipCode.ordinal) {
                title = "ZipCode"
                value = "1000"
                numbersOnly = true
            }
            header { title = "Header 3" }
            date(Tag.Date.ordinal) {
                title = "Date"
                dateValue = Date()
                dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
            }
            time(Time.ordinal) {
                title = "Time"
                dateValue = Date()
                dateFormat = SimpleDateFormat("hh:mm a", Locale.US)
            }
            dateTime(DateTime.ordinal) {
                title = "DateTime"
                dateValue = Date()
                dateFormat = SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US)
            }
            header { title = "Header 4" }
            dropDown<ListItem>(SingleItem.ordinal) {
                title = "SingleItem"
                dialogTitle = "SingleItem Dialog"
                options = fruits
                arrayAdapter = null
                value = ListItem(id = 1, name = "Banana")
            }
            multiCheckBox<List<ListItem>>(MultiItems.ordinal) {
                title = "MultiItems"
                dialogTitle = "MultiItems Dialog"
                options = fruits
                value = listOf(ListItem(id = 1, name = "Banana"))
            }
            autoComplete<ContactItem>(AutoCompleteElement.ordinal) {
                title = "AutoComplete"
                arrayAdapter = ContactAutoCompleteAdapter(this@FormActivityTest,
                        android.R.layout.simple_list_item_1, defaultItems =
                arrayListOf(ContactItem(id = 1, value = "", label = "Try \"Apple May\"")))
                dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
            }
            autoCompleteToken<List<ContactItem>>(AutoCompleteTokenElement.ordinal) {
                title = "AutoCompleteToken"
                arrayAdapter = EmailAutoCompleteAdapter(this@FormActivityTest,
                        android.R.layout.simple_list_item_1)
                dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
                hint = "Try \"Apple May\""
            }
            textView(TextViewElement.ordinal) {
                title = "TextView"
                value = "This is readonly!"
            }
            image(5){

            }
            header { title = "Header 5" }
            switch<String>(SwitchElement.ordinal) {
                title = "Switch"
                value = "No"
                onValue = "Yes"
                offValue = "No"
            }
            slider(SliderElement.ordinal) {
                title = "Slider"
                value = 50
                min = 0
                max = 100
                steps = 20
            }
            checkBox<Boolean>(CheckBoxElement.ordinal) {
                title = "CheckBox"
                value = false
                checkedValue = true
                unCheckedValue = false
            }
            progress(ProgressElement.ordinal) {
                title = "Progress"
                indeterminate = false
                progress = 25
                secondaryProgress = 50
                min = 0
                max = 100
            }
            segmented<ListItem>(SegmentedElement.ordinal) {
                title = "Segmented"
                options = fruits
                value = ListItem(id = 1, name = "Banana")
            }
            button(ButtonElement.ordinal) {
                value = "Button"
                valueObservers.add { _, _ ->
                    val confirmAlert = AlertDialog.Builder(this@FormActivityTest).create()
                    confirmAlert.setTitle("Confirm?")
                    confirmAlert.setButton(AlertDialog.BUTTON_POSITIVE, this@FormActivityTest.getString(android.R.string.ok)) { _, _ ->
                        // Get Form Element in two ways
                        val dateToDeleteElement = formBuilder.getFormElement<FormPickerDateElement>(Tag.Date.ordinal)
                        dateToDeleteElement.clear()
                        formBuilder.onValueChanged(dateToDeleteElement)

                        val dateToDeleteElementIndex = formBuilder.getElementAtIndex(9) as FormPickerDateElement
                        dateToDeleteElementIndex.clear()
                        formBuilder.onValueChanged(dateToDeleteElementIndex)

                        // Display Valid Form
                        Toast.makeText(this@FormActivityTest,
                                formBuilder.isValidForm.toString(),
                                Toast.LENGTH_SHORT).show()

                        formBuilder.clearAll()

                        formBuilder.setError(dateToDeleteElementIndex.errorView!!, "That's an error")
                    }
                    confirmAlert.setButton(AlertDialog.BUTTON_NEGATIVE, this@FormActivityTest.getString(android.R.string.cancel)) { _, _ ->
                    }
                    confirmAlert.show()
                }
            }
            text(Hidden.ordinal) {
                title = "Hidden"
                visible = false
            }
            label(LabelElement.ordinal) {
                title = "Label"
            }
        }

        val disabledButton = FormButtonElement(Disabled.ordinal)
        disabledButton.value = "Disabled Button"
        disabledButton.visible = true
        disabledButton.enabled = false
        disabledButton.addValueObserver { _, _ ->
            val confirmAlert = AlertDialog.Builder(this@FormActivityTest).create()
            confirmAlert.setTitle("Disabled?")
            confirmAlert.setButton(AlertDialog.BUTTON_POSITIVE, this@FormActivityTest.getString(android.R.string.ok)) { _, _ ->

            }
            confirmAlert.show()
        }

        // Add disabledButton using addFormElement
        formBuilder.addFormElement(disabledButton)
        formBuilder.setItems()

        // Add disabledButton using addFormElements
        formBuilder.addFormElements(listOf(disabledButton))
    }
}
