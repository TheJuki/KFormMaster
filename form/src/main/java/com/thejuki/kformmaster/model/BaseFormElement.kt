package com.thejuki.kformmaster.model

import android.support.annotation.ColorInt
import android.support.v7.widget.*
import android.text.InputFilter
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import com.thejuki.kformmaster.widget.SegmentedGroup
import kotlin.properties.Delegates


/**
 * Base Form Element
 *
 * Holds the class variables used by most form elements
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
open class BaseFormElement<T>(var tag: Int = -1) : ViewModel {

    /**
     * Form Element Title
     */
    var title: String? = null
        set(value) {
            field = value
            titleView?.let {
                it.text = value
                if (editView != null && editView is SegmentedGroup) {
                    it.visibility = if (value.isNullOrEmpty()) View.GONE else View.VISIBLE
                }
            }
        }

    /**
     * Form Element Unique ID
     */
    var id: Int = 0

    /**
     * Form Element Value Observers
     */
    val valueObservers = mutableListOf<(value: T?, element: BaseFormElement<T>) -> Unit>()

    /**
     * Form Element Value
     */
    var value: T? by Delegates.observable<T?>(null) { _, _, newValue ->
        valueObservers.forEach { it(newValue, this) }
        editView?.let {
            if (it is AppCompatEditText && it.text.toString() != value as? String) {
                it.setText(value as? String)

            } else if (it is TextView && value is String &&
                    it.text.toString() != value as? String &&
                    it !is SwitchCompat && it !is AppCompatCheckBox) {
                it.text = value as? String
            } else if (it is SegmentedGroup) {
                it.checkChild(value as? String)
            }
        }
    }

    /**
     * Form Element Hint
     */
    var hint: String? = null
        set(value) {
            field = value
            editView?.let {
                if (it is TextView) {
                    it.hint = hint
                }
            }
        }

    /**
     * Form Element Max Length
     */
    var maxLength: Int? = null
        set(value) {
            field = value
            editView?.let {
                if (it is TextView && it !is AppCompatCheckBox && it !is AppCompatButton &&
                        it !is SwitchCompat && it !is AppCompatAutoCompleteTextView) {
                    if (maxLength != null) {
                        it.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength ?: 0))
                    } else {
                        it.filters = arrayOf<InputFilter>()
                    }
                }
            }
        }

    /**
     * Form Element Max Lines
     */
    var maxLines: Int = 1
        set(value) {
            field = value
            editView?.let {
                if (it is TextView && it !is AppCompatCheckBox && it !is AppCompatButton && it !is SwitchCompat) {
                    it.setSingleLine(maxLines == 1)
                    it.maxLines = maxLines
                }
            }
        }

    /**
     * Form Element RTL
     */
    var rightToLeft: Boolean = true
        set(value) {
            field = value
            editView?.let {
                if (it is TextView && it !is AppCompatCheckBox && it !is AppCompatButton && it !is SwitchCompat) {
                    it.gravity = if (rightToLeft) Gravity.END else Gravity.START
                } else if (it is SegmentedGroup) {
                    it.gravity = if (rightToLeft) Gravity.END else Gravity.START
                }
            }
        }

    /**
     * Form Element Background Color
     */
    @ColorInt
    var backgroundColor: Int? = null
        set(value) {
            field = value
            itemView?.let {
                if (backgroundColor != null) {
                    it.setBackgroundColor(backgroundColor ?: 0)
                }
            }
        }

    /**
     * Form Element Title Text Color (When Focused)
     */
    @ColorInt
    var titleFocusedTextColor: Int? = null

    /**
     * Form Element Title Text Color
     */
    @ColorInt
    var titleTextColor: Int? = null
        set(value) {
            field = value
            titleView?.let {
                if (titleTextColor != null) {
                    it.setTextColor(titleTextColor ?: 0)
                }
            }
        }

    /**
     * Form Element Hint Text Color
     */
    @ColorInt
    var hintTextColor: Int? = null
        set(value) {
            field = value
            editView?.let {
                if (it is TextView) {
                    if (hintTextColor != null) {
                        it.setHintTextColor(hintTextColor ?: 0)
                    }
                }
            }
        }

    /**
     * Form Element Value Text Color
     */
    @ColorInt
    var valueTextColor: Int? = null
        set(value) {
            field = value
            editView?.let {
                if (it is TextView) {
                    if (valueTextColor != null) {
                        it.setTextColor(valueTextColor ?: 0)
                    }
                }
            }
        }

    /**
     * Form Element Error Text Color
     */
    @ColorInt
    var errorTextColor: Int? = null
        set(value) {
            field = value
            errorView?.let {
                if (errorTextColor != null) {
                    it.setTextColor(errorTextColor ?: 0)
                }
            }
        }

    /**
     * Form Element Error
     */
    var error: String? = null
        set(value) {
            field = value
            if (value != null) {
                errorView?.let {
                    it.visibility = View.VISIBLE
                    it.text = value
                }

            } else {
                errorView?.let {
                    it.visibility = View.GONE
                    it.text = null
                }
            }
        }

    /**
     * Form Element Required
     */
    var required: Boolean = false

    /**
     * Form Element Clearable
     * Setting this to true will display a clear button (X) to set the value to null.
     */
    var clearable: Boolean = false
        set(value) {
            field = value
            editView?.let {
                if (it is com.thejuki.kformmaster.widget.ClearableEditText) {
                    it.displayClear = value
                }
            }
        }

    /**
     * Form Element Display divider line before the element
     */
    var displayDivider: Boolean = true
        set(value) {
            field = value
            dividerView?.let {
                it.visibility = if (displayDivider) View.VISIBLE else View.GONE
            }
        }

    /**
     * Form Element Confirm Edit dialog should be shown before editing an element
     */
    var confirmEdit: Boolean = false

    /**
     * Form Element Confirm Edit dialog title
     */
    var confirmTitle: String? = null

    /**
     * Form Element Confirm Edit dialog message
     */
    var confirmMessage: String? = null

    /**
     * Form Element Update EditText value when focus is lost
     * By default, an EditText will update the form value as characters are typed.
     * Setting this to true will only update the value when focus is lost.
     */
    var updateOnFocusChange: Boolean = false

    /**
     * Form Element [InputType]
     */
    var inputType: Int? = null

    /**
     * Form Element [EditorInfo] imeOptions
     */
    var imeOptions: Int? = null

    /**
     * Form Element Item View
     */
    var itemView: View? = null
        set(value) {
            field = value
            itemView?.let {
                it.isEnabled = enabled
                // Trigger visibility
                this.visible = this.visible

                if (backgroundColor != null) {
                    it.setBackgroundColor(backgroundColor ?: 0)
                }
            }
        }

    /**
     * Form Element Edit View
     */
    var editView: View? = null
        set(value) {
            field = value
            editView?.let {
                it.isEnabled = enabled
                if (it is TextView && it !is AppCompatCheckBox && it !is AppCompatButton && it !is SwitchCompat) {
                    it.gravity = if (rightToLeft) Gravity.END else Gravity.START
                    it.setSingleLine(maxLines == 1)
                    it.maxLines = maxLines
                    if (it !is AppCompatAutoCompleteTextView) {
                        if (maxLength != null) {
                            it.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength
                                    ?: 0))
                        } else {
                            it.filters = arrayOf<InputFilter>()
                        }
                    }
                    if (valueTextColor != null) {
                        it.setTextColor(valueTextColor ?: 0)
                    }
                    if (hintTextColor != null) {
                        it.setHintTextColor(hintTextColor ?: 0)
                    }
                } else if (it is AppCompatButton) {
                    if (valueTextColor != null) {
                        it.setTextColor(valueTextColor ?: 0)
                    }
                } else if (it is SegmentedGroup) {
                    it.gravity = if (rightToLeft) Gravity.END else Gravity.START
                }
            }
        }

    /**
     * Form Element Divider View
     */
    var dividerView: View? = null
        set(value) {
            field = value
            dividerView?.let {
                it.visibility = if (displayDivider) View.VISIBLE else View.GONE
            }
        }

    /**
     * Form Element Title View
     */
    var titleView: AppCompatTextView? = null
        set(value) {
            field = value
            titleView?.let {
                it.isEnabled = enabled
                it.text = title
                if (titleTextColor != null) {
                    it.setTextColor(titleTextColor ?: 0)
                }
            }
        }

    /**
     * Form Element Error View
     */
    var errorView: AppCompatTextView? = null
        set(value) {
            field = value
            errorView?.let {
                if (errorTextColor != null) {
                    it.setTextColor(errorTextColor ?: 0)
                }

                if (error.isNullOrEmpty()) {
                    it.visibility = View.GONE
                    return
                }

                it.text = error
                it.visibility = View.VISIBLE
            }
        }

    /**
     * Form Element Visibility
     */
    var visible: Boolean = true
        set(value) {
            field = value
            if (value) {
                itemView?.let {
                    it.visibility = View.VISIBLE
                    val params = it.layoutParams
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    it.layoutParams = params
                }

            } else {
                itemView?.let {
                    it.visibility = View.GONE
                    val params = it.layoutParams
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT
                    params.height = 0
                    it.layoutParams = params
                }
            }
        }

    /**
     * Form Element Enabled
     */
    var enabled: Boolean = true
        set(value) {
            field = value
            itemView?.let {
                it.isEnabled = value
            }
            titleView?.let {
                it.isEnabled = value
            }
            editView?.let {
                it.isEnabled = value
            }
        }

    /**
     * Form Element Value String value
     */
    val valueAsString: String
        get() = this.value?.toString() ?: ""

    /**
     * Base validation
     */
    open val isValid: Boolean
        get() = !required || (required && value != null &&
                (value !is String || !(value as? String).isNullOrEmpty()))

    /**
     * Clear edit view
     */
    open fun clear() {
        this.value = null
    }

    /**
     * Clearable builder setter
     */
    fun setTag(clearable: Boolean): BaseFormElement<T> {
        this.clearable = clearable
        return this
    }

    /**
     * Tag builder setter
     */
    fun setTag(tag: Int): BaseFormElement<T> {
        this.tag = tag
        return this
    }

    /**
     * Title builder setter
     */
    fun setTitle(title: String): BaseFormElement<T> {
        this.title = title
        return this
    }

    /**
     * Value builder setter
     */
    @Suppress("UNCHECKED_CAST")
    open fun setValue(value: Any?): BaseFormElement<T> {
        this.value = value as T?
        return this
    }

    /**
     * Hint builder setter
     */
    fun setHint(hint: String?): BaseFormElement<T> {
        this.hint = hint
        return this
    }

    /**
     * updateOnFocusChange builder setter
     */
    fun setUpdateOnFocusChange(updateOnFocusChange: Boolean): BaseFormElement<T> {
        this.updateOnFocusChange = updateOnFocusChange
        return this
    }

    /**
     * [InputType] builder setter
     */
    fun setInputType(inputType: Int): BaseFormElement<T> {
        this.inputType = inputType
        return this
    }

    /**
     * [EditorInfo] imeOptions builder setter
     */
    fun setImeOptions(imeOptions: Int): BaseFormElement<T> {
        this.imeOptions = imeOptions
        return this
    }

    /**
     * RTL builder setter
     */
    fun setRightToLeft(rightToLeft: Boolean): BaseFormElement<T> {
        this.rightToLeft = rightToLeft
        return this
    }

    /**
     * Max Length builder setter
     */
    fun setMaxLength(maxLength: Int): BaseFormElement<T> {
        this.maxLength = maxLength
        return this
    }

    /**
     * Max Lines builder setter
     */
    fun setMaxLines(maxLines: Int): BaseFormElement<T> {
        this.maxLines = maxLines
        return this
    }

    /**
     * Error builder setter
     */
    fun setError(error: String?): BaseFormElement<T> {
        this.error = error
        return this
    }

    /**
     * Required builder setter
     */
    fun setRequired(required: Boolean): BaseFormElement<T> {
        this.required = required
        return this
    }

    /**
     * Confirm Display Divider builder setter
     */
    fun setDisplayDivider(displayDivider: Boolean): BaseFormElement<T> {
        this.displayDivider = displayDivider
        return this
    }

    /**
     * Confirm Edit builder setter
     */
    fun setConfirmEdit(confirmEdit: Boolean): BaseFormElement<T> {
        this.confirmEdit = confirmEdit
        return this
    }

    /**
     * Confirm Edit Title builder setter
     */
    fun setConfirmTitle(confirmTitle: String): BaseFormElement<T> {
        this.confirmTitle = confirmTitle
        return this
    }

    /**
     * Confirm Edit Message builder setter
     */
    fun setConfirmMessage(confirmMessage: String): BaseFormElement<T> {
        this.confirmMessage = confirmMessage
        return this
    }

    /**
     * Visible builder setter
     */
    fun setVisible(visible: Boolean): BaseFormElement<T> {
        this.visible = visible
        return this
    }

    /**
     * Enabled builder setter
     */
    fun setEnabled(enabled: Boolean): BaseFormElement<T> {
        this.enabled = enabled
        return this
    }

    /**
     * Adds a value observer
     */
    fun addValueObserver(observer: (T?, BaseFormElement<T>) -> Unit): BaseFormElement<T> {
        this.valueObservers.add(observer)
        return this
    }

    /**
     * Adds a list of value observers
     */
    fun addAllValueObservers(observers: List<(T?, BaseFormElement<T>) -> Unit>): BaseFormElement<T> {
        this.valueObservers.addAll(observers)
        return this
    }

    /**
     * Hint Text Color builder setter
     */
    fun setHintTextColor(@ColorInt hintTextColor: Int?): BaseFormElement<T> {
        this.hintTextColor = hintTextColor
        return this
    }

    /**
     * Title Text Color builder setter
     */
    fun setTitleTextColor(@ColorInt titleTextColor: Int?): BaseFormElement<T> {
        this.titleTextColor = titleTextColor
        return this
    }

    /**
     * Title Focused Text Color builder setter
     */
    fun setTitleFocusedTextColor(@ColorInt titleFocusedTextColor: Int?): BaseFormElement<T> {
        this.titleFocusedTextColor = titleFocusedTextColor
        return this
    }

    /**
     * Value Text Color builder setter
     */
    fun setValueTextColor(@ColorInt valueTextColor: Int?): BaseFormElement<T> {
        this.valueTextColor = valueTextColor
        return this
    }

    /**
     * Background Color builder setter
     */
    fun setBackgroundColor(@ColorInt backgroundColor: Int?): BaseFormElement<T> {
        this.backgroundColor = backgroundColor
        return this
    }

    /**
     * Error Text Color builder setter
     */
    fun setErrorTextColor(@ColorInt errorTextColor: Int?): BaseFormElement<T> {
        this.errorTextColor = errorTextColor
        return this
    }

    override fun hashCode(): Int {
        return id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseFormElement<*>) return false

        if (id != other.id) return false

        return true
    }

    override fun toString(): String {
        return "FormElement(tag=$tag, title=$title, id=$id, value=$value, hint=$hint, error=$error, required=$required, isValid=$isValid, visible=$visible)"
    }
}
