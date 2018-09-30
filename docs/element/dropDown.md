The DropDown form element is used for a single choice dialog.

![Example](/images/Dropdown1.PNG)

<img src="../../images/Dropdown2.PNG" alt="Example" width="300px"/>

## Array Adapter
Set your custom AutoComplete adapter. It is up to you if you want to retrieve items through an API call.

!!! note "NOTICE"

    The ListItem, ContactItem, and ContactAutoCompleteAdapter classes are provided in the example app. Your custom class just needs to override toString() to display in the dialog.

```kotlin
dropDown<ContactItem>(1) {
    arrayAdapter = ContactAutoCompleteAdapter(this@FormActivity,
                        android.R.layout.simple_list_item_1)
}
```

## displayRadioButtons
By default this is false.
Enable to display the radio buttons
```kotlin
dropDown<ListItem>(1) {
    displayRadioButtons = true
}
```

## Dialog Title
By default this is "Select One".
Setting this will override the alert dialog title.
```kotlin
dropDown<ListItem>(1) {
    dialogTitle = "Select One"
}
```

## Dialog Empty Message
By default this is "Empty".
Setting this will override the alert dialog message when the options list is empty or null.
```kotlin
dropDown<ListItem>(1) {
    dialogEmptyMessage = "Nothing to see here"
}
```

## Example

### Kotlin
```kotlin
dropDown<ListItem>(1) {
    dialogTitle = "Select One"
    options = listOf(ListItem(id = 1, name = "Banana"), 
                     ListItem(id = 2, name = "Orange"))
    value = ListItem(id = 1, name = "Banana")
}
```

### Java
```java
DropDownBuilder<ListItem> dropDown = new DropDownBuilder<>(1);
dropDown.setDialogTitle("Select One");
dropDown.setOptions(Arrays.asList(new ListItem(1L, "Banana"),
    new ListItem(2L, "Orange")));
dropDown.setValue(new ListItem(1L, "Banana"));
elements.add(dropDown.build());
```