package com.thejuki.kformmaster.item

/**
 * Contact Item
 *
 * An example class used for autoComplete and autoCompleteToken
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
data class ContactItem(val id: Long? = null,
                       val value: String? = null,
                       val label: String? = null
) {
    override fun toString(): String {
        return label.orEmpty()
    }
}