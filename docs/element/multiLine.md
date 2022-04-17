# Multi Line

The Multi Line form element is used for a multi line value field.

![Example](../images/MultiLine.PNG)

## Example

### Kotlin

```kotlin
textArea(1) {
    value = "Example"
    maxLines = 3
}
```

### Java

```java
List<BaseFormElement<?>> elements = new ArrayList<>();
FormMultiLineEditTextElement textArea = new FormMultiLineEditTextElement(1);
textArea.setValue("Example");
textArea.setMaxLines(3);
elements.add(textArea);
```
