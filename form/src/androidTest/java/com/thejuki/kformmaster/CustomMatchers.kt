package com.thejuki.kformmaster

import android.view.View
import android.widget.TextView
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
