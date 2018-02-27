package com.thejuki.kformmaster.item

import java.io.Serializable

data class ListItem(val id: Long? = null,
                    val name: String? = null
) : Serializable {
    override fun toString(): String {
        return name.orEmpty()
    }
}