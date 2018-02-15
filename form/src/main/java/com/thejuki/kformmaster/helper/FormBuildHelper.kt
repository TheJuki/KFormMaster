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

    private var formAdapter: RendererRecyclerViewAdapter? = null

    var elements: ArrayList<BaseFormElement<*>> = arrayListOf()

    private var listener: OnFormElementValueChangedListener? = null

    /**
     * return true if the form is valid
     *
     * @return
     */
    val isValidForm: Boolean
        get() {
            return (0 until this.formAdapter!!.itemCount)
                    .map { this.getElementAtIndex(it) }
                    .none { !it!!.isHeader and it.isRequired() and it.value.toString().trim().isEmpty() }
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

        // Initialize form adapter
        this.elements = ArrayList()
        this.formAdapter = RendererRecyclerViewAdapter(context)
        this.formAdapter!!.setDiffCallback(ElementDiffCallback())

        // Header
        this.formAdapter!!.registerRenderer(FormHeaderViewBinder(context, this).viewBinder)

        // Edit Texts
        this.formAdapter!!.registerRenderer(FormSingleLineEditTextViewBinder(context, this).viewBinder)
        this.formAdapter!!.registerRenderer(FormMultiLineEditTextViewBinder(context, this).viewBinder)
        this.formAdapter!!.registerRenderer(FormNumberEditTextViewBinder(context, this).viewBinder)
        this.formAdapter!!.registerRenderer(FormEmailEditTextViewBinder(context, this).viewBinder)
        this.formAdapter!!.registerRenderer(FormPhoneEditTextViewBinder(context, this).viewBinder)
        this.formAdapter!!.registerRenderer(FormPasswordEditTextViewBinder(context, this).viewBinder)

        // AutoCompletes
        this.formAdapter!!.registerRenderer(FormAutoCompleteViewBinder(context, this).viewBinder)
        this.formAdapter!!.registerRenderer(FormTokenAutoCompleteViewBinder(context, this).viewBinder)

        // Button
        this.formAdapter!!.registerRenderer(FormButtonViewBinder(context, this).viewBinder)

        // Switch
        this.formAdapter!!.registerRenderer(FormSwitchViewBinder(context, this).viewBinder)

        // Slider
        this.formAdapter!!.registerRenderer(FormSliderViewBinder(context, this).viewBinder)

        // Pickers
        this.formAdapter!!.registerRenderer(FormPickerDateViewBinder(context, this).viewBinder)
        this.formAdapter!!.registerRenderer(FormPickerTimeViewBinder(context, this).viewBinder)
        this.formAdapter!!.registerRenderer(FormPickerDateTimeViewBinder(context, this).viewBinder)
        this.formAdapter!!.registerRenderer(FormPickerMultiCheckBoxViewBinder(context, this).viewBinder)
        this.formAdapter!!.registerRenderer(FormPickerDropDownViewBinder(context, this).viewBinder)

        // Text View
        this.formAdapter!!.registerRenderer(FormTextViewViewBinder(context, this).viewBinder)

        this.listener = listener
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
        recyclerView.adapter = formAdapter
        recyclerView.itemAnimator = DefaultItemAnimator()
    }

    /**
     * add list of form elements to be shown
     *
     * @param formElements
     */
    fun addFormElements(formElements: List<BaseFormElement<*>>) {
        this.elements.addAll(formElements)
        this.formAdapter!!.setItems(this.elements)
    }

    fun setItems()
    {
        this.formAdapter!!.setItems(this.elements)
    }

    /**
     * redraws the view
     */
    fun refreshView() {
        this.formAdapter!!.notifyDataSetChanged()
    }

    /**
     * get value of specific form element
     *
     * @param tag
     * @return
     */
    fun getFormElement(tag: Int): BaseFormElement<*>? {

        return this.elements.firstOrNull { !it.isHeader && it.tag == tag }
    }

    fun getElementAtIndex(index: Int): BaseFormElement<*>? {
        return this.elements[index]

    }

    fun onValueChanged(element: BaseFormElement<*>) {
        listener?.onValueChanged(element)
    }
}