The Button form element is used for a button row. Click events can be caught with a valueObserver.

![Example](/images/Button.PNG)

## Example

### Kotlin
```kotlin
button(1) {
    value = "Button Text"
    valueObservers.add({ _, _ ->
        // Nothing was changed but the button was tapped!
    })
}
```

### Java
```java
List<BaseFormElement<?>> elements = new ArrayList<>();
ButtonBuilder button = new ButtonBuilder(1);
button.setValue("Button Text");
button.getValueObservers().add((newValue, element) -> {
    // Nothing was changed but the button was tapped!
    return Unit.INSTANCE;
});
elements.add(button.build());
```