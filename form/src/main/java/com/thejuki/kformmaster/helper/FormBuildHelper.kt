package com.thejuki.kformmaster.helper

import android.content.Context
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener
import com.thejuki.kformmaster.model.BaseFormElement
import com.thejuki.kformmaster.renderer.*
import java.util.*

/**
 * Form Build Helper
 *
 * Wrapper class around the adapter to assist in building the form
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormBuildHelper {

    private var mFormAdapter: RendererRecyclerViewAdapter? = null

    private var mElements: ArrayList<BaseFormElement<*>>? = null

    private var mListener: OnFormElementValueChangedListener? = null

    /**
     * return true if the form is valid
     *
     * @return
     */
    val isValidForm: Boolean
        get() {
            return (0 until this.mFormAdapter!!.itemCount)
                    .map { this.getElementAtIndex(it) }
                    .none { !it!!.isHeader and it.isRequired() and it.mValue.toString().trim().isEmpty() }
        }

    /**
     * constructor without listener callback for changed values
     *
     * @param context
     */
    constructor(context: Context) {
        initializeFormBuildHelper(context, null)
    }

    /**
     * constructor with listener callback for changed values
     *
     * @param context
     */
    constructor(context: Context, listener: OnFormElementValueChangedListener) {
        initializeFormBuildHelper(context, listener)
    }

    /**
     * private method for initializing form build helper
     *
     * @param context
     * @param listener
     */
    private fun initializeFormBuildHelper(context: Context, listener: OnFormElementValueChangedListener?) {

        // initialize form adapter
        this.mElements = ArrayList()

        this.mFormAdapter = RendererRecyclerViewAdapter()
        this.mFormAdapter!!.registerRenderer(FormHeaderRenderer(BaseFormElement.TYPE_HEADER, context))

        this.mFormAdapter!!.registerRenderer(FormEditTextRenderer(BaseFormElement.TYPE_EDITTEXT_TEXT_SINGLELINE, context, this))
        this.mFormAdapter!!.registerRenderer(FormEditTextRenderer(BaseFormElement.TYPE_EDITTEXT_TEXT_MULTILINE, context, this))
        this.mFormAdapter!!.registerRenderer(FormEditTextRenderer(BaseFormElement.TYPE_EDITTEXT_NUMBER, context, this))
        this.mFormAdapter!!.registerRenderer(FormEditTextRenderer(BaseFormElement.TYPE_EDITTEXT_EMAIL, context, this))
        this.mFormAdapter!!.registerRenderer(FormEditTextRenderer(BaseFormElement.TYPE_EDITTEXT_PHONE, context, this))
        this.mFormAdapter!!.registerRenderer(FormEditTextRenderer(BaseFormElement.TYPE_EDITTEXT_PASSWORD, context, this))

        this.mFormAdapter!!.registerRenderer(FormAutoCompleteRenderer(BaseFormElement.TYPE_EDITTEXT_AUTOCOMPLETE, context, this))
        this.mFormAdapter!!.registerRenderer(FormTokenAutoCompleteRenderer(BaseFormElement.TYPE_EDITTEXT_TOKEN_AUTOCOMPLETE, context, this))

        this.mFormAdapter!!.registerRenderer(FormButtonRenderer(BaseFormElement.TYPE_BUTTON, context, this))
        this.mFormAdapter!!.registerRenderer(FormSwitchRenderer(BaseFormElement.TYPE_SWITCH, context, this))
        this.mFormAdapter!!.registerRenderer(FormSliderRenderer(BaseFormElement.TYPE_SLIDER, context, this))

        this.mFormAdapter!!.registerRenderer(FormPickerDateRenderer(BaseFormElement.TYPE_PICKER_DATE, context, this))
        this.mFormAdapter!!.registerRenderer(FormPickerTimeRenderer(BaseFormElement.TYPE_PICKER_TIME, context, this))
        this.mFormAdapter!!.registerRenderer(FormPickerDateTimeRenderer(BaseFormElement.TYPE_PICKER_DATE_TIME, context, this))
        this.mFormAdapter!!.registerRenderer(FormPickerMultiCheckBoxRenderer(BaseFormElement.TYPE_PICKER_MULTI_CHECKBOX, context, this))
        this.mFormAdapter!!.registerRenderer(FormPickerDropDownRenderer(BaseFormElement.TYPE_PICKER_DROP_DOWN, context, this))

        this.mFormAdapter!!.registerRenderer(FormTextViewRenderer(BaseFormElement.TYPE_TEXTVIEW, context))

        this.mListener = listener
    }

    fun registerRenderer(r: ViewRenderer<*, *>) {
        this.mFormAdapter!!.registerRenderer(r)
    }

    fun attachRecyclerView(context: Context, recyclerView: RecyclerView?) {
        if (recyclerView == null) {
            return
        }

        // set up the recyclerview with adapter
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.stackFromEnd = false

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = mFormAdapter
        recyclerView.itemAnimator = DefaultItemAnimator()
    }

    /**
     * add list of form elements to be shown
     *
     * @param formElements
     */
    fun addFormElements(formElements: List<BaseFormElement<*>>) {
        this.mElements!!.addAll(formElements)
        this.mFormAdapter!!.setItems(this.mElements!!)
    }

    /**
     * redraws the view
     */
    fun refreshView() {
        this.mFormAdapter!!.notifyDataSetChanged()
    }

    /**
     * get value of specific form element
     *
     * @param tag
     * @return
     */
    fun getFormElement(tag: Int): BaseFormElement<*>? {

        return this.mElements!!.firstOrNull { !it.isHeader && it.mTag == tag }
    }

    fun getElementAtIndex(index: Int): BaseFormElement<*>? {
        return this.mElements!![index]

    }

    fun onValueChanged(element: BaseFormElement<*>) {
        mListener?.onValueChanged(element)
    }
}
