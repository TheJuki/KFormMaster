package com.thejuki.kformmaster.helper

import androidx.annotation.LayoutRes

/**
 * Form Layouts
 *
 * This class can override the form element layouts at the form level.
 * Each related form element in the form will use these layouts, if specified.
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormLayouts(@LayoutRes var header: Int? = null,
                  @LayoutRes var text: Int? = null,
                  @LayoutRes var textArea: Int? = null,
                  @LayoutRes var number: Int? = null,
                  @LayoutRes var email: Int? = null,
                  @LayoutRes var password: Int? = null,
                  @LayoutRes var phone: Int? = null,
                  @LayoutRes var autoComplete: Int? = null,
                  @LayoutRes var autoCompleteToken: Int? = null,
                  @LayoutRes var button: Int? = null,
                  @LayoutRes var date: Int? = null,
                  @LayoutRes var time: Int? = null,
                  @LayoutRes var dateTime: Int? = null,
                  @LayoutRes var dropDown: Int? = null,
                  @LayoutRes var multiCheckBox: Int? = null,
                  @LayoutRes var switch: Int? = null,
                  @LayoutRes var checkBox: Int? = null,
                  @LayoutRes var slider: Int? = null,
                  @LayoutRes var label: Int? = null,
                  @LayoutRes var textView: Int? = null,
                  @LayoutRes var segmented: Int? = null,
                  @LayoutRes var progress: Int? = null,
                  @LayoutRes var image: Int? = null,
                  @LayoutRes var inlineDateTimePicker: Int? = null)