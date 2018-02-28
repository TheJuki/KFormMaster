package com.thejuki.kformmaster

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.matcher.RootMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.SwitchCompat
import android.text.InputType
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import org.hamcrest.Matchers.*
import org.junit.Assert
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
class FormInstrumentedTest {

    @Rule
    @JvmField
    val mActivityRule = ActivityTestRule<FormActivityTest>(FormActivityTest::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        Assert.assertEquals("com.thejuki.kformmaster.test", appContext.packageName)
    }

    @Test
    fun header_isDisplayed() {
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<ViewHolder>(0))
        onView(withText("Header 1"))
                .check(matches(isDisplayed()))

        onView(withId(R.id.recyclerView)).perform(scrollToPosition<ViewHolder>(4))
        onView(withText("Header 2"))
                .check(matches(isDisplayed()))

        onView(withId(R.id.recyclerView)).perform(scrollToPosition<ViewHolder>(8))
        onView(withText("Header 3"))
                .check(matches(isDisplayed()))

        onView(withId(R.id.recyclerView)).perform(scrollToPosition<ViewHolder>(12))
        onView(withText("Header 4"))
                .check(matches(isDisplayed()))

        onView(withId(R.id.recyclerView)).perform(scrollToPosition<ViewHolder>(18))
        onView(withText("Header 5"))
                .check(matches(isDisplayed()))
    }

    @Test
    fun editTextElement_shouldHaveInputType() {

        // Check Email type
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<ViewHolder>(1))
        onView(allOf(`is`(instanceOf(AppCompatEditText::class.java)),
                hasTextViewInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)))
                .check(matches(isDisplayed()))

        // Check Password type
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<ViewHolder>(2))
        onView(allOf(`is`(instanceOf(AppCompatEditText::class.java)),
                hasTextViewInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)))
                .check(matches(isDisplayed()))

        // Check Phone type
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<ViewHolder>(3))
        onView(allOf(`is`(instanceOf(AppCompatEditText::class.java)),
                hasTextViewInputType(InputType.TYPE_CLASS_PHONE or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)))
                .check(matches(isDisplayed()))

        // Check Single Line type
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<ViewHolder>(5))
        onView(allOf(`is`(instanceOf(AppCompatEditText::class.java)),
                hasTextViewInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS),
                hasTextViewSingleLine()))
                .check(matches(isDisplayed()))

        // Check Multi Line type
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<ViewHolder>(6))
        onView(allOf(`is`(instanceOf(AppCompatEditText::class.java)),
                hasTextViewInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_FLAG_MULTI_LINE)))
                .check(matches(isDisplayed()))

        // Check Number type
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<ViewHolder>(7))
        onView(allOf(`is`(instanceOf(AppCompatEditText::class.java)),
                hasTextViewInputType(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)))
                .check(matches(isDisplayed()))
    }

    @Test
    fun text_shouldNotBeDisplayed_whenVisibleIsFalse() {
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<ViewHolder>(23))

        onView(withText("Hidden"))
                .check(matches(not(isDisplayed())))
    }

    @Test
    fun button_openDialog_whenClicked() {
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(22, click()))

        onView(withText("Confirm?"))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed()))
    }

    @Test
    fun checkBox_becomeChecked_whenClicked() {
        // Check it
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(21, click()))

        onView(`is`(instanceOf(AppCompatCheckBox::class.java)))
                .check(matches(isChecked()))

        // UnCheck it
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(21, click()))

        onView(`is`(instanceOf(AppCompatCheckBox::class.java)))
                .check(matches(isNotChecked()))
    }

    @Test
    fun switch_becomeChecked_whenClicked() {
        // Check it
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(19, click()))

        onView(`is`(instanceOf(SwitchCompat::class.java)))
                .check(matches(isChecked()))

        // UnCheck it
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(19, click()))

        onView(`is`(instanceOf(SwitchCompat::class.java)))
                .check(matches(isNotChecked()))
    }
}
