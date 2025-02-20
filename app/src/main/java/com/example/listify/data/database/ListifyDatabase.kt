package com.example.listify.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.listify.data.database.album.AlbumDao
import com.example.listify.data.database.album.AlbumEntity
import com.example.listify.data.database.artist.ArtistDao
import com.example.listify.data.database.artist.ArtistEntity

@Database(entities = [AlbumEntity::class, ArtistEntity::class, ArtistAlbumCrossRef::class, GenreAlbumCrossRef::class], version = 4, exportSchema = false)
abstract class ListifyDatabase: RoomDatabase() {

    abstract fun albumDao(): AlbumDao
    abstract fun artistDao(): ArtistDao
    abstract fun artistAlbumCrossRefDao(): ArtistAlbumCrossDao

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