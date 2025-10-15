package com.example.bitfitproj5


import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class FoodResponse(
    @SerialName("data")
    val data: List<Food>?
)

// Data class representing a campground
@Keep
@Serializable
data class Food(
    @SerialName("name")
    val name: String?,
    @SerialName("calories")
    val calories: String?,
)