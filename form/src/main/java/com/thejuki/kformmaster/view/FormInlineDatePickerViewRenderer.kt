package com.thejuki.kformmaster.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.extensions.IFormInlinePicker
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.helper.FormViewFinder
import com.thejuki.kformmaster.model.FormInlineDatePickerElement
import com.thejuki.kformmaster.widget.ClearableEditText
import com.thejuki.kformmaster.widget.datepicker.KFWheelDateTimePicker
import net.cachapa.expandablelayout.ExpandableLayout
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import java.util.*
import kotlin.concurrent.schedule

class FormInlineDatePickerViewRenderer(private val formBuilder: FormBuildHelper, @LayoutRes private val layoutID: Int?) : BaseFormViewRenderer() {

    private val handler = Handler(Looper.myLooper()!!)

    fun checkFinalDate(model: FormInlineDatePickerElement, editView: ClearableEditText) {
        if (model.pickerType == FormInlineDatePickerElement.PickerType.Secondary) {
            if (model.linkedPicker != null && model.linkedPicker?.value != null) {
                model.value?.let {
                    if (it < model.linkedPicker?.value) {
                        editView.setTextColor(ResourcesCompat.getColor(editView.context.resources, R.color.colorFormMasterElementErrorTitle, null))
                        editView.paintFlags = editView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        model.dateError = true
                    } else {
                        editView.setTextColor(ResourcesCompat.getColor(editView.context.resources, R.color.colorFormMasterElementTextValue, null))
                        editView.paintFlags = editView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                        model.dateError = false
                    }
                }
            }
        }
    }

    private fun dismissKeyboard(context: Context) {
        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        try {
            inputMethodManager.hideSoftInputFromWindow((context as Activity).currentFocus?.windowToken, 0)
        } catch (e: KotlinNullPointerException) {
            // handler
        }

        (context as Activity).currentFocus?.clearFocus()
    }

    @SuppressLint("ClickableViewAccessibility")
    var viewRenderer = ViewRenderer(
            layoutID
                    ?: R.layout.form_element_datetimepicker,
            FormInlineDatePickerElement::class.java) { model, finder: FormViewFinder, _ ->
        val formElementMainLayout = finder.find(R.id.formElementMainLayout) as LinearLayout
        val pickerWrapper = finder.find(R.id.pickerWrapper) as ExpandableLayout
        val textViewTitle = finder.find(R.id.formElementTitle) as AppCompatTextView
        val dividerView = finder.find(R.id.formElementDivider) as View
        val textViewError = finder.find(R.id.formElementError) as AppCompatTextView
        val itemView = finder.getRootView() as View
        val pickerView = finder.find(R.id.formElementPicker) as KFWheelDateTimePicker
        val editTextValue = finder.find(R.id.formElementValue) as ClearableEditText
        baseSetup(model, dividerView, textViewTitle, textViewError, itemView, formElementMainLayout, editTextValue)

        pickerView.dateTimeFormatter = model.dateTimePickerFormatter
        pickerView.startDate = model.startDate

        model.setDelegate(object : IFormInlinePicker {
            override fun setAllDayPicker() {
                if (model.isAllDay()) {
                    pickerView.wheelDatePicker?.visibility = View.VISIBLE
                    pickerView.wheelExtendedDatePicker?.visibility = View.GONE
                    pickerView.wheelHourPicker?.visibility = View.GONE
                    pickerView.wheelMinutePicker?.visibility = View.GONE
                } else {
                    pickerView.wheelDatePicker?.visibility = View.GONE
                    if (model.value != null) {
                        val cal = Calendar.getInstance()

                        var tempValue = LocalDateTime.of(model.value?.toLocalDate(), LocalTime.of(cal[Calendar.HOUR_OF_DAY], cal[Calendar.MINUTE]))

                        if (model.pickerType == FormInlineDatePickerElement.PickerType.Secondary)
                            tempValue = tempValue.plusHours(1)

                        model.value = tempValue

                        model.value?.let {
                            pickerView.setDateTime(it)
                        }
                    }
                    pickerView.wheelExtendedDatePicker?.visibility = View.VISIBLE
                    pickerView.wheelHourPicker?.visibility = View.VISIBLE
                    pickerView.wheelMinutePicker?.visibility = View.VISIBLE
                }
            }

            override fun setDateTime(dateTime: LocalDateTime) {
                model.value = dateTime
                pickerView.setDateTime(dateTime)

                if (model.value != null) {
                    checkFinalDate(model, editTextValue)
                }
            }

            override fun toggle() {
                pickerWrapper.toggle()
            }

            override fun isExpanded(): Boolean {
                return pickerWrapper.isExpanded
            }

            override fun collapse() {
                pickerWrapper.collapse()
            }

            override fun expand() {
                pickerWrapper.expand()
            }

            override fun setStartDate(date: LocalDate?) {
                pickerView.startDate = date
            }
        })

        if (model.value != null) {
            Timer("setTime", false).schedule(500) {
                handler.post {
                    model.value?.let {
                        pickerView.setDateTime(it)
                    }
                }
            }
        }

        model.valueObservers.add { newValue, _ ->
            if (newValue != null) {
                pickerView.setDateTime(newValue)
            }
        }

        editTextValue.hint = model.hint ?: ""
        editTextValue.alwaysShowClear = true

        editTextValue.setRawInputType(InputType.TYPE_NULL)
        editTextValue.isFocusable = false

        pickerView.setOnDateSelectedListener(object : KFWheelDateTimePicker.SWOnDateSelectedListener {
            override fun onDateSelected(picker: KFWheelDateTimePicker?, date: LocalDateTime?) {
                model.value = date
                if (model.value != null) {
                    if (model.pickerType == FormInlineDatePickerElement.PickerType.Primary) {
                        model.value?.plusHours(1)?.let { model.linkedPicker?.setDateTime(it) }
                    }

                    checkFinalDate(model, editTextValue)
                }

                if (model.isAllDay()) {
                    pickerWrapper.collapse()
                }
            }
        })

        fun toggleElement(context: Context) {
            dismissKeyboard(context)
            if (model.isAllDay()) {
                pickerView.wheelDatePicker?.visibility = View.VISIBLE
                pickerView.wheelExtendedDatePicker?.visibility = View.GONE
                pickerView.wheelHourPicker?.visibility = View.GONE
                pickerView.wheelMinutePicker?.visibility = View.GONE
            } else {
                pickerView.wheelDatePicker?.visibility = View.GONE
                pickerView.wheelExtendedDatePicker?.visibility = View.VISIBLE
                pickerView.wheelHourPicker?.visibility = View.VISIBLE
                pickerView.wheelMinutePicker?.visibility = View.VISIBLE
            }

            pickerWrapper.toggle()
            if (pickerWrapper.isExpanded) {
                formBuilder.elements.forEach { element ->
                    if (element is FormInlineDatePickerElement && element.tag != model.tag) {
                        element.collapse()
                    }
                }
            }
        }

        formElementMainLayout.setOnClickListener {
            toggleElement(itemView.context)
        }

        editTextValue.setOnClickListener {
            toggleElement(itemView.context)
        }

        fun elementTouch(context: Context, event: MotionEvent) {
            if (event.action == MotionEvent.ACTION_DOWN) {
                formElementMainLayout.setBackgroundColor(ResourcesCompat.getColor(context.resources, R.color.colorFormMasterElementSelectedBackground, null))
            }
            if (event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_UP) {
                formElementMainLayout.setBackgroundColor(ResourcesCompat.getColor(context.resources, R.color.colorFormMasterElementBackground, null))
            }
        }

        formElementMainLayout.setOnTouchListener { _, event ->
            elementTouch(itemView.context, event)
            false
        }

        editTextValue.setOnTouchListener { _, event ->
            elementTouch(itemView.context, event)
            false
        }

        model.displayNewValue()
    }
}