package com.thejuki.kformmaster.listener

import com.thejuki.kformmaster.model.BaseFormElement

/**
 * On Form Element Value Changed Listener
 *
 * Listener for changes to the form elements
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
interface OnFormElementValueChangedListener {

    fun onValueChanged(formElement: BaseFormElement<*>)

}