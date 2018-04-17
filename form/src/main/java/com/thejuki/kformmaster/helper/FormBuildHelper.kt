package com.thejuki.kformmaster.helper

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
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

    /**
     * Used for form models. Increments when a element is added.
     */
    var lastId = 0

    /**
     * RendererRecyclerViewAdapter instance
     */
    private lateinit var formAdapter: RendererRecyclerViewAdapter

    /**
     * RecyclerView instance
     */
    private lateinit var recyclerView: RecyclerView

    /**
     * Enable to cache all form elements in the [RecyclerView]
     */
    var cacheForm: Boolean = false

    /**
     * Holds the form elements
     */
    var elements: ArrayList<BaseFormElement<*>> = arrayListOf()
        internal set

    /**
     * Listener for when an element's value changes
     */
    private var listener: OnFormElementValueChangedListener? = null

    /**
     * Checks if the form is valid
     * - Not a header
     * - Is Required
     * - Value is not null
     */
    val isValidForm: Boolean
        get() {
            return (0 until this.formAdapter.itemCount)
                    .map { this.getElementAtIndex(it) }
                    .all {
                        !it!!.isHeader && it.isRequired() && it.value != null &&
                                (it.value !is String || !(it.value as? String).isNullOrEmpty())
                    }
        }

    /**
     * Creates a new FormBuildHelper using the Activity's [context] only
     * You must call attachRecyclerView afterward
     */
    constructor(context: Context) {
        initializeFormBuildHelper(context, null)
    }

    /**
     * Creates a new FormBuildHelper using the
     * Activity's [context], a form [listener], a [recyclerView], and [cacheForm] property
     */
    constructor(context: Context,
                listener: OnFormElementValueChangedListener? = null,
                recyclerView: RecyclerView? = null,
                cacheForm: Boolean = false) {
        initializeFormBuildHelper(context, listener)
        this.cacheForm = cacheForm

        if(recyclerView != null)
        {
            attachRecyclerView(context, recyclerView)
        }
    }

    /**
     * Sets the given [error] on [textViewError]
     */
    fun setError(textViewError: AppCompatTextView, error: String?) {
        if (error.isNullOrEmpty()) {
            textViewError.visibility = View.GONE
            return
        }

        textViewError.text = error
        textViewError.visibility = View.VISIBLE
    }

    /**
     * Initializes the form by setting up the RendererRecyclerViewAdapter
     */
    private fun initializeFormBuildHelper(context: Context, listener: OnFormElementValueChangedListener?) {

        // Initialize form adapter
        this.elements = ArrayList()
        this.formAdapter = RendererRecyclerViewAdapter(context)
        this.formAdapter.setDiffCallback(ElementDiffCallback())

        // Header
        this.formAdapter.registerRenderer(FormHeaderViewBinder(context, this).viewBinder)

        // Edit Texts
        registerEditTexts(context)

        // AutoCompletes
        this.formAdapter.registerRenderer(FormAutoCompleteViewBinder(context, this).viewBinder)
        this.formAdapter.registerRenderer(FormTokenAutoCompleteViewBinder(context, this).viewBinder)

        // Button
        this.formAdapter.registerRenderer(FormButtonViewBinder(context, this).viewBinder)

        // Switch
        this.formAdapter.registerRenderer(FormSwitchViewBinder(context, this).viewBinder)

        // CheckBox
        this.formAdapter.registerRenderer(FormCheckBoxViewBinder(context, this).viewBinder)

        // Slider
        this.formAdapter.registerRenderer(FormSliderViewBinder(context, this).viewBinder)

        // Pickers
        registerPickers(context)

        // Text View
        this.formAdapter.registerRenderer(FormTextViewViewBinder(context, this).viewBinder)

        this.listener = listener
    }

    private fun registerEditTexts(context: Context)
    {
        this.formAdapter.registerRenderer(FormSingleLineEditTextViewBinder(context, this).viewBinder)
        this.formAdapter.registerRenderer(FormMultiLineEditTextViewBinder(context, this).viewBinder)
        this.formAdapter.registerRenderer(FormNumberEditTextViewBinder(context, this).viewBinder)
        this.formAdapter.registerRenderer(FormEmailEditTextViewBinder(context, this).viewBinder)
        this.formAdapter.registerRenderer(FormPhoneEditTextViewBinder(context, this).viewBinder)
        this.formAdapter.registerRenderer(FormPasswordEditTextViewBinder(context, this).viewBinder)
    }

    private fun registerPickers(context: Context)
    {
        this.formAdapter.registerRenderer(FormPickerDateViewBinder(context, this).viewBinder)
        this.formAdapter.registerRenderer(FormPickerTimeViewBinder(context, this).viewBinder)
        this.formAdapter.registerRenderer(FormPickerDateTimeViewBinder(context, this).viewBinder)
        this.formAdapter.registerRenderer(FormPickerMultiCheckBoxViewBinder(context, this).viewBinder)
        this.formAdapter.registerRenderer(FormPickerDropDownViewBinder(context, this).viewBinder)
    }

    /**
     * Registers the custom [viewBinder]
     */
    fun registerCustomViewBinder(viewBinder: ViewBinder<*>) {
        this.formAdapter.registerRenderer(viewBinder)
    }

    /**
     * Attaches the given [recyclerView] to form
     */
    fun attachRecyclerView(context: Context, recyclerView: RecyclerView?) {
        if (recyclerView == null) {
            return
        }

        // Set up the RecyclerView with the adapter
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.stackFromEnd = false

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = formAdapter
        recyclerView.itemAnimator = DefaultItemAnimator()
        this.recyclerView = recyclerView
    }

    /**
     * Adds the given [formElements] to the elements list and the adapter
     */
    fun addFormElements(formElements: List<BaseFormElement<*>>) {
        this.elements.addAll(formElements)
        for (element in elements) {
            element.id = ++lastId
        }
        setItems()
    }

    /**
     * Sets the adapter items to the form elements
     */
    internal fun setItems()
    {
        this.formAdapter.setItems(this.elements)
        if (this.cacheForm) {
            recyclerView.setItemViewCacheSize(this.elements.size)
        }
    }

    /**
     * Redraws the view
     */
    fun refreshView() {
        this.formAdapter.notifyDataSetChanged()
    }

    /**
     * Gets a form element with the given [tag]
     */
    fun getFormElement(tag: Int): BaseFormElement<*>? {

        return this.elements.firstOrNull { !it.isHeader && it.tag == tag }
    }

    /**
     * Gets a form element at the given [index]
     */
    fun getElementAtIndex(index: Int): BaseFormElement<*>? {
        return this.elements[index]

    }

    /**
     * Calls onValueChanged and passes the given [element]
     */
    fun onValueChanged(element: BaseFormElement<*>) {
        listener?.onValueChanged(element)
    }
}