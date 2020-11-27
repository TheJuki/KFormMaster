package com.thejuki.kformmaster.model

import android.graphics.drawable.Drawable
import android.text.InputFilter
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.widget.*
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import com.thejuki.kformmaster.extensions.dpToPx
import com.thejuki.kformmaster.extensions.setMargins
import com.thejuki.kformmaster.helper.FormDsl
import com.thejuki.kformmaster.helper.InputMaskOptions
import com.thejuki.kformmaster.widget.*
import kotlin.properties.Delegates


/**
 * Base Form Element
 *
 * Holds the class variables used by most form elements
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
@FormDsl
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
     * Form Element onClick Unit
     */
    open var onClick: (() -> Unit)? = null

    /**
     * Form Element onFocus Unit
     */
    open var onFocus: (() -> Unit)? = null

    /**
     * Form Element onTouchUp Unit
     */
    open var onTouchUp: (() -> Unit)? = null

    /**
     * Form Element onTouchDown Unit
     */
    open var onTouchDown: (() -> Unit)? = null

    /**
     * Form Element onTitleIconClick Unit
     */
    open var onTitleIconClick: (() -> Unit)? = null

    /**
     * Form Element Value
     */
    var value: T? by Delegates.observable<T?>(null) { _, _, newValue ->
        valueObservers.forEach { it(newValue, this) }
        editView?.let {
            displayNewValue()
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
     * Form Element Input Mask Options
     */
    var inputMaskOptions: InputMaskOptions? = null

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
     * Form Element Edit View Gravity
     * By default, this is Gravity.END (Right to left)
     */
    open var editViewGravity: Int = Gravity.END
        set(value) {
            field = value
            editView?.let {
                if (it is TextView && it !is AppCompatCheckBox && it !is SwitchCompat) {
                    it.gravity = value
                } else if (it is SegmentedGroup) {
                    it.gravity = value
                }
            }

            titleView?.let {
                if (this is FormHeader || this is FormLabelElement) {
                    it.gravity = value
                }
            }
        }

    /**
     * Form Element Edit View Paint Flags
     * By default, this is null (No flags).
     */
    open var editViewPaintFlags: Int? = null
        set(value) {
            field = value
            editView?.let {
                if (it is TextView && it !is AppCompatCheckBox && it !is AppCompatButton && it !is SwitchCompat) {
                    editViewPaintFlags?.let { paintFlags ->
                        it.paintFlags = it.paintFlags or paintFlags
                    }
                }
            }
        }

    /**
     * Form Element layout Padding Bottom
     * By default, this will use android:paddingBottom in the XML
     */
    var layoutPaddingBottom: Int? = null
        set(value) {
            field = value
            itemView?.let {
                if (layoutPaddingBottom != null) {
                    it.setPadding(0, 0, 0, layoutPaddingBottom.dpToPx())
                }
            }
        }


    /**
     * Title Padding
     */
    var titlePadding: FormElementPadding? = null
        set(value) {
            field = value
            titleView?.let {
                if (titlePadding != null) {
                    it.setPadding(titlePadding?.left.dpToPx(),
                            titlePadding?.top.dpToPx(),
                            titlePadding?.right.dpToPx(),
                            titlePadding?.bottom.dpToPx())
                }
            }
        }


    /**
     * Form Element Padding
     */
    var padding: FormElementPadding? = null
        set(value) {
            field = value

            if (!(editView is SwitchCompat || editView is AppCompatCheckBox)) {
                editView?.let {
                    if (padding != null) {
                        it.setPadding(padding?.left.dpToPx(),
                                padding?.top.dpToPx(),
                                padding?.right.dpToPx(),
                                padding?.bottom.dpToPx())
                    }
                }
            } else {
                mainLayoutView?.let {
                    if (padding != null) {
                        it.setPadding(padding?.left.dpToPx(),
                                padding?.top.dpToPx(),
                                padding?.right.dpToPx(),
                                padding?.bottom.dpToPx())
                    }
                }
            }
            if (this is FormHeader) {
                titleView?.let {
                    if (padding != null) {
                        it.setPadding(padding?.left.dpToPx(),
                                padding?.top.dpToPx(),
                                padding?.right.dpToPx(),
                                padding?.bottom.dpToPx())
                    }
                }
            }
        }

    /**
     * Form Element Margins
     * By default, this will use layout_margin values in the XML
     */
    var margins: FormElementMargins? = null
        set(value) {
            field = value
            editView?.let {
                if (it is SegmentedGroup) {
                    if (margins != null) {
                        it.setMargins(margins?.left.dpToPx(),
                                margins?.top.dpToPx(),
                                margins?.right.dpToPx(),
                                margins?.bottom.dpToPx())
                    }
                }
            }
            if (editView == null || !(editView is SwitchCompat || editView is AppCompatCheckBox)) {
                mainLayoutView?.let {
                    if (margins != null) {
                        it.setMargins(margins?.left.dpToPx(),
                                margins?.top.dpToPx(),
                                margins?.right.dpToPx(),
                                margins?.bottom.dpToPx())
                    }
                }
            }
            // Switch and CheckBox are special. To keep the ripple the correct height,
            // set the padding instead of the margins.
            else if (editView is SwitchCompat || editView is AppCompatCheckBox) {
                mainLayoutView?.let {
                    if (margins != null) {
                        it.setPadding(margins?.left.dpToPx(),
                                margins?.top.dpToPx(),
                                margins?.right.dpToPx(),
                                margins?.bottom.dpToPx())
                    }
                }
            }
            if (this is FormHeader) {
                titleView?.let {
                    if (margins != null) {
                        it.setMargins(margins?.left.dpToPx(),
                                margins?.top.dpToPx(),
                                margins?.right.dpToPx(),
                                margins?.bottom.dpToPx())
                    }
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
            if (this is FormHeader || this is FormLabelElement) {
                if (backgroundColor != null) {
                    mainLayoutView?.setBackgroundColor(backgroundColor ?: 0)
                }
            }

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
            onErrorChanged()
        }

    /**
     * Form element property, error changed
     * Left intentionally blank in the base class.
     */
    protected open fun onErrorChanged() { /* intentionally blank */
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
                if (it is ClearableEditText) {
                    it.displayClear = value
                }
            }
        }

    /**
     * Form Element Title Icon
     * Setting this will set and display the title icon drawable (null will hide the icon). By default, no icon is displayed.
     */
    var titleIcon: Drawable? = null
        set(value) {
            field = value
            titleView?.let {
                if (it is IconTextView) {
                    it.icon = value
                    it.reInitIcon()
                }
            }
        }

    /**
     * Form Element Title Icon Location
     * Setting this set the title icon location.
     */
    var titleIconLocation: IconTextView.Location = IconTextView.Location.LEFT
        set(value) {
            field = value
            titleView?.let {
                if (it is IconTextView) {
                    it.iconLocation = value
                    it.reInitIcon()
                }
            }
        }

    /**
     * Form Element Title Icon Padding
     * Setting this set the padding between the title text and icon.
     */
    var titleIconPadding: Int = 20
        set(value) {
            field = value
            titleView?.let {
                if (it is IconTextView) {
                    it.iconPadding = value
                    it.reInitIcon()
                }
            }
        }

    /**
     * Form Element Clear on Focus
     * Setting this to true will clear the text value of the form element when focused.
     */
    var clearOnFocus: Boolean = false

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
     * Form Element Display Title besides the value field
     */
    var displayTitle: Boolean = true
        set(value) {
            field = value
            titleView?.let {
                it.visibility = if (displayTitle) View.VISIBLE else View.GONE
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
                it.isClickable = clickable
                it.isFocusable = focusable
                // Trigger visibility
                this.visible = this.visible

                if (backgroundColor != null) {
                    it.setBackgroundColor(backgroundColor ?: 0)
                }

                if (layoutPaddingBottom != null) {
                    it.setPadding(0, 0, 0, layoutPaddingBottom.dpToPx())
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
                it.isClickable = clickable
                it.isFocusable = focusable
                if (it is TextView && it !is AppCompatCheckBox && it !is AppCompatButton && it !is SwitchCompat) {
                    it.gravity = editViewGravity
                    it.isSingleLine = maxLines == 1
                    it.maxLines = maxLines

                    editViewPaintFlags?.let { paintFlags ->
                        it.paintFlags = it.paintFlags or paintFlags
                    }
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
                } else if (it is IconButton) {
                    if (valueTextColor != null) {
                        it.setTextColor(valueTextColor ?: 0)
                    }

                    it.gravity = editViewGravity
                } else if (it is SegmentedGroup) {
                    it.gravity = editViewGravity
                    if (margins != null) {
                        it.setMargins(margins?.left.dpToPx(),
                                margins?.top.dpToPx(),
                                margins?.right.dpToPx(),
                                margins?.bottom.dpToPx())
                    }
                }

                if (!(it is SwitchCompat || it is AppCompatCheckBox)) {
                    if (padding != null) {
                        it.setPadding(padding?.left.dpToPx(),
                                padding?.top.dpToPx(),
                                padding?.right.dpToPx(),
                                padding?.bottom.dpToPx())
                    }
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
                it.visibility = if (displayTitle) View.VISIBLE else View.GONE

                if (this is FormHeader || this is FormLabelElement) {
                    if (margins != null) {
                        it.setMargins(margins?.left.dpToPx(),
                                margins?.top.dpToPx(),
                                margins?.right.dpToPx(),
                                margins?.bottom.dpToPx())
                    }
                    it.gravity = editViewGravity

                    if (padding != null) {
                        it.setPadding(padding?.left.dpToPx(),
                                padding?.top.dpToPx(),
                                padding?.right.dpToPx(),
                                padding?.bottom.dpToPx())
                    }

                    editViewPaintFlags?.let { paintFlags ->
                        it.paintFlags = it.paintFlags or paintFlags
                    }
                }

                if (titlePadding != null) {
                    it.setPadding(titlePadding?.left.dpToPx(),
                            titlePadding?.top.dpToPx(),
                            titlePadding?.right.dpToPx(),
                            titlePadding?.bottom.dpToPx())
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
     * Form Element Main Layout View
     */
    var mainLayoutView: View? = null
        set(value) {
            field = value
            if (editView == null || !(editView is SwitchCompat || editView is AppCompatCheckBox)) {
                mainLayoutView?.let {
                    it.isEnabled = enabled
                    if (margins != null) {
                        it.setMargins(margins?.left.dpToPx(),
                                margins?.top.dpToPx(),
                                margins?.right.dpToPx(),
                                margins?.bottom.dpToPx())
                    }
                }
            }
            // Switch and CheckBox are special. To keep the ripple the correct height,
            // set the padding instead of the margins.
            else if (editView is SwitchCompat || editView is AppCompatCheckBox) {
                mainLayoutView?.let {
                    it.isEnabled = enabled
                    if (margins != null) {
                        it.setPadding(margins?.left.dpToPx(),
                                margins?.top.dpToPx(),
                                margins?.right.dpToPx(),
                                margins?.bottom.dpToPx())
                    }

                    if (padding != null) {
                        it.setPadding(padding?.left.dpToPx(),
                                padding?.top.dpToPx(),
                                padding?.right.dpToPx(),
                                padding?.bottom.dpToPx())
                    }
                }
            }

            if (this is FormHeader || this is FormLabelElement) {
                if (backgroundColor != null) {
                    mainLayoutView?.setBackgroundColor(backgroundColor ?: 0)
                }
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
    open var enabled: Boolean = true
        set(value) {
            field = value
            itemView?.isEnabled = value
            titleView?.isEnabled = value
            editView?.isEnabled = value
            mainLayoutView?.isEnabled = value

            onEnabled(value)
        }

    /**
     * Form Element Clickable
     */
    open var clickable: Boolean = true
        set(value) {
            field = value
            itemView?.isClickable = value
            titleView?.isClickable = value
            editView?.isClickable = value
        }

    /**
     * Form Element Focusable
     */
    open var focusable: Boolean = true
        set(value) {
            field = value
            itemView?.isFocusable = value
            titleView?.isFocusable = value
            editView?.isFocusable = value
        }

    /**
     * Form element property, enabled changed
     * Left intentionally blank in the base class
     */
    protected open fun onEnabled(enable: Boolean) { /* intentionally blank */
    }

    /**
     * Form Element Value String value
     */
    open val valueAsString: String
        get() = this.value?.toString() ?: ""

    /**
     * Base validation
     */
    open val isValid: Boolean
        get() = validityCheck()

    /**
     * Form element custom validity check
     */
    open var validityCheck: () -> Boolean = {
        !required || (required && value != null &&
                (value !is String || !(value as? String).isNullOrEmpty()))
    }

    /**
     * Displays the new value in the edit view
     */
    open fun displayNewValue() {
        editView?.let {
            if (it is AppCompatEditText && it.text.toString() != valueAsString) {
                it.setText(valueAsString)
            }
        }
    }

    /**
     * Clear edit view
     */
    open fun clear() {
        this.value = null
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
        return "FormElement(tag=$tag, title=$title, id=$id, value=$value, hint=$hint, " +
                "error=$error, required=$required, isValid=$isValid, visible=$visible)"
    }
}
