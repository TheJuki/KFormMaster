package com.thejuki.kformmaster.model

import android.widget.TextView
import java.io.Serializable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Form Picker Time Element
 *
 * Form element for AppCompatEditText (which on click opens a Time dialog)
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerTimeElement(tag: Int = -1) : FormPickerElement<FormPickerTimeElement.TimeHolder>(tag) {

    /**
     * Date Format part of TimeHolder
     */
    var dateFormat: DateFormat = SimpleDateFormat.getDateInstance()
        set(value) {
            field = value
            this.value = FormPickerTimeElement.TimeHolder(dateValue, dateFormat)
        }

    /**
     * Date Value part of TimeHolder
     */
    var dateValue: Date? = null
        set(value) {
            field = value
            this.value = FormPickerTimeElement.TimeHolder(dateValue, dateFormat)
        }

    override fun clear() {
        this.value?.useCurrentTime()
        (this.editView as? TextView)?.text = ""
        this.valueObservers.forEach { it(this.value, this) }
    }

    /**
     * Time Holder
     *
     * Holds the date fields for [FormPickerTimeElement]
     */
    class TimeHolder : Serializable {

        var isEmptyTime: Boolean = false
        var hourOfDay: Int? = null
        var minute: Int? = null
        var dateFormat: DateFormat = SimpleDateFormat.getTimeInstance()

        constructor(hourOfDay: Int, minute: Int) {
            this.hourOfDay = hourOfDay
            this.minute = minute
        }

        constructor() {
            useCurrentTime()
        }

        constructor(date: Date?, dateFormat: DateFormat = SimpleDateFormat.getDateInstance()) {
            if (date != null) {
                val calendar = Calendar.getInstance()
                calendar.time = date
                this.hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
                this.minute = calendar.get(Calendar.MINUTE)
            } else {
                isEmptyTime = true
            }
            this.dateFormat = dateFormat
        }

        override fun toString(): String {
            return if (isEmptyTime || hourOfDay == null || minute == null) ""
            else dateFormat.format(getTime())
        }

        fun getTime(): Date? {
            if (isEmptyTime || hourOfDay == null || minute == null) {
                return null
            }
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay ?: 0)
            calendar.set(Calendar.MINUTE, minute ?: 0)

            return calendar.time
        }

        fun getTime(zone: TimeZone,
                    aLocale: Locale): Date? {
            if (hourOfDay == null || minute == null) {
                return null
            }
            val calendar = Calendar.getInstance(zone, aLocale)
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay ?: 0)
            calendar.set(Calendar.MINUTE, minute ?: 0)

            return calendar.time
        }

        fun validOrCurrentTime() {
            if (hourOfDay == null || minute == null) {
                useCurrentTime()
            }
        }

        fun useCurrentTime() {
            val calendar = Calendar.getInstance()
            this.hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
            this.minute = calendar.get(Calendar.MINUTE)
            isEmptyTime = true
        }

        fun equals(another: TimeHolder): Boolean {
            return another.isEmptyTime == this.isEmptyTime &&
                    another.minute == this.minute &&
                    another.hourOfDay == this.hourOfDay
        }
    }

    override val isValid: Boolean
        get() = !required || (value != null && value?.getTime() != null)
}