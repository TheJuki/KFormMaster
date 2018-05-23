The Header element is used as a header row.

![Example](/images/Header.PNG)

## Collapsible
By default this is false.
Setting this to true will allow the form header to "collapse/uncollapse" (Hide/Show) elements below it when tapped.
```kotlin
header { collapsible = true }
```

## Example

### Kotlin
```kotlin
header { title = "I'm a header row"; collapsible = true }
```

### Java
```java
List<BaseFormElement<?>> elements = new ArrayList<>();
HeaderBuilder header = new HeaderBuilder("I'm a header row");
elements.add(header.build());
```