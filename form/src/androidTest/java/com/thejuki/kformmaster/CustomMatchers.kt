package com.thejuki.kformmaster

import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.test.espresso.matcher.BoundedMatcher
import com.thejuki.kformmaster.token.ItemsCompletionView
import com.thejuki.kformmaster.widget.SegmentedGroup
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

/**
 * Custom Matchers
 *
 * Custom Matchers for the Great Form Instrumented Test
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
fun hasTextViewHint(expectedHint: String) =
        object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("TextView with hint: $expectedHint")
            }

            override fun matchesSafely(view: View) = (view as? TextView)?.hint.toString() == expectedHint
        }

fun hasTextViewInputType(expectedType: Int) =
        object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("TextView with input type: $expectedType")
            }

            override fun matchesSafely(view: View) = (view as? TextView)?.inputType == expectedType
        }

fun hasTextViewSingleLine() =
        object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("TextView with max lines: 1")
            }

            override fun matchesSafely(view: View) = (view as? TextView)?.maxLines == 1
        }

fun withProgress(expectedProgress: Int) =
        object : BoundedMatcher<View, AppCompatSeekBar>(AppCompatSeekBar::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("SeekBar with progress: $expectedProgress")
            }

            override fun matchesSafely(seekBar: AppCompatSeekBar) = seekBar.progress == expectedProgress
        }

fun hasRadioButtonCheck(index: Int) =
        object : BoundedMatcher<View, SegmentedGroup>(SegmentedGroup::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("RadioButton with index: $index")
            }

            override fun matchesSafely(radioGroup: SegmentedGroup): Boolean {
                val radioButton = radioGroup.getChildAt(index) as RadioButton
                return radioButton.isChecked
            }
        }

fun <T> hasItemsCompletionViewObject(expectedObject: T) =
        object : BoundedMatcher<View, ItemsCompletionView>(ItemsCompletionView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("ItemsCompletionView with object: $expectedObject")
            }

            override fun matchesSafely(view: ItemsCompletionView) = view.objects.contains(expectedObject)
        }
