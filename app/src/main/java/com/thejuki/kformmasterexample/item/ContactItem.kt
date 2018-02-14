package com.thejuki.kformmasterexample.item

import java.io.Serializable

data class ContactItem(val id: Long? = null,
                       val value: String? = null,
                       val label: String? = null
): Serializable {
    override fun toString(): String {
        return label.orEmpty()
    }
}