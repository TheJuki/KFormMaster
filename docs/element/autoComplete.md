# AutoComplete

The AutoComplete form element is used for an AutoComplete row.

![Example](../images/Autocomplete1.PNG)

![Example](../images/Autocomplete2.PNG)

## Array Adapter

Set your custom AutoComplete adapter. It is up to you if you want to retrieve items through an API call.

!!! note "NOTICE"

    The ContactItem and ContactAutoCompleteAdapter classes are provided in the example app. Your custom class just needs to override toString() to display in the dropdown.

```kotlin
autoComplete<ContactItem>(1) {
    arrayAdapter = ContactAutoCompleteAdapter(this@FormActivity,
                        android.R.layout.simple_list_item_1)
}
```

## Dropdown Width

This is the width of the dropdown list. ViewGroup.LayoutParams.MATCH_PARENT will use the form's width.

```kotlin
autoComplete<ContactItem>(1) {
    dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
}
```

## Example

### Kotlin

```kotlin
autoComplete<ContactItem>(1) {
    arrayAdapter = ContactAutoCompleteAdapter(this@FormActivity,
                        android.R.layout.simple_list_item_1, defaultItems =
                            arrayListOf(ContactItem(id = 1, value = "", label = "Try \"Apple May\"")))
    dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
    value = ContactItem(id = 1, value = "John Smith", label = "John Smith (Tester)")
}
```

### Java

```java
List<BaseFormElement<?>> elements = new ArrayList<>();
FormAutoCompleteElement<ContactItem> autoComplete = new FormAutoCompleteElement<>(1);
autoComplete.setArrayAdapter(new ContactAutoCompleteAdapter(this,
    android.R.layout.simple_list_item_1,
    new ArrayList<>(Collections.singletonList(new ContactItem(1L, "", "Try \"Apple May\"")))));
autoComplete.setDropdownWidth(ViewGroup.LayoutParams.MATCH_PARENT);
autoComplete.setValue(new ContactItem(1L, "John Smith", "John Smith (Tester)"));
elements.add(autoComplete);
```
