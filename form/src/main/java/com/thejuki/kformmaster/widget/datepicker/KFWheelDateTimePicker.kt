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
import com.aigestudio.wheelpicker.WheelPicker
import com.aigestudio.wheelpicker.WheelPicker.OnWheelChangeListener
import com.thejuki.kformmaster.extensions.toLocalDateTime
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.text.ParseException
import java.util.*

/**
 * Wheel Date Time Picker
 *
 * @author **soareseneves** ([GitHub](https://github.com/soareseneves))
 * @version 1.0
 */
class KFWheelDateTimePicker @JvmOverloads constructor(
        context: Context?,
        attrs: AttributeSet? = null
) :
        LinearLayout(context, attrs), WheelPicker.OnItemSelectedListener,
        KFWheelDatePicker.SWOnDateSelectedListener,
        IDebug, IWheelPicker,
        IKFWheelDateTimePicker, IKFWheelExtendedDatePicker {
    private val mPickerDate: KFWheelDatePicker
    private val mPickerExtendedDate: KFWheelExtendedDatePicker
    private val mPickerHour: KFWheelHourPicker
    private val mPickerMinute: KFWheelMinutePicker
    private var mListener: SWOnDateSelectedListener? = null
    private var mDate: LocalDate
    private var mHour: Int
    private var mMinute: Int
    private var mStartDate: LocalDate? = null
    var dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE

    override fun onItemSelected(
            picker: WheelPicker,
            data: Any,
            position: Int
    ) {
        if (picker.id == com.thejuki.kformmaster.R.id.wheel_hour_picker) {
            if (data is Int)
                mHour = data
            else
                mHour = (data as String).toInt()
        } else if (picker.id == com.thejuki.kformmaster.R.id.wheel_minute_picker) {
            if (data is Int)
                mMinute = data
            else
                mMinute = (data as String).toInt()
        } else if (picker.id == com.thejuki.kformmaster.R.id.wheel_fulldate_picker) {
            mDate = mPickerExtendedDate.dates[position] as LocalDate
            mPickerHour.selectedDate = mDate
            mPickerMinute.selectedDate = mDate
            mHour = mPickerHour.selectedHour
            mMinute = mPickerMinute.selectedMinute
        }
        mPickerDate.selectedYear = mDate.year
        mPickerDate.selectedMonth = mDate.monthValue
        mPickerDate.selectedDay = mDate.dayOfMonth
        mPickerHour.selectedHour = mHour
        mPickerMinute.selectedMinute = mMinute

        val date = mDate.atTime(mPickerHour.selectedHour, mPickerMinute.selectedMinute)

        mListener?.onDateSelected(this, date)
    }

    override fun onDateSelected(picker: KFWheelDatePicker?, date: Date?) {
        mListener?.onDateSelected(this, date?.toLocalDateTime())
    }

    override fun setDebug(isDebug: Boolean) {
        mPickerDate.setDebug(isDebug)
        mPickerExtendedDate.setDebug(isDebug)
        mPickerHour.setDebug(isDebug)
        mPickerMinute.setDebug(isDebug)
    }

    override fun getVisibleItemCount(): Int {
        if (mPickerExtendedDate.visibleItemCount == mPickerHour.visibleItemCount &&
                mPickerHour.visibleItemCount == mPickerMinute.visibleItemCount
        ) return mPickerExtendedDate.visibleItemCount
        throw ArithmeticException(
                "Can not get visible item count correctly from" +
                        "WheelDatePicker!"
        )
    }

    override fun setVisibleItemCount(count: Int) {
        mPickerExtendedDate.visibleItemCount = count
    }

    override fun isCyclic(): Boolean {
        return mPickerExtendedDate.isCyclic && mPickerHour.isCyclic && mPickerMinute.isCyclic
    }

    override fun setCyclic(isCyclic: Boolean) {
        mPickerExtendedDate.isCyclic = isCyclic
        mPickerHour.isCyclic = isCyclic
        mPickerMinute.isCyclic = isCyclic
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
        if (mPickerExtendedDate.selectedItemTextColor == mPickerHour.selectedItemTextColor &&
                mPickerHour.selectedItemTextColor == mPickerMinute.selectedItemTextColor)
            return mPickerExtendedDate.selectedItemTextColor
        throw RuntimeException("Can not get color of selected item text correctly from" +
                "WheelDatePicker!")
    }

    override fun setSelectedItemTextColor(color: Int) {
        mPickerExtendedDate.selectedItemTextColor = color
        mPickerHour.selectedItemTextColor = color
        mPickerMinute.selectedItemTextColor = color
    }

    override fun getItemTextColor(): Int {
        if (mPickerExtendedDate.itemTextColor == mPickerHour.itemTextColor &&
                mPickerHour.itemTextColor == mPickerMinute.itemTextColor)
            return mPickerExtendedDate.itemTextColor
        throw RuntimeException("Can not get color of item text correctly from" +
                "WheelDatePicker!")
    }

    override fun setItemTextColor(color: Int) {
        mPickerExtendedDate.itemTextColor = color
        mPickerHour.itemTextColor = color
        mPickerMinute.itemTextColor = color
    }

    override fun getItemTextSize(): Int {
        if (mPickerExtendedDate.itemTextSize == mPickerHour.itemTextSize &&
                mPickerHour.itemTextSize == mPickerMinute.itemTextSize)
            return mPickerExtendedDate.itemTextSize
        throw RuntimeException("Can not get size of item text correctly from" +
                "WheelDatePicker!");
    }

    override fun setItemTextSize(size: Int) {
        mPickerExtendedDate.itemTextSize = size
        mPickerHour.itemTextSize = size
        mPickerMinute.itemTextSize = size
    }

    override fun getItemSpace(): Int {
        if (mPickerExtendedDate.itemSpace == mPickerHour.itemSpace &&
                mPickerHour.itemSpace == mPickerMinute.itemSpace)
            return mPickerExtendedDate.itemSpace
        throw RuntimeException("Can not get item space correctly from WheelDatePicker!")
    }

    override fun setItemSpace(space: Int) {
        mPickerExtendedDate.itemSpace = space
        mPickerHour.itemSpace = space
        mPickerMinute.itemSpace = space
    }

    override fun setIndicator(hasIndicator: Boolean) {
        mPickerExtendedDate.setIndicator(hasIndicator)
        mPickerHour.setIndicator(hasIndicator)
        mPickerMinute.setIndicator(hasIndicator)
    }

    override fun hasIndicator(): Boolean {
        return mPickerExtendedDate.hasIndicator() && mPickerHour.hasIndicator() &&
                mPickerMinute.hasIndicator()
    }

    override fun getIndicatorSize(): Int {
        if (mPickerExtendedDate.indicatorSize == mPickerHour.indicatorSize &&
                mPickerHour.indicatorSize == mPickerMinute.indicatorSize)
            return mPickerExtendedDate.indicatorSize
        throw RuntimeException("Can not get indicator size correctly from WheelDatePicker!")
    }

    override fun setIndicatorSize(size: Int) {
        mPickerExtendedDate.indicatorSize = size
        mPickerHour.indicatorSize = size
        mPickerMinute.indicatorSize = size
    }

    override fun getIndicatorColor(): Int {
        if (mPickerExtendedDate.curtainColor == mPickerHour.curtainColor &&
                mPickerHour.curtainColor == mPickerMinute.curtainColor)
            return mPickerExtendedDate.curtainColor
        throw RuntimeException("Can not get indicator color correctly from WheelDatePicker!")
    }

    override fun setIndicatorColor(color: Int) {
        mPickerExtendedDate.indicatorColor = color
        mPickerHour.indicatorColor = color
        mPickerMinute.indicatorColor = color
    }

    override fun setCurtain(hasCurtain: Boolean) {
        mPickerExtendedDate.setCurtain(hasCurtain)
        mPickerHour.setCurtain(hasCurtain)
        mPickerMinute.setCurtain(hasCurtain)
    }

    override fun hasCurtain(): Boolean {
        return mPickerExtendedDate.hasCurtain() && mPickerHour.hasCurtain() &&
                mPickerMinute.hasCurtain()
    }

    override fun getCurtainColor(): Int {
        if (mPickerExtendedDate.curtainColor == mPickerHour.curtainColor &&
                mPickerHour.curtainColor == mPickerMinute.curtainColor)
            return mPickerExtendedDate.curtainColor
        throw RuntimeException("Can not get curtain color correctly from WheelDatePicker!")
    }

    override fun setCurtainColor(color: Int) {
        mPickerExtendedDate.curtainColor = color
        mPickerHour.curtainColor = color
        mPickerMinute.curtainColor = color
    }

    override fun setAtmospheric(hasAtmospheric: Boolean) {
        mPickerExtendedDate.setAtmospheric(hasAtmospheric)
        mPickerHour.setAtmospheric(hasAtmospheric)
        mPickerMinute.setAtmospheric(hasAtmospheric)
    }

    override fun hasAtmospheric(): Boolean {
        return mPickerExtendedDate.hasAtmospheric() && mPickerHour.hasAtmospheric() &&
                mPickerMinute.hasAtmospheric()
    }

    override fun isCurved(): Boolean {
        return mPickerExtendedDate.isCurved && mPickerHour.isCurved && mPickerMinute.isCurved
    }

    override fun setCurved(isCurved: Boolean) {
        mPickerExtendedDate.isCurved = isCurved
        mPickerHour.isCurved = isCurved
        mPickerMinute.isCurved = isCurved
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
        if (mPickerExtendedDate.typeface == mPickerHour.typeface &&
                mPickerHour.typeface == mPickerMinute.typeface)
            return mPickerExtendedDate.typeface
        throw RuntimeException("Can not get typeface correctly from WheelDatePicker!")
    }

    override fun setTypeface(tf: Typeface) {
        mPickerExtendedDate.typeface = tf
        mPickerHour.typeface = tf
        mPickerMinute.typeface = tf
    }

    override fun setOnDateSelectedListener(listener: SWOnDateSelectedListener?) {
        mListener = listener
    }

    override val currentDate: LocalDateTime?
        get() {

            val date = mDate.atTime(mHour, mMinute)

            try {
                return date
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return null
        }

    override var itemAlignDate: Int
        get() {
            return mPickerExtendedDate.itemAlign
        }
        set(align) {
            mPickerExtendedDate.itemAlign = align
        }

    override var itemAlignHour: Int
        get() {
            return mPickerHour.itemAlign
        }
        set(align) {
            mPickerHour.itemAlign = align
        }

    override var itemAlignMinute: Int
        get() {
            return mPickerMinute.itemAlign
        }
        set(align) {
            mPickerMinute.itemAlign = align
        }

    override val wheelDatePicker: KFWheelDatePicker?
        get() {
            return mPickerDate
        }

    override val wheelExtendedDatePicker: KFWheelExtendedDatePicker?
        get() {
            return mPickerExtendedDate
        }

    override val wheelHourPicker: KFWheelHourPicker?
        get() {
            return mPickerHour
        }

    override val wheelMinutePicker: KFWheelMinutePicker?
        get() {
            return mPickerMinute
        }

    override var selectedDate: LocalDate
        get() {
            return mPickerExtendedDate.selectedDate
        }
        set(date) {
            mDate = date
            mPickerExtendedDate.selectedDate = date
        }

    override var startDate: LocalDate?
        get() {
            return mPickerExtendedDate.startDate
        }
        set(date) {
            mStartDate = date
            mPickerDate.startDate = date
            mPickerExtendedDate.dateTimeFormatter = dateTimeFormatter
            mPickerExtendedDate.startDate = date
            if (date != null) {
                mPickerHour.allHours = false
                mPickerMinute.allMinutes = false
            }
        }

    interface SWOnDateSelectedListener {
        fun onDateSelected(picker: KFWheelDateTimePicker?, date: LocalDateTime?)
    }

    init {
        val currentDate = LocalDate.now()
        LayoutInflater.from(context).inflate(com.thejuki.kformmaster.R.layout.view_kfwheel_datetime_picker, this)
        mPickerDate = findViewById<View>(com.thejuki.kformmaster.R.id.wheel_date_picker) as KFWheelDatePicker
        mPickerDate.setOnDateSelectedListener(this)
        mPickerDate.selectedYear = currentDate.year
        mPickerDate.selectedMonth = currentDate.monthValue
        mPickerDate.selectedDay = currentDate.dayOfMonth
        mPickerDate.setIndicator(true)
        mPickerDate.indicatorColor = ResourcesCompat.getColor(this.context.resources, com.thejuki.kformmaster.R.color.colorFormMasterElementFocusedDateTime, null)

        mPickerExtendedDate = findViewById<View>(com.thejuki.kformmaster.R.id.wheel_fulldate_picker) as KFWheelExtendedDatePicker
        mPickerExtendedDate.setOnItemSelectedListener(this)
        mPickerExtendedDate.selectedDate = currentDate
        mPickerExtendedDate.setIndicator(true)
        mPickerExtendedDate.indicatorColor = ResourcesCompat.getColor(this.context.resources, com.thejuki.kformmaster.R.color.colorFormMasterElementFocusedDateTime, null)
        mPickerExtendedDate.maximumWidthText = " dom 26 de jan "

        mPickerHour = findViewById<View>(com.thejuki.kformmaster.R.id.wheel_hour_picker) as KFWheelHourPicker
        mPickerHour.isCyclic = true
        mPickerHour.setOnItemSelectedListener(this)

        val cal = Calendar.getInstance()

        mPickerHour.selectedHour = cal[Calendar.HOUR_OF_DAY]
        mPickerHour.selectedDate = currentDate
        mPickerHour.setIndicator(true)
        mPickerHour.indicatorColor = ResourcesCompat.getColor(this.context.resources, com.thejuki.kformmaster.R.color.colorFormMasterElementFocusedDateTime, null)
        mPickerHour.maximumWidthText = "00"

        mPickerMinute = findViewById<View>(com.thejuki.kformmaster.R.id.wheel_minute_picker) as KFWheelMinutePicker
        mPickerMinute.isCyclic = true
        mPickerMinute.setOnItemSelectedListener(this)
        mPickerMinute.selectedMinute = cal[Calendar.MINUTE]
        mPickerMinute.selectedDate = currentDate
        mPickerMinute.setIndicator(true)
        mPickerMinute.indicatorColor = ResourcesCompat.getColor(this.context.resources, com.thejuki.kformmaster.R.color.colorFormMasterElementFocusedDateTime, null)
        mPickerMinute.maximumWidthText = "00"

        mDate = mPickerExtendedDate.selectedDate
        mHour = mPickerHour.selectedHour
        mMinute = mPickerMinute.selectedMinute
    }

    fun setDateTime(dateTime: LocalDateTime) {
        mPickerDate.selectedYear = dateTime.year
        mPickerDate.selectedMonth = dateTime.monthValue
        mPickerDate.selectedDay = dateTime.dayOfMonth
        mPickerExtendedDate.selectedDate = dateTime.toLocalDate()
        mPickerHour.selectedDate = dateTime.toLocalDate()
        mPickerMinute.selectedDate = dateTime.toLocalDate()
        mPickerHour.selectedHour = dateTime.hour
        mPickerMinute.selectedMinute = dateTime.minute

        mDate = dateTime.toLocalDate()
        mHour = dateTime.hour
        mMinute = dateTime.minute
    }

}