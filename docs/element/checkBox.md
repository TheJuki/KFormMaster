# CheckBox

The CheckBox form element is used for a checkbox row.

![Example](../images/CheckBox.PNG)

## Checked and UnChecked Values

The value is set to checkedValue when checked. The value is set to unCheckedValue when unChecked.

## Example

### Kotlin

```kotlin
checkBox<Boolean>(1) {
    value = true
    checkedValue = true
    unCheckedValue = false
}
```

### Java

```java
List<BaseFormElement<?>> elements = new ArrayList<>();
FormCheckBoxElement<Boolean> checkBox = new FormCheckBoxElement<>(1);
checkBox.setValue(true);
checkBox.setCheckedValue(true);
checkBox.setUnCheckedValue(false);
elements.add(checkBox);
```
