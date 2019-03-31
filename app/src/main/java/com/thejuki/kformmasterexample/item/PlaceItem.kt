package com.thejuki.kformmasterexample.item

import android.net.Uri
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.libraries.places.api.model.OpeningHours
import com.google.android.libraries.places.api.model.PhotoMetadata
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlusCode

/**
 * Place Item
 *
 * An example class used for placesAutoComplete.
 *
 * This class can be instantiated and toString can be overridden to display text in the textfield.
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
data class PlaceItem(val id: String? = null,
                     val name: String? = null,
                     val address: String? = null,
                     val attributions: List<String>? = null,
                     val latLng: LatLng? = null,
                     val phoneNumber: String? = null,
                     val openingHours: OpeningHours? = null,
                     val photoMetadatas: List<PhotoMetadata>? = null,
                     val plusCode: PlusCode? = null,
                     val priceLevel: Int? = null,
                     val rating: Double? = null,
                     val types: List<Place.Type>? = null,
                     val userRatingsTotal: Int? = null,
                     val viewport: LatLngBounds? = null,
                     val websiteUri: Uri? = null
) {

    // Text that is displayed in the textfield
    override fun toString(): String {
        return name.orEmpty()
    }

    constructor(place: Place) : this(
            place.id,
            place.name,
            place.address,
            place.attributions,
            place.latLng,
            place.phoneNumber,
            place.openingHours,
            place.photoMetadatas,
            place.plusCode,
            place.priceLevel,
            place.rating,
            place.types,
            place.userRatingsTotal,
            place.viewport,
            place.websiteUri
    )
}