Common values used by multiple form elements are stored in the base form element model such as tag, title, and value.

!!! info "Base Value Requirements"

    None of the base values are required but providing a title is advised.

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
This list of value observers are fired when the form element value changes. When a value changes, the Unit passes in the new value and form element.
```kotlin
text {
    value = "Hello World"
    valueObservers.add({ newValue, element ->
        Toast.makeText(this@FormActivity, newValue, LENGTH_SHORT).show()
    })
}
```

## Options and Options Selected
The list of options is used in the Form Picker Dropdown Element and Form Picker MultiCheckBox Element. 
The list of options selected is in the Form Picker MultiCheckBox Element. 
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
multiCheckBox<ListItem> {
    options = fruits
    optionsSelected = listOf(ListItem(id = 1, name = "Banana"))
}
```
### Updating options later
After changing the options in the model for a Dropdown or MultiCheckBox element, call the reInitDialog function.
```kotlin
element.options = listOf()
element.reInitDialog(this /* context */, formBuilder)
```