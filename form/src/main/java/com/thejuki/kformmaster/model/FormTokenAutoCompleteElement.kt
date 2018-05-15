package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable
import android.widget.ArrayAdapter
import com.thejuki.kformmaster.token.ItemsCompletionView
import java.io.Serializable

/**
 * Form TokenAutoComplete Element
 *
 * Form element for ItemsCompletionView
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormTokenAutoCompleteElement<T : Serializable> : BaseFormElement<T> {

    override val isValid: Boolean
        get() = !required || (value != null && value is List<*> && !(value as List<*>).isEmpty())

    override fun clear() {
        this.value = null
        (this.editView as? ItemsCompletionView)?.clear()
    }

    /**
     * Override the default array adapter
     * This is useful for a custom asynchronous adapter
     */
    var arrayAdapter: ArrayAdapter<*>? = null

    /**
     * Override the default dropdown width
     */
    var dropdownWidth: Int? = null

    /**
     * arrayAdapter builder setter
     */
    fun setArrayAdapter(arrayAdapter: ArrayAdapter<*>?): FormTokenAutoCompleteElement<T> {
        this.arrayAdapter = arrayAdapter
        return this
    }

    /**
     * dropdownWidth builder setter
     */
    fun setDropdownWidth(dropdownWidth: Int?): FormTokenAutoCompleteElement<T> {
        this.dropdownWidth = dropdownWidth
        return this
    }

    /**
     * Parcelable boilerplate
     */
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        super.writeToParcel(dest, flags)
    }

    constructor() : super()

    constructor(tag: Int = -1) : super(tag)

    constructor(`in`: Parcel) : super(`in`)

    companion object {

        /**
         * Creates an instance
         */
        fun createInstance(): FormTokenAutoCompleteElement<String> {
            return FormTokenAutoCompleteElement()
        }

        /**
         * Creates a generic instance
         */
        fun <T : Serializable> createGenericInstance(): FormTokenAutoCompleteElement<T> {
            return FormTokenAutoCompleteElement()
        }

        val CREATOR: Parcelable.Creator<FormTokenAutoCompleteElement<*>> = object : Parcelable.Creator<FormTokenAutoCompleteElement<*>> {
            override fun createFromParcel(source: Parcel): FormTokenAutoCompleteElement<*> {
                return FormTokenAutoCompleteElement<Serializable>(source)
            }

            override fun newArray(size: Int): Array<FormTokenAutoCompleteElement<*>?> {
                return arrayOfNulls(size)
            }
        }
    }
}
