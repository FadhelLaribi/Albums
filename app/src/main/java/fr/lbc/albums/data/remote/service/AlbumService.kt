package fr.lbc.albums.data.remote.service

import fr.lbc.albums.data.model.Album
import retrofit2.http.GET

interface AlbumService {

    @GET("img/shared/technical-test.json")
    suspend fun getAlbums(): List<Album>

}