package com.example.listify.network.repository

import android.util.Base64
import com.example.listify.network.api.SpotifyApiService
import com.example.listify.network.converters.toAlbumUi
import com.example.listify.network.converters.toArtistUi
import com.example.listify.network.model.AlbumDto
import com.example.listify.ui.model.AlbumUi
import com.example.listify.ui.model.ArtistUi

interface SpotifyRepository {
    suspend fun getAccessToken(): String
    suspend fun getArtistDiscography(artistId: String): List<AlbumUi>
    suspend fun searchArtist( artistName: String): List<ArtistUi>
}

class NetworkSpotifyRepository(private val tokenApiService: SpotifyApiService, private val spotifyApiService: SpotifyApiService) :
    SpotifyRepository {

    private val credentials = "da2899ca0ad54491822a6847aa112552:77d7265cfd8d40719f4089cf8de1b760"
    private val encodedCredentials: String = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)

    private var accessTokenData = AccessTokenData(null, 0, 0)


    override suspend fun getArtistDiscography(artistId: String): List<AlbumUi> {

        val allAlbums = mutableListOf<AlbumDto>()
        var nextUrl: String? = null

        var token: String

        do {
            token = getAccessToken()

            val response = if (nextUrl == null) {
                // First request
                spotifyApiService.getArtistAlbums(
                    authorization = "Bearer $token",
                    artistId = artistId,
                    limit = 10,  // Adjust limit as needed
                    offset = 0,
                    includeGroups = "album"
                )
            } else {
                // Next pages use direct URL
                spotifyApiService.getNextPage(
                    authorization = "Bearer $token",
                    url = nextUrl
                )
            }

            // Add albums from this response
            allAlbums.addAll(response.items)
            nextUrl = response.next // Update next page URL

        } while (nextUrl != null)

        return allAlbums.map { it.toAlbumUi() }
    }

    override suspend fun searchArtist(artistName: String): List<ArtistUi> {
        val token = getAccessToken()
        val response = spotifyApiService.getSearchArtist("Bearer $token", artistName)
        return response.artists.items.map { it.toArtistUi() }
    }

    override suspend fun getAccessToken(): String {

        if (!checkAccessTokenValid()) {
            val accessTokenResponse = tokenApiService.getAccessToken(authorization = "Basic $encodedCredentials")
            accessTokenData = AccessTokenData(accessTokenResponse.accessToken, System.currentTimeMillis(), accessTokenResponse.expiresIn * 1000)
        }
        return accessTokenData.accessToken!!
    }

    private fun checkAccessTokenValid(): Boolean {
        if (accessTokenData.accessToken == null) return false
        if (System.currentTimeMillis() - accessTokenData.lastTimeStamp > accessTokenData.expTime) return false
        return true
    }
}

data class AccessTokenData(
    val accessToken: String?,
    val lastTimeStamp: Long,
    val expTime: Int
)