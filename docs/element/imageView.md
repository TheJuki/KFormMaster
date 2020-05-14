The Image View form element is used to show an image and when tapped display options to get a new image from the camera or gallery app.

<img src="../../images/ImageView1.PNG" alt="Example" width="300px"/>

<img src="../../images/ImageView2.PNG" alt="Example" width="300px"/>

## Example

### Kotlin

```kotlin
imageView(1) {
    displayDivider = false
    imageTransformation = CircleTransform(borderColor = Color.WHITE, borderRadius = 3)
    required = false
    showChangeImageLabel = true
    changeImageLabel = "Change me"
    displayImageHeight = 200
    displayImageWidth = 200
    theme = R.style.CustomDialogPicker
    defaultImage = R.drawable.default_image
    value = "http://example.com/"
    imagePickerOptions = {
        it.cropX = 3f
        it.cropY = 4f
        it.maxWidth = 150
        it.maxHeight = 200
        it.maxSize = 500
    }
     onSelectImage = { file ->
        // If file is null, that means an error occurred trying to select the image
        if (file != null) {
            Toast.makeText(context, file.name, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Error getting the image", Toast.LENGTH_LONG).show()
        }
    }
}
```

### Java

```java
List<BaseFormElement<?>> elements = new ArrayList<>();
FormImageElement imageView = new FormImageElement(1);
imageView.setOnSelectImage((file) -> {
    if (file != null) {
        Toast.makeText(this, file.getName(), Toast.LENGTH_SHORT).show();
    } else {
        Toast.makeText(this, "Error getting the image", Toast.LENGTH_LONG).show();
    }
    return Unit.INSTANCE;
});
elements.add(imageView);
```
