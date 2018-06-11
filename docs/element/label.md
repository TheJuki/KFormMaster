The Label form element is used for a title only field.

![Example](/images/Label.PNG)

## Example

### Kotlin
```kotlin
label(1) {
    title = "This is a label. The title takes up the whole row."
}
```

### Java
```java
List<BaseFormElement<?>> elements = new ArrayList<>();
LabelBuilder label = new LabelBuilder(1);
label.setTitle("This is a label. The title takes up the whole row.");
elements.add(label.build());
```