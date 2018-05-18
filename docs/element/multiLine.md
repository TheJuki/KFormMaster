The Multi Line form element is used for a multi line value field.

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
MultiLineEditTextBuilder textArea = new MultiLineEditTextBuilder(1);
textArea.setValue("Example");
textArea.setMaxLines(3);
elements.add(textArea.build());
```