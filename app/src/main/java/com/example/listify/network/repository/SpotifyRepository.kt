package com.example.listify.network.repository

import android.util.Base64
import android.util.Log
import com.example.listify.network.api.SpotifyApiService
import com.example.listify.network.converters.toArtistUi
import com.example.listify.network.model.AlbumDto
import com.example.listify.network.model.ArtistListDto
import com.example.listify.ui.model.ArtistUi


class NetworkSpotifyRepository(
    private val tokenApiService: SpotifyApiService,
    private val spotifyApiService: SpotifyApiService
) {

    private val credentials = "da2899ca0ad54491822a6847aa112552:77d7265cfd8d40719f4089cf8de1b760"
    private val encodedCredentials: String = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)

    private var accessTokenData = AccessTokenData(null, 0, 0)

    suspend fun getArtistDiscography(artistId: String): List<AlbumDto> {

        val allAlbums = mutableListOf<AlbumDto>()
        var nextUrl: String? = null

        do {
             refreshAccessToken()

            val response = if (nextUrl == null) {
                // First request
                spotifyApiService.getArtistAlbums(
                    authorization = "Bearer ${accessTokenData.accessToken}",
                    artistId = artistId,
                    includeGroups = "album"
                )
            } else {
                // Next pages use direct URL
                spotifyApiService.getNextPage(
                    authorization = "Bearer ${accessTokenData.accessToken}",
                    url = nextUrl
                )
            }

            allAlbums.addAll(response.items)
            nextUrl = response.next // Update next page URL

        } while (nextUrl != null)

        return allAlbums.map { it }
    }

    suspend fun getArtistsInfo(artistIds: List<String>): ArtistListDto {

        refreshAccessToken()
        var response = ArtistListDto()

        val idsFormatted = artistIds.joinToString(",")
        try {
             response = spotifyApiService.getArtistsInfo(
                idsFormatted,
                "Bearer ${accessTokenData.accessToken}"
            )
        } catch (e: Exception) {
            Log.d("Error2", "${e.message}")
        }


        return response
    }

    suspend fun searchArtist(artistName: String): List<ArtistUi> {
        refreshAccessToken()
        val response = spotifyApiService.getSearchArtist("Bearer ${accessTokenData.accessToken}", artistName)
        return response.artists.items.map { it.toArtistUi() }
    }

    private suspend fun refreshAccessToken() {
        if (!checkAccessTokenValid()) {
            val accessTokenResponse = tokenApiService.getAccessToken(authorization = "Basic $encodedCredentials")
            accessTokenData = AccessTokenData(accessTokenResponse.accessToken, System.currentTimeMillis(), accessTokenResponse.expiresIn * 1000)
        }
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