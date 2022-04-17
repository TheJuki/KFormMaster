package com.thejuki.kformmaster

import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.InputFilter
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.thejuki.kformmaster.extensions.dpToPx
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.item.ContactItem
import com.thejuki.kformmaster.model.FormEmailEditTextElement
import com.thejuki.kformmaster.model.FormImageElement
import com.thejuki.kformmaster.model.FormInlineDatePickerElement
import com.thejuki.kformmaster.model.FormPhoneEditTextElement
import com.thejuki.kformmaster.token.ItemsCompletionView
import com.thejuki.kformmaster.widget.FormElementMargins
import com.thejuki.kformmaster.widget.SegmentedGroup
import org.hamcrest.Matchers.*
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Form Instrumented Test
 *
 * The Great Form Instrumented Test
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class FormInstrumentedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(FormActivityTest::class.java)

    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    private fun getActivity(): Activity? {
        var activity: Activity? = null
        activityRule.scenario.onActivity {
            activity = it
        }
        return activity
    }

    private fun getFormBuildHelper(): FormBuildHelper {
        val activity = getActivity() as FormActivityTest
        return activity.formBuilder
    }

    @Test
    fun form_modelColorsAreSet() {
        val formBuildHelper = getFormBuildHelper()

        instrumentation.runOnMainSync {
            // Get email form element
            val email = formBuildHelper.getFormElement<FormEmailEditTextElement>(FormActivityTest.Tag.Email.ordinal)

            // Check initially set colors
            checkColors(email)

            email.backgroundColor = Color.RED
            email.titleTextColor = Color.RED
            email.titleFocusedTextColor = Color.RED
            email.valueTextColor = Color.RED
            email.errorTextColor = Color.RED
            email.hintTextColor = Color.RED

            // Check new colors
            checkColors(email)
        }
    }

    private fun checkColors(email: FormEmailEditTextElement) {
        // Check set colors
        email.titleView?.let {
            val states = arrayOf(intArrayOf(android.R.attr.state_focused), intArrayOf())
            val colors = intArrayOf(email.titleFocusedTextColor
                    ?: ContextCompat.getColor(it.context,
                            R.color.colorFormMasterElementFocusedTitle),
                    email.titleTextColor
                            ?: email.titleView?.textColors?.getColorForState(intArrayOf(),
                                    (ContextCompat.getColor(it.context, R.color.colorFormMasterElementTextTitle)))
                            ?: -1
            )
            val colorState = ColorStateList(states, colors)

            // Check title focused text color
            assertEquals(colorState.getColorForState(intArrayOf(android.R.attr.state_focused), ContextCompat.getColor(it.context,
                    R.color.colorFormMasterElementFocusedTitle)),
                    it.textColors.getColorForState(intArrayOf(android.R.attr.state_focused), ContextCompat.getColor(it.context,
                            R.color.colorFormMasterElementFocusedTitle)))

            // Check title text color
            assertEquals(colorState.getColorForState(intArrayOf(), ContextCompat.getColor(it.context,
                    R.color.colorFormMasterElementTextTitle)),
                    it.textColors.getColorForState(intArrayOf(), ContextCompat.getColor(it.context,
                            R.color.colorFormMasterElementTextTitle)))
        }

        email.itemView?.let {
            // Check background color
            assertEquals(ColorDrawable(email.backgroundColor!!).color, (it.background as ColorDrawable).color)
        }

        email.editView?.let {
            if (it is TextView) {
                // Check hint color
                assertEquals(ColorStateList.valueOf(email.hintTextColor!!), it.hintTextColors)

                // Check value text color
                assertEquals(ColorStateList.valueOf(email.valueTextColor!!), it.textColors)
            }
        }

        email.errorView?.let {
            // Check error text color
            assertEquals(ColorStateList.valueOf(email.errorTextColor!!), it.textColors)
        }
    }

    @Test
    fun form_modelValuesAreSet() {
        val formBuildHelper = getFormBuildHelper()

        instrumentation.runOnMainSync {
            // Get email form element
            val email = formBuildHelper.getFormElement<FormEmailEditTextElement>(FormActivityTest.Tag.Email.ordinal)

            // Get email error text view
            val emailErrorTextView = email.errorView!!

            // Test Error visibility
            formBuildHelper.setError(emailErrorTextView, "Test")
            assertTrue(emailErrorTextView.text == "Test")
            assertTrue(emailErrorTextView.visibility == View.VISIBLE)

            formBuildHelper.setError(emailErrorTextView, null)
            assertTrue(emailErrorTextView.visibility == View.GONE)

            // Last Id should have incremented
            assertTrue(formBuildHelper.lastId > 0)

            // Clear the form values
            formBuildHelper.clearAll()

            // Check if the email form element is invalid now
            assertFalse(email.isValid)

            // Check if the form is invalid now
            assertFalse(formBuildHelper.isValidForm)

            // Check dynamic title view
            email.titleView?.let {
                email.title = null
                assertTrue(it.text.isNullOrEmpty())
                email.displayTitle = false
                assertTrue(it.visibility == View.GONE)
            }

            // Check edit view
            email.editView?.let {
                email.value = "test"
                email.hint = "test@example.com"
                email.maxLength = 20
                email.maxLines = 1
                email.clearable = true
                email.clearOnFocus = true
                email.editViewGravity = Gravity.START

                if (it is TextView) {
                    email.clear()
                    assertTrue(email.value.isNullOrEmpty())
                    assertTrue(it.text.isNullOrEmpty())

                    assertTrue(it.hint == email.hint)

                    assertEquals(email.maxLength!!, (it.filters[0] as InputFilter.LengthFilter).max)
                    email.maxLength = null
                    assertEquals(0, it.filters.size)

                    assertEquals(1, it.maxLines)

                    assertEquals(Gravity.TOP or Gravity.START, it.gravity)
                    email.editViewGravity = Gravity.END
                    assertEquals(Gravity.TOP or Gravity.END, it.gravity)

                    email.editViewGravity = Gravity.CENTER
                    assertEquals(Gravity.CENTER, it.gravity)
                }

                if (it is com.thejuki.kformmaster.widget.ClearableEditText) {
                    assertTrue(it.displayClear)
                }
            }

            // Check dynamic main layout view
            email.mainLayoutView?.let {
                email.margins = FormElementMargins(5, 10, 15, 20)

                if (it.layoutParams is ViewGroup.MarginLayoutParams) {
                    val p = it.layoutParams as ViewGroup.MarginLayoutParams
                    assertEquals(email.margins?.left.dpToPx(), p.leftMargin)
                    assertEquals(email.margins?.top.dpToPx(), p.topMargin)
                    assertEquals(email.margins?.right.dpToPx(), p.rightMargin)
                    assertEquals(email.margins?.bottom.dpToPx(), p.bottomMargin)
                }
            }

            // Check dynamic divider view
            email.dividerView?.let {
                email.displayDivider = false
                assertTrue(it.visibility == View.GONE)
                email.displayDivider = true
                assertTrue(it.visibility == View.VISIBLE)
            }
        }

    }

    @Test
    fun form_IsValid() {
        val formBuildHelper = getFormBuildHelper()

        // Get email form element
        val email = formBuildHelper.getFormElement<FormEmailEditTextElement>(FormActivityTest.Tag.Email.ordinal)
        val emailByIndex = formBuildHelper.getElementAtIndex(2) as FormEmailEditTextElement

        // Both methods to get a form element should be equal
        assertEquals(email, emailByIndex)

        email.validityCheck = {
            if (email.value != null) android.util.Patterns.EMAIL_ADDRESS.matcher(email.value ?: "").matches() else false
        }

        // Email should be required
        assertTrue(email.isValid)

        // Form should be valid
        assertTrue(formBuildHelper.isValidForm)

        // Check if the email form element is valid
        assertTrue(email.isValid)

        // Initialize with an invalid email
        var newEmail = "example.com"

        // Make the email form element invalid
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<RecyclerView.ViewHolder>(2))
        onView(allOf(`is`(instanceOf(AppCompatEditText::class.java)),
                hasTextViewInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)))
                .perform(replaceText(newEmail))

        // Check if the new value was set in the UI
        onView(withText(newEmail))
                .check(matches(isDisplayed()))

        // Check if the new value was set in the model
        assertEquals(newEmail, email.value)

        // Check if the form is invalid now
        assertFalse(formBuildHelper.isValidForm)

        // Check if the email form element is invalid now
        assertFalse(email.isValid)

        // Set error
        instrumentation.runOnMainSync {
            email.error = "Invalid email!"
        }
        instrumentation.waitForIdleSync()

        // Check if the new error was set in the UI
        onView(withText(email.error))
                .check(matches(isDisplayed()))

        // Set to valid email
        newEmail = "test@example.com"

        // Make the email form element valid again
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<RecyclerView.ViewHolder>(2))
        onView(allOf(`is`(instanceOf(AppCompatEditText::class.java)),
                hasTextViewInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)))
                .perform(replaceText(newEmail))

        // Check if the new value was set in the UI
        onView(withText(newEmail))
                .check(matches(isDisplayed()))

        // Check if the new value was set in the model
        assertEquals(newEmail, email.value)

        // Check if the form is valid now
        assertTrue(formBuildHelper.isValidForm)

        // Check if the email form element is valid now
        assertTrue(email.isValid)

        // Remove error
        instrumentation.runOnMainSync {
            email.error = null
        }
        instrumentation.waitForIdleSync()

        // Check if the error was removed in the UI
        onView(withText("Invalid email!"))
                .check(isNotDisplayed())
    }

    @Test
    fun phone_IsFormatted() {
        val formBuildHelper = getFormBuildHelper()

        // Get phone form element
        val phone = formBuildHelper.getFormElement<FormPhoneEditTextElement>(FormActivityTest.Tag.Phone.ordinal)

        // Scroll to Phone text
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<RecyclerView.ViewHolder>(4))

        // Set a new phone number
        val newPhone = "555 123 9999"

        onView(allOf(`is`(instanceOf(AppCompatEditText::class.java)),
                hasTextViewInputType(InputType.TYPE_CLASS_PHONE or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)))
                .perform(replaceText(newPhone))

        // Check if the new value was formatted in the UI
        onView(withText("+1 (555) 123-9999"))
                .check(matches(isDisplayed()))

        // Check if the new value was formatted in the model
        assertEquals("+1 (555) 123-9999", phone.value)
    }

    @Test
    fun header_isDisplayed() {
        // Check if Header 1 is displayed on the form
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<RecyclerView.ViewHolder>(1))
        onView(withText("Header 1"))
                .check(matches(isDisplayed()))

        // Check if Header 2 is displayed on the form
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<RecyclerView.ViewHolder>(5))
        onView(withText("Header 2"))
                .check(matches(isDisplayed()))

        // Check if Header 3 is displayed on the form
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<RecyclerView.ViewHolder>(9))
        onView(withText("Header 3"))
                .check(matches(isDisplayed()))

        // Check if Header 4 is displayed on the form
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<RecyclerView.ViewHolder>(13))
        onView(withText("Header 4"))
                .check(matches(isDisplayed()))

        // Check if Header 5 is displayed on the form
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<RecyclerView.ViewHolder>(19))
        onView(withText("Header 5"))
                .check(matches(isDisplayed()))
    }

    @Test
    fun editTextElement_shouldHaveInputType() {
        // Check Email type
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<RecyclerView.ViewHolder>(2))
        onView(allOf(`is`(instanceOf(AppCompatEditText::class.java)),
                hasTextViewInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)))
                .check(matches(isDisplayed()))

        // Check Password type
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<RecyclerView.ViewHolder>(3))
        onView(allOf(`is`(instanceOf(AppCompatEditText::class.java)),
                hasTextViewInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)))
                .check(matches(isDisplayed()))

        // Check Phone type
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<RecyclerView.ViewHolder>(4))
        onView(allOf(`is`(instanceOf(AppCompatEditText::class.java)),
                hasTextViewInputType(InputType.TYPE_CLASS_PHONE or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)))
                .check(matches(isDisplayed()))

        // Check Single Line type
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<RecyclerView.ViewHolder>(6))
        onView(allOf(`is`(instanceOf(AppCompatEditText::class.java)),
                hasTextViewInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS),
                hasTextViewSingleLine()))
                .check(matches(isDisplayed()))

        // Check Multi Line type
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<RecyclerView.ViewHolder>(7))
        onView(allOf(`is`(instanceOf(AppCompatEditText::class.java)),
                hasTextViewInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_FLAG_MULTI_LINE)))
                .check(matches(isDisplayed()))

        // Check Number type
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<RecyclerView.ViewHolder>(8))
        onView(allOf(`is`(instanceOf(AppCompatEditText::class.java)),
                hasTextViewInputType(InputType.TYPE_CLASS_NUMBER)))
                .check(matches(isDisplayed()))
    }

    @Test
    fun text_shouldNotBeDisplayed_whenVisibleIsFalse() {
        // Text element's visible property is set to false and should not show up on the form
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<RecyclerView.ViewHolder>(26))
        onView(withText("Hidden"))
                .check(matches(not(isDisplayed())))
    }

    @Test
    fun label_isDisplayed() {
        // Check if the Label is displayed on the form
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<RecyclerView.ViewHolder>(27))
        onView(withText("Label"))
                .check(matches(isDisplayed()))
    }

    @Test
    fun progress_changes_whenProgressed() {
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<RecyclerView.ViewHolder>(23))
        onView(withClassName(equalTo(ProgressBar::class.java.name)))
                .perform(setProgressBarProgress(75))
                .check(matches(withProgressBarProgress(75)))
    }

    @Test
    fun segmented_changes_whenClicked() {
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<RecyclerView.ViewHolder>(24))

        onView(withClassName(equalTo(SegmentedGroup::class.java.name)))
                .check(matches(hasRadioButtonCheck(0)))

        // Select "Mango"
        onView(withClassName(equalTo(SegmentedGroup::class.java.name)))
                .perform(setRadioButtonCheck(2))

        // Check each RadioButton
        onView(withClassName(equalTo(SegmentedGroup::class.java.name)))
                .check(matches(not(hasRadioButtonCheck(0))))
        onView(withClassName(equalTo(SegmentedGroup::class.java.name)))
                .check(matches(not(hasRadioButtonCheck(1))))
        onView(withClassName(equalTo(SegmentedGroup::class.java.name)))
                .check(matches(hasRadioButtonCheck(2)))
        onView(withClassName(equalTo(SegmentedGroup::class.java.name)))
                .check(matches(not(hasRadioButtonCheck(3))))
    }

    @Test
    fun inlineDatePicker_shouldExpand_whenClicked() {
        val formBuildHelper = getFormBuildHelper()

        // Get inlineDatePicker form element
        val inlineDatePicker = formBuildHelper.getFormElement<FormInlineDatePickerElement>(FormActivityTest.Tag.InlineDateTimePicker.ordinal)

        assertFalse(inlineDatePicker.isExpanded())

        // Click element to verify that the picker expands
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(28, click()))

        assertTrue(inlineDatePicker.isExpanded())
    }

    @Test
    fun inlineDatePicker_shouldVerifyLinkedDate_whenChanged() {
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<RecyclerView.ViewHolder>(29))
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<RecyclerView.ViewHolder>(30))

        val formBuildHelper = getFormBuildHelper()

        // Get startDate form element
        val startDate = formBuildHelper.getFormElement<FormInlineDatePickerElement>(FormActivityTest.Tag.InlineDateStartPicker.ordinal)

        // Get endDate form element
        val endDate = formBuildHelper.getFormElement<FormInlineDatePickerElement>(FormActivityTest.Tag.InlineDateEndPicker.ordinal)

        assertFalse(endDate.dateError)

        instrumentation.runOnMainSync {
            startDate.setDateTime(org.threeten.bp.LocalDateTime.of(2020, 11, 2, 2, 30))
            endDate.setDateTime(org.threeten.bp.LocalDateTime.of(2020, 11, 1, 2, 30))
        }
        instrumentation.waitForIdleSync()

        assertTrue(endDate.dateError)
    }

    @Test
    fun button_disabled_shouldDoNothing_whenClicked() {
        // Click button to verify that nothing happens because it is disabled
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(31, click()))
        onView(withId(R.id.recyclerView))
                .check(matches(not(withText("Disabled?"))))
    }

    @Test
    fun image_clearsImage_whenCleared() {
        // Verify initial image drawable is set
        // NOTE: Since the tests do not have access to the Internet, the default image will be used.
        // The Assert is setup in onInitialImageLoaded

        val formBuildHelper = getFormBuildHelper()

        // Get image view form element
        val imageView = formBuildHelper.getFormElement<FormImageElement>(FormActivityTest.Tag.ImageViewElement.ordinal)

        // Open Image Dialog and select "Remove"
        instrumentation.runOnMainSync {
            imageView.itemView?.performClick()
        }
        instrumentation.waitForIdleSync()
        onView(withText("Pick one"))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed()))
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Remove")))
                .perform(click())

        // Verify image drawable is now set the default image
        // The Assert is setup in onClear
    }

    @Test
    fun picker_openDialog_whenClicked() {
        // Open SingleItem Dialog and select "Orange"
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(14, click()))
        onView(withText("SingleItem Dialog"))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed()))
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Orange")))
                .perform(click())

        // Open MultiItems Dialog, select "Orange", click "OK"
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(15, click()))
        onView(withText("MultiItems Dialog"))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed()))
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Orange")))
                .perform(click())
        onView(withId(android.R.id.button1)).perform(click())

        // Open Date Dialog, enter 2/25/2018, click "OK"
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
        onView(withClassName(equalTo(DatePicker::class.java.name)))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed())).perform(PickerActions.setDate(2018, 2, 25))
        onView(withId(android.R.id.button1)).perform(click())

        // Open Time Dialog, enter 12:30 AM, click "OK"
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(11, click()))
        onView(withClassName(equalTo(TimePicker::class.java.name)))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed())).perform(PickerActions.setTime(12, 30))
        onView(withId(android.R.id.button1)).perform(click())

        //** DateTime Dialog */

        // Open Date Dialog, enter 2/25/2018, click "OK"
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(12, click()))
        onView(withClassName(equalTo(DatePicker::class.java.name)))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed())).perform(PickerActions.setDate(2018, 2, 25))
        onView(withId(android.R.id.button1)).perform(click())

        // Open Time Dialog, enter 12:30 AM, click "OK"
        onView(withClassName(equalTo(TimePicker::class.java.name)))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed())).perform(PickerActions.setTime(12, 30))
        onView(withId(android.R.id.button1)).perform(click())
    }

    //@Test
    fun autoComplete_providesSuggestions_whenTextIsTyped() {
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<RecyclerView.ViewHolder>(16))
        // Enter text in the autoComplete field, click on the suggestion, and verify it is displayed in the field
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(16, click()))

        onView(withClassName(equalTo(AppCompatAutoCompleteTextView::class.java.name)))
                .perform(typeText("Kotlin"))
        onData(equalTo(ContactItem(id = 3, value = "Kotlin Contact", label = "Kotlin Contact (Coder)")))
                .inRoot(RootMatchers.isPlatformPopup()).perform(click())
        onView(withText("Kotlin Contact (Coder)"))
                .check(matches(isDisplayed()))
    }

    //@Test
    fun autoCompleteToken_providesSuggestions_whenTextIsTyped() {
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<RecyclerView.ViewHolder>(17))
        // Enter text in the autoCompleteToken field, click on the suggestion, and verify it exists in the options list
        val contactItem = ContactItem(id = 3, value = "Kotlin.Contact@mail.com", label = "Kotlin Contact (Coder)")

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(17, click()))

        onView(withClassName(equalTo(ItemsCompletionView::class.java.name)))
                .perform(typeText("Kotlin"))
        onData(equalTo(contactItem))
                .inRoot(RootMatchers.isPlatformPopup()).perform(click())
        onView(withClassName(equalTo(ItemsCompletionView::class.java.name)))
                .check(matches(hasItemsCompletionViewObject(contactItem)))
    }

    @Test
    fun button_openDialog_whenClicked() {
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<RecyclerView.ViewHolder>(25))
        // Click button to verify the value observer Unit action works and displays an alert dialog
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(25, click()))
        onView(withText("Confirm?"))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed()))
        onView(withId(android.R.id.button1)).perform(click())
    }

    @Test
    fun slider_changes_whenProgressed() {
        // Change slider to check progress value
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(21, click()))
        onView(withClassName(equalTo(AppCompatSeekBar::class.java.name)))
                .perform(setSeekBarProgress(10))
                .check(matches(withSeekBarProgress(10)))
    }

    @Test
    fun checkBox_becomeChecked_whenClicked() {
        // Check it
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(22, click()))
        onView(`is`(instanceOf(AppCompatCheckBox::class.java)))
                .check(matches(isChecked()))

        // UnCheck it
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(22, click()))
        onView(`is`(instanceOf(AppCompatCheckBox::class.java)))
                .check(matches(isNotChecked()))
    }

    @Test
    fun switch_becomeChecked_whenClicked() {
        // Check it
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(20, click()))
        onView(`is`(instanceOf(SwitchCompat::class.java)))
                .check(matches(isChecked()))

        // UnCheck it
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(20, click()))
        onView(`is`(instanceOf(SwitchCompat::class.java)))
                .check(matches(isNotChecked()))
    }
}
