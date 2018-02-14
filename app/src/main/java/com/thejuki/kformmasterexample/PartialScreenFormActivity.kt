package com.thejuki.kformmasterexample

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import com.thejuki.kformmaster.helper.FormBuildHelper
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
    }

    private fun setupForm() {

        mFormBuilder = FormBuildHelper(this, object : OnFormElementValueChangedListener {
            override fun onValueChanged(formElement: BaseFormElement<*>) {

            }
        })
        mFormBuilder!!.attachRecyclerView(this, recyclerView)

        val elements: MutableList<BaseFormElement<*>> = mutableListOf()

        val personalInfoHeader = FormHeader.createInstance(getString(R.string.PersonalInfo))
        elements.add(personalInfoHeader)

        // Create an element with setters
        val emailElement = FormEditTextElement<String>(Email.ordinal)
                .setType(BaseFormElement.TYPE_EDITTEXT_EMAIL)
                .setTitle(getString(R.string.email))
                .setHint(getString(R.string.email_hint))
        elements.add(emailElement)

        // Create an element and then set variables
        val phoneElement = FormEditTextElement<String>(Phone.ordinal)
        phoneElement.mType = BaseFormElement.TYPE_EDITTEXT_PHONE
        phoneElement.mTitle = getString(R.string.Phone)
        phoneElement.mValue = "+8801712345678"
        elements.add(phoneElement)

        val familyInfoHeader = FormHeader.createInstance(getString(R.string.FamilyInfo))
        elements.add(familyInfoHeader)

        val locationElement = FormEditTextElement<String>(Location.ordinal)
        locationElement.mType = BaseFormElement.TYPE_EDITTEXT_TEXT_SINGLELINE
        locationElement.mTitle = getString(R.string.Location)
        locationElement.mValue = "Dhaka"
        elements.add(locationElement)

        val addressElement = FormEditTextElement<String>(Address.ordinal)
        addressElement.mType = BaseFormElement.TYPE_EDITTEXT_TEXT_MULTILINE
        addressElement.mTitle = getString(R.string.Address)
        addressElement.mValue = ""
        elements.add(addressElement)

        val zipCodeElement = FormEditTextElement<String>(ZipCode.ordinal)
        zipCodeElement.mType = BaseFormElement.TYPE_EDITTEXT_NUMBER
        zipCodeElement.mTitle = getString(R.string.ZipCode)
        zipCodeElement.mValue = "1000"
        elements.add(zipCodeElement)

        val scheduleHeader = FormHeader.createInstance(getString(R.string.Schedule))
        elements.add(scheduleHeader)

        val dateElement = FormPickerDateElement(Tag.Date.ordinal)
        dateElement.mType = BaseFormElement.TYPE_EDITTEXT_NUMBER
        dateElement.mTitle = getString(R.string.Date)
        dateElement.mValue = FormPickerDateElement.DateHolder(Date(),
                SimpleDateFormat("MM/dd/yyyy", Locale.US))
        elements.add(dateElement)

        val timeElement = FormPickerTimeElement(Time.ordinal)
        timeElement.mType = BaseFormElement.TYPE_EDITTEXT_NUMBER
        timeElement.mTitle = getString(R.string.Time)
        timeElement.mValue = FormPickerTimeElement.TimeHolder(Date(),
                SimpleDateFormat("hh:mm a", Locale.US))
        elements.add(timeElement)

        val dateTimeElement = FormPickerDateTimeElement(DateTime.ordinal)
        dateTimeElement.mType = BaseFormElement.TYPE_EDITTEXT_NUMBER
        dateTimeElement.mTitle = getString(R.string.DateTime)
        dateTimeElement.mValue = FormPickerDateTimeElement.DateTimeHolder(Date(),
                SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US))
        elements.add(dateTimeElement)

        val preferredItemsHeader = FormHeader.createInstance(getString(R.string.PreferredItems))
        elements.add(preferredItemsHeader)

        val fruits = listOf<ListItem>(ListItem(id = 1, name = "Banana"),
                ListItem(id = 2, name = "Orange"),
                ListItem(id = 3, name = "Mango"),
                ListItem(id = 4, name = "Guava")
        )

        val singleItemElement = FormPickerDropDownElement<ListItem>(SingleItem.ordinal)
        singleItemElement.dialogTitle = getString(R.string.SingleItem)
        singleItemElement.mTitle = getString(R.string.SingleItem)
        singleItemElement.mOptions = fruits
        singleItemElement.mValue = ListItem(id = 1, name = "Banana")
        elements.add(singleItemElement)

        val multipleItemsElement = FormPickerMultiCheckBoxElement<ListItem>(MultiItems.ordinal)
        multipleItemsElement.dialogTitle = getString(R.string.MultiItems)
        multipleItemsElement.mTitle = getString(R.string.MultiItems)
        multipleItemsElement.mOptions = fruits
        multipleItemsElement.mOptionsSelected = listOf(ListItem(id = 1, name = "Banana"))
        elements.add(multipleItemsElement)

        val autoCompleteElement = FormAutoCompleteElement<ContactItem>(AutoCompleteElement.ordinal)
        autoCompleteElement.arrayAdapter = ContactAutoCompleteAdapter(this,
                android.R.layout.simple_list_item_1, defaultItems =
        arrayListOf(ContactItem(id = 1, value = "", label = "Try \"Apple May\"")))
        autoCompleteElement.dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
        autoCompleteElement.mTitle = getString(R.string.AutoComplete)
        autoCompleteElement.mValue =
                ContactItem(id = 1, value = "John Smith", label = "John Smith (Tester)")
        elements.add(autoCompleteElement)

        val autoCompleteTokenElement = FormTokenAutoCompleteElement<ArrayList<ContactItem>>(AutoCompleteTokenElement.ordinal)
        autoCompleteTokenElement.arrayAdapter = EmailAutoCompleteAdapter(this,
                android.R.layout.simple_list_item_1)
        autoCompleteTokenElement.dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
        autoCompleteTokenElement.mTitle = getString(R.string.AutoCompleteToken)
        elements.add(autoCompleteTokenElement)

        val textViewElement = FormTextViewElement<String>(TextViewElement.ordinal)
        textViewElement.mTitle = getString(R.string.TextView)
        textViewElement.mValue = "This is readonly!"
        elements.add(textViewElement)

        val buttonElement = FormButtonElement<String>(ButtonElement.ordinal)
        buttonElement.mValue = getString(R.string.Button)
        // Listen for this element's changes. For a button, this just means the button was clicked.
        buttonElement.mValueChanged = object : OnFormElementValueChangedListener {
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
        elements.add(buttonElement)

        mFormBuilder!!.addFormElements(elements)
        mFormBuilder!!.refreshView()
    }
}
