package com.thejuki.kformmaster.helper

import android.support.annotation.LayoutRes

/**
 * Form Layouts
 *
 * This class can override the form element layouts at the form level.
 * Each related form element in the form will use these layouts, if specified.
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormLayouts(@LayoutRes val headerLayoutID: Int? = null)