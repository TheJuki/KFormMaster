package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable
import android.widget.TextView

import java.io.Serializable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Form Picker DateTime Element
 *
 * Form element for AppCompatEditText (which on click opens a Date dialog and then a Time dialog)
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerDateTimeElement : FormPickerElement<FormPickerDateTimeElement.DateTimeHolder> {

    override fun clear() {
        this.value?.useCurrentDate()
        (this.editView as? TextView)?.text = ""
        this.valueObservers.forEach { it(this.value, this) }
    }

    /**
     * DateTime Holder
     *
     * Holds the date fields for [FormPickerDateTimeElement]
     */
    class DateTimeHolder : Serializable {

        var isEmptyDateTime: Boolean = false
        var dayOfMonth: Int? = null
        var month: Int? = null
        var year: Int? = null
        var hourOfDay: Int? = null
        var minute: Int? = null
        var dateFormat: DateFormat = SimpleDateFormat.getDateInstance()

        constructor(dayOfMonth: Int, month: Int, year: Int, hourOfDay: Int, minute: Int) {
            this.dayOfMonth = dayOfMonth
            this.month = month
            this.year = year
            this.hourOfDay = hourOfDay
            this.minute = minute
        }

        constructor() {
            useCurrentDate()
        }

        constructor(date: Date?, dateFormat: DateFormat = SimpleDateFormat.getDateInstance()) {
            if (date != null) {
                val calendar = Calendar.getInstance()
                calendar.time = date
                this.year = calendar.get(Calendar.YEAR)
                this.month = calendar.get(Calendar.MONTH) + 1
                this.dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
                this.hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
                this.minute = calendar.get(Calendar.MINUTE)
            } else {
                isEmptyDateTime = true
            }

            this.dateFormat = dateFormat
        }

        override fun toString(): String {
            return if (isEmptyDateTime || year == null || month == null || dayOfMonth == null ||
                    hourOfDay == null || minute == null) ""
            else dateFormat.format(getTime())
        }

        fun validOrCurrentDate() {
            if (year == null || month == null || dayOfMonth == null ||
                    hourOfDay == null || minute == null) {
                useCurrentDate()
            }
        }

        fun useCurrentDate() {
            val calendar = Calendar.getInstance()
            this.year = calendar.get(Calendar.YEAR)
            this.month = calendar.get(Calendar.MONTH) + 1
            this.dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            this.hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
            this.minute = calendar.get(Calendar.MINUTE)

            isEmptyDateTime = true
        }

        fun getTime(): Date? {
            if (isEmptyDateTime || year == null || month == null || dayOfMonth == null ||
                    hourOfDay == null || minute == null) {
                return null
            }
            val calendar = Calendar.getInstance()
            calendar.set(year ?: 0, if ((month ?: 0) == 0) 0 else (month ?: 0) - 1, dayOfMonth ?: 0)
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay ?: 0)
            calendar.set(Calendar.MINUTE, minute ?: 0)

            return calendar.time
        }

        fun getTime(zone: TimeZone,
                    aLocale: Locale): Date? {
            if (year == null || month == null || dayOfMonth == null ||
                    hourOfDay == null || minute == null) {
                return null
            }
            val calendar = Calendar.getInstance(zone, aLocale)
            calendar.set(year ?: 0, if ((month ?: 0) == 0) 0 else (month ?: 0) - 1, dayOfMonth ?: 0)
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay ?: 0)
            calendar.set(Calendar.MINUTE, minute ?: 0)

            return calendar.time
        }

        fun equals(another: DateTimeHolder): Boolean {
            return another.isEmptyDateTime == this.isEmptyDateTime &&
                    another.year == this.year &&
                    another.month == this.month &&
                    another.dayOfMonth == this.dayOfMonth &&
                    another.minute == this.minute &&
                    another.hourOfDay == this.hourOfDay
        }
    }

    override val isValid: Boolean
        get() = !required || (value != null && value?.getTime() != null)

    /**
     * Parcelable boilerplate
     */
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        super.writeToParcel(dest, flags)
    }

    constructor(tag: Int = -1) : super(tag)

    constructor(`in`: Parcel) : super(`in`) {}

    companion object {
        /**
         * Creates an instance
         */
        fun createDateTimeInstance(): FormPickerDateTimeElement {
            return FormPickerDateTimeElement()
        }

        val CREATOR: Parcelable.Creator<FormPickerDateTimeElement> = object : Parcelable.Creator<FormPickerDateTimeElement> {
            override fun createFromParcel(source: Parcel): FormPickerDateTimeElement {
                return FormPickerDateTimeElement(source)
            }

            override fun newArray(size: Int): Array<FormPickerDateTimeElement?> {
                return arrayOfNulls(size)
            }
        }
    }
}
