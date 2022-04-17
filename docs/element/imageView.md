# Image View

The Image View form element is used to show an image and when tapped display options to get a new image from the camera or gallery app.

<img src="../../images/ImageView1.PNG" alt="Example" width="300px"/>

<img src="../../images/ImageView2.PNG" alt="Example" width="300px"/>

## Example

### Kotlin

```kotlin
private val startImagePickerForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
    val imageViewElement = formBuilder.getFormElement<FormImageElement>(
        Tag.ImageViewElement.ordinal)
    imageViewElement.handleActivityResult(result.resultCode, result.data)
}

imageView(1) {
    activityResultLauncher = startImagePickerForResult
    displayDivider = false
    applyCircleCrop = true
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
    onSelectImage = { uri, error ->
         // If uri is null, that means an error occurred trying to select the image
         if (uri != null) {
             Toast.makeText(this@YourActivity, uri.path, LENGTH_SHORT).show()
         } else {
             Toast.makeText(this@YourActivity, error, LENGTH_LONG).show()
         }
     }
}
```

### Java

```java
private ActivityResultLauncher<Intent> startImagePickerForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
    new ActivityResultCallback<>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            FormImageElement imageViewElement = formBuilder.getFormElement(Tag.ImageViewElement.ordinal());
            imageViewElement.handleActivityResult(result.getResultCode(), result.getData());
        }
    });

List<BaseFormElement<?>> elements = new ArrayList<>();
FormImageElement imageView = new FormImageElement(1);
imageView.setActivityResultLauncher(startImagePickerForResult);
imageView.setOnSelectImage((uri, error) -> {
    if (uri != null) {
        Toast.makeText(this, uri.getPath(), Toast.LENGTH_SHORT).show();
    } else {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
    return Unit.INSTANCE;
});
elements.add(imageView);
```
