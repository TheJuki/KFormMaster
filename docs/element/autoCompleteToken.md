The AutoCompleteToken form element is used for an AutoComplete row with multiple values represented as tokens.

## Example

### Kotlin
```kotlin
autoCompleteToken<ArrayList<ContactItem>>(1) {
    arrayAdapter = EmailAutoCompleteAdapter(this@FormActivity,
                        android.R.layout.simple_list_item_1)
    dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
    value = arrayListOf(ContactItem(id = 1, value = "John.Smith@mail.com", label = "John Smith (Tester)"))
}
```

### Java
```java
List<BaseFormElement<?>> elements = new ArrayList<>();
AutoCompleteTokenBuilder<ContactItem> autoCompleteToken = new AutoCompleteTokenBuilder<>(1);
autoCompleteToken.setArrayAdapter(new EmailAutoCompleteAdapter(this,
    android.R.layout.simple_list_item_1));
autoCompleteToken.setDropdownWidth(ViewGroup.LayoutParams.MATCH_PARENT);
elements.add(autoCompleteToken.build());
```