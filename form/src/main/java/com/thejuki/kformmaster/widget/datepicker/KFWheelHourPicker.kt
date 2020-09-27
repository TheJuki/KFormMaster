package com.thejuki.kformmaster.widget.datepicker

import android.content.Context
import android.util.AttributeSet
import com.aigestudio.wheelpicker.WheelPicker
import org.threeten.bp.LocalDate
import java.util.*

/**
 * Wheel Hour Picker
 *
 * @author **soareseneves** ([GitHub](https://github.com/soareseneves))
 * @version 1.0
 */
class KFWheelHourPicker @JvmOverloads constructor(
        context: Context?,
        attrs: AttributeSet? = null
) :
        WheelPicker(context, attrs), IKFWheelHourPicker {
    private var mSelectedHour: Int
    private var mSelectedDate: LocalDate
    private var cal = Calendar.getInstance()
    private var currentHour = cal[Calendar.HOUR_OF_DAY]
    private var mAllHours = true
    var hours: MutableList<Int?> = ArrayList()
    private fun updateHours() {
        val hourData: MutableList<String?> = ArrayList()

        cal = Calendar.getInstance()
        currentHour = cal[Calendar.HOUR_OF_DAY]

        var minHour = 0

        if (mSelectedDate == LocalDate.now() && !mAllHours) {
            minHour = currentHour
        }

        hours.removeAll { true }

        for (hour in minHour..23) {
            hourData.add(hour.toString().padStart(2, '0'))
            hours.add(hour)
        }

        super.setData(hourData)
    }

    private fun updateSelectedHour() {
        selectedItemPosition = hours.indexOf(mSelectedHour)
    }

    override fun setData(data: List<*>?) {
        throw UnsupportedOperationException("You can not invoke setData in WheelDayPicker")
    }

    override var selectedHour: Int
        get() {
            return mSelectedHour
        }
        set(hour) {
            mSelectedHour = hour
            updateSelectedHour()
        }

    override var selectedDate: LocalDate
        get() {
            return mSelectedDate
        }
        set(date) {
            mSelectedDate = date
            updateHours()
            if (mSelectedDate == LocalDate.now()) {
                selectedHour = currentHour
            } else {
                updateSelectedHour()
            }
        }

    override var allHours: Boolean
        get() {
            return mAllHours
        }
        set(allHours) {
            mAllHours = allHours
            updateHours()
            updateSelectedHour()
        }

    init {
        mSelectedDate = LocalDate.now()
        mSelectedHour = currentHour
        updateHours()
        updateSelectedHour()
    }
}