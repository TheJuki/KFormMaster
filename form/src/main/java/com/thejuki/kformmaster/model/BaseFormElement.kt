package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener
import java.io.Serializable
import java.util.*

/**
 * Base Form Element
 *
 * Holds the class variables used by most form elements
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
open class BaseFormElement<T : Serializable>(var mTag: Int = 0, var mTitle: String? = null) : ViewModel, Parcelable {

    // class variables
    var mType: Int = 0 // type for the form element
    open var mValue: T? = null // value to be shown on right
        set(value) {
            field = value
            mValueChanged?.onValueChanged(this)
        }
    var mOptions: List<T>? = null // list of options for single and multi picker
        get() = field ?: ArrayList()
    var mOptionsSelected: List<T>? = null // list of selected options for single and multi picker
        get() = field ?: ArrayList()
    var mHint: String? = null // value to be shown if mValue is null
    var mError: String? = null
    var mRequired: Boolean = false // value to set is the field is required
    var mVisible: Boolean = true
    var mValueChanged: OnFormElementValueChangedListener? = null

    val valueAsString: String
        get() = if (this.mValue == null) "" else this.mValue!!.toString()

    open val isHeader: Boolean
        get() = false

    // getters and setters
    fun setTag(mTag: Int): BaseFormElement<T> {
        this.mTag = mTag
        return this
    }

    fun setType(mType: Int): BaseFormElement<T> {
        this.mType = mType
        return this
    }

    fun setTitle(mTitle: String): BaseFormElement<T> {
        this.mTitle = mTitle
        return this
    }

    open fun setValue(mValue: Any?): BaseFormElement<T> {
        this.mValue = mValue as T?
        return this
    }

    fun setHint(mHint: String?): BaseFormElement<T> {
        this.mHint = mHint
        return this
    }

    fun setError(error: String?): BaseFormElement<T> {
        this.mError = error
        return this
    }

    fun setRequired(required: Boolean): BaseFormElement<T> {
        this.mRequired = required
        return this
    }

    fun setVisible(visible: Boolean): BaseFormElement<T> {
        this.mVisible = visible
        return this
    }

    open fun setOptions(mOptions: List<T>): BaseFormElement<T> {
        this.mOptions = mOptions
        return this
    }

    fun setValueChanged(mValueChanged: OnFormElementValueChangedListener?): BaseFormElement<T> {
        this.mValueChanged = mValueChanged
        return this
    }

    fun setOptionsSelected(mOptionsSelected: List<Any>): BaseFormElement<T> {
        this.mOptionsSelected = mOptionsSelected as List<T>
        return this
    }

    fun getTag(): Int {
        return this.mTag
    }

    open fun getType(): Int {
        return this.mType
    }

    fun getTitle(): String? {
        return this.mTitle
    }

    fun getValue(): T? {
        return this.mValue
    }

    fun getError(): String? {
        return this.mError
    }

    fun isRequired(): Boolean {
        return this.mRequired
    }

    fun isVisible(): Boolean {
        return this.mVisible
    }

    override fun toString(): String {
        return "TAG: " + this.mTag.toString() +
                ", TITLE: " + this.mTitle +
                ", VALUE: " + this.mValue +
                ", REQUIRED: " + this.mRequired.toString()
    }

    /**
     * Parcelable boilerplate
     */
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.mTag)
        dest.writeInt(this.mType)
        dest.writeString(this.mTitle)
        dest.writeSerializable(this.mValue)

        /*
         * We need special method to store array of generic objects
         * more here: https://stackoverflow.com/a/31979348/3625638
         */

        // mOptions
        if (mOptions == null || mOptions!!.size == 0) {
            dest.writeInt(0)
        } else {
            dest.writeInt(mOptions!!.size)

            val objectsType = mOptions!![0].javaClass
            dest.writeSerializable(objectsType)
            dest.writeList(mOptions)
        }

        // mOptionsSelected
        if (mOptionsSelected == null || mOptionsSelected!!.size == 0) {
            dest.writeInt(0)
        } else {
            dest.writeInt(mOptionsSelected!!.size)

            val objectsType = mOptionsSelected!![0].javaClass
            dest.writeSerializable(objectsType)
            dest.writeList(mOptionsSelected)
        }

        dest.writeString(this.mHint)
        dest.writeString(this.mError)
        dest.writeByte(if (this.mRequired) 1.toByte() else 0.toByte())
        dest.writeByte(if (this.mVisible) 1.toByte() else 0.toByte())
    }

    protected constructor(`in`: Parcel) : this() {
        this.mTag = `in`.readInt()
        this.mType = `in`.readInt()
        this.mTitle = `in`.readString()
        this.mValue = `in`.readSerializable() as T

        /*
         * We need special method to store array of generic objects
         * more here: https://stackoverflow.com/a/31979348/3625638
         */

        // mOptions
        val optionSize = `in`.readInt()
        if (optionSize == 0) {
            mOptions = null
        } else {
            val type = `in`.readSerializable() as Class<*>

            mOptions = ArrayList(optionSize)
            `in`.readList(mOptions, type.classLoader)
        }

        // mOptionsSelected
        val selectedOptionSize = `in`.readInt()
        if (selectedOptionSize == 0) {
            mOptionsSelected = null
        } else {
            val type = `in`.readSerializable() as Class<*>

            mOptionsSelected = ArrayList(selectedOptionSize)
            `in`.readList(mOptionsSelected, type.classLoader)
        }

        this.mHint = `in`.readString()
        this.mError = `in`.readString()
        this.mRequired = `in`.readByte().toInt() != 0
        this.mVisible = `in`.readByte().toInt() != 0
    }

    companion object {

        // different types for the form elements
        val TYPE_HEADER = 0
        val TYPE_EDITTEXT_TEXT_SINGLELINE = 1
        val TYPE_EDITTEXT_TEXT_MULTILINE = 2
        val TYPE_EDITTEXT_NUMBER = 3
        val TYPE_EDITTEXT_EMAIL = 4
        val TYPE_EDITTEXT_PHONE = 5
        val TYPE_EDITTEXT_PASSWORD = 6
        val TYPE_EDITTEXT_AUTOCOMPLETE = 7
        val TYPE_PICKER_DATE = 8
        val TYPE_PICKER_TIME = 9
        val TYPE_PICKER_DATE_TIME = 10
        val TYPE_PICKER_MULTI_CHECKBOX = 11
        val TYPE_PICKER_DROP_DOWN = 12
        val TYPE_TEXTVIEW = 13
        val TYPE_BUTTON = 14
        val TYPE_SWITCH = 15
        val TYPE_SLIDER = 16
        val TYPE_EDITTEXT_TOKEN_AUTOCOMPLETE = 17

        /**
         * static method to create instance
         *
         * @return
         */
        fun createInstance(): BaseFormElement<String> {
            return BaseFormElement()
        }

        /**
         * static method to create instance using
         * custom generic value type
         *
         * @return
         */
        fun <T : Serializable> createGenericInstance(): BaseFormElement<T> {
            return BaseFormElement()
        }

        val CREATOR: Parcelable.Creator<BaseFormElement<*>> = object : Parcelable.Creator<BaseFormElement<*>> {
            override fun createFromParcel(source: Parcel): BaseFormElement<*> {
                return BaseFormElement<Serializable>(source)
            }

            override fun newArray(size: Int): Array<BaseFormElement<*>?> {
                return arrayOfNulls(size)
            }
        }
    }
}
