The Slider form element is used for a slider (SeekBar) row.

## Example

### Kotlin
```kotlin
slider(1) {
    value = 50
    min = 0
    max = 100
    steps = 20
}
```

### Java
```java
List<BaseFormElement<?>> elements = new ArrayList<>();
SliderBuilder slider = new SliderBuilder(1);
slider.setValue(50);
slider.setMin(0);
slider.setMax(100);
slider.setSteps(20);
elements.add(slider.build());
```