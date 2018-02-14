package com.thejuki.kformmasterexample

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import com.thejuki.kformmaster.helper.*
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener
import com.thejuki.kformmaster.model.*
import com.thejuki.kformmasterexample.PartialScreenFormActivity.Tag.*
import com.thejuki.kformmasterexample.adapter.ContactAutoCompleteAdapter
import com.thejuki.kformmasterexample.adapter.EmailAutoCompleteAdapter
import com.thejuki.kformmasterexample.item.ContactItem
import com.thejuki.kformmasterexample.item.ListItem
import kotlinx.android.synthetic.main.activity_partial_screen_form.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.Date

/**
 * Partial Screen Form Activity
 *
 * The scrollable form is only displayed partially on the screen
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class PartialScreenFormActivity : AppCompatActivity() {

    private var mFormBuilder: FormBuildHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partial_screen_form)

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
            actionBar.title = getString(R.string.partial_screen_form)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
        }

    }

    private val fruits = listOf<ListItem>(ListItem(id = 1, name = "Banana"),
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
        SwitchElement,
        SliderElement,
    }

    private fun setupForm() {

        val listener = object : OnFormElementValueChangedListener {
            override fun onValueChanged(formElement: BaseFormElement<*>) {

            }
        }

        mFormBuilder = form(this, recyclerView, listener) {
            header { title = getString(R.string.PersonalInfo) }
            editText<String>(Email.ordinal) {
                title = getString(R.string.email)
                type = BaseFormElement.TYPE_EDITTEXT_EMAIL
                hint = getString(R.string.email_hint)
            }
            editText<String>(Password.ordinal) {
                title = getString(R.string.password)
                type = BaseFormElement.TYPE_EDITTEXT_PASSWORD
            }
            editText<String>(Phone.ordinal) {
                title = getString(R.string.Phone)
                type = BaseFormElement.TYPE_EDITTEXT_PHONE
                value = "+8801712345678"
            }
            header { title = getString(R.string.FamilyInfo) }
            editText<String>(Location.ordinal) {
                title = getString(R.string.Location)
                type = BaseFormElement.TYPE_EDITTEXT_TEXT_SINGLELINE
                value = "Dhaka"
            }
            editText<String>(Address.ordinal) {
                title = getString(R.string.Address)
                type = BaseFormElement.TYPE_EDITTEXT_TEXT_MULTILINE
                value = ""
            }
            editText<String>(ZipCode.ordinal) {
                title = getString(R.string.ZipCode)
                type = BaseFormElement.TYPE_EDITTEXT_NUMBER
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
            multiCheckBox<ListItem>(MultiItems.ordinal) {
                title = getString(R.string.MultiItems)
                dialogTitle = getString(R.string.MultiItems)
                options = fruits
                optionsSelected = listOf(ListItem(id = 1, name = "Banana"))
            }
            autoComplete<ContactItem>(AutoCompleteElement.ordinal) {
                title = getString(R.string.AutoComplete)
                arrayAdapter = ContactAutoCompleteAdapter(this@PartialScreenFormActivity,
                        android.R.layout.simple_list_item_1, defaultItems =
                arrayListOf(ContactItem(id = 1, value = "", label = "Try \"Apple May\"")))
                dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
                value = ContactItem(id = 1, value = "John Smith", label = "John Smith (Tester)")
            }
            autoCompleteToken<ArrayList<ContactItem>>(AutoCompleteTokenElement.ordinal) {
                title = getString(R.string.AutoCompleteToken)
                arrayAdapter = EmailAutoCompleteAdapter(this@PartialScreenFormActivity,
                        android.R.layout.simple_list_item_1)
                dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
            }
            textView<String>(TextViewElement.ordinal) {
                title = getString(R.string.TextView)
                value = "This is readonly!"
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
            button<String>(ButtonElement.ordinal) {
                value = getString(R.string.Button)
                valueChanged = object : OnFormElementValueChangedListener {
                    override fun onValueChanged(formElement: BaseFormElement<*>) {
                        val confirmAlert = AlertDialog.Builder(this@PartialScreenFormActivity).create()
                        confirmAlert.setTitle(this@PartialScreenFormActivity.getString(R.string.Confirm))
                        confirmAlert.setButton(AlertDialog.BUTTON_POSITIVE, this@PartialScreenFormActivity.getString(android.R.string.ok), { _, _ ->
                            // Could be used to clear another field:
                            val dateToDeleteElement = mFormBuilder!!.getFormElement(Tag.Date.ordinal)
                            // Display current date
                            Toast.makeText(this@PartialScreenFormActivity,
                                    (dateToDeleteElement!!.getValue() as FormPickerDateElement.DateHolder).getTime().toString(),
                                    Toast.LENGTH_SHORT).show()
                            (dateToDeleteElement.getValue() as FormPickerDateElement.DateHolder).useCurrentDate()
                            mFormBuilder!!.onValueChanged(dateToDeleteElement)
                            mFormBuilder!!.refreshView()
                        })
                        confirmAlert.setButton(AlertDialog.BUTTON_NEGATIVE, this@PartialScreenFormActivity.getString(android.R.string.cancel), { _, _ ->
                        })
                        confirmAlert.show()
                    }
                }
            }
        }
    }
}