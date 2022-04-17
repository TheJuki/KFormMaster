# Base

Common values used by multiple form elements are stored in the base form element model such as tag, title, and value.

!!! info "Base Value Requirements"

    - None of the base values are required but providing a title is advised.
    - Editable fields that require a keyboard change the form element value when they lose focus. Because of this, before validating or submitting a form, clear the focus of the form using `currentFocus?.clearFocus()` in your activity.

!!! info "Setting Colors"

    - Setting the form element colors requires a Color Int
    - R.color.name is a Color Res and you must get the Color Int using ResourcesCompat.getColor() or similar
    - Examples:
        - Color.WHITE
        - Color.parseColor("#FF4081")
        - ResourcesCompat.getColor(resources, R.color.name, null) 

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

## On Click Unit
This unit is invoked when the form element is clicked.
NOTE: The click event is not fired when a text field is clicked on to focus. Use onFocus to get the event when a field has focus.
```kotlin
text {
     onClick = {
        Toast.makeText(this@FormActivity, this.javaClass.simpleName, LENGTH_SHORT).show()
    }
}
```

## On Focus Unit
This unit is invoked when the form element is focused.
```kotlin
text {
     onFocus = {
        Toast.makeText(this@FormActivity, this.javaClass.simpleName, LENGTH_SHORT).show()
    }
}
```

## On Touch Up Unit
This unit is invoked when the form element is touched up.
```kotlin
text {
     onTouchUp = {
        Toast.makeText(this@FormActivity, this.javaClass.simpleName, LENGTH_SHORT).show()
    }
}
```

## On Touch Down Unit
This unit is invoked when the form element is touched down.
```kotlin
text {
     onTouchDown = {
        Toast.makeText(this@FormActivity, this.javaClass.simpleName, LENGTH_SHORT).show()
    }
}
```

## On Title Icon Click Unit
This unit is invoked when the title's icon is clicked.
```kotlin
text {
     onTitleIconClick = {
        Toast.makeText(this@FormActivity, "Icon clicked", LENGTH_SHORT).show()
    }
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

## Update On Focus Change
By default this is false.
An EditText will update the form value as characters are typed.
Setting this to true will only update the value when focus is lost or if Done is tapped on the keyboard. (See IME Options)
```kotlin
text {
    updateOnFocusChange = true
}
```

## Input Type
By default this is set by the form element type.
Setting this will override the EditText form element's Input Type.
```kotlin
text {
    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
}
```

## IME Options
By default this is EditorInfo.IME_ACTION_NEXT.
Setting this will override the EditText form element's IME Options.
If EditorInfo.IME_ACTION_DONE is used, tapping Done on the keyboard will update the form element value.
```kotlin
text {
    imeOptions = EditorInfo.IME_ACTION_DONE
}
```

## Input Mask Options
By default edit text fields will not have any input mask format. Setting an input mask format will format the text field for the set value and as you type.
```kotlin
phone {
    inputMaskOptions = InputMaskOptions(primaryFormat = "+1 ([000]) [000]-[0000]")
}
```

## Hint
The hint is the hint (or placeholder) of the value field.
```kotlin
text {
    hint = "Enter a name"
}
```

## Max Length
By default this is null which means that there is no max text length.
Max length represents the number of characters the value field will allow.

!!! error "Unsupported Fields"

    * AutoComplete
    * AutoCompleteToken

```kotlin
text {
    maxLength = 10
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

## Edit View Gravity
By default, this is Gravity.END (Right to left).
This determines the gravity of the text in the value field, button, or header.
```kotlin
text {
    editViewGravity = Gravity.START
}
```

## Edit View Paint Flags
By default, this is null (No flags).
This determines the extra paint flags for the edit view.
```kotlin
text {
    editViewPaintFlags = Paint.UNDERLINE_TEXT_FLAG
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

## Clearable
By default this is false.
Setting this to true will display a clear button (X) to set the value to null.

!!! error "Unsupported Fields"

    * AutoComplete
    * AutoCompleteToken

```kotlin
text {
    clearable = true
}
```

## Title Icon
By default this is null.
Setting this will set and display the title icon drawable (null will hide the icon).

!!! note "Button"

    Setting this for the button form element will add the icon to the button.

```kotlin
text {
    titleIcon = ContextCompat.getDrawable(this@FormActivity, R.drawable.ic_email_blue_24dp)
}
```

## Title Icon Location
By default this is IconTextView.Location.LEFT.
Setting this set the title icon location (LEFT or RIGHT of the title).

```kotlin
text {
    titleIconLocation = IconTextView.Location.RIGHT
}
```

## Title Icon Padding
By default this is 20.
Setting this set the padding between the title text and icon.

```kotlin
text {
    titleIconPadding = 25
}
```

## Clear On Focus
By default this is false.
Setting this to true will clear the text value of the form element when focused.

!!! error "Unsupported Fields"

    * AutoComplete
    * AutoCompleteToken

```kotlin
text {
    clearOnFocus = true
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

## Clickable
By default this is true.
Setting clickable to false will disable click events.

!!! info "Use focusable"

    Click events will still be fired unless focusable is also set to false.

```kotlin
text {
    clickable = false
}
```

## Focusable
By default this is true.
Setting focusable to false will prevent a field from being focused.

!!! info "Focusable elements"

    This value does not change the behavior of the form elements that cannot be focused.

```kotlin
text {
    focusable = false
}
```

## Display Divider
By default this is true.
Setting displayDivider to false will hide the divider line displayed before the element.
```kotlin
text {
    displayDivider = true
}
```

## Display Title
By default this is true.
Setting displayTitle to false will hide the title.
```kotlin
text {
    displayTitle = true
}
```

## Layout Padding Bottom (DP)
By default, this will use android:paddingBottom in the XML.
Setting layoutPaddingBottom will override the bottom padding of the form element.
```kotlin
text {
    layoutPaddingBottom = 0
}
```

## Title Padding (DP)
Setting padding will override the padding of the form element title view.

!!! info "FormElementPadding"

    FormElementPadding is a simple class to set all padding.

```kotlin
text {
    titlePadding = FormElementPadding(/* Left */ 16, /* Top */ 16, /* Right */ 16, /* Bottom */ 0)
}
```

## Padding (DP)
Setting padding will override the padding of the form element edit view.

!!! info "FormElementPadding"

    FormElementPadding is a simple class to set all padding.

```kotlin
text {
    padding = FormElementPadding(/* Left */ 16, /* Top */ 16, /* Right */ 16, /* Bottom */ 0)
}
```

## Margins (DP)
By default, this will use layout_margin values in the XML.
Setting margins will override the margins of the form element.

!!! info "FormElementMargins"

    FormElementMargins is a simple class to set all margins.

```kotlin
text {
    margins = FormElementMargins(/* Left */ 16, /* Top */ 16, /* Right */ 16, /* Bottom */ 0)
}
```

## Confirm Edit
By default this is false.
Setting confirmEdit to true will display an alert dialog confirming if the user wishes to edit the value if a current value exists.
Currently, this only applies to the picker elements that display a dialog.
```kotlin
date {
    confirmEdit = true
}
```

## Confirm Title
By default this is "Confirm Edit".
Setting this will override the confirm edit dialog title.
```kotlin
date {
    confirmTitle = "Really edit this?"
}
```

## Confirm Message
By default this is "Edit this value?".
Setting this will override the confirm edit dialog message.
```kotlin
date {
    confirmMessage = "Are you ABSOLUTELY sure?"
}
```

## Background Color
By default this is the value of R.Color.colorFormMasterElementBackground.
Setting this will override the background color.
```kotlin
text {
    backgroundColor = Color.WHITE
}
```

## Title Text Color
By default this is the value of R.Color.colorFormMasterElementTextTitle.
Setting this will override the title text color.
```kotlin
text {
    titleTextColor = Color.BLACK
}
```

## Title Focused Text Color
By default this is the value of R.Color.colorFormMasterElementFocusedTitle.
Setting this will override the title text (when focused) color.
```kotlin
text {
    titleFocusedTextColor = Color.BLACK
}
```

## Value Text Color
By default this is the value of R.Color.colorFormMasterElementTextValue.
Setting this will override the value text color.
```kotlin
text {
    valueTextColor = Color.BLACK
}
```

## Error Text Color
By default this is the value of R.Color.colorFormMasterElementErrorTitle.
Setting this will override the error text color.
```kotlin
text {
    errorTextColor = Color.BLACK
}
```

## Hint Text Color
By default this is the value of R.Color.colorFormMasterElementHint.
Setting this will override the hint text color.
```kotlin
text {
    hintTextColor = Color.BLACK
}
```

## Clear value
Clear the value of the element.
```kotlin
element.clear()
```

## Is the value valid?
isValid contains a getter that checks if the element is valid. At the base level, this checks if the value is not null or empty.
This is used by formBuilder.isValidForm.
```kotlin
element.isValid
```

## Custom Validation
To add custom validation check, assign a lambda to validityCheck.
```kotlin
text {
    validityCheck = {
        value?.matches("some regex".toRegex()) == true
    }
}
```

## Display New Value
This function is called when the value changes to display the new value specific to the form element.
By default, this handles setting the text of the AppCompatEditText view.
```kotlin
element.displayNewValue()
```

## Dynamic Views

!!! note "NOTICE"

    The Item View, Title View, Edit View, and Error View are accessible from the model to support changing variables such as the title, value, visibility, and error. However, try not to use the views directly. Let the variable setters do the work for you. Also, the views cannot be modified during the creation of the form elements as they are initialized during the render of the RecyclerView.