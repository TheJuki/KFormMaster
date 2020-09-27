package com.thejuki.kformmaster.widget.datepicker

import android.content.Context
import android.util.AttributeSet
import com.aigestudio.wheelpicker.WheelPicker
import org.threeten.bp.LocalDate
import java.util.*

/**
 * Wheel Minute Picker
 *
 * @author **soareseneves** ([GitHub](https://github.com/soareseneves))
 * @version 1.0
 */
class KFWheelMinutePicker @JvmOverloads constructor(
        context: Context?,
        attrs: AttributeSet? = null
) :
        WheelPicker(context, attrs), IKFWheelMinutePicker {
    private var mSelectedMinute: Int
    private var mSelectedDate: LocalDate
    private var cal = Calendar.getInstance()
    private var currentMinute = cal[Calendar.MINUTE]
    private var mAllMinutes = true
    var minutes: MutableList<Int?> = ArrayList()
    private fun updateMinutes() {
        val minuteData: MutableList<String?> = ArrayList()

        cal = Calendar.getInstance()
        currentMinute = cal[Calendar.MINUTE]

        var minMinute = 0

        if (mSelectedDate == LocalDate.now() && !mAllMinutes) {
            minMinute = currentMinute
        }

        minutes.removeAll { true }

        for (minute in minMinute..59) {
            minuteData.add(minute.toString().padStart(2, '0'))
            minutes.add(minute)
        }

        super.setData(minuteData)
    }

    private fun updateSelectedMinute() {
        selectedItemPosition = minutes.indexOf(mSelectedMinute)
    }

    override fun setData(data: List<*>?) {
        throw UnsupportedOperationException("You can not invoke setData in KFWheelMinutePicker")
    }

    override var selectedMinute: Int
        get() {
            return mSelectedMinute
        }
        set(hour) {
            mSelectedMinute = hour
            updateSelectedMinute()
        }

    override var selectedDate: LocalDate
        get() {
            return mSelectedDate
        }
        set(date) {
            mSelectedDate = date
            updateMinutes()
            if (mSelectedDate == LocalDate.now()) {
                selectedMinute = currentMinute
            } else {
                updateSelectedMinute()
            }
        }

    override var allMinutes: Boolean
        get() {
            return mAllMinutes
        }
        set(allHours) {
            mAllMinutes = allHours
            updateMinutes()
            updateSelectedMinute()
        }

    init {
        mSelectedDate = LocalDate.now()
        mSelectedMinute = currentMinute
        updateMinutes()
        updateSelectedMinute()
    }
}