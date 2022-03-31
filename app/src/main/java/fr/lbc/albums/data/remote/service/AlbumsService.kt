package fr.lbc.albums.data.remote.service

import fr.lbc.albums.data.model.to.AlbumTo
import retrofit2.http.GET

interface AlbumsService {

    @GET("img/shared/technical-test.json")
    suspend fun getAlbums(): List<AlbumTo>

}