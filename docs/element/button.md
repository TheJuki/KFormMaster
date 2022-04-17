# Button

The Button form element is used for a button row. Click events can be caught with a valueObserver.

![Example](../images/Button.PNG)

## Title Icon
By default this is null.
Setting this will set and display the title icon drawable (null will hide the icon).

!!! note "Button"

    Setting this for the button form element will add the icon to the button.

```kotlin
button {
    titleIcon = ContextCompat.getDrawable(this@FormActivity, R.drawable.ic_email_blue_24dp)
}
```

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
FormButtonElement button = new FormButtonElement(1);
button.setValue("Button Text");
button.getValueObservers().add((newValue, element) -> {
    // Nothing was changed but the button was tapped!
    return Unit.INSTANCE;
});
elements.add(button);
```
