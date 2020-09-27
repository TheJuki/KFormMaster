package com.thejuki.kformmaster.widget.datepicker

import android.content.Context
import android.util.AttributeSet
import com.aigestudio.wheelpicker.WheelPicker
import com.aigestudio.wheelpicker.widgets.IWheelMonthPicker
import java.util.*

/**
 * Wheel Month Picker
 *
 * @author **soareseneves** ([GitHub](https://github.com/soareseneves))
 * @version 1.0
 */
class KFWheelMonthPicker @JvmOverloads constructor(
        context: Context?,
        attrs: AttributeSet? = null
) :
        WheelPicker(context, attrs), IWheelMonthPicker {
    private var mSelectedMonth: Int
    private fun updateSelectedYear() {
        selectedItemPosition = mSelectedMonth - 1
    }

    override fun setData(data: List<*>?) {
        throw UnsupportedOperationException("You can not invoke setData in WheelMonthPicker")
    }

    override fun getSelectedMonth(): Int {
        return mSelectedMonth
    }

    override fun setSelectedMonth(month: Int) {
        mSelectedMonth = month
        updateSelectedYear()
    }

    override fun getCurrentMonth(): Int {
        return Integer.valueOf(data[currentItemPosition].toString())
    }

    init {
        val data: MutableList<String?> = ArrayList()
        for (i in 1..12) data.add(i.toString().padStart(2, '0'))
        super.setData(data)
        mSelectedMonth = Calendar.getInstance()[Calendar.MONTH] + 1
        updateSelectedYear()
    }
}