# Text View

The Text View form element is used for a readonly text value field.

![Example](../images/TextView.PNG)

## Example

### Kotlin

```kotlin
textView(1) {
    value = "Example"
}
```

### Java

```java
List<BaseFormElement<?>> elements = new ArrayList<>();
FormTextViewElement textView = new FormTextViewElement(1);
textView.setValue("Example");
elements.add(textView);
```
