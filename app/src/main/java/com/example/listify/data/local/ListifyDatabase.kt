package com.example.listify.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.listify.data.model.ArtistEntity
import com.example.listify.data.model.AlbumEntity
import com.example.listify.data.model.ArtistAlbumCrossRefEntity
import com.example.listify.data.model.ArtistGenreCrossRefEntity
import com.example.listify.data.model.GenreAlbumCrossRefEntity

@Database(
    entities = [
        AlbumEntity::class,
        ArtistEntity::class,
        ArtistAlbumCrossRefEntity::class,
        GenreAlbumCrossRefEntity::class,
        ArtistGenreCrossRefEntity::class
               ],
    version = 7,
    exportSchema = false
)
abstract class ListifyDatabase: RoomDatabase() {

    abstract fun albumDao(): AlbumDao
    abstract fun artistDao(): ArtistDao
    abstract fun artistAlbumCrossRefDao(): ArtistAlbumCrossDao
    abstract fun artistGenreCrossRefDao(): ArtistGenreCrossDao

    companion object {
        @Volatile
        private var Instance: ListifyDatabase? = null

        fun getDatabase(context: Context): ListifyDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ListifyDatabase::class.java, "listify_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it}
            }
        }
    }
}