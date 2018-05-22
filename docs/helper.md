### Cache Form Elements
By default, RecyclerView will cache 2 views. By setting cacheForm = true in the FormBuildHelper, all form elements will be cached instead of recycled.
NOTE: This is enabled by default.

### Refresh Dropdown or MultiCheckBox options
After changing the options in the model for a Dropdown or MultiCheckBox element, call the reInitDialog function.

```kotlin
element.options = listOf()
element.reInitDialog(this /* context */, formBuilder)
```

### Set form element value change listener to get changed value instantly
While creating a new instance of FormBuildHelper, add a listener in the constructor.

```kotlin
var formBuilder = FormBuildHelper(this, object : OnFormElementValueChangedListener {
    override fun onValueChanged(formElement: BaseFormElement<*>) {
         // do anything here with formElement.value
    }
}, findViewById(R.id.recyclerView), cacheForm = true)
```

### Get value for unique form elements
Use the unique tag assigned earlier to retrieve value (See examples in this repo).
```kotlin
val element = formBuilder.getFormElement<FormEmailEditTextElement>(Email.ordinal)
val value: String = element.value
```
Use the added index of the element instead if you did not assign a tag.
```kotlin
val element = formBuilder.getElementAtIndex(2)
val value: String = element?.value as String
```

### Check if the form is valid
Use this variable (method in Java) if you need to check whether the required elements of the form are completed.
```kotlin
formBuilder.isValidForm // returns Boolean of whether the form is valid or not
```

### Clear all values
Clear all values of the form elements by calling clearAll().
```kotlin
formBuilder.clearAll()
```

### Add Form Element
Add one form element. Note that setItems() needs to be called afterward.
```kotlin
formBuilder.addFormElement(FormButtonElement())
formBuilder.setItems()
```

### Add Form Elements
Add multiple form elements.
```kotlin
val elements: MutableList<BaseFormElement<*>> = mutableListOf()
formBuilder.addFormElements(elements)
```

### Auto Measure Enabled
By default this is false.
Enable autoMeasureEnabled when the layout_height of recyclerView is wrap_content such as the LoginFormActivity example.
```kotlin
formBuilder = FormBuildHelper(this, recyclerView, autoMeasureEnabled = true)
```

```kotlin
formBuilder = FormBuildHelper(this)
formBuilder.attachRecyclerView(this, recyclerView, autoMeasureEnabled = true)
```