package com.thejuki.kformmasterexample

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.thejuki.kformmaster.helper.*
import com.thejuki.kformmaster.model.FormHeader
import com.thejuki.kformmaster.model.FormPickerDateElement
import com.thejuki.kformmasterexample.FullscreenFormActivity.Tag.*
import com.thejuki.kformmasterexample.adapter.ContactAutoCompleteAdapter
import com.thejuki.kformmasterexample.adapter.EmailAutoCompleteAdapter
import com.thejuki.kformmasterexample.item.ContactItem
import com.thejuki.kformmasterexample.item.ListItem
import kotlinx.android.synthetic.main.activity_fullscreen_form.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen_form)

        setupToolBar()

        setupForm()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_form, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                R.id.action_validate -> {
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
        formBuilder.elements.forEach({
            if (it !is FormHeader) {
                it.visible = !it.visible
            }
        })
    }

    private fun validate() {
        val alert = AlertDialog.Builder(this@FullscreenFormActivity).create()

        if (formBuilder.isValidForm) {
            alert.setTitle(this@FullscreenFormActivity.getString(R.string.FormValid))
        } else {
            alert.setTitle(this@FullscreenFormActivity.getString(R.string.FormInvalid))

            formBuilder.elements.forEach({
                if (!it.isValid) {
                    it.error = "This field is required!"
                }
            })
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
        CheckBoxElement,
    }

    private fun setupForm() {
        formBuilder = form(this, recyclerView, cacheForm = true) {
            header { title = getString(R.string.PersonalInfo) }
            email(Email.ordinal) {
                title = getString(R.string.email)
                hint = getString(R.string.email_hint)
                value = "mail@mail.com"
                required = true
                valueObservers.add({ newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            password(Password.ordinal) {
                title = getString(R.string.password)
                value = "Password123"
                required = true
                valueObservers.add({ newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            phone(Phone.ordinal) {
                title = getString(R.string.Phone)
                value = "+8801712345678"
                required = true
                valueObservers.add({ newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            header { title = getString(R.string.FamilyInfo) }
            text(Location.ordinal) {
                title = getString(R.string.Location)
                value = "Dhaka"
                required = true
                valueObservers.add({ newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            textArea(Address.ordinal) {
                title = getString(R.string.Address)
                value = "123 Street"
                required = true
                valueObservers.add({ newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            number(ZipCode.ordinal) {
                title = getString(R.string.ZipCode)
                value = "123456"
                numbersOnly = true
                required = true
                valueObservers.add({ newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            header { title = getString(R.string.Schedule) }
            date(Tag.Date.ordinal) {
                title = getString(R.string.Date)
                dateValue = Date()
                dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
                required = true
                valueObservers.add({ newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            time(Time.ordinal) {
                title = getString(R.string.Time)
                dateValue = Date()
                dateFormat = SimpleDateFormat("hh:mm a", Locale.US)
                required = true
                valueObservers.add({ newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            dateTime(DateTime.ordinal) {
                title = getString(R.string.DateTime)
                dateValue = Date()
                dateFormat = SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US)
                required = true
                valueObservers.add({ newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            header { title = getString(R.string.PreferredItems) }
            dropDown<ListItem>(SingleItem.ordinal) {
                title = getString(R.string.SingleItem)
                dialogTitle = getString(R.string.SingleItem)
                options = fruits
                value = ListItem(id = 1, name = "Banana")
                required = true
                valueObservers.add({ newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            multiCheckBox<ListItem>(MultiItems.ordinal) {
                title = getString(R.string.MultiItems)
                dialogTitle = getString(R.string.MultiItems)
                options = fruits
                optionsSelected = listOf(ListItem(id = 1, name = "Banana"))
                required = true
                valueObservers.add({ newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            autoComplete<ContactItem>(AutoCompleteElement.ordinal) {
                title = getString(R.string.AutoComplete)
                arrayAdapter = ContactAutoCompleteAdapter(this@FullscreenFormActivity,
                        android.R.layout.simple_list_item_1, defaultItems =
                arrayListOf(ContactItem(id = 1, value = "", label = "Try \"Apple May\"")))
                dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
                value = ContactItem(id = 1, value = "John Smith", label = "John Smith (Tester)")
                required = true
                valueObservers.add({ newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            autoCompleteToken<ArrayList<ContactItem>>(AutoCompleteTokenElement.ordinal) {
                title = getString(R.string.AutoCompleteToken)
                arrayAdapter = EmailAutoCompleteAdapter(this@FullscreenFormActivity,
                        android.R.layout.simple_list_item_1)
                dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
                hint = "Try \"Apple May\""
                value = arrayListOf(ContactItem(id = 1, value = "John.Smith@mail.com", label = "John Smith (Tester)"))
                required = true
                valueObservers.add({ newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            textView(TextViewElement.ordinal) {
                title = getString(R.string.TextView)
                value = "This is readonly!"
            }
            header { title = getString(R.string.MarkComplete) }
            switch<String>(SwitchElement.ordinal) {
                title = getString(R.string.Switch)
                value = "Yes"
                onValue = "Yes"
                offValue = "No"
                required = true
                valueObservers.add({ newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            slider(SliderElement.ordinal) {
                title = getString(R.string.Slider)
                value = 50
                min = 0
                max = 100
                steps = 20
                required = true
                valueObservers.add({ newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            checkBox<Boolean>(CheckBoxElement.ordinal) {
                title = getString(R.string.CheckBox)
                value = true
                checkedValue = true
                unCheckedValue = false
                required = true
                valueObservers.add({ newValue, element ->
                    Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            button(ButtonElement.ordinal) {
                value = getString(R.string.Button)
                valueObservers.add({ newValue, element ->
                    val confirmAlert = AlertDialog.Builder(this@FullscreenFormActivity).create()
                    confirmAlert.setTitle(this@FullscreenFormActivity.getString(R.string.Confirm))
                    confirmAlert.setButton(AlertDialog.BUTTON_POSITIVE, this@FullscreenFormActivity.getString(android.R.string.ok), { _, _ ->
                        // Could be used to clear another field:
                        val dateToDeleteElement = formBuilder.getFormElement<FormPickerDateElement>(Tag.Date.ordinal)
                        // Display current date
                        Toast.makeText(this@FullscreenFormActivity,
                                dateToDeleteElement.value?.getTime().toString(),
                                Toast.LENGTH_SHORT).show()
                        dateToDeleteElement.clear()
                        formBuilder.onValueChanged(dateToDeleteElement)
                    })
                    confirmAlert.setButton(AlertDialog.BUTTON_NEGATIVE, this@FullscreenFormActivity.getString(android.R.string.cancel), { _, _ ->
                    })
                    confirmAlert.show()
                })
            }
        }
    }
}