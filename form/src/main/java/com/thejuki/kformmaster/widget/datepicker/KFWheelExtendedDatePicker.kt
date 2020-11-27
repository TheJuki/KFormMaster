package com.thejuki.kformmaster.widget.datepicker

import android.content.Context
import android.util.AttributeSet
import com.aigestudio.wheelpicker.WheelPicker
import com.thejuki.kformmaster.extensions.rangeTo
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

/**
 * Wheel Extender Date Picker
 *
 * @author **soareseneves** ([GitHub](https://github.com/soareseneves))
 * @version 1.0
 */
class KFWheelExtendedDatePicker @JvmOverloads constructor(
        context: Context?,
        attrs: AttributeSet? = null
) :
        WheelPicker(context, attrs), IKFWheelExtendedDatePicker {
    private var mSelectedDate: LocalDate = LocalDate.now()
    private var mStartDate: LocalDate? = null
    var dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE
    var dates: MutableList<LocalDate?> = ArrayList()

    private fun updateDates() {

        val startDate: LocalDate = if (this.startDate != null)
            this.startDate!!
        else
            LocalDate.now().minusYears(100)

        val endDate = LocalDate.now().plusYears(100)

        val data: MutableList<String?> = ArrayList()

        dates.clear()

        for (date in startDate..endDate step 1) {
            val formattedDate = date.format(dateTimeFormatter)
            data.add(formattedDate)
            dates.add(date)
        }

        super.setData(data)
    }

    private fun updateSelectedDate() {
        selectedItemPosition = dates.indexOf(mSelectedDate)
    }

    override fun setData(data: List<*>?) {
        throw UnsupportedOperationException("You can not invoke setData in KFWheelExtendedDatePicker")
    }

    override var selectedDate: LocalDate
        get() {
            return mSelectedDate
        }
        set(date) {
            mSelectedDate = date
            updateSelectedDate()
        }

    override var startDate: LocalDate?
        get() {
            return mStartDate
        }
        set(date) {
            mStartDate = date
            updateDates()
            updateSelectedDate()
        }
}