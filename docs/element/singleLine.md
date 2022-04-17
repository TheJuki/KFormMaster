# Single Line

The Single Line form element is used for a single line value field.

![Example](../images/SingleLine.PNG)

## Example

### Kotlin

```kotlin
text(1) {
    value = "Example"
}
```

### Java

```java
List<BaseFormElement<?>> elements = new ArrayList<>();
FormSingleLineEditTextElement text = new FormSingleLineEditTextElement(1);
text.setValue("Example");
elements.add(text);
```
