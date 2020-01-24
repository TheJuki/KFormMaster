package com.thejuki.kformmaster.model

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Form Picker Date Element
 *
 * Form element for AppCompatEditText (which on click opens a Date dialog)
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerDateElement(tag: Int = -1) : FormPickerElement<FormPickerDateElement.DateHolder>(tag) {

    /**
     * Start Date for the DateTimeHolder if the dateValue is null
     */
    var startDate: Date? = null
        set(value) {
            field = value
            this.value = DateHolder(dateValue, dateFormat, startDate)
            reInitDialog()
        }

    /**
     * Date Format part of DateHolder
     */
    var dateFormat: DateFormat = SimpleDateFormat.getDateInstance()
        set(value) {
            field = value
            this.value = DateHolder(dateValue, dateFormat, startDate)
            reInitDialog()
        }

    /**
     * Date Value part of DateHolder
     */
    var dateValue: Date? = null
        set(value) {
            field = value
            this.value = DateHolder(dateValue, dateFormat, startDate)
            reInitDialog()
        }

    /**
     * Maximum Date/Time in the picker
     */
    var maximumDate: Date? = null
        set(value) {
            field = value
            reInitDialog()
        }

    /**
     * Minimum Date/Time in the picker
     */
    var minimumDate: Date? = null
        set(value) {
            field = value
            reInitDialog()
        }

    /**
     * Theme
     */
    var theme: Int = 0

    /**
     * Alert Dialog Builder
     * Used to call reInitDialog without needing context again.
     */
    private var alertDialogBuilder: AlertDialog.Builder? = null

    /**
     * Hold the [OnFormElementValueChangedListener] from [FormBuildHelper]
     */
    private var listener: OnFormElementValueChangedListener? = null

    override fun clear() {
        this.value?.useCurrentDate()
        this.displayNewValue()
        this.valueObservers.forEach { it(this.value, this) }
    }

    /**
     * Date Holder
     *
     * Holds the date fields for [FormPickerDateElement]
     */
    class DateHolder {

        var isEmptyDate: Boolean = false
        var dayOfMonth: Int? = null
        var month: Int? = null
        var year: Int? = null
        var dateFormat: DateFormat = SimpleDateFormat.getDateInstance()

        constructor(dayOfMonth: Int, month: Int, year: Int) {
            this.dayOfMonth = dayOfMonth
            this.month = month
            this.year = year
        }

        constructor() {
            useCurrentDate()
        }

        constructor(date: Date?, dateFormat: DateFormat = SimpleDateFormat.getDateInstance(), startDate: Date? = null) {
            if (date != null) {
                val calendar = Calendar.getInstance()
                calendar.time = date
                this.year = calendar.get(Calendar.YEAR)
                this.month = calendar.get(Calendar.MONTH) + 1
                this.dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            } else {
                if (startDate != null) {
                    val calendar = Calendar.getInstance()
                    calendar.time = startDate
                    this.year = calendar.get(Calendar.YEAR)
                    this.month = calendar.get(Calendar.MONTH) + 1
                    this.dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
                }
                isEmptyDate = true
            }

            this.dateFormat = dateFormat
        }

        override fun toString(): String {
            val date = getTime()

            return if (date == null) ""
            else dateFormat.format(date)
        }

        fun validOrCurrentDate() {
            if (year == null || month == null || dayOfMonth == null) {
                useCurrentDate()
            }
        }

        fun useCurrentDate() {
            val calendar = Calendar.getInstance()
            this.year = calendar.get(Calendar.YEAR)
            this.month = calendar.get(Calendar.MONTH) + 1
            this.dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            isEmptyDate = true
        }

        fun getTime(): Date? {
            if (isEmptyDate || year == null || month == null || dayOfMonth == null) {
                return null
            }
            val calendar = Calendar.getInstance()
            calendar.set(year ?: 0, if ((month ?: 0) == 0) 0 else (month ?: 0) - 1, dayOfMonth ?: 0)

            return calendar.time
        }

        fun getTime(zone: TimeZone,
                    aLocale: Locale): Date? {
            if (year == null || month == null || dayOfMonth == null) {
                return null
            }
            val calendar = Calendar.getInstance(zone, aLocale)
            calendar.set(year ?: 0, if ((month ?: 0) == 0) 0 else (month ?: 0) - 1, dayOfMonth ?: 0)

            return calendar.time
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as DateHolder

            if (isEmptyDate != other.isEmptyDate) return false
            if (dayOfMonth != other.dayOfMonth) return false
            if (month != other.month) return false
            if (year != other.year) return false
            if (dateFormat != other.dateFormat) return false

            return true
        }

        override fun hashCode(): Int {
            var result = isEmptyDate.hashCode()
            result = 31 * result + (dayOfMonth ?: 0)
            result = 31 * result + (month ?: 0)
            result = 31 * result + (year ?: 0)
            result = 31 * result + dateFormat.hashCode()
            return result
        }
    }

    override val isValid: Boolean
        get() = validityCheck()

    override var validityCheck = { !required || (value != null && value?.getTime() != null) }

    /**
     * Re-initializes the dialog
     * Should be called value changes by user
     */
    fun reInitDialog(formBuilder: FormBuildHelper? = null) {

        if (formBuilder != null) {
            listener = formBuilder.listener
        }

        val editTextView = this.editView as? AppCompatEditText

        if (editTextView?.context == null) {
            return
        }

        val datePickerDialog = DatePickerDialog(editTextView.context,
                theme,
                dateDialogListener(editTextView),
                value?.year ?: 0,
                if ((value?.month ?: 0) == 0) 0 else (value?.month ?: 0) - 1,
                value?.dayOfMonth ?: 0)

        maximumDate?.let {
            datePickerDialog.datePicker.maxDate = it.time
        }

        minimumDate?.let {
            datePickerDialog.datePicker.minDate = it.time
        }

        if (alertDialogBuilder == null) {
            alertDialogBuilder = AlertDialog.Builder(editTextView.context, theme)
            if (this.confirmTitle == null) {
                this.confirmTitle = editTextView.context.getString(R.string.form_master_confirm_title)
            }
            if (this.confirmMessage == null) {
                this.confirmMessage = editTextView.context.getString(R.string.form_master_confirm_message)
            }
        }

        // display the dialog on click
        val listener = View.OnClickListener {
            // Invoke onClick Unit
            this.onClick?.invoke()

            if (!confirmEdit || valueAsString.isEmpty()) {
                datePickerDialog.show()
            } else if (confirmEdit && value != null) {
                alertDialogBuilder
                        ?.setTitle(confirmTitle)
                        ?.setMessage(confirmMessage)
                        ?.setPositiveButton(android.R.string.ok) { _, _ ->
                            datePickerDialog.show()
                        }?.setNegativeButton(android.R.string.cancel) { _, _ -> }?.show()
            }
        }

        itemView?.setOnClickListener(listener)
        editTextView.setOnClickListener(listener)
    }

    private fun dateDialogListener(editTextView: AppCompatEditText): DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            // get current form element, existing value and new value
            with(value)
            {
                this?.year = year
                this?.month = monthOfYear + 1
                this?.dayOfMonth = dayOfMonth
                this?.isEmptyDate = false
            }

            error = null
            listener?.onValueChanged(this)
            valueObservers.forEach { it(value, this) }
            editTextView.setText(valueAsString)
        }
    }

    override fun displayNewValue() {
        editView?.let {
            if (it is TextView) {
                it.text = valueAsString
            }
        }
    }
}
