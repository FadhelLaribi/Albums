package fr.lbc.albums.data.model.mapper

import fr.lbc.albums.data.model.Album
import fr.lbc.albums.data.model.entity.AlbumEntity
import fr.lbc.albums.data.model.to.AlbumTo

fun AlbumEntity.toAlbum() = Album(id, title, url)

fun AlbumTo.toAlbumEntity() = AlbumEntity(id, albumId, title, url, thumbnailUrl)

fun AlbumTo.toAlbum() = Album(id, title, url)