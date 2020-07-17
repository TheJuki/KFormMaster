package com.thejuki.kformmaster.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.thejuki.kformmaster.widget.datepicker.KFWheelDateTimePicker
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.extensions.IFormInlinePicker
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormInlineDatePickerElement
import com.thejuki.kformmaster.model.PickerType
import com.thejuki.kformmaster.state.FormInlineDatePickerViewState
import net.cachapa.expandablelayout.ExpandableLayout
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*
import kotlin.concurrent.schedule

class FormInlineDatePickerBinder(private val context: Context, private val formBuilder: FormBuildHelper, @LayoutRes private val layoutID: Int?) : BaseFormViewBinder() {

    val handler = Handler()

    fun formatPickerDate(model : FormInlineDatePickerElement) : String {
        var dateTimeMask = "E d 'de' MMM 'de' YYYY HH:mm"

        if (model.pickerType == PickerType.Secondary && model.linkedPicker != null) {
            if (model.value!!.toLocalDate() == model.linkedPicker!!.value!!.toLocalDate())
                dateTimeMask = "HH:mm"
        }

        if (model.isAllDay())
            dateTimeMask = "E d 'de' MMM 'de' YYYY"

        val formatter: DateTimeFormatter =
                DateTimeFormatter.ofPattern(dateTimeMask)

        return model.value!!.format(formatter)
    }

    fun checkFinalDate(model: FormInlineDatePickerElement, edit: AppCompatTextView){
        if (model.pickerType == PickerType.Secondary){
            if (model.linkedPicker != null) {
                if (model.value!! < model.linkedPicker!!.value){
                    edit.setTextColor(ResourcesCompat.getColor(context.resources, R.color.colorFormMasterElementErrorTitle, null))
                    edit.paintFlags = edit.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    edit.setTextColor(ResourcesCompat.getColor(context.resources, R.color.colorFormMasterElementTextValue, null))
                    edit.paintFlags = edit.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
        }
    }

    fun dismissKeyboard(context: Context) {
        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        try {
            inputMethodManager.hideSoftInputFromWindow((context as Activity).currentFocus!!.windowToken, 0)
        }
        catch (e: KotlinNullPointerException) {
            // handler
        }

        (context as Activity).currentFocus?.clearFocus()
    }

    @SuppressLint("ClickableViewAccessibility")
    var viewBinder = ViewBinder(
            layoutID
                    ?: R.layout.form_element_datetimepicker,
            FormInlineDatePickerElement::class.java, { model, finder, _ ->
        val formElementMainLayout = finder.find(R.id.formElementMainLayout) as LinearLayout
        val pickerWrapper = finder.find(R.id.pickerWrapper) as ExpandableLayout
        val textViewTitle = finder.find(R.id.formElementTitle) as AppCompatTextView
        val dividerView = finder.find(R.id.formElementDivider) as View
        val textViewError = finder.find(R.id.formElementError) as AppCompatTextView
        val itemView = finder.getRootView() as View
        val pickerView = finder.find(R.id.formElementPicker) as KFWheelDateTimePicker
        val imageView = finder.find(R.id.imageView) as ImageView
        val editTextValue = finder.find(R.id.formElementValue) as AppCompatTextView
        baseSetup(model, dividerView, textViewTitle, textViewError, itemView, formElementMainLayout, editTextValue)

        if (!model.enabled){
            imageView.visibility = View.GONE
            formElementMainLayout.isEnabled = false
            editTextValue.setTextColor(ResourcesCompat.getColor(context.resources, R.color.colorFormMasterElementTextDisabled, null))
        } else {
            imageView.visibility = View.VISIBLE
            formElementMainLayout.isEnabled = true
            editTextValue.setTextColor(ResourcesCompat.getColor(context.resources, R.color.colorFormMasterElementTextView, null))
        }

        model.setDelegate(object: IFormInlinePicker {
            override fun setAllDayPicker() {
                //pickerWrapper.collapse()
                if (model.isAllDay()) {
                    pickerView.wheelDatePicker?.visibility = View.VISIBLE
                    pickerView.wheelExtendedDatePicker?.visibility = View.GONE
                    pickerView.wheelHourPicker?.visibility = View.GONE
                    pickerView.wheelMinutePicker?.visibility = View.GONE
                } else {
                    pickerView.wheelDatePicker?.visibility = View.GONE
                    if (model.value != null) {
                        val cal = Calendar.getInstance()

                        var tempValue = LocalDateTime.of(model.value!!.toLocalDate(), LocalTime.of(cal[Calendar.HOUR_OF_DAY], cal[Calendar.MINUTE]))

                        if (model.pickerType == PickerType.Secondary)
                            tempValue = tempValue.plusHours(1)

                        model.value = tempValue

                        pickerView.setDateTime(model.value!!)
                    }
                    pickerView.wheelExtendedDatePicker?.visibility = View.VISIBLE
                    pickerView.wheelHourPicker?.visibility = View.VISIBLE
                    pickerView.wheelMinutePicker?.visibility = View.VISIBLE
                }

                if (model.value != null) {
                    editTextValue.text = formatPickerDate(model)
                }
            }

            override fun setDateTime(dateTime : LocalDateTime) {
                model.value = dateTime
                pickerView.setDateTime(dateTime)

                if (model.value != null) {
                    editTextValue.text = formatPickerDate(model)
                    checkFinalDate(model, editTextValue)
                }
            }

            override fun toggle() {
                pickerWrapper.toggle()
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
            editTextValue.text = formatPickerDate(model)

            Timer("setTime", false).schedule(500) {
                handler.post {
                    pickerView.setDateTime(model.value!!)
                }
            }
        }

        model.valueObservers.add{ newValue, _ ->
            if (newValue != null)
                pickerView.setDateTime(newValue)
        }

        editTextValue.hint = model.hint ?: ""

        pickerView.setOnDateSelectedListener(object : KFWheelDateTimePicker.SWOnDateSelectedListener {
            override fun onDateSelected(picker: KFWheelDateTimePicker?, date: LocalDateTime?) {
                model.value = date
                if (model.value != null) {
                    editTextValue.text = formatPickerDate(model)

                    if (model.pickerType == PickerType.Primary){
                        model.linkedPicker?.setDateTime(model.value!!.plusHours(1))
                    }

                    checkFinalDate(model, editTextValue)
                }

                if (model.isAllDay())
                    pickerWrapper.collapse()
            }
        })

        model.editView = editTextValue

        fun toggleElement(){
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
            if (pickerWrapper.isExpanded){
                formBuilder.elements.forEach { element ->
                    if (element is FormInlineDatePickerElement && element.tag != model.tag){
                        element.collapse()
                    }
                }
            }
        }

        formElementMainLayout.setOnClickListener {
            toggleElement()
        }

        editTextValue.setOnClickListener {
            toggleElement()
        }

        fun elementTouch(event: MotionEvent){
            if (event.action == MotionEvent.ACTION_DOWN) {
                formElementMainLayout.setBackgroundColor(ResourcesCompat.getColor(this.context.resources, R.color.colorFormMasterElementSelectedBackground, null))
            }
            if (event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_UP) {
                formElementMainLayout.setBackgroundColor(ResourcesCompat.getColor(this.context.resources, R.color.colorFormMasterElementBackground, null))
            }
        }

        formElementMainLayout.setOnTouchListener { v, event ->
            elementTouch(event)
            false
        }

        editTextValue.setOnTouchListener { v, event ->
            elementTouch(event)
            false
        }

    }, object : ViewStateProvider<FormInlineDatePickerElement, ViewHolder> {
        override fun createViewStateID(model: FormInlineDatePickerElement): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormInlineDatePickerViewState(holder)
        }
    })

}