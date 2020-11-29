package com.thejuki.kformmasterexample

import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.thejuki.kformmaster.helper.*
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener
import com.thejuki.kformmaster.model.BaseFormElement
import com.thejuki.kformmaster.model.FormPickerDateElement
import com.thejuki.kformmasterexample.FormListenerActivity.Tag.*
import com.thejuki.kformmasterexample.adapter.ContactAutoCompleteAdapter
import com.thejuki.kformmasterexample.adapter.EmailAutoCompleteAdapter
import com.thejuki.kformmasterexample.databinding.ActivityFullscreenFormBinding
import com.thejuki.kformmasterexample.item.ContactItem
import com.thejuki.kformmasterexample.item.ListItem
import java.text.SimpleDateFormat
import java.util.*
import java.util.Date

/**
 * Form Listener Activity
 *
 * OnFormElementValueChangedListener is overridden at the activity level
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormListenerActivity : AppCompatActivity(), OnFormElementValueChangedListener {

    private lateinit var binding: ActivityFullscreenFormBinding
    private lateinit var formBuilder: FormBuildHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFullscreenFormBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
            actionBar.title = getString(R.string.form_with_listener)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
        }

    }

    private val fruits = listOf(ListItem(id = 1, name = "Banana"),
            ListItem(id = 2, name = "Orange"),
            ListItem(id = 3, name = "Mango"),
            ListItem(id = 4, name = "Guava")
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
        formBuilder = form(binding.recyclerView, this, true) {
            imageView(ImageViewElement.ordinal) {
                onSelectImage = { file ->
                    // If file is null, that means an error occurred trying to select the image
                    if (file != null) {
                        Toast.makeText(this@FormListenerActivity, file.name, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@FormListenerActivity, "Error getting the image", Toast.LENGTH_LONG).show()
                    }
                }
            }
            header { title = getString(R.string.PersonalInfo) }
            email(Email.ordinal) {
                title = getString(R.string.email)
                hint = getString(R.string.email_hint)
            }
            password(Password.ordinal) {
                title = getString(R.string.password)
            }
            phone(Phone.ordinal) {
                title = getString(R.string.Phone)
                value = "+8801712345678"
            }
            header { title = getString(R.string.FamilyInfo) }
            text(Location.ordinal) {
                title = getString(R.string.Location)
                value = "Dhaka"
            }
            textArea(Address.ordinal) {
                title = getString(R.string.Address)
                value = ""
            }
            number(ZipCode.ordinal) {
                title = getString(R.string.ZipCode)
                value = "1000"
            }
            header { title = getString(R.string.Schedule) }
            date(Tag.Date.ordinal) {
                title = getString(R.string.Date)
                dateValue = Date()
                dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
            }
            time(Time.ordinal) {
                title = getString(R.string.Time)
                dateValue = Date()
                dateFormat = SimpleDateFormat("hh:mm a", Locale.US)
            }
            dateTime(DateTime.ordinal) {
                title = getString(R.string.DateTime)
                dateValue = Date()
                dateFormat = SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US)
            }
            header { title = getString(R.string.PreferredItems) }
            dropDown<ListItem>(SingleItem.ordinal) {
                title = getString(R.string.SingleItem)
                dialogTitle = getString(R.string.SingleItem)
                options = fruits
                value = ListItem(id = 1, name = "Banana")
            }
            multiCheckBox<List<ListItem>>(MultiItems.ordinal) {
                title = getString(R.string.MultiItems)
                dialogTitle = getString(R.string.MultiItems)
                options = fruits
                value = listOf(ListItem(id = 1, name = "Banana"))
            }
            autoComplete<ContactItem>(AutoCompleteElement.ordinal) {
                title = getString(R.string.AutoComplete)
                arrayAdapter = ContactAutoCompleteAdapter(this@FormListenerActivity,
                        android.R.layout.simple_list_item_1, defaultItems =
                arrayListOf(ContactItem(id = 1, value = "", label = "Try \"Apple May\"")))
                dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
                value = ContactItem(id = 1, value = "John Smith", label = "John Smith (Tester)")
            }
            autoCompleteToken<List<ContactItem>>(AutoCompleteTokenElement.ordinal) {
                title = getString(R.string.AutoCompleteToken)
                arrayAdapter = EmailAutoCompleteAdapter(this@FormListenerActivity,
                        android.R.layout.simple_list_item_1)
                dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
                hint = "Try \"Apple May\""
            }
            textView(TextViewElement.ordinal) {
                title = getString(R.string.TextView)
                value = "This is readonly!"
            }
            label(LabelElement.ordinal) {
                title = getString(R.string.Label)
                editViewGravity = Gravity.START
            }
            header { title = getString(R.string.MarkComplete) }
            switch<String>(SwitchElement.ordinal) {
                title = getString(R.string.Switch)
                value = "Yes"
                onValue = "Yes"
                offValue = "No"
            }
            slider(SliderElement.ordinal) {
                title = getString(R.string.Slider)
                value = 50
                min = 0
                max = 100
                steps = 20
            }
            checkBox<Boolean>(CheckBoxElement.ordinal) {
                title = getString(R.string.CheckBox)
                value = true
                checkedValue = true
                unCheckedValue = false
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
                value = ListItem(id = 1, name = "Banana")
            }
            button(ButtonElement.ordinal) {
                value = getString(R.string.Button)
                valueObservers.add { newValue, element ->
                    val confirmAlert = AlertDialog.Builder(this@FormListenerActivity).create()
                    confirmAlert.setTitle(this@FormListenerActivity.getString(R.string.Confirm))
                    confirmAlert.setButton(AlertDialog.BUTTON_POSITIVE, this@FormListenerActivity.getString(android.R.string.ok)) { _, _ ->
                        // Could be used to clear another field:
                        val dateToDeleteElement = formBuilder.getFormElement<FormPickerDateElement>(Tag.Date.ordinal)
                        // Display current date
                        Toast.makeText(this@FormListenerActivity,
                                dateToDeleteElement.value?.getTime().toString(),
                                Toast.LENGTH_SHORT).show()
                        dateToDeleteElement.clear()
                        formBuilder.onValueChanged(dateToDeleteElement)
                    }
                    confirmAlert.setButton(AlertDialog.BUTTON_NEGATIVE, this@FormListenerActivity.getString(android.R.string.cancel)) { _, _ ->
                    }
                    confirmAlert.show()
                }
            }
        }
        formBuilder.isValidForm
    }

    override fun onValueChanged(formElement: BaseFormElement<*>) {
        Toast.makeText(this, formElement.valueAsString, Toast.LENGTH_SHORT).show()
    }
}