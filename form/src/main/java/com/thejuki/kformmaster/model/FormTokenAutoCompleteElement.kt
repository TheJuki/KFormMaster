package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable
import android.widget.ArrayAdapter
import java.io.Serializable
import java.util.*

/**
 * Form TokenAutoComplete Element
 *
 * Form element for ItemsCompletionView
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormTokenAutoCompleteElement<T : Serializable> : BaseFormElement<T> {

    constructor(tag: Int = 0) : super(tag)

    /**
     * Because the written text in the EditText not always matched the
     * Values in options array, we keep the TypedString: what the user typed.
     */
    var typedString: String? = null
    /**
     * To determine what strings can be typed, we keep a set of the options
     * converted to strings
     */
    private var mStringOptions: HashSet<String>? = null

    /**
     * Override the default array adapter
     * This is useful for a custom asynchronous adapter
     */
    var arrayAdapter: ArrayAdapter<*>? = null

    /**
     * Override the default dropdown width
     */
    var dropdownWidth: Int? = null

    fun setArrayAdapter(arrayAdapter: ArrayAdapter<*>?): FormTokenAutoCompleteElement<T> {
        this.arrayAdapter = arrayAdapter
        return this
    }

    fun setDropdownWidth(dropdownWidth: Int?): FormTokenAutoCompleteElement<T> {
        this.dropdownWidth = dropdownWidth
        return this
    }

    override fun getType(): Int {
        return BaseFormElement.Companion.TYPE_EDITTEXT_TOKEN_AUTOCOMPLETE
    }

    override var mValue: T? = null
        set(value) {
            field = value
            typedString = value?.toString()
            mValueChanged?.onValueChanged(this)
        }

    override fun setValue(mValue: Any?): BaseFormElement<T> {
        typedString = mValue?.toString()

        return super.setValue(mValue)
    }

    override fun setOptions(mOptions: List<T>): FormTokenAutoCompleteElement<T> {
        super.setOptions(mOptions)
        mStringOptions = HashSet()
        for (i in mOptions) {
            mStringOptions!!.add(i.toString())
        }
        return this
    }

    fun setResultOption(resultOption: Any) {
        super.setOptions(listOf(resultOption) as List<T>)
        super.setOptionsSelected(listOf(resultOption) as List<T>)
        mStringOptions = HashSet()
        mStringOptions!!.add(resultOption.toString())
    }

    /**
     * Parcelable boilerplate
     */
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        super.writeToParcel(dest, flags)
        dest.writeString(this.typedString)
        dest.writeSerializable(this.mStringOptions)
    }

    protected constructor(`in`: Parcel) : super(`in`) {
        this.typedString = `in`.readString()
        this.mStringOptions = `in`.readSerializable() as HashSet<String>
    }

    companion object {

        fun createInstance(): FormTokenAutoCompleteElement<String> {
            return FormTokenAutoCompleteElement()
        }

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
