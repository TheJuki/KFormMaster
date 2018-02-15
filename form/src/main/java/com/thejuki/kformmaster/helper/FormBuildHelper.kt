package com.thejuki.kformmaster.helper

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener
import com.thejuki.kformmaster.model.BaseFormElement
import com.thejuki.kformmaster.view.*
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

    var mElements: ArrayList<BaseFormElement<*>> = arrayListOf()

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
    constructor(context: Context, listener: OnFormElementValueChangedListener? = null, recyclerView: RecyclerView? = null) {
        initializeFormBuildHelper(context, listener)

        if(recyclerView != null)
        {
            attachRecyclerView(context, recyclerView)
        }
    }

    fun setError(textViewError: AppCompatTextView, error: String?) {
        if (error.isNullOrEmpty()) {
            textViewError.visibility = View.GONE
            return
        }

        textViewError.text = error
        textViewError.visibility = View.VISIBLE
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

        this.mFormAdapter = RendererRecyclerViewAdapter(context)
        this.mFormAdapter!!.registerRenderer(FormHeaderViewBinder(context, this).viewBinder)

        this.mFormAdapter!!.registerRenderer(FormEditTextViewBinder(context, this).viewBinder)

        this.mFormAdapter!!.registerRenderer(FormAutoCompleteViewBinder(context, this).viewBinder)
        this.mFormAdapter!!.registerRenderer(FormTokenAutoCompleteViewBinder(context, this).viewBinder)

        this.mFormAdapter!!.registerRenderer(FormButtonViewBinder(context, this).viewBinder)
        this.mFormAdapter!!.registerRenderer(FormSwitchViewBinder(context, this).viewBinder)
        this.mFormAdapter!!.registerRenderer(FormSliderViewBinder(context, this).viewBinder)

        this.mFormAdapter!!.registerRenderer(FormPickerDateViewBinder(context, this).viewBinder)
        this.mFormAdapter!!.registerRenderer(FormPickerTimeViewBinder(context, this).viewBinder)
        this.mFormAdapter!!.registerRenderer(FormPickerDateTimeViewBinder(context, this).viewBinder)
        this.mFormAdapter!!.registerRenderer(FormPickerMultiCheckBoxViewBinder(context, this).viewBinder)
        this.mFormAdapter!!.registerRenderer(FormPickerDropDownViewBinder(context, this).viewBinder)

        this.mFormAdapter!!.registerRenderer(FormTextViewViewBinder(context, this).viewBinder)

        this.mListener = listener
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
        this.mElements.addAll(formElements)
        this.mFormAdapter!!.setItems(this.mElements)
    }

    fun setItems()
    {
        this.mFormAdapter!!.setItems(this.mElements)
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

        return this.mElements.firstOrNull { !it.isHeader && it.mTag == tag }
    }

    fun getElementAtIndex(index: Int): BaseFormElement<*>? {
        return this.mElements[index]

    }

    fun onValueChanged(element: BaseFormElement<*>) {
        mListener?.onValueChanged(element)
    }
}