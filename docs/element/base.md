Common values used by multiple form elements are stored in the base form element model such as tag, title, and value.

!!! info "Base Value Requirements"

    - None of the base values are required but providing a title is advised.
    - Editable fields that require a keyboard change the form element value when they lose focus. Because of this, before validating or submitting a form, clear the focus of the form using `currentFocus?.clearFocus()` in your activity.

## Tag
The tag is an integer used to easily get a form element later. You can also retrieve a form element by the index it was inserted into the form elements list.
```kotlin
text(1) {

}
email(Tag.Email.ordinal) {

}
```
```kotlin
val textElement = formBuilder.getElementAtIndex(0) as FormSingleLineEditTextElement
val emailElement = formBuilder.getFormElement<FormEmailEditTextElement>(Tag.Email.ordinal)
```

## Title
The title is the text displayed next to the value of the form element.
```kotlin
text {
    title = getString(R.string.title)
}
```

## Value
The value is the value of the form element. Depending on the form element, this can be a String, Date, or an object of your choice.
```kotlin
text {
    value = "Hello World"
}
autoComplete<ContactItem> {
    value = ContactItem(id = 1, value = "John Smith")
}
```

## Value Observers
The list of value observers are fired when the form element value changes. When a value changes, the Unit passes in the new value and form element.
```kotlin
text {
    value = "Hello World"
    valueObservers.add({ newValue, element ->
        Toast.makeText(this@FormActivity, newValue, LENGTH_SHORT).show()
    })
}
```

## Options
The list of options is used in form elements such as the Form Picker Dropdown Element and Form Picker MultiCheckBox Element. 
```kotlin
private val fruits = listOf(ListItem(id = 1, name = "Banana"),
    ListItem(id = 2, name = "Orange"),
    ListItem(id = 3, name = "Mango"),
    ListItem(id = 4, name = "Guava"),
    ListItem(id = 5, name = "Apple")
)

dropDown<ListItem> {
    options = fruits
    value = ListItem(id = 1, name = "Banana")
}
multiCheckBox<List<ListItem>> {
    options = fruits
    value = listOf(ListItem(id = 1, name = "Banana"))
}
```
### Updating options later
After changing the options in the model for a Dropdown or MultiCheckBox element, call the reInitDialog function.
```kotlin
element.options = listOf()
element.reInitDialog(this /* context */, formBuilder)
```

## Hint
The hint is the hint (or placeholder) of the value field.
```kotlin
text {
    hint = "Enter a name"
}
```

## Max Lines
By default this is 1. TextArea defaults to 4.
Max lines represents the number of lines the value field will display.

!!! error "Unsupported Fields"

    * Password
    * Number
    * Text (Use TextArea instead)

```kotlin
textArea {
    maxLines = 3
}
```

## Right To Left (RTL)
By default this is true.
This determines the gravity of the text in the value field.
```kotlin
text {
    rightToLeft = false
}
```

## Error
This is the error text displayed in red below the form element.
```kotlin
text {
    error = "That's an error"
}
```

## Required
By default this is false.
Setting required to true will cause FormBuildHelper.isValidForm to return false if a value is not set.
```kotlin
text {
    required = true
}
```

## Visible
By default this is true.
Setting visible to false will hide the form element.
```kotlin
text {
    visible = false
}
```

## Enabled
By default this is true.
Setting enabled to false will disable the form element. This means that the click event will not fire when tapped, the text will be grayed out, and the value cannot be edited.

!!! info "Text View"

    Use the TextView element if you just want a simple uneditable text element.

```kotlin
text {
    enabled = false
}
```

## Clear value
Clear the value of the element.
```kotlin
element.clear()
```

## Is the value valid?
isValid contains a getter that checks if the valid is valid. At the base level, this checks if the value is not null or empty.
This is used by formBuilder.isValidForm.
```kotlin
element.isValid
```

## Dynamic Views

!!! note "NOTICE"

    The Item View, Title View, Edit View, and Error View are accessible from the model to support changing variables such as the title, value, visibility, and error. However, try not to use the views directly. Let the variable setters do the work for you. Also, the views cannot be modified during the creation of the form elements as they are initialized during the render of the RecylerView.