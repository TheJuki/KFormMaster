The AutoComplete form element is used for an AutoComplete row.

## Example

### Kotlin
```kotlin
autoComplete<ContactItem>(1) {
    arrayAdapter = ContactAutoCompleteAdapter(this@FullscreenFormActivity,
                        android.R.layout.simple_list_item_1, defaultItems =
                            arrayListOf(ContactItem(id = 1, value = "", label = "Try \"Apple May\"")))
    dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
    value = ContactItem(id = 1, value = "John Smith", label = "John Smith (Tester)")
}
```

### Java
```java
List<BaseFormElement<?>> elements = new ArrayList<>();
AutoCompleteBuilder<ContactItem> autoComplete = new AutoCompleteBuilder<>(1);
autoComplete.setArrayAdapter(new ContactAutoCompleteAdapter(this,
    android.R.layout.simple_list_item_1,
    new ArrayList<>(Collections.singletonList(new ContactItem(1L, "", "Try \"Apple May\"")))));
autoComplete.setDropdownWidth(ViewGroup.LayoutParams.MATCH_PARENT);
autoComplete.setValue(new ContactItem(1L, "John Smith", "John Smith (Tester)"));
elements.add(autoComplete.build());
```