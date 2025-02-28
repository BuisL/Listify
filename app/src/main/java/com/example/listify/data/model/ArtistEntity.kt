package com.example.listify.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artist")
data class ArtistEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "artist_name") val artistName: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "completion_percentage") val completionPercent: Float,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean,
    @ColumnInfo(name = "rating") val rating: Int,
    @ColumnInfo(name = "note") val note: String,
) {
    init {
        require(completionPercent in 0.0..1.0) {
            "completionPercentage must be between 0 and 1"
        }
        require(rating in 0..10) {
            "completionPercentage must be between 1 and 10"
        }
    }
}