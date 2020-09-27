package com.thejuki.kformmaster.widget.datepicker

import android.content.Context
import android.util.AttributeSet
import com.aigestudio.wheelpicker.WheelPicker
import com.aigestudio.wheelpicker.widgets.IWheelDayPicker
import java.util.*

/**
 * Wheel Day Picker
 *
 * @author **soareseneves** ([GitHub](https://github.com/soareseneves))
 * @version 1.0
 */
class KFWheelDayPicker @JvmOverloads constructor(
        context: Context?,
        attrs: AttributeSet? = null
) :
        WheelPicker(context, attrs), IWheelDayPicker {
    private val mCalendar: Calendar
    private var mYear: Int
    private var mMonth: Int
    private var mSelectedDay: Int
    private fun updateDays() {
        mCalendar[Calendar.YEAR] = mYear
        mCalendar[Calendar.MONTH] = mMonth - 1
        val days = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        var data =
                DAYS[days]
        if (null == data) {
            data = ArrayList()
            for (i in 1..days) data.add(i.toString().padStart(2, '0'))
            DAYS[days] = data
        }
        super.setData(data)
    }

    private fun updateSelectedDay() {
        selectedItemPosition = mSelectedDay - 1
    }

    override fun setData(data: List<*>?) {
        throw UnsupportedOperationException("You can not invoke setData in WheelDayPicker")
    }

    override fun getSelectedDay(): Int {
        return mSelectedDay
    }

    override fun setSelectedDay(day: Int) {
        mSelectedDay = day
        updateSelectedDay()
    }

    override fun getCurrentDay(): Int {
        return Integer.valueOf(data[currentItemPosition].toString())
    }

    override fun setYearAndMonth(year: Int, month: Int) {
        mYear = year
        mMonth = month - 1
        updateDays()
    }

    override fun getYear(): Int {
        return mYear
    }

    override fun setYear(year: Int) {
        mYear = year
        updateDays()
    }

    override fun getMonth(): Int {
        return mMonth
    }

    override fun setMonth(month: Int) {
        mMonth = month - 1
        updateDays()
    }

    companion object {
        private val DAYS: MutableMap<Int, MutableList<String?>> =
                HashMap()
    }

    init {
        mCalendar = Calendar.getInstance()
        mYear = mCalendar[Calendar.YEAR]
        mMonth = mCalendar[Calendar.MONTH] + 1
        updateDays()
        mSelectedDay = mCalendar[Calendar.DAY_OF_MONTH]
        updateSelectedDay()
    }
}