package com.thejuki.kformmaster.widget.datepicker

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import com.aigestudio.wheelpicker.IDebug
import com.aigestudio.wheelpicker.IWheelPicker
import com.aigestudio.wheelpicker.R
import com.aigestudio.wheelpicker.WheelPicker
import com.aigestudio.wheelpicker.WheelPicker.OnWheelChangeListener
import com.aigestudio.wheelpicker.widgets.IWheelDayPicker
import com.aigestudio.wheelpicker.widgets.IWheelMonthPicker
import com.aigestudio.wheelpicker.widgets.IWheelYearPicker
import com.aigestudio.wheelpicker.widgets.WheelYearPicker
import com.thejuki.kformmaster.extensions.toDate
import org.threeten.bp.LocalDate
import java.text.ParseException
import java.util.*

/**
 * Wheel Date Picker
 *
 * @author **soareseneves** ([GitHub](https://github.com/soareseneves))
 * @version 1.0
 */
class KFWheelDatePicker @JvmOverloads constructor(
        context: Context?,
        attrs: AttributeSet? = null
) :
        LinearLayout(context, attrs), WheelPicker.OnItemSelectedListener,
        IDebug, IWheelPicker,
        IKFWheelDatePicker,
        IWheelYearPicker, IWheelMonthPicker, IWheelDayPicker {
    private val mPickerYear: WheelYearPicker
    private val mPickerMonth: KFWheelMonthPicker
    private val mPickerDay: KFWheelDayPicker
    private var mListener: SWOnDateSelectedListener? = null
    private var mYear: Int
    private var mMonth: Int
    private var mDay: Int
    private var mStartDate: LocalDate? = null
    private fun setMaximumWidthTextYear() {
        val years = mPickerYear.data
        val lastYear = years[years.size - 1].toString()
        val sb = StringBuilder()
        for (i in lastYear.indices) sb.append("0")
        mPickerYear.maximumWidthText = sb.toString()
    }

    override fun onItemSelected(
            picker: WheelPicker,
            data: Any,
            position: Int
    ) {
        if (picker.id == R.id.wheel_date_picker_year) {
            mYear = if (data is Int)
                data
            else
                (data as String).toInt()
            mPickerDay.year = mYear
        } else if (picker.id == R.id.wheel_date_picker_month) {
            mMonth = if (data is Int)
                data
            else
                (data as String).toInt()
            mPickerDay.month = mMonth
        }
        mDay = mPickerDay.currentDay
        if (null != mListener) try {
            mListener?.onDateSelected(this, currentDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    override fun setDebug(isDebug: Boolean) {
        mPickerYear.setDebug(isDebug)
        mPickerMonth.setDebug(isDebug)
        mPickerDay.setDebug(isDebug)
    }

    override fun getVisibleItemCount(): Int {
        if (mPickerYear.visibleItemCount == mPickerMonth.visibleItemCount &&
                mPickerMonth.visibleItemCount == mPickerDay.visibleItemCount
        ) return mPickerYear.visibleItemCount
        throw ArithmeticException(
                "Can not get visible item count correctly from" +
                        "WheelDatePicker!"
        )
    }

    override fun setVisibleItemCount(count: Int) {
        mPickerYear.visibleItemCount = count
        mPickerMonth.visibleItemCount = count
        mPickerDay.visibleItemCount = count
    }

    override fun isCyclic(): Boolean {
        return mPickerYear.isCyclic && mPickerMonth.isCyclic && mPickerDay.isCyclic
    }

    override fun setCyclic(isCyclic: Boolean) {
        mPickerYear.isCyclic = isCyclic
        mPickerMonth.isCyclic = isCyclic
        mPickerDay.isCyclic = isCyclic
    }

    @Deprecated("")
    override fun setOnItemSelectedListener(listener: WheelPicker.OnItemSelectedListener) {
        throw UnsupportedOperationException(
                "You can not set OnItemSelectedListener for" +
                        "WheelDatePicker"
        )
    }

    @Deprecated("")
    override fun getSelectedItemPosition(): Int {
        throw UnsupportedOperationException(
                "You can not get position of selected item from" +
                        "WheelDatePicker"
        )
    }

    @Deprecated("")
    override fun setSelectedItemPosition(position: Int) {
        throw UnsupportedOperationException(
                "You can not set position of selected item for" +
                        "WheelDatePicker"
        )
    }

    @Deprecated("")
    override fun getCurrentItemPosition(): Int {
        throw UnsupportedOperationException(
                "You can not get position of current item from" +
                        "WheelDatePicker"
        )
    }

    @Deprecated("")
    override fun getData(): List<*> {
        throw UnsupportedOperationException("You can not get data source from WheelDatePicker")
    }

    @Deprecated("")
    override fun setData(data: List<*>?) {
        throw UnsupportedOperationException(
                "You don't need to set data source for" +
                        "WheelDatePicker"
        )
    }

    @Deprecated("")
    override fun setSameWidth(hasSameSize: Boolean) {
        throw UnsupportedOperationException(
                "You don't need to set same width for" +
                        "WheelDatePicker"
        )
    }

    @Deprecated("")
    override fun hasSameWidth(): Boolean {
        throw UnsupportedOperationException(
                "You don't need to set same width for" +
                        "WheelDatePicker"
        )
    }

    @Deprecated("")
    override fun setOnWheelChangeListener(listener: OnWheelChangeListener) {
        throw UnsupportedOperationException(
                "WheelDatePicker unsupport set" +
                        "OnWheelChangeListener"
        )
    }

    @Deprecated("")
    override fun getMaximumWidthText(): String {
        throw UnsupportedOperationException(
                "You can not get maximum width text from" +
                        "WheelDatePicker"
        )
    }

    @Deprecated("")
    override fun setMaximumWidthText(text: String) {
        throw UnsupportedOperationException(
                "You don't need to set maximum width text for" +
                        "WheelDatePicker"
        )
    }

    @Deprecated("")
    override fun getMaximumWidthTextPosition(): Int {
        throw UnsupportedOperationException(
                "You can not get maximum width text position" +
                        "from WheelDatePicker"
        )
    }

    @Deprecated("")
    override fun setMaximumWidthTextPosition(position: Int) {
        throw UnsupportedOperationException(
                "You don't need to set maximum width text" +
                        "position for WheelDatePicker"
        )
    }

    override fun getSelectedItemTextColor(): Int {
        if (mPickerYear.selectedItemTextColor == mPickerMonth.selectedItemTextColor &&
                mPickerMonth.selectedItemTextColor == mPickerDay.selectedItemTextColor
        ) return mPickerYear.selectedItemTextColor
        throw RuntimeException(
                "Can not get color of selected item text correctly from" +
                        "WheelDatePicker!"
        )
    }

    override fun setSelectedItemTextColor(color: Int) {
        mPickerYear.selectedItemTextColor = color
        mPickerMonth.selectedItemTextColor = color
        mPickerDay.selectedItemTextColor = color
    }

    override fun getItemTextColor(): Int {
        if (mPickerYear.itemTextColor == mPickerMonth.itemTextColor &&
                mPickerMonth.itemTextColor == mPickerDay.itemTextColor
        ) return mPickerYear.itemTextColor
        throw RuntimeException(
                "Can not get color of item text correctly from" +
                        "WheelDatePicker!"
        )
    }

    override fun setItemTextColor(color: Int) {
        mPickerYear.itemTextColor = color
        mPickerMonth.itemTextColor = color
        mPickerDay.itemTextColor = color
    }

    override fun getItemTextSize(): Int {
        if (mPickerYear.itemTextSize == mPickerMonth.itemTextSize &&
                mPickerMonth.itemTextSize == mPickerDay.itemTextSize
        ) return mPickerYear.itemTextSize
        throw RuntimeException(
                "Can not get size of item text correctly from" +
                        "WheelDatePicker!"
        )
    }

    override fun setItemTextSize(size: Int) {
        mPickerYear.itemTextSize = size
        mPickerMonth.itemTextSize = size
        mPickerDay.itemTextSize = size
    }

    override fun getItemSpace(): Int {
        if (mPickerYear.itemSpace == mPickerMonth.itemSpace &&
                mPickerMonth.itemSpace == mPickerDay.itemSpace
        ) return mPickerYear.itemSpace
        throw RuntimeException("Can not get item space correctly from WheelDatePicker!")
    }

    override fun setItemSpace(space: Int) {
        mPickerYear.itemSpace = space
        mPickerMonth.itemSpace = space
        mPickerDay.itemSpace = space
    }

    override fun setIndicator(hasIndicator: Boolean) {
        mPickerYear.setIndicator(hasIndicator)
        mPickerMonth.setIndicator(hasIndicator)
        mPickerDay.setIndicator(hasIndicator)
    }

    override fun hasIndicator(): Boolean {
        return mPickerYear.hasIndicator() && mPickerMonth.hasIndicator() &&
                mPickerDay.hasIndicator()
    }

    override fun getIndicatorSize(): Int {
        if (mPickerYear.indicatorSize == mPickerMonth.indicatorSize &&
                mPickerMonth.indicatorSize == mPickerDay.indicatorSize
        ) return mPickerYear.indicatorSize
        throw RuntimeException("Can not get indicator size correctly from WheelDatePicker!")
    }

    override fun setIndicatorSize(size: Int) {
        mPickerYear.indicatorSize = size
        mPickerMonth.indicatorSize = size
        mPickerDay.indicatorSize = size
    }

    override fun getIndicatorColor(): Int {
        if (mPickerYear.curtainColor == mPickerMonth.curtainColor &&
                mPickerMonth.curtainColor == mPickerDay.curtainColor
        ) return mPickerYear.curtainColor
        throw RuntimeException("Can not get indicator color correctly from WheelDatePicker!")
    }

    override fun setIndicatorColor(color: Int) {
        mPickerYear.indicatorColor = color
        mPickerMonth.indicatorColor = color
        mPickerDay.indicatorColor = color
    }

    override fun setCurtain(hasCurtain: Boolean) {
        mPickerYear.setCurtain(hasCurtain)
        mPickerMonth.setCurtain(hasCurtain)
        mPickerDay.setCurtain(hasCurtain)
    }

    override fun hasCurtain(): Boolean {
        return mPickerYear.hasCurtain() && mPickerMonth.hasCurtain() &&
                mPickerDay.hasCurtain()
    }

    override fun getCurtainColor(): Int {
        if (mPickerYear.curtainColor == mPickerMonth.curtainColor &&
                mPickerMonth.curtainColor == mPickerDay.curtainColor
        ) return mPickerYear.curtainColor
        throw RuntimeException("Can not get curtain color correctly from WheelDatePicker!")
    }

    override fun setCurtainColor(color: Int) {
        mPickerYear.curtainColor = color
        mPickerMonth.curtainColor = color
        mPickerDay.curtainColor = color
    }

    override fun setAtmospheric(hasAtmospheric: Boolean) {
        mPickerYear.setAtmospheric(hasAtmospheric)
        mPickerMonth.setAtmospheric(hasAtmospheric)
        mPickerDay.setAtmospheric(hasAtmospheric)
    }

    override fun hasAtmospheric(): Boolean {
        return mPickerYear.hasAtmospheric() && mPickerMonth.hasAtmospheric() &&
                mPickerDay.hasAtmospheric()
    }

    override fun isCurved(): Boolean {
        return mPickerYear.isCurved && mPickerMonth.isCurved && mPickerDay.isCurved
    }

    override fun setCurved(isCurved: Boolean) {
        mPickerYear.isCurved = isCurved
        mPickerMonth.isCurved = isCurved
        mPickerDay.isCurved = isCurved
    }

    @Deprecated("")
    override fun getItemAlign(): Int {
        throw UnsupportedOperationException("You can not get item align from WheelDatePicker")
    }

    @Deprecated("")
    override fun setItemAlign(align: Int) {
        throw UnsupportedOperationException(
                "You don't need to set item align for" +
                        "WheelDatePicker"
        )
    }

    override fun getTypeface(): Typeface {
        if (mPickerYear.typeface == mPickerMonth.typeface && mPickerMonth.typeface == mPickerDay.typeface) return mPickerYear.typeface
        throw RuntimeException("Can not get typeface correctly from WheelDatePicker!")
    }

    override fun setTypeface(tf: Typeface) {
        mPickerYear.typeface = tf
        mPickerMonth.typeface = tf
        mPickerDay.typeface = tf
    }

    override fun setOnDateSelectedListener(listener: SWOnDateSelectedListener?) {
        mListener = listener
    }

    override val currentDate: Date?
        get() {
            return LocalDate.of(mYear, mMonth, mDay).toDate()
        }

    override var itemAlignYear: Int
        get() {
            return mPickerYear.itemAlign
        }
        set(align) {
            mPickerYear.itemAlign = align
        }

    override var itemAlignMonth: Int
        get() {
            return mPickerMonth.itemAlign
        }
        set(align) {
            mPickerMonth.itemAlign = align
        }

    override var itemAlignDay: Int
        get() {
            return mPickerDay.itemAlign
        }
        set(align) {
            mPickerDay.itemAlign = align
        }

    override val wheelYearPicker: WheelYearPicker?
        get() {
            return mPickerYear
        }

    override val wheelMonthPicker: KFWheelMonthPicker?
        get() {
            return mPickerMonth
        }

    override val wheelDayPicker: KFWheelDayPicker?
        get() {
            return mPickerDay
        }

    override fun setYearFrame(start: Int, end: Int) {
        mPickerYear.setYearFrame(start, end)
    }

    override fun getYearStart(): Int {
        return mPickerYear.yearStart
    }

    override fun setYearStart(start: Int) {
        mPickerYear.yearStart = start
    }

    override fun getYearEnd(): Int {
        return mPickerYear.yearEnd
    }

    override fun setYearEnd(end: Int) {
        mPickerYear.yearEnd = end
    }

    override fun getSelectedYear(): Int {
        return mPickerYear.selectedYear
    }

    override fun setSelectedYear(year: Int) {
        mYear = year
        mPickerYear.selectedYear = year
        mPickerDay.year = year
    }

    override fun getCurrentYear(): Int {
        return mPickerYear.currentYear
    }

    override fun getSelectedMonth(): Int {
        return mPickerMonth.selectedMonth
    }

    override fun setSelectedMonth(month: Int) {
        mMonth = month
        mPickerMonth.selectedMonth = month
        mPickerDay.month = month
    }

    override fun getCurrentMonth(): Int {
        return mPickerMonth.currentMonth
    }

    override fun getSelectedDay(): Int {
        return mPickerDay.selectedDay
    }

    override fun setSelectedDay(day: Int) {
        mDay = day
        mPickerDay.selectedDay = day
    }

    override fun getCurrentDay(): Int {
        return mPickerDay.currentDay
    }

    override fun setYearAndMonth(year: Int, month: Int) {
        mYear = year
        mMonth = month
        mPickerYear.selectedYear = year
        mPickerMonth.selectedMonth = month
        mPickerDay.setYearAndMonth(year, month)
    }

    override fun getYear(): Int {
        return selectedYear
    }

    override fun setYear(year: Int) {
        mYear = year
        mPickerYear.selectedYear = year
        mPickerDay.year = year
    }

    override fun getMonth(): Int {
        return selectedMonth
    }

    override fun setMonth(month: Int) {
        mMonth = month
        mPickerMonth.selectedMonth = month
        mPickerDay.month = month
    }

    interface SWOnDateSelectedListener {
        fun onDateSelected(picker: KFWheelDatePicker?, date: Date?)
    }

    override var startDate: LocalDate?
        get() {
            return mStartDate
        }
        set(date) {
            mStartDate = date
            mStartDate?.let {
                mPickerYear.yearStart = it.year
            }
        }

    init {
        LayoutInflater.from(context).inflate(com.thejuki.kformmaster.R.layout.view_kfwheel_date_picker, this)
        mPickerYear =
                findViewById<View>(R.id.wheel_date_picker_year) as WheelYearPicker
        mPickerMonth =
                findViewById<View>(R.id.wheel_date_picker_month) as KFWheelMonthPicker
        mPickerDay = findViewById<View>(R.id.wheel_date_picker_day) as KFWheelDayPicker
        mPickerYear.setOnItemSelectedListener(this)
        mPickerMonth.setOnItemSelectedListener(this)
        mPickerDay.setOnItemSelectedListener(this)
        mPickerYear.yearStart = LocalDate.now().minusYears(100).year
        mPickerYear.yearEnd = LocalDate.now().plusYears(100).year
        mPickerYear.selectedYear = LocalDate.now().year
        mPickerMonth.selectedMonth = LocalDate.now().monthValue
        mPickerDay.selectedDay = LocalDate.now().dayOfMonth
        mPickerDay.setIndicator(true)
        mPickerDay.indicatorColor = ResourcesCompat.getColor(this.context.resources, com.thejuki.kformmaster.R.color.colorFormMasterElementFocusedDateTime, null)
        mPickerMonth.setIndicator(true)
        mPickerMonth.indicatorColor = ResourcesCompat.getColor(this.context.resources, com.thejuki.kformmaster.R.color.colorFormMasterElementFocusedDateTime, null)
        mPickerYear.setIndicator(true)
        mPickerYear.indicatorColor = ResourcesCompat.getColor(this.context.resources, com.thejuki.kformmaster.R.color.colorFormMasterElementFocusedDateTime, null)
        setMaximumWidthTextYear()
        mPickerMonth.maximumWidthText = "00"
        mPickerDay.maximumWidthText = "00"
        mYear = mPickerYear.currentYear
        mMonth = mPickerMonth.currentMonth
        mDay = mPickerDay.currentDay
    }
}