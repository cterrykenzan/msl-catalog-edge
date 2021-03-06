/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.catalog.edge.services.impl;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.kenzan.msl.catalog.edge.services.AlbumService;
import com.kenzan.msl.catalog.edge.services.ArtistService;
import com.kenzan.msl.catalog.edge.services.CatalogEdgeService;
import com.kenzan.msl.catalog.edge.services.SongService;
import com.kenzan.msl.common.bo.AlbumBo;
import com.kenzan.msl.common.bo.ArtistBo;
import com.kenzan.msl.common.bo.SongBo;
import com.kenzan.msl.catalog.edge.translate.Translators;

import java.util.UUID;

import io.swagger.model.AlbumInfo;
import io.swagger.model.AlbumList;
import io.swagger.model.ArtistInfo;
import io.swagger.model.ArtistList;
import io.swagger.model.SongInfo;
import io.swagger.model.SongList;

import org.apache.commons.lang3.StringUtils;

import rx.Observable;

/**
 * @author kenzan Implementation of the CatalogEdgeService interface that retrieves its data from a
 *         Cassandra cluster.
 */
public class CatalogEdgeServiceImpl implements CatalogEdgeService {

  private AlbumService albumsService;
  private ArtistService artistsService;
  private SongService songsService;

  @Inject
  public CatalogEdgeServiceImpl(final AlbumService albumsService,
      final ArtistService artistsService, final SongService songsService) {
    this.albumsService = albumsService;
    this.artistsService = artistsService;
    this.songsService = songsService;
  }

  // ==========================================================================================================
  // ALBUMS
  // =================================================================================================================

  /**
   * Get browsing data for albums in the catalog.
   * <p>
   * This is a paginated query - it returns data one page at a time. The first page is retrieved by
   * passing <code>null</code> as the <code>pagingState</code>. Subsequent pages are retrieved by
   * passing the <code>pagingState</code> that accompanied the previously retrieved page.
   * <p>
   * The page size is determined by the <code>items</code> parameter when retrieving the first page.
   * This value is used for all subsequent pages, (the <code>items</code> parameter is ignored when
   * retrieving subsequent pages).
   * <p>
   * Data can be filtered using the <code>facets</code> parameter when retrieving the first page.
   * This value is used for all subsequent pages, (the <code>facets</code> parameter is ignored when
   * retrieving subsequent pages).
   *
   * @param pagingState Used for pagination control. To retrieve the first page, use
   *        <code>null</code>. To retrieve subsequent pages, use the <code>pagingState</code> that
   *        accompanied the previous page.
   * @param items Specifies the number of items to include in each page. This value is only
   *        necessary on the retrieval of the first page, and will be used for all subsequent pages.
   * @param facets Specifies a comma delimited list of search facet Ids to filter the results. Pass
   *        null or an empty string to not filter.
   * @param userId Specifies a user UUID identifying the currently logged-in user. Will be null for
   *        unauthenticated requests.
   * @return Observable.AlbumList
   */
  public Observable<AlbumList> browseAlbums(String pagingState, Integer items, String facets,
      String userId) {
    Optional<UUID> pagingStateUuid =
        StringUtils.isEmpty(pagingState) ? Optional.absent() : Optional.of(UUID
            .fromString(pagingState));
    Optional<UUID> userUuid =
        StringUtils.isEmpty(userId) ? Optional.absent() : Optional.of(UUID.fromString(userId));

    return Observable.just(Translators.translate(albumsService.getAlbumsList(userUuid, items,
        facets, pagingStateUuid)));
  }

  /**
   * Get data on an album in the catalog.
   *
   * @param albumId Specifies the UUID of the album to retrieve.
   * @param userId Specifies the UUID of the authenticated user.
   * @return Observable&lt;Optional&lt;AlbumInfo&gt;&gt;
   */
  public Observable<Optional<AlbumInfo>> getAlbum(String albumId, String userId) {
    UUID albumUuid = UUID.fromString(albumId);
    Optional<UUID> userUuid =
        null == userId ? Optional.absent() : Optional.of(UUID.fromString(userId));

    Optional<AlbumBo> optAlbumBo = albumsService.getAlbum(userUuid, albumUuid);

    if (!optAlbumBo.isPresent()) {
      return Observable.just(Optional.absent());
    }
    return Observable.just(Optional.of(Translators.translate(optAlbumBo.get())));
  }

  // =========================================================================================================
  // ARTISTS
  // =================================================================================================================

  /**
   * Get browsing data for artists in the catalog.
   * <p>
   * This is a paginated query - it returns data one page at a time. The first page is retrieved by
   * passing <code>null</code> as the <code>pagingState</code>. Subsequent pages are retrieved by
   * passing the <code>pagingState</code> that accompanied the previously retrieved page.
   * <p>
   * The page size is determined by the <code>items</code> parameter when retrieving the first page.
   * This value is used for all subsequent pages, (the <code>items</code> parameter is ignored when
   * retrieving subsequent pages).
   * <p>
   * Data can be filtered using the <code>facets</code> parameter when retrieving the first page.
   * This value is used for all subsequent pages, (the <code>facets</code> parameter is ignored when
   * retrieving subsequent pages).
   *
   * @param pagingState Used for pagination control. To retrieve the first page, use
   *        <code>null</code>. To retrieve subsequent pages, use the <code>pagingState</code> that
   *        accompanied the previous page.
   * @param items Specifies the number of items to include in each page. This value is only
   *        necessary on the retrieval of the first page, and will be used for all subsequent pages.
   * @param facets Specifies a comma delimited list of search facet Ids to filter the results. Pass
   *        null or an empty string to not filter.
   * @param userId Specifies a user UUID identifying the currently logged-in user. Will be null for
   *        unauthenticated requests.
   * @return Observable&lt;ArtistList&gt;
   */
  public Observable<ArtistList> browseArtists(String pagingState, Integer items, String facets,
      String userId) {
    Optional<UUID> pagingStateUuid =
        StringUtils.isEmpty(pagingState) ? Optional.absent() : Optional.of(UUID
            .fromString(pagingState));
    Optional<UUID> userUuid =
        StringUtils.isEmpty(userId) ? Optional.absent() : Optional.of(UUID.fromString(userId));

    return Observable.just(Translators.translate(artistsService.getArtistsList(userUuid, items,
        facets, pagingStateUuid)));
  }

  /**
   * Get data on an artist in the catalog.
   *
   * @param artistId Specifies the UUID of the artist to retrieve.
   * @param userId Specifies the UUID of the authenticated user
   * @return Observable&lt;Optional&lt;ArtistInfo&gt;&gt;
   */
  public Observable<Optional<ArtistInfo>> getArtist(String artistId, String userId) {
    UUID artistUuid = UUID.fromString(artistId);
    Optional<UUID> userUuid =
        StringUtils.isNotEmpty(userId) ? Optional.of(UUID.fromString(userId)) : Optional.absent();

    Optional<ArtistBo> optArtistBo = artistsService.getArtist(userUuid, artistUuid);

    if (optArtistBo.isPresent()) {
      return Observable.just(Optional.of(Translators.translate(optArtistBo.get())));
    }

    return Observable.just(Optional.absent());
  }

  // ===========================================================================================================
  // SONGS
  // =================================================================================================================

  /**
   * Get browsing data for songs in the catalog.
   * <p>
   * This is a paginated query - it returns data one page at a time. The first page is retrieved by
   * passing <code>null</code> as the <code>pagingState</code>. Subsequent pages are retrieved by
   * passing the <code>pagingState</code> that accompanied the previously retrieved page.
   * <p>
   * The page size is determined by the <code>items</code> parameter when retrieving the first page.
   * This value is used for all subsequent pages, (the <code>items</code> parameter is ignored when
   * retrieving subsequent pages).
   * <p>
   * Data can be filtered using the <code>facets</code> parameter when retrieving the first page.
   * This value is used for all subsequent pages, (the <code>facets</code> parameter is ignored when
   * retrieving subsequent pages).
   *
   * @param pagingState Used for pagination control. To retrieve the first page, use
   *        <code>null</code>. To retrieve subsequent pages, use the <code>pagingState</code> that
   *        accompanied the previous page.
   * @param items Specifies the number of items to include in each page. This value is only
   *        necessary on the retrieval of the first page, and will be used for all subsequent pages.
   * @param facets Specifies a comma delimited list of search facet Ids to filter the results. Pass
   *        null or an empty string to not filter.
   * @param userId Specifies a user UUID identifying the currently logged-in user. Will be null for
   *        unauthenticated requests.
   * @return Observable&lt;SongList&gt;
   */
  public Observable<SongList> browseSongs(String pagingState, Integer items, String facets,
      String userId) {
    Optional<UUID> pagingStateUuid =
        StringUtils.isEmpty(pagingState) ? Optional.absent() : Optional.of(UUID
            .fromString(pagingState));
    Optional<UUID> userUuid =
        StringUtils.isEmpty(userId) ? Optional.absent() : Optional.of(UUID.fromString(userId));

    return Observable.just(Translators.translate(songsService.getSongsList(userUuid, items, facets,
        pagingStateUuid)));
  }

  /**
   * Get data on a song in the catalog.
   *
   * @param songId Specifies the UUID of the song to retrieve.
   * @param userId Specifies the UUID of the authenticated user.
   * @return Observable&lt;Optional&lt;SongInfo&gt;&gt;
   */
  public Observable<Optional<SongInfo>> getSong(String songId, String userId) {
    UUID songUuid = UUID.fromString(songId);
    Optional<UUID> userUuid =
        null == userId ? Optional.absent() : Optional.of(UUID.fromString(userId));

    Optional<SongBo> optSongBo = songsService.getSong(userUuid, songUuid);

    if (optSongBo.isPresent()) {
      return Observable.just(Optional.of(Translators.translate(optSongBo.get())));
    }

    return Observable.just(Optional.absent());
  }

}
