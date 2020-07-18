package com.thejuki.kformmaster.widget.datepicker

import android.content.Context
import android.util.AttributeSet
import com.thejuki.kformmaster.extensions.rangeTo
import com.aigestudio.wheelpicker.WheelPicker
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

class KFWheelExtendedDatePicker @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null
) :
    WheelPicker(context, attrs), IKFWheelExtendedDatePicker {
    private var mSelectedDate: LocalDate
    private var mStartDate: LocalDate? = null
    var dates : MutableList<LocalDate?> = ArrayList()
    private fun updateDates() {

        val startDate : LocalDate

        if (this.startDate != null)
            startDate = this.startDate!!
        else
            startDate = LocalDate.now().minusYears(100)

        val endDate = LocalDate.now().plusYears(100)

        val data : MutableList<String?> = ArrayList()

        dates.removeAll { true }

        for (date in startDate..endDate step 1) {
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("E d 'de' MMM")
            val formattedDate = date.format(formatter)
            data.add(formattedDate)
            dates.add(date)
        }

        super.setData(data)
    }

    private fun updateSelectedDate() {
        selectedItemPosition = dates.indexOf(mSelectedDate)
    }

    override fun setData(data: List<*>?) {
        throw UnsupportedOperationException("You can not invoke setData in WheelDayPicker")
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

    init {
        updateDates()
        mSelectedDate = LocalDate.now()
        updateSelectedDate()
    }
}