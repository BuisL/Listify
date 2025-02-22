package com.example.listify.network.api

import com.example.listify.network.model.AccessTokenDto
import com.example.listify.network.model.ArtistAlbumsDto
import com.example.listify.network.model.SearchArtistDto
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url


interface SpotifyApiService {

    @FormUrlEncoded
    @POST("/api/token")
    suspend fun getAccessToken(
        @Header("Authorization") authorization: String, // Base64 encoded "ClientID:ClientSecret"
        @Field("grant_type") grantType: String = "client_credentials"
    ): AccessTokenDto

    @GET("/v1/artists/{id}/albums")
    suspend fun getArtistAlbums(
        @Header("Authorization") authorization: String, // Bearer token
        @Path("id") artistId: String,
        @Query("limit") limit: Int? = 10, // Optional query parameter for pagination
        @Query("offset") offset: Int? = 0, // Optional query parameter for pagination
        @Query("include_groups") includeGroups: String? = null,
    ): ArtistAlbumsDto

    @GET("/v1/search")
    suspend fun getSearchArtist(
        @Header("Authorization") authorization: String, // Bearer token
        @Query("q") query: String,  // The artist's name
        @Query("type") type: String = "artist", // Searching only for artists
        @Query("limit") limit: Int? = null, // Optional: Limit the number of results
        @Query("offset") offset: Int? = null // Optional: Pagination offset
    ): SearchArtistDto

    @GET
    suspend fun getNextPage(
        @Header("Authorization") authorization: String,
        @Url url: String // Use @Url to request the next page dynamically
    ): ArtistAlbumsDto

}