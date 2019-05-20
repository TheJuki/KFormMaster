package com.thejuki.kformmaster.helper

/**
 * Image Picker Options
 *
 * Used to set the image picker options
 *
 * @author **soareseneves** ([GitHub](https://github.com/soareseneves))
 * @version 1.0
 */
class ImagePickerOptions {
    /**
     * Set an aspect ratio for crop bounds.
     * User won't see the menu with other ratios options.
     *
     * cropX aspect ratio X
     * cropY aspect ratio Y
     */
    var cropX: Float = 0f
    var cropY: Float = 0f

    /**
     * Max Width and Height of final image
     */
    var maxWidth: Int = 0
    var maxHeight: Int = 0

    /**
     * Compress Image so that max image size can be below specified size
     *
     * maxSize Size in KB
     */
    var maxSize: Int = 0
}