package com.thejuki.kformmaster.helper

import android.content.Context
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
@JvmOverloads constructor(context: Context, listener: OnFormElementValueChangedListener? = null, recyclerView: RecyclerView? = null, val cacheForm: Boolean = true, val formLayouts: FormLayouts? = null) {

    init {
        initializeFormBuildHelper(context, listener)

        if (recyclerView != null) {
            attachRecyclerView(context, recyclerView)
        }
    }

    /**
     * Used for form models. Increments when an element is added.
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
    internal var listener: OnFormElementValueChangedListener? = null

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
        this.formAdapter = RendererRecyclerViewAdapter()
        this.formAdapter.setDiffCallback(ElementDiffCallback())

        // Header
        this.formAdapter.registerRenderer(FormHeaderViewBinder(context, this, formLayouts?.header).viewBinder)

        // Label
        this.formAdapter.registerRenderer(FormLabelViewBinder(context, this, formLayouts?.label).viewBinder)

        // Edit Texts
        registerEditTexts(context)

        // AutoCompletes
        this.formAdapter.registerRenderer(FormAutoCompleteViewBinder(context, this, formLayouts?.autoComplete).viewBinder)
        this.formAdapter.registerRenderer(FormTokenAutoCompleteViewBinder(context, this, formLayouts?.autoCompleteToken).viewBinder)

        // Button
        this.formAdapter.registerRenderer(FormButtonViewBinder(context, this, formLayouts?.button).viewBinder)

        // Switch
        this.formAdapter.registerRenderer(FormSwitchViewBinder(context, this, formLayouts?.switch).viewBinder)

        // CheckBox
        this.formAdapter.registerRenderer(FormCheckBoxViewBinder(context, this, formLayouts?.checkBox).viewBinder)

        // Segmented
        this.formAdapter.registerRenderer(FormSegmentedViewBinder(context, this, formLayouts?.segmented).viewBinder)

        // Slider
        this.formAdapter.registerRenderer(FormSliderViewBinder(context, this, formLayouts?.slider).viewBinder)

        // Progress
        this.formAdapter.registerRenderer(FormProgressViewBinder(context, this, formLayouts?.progress).viewBinder)

        // Pickers
        registerPickers(context)

        // Text View
        this.formAdapter.registerRenderer(FormTextViewViewBinder(context, this, formLayouts?.textView).viewBinder)

        this.listener = listener
    }

    private fun registerEditTexts(context: Context) {
        this.formAdapter.registerRenderer(FormSingleLineEditTextViewBinder(context, this, formLayouts?.text).viewBinder)
        this.formAdapter.registerRenderer(FormMultiLineEditTextViewBinder(context, this, formLayouts?.textArea).viewBinder)
        this.formAdapter.registerRenderer(FormNumberEditTextViewBinder(context, this, formLayouts?.number).viewBinder)
        this.formAdapter.registerRenderer(FormEmailEditTextViewBinder(context, this, formLayouts?.email).viewBinder)
        this.formAdapter.registerRenderer(FormPhoneEditTextViewBinder(context, this, formLayouts?.phone).viewBinder)
        this.formAdapter.registerRenderer(FormPasswordEditTextViewBinder(context, this, formLayouts?.password).viewBinder)
    }

    private fun registerPickers(context: Context) {
        this.formAdapter.registerRenderer(FormPickerDateViewBinder(context, this, formLayouts?.date).viewBinder)
        this.formAdapter.registerRenderer(FormPickerTimeViewBinder(context, this, formLayouts?.time).viewBinder)
        this.formAdapter.registerRenderer(FormPickerDateTimeViewBinder(context, this, formLayouts?.dateTime).viewBinder)
        this.formAdapter.registerRenderer(FormPickerMultiCheckBoxViewBinder(context, this, formLayouts?.multiCheckBox).viewBinder)
        this.formAdapter.registerRenderer(FormPickerDropDownViewBinder(context, this, formLayouts?.dropDown).viewBinder)
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
        recyclerView?.let {
            // Set up the RecyclerView with the adapter
            it.layoutManager = LinearLayoutManager(context).apply {
                orientation = RecyclerView.VERTICAL
                stackFromEnd = false
            }
            it.adapter = formAdapter
            it.itemAnimator = DefaultItemAnimator()
            this.recyclerView = it
        }
    }

    /**
     * Adds the given [formElement] to the elements list returns it
     */
    fun <T : BaseFormElement<*>> addFormElement(formElement: T): T {
        formElement.id = ++lastId
        this.elements.add(formElement)
        return formElement
    }

    /**
     * Adds the given [formElements] to the elements list and the adapter
     */
    fun addFormElements(formElements: List<BaseFormElement<*>>) {
        this.elements.addAll(formElements)
        for (element in elements) {
            if (element.id == 0) {
                element.id = ++lastId
            }
        }
        setItems()
    }

    /**
     * Sets the adapter items to the form elements
     */
    fun setItems() {
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
        this.elements.forEach { it.clear() }
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