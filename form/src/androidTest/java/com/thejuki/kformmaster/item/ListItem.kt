package com.thejuki.kformmaster.item

/**
 * List Item
 *
 * An example class used for dropDown and multiCheckBox
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
data class ListItem(val id: Long? = null,
                    val name: String? = null
) {
    override fun toString(): String {
        return name.orEmpty()
    }
}