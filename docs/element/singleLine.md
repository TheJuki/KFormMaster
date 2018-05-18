The Single Line form element is used for a single line value field.

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
SingleLineEditTextBuilder text = new SingleLineEditTextBuilder(1);
text.setValue("Example");
elements.add(text.build());
```