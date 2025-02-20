package com.example.listify.data

import android.util.Base64
import com.example.listify.data.database.api.AAArtist
import com.example.listify.network.SpotifyApiService

interface SpotifyRepository {
    suspend fun getAccessToken(): String
    suspend fun getArtistDiscography(artistId: String): List<Album>
    suspend fun searchArtist( artistName: String): List<AAArtist>
}

class NetworkSpotifyRepository(private val tokenApiService: SpotifyApiService, private val spotifyApiService: SpotifyApiService) : SpotifyRepository {

    private val credentials = "da2899ca0ad54491822a6847aa112552:77d7265cfd8d40719f4089cf8de1b760"
    private val encodedCredentials: String = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)

    private var accessTokenData = AccessTokenData(null, 0, 0)


    override suspend fun getArtistDiscography(artistId: String): List<Album> {

        val allAlbums = mutableListOf<Album>()
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

        return allAlbums
    }

    override suspend fun searchArtist(artistName: String): List<AAArtist> {
        val token = getAccessToken()
        val response = spotifyApiService.getSearchArtist("Bearer $token", artistName)
        return response.artists.items
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