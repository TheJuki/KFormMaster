package com.thejuki.kformmaster.helper

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer
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
@JvmOverloads constructor(listener: OnFormElementValueChangedListener? = null, recyclerView: RecyclerView? = null, val cacheForm: Boolean = true, val formLayouts: FormLayouts? = null) {

    init {
        initializeFormBuildHelper(listener)

        if (recyclerView != null) {
            attachRecyclerView(recyclerView)
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
    private fun initializeFormBuildHelper(listener: OnFormElementValueChangedListener?) {

        // Initialize form adapter
        this.elements = ArrayList()
        this.formAdapter = FormViewAdapter()
        this.formAdapter.setDiffCallback(ElementDiffCallback())

        // Header
        this.formAdapter.registerRenderer(FormHeaderViewRenderer(this, formLayouts?.header).viewRenderer)

        // Label
        this.formAdapter.registerRenderer(FormLabelViewRenderer(this, formLayouts?.label).viewRenderer)

        // Edit Texts
        registerEditTexts()

        // AutoCompletes
        this.formAdapter.registerRenderer(FormAutoCompleteViewRenderer(this, formLayouts?.autoComplete).viewRenderer)
        this.formAdapter.registerRenderer(FormTokenAutoCompleteViewRenderer(this, formLayouts?.autoCompleteToken).viewRenderer)

        // Button
        this.formAdapter.registerRenderer(FormButtonViewRenderer(this, formLayouts?.button).viewRenderer)

        // Switch
        this.formAdapter.registerRenderer(FormSwitchViewRenderer(this, formLayouts?.switch).viewRenderer)

        // CheckBox
        this.formAdapter.registerRenderer(FormCheckBoxViewRenderer(this, formLayouts?.checkBox).viewRenderer)

        // Segmented
        this.formAdapter.registerRenderer(FormSegmentedViewRenderer(this, formLayouts?.segmented).viewRenderer)

        // Slider
        this.formAdapter.registerRenderer(FormSliderViewRenderer(this, formLayouts?.slider).viewRenderer)

        // Progress
        this.formAdapter.registerRenderer(FormProgressViewRenderer(this, formLayouts?.progress).viewRenderer)

        // Pickers
        registerPickers()

        // Text View
        this.formAdapter.registerRenderer(FormTextViewViewRenderer(this, formLayouts?.textView).viewRenderer)

        // Image
        this.formAdapter.registerRenderer(FormImageViewRenderer(this, formLayouts?.image).viewRenderer)

        // InlineDateTimePicker
        this.formAdapter.registerRenderer(FormInlineDatePickerViewRenderer(this, formLayouts?.inlineDateTimePicker).viewRenderer)

        this.listener = listener
    }

    private fun registerEditTexts() {
        this.formAdapter.registerRenderer(FormSingleLineEditTextViewRenderer(this, formLayouts?.text).viewRenderer)
        this.formAdapter.registerRenderer(FormMultiLineEditTextViewRenderer(this, formLayouts?.textArea).viewRenderer)
        this.formAdapter.registerRenderer(FormNumberEditTextViewRenderer(this, formLayouts?.number).viewRenderer)
        this.formAdapter.registerRenderer(FormEmailEditTextViewRenderer(this, formLayouts?.email).viewRenderer)
        this.formAdapter.registerRenderer(FormPhoneEditTextViewRenderer(this, formLayouts?.phone).viewRenderer)
        this.formAdapter.registerRenderer(FormPasswordEditTextViewRenderer(this, formLayouts?.password).viewRenderer)
    }

    private fun registerPickers() {
        this.formAdapter.registerRenderer(FormPickerDateViewRenderer(this, formLayouts?.date).viewRenderer)
        this.formAdapter.registerRenderer(FormPickerTimeViewRenderer(this, formLayouts?.time).viewRenderer)
        this.formAdapter.registerRenderer(FormPickerDateTimeViewRenderer(this, formLayouts?.dateTime).viewRenderer)
        this.formAdapter.registerRenderer(FormPickerMultiCheckBoxViewRenderer(this, formLayouts?.multiCheckBox).viewRenderer)
        this.formAdapter.registerRenderer(FormPickerDropDownViewRenderer(this, formLayouts?.dropDown).viewRenderer)
    }

    /**
     * Registers the custom [ViewRenderer]
     */
    fun registerCustomViewRenderer(viewRenderer: ViewRenderer<*, *>) {
        this.formAdapter.registerRenderer(viewRenderer)
    }

    /**
     * Attaches the given [recyclerView] to form
     */
    fun attachRecyclerView(recyclerView: RecyclerView?) {
        recyclerView?.let {
            // Set up the RecyclerView with the adapter
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