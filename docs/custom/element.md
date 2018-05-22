It's possible to create your own form element. Here is what is needed.

### Form Element Layout
Create a new XML layout file. We'll name it form_element_custom.xml.
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@android:color/black"
    android:orientation="vertical"
    android:paddingBottom="16dp">

    <View
        android:layout_width="match_parent"
        android:layout_height="0.5dp"
        android:layout_marginLeft="16dp"
        android:layout_marginRight="16dp"
        android:background="@color/colorFormMasterDivider" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginLeft="16dp"
        android:layout_marginRight="16dp"
        android:layout_marginTop="16dp"
        android:orientation="horizontal">

        <android.support.v7.widget.AppCompatTextView
            android:id="@+id/formElementTitle"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="2"
            android:textColor="@android:color/white"
            android:textSize="@dimen/elementTextTitleSize"
            tools:text="Test Title" />

        <android.support.v7.widget.AppCompatEditText
            android:id="@+id/formElementValue"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="3"
            android:background="@null"
            android:gravity="end"
            android:imeOptions="actionNext"
            android:inputType="textNoSuggestions"
            android:maxLines="1"
            android:textColor="@color/colorFormMasterElementFocusedTitle"
            android:textSize="20sp"
            tools:text="Test Value" />

    </LinearLayout>

    <android.support.v7.widget.AppCompatTextView
        android:id="@+id/formElementError"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_marginLeft="16dp"
        android:layout_marginRight="16dp"
        android:layout_marginTop="10dp"
        android:layout_weight="1"
        android:textColor="@color/colorFormMasterElementErrorTitle"
        android:textSize="@dimen/elementErrorTitleSize"
        android:visibility="gone"
        tools:text="Test Error" />

</LinearLayout>
```

### Form Element Model
Note that a new model does not need to contain a body if BaseFormElement provides everything you need.
```kotlin
class FormCustomElement: BaseFormElement<String> {
    constructor() : super()
    constructor(tag: Int) : super(tag)
}
```

### Optional: Form Builder Extension
Create a FormBuildHelper DSL method and builder class for your custom form model.
```kotlin
/** Builder method to add a CustomElement */
class CustomElementBuilder(tag: Int = -1) : BaseElementBuilder<String>(tag) {
    override fun build() =
            FormCustomElement(tag).apply {
                this@CustomElementBuilder.let {
                    title = it.title.orEmpty()
                    value = it.value
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a CustomElement */
fun FormBuildHelper.customEx(tag: Int = -1, init: CustomElementBuilder.() -> Unit): FormCustomElement {
    return addFormElement(CustomElementBuilder(tag).apply(init).build())
}
```

### Optional: Form Element View State
Create a view state class your custom form element value.
```kotlin
class FormCustomViewState(holder: ViewHolder) : BaseFormViewState(holder) {
    private var value: String? = null

    init {
        val editText = holder.viewFinder.find(R.id.formElementValue) as AppCompatEditText
        value = editText.text.toString()
    }

    override fun restore(holder: ViewHolder) {
        super.restore(holder)
        holder.viewFinder.setText(R.id.formElementValue, value)
    }
}
```

### Form Element View Binder
Create a view binder for your custom form element.

!!! note "ViewBinder"

    * layoutID parameter - Form element layout name
    * type parameter - Form element model class (ModelName::class.java)
    * binder parameter: 
        - model is a form element instance model
        - finder can find views or set fields of a view using its ID
        - payloads can be replaced with "_" as it is not used
    * viewStateProvider parameter - Form element view state provider

```kotlin
class CustomViewBinder(private val context: Context, 
private val formBuilder: FormBuildHelper) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(R.layout.form_element_custom, 
    FormCustomElement::class.java, { model, finder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as AppCompatTextView
        val textViewError = finder.find(R.id.formElementError) as AppCompatTextView
        val itemView = finder.getRootView() as View
        baseSetup(model, textViewTitle, textViewError, itemView)

        val editTextValue = finder.find(R.id.formElementValue) as AppCompatEditText

        editTextValue.setText(model.valueAsString)
        editTextValue.hint = model.hint ?: ""

        setEditTextFocusEnabled(editTextValue, itemView)

        editTextValue.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                textViewTitle.setTextColor(ContextCompat.getColor(context,
                        R.color.colorFormMasterElementFocusedTitle))
            } else {
                textViewTitle.setTextColor(ContextCompat.getColor(context,
                        R.color.colorFormMasterElementTextTitle))
            }
        }

        model.editView = editTextValue

        // Initially use 4 lines
        // unless a different number was provided
        if (model.maxLines == 1) {
            model.maxLines = 4
        }

        editTextValue.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {

                // get current form element, existing value and new value
                val currentValue = model.valueAsString
                val newValue = charSequence.toString()

                // trigger event only if the value is changed
                if (currentValue != newValue) {
                    // NOTE: Use setValue() 
                    // as this will suppress the unchecked cast
                    model.setValue(newValue)
                    model.error = null
                    formBuilder.onValueChanged(model)
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }, object : ViewStateProvider<FormCustomElement, ViewHolder> {
        override fun createViewStateID(model: FormCustomElement): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormCustomViewState(holder)
        }
    })

    private fun setEditTextFocusEnabled(editTextValue: AppCompatEditText, itemView: View) {
        itemView.setOnClickListener {
            editTextValue.requestFocus()
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            editTextValue.setSelection(editTextValue.text.length)
            imm.showSoftInput(editTextValue, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}
```

### Use your Custom Form Element
Create a form activity for your custom form element.

!!! error "IMPORTANT"

    * Register your custom view binder or you will get a RuntimeException
    * RuntimeException: ViewRenderer not registered for this type

```kotlin
class CustomFormActivity : AppCompatActivity() {

    // Setup the FormBuildHelper at the class level if necessary
    private lateinit var formBuilder: FormBuildHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        setupForm()
    }

    private enum class Tag {
        Custom
    }

    private fun setupForm() {
        formBuilder = form(this, recyclerView) {
            customEx(Tag.Custom.ordinal) {
                title = getString(R.string.Custom)
            }
        }

        // Required
        formBuilder.registerCustomViewBinder(CustomViewBinder(this, formBuilder).viewBinder)
    }
}
```