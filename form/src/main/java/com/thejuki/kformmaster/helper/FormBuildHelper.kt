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
@FormDsl
class FormBuildHelper
@JvmOverloads constructor(context: Context, listener: OnFormElementValueChangedListener? = null, recyclerView: RecyclerView? = null, val cacheForm: Boolean = true, autoMeasureEnabled: Boolean = false) {

    init {
        initializeFormBuildHelper(context, listener)

        if (recyclerView != null) {
            attachRecyclerView(context, recyclerView, autoMeasureEnabled)
        }
    }

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
        get() = this.elements.all { it.isValid }

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

    private fun registerEditTexts(context: Context) {
        this.formAdapter.registerRenderer(FormSingleLineEditTextViewBinder(context, this).viewBinder)
        this.formAdapter.registerRenderer(FormMultiLineEditTextViewBinder(context, this).viewBinder)
        this.formAdapter.registerRenderer(FormNumberEditTextViewBinder(context, this).viewBinder)
        this.formAdapter.registerRenderer(FormEmailEditTextViewBinder(context, this).viewBinder)
        this.formAdapter.registerRenderer(FormPhoneEditTextViewBinder(context, this).viewBinder)
        this.formAdapter.registerRenderer(FormPasswordEditTextViewBinder(context, this).viewBinder)
    }

    private fun registerPickers(context: Context) {
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
    fun attachRecyclerView(context: Context, recyclerView: RecyclerView?, autoMeasureEnabled: Boolean = false) {
        recyclerView?.let {
            // Set up the RecyclerView with the adapter
            it.layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
                stackFromEnd = false
                isAutoMeasureEnabled = autoMeasureEnabled
            }
            it.adapter = formAdapter
            it.itemAnimator = DefaultItemAnimator()
            this.recyclerView = it
        }
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
    internal fun setItems() {
        this.formAdapter.setItems(this.elements)
        if (this.cacheForm) {
            this.recyclerView.setItemViewCacheSize(this.elements.size)
        }
        this.formAdapter.notifyDataSetChanged()
    }

    /**
     * Clears all form element values
     */
    fun clearAll() {
        this.elements.forEach({ it.clear() })
    }

    /**
     * Gets a form element with the given [tag]
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : BaseFormElement<*>> getFormElement(tag: Int): T {
        return this.elements.first { it.tag == tag } as T
    }

    /**
     * Gets a form element at the given [index]
     */
    fun getElementAtIndex(index: Int): BaseFormElement<*> {
        return this.elements[index]

    }

    /**
     * Calls onValueChanged and passes the given [element]
     */
    fun onValueChanged(element: BaseFormElement<*>) {
        listener?.onValueChanged(element)
    }
}