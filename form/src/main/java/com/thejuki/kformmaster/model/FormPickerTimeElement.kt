package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable
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
class FormPickerTimeElement : FormPickerElement<FormPickerTimeElement.TimeHolder> {
    class TimeHolder : Serializable {

        private var isEmptyTime: Boolean = false
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

        constructor(date: Date?, dateFormat: DateFormat) {
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
            if (hourOfDay == null || minute == null) {
                return null
            }
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay!!)
            calendar.set(Calendar.MINUTE, minute!!)

            return calendar.time
        }

        fun getTime(zone: TimeZone,
                    aLocale: Locale): Date? {
            if (hourOfDay == null || minute == null) {
                return null
            }
            val calendar = Calendar.getInstance(zone, aLocale)
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay!!)
            calendar.set(Calendar.MINUTE, minute!!)

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
        }

        fun equals(another: TimeHolder): Boolean {
            return another.isEmptyTime == this.isEmptyTime &&
                    another.minute == this.minute &&
                    another.hourOfDay == this.hourOfDay
        }
    }

    override fun getType(): Int {
        return BaseFormElement.TYPE_PICKER_TIME
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

    constructor(tag: Int = 0) : super(tag)

    protected constructor(`in`: Parcel) : super(`in`) {}

    companion object {

        fun createTimeInstance(): FormPickerTimeElement {
            return FormPickerTimeElement()
        }

        val CREATOR: Parcelable.Creator<FormPickerTimeElement> = object : Parcelable.Creator<FormPickerTimeElement> {
            override fun createFromParcel(source: Parcel): FormPickerTimeElement {
                return FormPickerTimeElement(source)
            }

            override fun newArray(size: Int): Array<FormPickerTimeElement?> {
                return arrayOfNulls(size)
            }
        }
    }
}